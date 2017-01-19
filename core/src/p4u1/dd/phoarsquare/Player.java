package p4u1.dd.phoarsquare;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;


public class Player implements GestureListener, Serializable, GameDrawable{
	private static int SERVING = 1;
	
	int state = 0;
	String name;
	boolean isHuman = false;
	boolean flung = false;
	int ai_serve_delay = 0;
	int serve_delay_count = 0 ;
	int serves = 0;
	int model = 2;
	int points = 0;
	int lives = 3;
	Vector2 fling;
	boolean isMale = true;
	volatile boolean moving;
	static Walker walker;

	
	public Player(String arg0) {
		name = arg0;
		if (walker == null) {
			walker = new Walker();
		}
	}
	
	public Player(String arg0, int arg1, boolean arg2) {
		this(arg0);
		model = arg1;
		isMale = arg2;
        moving = false;
	}
	
	GameCoord loc;
	
	public void setOrder(int arg0) {
		order = arg0;
	}
	
	public void makeHuman() {
		isHuman = true;
	}
	
	public Ball serve() {
		this.moveTo(new Vector3(-380f, 380f,0f));
		state = Player.SERVING;
		ai_serve_delay = (int)(Math.random() * 100) + 50;
		serve_delay_count = 0;
		Ball my_ball = new Ball();
		my_ball.loc.setVector3(new Vector3(-380,380,30));
		
		return my_ball;
	}
	
	boolean time_to_hit = false;
	long hit_time_start;
	int hit_delay;
	
	public boolean update(Ball ball) {
		boolean served = false;
		if (state == Player.SERVING){
			this.moveTo(new Vector3(-380f, 380f,0f));
			if (isHuman && this.flung){
				this.points += 500;
				flung = false;
				Vector3 tangle = this.loc.getVector3();
				Vector2 angle = new Vector2(fling.x, fling.y);
				boolean downer = false;
				int power_d = 1;
				int up_d = 0;
				if (fling.y > 0) {
					power_d = 3;
					up_d = 15;
					angle.y *= -1;
					angle.x *= -1;
				}
				angle.x *= -1f;
				angle.nor();
				tangle.x *= -1;
				tangle.y *= -1;
				tangle = tangle.nor();
				tangle.scl(20*power_d);
				Vector3 power = new Vector3(0, 0, 15-up_d);
				tangle = tangle.add(power);
				tangle = tangle.rotate( (float)angle.angle() + 90, 0, 0, 1);
				//System.out.println(tangle.x + " " + tangle.y);
				this.serves++;
				served = true;
				ball.Hit(tangle, this.order);
				this.state = 0;
			}
			serve_delay_count++;
			if (serve_delay_count > this.ai_serve_delay && !isHuman){
				Vector3 angle = new Vector3(1,-1,0);
				angle = angle.nor();
				angle.scl(20);
				Vector3 power = new Vector3(0, 0, 15);
				this.serves++;
				served = true;
				ball.Hit(angle.add(power).rotate(Vector3.Z, (float)(Math.random()-0.5) * 45), this.order);
				this.state = 0;
			}
		} else if (order < 4) {
			if ((inMySquare(ball) || ball.last_bounced == order) && order != ball.last_hit && ball.alive) {
				moveTo(new Vector3(ball.loc.x, ball.loc.y, 0));
				
				if (ball.loc.z > 30f && ball.bounce_count > 0 && ball.last_bounced == this.order && !isHuman) {
					
					if (this.time_to_hit == false) {
						this.time_to_hit = true;
						this.hit_time_start = System.currentTimeMillis();
						this.hit_delay = (int)(Math.random() * 400) + 50;
					}
					if (System.currentTimeMillis() - this.hit_time_start > this.hit_delay) {
						Vector3 tangle = this.loc.getVector3();
						tangle.x *= -1;
						tangle.y *= -1;
						tangle = tangle.nor();
						tangle.scl(5);
						Vector3 power = new Vector3(0, 0, 35 + (float)(Math.random() - 0.5) * 10);
						tangle = tangle.add(power);
						tangle = tangle.rotate( (float)(Math.random() - 0.5) * 90, 0, 0, 1);
						//System.out.println(tangle.x + " " + tangle.y);
						ball.Hit(tangle, this.order);
						this.time_to_hit = false;
					}
				}
				
				if (isHuman && this.flung){
					this.points += 200;
					flung = false;
					Vector3 tangle = this.loc.getVector3();
					Vector2 angle = new Vector2(fling.x, fling.y);
					angle.x *= -1f;
					angle.nor();
					boolean downer = false;
					float power_d = 0.75f;
					int up_d = 0;
					if (fling.y > 0) {
						downer = true;
						power_d = 4;
						up_d = 33;
						angle.y *= -1;
						angle.x *= -1;
					}
					tangle.x *= -1;
					tangle.y *= -1;
					tangle = tangle.nor();
					tangle.scl(8*power_d);
					Vector3 power = new Vector3(0, 0, 35-up_d);
					tangle = tangle.add(power);
					tangle = tangle.rotate( (float)angle.angle() + 90, 0, 0, 1);
					//System.out.println(tangle.x + " " + tangle.y);
					ball.Hit(tangle, this.order);
				}
				
			} else {
				flung = false;
				moveTo(new Vector3(getMyQuadCenter().x, getMyQuadCenter().y, 0));
			}
		}
		return served;
	}
	
	int direction = 0;
	
	public void setDirection(Vector3 arg0) {
		if (Math.abs(arg0.x) > Math.abs(arg0.y)){
			if (arg0.x > 0)  {
				direction = 2;			
			} else {
				direction = 1;
			}
		} else {
			direction = 0;
		}
	}

	long last_time = System.currentTimeMillis();
	int frame = 0;
	
	public void moveTo(Vector3 arg0) {
		long curr_time = System.currentTimeMillis();
		if (moving) {
			if (curr_time - last_time > 75) {
				frame++;
				last_time = curr_time;
				if (frame == 4) {
					frame = 0;
				}
			}
		} else {
			frame = 0;
		}
		Vector3 delta = new Vector3();
		delta = arg0.cpy().sub(this.loc.getVector3());
		delta = delta.nor();
		Vector3 new_loc = this.loc.getVector3().cpy();
		new_loc.add(delta);
		Vector2 new_pixel = GameCoord.convertToPixel(new_loc);
		Vector2 pixel_delta = new_pixel.sub(GameCoord.convertToPixel(this.loc.getVector3()));
		setDirection(delta);
		if (Vector3.dst(arg0.x, arg0.y, arg0.z, loc.x, loc.y, loc.z) < 3f) {
			loc.setVector3(new Vector3(arg0.x, arg0.y, arg0.z));
			this.moving = false;
		}else {
			//System.out.println(Vector3.dst(arg0.x, arg0.y, arg0.z, loc.x, loc.y, loc.z) );
			loc.setVector3(loc.getVector3().add(delta.scl(3f)));
			moving = true;
		}
	}
	
	public Vector2 getMyQuadCenter() {
		Vector2 center = new Vector2();
		switch (order) {
			case 0:
				center.x = -175f;
				center.y = 175f;
				break;
			case 1:
				center.x = -175f;
				center.y = -175f;
				break;
			case 2:
				center.x = 175f;
				center.y = -175f;
				break;
			default:
				center.x = 175f;
				center.y = 175f;
		}
		return center;
	}
	
	public boolean inMySquare(Ball ball) {
		int x_flag, y_flag;
		switch(order) {
		case 0:
			x_flag = -1;
			y_flag = 1;
			break;
		case 1:
			x_flag = -1;
			y_flag = -1;
			break;
		case 2:
			x_flag = 1;
			y_flag = -1;
			break;
		default:
			x_flag = 1;
			y_flag = 1;
			break;
		}
		return ((x_flag * ball.loc.x) > 0 && (y_flag * ball.loc.y) > 0);
	}
	
	public static int whosQuadYouIn(Ball ball) {
		if(ball.loc.x < 0 && ball.loc.y > 0) {
			return 0;
		}
		
		if(ball.loc.x < 0 && ball.loc.y < 0) {
			return 1;
		}
		
		if (ball.loc.x > 0 && ball.loc.y < 0) {
			return 2;
		}
		
		return 3;
	
	}
	
	private Vector3 getServeVector() {
		Vector3 angle = new Vector3(1,-1,0);
		angle = angle.nor();
		angle.scl(20);
		Vector3 power = new Vector3(0, 0, 15);
		
		return angle.add(power).rotate(Vector3.Z, (float)(Math.random()-0.5) * 45);
	}
	
	int order = 0;

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
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
		this.flung = true;
		this.fling = new Vector2(velocityX, velocityY);
		//System.out.println(velocityX + " " + velocityY);
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
	public void drawMe(SpriteBatch arg0) {
		walker.drawMe(arg0, this);
	}

	@Override
	public GameCoord getLoc() {
		return loc;
	}

	public static void loadWalker() {
		if (walker == null) {
			walker = new Walker();
		}
	}

	public static void setWalker(Walker arg0) {
		walker = arg0;
	}
}
