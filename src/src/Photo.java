import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * !!!!!!!!!!!! NOT IMPLEMENTED !!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class Photo implements Paintable {
	
	private float x, y, scale;
	BufferedImage original_image;
	
	public Photo(BufferedImage original_image) {
		this.original_image = original_image;
	}
	
	public Photo(BufferedImage bi, Picture p) {
		this(bi);
		randomize(p);
	}

	@Override
	public void paint(BufferedImage bi, int paintMethod) {
		   if (paintMethod == Picture.OVERLAP_METHOD) {
			   // No. 1
			   // Create a graphics context on the buffered image
			   Graphics2D g2d = bi.createGraphics();
			   
			   // Draw on the buffered image
			   g2d.drawImage(original_image, 
					   0, 0, 
					   bi.getWidth(), bi.getHeight(), 
					   (int)x, (int)y, 
					   (int)(x+original_image.getWidth()*scale), (int)(y+original_image.getHeight()*scale), 
					   null);
			   
			   System.out.println(bi.getWidth()+ " " + bi.getHeight()+ " " +
					   (int)x + " " + (int)y + " " +
					   (int)(x+original_image.getWidth()*scale) +" "+ (int)(y+original_image.getHeight()*scale));
			   
			   g2d.dispose();
		   }
		   else if (paintMethod == Picture.TRANSPARENT_METHOD) {
			   System.out.println("TRANSPARENT_METHOD NOT SUPPORTED");
			   // No.2
			   // In case the buffered image supports transparency
			   //Graphics2D g2d = bi.createGraphics();
			    
			   // Transparency is created on all the filled pixels
			   //Color transparent = new Color(color.getRed(), color.getGreen(), color.getBlue(), 127);
			   //g2d.setColor(transparent);
			   //g2d.setComposite(AlphaComposite.SrcOver);
			   //g2d.fill(new Ellipse2D.Float(x, y, r, r));
			   //g2d.dispose();
		   }
	}

	@Override
	public void randomize(Picture p) {
		scale = RandomGenerator.getRandomFloat() * ((float)p.getWidth() / original_image.getWidth()) / 2.0f;
		x = RandomGenerator.getRandomFloat() * p.getWidth() - original_image.getWidth()*scale/2.0f;
		y = RandomGenerator.getRandomFloat() * p.getHeight() - original_image.getHeight()*scale/2.0f;
		
		//scale = 1.0f;
		System.out.println(scale);
	}

	@Override
	public Paintable copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
