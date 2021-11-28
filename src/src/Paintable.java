import java.awt.image.BufferedImage;

public interface Paintable {
	/**
	 * URL: https://www.java-tips.org/java-se-tips-100019/27-java-awt-geom/508-how-to-draw-on-a-bufferedimage.html
	 * @param bi			Image that where things will be painted
	 * @param paintMethod	Locates in Picture.java
	 */
	public void paint(BufferedImage bi, int paintMethod);

	/**
	 * Randomizes its parameter according to Picture's dimensions
	 * @param p	Reference for max size of shape
	 */
	public void randomize(Picture p);
	
	/**
	 * Clones object
	 * @return	Cloned object
	 */
	public Paintable copy();
}
