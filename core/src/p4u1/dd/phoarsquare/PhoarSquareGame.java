package p4u1.dd.phoarsquare;


import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PhoarSquareGame implements Screen, GestureListener, TextInputListener {
	
	public static int COURT_SIZE = 350;
	public static float gravity = 0.978f;
	
	final PhoarSquare game;
	
	OrthographicCamera camera;
	private Texture texture;
	private Sprite sprite;
	ShapeRenderer shape;

	Array<String> log = new Array<String>();
	int ruler_offset;
	int camera_angle = 0;
	Texture dropImage;
	Texture landscape;
	OrthographicCamera ortho_camera;
	PhoarMatch match;
	float rotate = 0;
	boolean hasName = true;
	boolean isServer = false;
	
	Texture court_texture;
	PolygonRegion court_pol_region;
	TextureRegion court_region;
	
	Preferences prefs;
	
	Texture ground_texture;
	PolygonRegion ground_pol_region;
	TextureRegion ground_region;
	Walker walker;
	Vector2 n = new GameCoord (0,COURT_SIZE,0).getScreenLocation();
	Vector2 s = new GameCoord (0,-COURT_SIZE,0).getScreenLocation();
	Vector2 e = new GameCoord (COURT_SIZE,0,0).getScreenLocation();
	Vector2 w = new GameCoord (-COURT_SIZE,0,0).getScreenLocation();
	
	Vector2 nw = new GameCoord (-COURT_SIZE,COURT_SIZE,0).getScreenLocation();
	Vector2 ne = new GameCoord (COURT_SIZE,COURT_SIZE,0).getScreenLocation();
	Vector2 se = new GameCoord (COURT_SIZE,-COURT_SIZE,0).getScreenLocation();
	Vector2 sw = new GameCoord (-COURT_SIZE,-COURT_SIZE,0).getScreenLocation();
	
	int high_score;
	long open_time;
	FlashingFonts flasher;

	
	public PhoarSquareGame(final PhoarSquare arg0, boolean arg1) {
		Gdx.input.setCatchBackKey(true);
		this.game = arg0;
		shape = new ShapeRenderer();
		flasher = new FlashingFonts();
		court_texture = new Texture(Gdx.files.internal("data/sprites/court.png"));
		court_region = new TextureRegion(court_texture);
		ground_texture = new Texture(Gdx.files.internal("data/sprites/ground3.png"));
		ground_region = new TextureRegion(court_texture);

		walker = new Walker();
		Player.setWalker(walker);
		open_time = System.currentTimeMillis();

		ruler_offset=0;
		camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        landscape = new Texture(Gdx.files.internal("data/landscape2.png"));
                

                
        isServer = arg1;
        
        prefs = Gdx.app.getPreferences("data");
        high_score = prefs.getInteger("high score");
        //System.out.println(high_score);
        
        if (isServer) {
        	if (!hasName) {
            	//Gdx.input.getTextInput(this, "Name: ", "");
            }
        	match = new PhoarMatch();
        	//Gdx.input.setInputProcessor(new GestureDetector(match.players.get(match.human)));
        	Gdx.input.setInputProcessor(new GestureDetector(this));
        }
	}

	@Override
	public void dispose() {
		game.batch.dispose();
	}
	
	ShapeRenderer court;
	ShapeRenderer ball_renderer;
		
	public void renderCourt() {
		Color color = new Color(Color.WHITE);
		game.batch.begin();
		court_pol_region = new PolygonRegion(court_region, new float[] {
				0, 0,
				0, 256,
				256, 256,
				256, 0
				}, new short[] {0, 1, 2, 0, 2, 3});
		float[] vertices = 
			{nw.x, nw.y, color.toFloatBits(), court_region.getU(), court_region.getV(),
				ne.x, ne.y, color.toFloatBits(), court_region.getU(), court_region.getV2(),
				se.x, se.y, color.toFloatBits(), court_region.getU2(), court_region.getV2(),
				sw.x, sw.y, color.toFloatBits(), court_region.getU2(), court_region.getV()};
		game.batch.draw(court_texture, vertices, 0, vertices.length);
		//System.out.println(this.match.ball.alive);
		if (this.match.ball.alive && this.match.ball.last_bounced == 0) {
			game.small_font.setColor(Color.RED);
		}
		
		game.small_font.draw(game.batch, "K", GameCoord.convertToPixel(new Vector3(-35, 35, 10)).x, GameCoord.convertToPixel(new Vector3(-35, 35, 10)).y);
		game.small_font.setColor(Color.BLACK);
		if (this.match.ball.alive && this.match.ball.last_bounced == 1) {
			/*
			shape.begin(ShapeType.Filled);
			Color highlight = Color.RED;
			
			highlight.set(highlight.r, highlight.g, highlight.b, 70);
			shape.setColor(highlight);
			float[] highbox = {
					0, 0,
					n.x, n.y,
					nw.x, nw.y,
					w.x, w.y
			};
			shape.polygon(highbox);
			shape.end();
			*/
			game.small_font.setColor(Color.RED);
		}
    	game.small_font.draw(game.batch, "Q", GameCoord.convertToPixel(new Vector3(-35, -35, 10)).x, GameCoord.convertToPixel(new Vector3(-35, -35, 10)).y);
    	game.small_font.setColor(Color.BLACK);
    	if (this.match.ball.alive && this.match.ball.last_bounced == 2) {
    		game.small_font.setColor(Color.RED);
		}
    	game.small_font.draw(game.batch, "3", GameCoord.convertToPixel(new Vector3(35, -35, 10)).x, GameCoord.convertToPixel(new Vector3(35, -35, 10)).y);
    	game.small_font.setColor(Color.BLACK);
    	if (this.match.ball.alive && this.match.ball.last_bounced == 3) {
    		game.small_font.setColor(Color.RED);
		}
    	game.small_font.draw(game.batch, "B", GameCoord.convertToPixel(new Vector3(35, 35, 10)).x, GameCoord.convertToPixel(new Vector3(35, 35, 10)).y);
    	game.small_font.setColor(Color.BLACK);

    	
    	//game.small_font.draw(game.batch, "10000", 400, 400);
        game.batch.end();
		shape.begin(ShapeType.Line);
		shape.setProjectionMatrix(camera.combined);
		Gdx.gl20.glLineWidth(5);
		//shape.line(n, s);
		shape.setColor(Color.BLACK);
		shape.line(n.x, n.y, s.x, s.y);
		//shape.line(e, w);
		shape.line(e.x, e.y, w.x, w.y);
		shape.line(nw, ne);
		shape.line(ne, se);
		shape.line(sw, se);
		shape.line(sw, nw);
		shape.end();
		game.batch.begin();
		game.small_font.setColor(Color.BLACK);
		game.small_font.draw(game.batch, "High Score", 650, 480);
		game.small_font.draw(game.batch, high_score + "", 650, 460);
		game.small_font.draw(game.batch, "Lives: " + match.getHuman().lives, 5, 480);
		game.batch.end();


		
	}
	
	public void updateDirections() {
		n = new GameCoord (0,COURT_SIZE,0).getScreenLocation();
    	s = new GameCoord (0,-COURT_SIZE,0).getScreenLocation();
    	e = new GameCoord (COURT_SIZE,0,0).getScreenLocation();
    	w = new GameCoord (-COURT_SIZE,0,0).getScreenLocation();
    	
    	nw = new GameCoord (-COURT_SIZE,COURT_SIZE,0).getScreenLocation();
    	ne = new GameCoord (COURT_SIZE,COURT_SIZE,0).getScreenLocation();
    	se = new GameCoord (COURT_SIZE,-COURT_SIZE,0).getScreenLocation();
    	sw = new GameCoord (-COURT_SIZE,-COURT_SIZE,0).getScreenLocation();
	}

    public void render(float delta) {	
		Gdx.gl.glClearColor(255, 255, 255, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        logic();
        updateDirections();
        
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        
        //batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderCourt();
		game.batch.begin();
        int landx1, landx2;
        landx1 = (int)(((180 - rotate) / 180) * 800);
        landx2 = landx1 - 1600;
    	//game.batch.draw(landscape, landx1, -1568);
        //game.batch.draw(landscape, landx2, -1568);
    	
    	String high_score = "Turns at King:";
    	for (int i = 0; i < match.players.size(); i++) {
    		high_score += " " + match.players.get(i).name + " " + match.players.get(i).serves;	
    	}
    	
    	String score = match.getHuman().points + "";
    	game.med_tit.setColor(Color.BLACK);
    	int spacer = 0;
    	int tenPower = 1;
    	while ((Math.pow(10, tenPower) - 1) < match.getHuman().points) {
    		spacer+=25;
    		tenPower++;
    	}
    	game.med_tit.draw(game.batch, score, 375 - spacer, 470);
    	
    	//game.small_font.draw(game.batch, high_score, 0, 20);
    	game.small_font.setColor(Color.BLACK);
    	game.small_font.draw(game.batch, match.king.name, match.king.loc.getScreenLocation().x, match.king.loc.getScreenLocation().y);
    	game.small_font.draw(game.batch, match.queen.name, match.queen.loc.getScreenLocation().x, match.queen.loc.getScreenLocation().y);
    	game.small_font.draw(game.batch, match.third.name, match.third.loc.getScreenLocation().x, match.third.loc.getScreenLocation().y);
    	game.small_font.draw(game.batch, match.bitch.name, match.bitch.loc.getScreenLocation().x, match.bitch.loc.getScreenLocation().y);
    	game.batch.end();

		ArrayList<GameDrawable> drawables = new ArrayList<GameDrawable>();
		drawables.addAll(match.players);
		drawables.add(match.ball);
		ArrayList<GameDrawable> orderedDrawables = placeInOrder(drawables);
		for (Iterator<GameDrawable> iter = orderedDrawables.iterator(); iter.hasNext(); ) {
    		GameDrawable curr = iter.next();
			curr.drawMe(game.batch);
		}
		if (match.status == PhoarMatch.GAME_OVER) {
			game.batch.begin();
			game.med_tit.draw(game.batch, "GAME OVER", 150, 300);
			game.med_tit.setColor(Color.BLACK);
			//game.med_tit.draw(game.batch, "play again", 150, 151);
			game.med_tit.setColor(flasher.getCurrentColor());
			game.med_tit.draw(game.batch, "play again", 163, 150);
			game.batch.end();
		}
		if (match.getHuman().order > 3) {
			game.batch.begin();
			game.med_tit.setColor(flasher.getCurrentColor());
			game.med_tit.draw(game.batch, "skip", 303, 150);
			game.batch.end();
		}

	}
    
    public ArrayList<GameDrawable> placeInOrder(ArrayList<GameDrawable> arg0) {
    	ArrayList<GameDrawable> temp = (ArrayList<GameDrawable>)arg0.clone();
    	if (arg0.size() == 0 || arg0.size() == 1) {
    		return arg0;
    	}
    	//System.out.println(arg0.size());
    	GameDrawable pivot = temp.remove(0);
    	GameCoord pivotLoc = pivot.getLoc();
    	int pivot_y = (int) new GameCoord(new Vector3(pivotLoc.x, pivotLoc.y, 0)).getScreenLocation().y;
    	ArrayList<GameDrawable> less = new ArrayList<GameDrawable>();
    	ArrayList<GameDrawable> more = new ArrayList<GameDrawable>();
    	for (Iterator<GameDrawable> iter = temp.iterator(); iter.hasNext(); ) {
    		GameDrawable curr = iter.next();
			GameCoord currLoc = curr.getLoc();
    		int curr_y = (int) new GameCoord(new Vector3(currLoc.x, currLoc.y, 0)).getScreenLocation().y;
    		if (curr_y > pivot_y) {
    			less.add(curr);
    		} else {
    			more.add(curr);
    		}
		}
    	ArrayList<GameDrawable> result = placeInOrder(less);
    	result.add(pivot);
    	result.addAll(placeInOrder(more));
    	return result;
    }
	
	public void logic(){
		/*if (!isServer) {
			if(!gameClient.connected) {
				return;
			} else {
				if (gameClient.startMe) {
					this.match = new PhoarMatch(gameClient);
				}
				if (gameClient.hasMatch) {
					this.match = gameClient.match;
				}
			}
		} */
		ruler_offset++;
		if (ruler_offset > 10) {
			ruler_offset = 0;
		}
		if (match.human > 3) {
			if (match.human == 4 && match.status == PhoarMatch.TRANSITION) {
				if (rotate > 181) {
					rotate -= .4;
				} else if (rotate < 180){
					rotate += .4;
				}
			} else {
				rotate += .2f;
				if (rotate >359) {
					rotate = 0;
				}
			}
		} else if (match.status == PhoarMatch.TRANSITION && match.human > match.loser) {
			
				rotate += .4;
			
				
		} else {
			switch (match.human) {
			case 1:
				rotate = 0;
				break;
			case 0:
				rotate = 90;
				break;
			case 2:
				rotate = 270;
				break;
			default:
				rotate = 180;
			}
		}
		String for_log;
		if (isServer) {
			match.update();
		//	for_log = match.update(gameServer);
			//gameServer.broadcast(new PhoarSquareProtocol(PhoarSquareProtocol.MATCH_UPDATE, match));
			//gameServer.broadcast(new PhoarSquareProtocol(PhoarSquareProtocol.UPDATE_BALL, match.ball));
		} else {
		//	for_log = match.update(gameClient);
		}
	/*	if (for_log.length() > 0 ) {
			log.add(for_log);
		}
		if(log.size>20) {
			log.removeIndex(0);
		}
		*/
		if (Gdx.input.isTouched()) {
		
		}
		GameCoord.rotateWorldToAngle(rotate);
		if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
			game.setScreen(new SplashScreen(game));
		}
		/*
		camera_angle += 2;
		camera.position.set((float)Math.cos(Math.toRadians(camera_angle)) * 10, 10f, (float)Math.sin(Math.toRadians(camera_angle)) * 10);
        camera.lookAt(0,0,0);
        camera.normalizeUp();
        camera.update();
        */
		//camera.rotateAround(new Vector3(0f, 0f, 0f), new Vector3(0f, 0f, 1f), 1);
		//camera.update();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		//System.out.println("touch down");
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		//System.out.println("tap");
		if (match.status == PhoarMatch.GAME_OVER) {
			high_score = prefs.getInteger("high score");
			match = new PhoarMatch();
		}
		if (match.getHuman().order > 3) {
			match.skip();
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		long current_time = System.currentTimeMillis();
		log.add(velocityX + " , " + velocityY);
		if (current_time - open_time > 500) {
			match.getHuman().fling(velocityX, velocityY, button);
		}
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pinchStop() {
	}

	@Override
	public void input(String text) {
		// TODO Auto-generated method stub
		if (text.length() > 0) {
			for (int i = 0; i < match.players.size(); i++) {
				if (text.equals(match.players.get(i).name)) {
					match.players.get(i).name = "Wade";
				} else {
					//System.out.println(text + " " + match.players.get(i).name);
				}
			}
			hasName = true;
			match.players.get(match.human).name = text;
		} else {
			hasName = false;
		}
	}

	@Override
	public void canceled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
