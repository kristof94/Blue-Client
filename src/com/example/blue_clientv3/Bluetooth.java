package com.example.blue_clientv3;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class Bluetooth extends Thread {

	BluetoothAdapter adapter;
	BluetoothDevice device;
	BluetoothSocket socket;
	boolean ready = false;
	
	public Bluetooth() {
		adapter =  BluetoothAdapter.getDefaultAdapter();
		if (adapter == null) {
		    // Device does not support Bluetooth
		}
	}
	
	public void set_device_paired(String name){
	// Get the bluetooth device server with the name. It must be paired with this client.
		Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices(); //List of all paired devices.
		if (pairedDevices.size() > 0) {					//Check if paired device exist
			for (BluetoothDevice DEVICE : pairedDevices) {
				if(DEVICE.getName().contains(name))
				{
					
					device = DEVICE;
					for(int i =0;i<device.getUuids().length;i++)
						Log.d("blue", String.valueOf( device.getUuids()[i].getUuid() ));

					break;
				}
			}
		}		
	}
int i = 0;
	public void create_socket(){
		BluetoothSocket tmp = null; 
		try {
			tmp = device.createRfcommSocketToServiceRecord(device.getUuids()[i].getUuid()) ;
			Log.d("blue",""+tmp);
			i++;
			if(i==device.getUuids().length)
				i=0;
		} catch (IOException e) { }
		socket = tmp;
	}
	
	public BluetoothSocket get_socket(){
		return socket;
	}
	
	public BluetoothAdapter getAdapter() {
		return adapter;
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
		ready = true;
		super.run();
	}

	public boolean get_ready(){
		return ready;
	}
	
	public void cancel() {
		try {
			socket.close();
		} catch (IOException e) { }
	}

}
