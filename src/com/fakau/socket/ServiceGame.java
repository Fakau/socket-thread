package com.fakau.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

public class ServiceGame extends Thread {
	private int  playerCount;
	private Socket socket;
	private static SocketAddress win = null;
	private static int numberGenerate = 0;
	private static StatusGame statusGame = StatusGame.IN_PLAY;
	
	public ServiceGame(int niveau, int playerCount, Socket socket) {
		super();
		this.playerCount = playerCount;
		this.socket = socket;
		if(niveau == 1){
			numberGenerate = (int) (Math.random()*100);
		}else if(niveau == 2){
			numberGenerate = (int) (Math.random()*50);
		}
	}
	public ServiceGame(int playerCount, Socket socket) {
		super();
		this.playerCount = playerCount;
		this.socket = socket;
		//waitingMessage(this.socket);
	}
	@Override
	public void run() {
	    PrintWriter pw = null;
		while (win == null) {
			  try{
			
					InputStream in=socket.getInputStream();
					OutputStream out= socket.getOutputStream();
					pw=new PrintWriter(out, true);
					InputStreamReader inR=new InputStreamReader(in);
					BufferedReader bRIn=new BufferedReader(inR);
					pw.println("Saisir un nombre entre :"+ (CheckPlayer.niveau == 1?"1 a 100": "1 a 50"));
					int val = Integer.parseInt(bRIn.readLine());
					if(val == numberGenerate){
						win =  socket.getRemoteSocketAddress();
						statusGame = StatusGame.FIN;
						pw.println("\n\n");
						pw.println("***************BINGO*******************");
						pw.println("***********Vous avez gagne**************");
						pw.println("\n\n");
					}else if(val > numberGenerate){
						pw.println("Valeur superieur au nombre rechercher");
					}else if(val < numberGenerate){
						pw.println("Valeur inferieur au nombre rechercher");
					}
				}catch(Exception ex){
					pw.println("\nError de saisir use a number "+ ex.getMessage());
				}
		}
		whenDone(socket);
	}
	
	public static void whenDone(Socket s){
		
		try {
			OutputStream out = s.getOutputStream();
			PrintWriter pw=new PrintWriter(out, true);
			pw.println("Jeux terminer");
			pw.println("Player :"+ win +" a gagne");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
public static void waitingMessage(Socket s){
		
		try {
			OutputStream out = s.getOutputStream();
			PrintWriter pw=new PrintWriter(out, true);
			pw.println("Wait for configuration......");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
