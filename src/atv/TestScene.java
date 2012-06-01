package atv;

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


	int wheelA = 41;
	int wheelB = 100;

	List<Wheel> wheels = new LinkedList<Wheel>();

	float angle = 0F;
	
	private void createWheels(int count, int radiusX, int radiusY) {
		float a = PI;		
		if (count % 2 == 0) a = 2*PI / count / 2;
		
		for (int i = 0; i < count; i++) {
			wheels.add(new Wheel(this, (a > 0 ? Side.RIGHT : Side.LEFT), new Point((int)round(sin(a) * radiusX), (int)round(cos(a) * radiusY))));
			a = angle(a + 2*PI / count);
		}

	}

	public void setup() {		
		createWheels(4, 50, 80);
		
		size(1000, 600);

		stroke(0);
		smooth();
		frameRate(10);

		noLoop();
	}

	private volatile boolean drawing = false;

	public void draw() {
		drawing = true;

		clear();
		drawFps();
		
		
		textAlign(LEFT, BOTTOM);
		int i = 0; for (String debug : debugs) text(debug, 0, i+=15);
		debugs.clear();

		translate(zero.x, zero.y);
		
		debugs.add("mousep: " + mousep);
		debugs.add("center: " + center);
		angle = calcAngleBetween(new Point(0, 0), center);
		
		debugs.add(String.valueOf(angle));

		drawAxes();

		drawDebug();

		drawWheels();

		drawing = false;
	}


	@Override
	public void mouseMoved() {
		if (drawing)
			return;

		center = mousep = new Point(mouseX-zero.x, -mouseY+zero.y);
		
		redraw();
	}



	void drawDebug() {
		pushStyle();

		stroke(Colors.RED);
		strokeWeight(.5F);

		line(0, 0, center.x, -center.y);

		arc(0, 0, 50, 50, -PI/4, 0);

		popStyle();
	}

	void drawWheels() {
		for (Wheel wheel : wheels) {
			
			float a = calcAngleBetween(wheel.getAxis(), center);
			
			pushStyle();
			
			stroke(Colors.GREEN_LIGHT2);
			noFill();
			
			line(wheel.getAxis().x, -wheel.getAxis().y, center.x, -center.y);
			
			int r = (int) dist(wheel.getAxis().x, wheel.getAxis().y, center.x, center.y);
			
			arc(center.x, -center.y, 2*r, 2*r, a, a+.1F);
			
			popStyle();
			
			wheel.draw(a, false);
		}
	}

	void drawAxes() {
		pushStyle();
		stroke(0xFF666666);
		line(0, -height, 0, height);
		line(-width, 0, width, 0);
		popStyle();
	}

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
	
	
}
