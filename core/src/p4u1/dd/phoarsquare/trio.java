package p4u1.dd.phoarsquare;

public class trio {
	
	float x =0;
	float y = 0;
	float z = 0;
	
	public trio() {
		// TODO Auto-generated constructor stub
	}
	
	public trio(float arg0, float arg1, float arg2) {
		x = arg0;
		y = arg1;
		z = arg2;
	}
	
	public void rotate (float a) {
		float theta;
		theta = a;
		//p.p("theta: " + theta);
		float old_x = this.x;
		this.x = (this.x * (float)Math.cos(theta)) - (this.y * (float)Math.sin(theta));
		this.y = (old_x * (float)Math.sin(theta)) + (this.y * (float)Math.cos(theta));
	}
	
	public trio copy() {
		return new trio (x,y,z);
	}
	

}
