package com.fakau.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class CheckPlayer {
	public static int niveau;
	private int playerCount;
	private Socket socket;
	
	

	public CheckPlayer(int niveau, int playerCount, Socket socket) {
		super();
		this.niveau = niveau;
		this.playerCount = playerCount;
		this.socket = socket;
	}



	public void config() throws IOException{
		InputStream in=socket.getInputStream();
		OutputStream out= socket.getOutputStream();
		PrintWriter pw=new PrintWriter(out, true);
		InputStreamReader inR=new InputStreamReader(in);
		BufferedReader bRIn=new BufferedReader(inR);
		    pw.println("Choisir un niveau de jeux");
		    pw.println("1- Apuiyer sur 1 pour le niveau 1");
		    pw.println("2- Apuiyer sur 2 pour le niveau 2");
			try{
				niveau =Integer.parseInt( bRIn.readLine());
				if(niveau != 1 && niveau != 2 && niveau != 3){
					throw new RuntimeException("Niveau out of bound");
				}
				new ServiceGame(niveau, playerCount, socket).start();
			}catch(Exception ex){
				pw.println("\nError de saisir use a number "+ ex.getMessage());
				config();
			}
	}

}
