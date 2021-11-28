import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		// Default parameters:
		String imageFile = "image.png";
	    String shape = "square";		// "square" or "circle" or "triangle"
	    String outputFile = "generated_image_";
	    int saveOutputs = 0;
	    int pictureMethod = 0 ;			// Picture.OVERLAP_METHOD=1 or Picture.TRANSPARENT_METHOD=0
	    int numOfPicture = 150; 		// population count
	    int numOfShape = 300;   		// shape count  (similar to DNA length)
	    int generation = 5000;
	    double selectionRate = 0.30;
	    double mutuationRate = 0.005;
	    
		// Get parameters
		if (args.length == 10 )
		{
			System.out.println("Using given parameters");
			imageFile = args[0];
			shape = args[1];
			outputFile = args[2];
			saveOutputs =  Integer.parseInt(args[3]);
			pictureMethod =  Integer.parseInt(args[4]) ;
			numOfPicture =  Integer.parseInt(args[5]) ;
			numOfShape =  Integer.parseInt(args[6]) ;
			generation =  Integer.parseInt(args[7]) ;
		    selectionRate = Double.parseDouble(args[8]);
		    mutuationRate = Double.parseDouble(args[9]);
		    
		}
		else
		{
			if (args.length != 0)
			{
				System.out.println("Some parameters are missing. See documentation");
				System.exit(1);
			}
			System.out.println("Using default parameters");
		}	
		
		// Create original GUI
		Screen original_screen = new Screen(640, 640, 0, 0, true);
		
		// Load from file
		BufferedImage original_image = ImageUtil.loadPNG(imageFile);		
		
		// Scale image to 640x480
		BufferedImage original_image_scaled = ImageUtil.resizeImage(original_image, 640, 480, true);
		
		// Show image
		original_screen.show(original_image_scaled);
	
		// Set textbox contents
		original_screen.setTitle("Original Image");
		original_screen.setTextSize(30);
		        
	    // Create generated GUI
	    Screen generated_screen = new Screen(640, 640, 650, 0, true);
	
	    // Set textbox contents
	    generated_screen.setTitle("Best Genome");
	    generated_screen.setTextSize(30);
	
	    Genetic g = new Genetic(numOfPicture, shape, numOfShape, pictureMethod, original_image, selectionRate, mutuationRate);
	    
	    PrintWriter xGenyFit = null;
	    if (saveOutputs>0) {
	    	xGenyFit = new PrintWriter("xGen_yFitness.txt", "UTF-8");
	    }
	    
	    for (int i=0; i<generation; i++) {
	        g.fitnessCalculation();
	        g.Selection();
	        
	        // Show best picture
	        Picture p = g.getBest();
	        generated_screen.show(p.getImage(), "Generation: " + i, "Fitness: " + String.format("%.3f", p.fitness));

	        if (saveOutputs>0)
	        {
	        	// Save best picture
		        File outputfile = new File(outputFile + i +".jpg");
		        ImageIO.write(p.getImage(), "png", outputfile);
		        
		        // save gen x fit
		        xGenyFit.println(i +", "+ p.fitness);
		        xGenyFit.flush();
	        }
	        
	        g.crossOver();
	        g.mutuation();
	    }
	    
	    xGenyFit.close();
	    generated_screen.close();
	    original_screen.close();
	    System.exit(0);
    
	}

}
