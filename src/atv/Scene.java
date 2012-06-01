package atv;

import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;

public abstract class Scene extends PApplet {

	private static final long serialVersionUID = 1L;

	public List<String> debugs = new LinkedList<String>();

	public float calcAngleBetween(Point p1, Point p2) {
		return calcAngleBetween(p1.x, p1.y, p2.x, p2.y);
	}
	
	public float calcAngleBetween(float x1, float y1, float x2, float y2) {
		float x = x2 - x1;    				//debugs.add("x1: " + x1 + ", x2: " + x2 + ", x: " + x);
		float y = y2 - y1;					//debugs.add("y1: " + y1 + ", y2: " + y2 + ", y: " + y);
		float d = dist(x1, y1, x2, y2);		//debugs.add("d: " + d);
		
		if (d == 0)
			return 0;

		float s = asin(x / d); //debugs.add("asin: " + degrees(s));
		float c = acos(y / d); //debugs.add("acos: " + degrees(c));

		if (s < 0)
			c = -c;
		
		//debugs.add("angle: " + degrees(c));
		return c;
	}

	public static float angle(float angle) {
		angle %= 2*PI;
		if (angle < -PI) return angle + 2*PI;
		if (angle >  PI) return angle - 2*PI;
		return angle;
	}
}
