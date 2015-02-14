import java.io.IOException;
import java.util.*;

import javax.swing.*;

public class ProxyInterface 
{
	public static final int PROXY_PORT = 10000;
	JFrame appWindow;
	BlackList myBlackList;
	/**
	 * Constructor for creating a frame for a proxy GUI.
	 * */
	ProxyInterface() throws IOException
	{
		appWindow = new JFrame("Proxy Server");
		appWindow.setSize(500, 500);
		appWindow.setVisible(true);
		appWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ProxyServer myProxy = new ProxyServer(PROXY_PORT);
		
		myProxy.run();
	}
	/***
	 * Initialises the proxy program
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException
	{		
		ProxyInterface myInterface = new ProxyInterface();
	}
}
