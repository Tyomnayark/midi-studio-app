package com.tyom.data.midi

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.media.midi.MidiInputPort
import android.media.midi.MidiManager
import android.media.midi.MidiReceiver
import android.os.Handler
import android.os.Looper
import android.os.ParcelUuid
import android.util.Log
import com.tyom.utils.BuildConfig
import com.tyom.utils.constants.BuildTypeConstants.DEBUG_TYPE
import com.tyom.utils.extensions.isNotNull
import dagger.hilt.android.qualifiers.ApplicationContext
import java.nio.ByteBuffer
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


@Singleton
class MidiProvider(
    @ApplicationContext context: Context
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

    suspend fun connectBluetoothDevice(device: BluetoothDevice, midiReceiver: MidiReceiver) {
        midiManager?.run {
            val handler = Handler(Looper.getMainLooper())
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
            openBluetoothDevice(device, deviceOpenedListener, handler)
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