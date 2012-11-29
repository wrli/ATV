package atv;


public class Wheel {
	
	private final static float TRAVEL_SPEED = Scene.PI / 16;
	public final static int TRAIL_POINTS = 100;
	
	private int height = 41;
	private int width = 15;
	
	private Scene scene;
	private Side side;
	private Point axis;
	private boolean flipped = false;
	
	private float currentAngle, targetAngle = -Scene.HALF_PI;
	
	public PointF[] trail = new PointF[TRAIL_POINTS];
	public int trailLast = TRAIL_POINTS-1;
	{
		for (int i = 0; i < TRAIL_POINTS; i++)
			trail[i] = new PointF();
	}
	
	public Wheel(Scene scene, Point axis) {
		this.scene = scene;
		this.side = axis.x > 0 ? Side.RIGHT : Side.LEFT;
		this.axis = axis;
	}
	
	public Wheel(Scene scene, Point axis, int width, int height) {
		this(scene, axis);
		this.width = width;
		this.height = height;
	}
	
	
	public void draw(Float angle, float speed) {
		scene.debugs.add("angle: " + angle + ", speed: " + speed);
		
		if (angle != null) targetAngle = angle;
		
		angle = Scene.anglePoz(targetAngle) - Scene.anglePoz(currentAngle);
		
		if (Math.abs(angle) > Scene.PI) {
			angle = Math.signum(angle) * (angle - Scene.TWO_PI);
		}
		
		angle = Math.signum(angle) * Math.min(Math.abs(angle / 2F), TRAVEL_SPEED);
		
		currentAngle = Scene.angle(currentAngle + angle);
		
		float dx = (float) (speed * 10 * Math.cos(currentAngle));
		float dy = (float) (speed * 10 * Math.sin(currentAngle));
		PointF p1 = trail[(trailLast-1) % TRAIL_POINTS];
		PointF p0 = trail[trailLast % TRAIL_POINTS];
		if (Scene.dist(p1.x, p1.y, p0.x + dx, p0.y + dy) < 2) {
			p0.x += dx;
			p0.y += dy;			
		} else {
			trailLast++;
			trail[trailLast % TRAIL_POINTS].x = dx;
			trail[trailLast % TRAIL_POINTS].y = dy;
		}
		
		scene.pushMatrix();
		scene.pushStyle();

		scene.noFill();

		scene.rectMode(Scene.CENTER);
		scene.ellipseMode(Scene.CENTER);

		scene.translate(axis.x, -axis.y);
		
		scene.strokeWeight(.8F);
		scene.stroke(Colors.BLUE_LIGHT);
		scene.line(0F, 0F, 200 * speed * (float)Math.cos(currentAngle), 200 * speed * (float)Math.sin(currentAngle));
		
		if (angle > 0) {
			scene.stroke(Colors.GREEN_LIGHT);		
			scene.arc(0, 0, 100, 100, currentAngle, targetAngle);
		} else if (angle < 0) {
			scene.stroke(Colors.RED_LIGHT);		
			scene.arc(0, 0, 100, 100, targetAngle, currentAngle);
		}
				
		float angle2 = currentAngle + (side == Side.RIGHT ? Scene.HALF_PI : -Scene.HALF_PI);
		angle2 = Scene.angle(angle2);

		float angle3 = Scene.angle(angle2 + (flipped ? Scene.PI : 0));
		
		scene.pushMatrix();

		scene.rotate(angle3);
		
		scene.pushMatrix();

		scene.translate(width / 2 + 5, 0);
		drawTire();
		
		scene.popMatrix();
		
		scene.translate(-width / 2 - 5, 0);
		drawTire();
				
		scene.popMatrix();
		
		//scene.noFill();
		scene.stroke(Colors.METAL);
		scene.strokeWeight(2);
		
		scene.ellipse(0, 0, 8, 8);
		
		scene.popStyle();
		scene.popMatrix();
	}
	
	
	private void drawTire() {
		scene.pushStyle();
		
		scene.stroke(Colors.RUBBER);		
		scene.strokeWeight(.8F);
//		scene.fill(reverse ? Colors.RED_LIGHT2 : Colors.GREEN_LIGHT2);
		scene.fill(Colors.GREEN_LIGHT2);
		scene.rect(0, 0, width, height);
		
		scene.stroke(Colors.RUBBER_LIGHT);
		scene.strokeWeight(.8F);
		float w = width / 2.0F;
		float h = height / 2.0F;
		
//		if ((reverse && side == Side.RIGHT) || (!reverse && side == Side.LEFT)) {
		if (side == Side.LEFT) {
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
		
		scene.popStyle();
	}
	


	public Side getSide() {
		return side;
	}


	public Point getAxis() {
		return axis;
	}
	
}
