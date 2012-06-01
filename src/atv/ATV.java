package atv;

import processing.core.PApplet;

public class ATV extends PApplet {

	private static final long serialVersionUID = 1L;

	public static void main(String _args[]) {
		PApplet.main(new String[] { atv.ATV.class.getName() });
	}


	float ctrlH = 140;
	float ctrlH2 = ctrlH / 2;
	float ctrlW = 11;
	float ctrlW2 = ctrlW / 2;
	float ctrlXL = 800 - 65;
	float ctrlYL = 120;
	float ctrlXR = ctrlXL + ctrlW * 2;
	float ctrlYR = ctrlYL;

	int valueMax = 70;
	int valueMin = -70;

	int valueL = 0;
	int valueR = 0;

	int zeroX = 350;
	int zeroY = 300;

	int centerX = 0; // x kordinata sjecista sa x-osi (y=0)
	int centerY = 0; // y kordinata sjecista sa y-osi (x=0)
	float _centerX = 0; // x kordinata sjecista sa x-osi (y=0)
	float _centerY = 0; // y kordinata sjecista sa y-osi (x=0)

	int wheelL = -50;
	int wheelR = +50;
	int wheelT = -70;
	int wheelB = +70;
	float wheelATL = 0, wheelATR = 0, wheelABL = 0, wheelABR = 0;

	boolean auto = false;
	float _valueL = valueL = -29;
	float _valueR = valueR = 28;
	float _dL = 0.13F;
	float _dR = -0.093F;

	

	public void keyPressed() {
		switch (key) {
		case 'a':
			auto = !auto;
			if (auto) loop(); else noLoop();
			break;
		case '7':
			valueL = (int) (_valueL = valueL + 1);
			redraw();
			break;
		case '1':
			valueL = (int) (_valueL = valueL - 1);
			redraw();
			break;
		case '9':
			valueR = (int) (_valueR = valueR + 1);
			redraw();
			break;
		case '3':
			valueR = (int) (_valueR = valueR - 1);
			redraw();
			break;
		}
	}

	public void setup() {
		size(800, 600);

		stroke(0);
		smooth();
		frameRate(100);

		noLoop();
	}

	public void draw() {
		clear();
		drawFps();

		if (auto) {
			if (!draggingL) {
				if (_valueL + _dL < valueMin || _valueL + _dL > valueMax)
					_dL = -_dL;
				_valueL += _dL;
				valueL = round(_valueL);
			}

			if (!draggingR) {
				if (_valueR + _dR < valueMin || _valueR + _dR > valueMax)
					_dR = -_dR;
				_valueR += _dR;
				valueR = round(_valueR);
			}
		}

		drawControlls();
		doAxes();

		if (valueL == valueR) {
			_centerX = centerX = 0;
			_centerY = centerY = valueL;
		} else {
			centerY = round(_centerY = (_valueL + _valueR) / 2.0F);
			centerX = round(_centerX = -_centerY
					/ ((_valueR - _valueL) / (wheelR - wheelL)));
		}

		drawWheels();

		drawCtrlLines();

	}

	float formula(float x) { 
	  if (valueL != valueR) 
	    return x * (valueR - valueL) / (wheelR - wheelL) + (valueL + valueR) / 2.0F;
	  
	  return valueL;
	}


	void drawCtrlLines() {
	  stroke(Colors.GREEN_LIGHT);
	  pushMatrix();
	  translate(zeroX, zeroY);

	  line(wheelL, -valueMax, wheelL, -valueMin);
	  ellipse(wheelL, -valueL, 3, 3);

	  line(wheelR, -valueMax, wheelR, -valueMin);
	  ellipse(wheelR, -valueR, 3, 3);
	  
	  stroke(Colors.RED_LIGHT);
	  line(-1000, -formula(-1000), 1000, -formula(1000));
	  
	  rect(0, -centerY, 4, 4);
	  rect(centerX, 0, 4, 4);

	  popMatrix();
	}


	float calcAngleBetween(float x1, float y1, float x2, float y2) {
	  pushMatrix();
	  translate(zeroX, zeroY);
	  stroke(Colors.RED_LIGHT);
	  line(x1, -y1, x2, -y2);
	  popMatrix();
	  
	  float x = x2-x1;
	  float y = y2-y1;
	  float d = dist(x1, y1, x2, y2);

	  float a = asin(x / d);
	  
	  if (y < 0)
	    a = PI - a;
	    
	  return a;
	}

	void drawWheels() {
	  drawWheel(wheelL, wheelT, valueL==valueR ? 0 : calcAngleBetween(wheelL, -wheelT, centerX, 0) - HALF_PI, 1);
	  drawWheel(wheelR, wheelT, valueL==valueR ? 0 : calcAngleBetween(wheelR, -wheelT, centerX, 0) - HALF_PI, -1);
	  drawWheel(wheelR, wheelB, valueL==valueR ? 0 : calcAngleBetween(wheelR, -wheelB, centerX, 0) - HALF_PI, -1);
	  drawWheel(wheelL, wheelB, valueL==valueR ? 0 : calcAngleBetween(wheelL, -wheelB, centerX, 0) - HALF_PI, 1);
	}

	void drawWheel(float x, float y, float angle, int axis) {
	  int w = 10;
	  int h = 20;
	  boolean flip = false;
	  boolean rev = false;
	  axis = axis==0 ? axis : axis / abs(axis) * (w+5);
	  
	  if (x > 0) {
	    rev = (centerY < 0) || (centerY == 0 && valueR < 0);
	    if (centerX > 0 && centerX <= wheelR) rev = !rev;
	  }
	  if (x < 0) {
	    rev = (centerY < 0) || (centerY == 0 && valueL < 0);
	    if (centerX < 0 && centerX >= wheelL) rev = !rev;
	  }
	  

	  print("x:" + x + ", centerX: " + centerX + ", centerY: " + centerY + ", axis: " + axis + ", rev: " + rev);
	  
	  if (valueL != valueR && centerX < x) {
	    flip = true;
	  }    


	  println(", rev: " + rev + ", axis: " + axis + ", flip: " + flip);
	  
	  
	  pushMatrix();
	  translate(zeroX, zeroY);
	  translate(x, y);  
	  
	  fill(Colors.RED_LIGHT2);
	  stroke(Colors.RUBBER);
	  ellipse(0, 0, 6, 6);

	  rotate(angle);
	  
	  if (flip)
	  rotate(-PI);
	  
	  translate(-axis, 0);
	      
	  rectMode(CENTER);
	  fill(rev ? Colors.RED_LIGHT2 : Colors.GREEN_LIGHT2);
	  rect(0, 0, w*2, h*2);
	  
	  stroke(Colors.RUBBER_LIGHT);
	  if (rev) {
	    line(0, 0-h+w, 0-w, 0-h);  
	    line(0, 0-h+w, 0+w, 0-h); 
	    
	    line(0, 0-h/2+w, 0-w, 0-h/2);  
	    line(0, 0-h/2+w, 0+w, 0-h/2); 
	    
	    line(0, 0+w, 0-w, 0);  
	    line(0, 0+w, 0+w, 0);  
	    
	    line(0, 0+h/2+w, 0-w, 0+h/2);  
	    line(0, 0+h/2+w, 0+w, 0+h/2); 

	  } else {

	    line(0, 0-h, 0-w, 0-h+w);  
	    line(0, 0-h, 0+w, 0-h+w); 
	    
	    line(0, 0-h/2, 0-w, 0-h/2+w);  
	    line(0, 0-h/2, 0+w, 0-h/2+w); 
	    
	    line(0, 0, 0-w, 0+w);  
	    line(0, 0, 0+w, 0+w);  
	    
	    line(0, 0+h/2, 0-w, 0+h/2+w);  
	    line(0, 0+h/2, 0+w, 0+h/2+w); 
	  }

	  stroke(0xFF333399);
	  noFill();
	  rect(0, 0, w*2, h*2);
	  
	  
	  popMatrix();
	}


	void doAxes() {
	  stroke(0xFF666666);
	  line(zeroX, 0, zeroX, height);
	  line(0, zeroY, width, zeroY);
	}





	boolean draggingL = false;
	boolean draggingR = false;

	public void mousePressed() {
	  draggingL = mouseX >= ctrlXL-ctrlW2 && mouseX <= ctrlXL+ctrlW2 && mouseY >= ctrlYL-ctrlH2-ctrlW2 && mouseY <= ctrlYL+ctrlH2+ctrlW2;
	  draggingR = mouseX >= ctrlXR-ctrlW2 && mouseX <= ctrlXR+ctrlW2 && mouseY >= ctrlYR-ctrlH2-ctrlW2 && mouseY <= ctrlYR+ctrlH2+ctrlW2;
	  mouseDragged();
	}

	public void mouseReleased() {
	  draggingL = draggingR = false;
	}

	public void mouseDragged() {
	  if (draggingL) {
	    _valueL = map(constrain(mouseY, ctrlYL-ctrlH2, ctrlYL+ctrlH2), ctrlYL-ctrlH2, ctrlYL+ctrlH2, valueMax, valueMin);
	    valueL = round(_valueL);
	  }
	  if (draggingR) {
	    _valueR = map(constrain(mouseY, ctrlYR-ctrlH2, ctrlYR+ctrlH2), ctrlYR-ctrlH2, ctrlYR+ctrlH2, valueMax, valueMin);
	    valueR = round(_valueR);
	  }
	  redraw();
	}





	void drawControlls() {
	  drawControll(ctrlXL, ctrlYL, valueL, -10);
	  drawControll(ctrlXR, ctrlYR, valueR, 10);
	}

	void drawControll(float x, float y, float value, float toff) {
	  rectMode(CORNER);
	  noStroke();
	  fill(0xFFFFFFFF);
	  rect(x-ctrlW2, y-ctrlH2, ctrlW, ctrlH+1);
	  arc(x, y-ctrlH2, ctrlW, ctrlW, -PI, 0);
	  arc(x, y+ctrlH2+1, ctrlW, ctrlW, 0, PI);
	 

	  stroke(0xFFAFAFAF);
	  //line(x, y-ctrlH2, x, y+ctrlH2);
	  rect(x-1, y-ctrlH2, 2, ctrlH);
	  
	  
	  float val = map(-value, valueMin, valueMax, y-ctrlH2, y+ctrlH2);
	  //noFill();
	  stroke(0);
	  ellipse(x, val, ctrlW-3, ctrlW-3);
	  
	  if (toff < 0) 
	    textAlign(RIGHT, CENTER);
	  else
	    textAlign(LEFT, CENTER);
	  
	  text(round(value), x + toff, val-2);
	    
	}

	void clear() {
	  background(196);
	  resetMatrix();
	}

	void drawFps() {
	  textAlign(RIGHT, BOTTOM);
	  text("" + (round(frameRate * 10) / 10.0) + " FPS", width, height);
	}
	
}
