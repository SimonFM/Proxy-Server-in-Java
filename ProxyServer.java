/*
 * Created by Simon Markham
 * A proxy server class that accepts a request from a browser and then
 * forwards that request to the designated HOST, then accepts data from 
 * that host and in turn forwards that data back to the user.
 * */
import java.io.*;
import java.net.*;

import javax.swing.JTextArea;

public class ProxyServer
{
	// in milli seconds
	public static final int TIME_OUT = 10000;
	// a socket to user and to request
	Socket proxySocketUser; 
	// go between for the proxy
	ServerSocket proxyServer; 
	/**
	 * Created a proxyServer.
	 * @param serverPort - port number for proxy to listen on
	 * @throws IOException
	 **/
	ProxyServer(int serverPort) throws IOException{
		try{
			proxyServer = new ServerSocket(serverPort);
			//proxyServer.setSoTimeout(TIME_OUT);
			System.out.println("Started a proxy on port: " + serverPort);
		}
		catch (IOException e){
			System.out.println("Could not create proxy server on port: "+serverPort);
		}
	}
	/**
	 * Method that maintains a connection to a host from a socket.
	 **/
	void run() throws IOException{
		while(true){
			try{
				System.out.println("Listening on port: "+proxyServer.getLocalPort());
				new ProxySocket( proxyServer.accept()).run();
			}
			catch(SocketTimeoutException timeout){
				System.out.println("Okay so a timeout occured, but relax its restarting");
			}
			catch (IOException e){
				System.out.println("Something went wrong with the connection");
			}
		}
	}
}