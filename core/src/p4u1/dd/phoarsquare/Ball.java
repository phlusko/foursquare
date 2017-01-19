package p4u1.dd.phoarsquare;

import java.io.Serializable;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

public class Ball implements Serializable, GameDrawable{
	
	GameCoord loc;
	
	Vector3 v;
	boolean alive;
	int age;
	
	int last_hit = 0;
	int last_bounced = 0;
	int bounce_count = 0;
	int loser = 0;
	boolean serving = false;
	ShapeRenderer shape;
	OrthographicCamera camera;
	
	public Ball() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		shape = new ShapeRenderer();
		serving = true;
		loc = new GameCoord();
		v = new Vector3();
		age = 0;
		alive=false;
	}
	
	public Ball(Vector3 arg0) {
		this();
		loc.setVector3(new Vector3(-375f, 375, 30f));
		v = arg0;
		alive = true;
		last_hit = 0;
	}
	
	public void Hit(Vector3 arg0, int who) {
		age = 0;
		this.alive = true;
		this.bounce_count = 0;
		this.last_hit = who;
		this.v = arg0;
		this.serving = false;
	}
	
	public boolean update() {
		age++;
		v.z -= PhoarSquareGame.gravity;
		
		loc.x += v.x;
		loc.y += v.y;
		loc.z += v.z;
		boolean bounced = false;
		if (loc.z < 10) {
			bounced = true;
			loc.z = 10;
			v.z *= -0.85;
			if (this.loc.x > 350 || this.loc.x < -350 || this.loc.y > 350 || this.loc.y < -350) {
				if (this.bounce_count > 0) {
					this.loser = this.last_bounced;
				} else {
					this.loser = this.last_hit;
				}
				this.alive = false;
				this.bounce_count = 0;
				
			} else {
				this.bounce_count++;
				if (this.bounce_count > 1) {
					this.alive = false;
					this.bounce_count = 0;
					this.loser = this.last_bounced;
				} else {
					this.last_bounced = Player.whosQuadYouIn(this);
					if (this.last_bounced == this.last_hit) {
						this.alive = false;
						this.bounce_count = 0;
						this.loser = this.last_hit;
					}
				}
			}
			
		}
		if (age > 200) {
			alive = false;
		}
		return bounced;
	}

	@Override
	public void drawMe(SpriteBatch arg0) {
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(Color.BLACK);
		shape.ellipse(GameCoord.convertToPixel(new Vector3(this.loc.x, this.loc.y, 0)).x - 15, GameCoord.convertToPixel(new Vector3(this.loc.x, this.loc.y, 0)).y + 2, 30, 4);
		shape.setColor(Color.RED);
		shape.circle(this.loc.getScreenLocation().x, this.loc.getScreenLocation().y + 20, 20);
		shape.end();
	}

	@Override
	public GameCoord getLoc() {
		return loc;
	}
}
