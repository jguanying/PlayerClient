package com.socket.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.qr_codescan.MainActivity;
import com.example.qr_codescan.PlayerFullScreenActivity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.renderscript.Long4;
import android.util.Log;

public class AndroidServer extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
//		new SocketServer(getApplicationContext()).start();
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		new SocketServer(getApplicationContext()).start();
		super.onStart(intent, startId);
	}
	
	class SocketServer extends Thread {
		
		private Context mContext;
		
		SocketServer(Context context) {
			mContext = context;
		}

		@Override
		public void run() {
			try {
				ServerSocket serverSocket=new ServerSocket(54321);
				while(true)
				{
					System.out.println("SocketServer begin .... ");
					Log.d("scan_server", "SocketServer begin .... ");
					//���ܿͻ�������
					Socket client=serverSocket.accept();
					try
					{
						//���ܿͻ�����Ϣ
						BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
						String str=in.readLine();
						System.out.println("read:  "+str);
						Log.d("scan_server str is :", str);
						//�������������Ϣ
//						PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
//						out.println("return	"+str);
						
						Intent intent = new Intent();
						intent.setClass(mContext, PlayerFullScreenActivity.class);
						String mp4name ="" + str;
						intent.putExtra(PlayerFullScreenActivity.PLAY_DATA, mp4name);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
						mContext.startActivity(intent);
						in.close();
//						out.close();
					}catch(Exception ex)
					{
						System.out.println(ex.getMessage());
						ex.printStackTrace();
					}
					finally
					{
						client.close();
						System.out.println("close");
					}
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			super.run();
		}
		
	}

}
