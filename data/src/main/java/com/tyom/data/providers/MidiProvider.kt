package com.tyom.data.providers

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
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
import androidx.compose.ui.graphics.colorspace.connect
import com.tyom.core_utils.BuildConfig
import com.tyom.core_utils.constants.BuildTypeConstants.DEBUG_TYPE
import com.tyom.core_utils.extensions.isNotNull
import com.tyom.data.nativemidi.AppMidiManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.nio.ByteBuffer
import java.util.UUID
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


@Singleton
class MidiProvider(
    @ApplicationContext context: Context,
) {

    private val TIME_FOR_SCANNING = 3000L
    private val TIME_FOR_UPDATING = 1000L
    private val MIDI_SERVICE_UUID = "03B80E5A-EDE8-4B33-A751-6CE34EC4C700"
    private val UUID_SPP = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
    private val DEFAULT_PORT_NUMBER = 0
    private val DEFAULT_CAPACITY = 3
    private val NOTE_ON_MESSAGE = 0x90
    private val NOTE_OFF_MESSAGE = 0x80
    private val ZERO_VELOCITY = 0
    private val ZERO_TIMESTAMP = 0

    private val midiManager: MidiManager? by lazy {
        context.getSystemService(Context.MIDI_SERVICE) as MidiManager
    }
    private val mAppMidiManager: AppMidiManager by lazy {
        AppMidiManager(midiManager)
    }

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val sendDevices = arrayListOf<MidiDeviceInfo>()
    private val receiveDevices = arrayListOf<MidiDeviceInfo>()


    @SuppressLint("MissingPermission")
    suspend fun scanMidiInstruments(): List<BluetoothDevice?> = withContext(Dispatchers.IO) {
        suspendCoroutine{ continuation ->
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
                    if (!devices.contains(result.device)) {
                        devices.add(result.device)
                    }
                }

                override fun onBatchScanResults(results: List<ScanResult?>) {
                    results.forEach { result ->
                        result?.device?.let { device ->
                            if (!devices.contains(device)) {
                                devices.add(device)
                            }
                        }
                    }
                }

                override fun onScanFailed(errorCode: Int) {
                    continuation.resumeWithException(Exception("Scan failed with error code $errorCode"))
                }
            }

            bluetoothLeScanner?.startScan(scanFilters, scanSettings, mScanCallback)

            Handler(Looper.getMainLooper()).postDelayed({
                bluetoothLeScanner?.stopScan(mScanCallback)
                continuation.resume(devices)
            }, TIME_FOR_SCANNING)
        }
    }

    @Throws(IOException::class)
    fun connectBluetoothSocket(device: BluetoothDevice): BluetoothSocket? {
        val socket: BluetoothSocket?
        try {
            // Создание сокета для подключения к устройству
            socket = device.createRfcommSocketToServiceRecord(UUID_SPP)
            socket.connect()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return socket
    }

    suspend fun connectBluetoothDevice(device: BluetoothDevice, midiReceiver: MidiReceiver): Boolean {
        return withContext(Dispatchers.IO) {
            midiManager?.run {
                val deviceOpenedListener = MidiManager.OnDeviceOpenedListener { midiDevice ->
                    if (BuildConfig.BUILD_TYPE == DEBUG_TYPE) {
                        Log.d("MidiProvider", "connectBluetoothDevice: ${midiDevice.info}")
                    }

                    val outputPort = midiDevice.openOutputPort(DEFAULT_PORT_NUMBER)
                    if (outputPort.isNotNull()) {
                        outputPort.connect(midiReceiver)
                    } else {
                        if (BuildConfig.BUILD_TYPE == DEBUG_TYPE) {
                            Log.e("MidiProvider", "Failed to open output port")
                        }
                    }
                }

                try {
                    openBluetoothDevice(device, deviceOpenedListener, null)
                } catch (e: Exception) {
                    Log.e("MidiProvider", "Error opening Bluetooth MIDI device", e)
                    return@run false
                }
            }
            true
        }
    }

    suspend fun connectBluetoothDeviceTwo(
        device: BluetoothDevice,
        midiReceiver: MidiReceiver,
    ): Boolean {
        return withContext(Dispatchers.IO) { // Or a suitable dispatcher
            try {
                val midiDevice = midiManager?.openBluetoothDevice(device, null, null)
                if (midiDevice != null) {
                    val outputPort = null
                    if (outputPort.isNotNull()) {
                        outputPort
                        // Add disconnection handling here (MidiDevice.StateChangeListener)
                        return@withContext true
                    } else {
                        Log.e("MidiProvider", "Failed to open output port")
                    }
                } else {
                    Log.e("MidiProvider", "Failed to open MIDI device")
                }
            } catch (e: Exception) {
                Log.e("MidiProvider", "Error connecting to MIDI device", e)
            }
            return@withContext false
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
}