/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package serialport;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.os.Bundle;


public abstract class SerialPortActivity extends Activity {

//	protected Application mApplication;
	protected SerialPort mSerialPort;
	protected OutputStream mOutputStream;
	private InputStream mInputStream;
	private ReadThread mReadThread;

	private class ReadThread extends Thread {

		@Override
		public void run() {
			super.run();
			while(!isInterrupted()) {
				int size;
				try {
					byte[] buffer = new byte[64];
					if (mInputStream == null) return;
					size = mInputStream.read(buffer);
					if (size > 0) {
						onDataReceived(buffer, size);
					}
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			try {
				mSerialPort = new SerialPort(new File("/dev/ttyUSB0"), 19200, 0);
				mOutputStream = mSerialPort.getOutputStream();
				mInputStream = mSerialPort.getInputStream();

				/* Create a receiving thread */
				mReadThread = new ReadThread();
				mReadThread.start();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

	}

	protected abstract void onDataReceived(final byte[] buffer, final int size);

	@Override
	protected void onDestroy() {
		if (mReadThread != null)
			mReadThread.interrupt();
//		mApplication.closeSerialPort();
		if (mSerialPort != null) {
			mSerialPort.close();
			mSerialPort = null;
		}
		mSerialPort = null;
		super.onDestroy();
	}
}
