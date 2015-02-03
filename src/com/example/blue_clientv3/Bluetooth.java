package com.example.blue_clientv3;

import java.io.IOException;
import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class Bluetooth extends Thread implements IBuetooth {

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

	@Override
	public void set_paired_device(String name) {
		Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices(); //List of all paired devices.
		if (pairedDevices.size() > 0) {					//Check if paired device exist
			for (BluetoothDevice DEVICE : pairedDevices) {
				if(DEVICE.getName().contains(name))
				{
					
					device = DEVICE;
					for(int i =0;i<device.getUuids().length;i++)
						{
							Log.d("blue", String.valueOf( device.getUuids()[i].getUuid()));
							
						}
					
					break;
				}
			}
		}		
		
	}

	@Override
	public void create_scoket() {
		BluetoothSocket tmp = null; 
		try {
					tmp = device.createRfcommSocketToServiceRecord(device.getUuids()[0].getUuid()) ;
			boolean v = true;
			
			if(device.setPairingConfirmation(v))
				Log.d("blue","CONFIRME");
			else
				Log.d("blue","NOT PAIRED");
		} catch (IOException e) { }
		socket = tmp;
		
	}

	@Override
	public Object get_socket() {
		return socket;
		
	}

	@Override
	public Object getAdapter() {
		return adapter;
		
	}

	@Override
	public boolean get_ready() {
		return ready;
		
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
		Log.d("blue", "Connexion �tablie");
		ready = true;
		super.run();
	}
	
	public void cancel() {
		try {
			socket.close();
		} catch (IOException e) { }
	}
}
