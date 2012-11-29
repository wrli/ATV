


package atv;

import processing.core.PApplet;

public class TestInput extends Scene {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		PApplet.main(new String[] { atv.TestInput.class.getName() });
	}
	
	
	public Point zero = new Point(550, 300);
	public Point center = new Point();
	
	
	public void setup(){
		size(1000, 600);

		stroke(0);
		smooth();
		frameRate(10);
		
		noLoop();
	}
	
	float a = 0F;
	float b = 0F;
	
	public void draw() {
		background(196);
		resetMatrix();
		
		textAlign(LEFT, TOP);
		
		translate(zero.x, zero.y);

		noFill();
		stroke(Colors.BLUE);
		
		drawDebugs();
	}
	
	public void keyPressed() {
		if (key == CODED) {
			debugs.add("keyCode: " + keyCode);
		} else {
			debugs.add("key: " + key);
		}
		
		redraw();
	}


}
