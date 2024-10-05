package com.tyom.data.providers

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.media.midi.MidiDeviceInfo
import android.media.midi.MidiInputPort
import android.media.midi.MidiManager
import android.media.midi.MidiReceiver
import android.os.Handler
import android.os.Looper
import android.os.ParcelUuid
import android.util.Log
import com.tyom.core_utils.BuildConfig
import com.tyom.core_utils.constants.BuildTypeConstants.DEBUG_TYPE
import com.tyom.core_utils.extensions.isNotNull
import dagger.hilt.android.qualifiers.ApplicationContext
import java.nio.ByteBuffer
import java.util.UUID
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


@Singleton
class MidiProvider(
    @ApplicationContext val context: Context
) {
    private val TIME_FOR_SCANNING = 3000L
    private val MIDI_SERVICE_UUID = "03B80E5A-EDE8-4B33-A751-6CE34EC4C700"
    private val DEFAULT_PORT_NUMBER = 0
    private val DEFAULT_CAPACITY = 3
    private val NOTE_ON_MESSAGE = 0x90
    private val NOTE_OFF_MESSAGE = 0x80
    private val ZERO_VELOCITY = 0
    private val ZERO_TIMESTAMP = 0

    private val midiManager: MidiManager? by lazy {
        context.getSystemService(Context.MIDI_SERVICE) as MidiManager
    }

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }


    private val bluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                // successfully connected to the GATT Server
                Log.d("MidiProvider", "successfully connected to the GATT Server")

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                //
                Log.d("MidiProvider", "disconnected from the GATT Server")

            } else if (newState == BluetoothProfile.STATE_DISCONNECTING) {

            } else {

            }
        }
    }


    @SuppressLint("MissingPermission")
    suspend fun scanMidiInstruments(): List<BluetoothDevice?> = suspendCoroutine { continuation ->
        val bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner

        val scanFilter = ScanFilter.Builder()
            .setServiceUuid(ParcelUuid.fromString(MIDI_SERVICE_UUID))
            .build()

        val scanFilters = listOf(scanFilter)

        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        val devices = mutableListOf<BluetoothDevice?>()

        val mScanCallback: ScanCallback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                if (BuildConfig.BUILD_TYPE == DEBUG_TYPE) {
                    Log.d("MidiProvider", "onScanResult: callbackType ${callbackType}")
                    Log.d("MidiProvider", "onScanResult: result ${result}")
                }
                val btDevice: BluetoothDevice = result.device
                if (!devices.contains(btDevice)) {
                    devices.add(btDevice)
                }
            }

            override fun onBatchScanResults(results: List<ScanResult?>) {
                for (sr in results) {
                    if (BuildConfig.BUILD_TYPE == DEBUG_TYPE) {
                        Log.d("MidiProvider", "Scan Result - Results: ${sr}")
                    }
                    sr?.device?.let { device ->
                        if (!devices.contains(device)) {
                            devices.add(device)
                        }
                    }
                }
            }

            override fun onScanFailed(errorCode: Int) {
                if (BuildConfig.BUILD_TYPE == DEBUG_TYPE) {
                    Log.d("MidiProvider Scan Failed", "Error Code: $errorCode")
                }
                continuation.resumeWithException(Exception("MidiProvider Scan failed with error code $errorCode"))
            }
        }

        bluetoothLeScanner?.startScan(scanFilters, scanSettings, mScanCallback)

        Handler(Looper.getMainLooper()).postDelayed({
            bluetoothLeScanner?.stopScan(mScanCallback)
            continuation.resume(devices)
        }, TIME_FOR_SCANNING)
    }

    suspend fun connectBluetoothDevice(device: BluetoothDevice, midiReceiver: MidiReceiver) =
        suspendCoroutine { continuation ->
            midiManager?.run {
                val deviceOpenedListener = MidiManager.OnDeviceOpenedListener { midiDevice ->
                    if (BuildConfig.BUILD_TYPE == DEBUG_TYPE) {
                        Log.d("MidiProvider", "connectBluetoothDevice: ${midiDevice.info}")
                    }

                    val outputPort = midiDevice.openOutputPort(DEFAULT_PORT_NUMBER)
                    if (outputPort.isNotNull()) {
                        outputPort.connect(midiReceiver)
                        continuation.resume(true)
                    } else {
                        if (BuildConfig.BUILD_TYPE == DEBUG_TYPE) {
                            Log.e("MidiProvider", "Failed to open output port")
                        }
                        continuation.resume(false)
                    }
                }
                openBluetoothDevice(device, deviceOpenedListener, null)

            }
        }

    private fun sendNoteOn(inputPort: MidiInputPort, note: Int, velocity: Int) {
        val message = ByteBuffer.allocate(DEFAULT_CAPACITY)
        message.put(NOTE_ON_MESSAGE.toByte())
        message.put(note.toByte())
        message.put(velocity.toByte())
        message.flip()

        inputPort.send(message.array(), ZERO_TIMESTAMP, message.remaining())
    }

    private fun sendNoteOff(inputPort: MidiInputPort, note: Int) {
        val message = ByteBuffer.allocate(DEFAULT_CAPACITY)
        message.put(NOTE_OFF_MESSAGE.toByte())
        message.put(note.toByte())
        message.put(ZERO_VELOCITY.toByte())
        message.flip()

        inputPort.send(message.array(), ZERO_TIMESTAMP, message.remaining())
    }

    fun startAdvertising() {
        val bluetoothLeAdvertiser = bluetoothAdapter?.bluetoothLeAdvertiser

        if (bluetoothLeAdvertiser == null) {
            Log.e("BleAdvertiser", "BLE Advertiser is not supported on this device")
            return
        }

        val advertiseSettings = AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY) // Высокая частота рекламирования
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH) // Высокий уровень мощности передачи
            .setConnectable(true) // Устройство будет доступно для подключения
            .build()

        val advertiseData = AdvertiseData.Builder()
            .setIncludeDeviceName(true) // Включить имя устройства в рекламные данные
            .setIncludeTxPowerLevel(true) // Включить уровень мощности передачи
            .addServiceUuid(ParcelUuid(UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb"))) // Пример UUID сервиса
            .build()

        val advertiseCallback = object : AdvertiseCallback() {
            override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
                super.onStartSuccess(settingsInEffect)
                Log.d("BleAdvertiser", "Advertising started successfully")
            }

            override fun onStartFailure(errorCode: Int) {
                super.onStartFailure(errorCode)
                Log.e("BleAdvertiser", "Advertising failed with error code: $errorCode")
            }
        }

        bluetoothLeAdvertiser.startAdvertising(advertiseSettings, advertiseData, advertiseCallback)
    }

    fun stopAdvertising() {
        val bluetoothLeAdvertiser = bluetoothAdapter?.bluetoothLeAdvertiser
        bluetoothLeAdvertiser?.stopAdvertising(object : AdvertiseCallback() {})
        Log.d("BleAdvertiser", "Advertising stopped")
    }
}