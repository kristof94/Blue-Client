package com.example.blue_clientv3;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import android.bluetooth.BluetoothSocket;

public class Client extends Thread{

	BluetoothSocket socket;
	DataOutputStream out;
	ByteBuffer tab = ByteBuffer.allocate(Float.SIZE);
	ByteBuffer tab2 = ByteBuffer.allocate(8);

	boolean v =false;
	public Client(BluetoothSocket s)
	{
		socket = s;


		try {
			out = new DataOutputStream(socket.getOutputStream());
			v=true;
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



	public void close(){

		try{
		write(-6);
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void write(int x) {
		
			tab2.asIntBuffer().put((int) x );
			try {
				out.write(tab2.array());
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	

	public void write(float x, float y) {

		tab.asIntBuffer().put((int) x );
		tab.asIntBuffer().put(4,(int) y);
		try {
			out.write(tab.array());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
