package atv;

public class PointF {
	
	public float x;
	public float y;
	public float z;
	
	public PointF() { 		
	}
	
	public PointF(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public PointF(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "[" + x + " : " + y +" : " + z + "]";
	}
	
	
}
