


package atv;

import processing.core.PApplet;

public class Testis extends Scene {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		PApplet.main(new String[] { atv.Testis.class.getName() });
	}
	
	
	public Point zero = new Point(550, 300);
	public Point center = new Point();
	
	
	public void setup(){
		size(1000, 600);

		stroke(0);
		smooth();
		frameRate(10);
	}
	
	float a = 0F;
	float b = 0F;
	
	public void draw() {
		background(196);
		resetMatrix();
		
		textAlign(LEFT, TOP);
		
		a = angle(a + .01F);		
		debugs.add("" + a);
		
		b = angle(b - .02F);		
		debugs.add("" + b);
		

		translate(zero.x, zero.y);

		noFill();
		stroke(Colors.BLUE);
		
		arc(0, 0, 200, 200, a, b);			
		
		
		stroke(Colors.GREEN);
		line(0, 0, cos(a) * 100, 0);
		line(0, 0, 0, sin(a) * 100);
		line(0, 0, cos(a) * 100, sin(a) * 100);

		stroke(Colors.RED);
		line(0, 0, cos(b) * 100, 0);
		line(0, 0, 0, sin(b) * 100);
		line(0, 0, cos(b) * 100, sin(b) * 100);
		
		float c = calcAngleBetween(cos(b) * 100, -sin(b) * 100, cos(a) * 100, -sin(a) * 100);
		
		debugs.add("" + c);
		//debugs.add(Scene.degrees(radians))
		
		
		drawDebugs();
	}
	
	


}
