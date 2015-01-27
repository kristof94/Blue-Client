package com.example.blue_client.v2;

import java.io.IOException;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class Bluetooth extends Thread {

	BluetoothAdapter adapter;
	BluetoothDevice device;
	BluetoothSocket socket;
	final static int REQUEST_ENABLE_BT = 1;
	
	public Bluetooth() {
	//Test if bluetooth is enabled ? No: power on bluetooth
		if (!mBluetoothAdapter.isEnabled()) {
    			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
	}

	public void set_device_paired(String name){

		Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
		if (pairedDevices.size() > 0) {
			for (BluetoothDevice DEVICE : pairedDevices) {
				if(DEVICE.getName().contains(name))
				{
					device = DEVICE;
					break;
				}
			}
		}		
	}

	public void create_socket(){
		BluetoothSocket tmp=null; 
		try {
			tmp = device.createRfcommSocketToServiceRecord(device.getUuids()[4].getUuid());
		} catch (IOException e) { }
		socket = tmp;
	}

	@Override
	public void run() {
		try {
			// Connect the device through the socket. This will block
			// until it succeeds or throws an exception
			socket.connect();
		} catch (IOException connectException) {
			// Unable to connect; close the socket and get out
			try {
				socket.close();
			} catch (IOException closeException) { }
			return;
		}
		Log.d("blue", "Connexion établie");
		// Do work to manage the connection (in a separate thread)
		super.run();
	}

	public void cancel() {
		try {
			socket.close();
		} catch (IOException e) { }
	}
}
