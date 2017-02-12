package com.example.qr_codescan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	/**
	 * 锟斤拷示扫锟斤拷锟斤拷
	 */
	private TextView mTextView;
	/**
	 * 锟斤拷示扫锟斤拷锟侥碉拷图片
	 */
	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTextView = (TextView) findViewById(R.id.result);
		mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);

		// 锟斤拷锟斤拷锟脚ワ拷锟阶拷锟斤拷锟轿拷锟缴拷锟斤拷锟芥，锟斤拷锟斤拷锟矫碉拷锟斤拷startActivityForResult锟斤拷转
		// 扫锟斤拷锟斤拷锟斤拷之锟斤拷锟斤拷锟斤拷媒锟斤拷锟�
		Button mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MipcaActivityCapture.class);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
				
//				sendResult("rrrrrrrr");

				// Intent intent = new Intent();
				// intent.setClass(MainActivity.this,
				// PlayerFullScreenActivity.class);
				// String mp4name ="http://123.57.57.7/estmis/demo.mp4";
				// intent.putExtra(PlayerFullScreenActivity.PLAY_DATA, mp4name);
				// intent.putExtra(PlayerFullScreenActivity.PLAY_MODEL, 1);
				// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
				// startActivity(intent);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				String str = bundle.getString("result");
				Toast.makeText(getApplicationContext(), "4444444444 is : " + str, Toast.LENGTH_LONG).show();

				// 锟斤拷示扫锟借到锟斤拷锟斤拷锟斤拷
				mTextView.setText(bundle.getString("result"));
				sendResult(bundle.getString("result"));
				// 锟斤拷示
				mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
			}
			break;
		}
	}

	private void sendResult(final String result) {

		Toast.makeText(getApplicationContext(), "sendResult:begin", Toast.LENGTH_LONG).show();
		
		MyHandler handler = new MyHandler(this, result);
		Message msg = new Message();
		msg.what = 0;
		handler.sendMessage(msg);

		Toast.makeText(getApplicationContext(), "sendResult:end", Toast.LENGTH_LONG).show();
	}

	class PlayRunnable implements Runnable {
		String str;

		public PlayRunnable(String str) {
			this.str = str;
		}

		@Override
		public void run() {
			try {

//				Toast.makeText(MainActivity.this, "PlayRunnable:begin", Toast.LENGTH_LONG).show();

				Socket socket = new Socket("192.168.1.199", 1234);
//				Socket socket = new Socket("192.168.56.84", 1234);
				// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟较�
				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
						true);
				out.println(str);
//				Toast.makeText(MainActivity.this, "PlayRunnable:str", Toast.LENGTH_LONG).show();
				// 锟斤拷锟杰凤拷锟斤拷锟斤拷锟斤拷锟斤拷息
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String mstr = br.readLine();
				out.close();
				br.close();
				socket.close();
//				Toast.makeText(MainActivity.this, "PlayRunnable:end", Toast.LENGTH_LONG).show();

			} catch (UnknownHostException e) {
//				Toast.makeText(MainActivity.this, "11111111111111", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			} catch (IOException e) {
//				Toast.makeText(MainActivity.this, "222222222222", Toast.LENGTH_LONG).show();
//				Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			} catch (Exception e) {
//				Toast.makeText(MainActivity.this, "3333333333333", Toast.LENGTH_LONG).show();
				Log.e("", e.toString());
			}
		}
	}

	class MyHandler extends Handler { 
		private Context activity;
		private String result;
        public MyHandler(Activity activity, String result) { 
        	this.result = result;
        	this.activity = activity;
        } 
 
        // 子类必须重写此方法，接受数据 
        @Override 
        public void handleMessage(Message msg) { 

        	Toast.makeText(activity, "555555555", Toast.LENGTH_LONG).show();
//        	if (msg.what == 0) {
        		PlayRunnable playRunnable = new PlayRunnable(result);
        		new Thread(playRunnable).start();
//        	}
        		Toast.makeText(activity, "666666666", Toast.LENGTH_LONG).show();
 
        } 
    } 
	

}
