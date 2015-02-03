package com.example.blue_clientv3;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	String tag = "blue";
	Bluetooth b;
	Client c;
	final static int REQUEST_ENABLE_BT = 1;
	Button button;
	//EditText t1,t2;
	Display display;
	Point size;
	private GestureDetectorCompat gDetect;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.button1);
		display = getWindowManager().getDefaultDisplay(); 
		size = new Point();
		//t1 = (EditText) findViewById(R.id.editText1);
		//t2 = (EditText) findViewById(R.id.editText2);
		//t1.setFocusable(false);
		//t2.setFocusable(false);

		gDetect = new GestureDetectorCompat(this, new GestureListener());


	}

	public void seConnecter(View v){
		b.set_paired_device("bunt");
		b.create_scoket();
		Log.d(tag, "se connecter");
		b.run();
		if(b.get_ready())
		{
			c = new Client( (BluetoothSocket)   b.get_socket());//toast ok +start another activity with black screen
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
		if (!((BluetoothAdapter) b.getAdapter()).isEnabled()) {
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
		this.gDetect.onTouchEvent(event);
		return super.onTouchEvent(event);

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_VOLUME_UP) 
			   c.write(-3);
			 else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) 
				   c.write(-2);
			
			   return super.onKeyDown(keyCode, event);
			
	}
	
	class GestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			c.write(-5);
			return super.onSingleTapUp(e);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			c.write(distanceX, distanceY);
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

	}
}
