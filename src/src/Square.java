import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Square implements Paintable{
	
	private float x, y, width, height;
	private Color color;
	
	public Square() {
		
	}
	
	private Square(float x, float y, float width, float height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public Square(Picture p) {
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
			   g2d.fill(new Rectangle2D.Float(x, y, width, height));
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
			   g2d.fill(new Rectangle2D.Float(x, y, width, height));
			   g2d.dispose();
		   }
	}

	@Override
	public void randomize(Picture p) {
			x = RandomGenerator.getRandomFloat() * p.getWidth();
			y = RandomGenerator.getRandomFloat() * p.getHeight();
			width = RandomGenerator.getRandomFloat() * p.getWidth() / 4;
			height = RandomGenerator.getRandomFloat() * p.getHeight() / 4;
			color = RandomGenerator.getRandomColor();
	}

	@Override
	public Paintable copy() {
		return new Square(x, y, width, height, color);
	}
	
	// -------------------- SETTER / GETTER --------------------------
	
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

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
