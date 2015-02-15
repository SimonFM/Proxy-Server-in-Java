// Created by Simon Markham
// 

import java.io.*;
import java.net.*;

/**
 * A class that represents each call from the user's end
 * of a connection and creates a corresponding request to 
 * the desired location of user. 
 * 	ie: a web-site from mozilla.
 * */
public class ProxySocket extends Thread{
	final int BUFFER_SIZE = 65635;
	Socket userSocket, forwardSocket;

	/**
	 * Constructor for a proxySocket:
	 * Takes in a socket s and then assigns that socket to an 
	 * instance of a socket.
	 * */ 
	ProxySocket(Socket s){
		this.userSocket = s;
	}
	/**
	 * Runs the proxy thread
	 * */
	public void run() 
	{
		try{
			// While the socket is not closed, keep sending
			// and receiving requests
			InputStream fromUser = userSocket.getInputStream();
			OutputStream toUser = userSocket.getOutputStream();
			InputStream fromWebsite = null;
			OutputStream toWebsite = null;
			String host = "";

			// Read in the request from the user in to 
			// a byte array 'c'
			byte[] c = new byte[BUFFER_SIZE];
			fromUser.read(c);
			String ss = new String(c);	
			System.out.println(ss);

			// Split the request up in to the individual
			// requests. And then search those elements for
			// the Host name.
			String[] lines = ss.split("\r\n");
			for(int i = 0; i < lines.length;i++){
				if(lines[i].contains("Host: ")){
					host = lines[i].substring(6).trim();
				}
			}
			// Connect to the host via socket 80
			// Input and Output streams from web-sites
			forwardSocket = new Socket(host,80);
			System.out.println("Connected to: "+ host);

			fromWebsite = forwardSocket.getInputStream();
			toWebsite = forwardSocket.getOutputStream();

			//Send request for data
			byte[] request = ss.getBytes();
			toWebsite.write(request);
			toWebsite.flush();	

			// Receive data from web-site.
			byte[] sentFromWeb = new byte[BUFFER_SIZE];
			int size = fromWebsite.read(sentFromWeb);
			// While the size of the packets are a positive number
			// write the data in to the user's socket.
			while( size > 0 )
			{
				toUser.write(sentFromWeb,0,size);
				toUser.flush();
				size = fromWebsite.read(sentFromWeb);
				System.out.println("Receiving data....from : "+host);
			}
			int length;
			// to deal with requests greater than 4k
			while(fromUser.available() != 0 && fromWebsite.available() != 0){

				if(fromUser.available() != 0){
					length = fromUser.read(c);
					toWebsite.write(c, 0, length);
					toWebsite.flush();
				}

				if(fromWebsite.available() != 0){
					length = fromWebsite.read(c);
					toUser.write(c, 0, length);
					toUser.flush();
				}

			}
			try
			{
				// Close the connection to the web site
				toWebsite.close();
				fromWebsite.close();
				// Close the connection to the user.
				toUser.close();
				fromUser.close();
				System.out.println("Closed Connection");
			}
			catch(IOException e1)
			{
				System.out.println("Unable to close connection");
				e1.printStackTrace();
			}
		}
		catch(IOException e1)
		{
			System.out.println("Unable to connect to host");
			e1.printStackTrace();
		}
	}
}