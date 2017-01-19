package p4u1.dd.phoarsquare;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class GameClient extends Thread implements TextInputListener {
	
	String server;
	
	Socket socket = null;  
	ObjectOutputStream os = null;
    ObjectInputStream is = null;
    boolean connected = false;
	
	boolean running = true;
	boolean fail = false;
	boolean startMe = false;
	boolean hasMatch = false;
	ArrayList<Player> players;
	Vector3 ball_loc;
	Ball ball;
	PhoarMatch match;
	
	int match_status;
	
	public GameClient() {
		players = new ArrayList<Player>();
		match = new PhoarMatch(this);
		match_status = PhoarMatch.TRANSITION;
		//Gdx.input.getTextInput(this, "Server Address", "");
	}
	
	public void connect() {
		try {
			socket = new Socket(server, GameServer.PORT_NO);
		} catch (UnknownHostException e) {
			fail = true;
			e.printStackTrace();
		} catch (IOException e) {
			fail = true;
			e.printStackTrace();
		}
		
		if (!fail) {
			try {
				os = new ObjectOutputStream(socket.getOutputStream());
				is = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				fail = true;
				e.printStackTrace();
			}
		}
		
		if (!fail) {
			connected = true;
			startMe = true;
			this.start();
		}
	}
	
	public void handleProtocol(PhoarSquareProtocol arg0) {
		int protocol = arg0.getProtocol();
		switch (protocol) {
		case PhoarSquareProtocol.BALL_LOC_UPDATE:
			this.ball_loc = arg0.getLocation();
			break;
		case PhoarSquareProtocol.PLAYER_ORDER:
			this.players = arg0.getPlayerList();
			break;
		case PhoarSquareProtocol.ROUND_END:
			this.match_status = PhoarMatch.TRANSITION;
			break;
		case PhoarSquareProtocol.ROUND_SERVING:
			this.match_status = PhoarMatch.SERVING;
			break;
		case PhoarSquareProtocol.ROUND_SERVED:
			this.match_status = PhoarMatch.IN_PLAY;
			break;
		case PhoarSquareProtocol.UPDATE_BALL:
			this.ball = arg0.getBall();
			if (this.hasMatch){
				this.match.ball = arg0.getBall();
				System.out.println("--" + this.match.ball.v.toString());
			}
			break;
		case PhoarSquareProtocol.MATCH_UPDATE:
			synchronized(match) {
				this.match = arg0.getMatch();
			}
			startMe = true;
			
			this.hasMatch = true;
			System.out.println(this.match.ball.v.toString());
			//System.out.println("yo");
			break;
		}
		
	}
	
	public void run() {
		while (running) {
        	try {
				PhoarSquareProtocol protocol = (PhoarSquareProtocol) is.readObject();
				if (protocol != null) {
					handleProtocol(protocol);
					//System.out.println(protocol.toString());
				}
        	}catch (ClassCastException e) {
        		e.printStackTrace();
        	} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SocketException e) {
        		System.out.println("Server gone.");
        		running = false;
			} catch (IOException e) {
        		e.printStackTrace();
        	}
        }
		
	}
	/*
	public static void main(String[] args) throws IOException, UnknownHostException{
		System.out.println("This is a client, bitch.");
		Socket socket = null;  
		ObjectOutputStream os = null;
        ObjectInputStream is = null;
        boolean running = true;
        try {
        	socket = new Socket("localhost", GameServer.PORT_NO);
        } catch (ConnectException e) {
        	System.out.println("Could not connect to Server");
        	running = false;
        }
        if (running) {
        	os = new ObjectOutputStream(socket.getOutputStream());
        	is = new ObjectInputStream(socket.getInputStream());
        }
        String input, response;
        
       
        
        while (running) {
        	try {
				PhoarSquareProtocol protocol = (PhoarSquareProtocol) is.readObject();
				if (protocol != null) {
					System.out.println(protocol.toString());
				}
        	} catch (SocketException e) {
        		System.out.println("Server gone.");
        		running = false;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
        }
	}
	*/
	
	@Override
	public void input(String text) {
		// TODO Auto-generated method stub
		server = text;
		connect();
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
		
	}
}
