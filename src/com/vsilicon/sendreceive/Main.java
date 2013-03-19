package com.vsilicon.sendreceive;


import java.io.IOException;

import serialport.SerialPortActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


public class Main extends SerialPortActivity {

	EditText mReceice;
	EditText mSend;
	Button btSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		

		mReceice = (EditText) findViewById(R.id.etReceive);
		mSend = (EditText) findViewById(R.id.etSend);
		mSend.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				int i;
				CharSequence t = v.getText();
				char[] text = new char[t.length()];
				for (i=0; i<t.length(); i++) {
					text[i] = t.charAt(i);
				}
				try {
					mOutputStream.write(new String(text).getBytes());
					mOutputStream.write('\n');
					mSend.setText("");
				} catch (IOException e) {
					e.printStackTrace();
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDataReceived(final byte[] buffer, final int size) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (mReceice != null) {
					mReceice.append(new String(buffer, 0, size));
				}
			}
		});
	}
	


}
