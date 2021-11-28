import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

public class Triangle implements Paintable {
	
	private int x1, y1, x2, y2, x3, y3;
	private Color color;
	
	public Triangle() {
		
	}
	
	private Triangle(int x1, int y1, int x2, int y2, int x3, int y3, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
		this.color = color;	
	}
	
	public Triangle(Picture p) {
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
			   g2d.fill(new Polygon(new int[]{x1,x2,x3}, new int[]{y1,y2,y3}, 3));
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
			   g2d.fill(new Polygon(new int[]{x1,x2,x3}, new int[]{y1,y2,y3}, 3));
			   g2d.dispose();
		   }
	}

	@Override
	public void randomize(Picture p) {
		x1 = RandomGenerator.getRandomInt(p.getWidth());
		y1 = RandomGenerator.getRandomInt(p.getHeight());
		x2 = RandomGenerator.getRandomInt(p.getWidth());
		y2 = RandomGenerator.getRandomInt(p.getHeight());
		x3 = RandomGenerator.getRandomInt(p.getWidth());
		y3 = RandomGenerator.getRandomInt(p.getHeight());
		color = RandomGenerator.getRandomColor();
	}

	@Override
	public Paintable copy() {
		return new Triangle(x1, y1, x2, y2, x3, y3, color);
	}

}
