package com.example.blue_clientv3;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	String tag = "blue";
	Bluetooth b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void seConnecter(View v){
		Log.d(tag, "se connecter");
		b.run();
	}
	
	public void Send(View v){
		Log.d(tag, "send");
	}
	
	@Override
	protected void onStart() {
		b = new Bluetooth();
		super.onStart();
	}

	@Override
	protected void onResume() {
		b.set_paired("RISTOF");
		b.create_socket();
		super.onResume();
	}

	@Override
	protected void onStop() {
		b.cancel();
		super.onStop();
	}

}
