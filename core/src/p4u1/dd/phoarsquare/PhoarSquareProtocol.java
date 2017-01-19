package p4u1.dd.phoarsquare;

import java.io.Serializable;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class PhoarSquareProtocol implements Serializable{
	
	public static final int JOIN_GAME_REQUEST = 1;
	public static final int JOIN_GAME_CONFIRM = 2;
	public static final int BALL_LOC_UPDATE = 3;
	public static final int BALL_BOUNCE = 4;
	public static final int ROUND_END = 5;
	public static final int ROUND_SERVING = 6;
	public static final int CLIENT_READY = 7;
	public static final int CLIENT_LEAVE = 8;
	public static final int PLAYER_ORDER = 9;
	public static final int ROUND_SERVED = 10;
	public static final int UPDATE_BALL = 11;
	public static final int MATCH_UPDATE = 12;
	
	private String message = null;
	private Vector3 loc = null;
	private ArrayList<Player> player_list = null;
	private int protocol = 0;
	private Ball ball;
	private PhoarMatch match;
	
	public PhoarSquareProtocol(int arg0) {
		this.protocol = arg0;
	}
	
	public PhoarSquareProtocol(int arg0, PhoarMatch  arg1) {
		this(arg0);
		this.match = arg1;
	}
	
	public PhoarSquareProtocol(int arg0, Vector3 arg1) {
		this(arg0);
		this.loc = arg1;
	}
	
	public PhoarSquareProtocol(int arg0, Ball arg1) {
		this(arg0);
		this.ball = arg1;
	}
	
	public PhoarSquareProtocol(int arg0, ArrayList<Player> arg1) {
		this(arg0);
		this.player_list = arg1;
	}
	
	public PhoarSquareProtocol(int arg0, String arg1) {
		this(arg0);
		this.message = arg1;
	}
	
	public String toString() {
		String result = "";
		switch (protocol) {
		case BALL_LOC_UPDATE:
				result = "BALL_LOC_UPDATE :" + loc.toString();
			break;
		}
		return result;
	}
	
	public int getProtocol(){
		return this.protocol;
	}
	
	public Vector3 getLocation() {
		return this.loc;
	}
	
	public ArrayList<Player> getPlayerList() {
		return this.player_list;
	}
	
	public Ball getBall() {
		return this.ball;
	}
	
	public PhoarMatch getMatch() {
		return this.match;
	}

}
