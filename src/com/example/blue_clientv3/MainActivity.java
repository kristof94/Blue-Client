package com.example.blue_clientv3;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	String tag = "blue";
	Bluetooth b;
	Client c;
	final static int REQUEST_ENABLE_BT = 1;
	EditText t;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		t = (EditText) findViewById(R.id.editText1);
	}

	public void seConnecter(View v){
		b.set_device_paired("RISTOF");
		b.create_socket();
		Log.d(tag, "se connecter");
		b.run();
		if(b.get_ready())
			c = new Client(b.get_socket());//toast ok +start another activity with black screen
	}

	public void Send(View v){
		Log.d(tag, "send");
		c.write(t.getText().toString());
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		b = new Bluetooth();
		//Test if bluetooth is enabled ? No: power on bluetooth	
		if (!b.getAdapter().isEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
		
		super.onResume();
	}

	@Override
	protected void onStop() {
		//b.cancel();
		super.onStop();
	}

}
