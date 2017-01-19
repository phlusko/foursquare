package p4u1.dd.phoarsquare;

import java.io.Serializable;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameCoord implements Serializable{
	
	private static float rotate = 0;
	
	float x, y, z;
	
	public static void rotateWorldToAngle(float angle) {
		rotate = angle;
	}
	public GameCoord() {
		// TODO Auto-generated constructor stub
		x = y = z = 0;
		
	}
	
	public GameCoord(float arg0, float arg1, float arg2) {
		this();
		x = arg0;
		y = arg1;
		z = arg2;
	}
	
	public GameCoord(Vector3 arg0) {
		this(arg0.x, arg0.y, arg0.z);
	}
	
	public void setVector3(Vector3 arg0) {
		x = arg0.x;
		y = arg0.y;
		z = arg0.z;
	}
	
	public void add(Vector3 arg0) {
		x += arg0.x;
		y += arg0.y;
		z += arg0.z;
	}
	
	public Vector3 getVector3() {
		return new Vector3(x,y,z);
	}
	
	public Vector2 getScreenLocation(){
		/*
		int rx, ry;
		float w2 = 800 / 2;
		float h2 =480 / 2;
		float sx = 1 / 2f; 
		float sy = 1 / 5.2f;
		float sz = 1 / 2.5f;
		Vector3 curr = this.getVector3();
		curr = GameCoord.rotate(curr, rotate);
		rx = (int)(w2 + ((curr.x) * sx) + ((curr.y * -1) * sx));
		ry = (int)(h2 + ((curr.x) * sy)  + (-1 * (curr.y * -1) * sy)) + (int)(curr.z*sz) - 100;
		*/
		return GameCoord.convertToPixel(this.getVector3());
	}


	public static Vector2 convertToPixel(Vector3 arg0) {
		int rx, ry;
		float w2 = 800 / 2;
		float h2 =480 / 2;
		float sx = 1 / 2f; 
		float sy = 1 / 5.2f;
		float sz = 1 / 2.5f;
		arg0 = GameCoord.rotate(arg0, rotate);
		rx = (int)(w2 + ((arg0.x) * sx) + ((arg0.y * -1) * sx));
		ry = (int)(h2 + ((arg0.x) * sy)  + (-1 * (arg0.y * -1) * sy)) + (int)(arg0.z*sz) - 100;
		return new Vector2(rx,ry);
	}
	
	public GameCoord getFloor() {
		return new GameCoord(this.x, this.y, 0);
	}
	
	public static Vector3 rotate(Vector3 arg0, float angle) {
		float theta;
		theta = (float)Math.toRadians(angle);
		//p.p("theta: " + theta);
		float old_x = arg0.x;
		arg0.x = (arg0.x * (float)Math.cos(theta)) - (arg0.y * (float)Math.sin(theta));
		arg0.y = (old_x * (float)Math.sin(theta)) + (arg0.y * (float)Math.cos(theta));
	
		return new Vector3(arg0.x, arg0.y, arg0.z);
	}

}
