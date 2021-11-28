import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Circle implements Paintable {
	
	private float x, y, r;
	private Color color;
	
	/**
	 * Circle Constructor
	 */
	public Circle() {
		
	}
	
	private Circle(float x, float y, float r, Color color) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.color = color;
	}
	
	/**
	 * Circle Construtor. Randomizes values referenced to Picture
	 * @param p		Randomizer reference
	 */
	public Circle(Picture p) {
		this();
		randomize(p);
	}

	@Override
	public void paint(BufferedImage bi, int paintMethod) { 
	   if (paintMethod == Picture.OVERLAP_METHOD) {
		   // No. 1
		   // Create a graphics context on the buffered image
		   Graphics2D g2d = bi.createGraphics();
		   
		   // Draw on the buffered image
		   g2d.setColor(color);
		   g2d.fill(new Ellipse2D.Float(x, y, r, r));
		   g2d.dispose();
	   }
	   else if (paintMethod == Picture.TRANSPARENT_METHOD) {
		   // No.2
		   // In case the buffered image supports transparency
		   Graphics2D g2d = bi.createGraphics();
		    
		   // Transparency is created on all the filled pixels
		   Color transparent = new Color(color.getRed(), color.getGreen(), color.getBlue(), 127);
		   g2d.setColor(transparent);
		   g2d.setComposite(AlphaComposite.SrcOver);
		   g2d.fill(new Ellipse2D.Float(x, y, r, r));
		   g2d.dispose();
	   }
	}

	@Override
	public void randomize(Picture p) {
		x = RandomGenerator.getRandomFloat() * p.getWidth();
		y = RandomGenerator.getRandomFloat() * p.getHeight();
		r = RandomGenerator.getRandomFloat() * Float.min(p.getWidth(), p.getHeight()) / 2;
		x -= r/2;
		y -= r/2;
		color = RandomGenerator.getRandomColor();
	}
	
	@Override
	public Paintable copy() {
		return new Circle(x, y, r, color);
	}
	
	/************************ SETTER/GETTER ********************************/

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
