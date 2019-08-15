package com.fakau.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainThread extends Thread{
	private int playerCount = 0;
	private int niveau = 0;

	public static void main(String arg[]) {
		new MainThread().start();
	}
	@Override
	public void run() {
		try {
			ServerSocket serverSocket=new ServerSocket(4232);
			while (true) {
				
				
				System.out.println("wait for new player");
				Socket socket = serverSocket.accept();
				System.out.println("new player connected "+ socket.getRemoteSocketAddress());
				
			
			    if(playerCount == 0 ){
			    	++playerCount;
			    	System.out.println("Count player is: "+ playerCount);
			    	new CheckPlayer(niveau, playerCount, socket).config();
			    }else{
			    	++playerCount;
			    	System.out.println("Count player is: "+ playerCount);
			    	new ServiceGame(playerCount,socket).start();
			    }
			}
			
		} catch (IOException e) {
			System.out.println("Error "+ e.getMessage());
		}
	}

}
