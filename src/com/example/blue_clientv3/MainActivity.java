package com.example.blue_clientv3;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	String tag = "blue";
	Bluetooth b;
	Client c;
	final static int REQUEST_ENABLE_BT = 1;
	Button button;
	Display display;
	Point size;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button1);
		display = getWindowManager().getDefaultDisplay(); 
		size = new Point();
	}

	public void seConnecter(View v){
		b.set_device_paired("RISTOF");
		b.create_socket();
		Log.d(tag, "se connecter");
		b.run();
		if(b.get_ready())
		{
			c = new Client(b.get_socket());//toast ok +start another activity with black screen*/
			button.setVisibility(View.GONE);
		}
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
		c.close();
		super.onStop();
	}

	float x=0,y=0;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (b.get_ready())
			{
				c.write(event.getX()-x, event.getY()-y);
				x =  event.getX();
				y= event.getY();
				}
		return super.onTouchEvent(event);
	}

}
