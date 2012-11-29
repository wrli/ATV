package atv;

import processing.core.PApplet;

public class Test3D extends Scene {

	private static final long serialVersionUID = 1L;

	public Point zero = new Point(500, 300, 300);
	public Point center = new Point();
	
	private Point mousep = new Point();

	float angle = 0F;

	
	public static void main(String[] args) {
		PApplet.main(new String[] { atv.Test3D.class.getName() });
	}

	
	public void setup() {		
		size(1000, 600, P3D);

		stroke(0);
		frameRate(30);

		//noLoop();
	}

	float rx, ry, rz;
	
	public void draw() {
		clear();
		drawFps();
		
		stroke(0xFF666666);
		
		drawDebugs();
		
		//drawCtrl();

		translate(zero.x, zero.y, zero.z);
		
		rotateY(ry += PI / 600);
		
		//drawAxes();
		
		noFill();

		box(100);
		
		rotateZ(rz += PI / 300);
		cylinder(0, 0, 0, 80, 30);
		
		
		debugs.add("mousep: " + mousep);
		debugs.add("center: " + center);
		angle = calcAngleBetween(new Point(0, 0), center);
		
		debugs.add(String.valueOf(angle));
		
		drawDebug();

		//drawWheels();

	}
	
	
	void cylinder(int x, int y, int z, int d, int l) {
		pushMatrix();
		pushStyle();
		
		translate(x, y, z);

		stroke(0xFF330000);
		box(10);
		
		fill(0xFF996633);
		//smooth();
		noSmooth();
		noStroke();
		beginShape(QUADS);
		for (int i = 0; i < 36; i++) {
			float a = TWO_PI / 36 * i;
			vertex(sin(a) * d/2, cos(a) * d/2, i % 2 == 0 ? l/2 : -l/2);
			vertex(sin(a) * d/2, cos(a) * d/2, i % 2 == 0 ? -l/2 : l/2);
		}
		endShape();
		fill(0xFFDD9966);
		beginShape(QUADS);
		for (int i = 0; i < 36; i++) {
			float a = TWO_PI / 36 * (i+1);
			vertex(sin(a) * d/2, cos(a) * d/2, i % 2 == 0 ? l/2 : -l/2);
			vertex(sin(a) * d/2, cos(a) * d/2, i % 2 == 0 ? -l/2 : l/2);
		}
		endShape();

		smooth();
		noFill();
		strokeWeight(3F);
		stroke(0xFF996633);
		
		translate(0, 0, l/2);
		ellipse(0, 0, d-1, d-1);
		translate(0, 0, -l);
		ellipse(0, 0, d-1, d-1);

		/*
		translate(0, 0, l/2);
		noSmooth();
		noStroke();
		fill(0xFF00FF00);
		ellipse(0, 0, d, d);
		smooth();
		stroke(0xFF00DD00);
		noFill();
		ellipse(0, 0, d, d);

		translate(0, 0, -l);
		noSmooth();
		noStroke();
		fill(0xFF0000FF);
		ellipse(0, 0, d, d);
		smooth();
		stroke(0xFF0000DD);
		noFill();
		ellipse(0, 0, d, d);
		*/
		popStyle();
		popMatrix();
	}
	
	
	@Override
	public void keyPressed() {
		debugs.add("" + keyCode);
		switch (keyCode) {
		case Keys.LEFT:
			zero.x -= 10;
			break;
		case Keys.RIGHT:
			zero.x += 10;
			break;
		case Keys.UP:
			zero.y += 10;
			break;
		case Keys.DOWN:
			zero.y -= 10;
			break;
		case Keys.CW:
			zero.z += 10;
			break;
		case Keys.CCW:
			zero.z -= 10;
			break;
		}
		debugs.add(zero.toString());
	}


	
	void drawDebug() {
		pushStyle();

		stroke(Colors.RED);
		strokeWeight(.5F);

		line(0, 0, 0, center.x, -center.y, center.z);

		//arc(0, 0, 50, 50, -PI/4, 0);

		popStyle();
	}


	void drawAxes() {
		pushStyle();
		stroke(0xFF666666);
		line(0, -height/2, 0, height/2);
		line(-width/2, 0, width/2, 0);
		popStyle();
	}

	void clear() {
		background(196);
		//resetMatrix();
	}

	void drawFps() {
		pushStyle();
		textAlign(RIGHT, BOTTOM);
		stroke(0xFF000000);
		text("" + (round(frameRate * 10) / 10.0) + " FPS", width, height);
		popStyle();
	}

	
}
