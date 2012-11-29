package atv;

public class Point {
	
	public int x;
	public int y;
	public int z;
	
	public Point() { 		
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "[" + x + " : " + y +" : " + z + "]";
	}
	
	
}
