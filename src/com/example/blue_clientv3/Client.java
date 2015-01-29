package com.example.blue_clientv3;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import android.bluetooth.BluetoothSocket;

public class Client extends Thread{

	BluetoothSocket socket;
	OutputStream out;
	PrintWriter print;
	public Client(BluetoothSocket s)
	{
		socket = s;
		try {
			out = socket.getOutputStream();
			//print = new PrintWriter(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
	}

	public void write(String msg)
	{
		print = new PrintWriter(out);
		print.println(msg);
		print.close();
	}
}
