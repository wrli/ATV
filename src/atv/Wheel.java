package atv;


public class Wheel {
	
	private int height = 35;
	private int width = 15;
	
	private Scene scene;
	private Side side;
	private Point axis;
	private float maxAngle = Scene.HALF_PI + Scene.PI*1/3;
	private float minAngle = -Scene.HALF_PI + Scene.PI*1/3;
	
	public Wheel(Scene scene, Side side, Point axis) {
		this.scene = scene;
		this.side = side;
		this.axis = axis;
		
		float a = scene.calcAngleBetween(0, 0, axis.x, axis.y);
		a += Scene.HALF_PI * (side==Side.LEFT ? -1 : 1);
		minAngle = Scene.angle(a - Scene.HALF_PI);
		maxAngle = Scene.angle(a + Scene.HALF_PI);
		if (maxAngle < minAngle) {
			a = minAngle;
			minAngle = maxAngle;
			maxAngle = a;
		}
	}
	
	public Wheel(Scene scene, Side side, Point axis, int width, int height) {
		this(scene, side, axis);
		this.width = width;
		this.height = height;
	}
	
	
	public void draw(float angle, boolean reverse) {
		//scene.debugs.add("angle w: " + Scene.degrees(scene.calcAngleBetween(0, 0, axis.x, axis.y)) + ", axis: " + axis);
		
		scene.pushMatrix();
		scene.pushStyle();

		scene.rectMode(Scene.CENTER);
		scene.ellipseMode(Scene.CENTER);

		scene.translate(axis.x, -axis.y);
		
		scene.noFill();
		
		scene.stroke(Colors.RUBBER);		
		
		//scene.debugs.add("angle1: " + Scene.degrees(angle));
		
		angle %= 2*Scene.PI;
		
		//scene.debugs.add("angle2: " + Scene.degrees(angle) + ", maxAngle: " + Scene.degrees(maxAngle) + ", minAngle: " + Scene.degrees(minAngle));

		if (angle > maxAngle || angle < minAngle) {
			angle = (angle + Scene.PI) % (2*Scene.PI);
			//scene.debugs.add("angle3: " + Scene.degrees(angle));
			reverse = !reverse;
		}
		//scene.debugs.add("reverse: " + reverse);

		scene.pushMatrix();

		scene.rotate(angle);
		//scene.translate(side == Side.LEFT ? -width / 2 - 5 : width / 2 + 5, 0);

		scene.fill(reverse ? Colors.RED_LIGHT2 : Colors.GREEN_LIGHT2);
		scene.rect(0, 0, width, height);
		
		scene.stroke(Colors.RUBBER_LIGHT);
		scene.strokeWeight(.8F);
		float w = width / 2.0F;
		float h = height / 2.0F;
		
		if (reverse) {
			scene.line(0, 0-h+w, 0-w, 0-h);  
		    scene.line(0, 0-h+w, 0+w, 0-h); 
		    
		    scene.line(0, 0-h/2+w, 0-w, 0-h/2);  
		    scene.line(0, 0-h/2+w, 0+w, 0-h/2); 
		    
		    scene.line(0, 0+w, 0-w, 0);  
		    scene.line(0, 0+w, 0+w, 0);  
		    
		    scene.line(0, 0+h/2+w, 0-w, 0+h/2);  
		    scene.line(0, 0+h/2+w, 0+w, 0+h/2); 
		} else {	
			scene.line(0, 0-h, 0-w, 0-h+w);  
			scene.line(0, 0-h, 0+w, 0-h+w); 
		    
			scene.line(0, 0-h/2, 0-w, 0-h/2+w);  
			scene.line(0, 0-h/2, 0+w, 0-h/2+w); 
		    
			scene.line(0, 0, 0-w, 0+w);  
			scene.line(0, 0, 0+w, 0+w);  
		    
			scene.line(0, 0+h/2, 0-w, 0+h/2+w);  
			scene.line(0, 0+h/2, 0+w, 0+h/2+w); 
		}
		
		scene.popMatrix();
		
		//scene.noFill();
		scene.stroke(Colors.METAL);
		scene.strokeWeight(2);
		
		scene.ellipse(0, 0, 8, 8);
		
		scene.popStyle();
		scene.popMatrix();
	}
	


	public Side getSide() {
		return side;
	}


	public Point getAxis() {
		return axis;
	}
	
}
