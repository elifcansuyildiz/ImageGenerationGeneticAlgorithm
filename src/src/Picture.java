import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Picture implements Comparable<Picture>{
	public static final int TRANSPARENT_METHOD = 0;
	public static final int OVERLAP_METHOD = 1;
	public static final Color BACKGROUND_COLOR = Color.WHITE;
	
	private BufferedImage image;
	private int width;
	private int height;
	private int defaultPaintMethod;
	private ArrayList<Paintable> shape_list;
	private boolean isFresh;
	double fitness;
	
	/**
	 * Picture Constructor
	 * @param width		Width of the image
	 * @param height	Height of the image
	 * @param defaultPaintMethod	Default painting method when no arg given
	 */
	public Picture(int width, int height, int defaultPaintMethod) {
		this.width = width;
		this.height = height;
		this.defaultPaintMethod = defaultPaintMethod;
		this.shape_list = new ArrayList<Paintable>();
		isFresh = false;
	}
	
	/**
	 * Paints all shapes on to Image (This may take some time)
	 * @param paintMethod	Painting method when shapes goes on to the image
	 * @return	Painted image
	 */
	public BufferedImage getImage(int paintMethod) {
		
		if (isFresh) {
			return image;
		}
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		
		Graphics2D graphics = image.createGraphics();

		graphics.setPaint ( (Paint)BACKGROUND_COLOR );
		graphics.fillRect ( 0, 0, image.getWidth(), image.getHeight() );
		
		for (Paintable p : shape_list) {
			if (p != null) {
				p.paint(image, paintMethod);
			}
		}
		
		return image;
	}
	
	/**
	 * Paints all shapes on to Image with default Paint method (This may take some time)
	 * @return	Painted image
	 */
	public BufferedImage getImage() {
		return getImage(defaultPaintMethod);
	}
	
	/**
	 * Adds the copy of p to shape list
	 * @param p	Source object
	 */
	public void addShapeItem(Paintable p) {
		isFresh = false;
		shape_list.add(p.copy());
	}
	
	/**
	 * Inserts the copy of p to shape list
	 * @param p	Source object
	 */
	public void insertShapeItem(int index, Paintable p) {
		isFresh = false;
		shape_list.add(index, p.copy());
	}
	
	/**
	 * Returns original shape object
	 * @param index
	 * @return	Original shape object
	 */
	public Paintable getShapeItem(int index) {
		isFresh = false;
		return shape_list.get(index);
	}
	
	/**
	 * Sets shape object with copy of p
	 * @param index
	 * @param p
	 */
	public void setShapeItem(int index, Paintable p) {
		isFresh = false;
		shape_list.set(index, p.copy());
	}
	
	/**
	 * Gets shape count
	 * @return
	 */
	public int getShapeCount() {
		return shape_list.size();
	}
	
	public void removeShapeItem(int index) {
		isFresh = false;
		shape_list.remove(index);
	}
	
	/**
	 * Clears all shapes
	 */
	public void clearShapeList() {
		isFresh = false;
		shape_list.clear();
	}
	
    public int compareTo(Picture object){
        //Picture p=(Picture) object;
        
        if(fitness==object.fitness){
            return 0;
        }            
        else if(fitness>object.fitness)
            return -1;
        else
            return 1;
    }
	
	/********************************* GETTER/SETTER **************************/
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}

	public int getDefaultPaintMethod() {
		return defaultPaintMethod;
	}

	public void setDefaultPaintMethod(int defaultPaintMethod) {
		this.defaultPaintMethod = defaultPaintMethod;
	}
	
}
