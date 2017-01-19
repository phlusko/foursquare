package p4u1.dd.phoarsquare;

import java.io.Serializable;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import p4u1.dd.phoarsquare.NameFactory.Name;

public class PhoarMatch implements Serializable {
	
	public static final int SERVING = 1;
	public static final int IN_PLAY = 2;
	public static final int TRANSITION = 3;
	public static final int GAME_OVER = 4;

	ArrayList<Player> players;
	Ball ball;
	Player king, queen, third, bitch;
	int human;
	ArrayList<Sound> sounds;
	
	public int status;
	
	public boolean clientMatch = false;
	
	GameClient client;
	NameFactory nameFactory;
	
	public PhoarMatch() {
		nameFactory = new NameFactory();
		status = PhoarMatch.TRANSITION;
	
		players = new ArrayList<Player>();
		players.add(new Player("Player 1", 3, false));
		players.get(0).makeHuman();
		human = 0;
		//players.add(new Player("Luna", 1, false));
		for (int i = 0; i < 5; i++){

			NameFactory.Name tempName;
			tempName = nameFactory.getRandomName();
			boolean tempMale;
			if (tempName.isMale) {
				tempMale = true;
			} else {
				tempMale = false;
			}
			//players.add(new Player(tempName.name, tempModel));
			if (tempName.name.contentEquals("Luna")) {
				players.add(new Player(tempName.name, 1, false));
			} else if (tempName.name.contentEquals("Paul")) {
				players.add(new Player(tempName.name, 0, false));
			} else if (tempName.name.contentEquals("Krystal")) {
				players.add(new Player(tempName.name, 3, false));
			} else {
				players.add(new Player(tempName.name, 5 + ((int) (Math.random() * 255)), tempMale));
			}
		}

		ball = new Ball();
		start();
	}
	
	public PhoarMatch(GameClient client) {
		/*
		clientMatch = true;
		status = client.match_status;
		players = client.players;
		if(players.size() > 0) {
			this.king = players.get(0);
			this.queen = players.get(1);
			this.third = players.get(2);
			this.bitch = players.get(3);
		}
		ball = new Ball();
		*/
	}
	
	public Player getHuman() {
		return this.players.get(this.human);
	}
	
	public String update(GameClient client) {
		return "";
		/*
		players = client.players;
		if (players.size() > 0) {
			this.king = players.get(0);
			this.queen = players.get(1);
			this.third = players.get(2);
			this.bitch = players.get(3);
		}
		ball = client.ball;
		this.status = client.match_status;
		
		return "";
		*/
	}
	long transition_start;
	boolean transition_begin = false;
	
	public void transition() {
		
		if (transition_begin == false) {
			transition_begin = true;
			transition_start = System.currentTimeMillis();
		}
		long current_time = System.currentTimeMillis();

		if (current_time - transition_start < 3000) {

			king = players.get(0);
			king.moveTo(new Vector3(-175f, 175f,0f));
			//king.setOrder(0);
			queen = players.get(1);
			queen.moveTo(new Vector3(-175f, -175f,0f));
			//queen.setOrder(1);
			third = players.get(2);
			third.moveTo(new Vector3(175f, -175f,0f));
			//third.setOrder(2);
			bitch = players.get(3);
			bitch.moveTo(new Vector3(175f, 175f,0f));
			//bitch.setOrder(3);
			for (int i = 4; i < players.size(); i++) {
				players.get(i).moveTo(new Vector3(350 + ((i-3) * 75), 350, 0));
				//players.get(i).setOrder(i);
			}
		} else {
			transition_begin = false;
			start();
		}
		for(int i = 0 ; i < players.size(); i++) {
			if (players.get(i).isHuman) {
				human = i;
			}
		}
		
	}
	
	public String update() {
		String response = "";
		if (this.status == PhoarMatch.TRANSITION) {
			transition();
		} else if (this.status != PhoarMatch.GAME_OVER) { 
			if (ball.alive) {
				//server.queueOutput(new PhoarSquareProtocol(PhoarSquareProtocol.BALL_LOC_UPDATE, ball.loc.getVector3()));
				if (ball.update()) {
					int rand = (int)(Math.random()*100) % 7;
					//sounds.get(rand).play();
				}
				//server.queueOutput(new PhoarSquareProtocol(PhoarSquareProtocol.UPDATE_BALL, ball));
			} else if(!ball.serving) {
				this.status = PhoarMatch.TRANSITION;
				response += players.get(ball.loser).name + " lost.";
				loser(ball.loser);
				//server.queueOutput(new PhoarSquareProtocol(PhoarSquareProtocol.ROUND_END));
				//this.start();
			}
			if (king.update(ball)) {
				//server.queueOutput(new PhoarSquareProtocol(PhoarSquareProtocol.ROUND_SERVED));
				this.status = PhoarMatch.IN_PLAY;
				//server.queueOutput(new PhoarSquareProtocol(PhoarSquareProtocol.UPDATE_BALL, ball));
			}
			queen.update(ball);
			third.update(ball);
			bitch.update(ball);
		}
		//server.queueOutput(new PhoarSquareProtocol(PhoarSquareProtocol.UPDATE_BALL, ball));
		//server.broadcast(new PhoarSquareProtocol(PhoarSquareProtocol.MATCH_UPDATE, this));
		return response;
	}
	
	public void loser(int arg0) {
		loser = arg0;
		if (loser == human) {
			this.getHuman().lives--;
			if (this.getHuman().lives < 1) {
				this.status = PhoarMatch.GAME_OVER;
				Preferences prefs = Gdx.app.getPreferences("data");
				int player_score = this.getHuman().points;
				int high_score = prefs.getInteger("high score");
				if (player_score > high_score) {
					prefs.putInteger("high score", player_score);
					prefs.flush();
				}
			}
		}
		if (this.status != PhoarMatch.GAME_OVER) {
			Player temp = players.remove(arg0);
			temp.setOrder(5);
			players.add(temp);
		}
		//move instead slowly rotate camera if user
		//start();
	}	
	int loser;
	
	public void start() {
		king = players.get(0);
		king.loc = new GameCoord(new Vector3(-175f, 175f,0f));
		king.setOrder(0);
		queen = players.get(1);
		queen.loc = new GameCoord(new Vector3(-175f, -175f,0f));
		queen.setOrder(1);
		third = players.get(2);
		third.loc = new GameCoord(new Vector3(175f, -175f,0f));
		third.setOrder(2);
		bitch = players.get(3);
		bitch.loc = new GameCoord(new Vector3(175f, 175f,0f));
		bitch.setOrder(3);
		for (int i = 4; i < players.size(); i++) {
			players.get(i).loc = new GameCoord(new Vector3(350 + ((i-3) * 75), 350, 0));
			players.get(i).setOrder(i);
		}
		for(int i = 0 ; i < players.size(); i++) {
			if (players.get(i).isHuman) {
				human = i;
			}
		}
		status = PhoarMatch.SERVING;
		//server.queueOutput(new PhoarSquareProtocol(PhoarSquareProtocol.ROUND_SERVING));
		ball = king.serve();
	}

	public void skip() {
		ball.loser = (int)(Math.random() * 4);
		ball.alive = false;
		loser(ball.loser);
		this.start();
	}
}
