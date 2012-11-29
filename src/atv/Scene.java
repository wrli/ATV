package atv;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public abstract class Scene extends PApplet {

	private static final long serialVersionUID = 1L;

	void clear() {
		background(196);
		resetMatrix();
	}

	void drawFps() {
		pushStyle();
		textAlign(RIGHT, BOTTOM);
		text("" + (round(frameRate * 10) / 10.0) + " FPS", width, height);
		popStyle();
	}
	

	public List<String> debugs = new ArrayList<String>();
	public void drawDebugs() {
		pushMatrix();
		pushStyle();
		
		resetMatrix();
		stroke(GRAY);
		
		textAlign(LEFT, BOTTOM);
		int i = 0; for (String debug : debugs) text(debug, 0, i+=15);
		debugs.clear();

		popMatrix();
		popStyle();
	}

	public float calcAngleBetween(Point p1, Point p2) {
		return calcAngleBetween(p1.x, p1.y, p2.x, p2.y);
	}
	
	public float calcAngleBetween(float x1, float y1, float x2, float y2) {
		//pushStyle();
		//stroke(Colors.RED);
		//line(x1, -y1, x2, -y2);
		
		float x = x2 - x1;    				//debugs.add("x1: " + x1 + "\t, x2: " + x2 + "\t, x: " + x);
		float y = y2 - y1;					//debugs.add("y1: " + y1 + "\t, y2: " + y2 + "\t, y: " + y);
		float d = dist(x1, y1, x2, y2);		//debugs.add("d: " + d);
		
		if (d == 0)
			return 0;

		float s = asin(y / d); 				//debugs.add("asin: " + s);
		float c = acos(x / d); 				//debugs.add("acos: " + c);
		
		if (s > 0) c = -c;
		
		float r = c;
		
		//arc(0, 0, 190, 190, Math.min(0, r), Math.max(0, r));
		

		//popStyle();

		return r;

	}

	public static float angle(float angle) {
		angle %= 2*PI;
		if (angle < -PI) return angle + 2*PI;
		if (angle >  PI) return angle - 2*PI;
		return angle;
	}
	
	public static float anglePoz(float angle) {
		angle %= 2*PI;
		if (angle < 0) return angle + 2*PI;
		return angle;
	}

	@Override
	public void arc(float a, float b, float c, float d, float start, float stop) {
		//debugs.add("start: " + start);
		//debugs.add("stop:  " + stop);
		
		if (start > stop) {
			stop += 2*PI;			
			//debugs.add("stop:  " + stop);
		}
		super.arc(a, b, c, d, start, stop);
	}
	
	public float signum(float f) {
		return Math.signum(f);
	}
	
	public double signum(double d) {
		return Math.signum(d);
	}
}
