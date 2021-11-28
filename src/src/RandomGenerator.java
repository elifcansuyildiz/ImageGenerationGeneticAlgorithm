import java.awt.Color;
import java.util.Random;

public class RandomGenerator {
	
	private static Random rand = null;

	// Prevent constructing RandomGenerator
	private RandomGenerator() {}
	
	private static void checkInstance() {
		if (rand == null) {
			rand = new Random();
		}
	}
	
	/**
	 * Generates random Color RGB value. Each calls may return different value
	 * @return Random value
	 */
	public static Color getRandomColor() {
		checkInstance();
		//return new Color(rand.nextInt(0xFFFFFF));
		return Color.getHSBColor(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
	}
	
	/**
	 * Generates random int value. Each calls may return different value
	 * @param upperLimit	Limit value	
	 * @return Random value	between 0 to upperLimit
	 */
	public static int getRandomInt(int upperLimit) {
		checkInstance();
		return rand.nextInt(upperLimit);
	}
	
	/**
	 * Generates random int value. Each calls may return different value
	 * @return Random value	with no limit
	 */
	public static int getRandomInt() {
		checkInstance();
		return rand.nextInt();
	}
	
	/**
	 * Generates random float value. Each calls may return different value
	 * @return Random value between 0.0 to 1.0
	 */
	public static float getRandomFloat() {
		checkInstance();
		return rand.nextFloat();
	}
	
	/**
	 * Generates random double value. Each calls may return different value
	 * @return Random value between 0.0 to 1.0
	 */
	public static double getRandomDouble() {
		checkInstance();
		return rand.nextDouble();
	}
	
	/**
	 * Generates random boolean value. Each calls may return different value
	 * @return Random value
	 */
	public static boolean getRandomBoolean() {
		checkInstance();
		return rand.nextBoolean();
	}
	
	/**
	 * Generates random long value. Each calls may return different value
	 * @return Random value with no upper limit
	 */
	public static long getRandomLong() {
		checkInstance();
		return rand.nextLong();
	}
}
