package p4u1.dd.phoarsquare;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.badlogic.gdx.utils.Array;

public class GameServer {

	public final static int PORT_NO = 6948;

	private ServerSocket listener;
	private List<Connection> clients;
	private ConnectionThread connectizer;
	Array<PhoarSquareProtocol> inputBuffer;
	Array<PhoarSquareProtocol> outputBuffer;
	WorkHorse work_horse;
	String ipaddress = "";
	
	public GameServer() {
		System.out.println("Enter GameServer Constructor");
		try {
			listener = new ServerSocket(PORT_NO);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("ServerSocket Created");
		inputBuffer = new Array<PhoarSquareProtocol>();
		outputBuffer = new Array<PhoarSquareProtocol>();
		connectizer = new ConnectionThread();
		work_horse = new WorkHorse();
		clients = new ArrayList<Connection>();
		//System.out.println(InetAddress.getLocalHost().getHostAddress());
		//ipaddress = InetAddress.getLocalHost().getHostAddress();
		try {
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
			for (; n.hasMoreElements();)
		    {
		        NetworkInterface e = n.nextElement();

		        Enumeration<InetAddress> a = e.getInetAddresses();
		        for (; a.hasMoreElements();)
		        {
		            InetAddress addr = a.nextElement();
		            String address = addr.getHostAddress();
		            if (address.substring(0, 3).equals("192")) {
		            	System.out.println(addr.getHostAddress());
		            	this.ipaddress = addr.getHostAddress();
		            }
		        }
		    }
		} catch (SocketException e) {
			e.printStackTrace();
		}
		System.out.println("End GameServer Constructor");
	}
	
	public void startServer() {
		connectizer.start();
		System.out.println("Connectizer running");
		work_horse.start();		
		System.out.println("WorkHorse running");
	}
	
	public void queueOutput(PhoarSquareProtocol arg0) {
		outputBuffer.add(arg0);
		//System.out.println(outputBuffer.size);
	}
	
	public void broadcast(PhoarSquareProtocol arg0) {
		for (int i = 0; i < clients.size(); i++){
			//System.out.println("Broadcast");
			clients.get(i).send(arg0);
		}
	}
	
	private class WorkHorse extends Thread {
		public synchronized void run() {
			PhoarSquareProtocol psp;
			while (true) {
				//System.out.println(outputBuffer.size);
				synchronized(outputBuffer) {
					while (outputBuffer.size > 0) {
						//System.out.println("Common");
						psp = outputBuffer.removeIndex(0);
						broadcast(psp);
					}
				}
			}
		}
	}
	
	private class ConnectionThread extends Thread {
		
		public void run() {
			while(true) {
				try {
					synchronized(clients) {
						clients.add(new Connection(listener.accept()));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	private class Connection extends Thread
	{
		private volatile ObjectInputStream br;
		private volatile ObjectOutputStream pw;
		private String clientName;
		Connection(Socket s) throws IOException
		{
			System.out.println("Yay! Client!");
			br = new ObjectInputStream(s.getInputStream());
			pw = new ObjectOutputStream(s.getOutputStream());
		}
		@Override
		public void run()
		{
			//System.out.println("test");
			PhoarSquareProtocol message;
			try
			{
				try {
					while ((message = (PhoarSquareProtocol) br.readObject()) != null){
						inputBuffer.add(message);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			catch (IOException ioe)
			{
				System.err.println("I/O error: "+ioe.getMessage());
			}
			finally
			{
				synchronized(clients)
				{
					clients.remove(this);
				}
			}

		}

		private void send(PhoarSquareProtocol message)
		{
			//pw.println(message);
			try {
				pw.writeObject(message);
			} catch (SocketException e) {
				System.out.println("Guess they left.");
				clients.remove(this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


}
