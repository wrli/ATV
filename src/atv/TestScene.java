package atv;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;

public class TestScene extends Scene {

	private static final long serialVersionUID = 1L;

	public static void main(String _args[]) {
		PApplet.main(new String[] { atv.TestScene.class.getName() });
	}

	public Point zero = new Point(550, 300);
	public Point center = new Point();
	
	private Point mousep = new Point();

	List<Wheel> wheels = new LinkedList<Wheel>();
	
	float ctrlX, ctrlY, ctrlZ;
	final static float CTRL_STEP_XY = .01F; 
	final static float CTRL_STEP_Z = .05F; 
	final static int CIRCLE_RADIUS = 200;
	final static float DEAD_BAND_XY = .03F;
	final static float DEAD_BAND_SPEED = .01F;

	float angle = 0F;
	
	
	private void createSquareBody(int countX, int countY, int width, int height) {
		for (int x = 0; x < countX; x++) {
			for (int y = 0; y < countY; y++) {
				if (x == 0 || x == countX-1 || y == 0 || y == countY-1) 
					wheels.add(new Wheel(this, new Point(-width/2 + (int)round(width/(countX-1)) * x, -height/2 + (int)round(height/(countY-1)) * y)));
			}
		}
		Collections.sort(wheels, new Comparator<Wheel>() {
			@Override
			public int compare(Wheel o1, Wheel o2) {
				float a1 = calcAngleBetween(new Point(), o1.getAxis());
				float a2 = calcAngleBetween(new Point(), o2.getAxis());
				if (a1 > a2) return 1;
				if (a1 < a2) return -1;
				return 0;
			}
		});
	}

	public void setup() {		
		//createRoundBody(6, 80, 80);
//		createSquareBody(2, 3, 141, 200);
		createSquareBody(2, 2, 141, 141);
		
		size(1000, 600);

		stroke(0);
		smooth();
		frameRate(20);

		//noLoop();
	}

	private volatile boolean drawing = false;

	public void draw() {
		drawing = true;

		clear();
		drawFps();
		
		
		drawDebugs();

		drawCtrl();

		translate(zero.x, zero.y);
		
		debugs.add("ctrl: " + ctrlX + ", " + ctrlY + ", " + ctrlZ);
		debugs.add("mousep: " + mousep);
		debugs.add("center: " + center);
		angle = calcAngleBetween(0, 0, ctrlX, ctrlY);
		
		debugs.add(String.valueOf(angle));
		
		drawAxes();

		drawDebug();
		
		drawTrails();

		drawWheels();

		drawing = false;
	}

	@Override
	public void mouseMoved() {
		if (drawing)
			return;

		mousep = new Point(mouseX-zero.x, -mouseY+zero.y);
		
		if (dist(mousep.x, mousep.y, 0, 0) <= CIRCLE_RADIUS) {
			ctrlX = mousep.x / (float)CIRCLE_RADIUS;
			ctrlY = mousep.y / (float)CIRCLE_RADIUS;
		}
	}
	
	@Override
	public void keyPressed() {
		debugs.add("" + keyCode);
		float other;
		switch (keyCode) {
		case Keys.LEFT:
			ctrlX = max(-1F, ctrlX - CTRL_STEP_XY);
			other = (float) sqrt(1F - ctrlX * ctrlX);
			ctrlY = max(-other, min(other, ctrlY));
			break;
		case Keys.RIGHT:
			ctrlX = min(1F, ctrlX + CTRL_STEP_XY);
			other = (float) sqrt(1F - ctrlX * ctrlX);
			ctrlY = max(-other, min(other, ctrlY));
			break;
		case Keys.DOWN:
			ctrlY = max(-1F, ctrlY - CTRL_STEP_XY);
			other = (float) sqrt(1F - ctrlY * ctrlY);
			ctrlX = max(-other, min(other, ctrlX));
			break;
		case Keys.UP:
			ctrlY = min(1F, ctrlY + CTRL_STEP_XY);
			other = (float) sqrt(1F - ctrlY * ctrlY);
			ctrlX = max(-other, min(other, ctrlX));
			break;
		case Keys.CW:
			ctrlZ = min(HALF_PI, ctrlZ + CTRL_STEP_Z);
			break;
		case Keys.CCW:
			ctrlZ = max(-HALF_PI, ctrlZ - CTRL_STEP_Z);
			break;
		case Keys.RESET:
			ctrlX = ctrlY = ctrlZ = 0;
			break;
		}
		
		if (abs(ctrlX) < CTRL_STEP_XY) ctrlX = 0;
		if (abs(ctrlY) < CTRL_STEP_XY) ctrlY = 0;
		if (abs(ctrlZ) < CTRL_STEP_Z) ctrlZ = 0;

//		center = new Point((int)(CIRCLE_RADIUS * ctrlX), (int)(CIRCLE_RADIUS * ctrlY));
		
		redraw();
	}

	void drawCtrl() {
		int r = 50;
		
		pushStyle();
		pushMatrix();
		
		noFill();
		strokeWeight(.8F);
		stroke(0xFF999999);
		
		translate(width-r-10, r+10);
		
		ellipse(0, 0, 2*r, 2*r);
		
		stroke(0xFF333333);
		strokeWeight(2F);
		fill(0xFFAAAAAA);
		
		translate(r * ctrlX, r * -ctrlY);
		
		rotate(ctrlZ);
		
		ellipse(0, 0, 10, 20);
		
		popMatrix();
		popStyle();
	}


	void drawDebug() {
		pushStyle();

		stroke(Colors.BLUE);
		strokeWeight(.5F);
		noFill();

		line(0, 0, ctrlX * CIRCLE_RADIUS, -ctrlY * CIRCLE_RADIUS);
		
		ellipse(0, 0, 2 * CIRCLE_RADIUS * DEAD_BAND_XY, 2 * CIRCLE_RADIUS * DEAD_BAND_XY);
		ellipse(0, 0, 2 * CIRCLE_RADIUS, 2 * CIRCLE_RADIUS);

		//arc(0, 0, 50, 50, -PI/4, 0);
		
		if (ctrlZ != 0) {
			if (ctrlY > 0)
				stroke(Colors.GREEN);
			else
				stroke(Colors.RED);
			line(0, 0, center.x, -center.y);
		}

		popStyle();
	}

	void drawWheels() {
		float speed = dist(0, 0, ctrlX, ctrlY);
		Float angle = calcAngleBetween(0, 0, ctrlX, ctrlY);
		
		if (speed < DEAD_BAND_SPEED) {
			for (Wheel wheel : wheels) {
				wheel.draw(null, 0);
			}
		} else
		if (ctrlZ == 0) {
			for (Wheel wheel : wheels) {
				wheel.draw(angle, speed);
			}
		} else {	
			angle = angle + HALF_PI * signum(ctrlZ) * signum(ctrlY);
			
			float r = 1 / abs(pow(ctrlZ, 5));
			float cR = r * CIRCLE_RADIUS;
			center.x = (int) (cos(angle) * cR);
			center.y = (int) (-sin(angle) * cR);
			
			for (Wheel wheel : wheels) {
				float a = calcAngleBetween(wheel.getAxis(), center) - HALF_PI * signum(ctrlZ) * signum(ctrlY);
				float s = speed * dist(wheel.getAxis().x, wheel.getAxis().y, center.x, center.y) / cR;
				wheel.draw(a, s);
			}
		}
	}
	
	void drawTrails() {
		pushStyle();
		noFill();
		stroke(Colors.METAL);
		strokeWeight(.1F);
		for (Wheel wheel : wheels) {
			pushMatrix();
			translate(wheel.getAxis().x, wheel.getAxis().y);
			float x = 0F, y = 0F;
			beginShape();
			curveVertex(0, 0);
			for (int i = 0; i < Wheel.TRAIL_POINTS; i++) {
				curveVertex(x -= wheel.trail[(wheel.trailLast - i) % Wheel.TRAIL_POINTS].x, y -= wheel.trail[(wheel.trailLast - i) % Wheel.TRAIL_POINTS].y);
			}
			endShape();
			popMatrix();
		}
		
		float[] x = new float[wheels.size()];
		float[] y = new float[wheels.size()];
		for (int i = 0; i < wheels.size(); i++) {
			Wheel wheel = wheels.get(i);
			x[i] = wheel.getAxis().x;
			y[i] = wheel.getAxis().y;
		}
		
		strokeWeight(.1F);
		
//		for (int p = 0; p < Wheel.TRAIL_POINTS; p++) {
//			beginShape();
//			for (int w = 0; w < wheels.size(); w++) {
//				Wheel wheel = wheels.get(w);
//				x[w] -= wheel.trail[(wheel.trailLast - p) % Wheel.TRAIL_POINTS].x; 
//				y[w] -= wheel.trail[(wheel.trailLast - p) % Wheel.TRAIL_POINTS].y;
//				vertex(x[w], y[w]);
//			}
//			endShape(CLOSE);
//		}
		
		popStyle();
	}

	void drawAxes() {
		pushStyle();
		stroke(0xFF666666);
		line(0, -height, 0, height);
		line(-width, 0, width, 0);
		popStyle();
	}

	
}
