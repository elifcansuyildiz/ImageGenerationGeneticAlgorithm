import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

	/**
	 * Deep copies the source image
	 * @param source	Source image
	 * @return	Deep copied image
	 */
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
	
	/**
	 * Resizes the image
	 * @param image			Source image
	 * @param newX			New width or x of image
	 * @param newY			New height or y of image
	 * @param keepRatio		If true, scale_x will be equal to scale_y
	 * @return	Scaled image
	 */
	public static BufferedImage resizeImage(BufferedImage image, int newX, int newY, boolean keepRatio) {	
		double scaleX = (double)newX / image.getWidth();
		double scaleY = (double)newY / image.getHeight();
		
		if (keepRatio) {
			if (scaleX > scaleY) {
				scaleX = scaleY;
			}
			else
			{
				scaleY = scaleX;
			}
		}
		
    	AffineTransform at = new AffineTransform();
    	at.scale(scaleX, scaleY);
    	AffineTransformOp scaleOp = 
    	   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    	return  scaleOp.filter(image, null);
	}
	
    /**
     * Reads image from file
     * @param filename	File location (ex: "C:\\Users\\UserName\\Desktop\\test.png")
     * @return	Imported image as BufferedImage
     */
    public static BufferedImage loadPNG(String filename) {
    	BufferedImage map = null;
    	try {
    		map = ImageIO.read(new File(filename));
    	} catch (IOException e) {
    		System.err.println(filename + " file not found!");
    	}
    	return map;
    }
    
    /**
     * 
     * @param image1
     * @param image2
     * @return	Difference between 0.0 and 1.0
     * @throws Exception
     */
    public static double differenceImageHSB(BufferedImage image1, BufferedImage image2) throws Exception {

        final byte[] pixels1 = ((DataBufferByte) image1.getRaster().getDataBuffer()).getData();
        final byte[] pixels2 = ((DataBufferByte) image2.getRaster().getDataBuffer()).getData();
        final boolean hasAlphaChannel1 = image1.getAlphaRaster() != null;
        final boolean hasAlphaChannel2 = image2.getAlphaRaster() != null;
    	float hsb1[] = new float[3];
    	float hsb2[] = new float[3];
    	float dH, dS, dB;
    	double distance = 0;
    	int imagePixelCount;
       
    	// Find pixel lengths
        int pixelLength1 = 3;
        int pixelLength2 = 3;
        
        if (hasAlphaChannel1) {
        	pixelLength1 = 4;
        }
        
        if (hasAlphaChannel2) {
        	pixelLength2 = 4;
        }
        
        // Error check
        if (pixels1.length / pixelLength1 != pixels2.length / pixelLength2) {
        	throw new Exception("Image size are not equal!");
        }
        
        // Find image size
        imagePixelCount = pixels1.length / pixelLength1;
        
        for (int pixel1 = 0, pixel2 = 0; pixel1 < pixels1.length && pixel2 < pixels2.length; pixel1 += pixelLength1, pixel2 += pixelLength2) {
        	
        	if (hasAlphaChannel1) {
        		// results to hsb1
        		Color.RGBtoHSB((int)pixels1[pixel1 + 3] & 0xff, (int)pixels1[pixel1 + 2] & 0xff, (int)pixels1[pixel1 + 1] & 0xff, hsb1);
        	}
        	else
        	{
        		// results to hsb1
        		Color.RGBtoHSB((int)pixels1[pixel1 + 2] & 0xff, (int)pixels1[pixel1 + 1] & 0xff, (int)pixels1[pixel1] & 0xff, hsb1);
        	}
        	
        	
        	if (hasAlphaChannel2) {
        		// results to hsb2
        		Color.RGBtoHSB((int)pixels2[pixel2 + 3] & 0xff, (int)pixels2[pixel2 + 2] & 0xff, (int)pixels2[pixel2 + 1] & 0xff, hsb2);
        	}
        	else
        	{
        		// results to hsb2
        		Color.RGBtoHSB((int)pixels2[pixel2 + 2] & 0xff, (int)pixels2[pixel2 + 1] & 0xff, (int)pixels2[pixel2] & 0xff, hsb2);
        	}
        	
            dH = Float.min(Math.abs(hsb1[0] - hsb2[0]), 1.0f - Math.abs(hsb1[0] - hsb2[0]));
            dS = Math.abs(hsb1[1]-hsb2[1]);
            dB = Math.abs(hsb1[2]-hsb2[2]);
        	
            distance += Math.sqrt(dH*dH + dS*dS + dB*dB);
        }   
        
        return distance / imagePixelCount;
     }
    
    /**
     * 
     * @param image1
     * @param image2
     * @return	Difference between 0.0 and 1.0
     * @throws Exception
     */
    public static double differenceImageRGB(BufferedImage image1, BufferedImage image2) throws Exception {

        final byte[] pixels1 = ((DataBufferByte) image1.getRaster().getDataBuffer()).getData();
        final byte[] pixels2 = ((DataBufferByte) image2.getRaster().getDataBuffer()).getData();
        final boolean hasAlphaChannel1 = image1.getAlphaRaster() != null;
        final boolean hasAlphaChannel2 = image2.getAlphaRaster() != null;
    	float rgb1[] = new float[3];
    	float rgb2[] = new float[3];
    	double distance = 0;
    	int imagePixelCount;
       
    	// Find pixel lengths
        int pixelLength1 = 3;
        int pixelLength2 = 3;
        
        if (hasAlphaChannel1) {
        	pixelLength1 = 4;
        }
        
        if (hasAlphaChannel2) {
        	pixelLength2 = 4;
        }
        
        // Error check
        if (pixels1.length / pixelLength1 != pixels2.length / pixelLength2) {
        	throw new Exception("Image size are not equal!");
        }
        
        // Find image size
        imagePixelCount = pixels1.length / pixelLength1;
        
        for (int pixel1 = 0, pixel2 = 0; pixel1 < pixels1.length && pixel2 < pixels2.length; pixel1 += pixelLength1, pixel2 += pixelLength2) {
        	
        	if (hasAlphaChannel1) {
        		// results to rgb1
        		rgb1[0] = (float)((int)pixels1[pixel1 + 3] & 0xff) / 256.0f;
        		rgb1[1] = (float)((int)pixels1[pixel1 + 2] & 0xff) / 256.0f;
        		rgb1[2] = (float)((int)pixels1[pixel1 + 1] & 0xff) / 256.0f;
        		
        	}
        	else
        	{
        		// results to rgb1
        		rgb1[0] = (float)((int)pixels1[pixel1 + 2] & 0xff) / 256.0f;
        		rgb1[1] = (float)((int)pixels1[pixel1 + 1] & 0xff) / 256.0f;
        		rgb1[2] = (float)((int)pixels1[pixel1 + 0] & 0xff) / 256.0f;
        	}
        	
        	
        	if (hasAlphaChannel2) {
        		// results to rgb2
        		rgb2[0] = (float)((int)pixels2[pixel2 + 3] & 0xff) / 256.0f;
        		rgb2[1] = (float)((int)pixels2[pixel2 + 2] & 0xff) / 256.0f;
        		rgb2[2] = (float)((int)pixels2[pixel2 + 1] & 0xff) / 256.0f;
        	}
        	else
        	{
        		// results to rgb2
        		rgb2[0] = (float)((int)pixels2[pixel2 + 2] & 0xff) / 256.0f;
        		rgb2[1] = (float)((int)pixels2[pixel2 + 1] & 0xff) / 256.0f;
        		rgb2[2] = (float)((int)pixels2[pixel2 + 0] & 0xff) / 256.0f;
        	}
        	
        	float dR = rgb1[0]-rgb2[0];
        	float dG = rgb1[1]-rgb2[1];
        	float dB = rgb1[2]-rgb2[2];
            distance += Math.sqrt(dR*dR + dG*dG + dB*dB);
        }   
        
        return distance / imagePixelCount;
     }
	
}
