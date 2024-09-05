package com.tyom.data.midi

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.tyom.domain.models.Instrument
import javax.inject.Inject

class BluetoothMidiInstrumentProvider @Inject constructor(
    private val context: Context,
) {

    companion object {
        private const val REQUEST_CODE_BLUETOOTH_PERMISSIONS = 1001
    }

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    @SuppressLint("MissingPermission")
    fun getBluetoothInstruments(): List<Instrument> {
        val instruments = mutableListOf<Instrument>()
        val adapter = bluetoothAdapter ?: return instruments

        if (!adapter.isEnabled) {
            // Bluetooth недоступен или выключен
            return instruments
        }

        if (!hasBluetoothPermissions()) {
            requestBluetoothPermissions()
            return instruments
        }

        val bondedDevices = adapter.bondedDevices
        bondedDevices?.let { devices ->
            for (device in devices) {
                if (isMidiDevice(device)) {
                    val instrument = device.toInstrument()
                    instrument?.let { instr ->
                        instruments.add(instr)
                    }
                }
            }
        }
        return instruments
    }

    private fun hasBluetoothPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestBluetoothPermissions() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
            REQUEST_CODE_BLUETOOTH_PERMISSIONS
        )
    }

    @SuppressLint("MissingPermission")
    private fun isMidiDevice(device: BluetoothDevice): Boolean {
        return device.name?.contains("MIDI", ignoreCase = true) ?: false
    }

    @SuppressLint("MissingPermission")
    private fun BluetoothDevice.toInstrument(): Instrument {
        return Instrument(
            name = this.name ?: "Unknown"
        )
    }
}
