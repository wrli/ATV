package atv;

public class Utils {
	
	public static float cycle(float value, float max, float min, float halfCycle) {
		value %= halfCycle;
		if (value > max) return value - halfCycle;
		if (value < min) return value + halfCycle;
		return value;
	}


}
