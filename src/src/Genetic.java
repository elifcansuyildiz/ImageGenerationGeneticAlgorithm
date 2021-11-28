import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Cansu
 */
public class Genetic {
	
	public static final int THREAD_NUM = 8;
    
    ArrayList<Picture> picture;
    ArrayList<Picture> selectedPicture;
    int numOfShape;
    int numOfPicture;
    double selectionRate;
    double mutuationRate;
    int pictureMethod;
    String shape;
    BufferedImage realImage;
    Picture bestPicture;
    
    public Genetic(int numOfPicture,String shape, int numOfShape, int pictureMethod, BufferedImage realImage, double selectionRate, double mutuationRate){
        this.numOfShape=numOfShape;
        this.numOfPicture=numOfPicture;
        this.realImage=realImage;
        this.selectionRate=selectionRate;
        this.pictureMethod=pictureMethod;
        this.shape=shape;
        this.mutuationRate=mutuationRate;     
        this.picture = new ArrayList<Picture>();
        this.selectedPicture = new ArrayList<Picture>();
        
        initialize();  
    }
    
    public void initialize(){
        
        picture.clear();
               
        //picture population is created
        for(int i=0;i<numOfPicture;i++){
        	picture.add(new Picture(realImage.getWidth(), realImage.getHeight(), pictureMethod));
        }
        
        //each shape is created and added to array of picture named as "shape"
        //sizes of shapes are randomized while creating object of shapes
        for(int i=0;i<numOfPicture;i++){
            if(shape.equals("circle")){                                  
                for(int j=0;j<numOfShape;j++){
                    Paintable p=new Circle(picture.get(i));
                    picture.get(i).addShapeItem(p);
                }
            }
            else if(shape.equals("square")){
                for(int j=0;j<numOfShape;j++){
                    Paintable p=new Square(picture.get(i));
                    picture.get(i).addShapeItem(p);
                }
            }
            else if(shape.equals("triangle")){
                for(int j=0;j<numOfShape;j++){
                    Paintable p=new Triangle(picture.get(i));
                    picture.get(i).addShapeItem(p);
                }
            }
            else{
                System.err.println("Shape ["+ shape + "] does not recognized by Genetic");
            }
        }
    }
    
    public void fitnessCalculation(){    
    	
        // Set Best picture
    	if (bestPicture != null)
    		picture.set(0, bestPicture);
    	
    	// SINGLE-THREAD
    	/*
        for(int i=0; i<picture.size(); i++){
            try {
				picture.get(i).fitness= 1.0 - ImageUtil.differenceImage(picture.get(i).getImage(), realImage);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        */
    	
    	// MULTI-THREAD [8]
    	FitnessCalculator t[] = new FitnessCalculator[THREAD_NUM];
    	
    	for (int i=0; i<THREAD_NUM; i++) {
    		t[i] = new FitnessCalculator(i, THREAD_NUM, picture, realImage);
    	}
    	
    	for (int i=0; i<THREAD_NUM; i++) {
    		t[i].start();
    	}
    	
    	for (int i=0; i<THREAD_NUM; i++) {
    		try {
				t[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	
    	
    	
    }
    
    public void Selection(){
    	// Sort best to worst
        Collections.sort(picture);
        
        // Do selection
        selectedPicture.clear();    
        for (int i = 0; i <= (int)(picture.size() * selectionRate) ; i++) {
            selectedPicture.add(picture.get(i));
        }
 
        // Print selected pictures' fitnesses
        /*
        for(int i=0; i<selectedPicture.size(); i++){
            System.out.println("Fitness[" + (i) + "] = " + selectedPicture.get(i).fitness);
        }       
        */
    }
    
    public void crossOver(){     
    	
    	Picture p1 = null;
    	Picture p2 = null;
        
    	// Clear old population and create new
        initialize();
        
        for (int i = 0; 2*i+1 < picture.size(); i++) {  
        	
        	p1 = selectedPicture.get( RandomGenerator.getRandomInt(selectedPicture.size()) ); //selected random picture to cross over
            
            do {
            	p2 = selectedPicture.get( RandomGenerator.getRandomInt(selectedPicture.size()) ); //selected random picture to cross over
            } while (p1 == p2);
            
            int crossOverPoint = RandomGenerator.getRandomInt(numOfShape); 
            
            //crossing over
            for (int j = 0; j < p1.getShapeCount() && j < p2.getShapeCount(); j++) {
                if (j < crossOverPoint) {                 
                    picture.get(2 * i).setShapeItem(j, p1.getShapeItem(j) );                    
                    picture.get(2 * i + 1).setShapeItem(j, p2.getShapeItem(j));
                } 
                else {                   
                    picture.get(2 * i).setShapeItem(j, p2.getShapeItem(j));
                    picture.get(2 * i + 1).setShapeItem(j, p1.getShapeItem(j));
                }
            }               
        }   
    }
    
    public void mutuation(){
    	for (Picture p : picture) {
    		for (int i=0; i<p.getShapeCount(); i++) {
                if(RandomGenerator.getRandomFloat() < mutuationRate) {
                	// Move shape item to end of the list
                	Paintable paintable = p.getShapeItem(i);
                	p.removeShapeItem(i);
                	paintable.randomize(p);
                	//p.setShapeItem(i, paintable);
                	p.insertShapeItem(RandomGenerator.getRandomInt(p.getShapeCount()),paintable);
                	
                }
    		}
    	}
    
    }
    
    public Picture getBest(){         
        Collections.sort(selectedPicture);
        bestPicture = selectedPicture.get(0);
        System.out.println("SelectedBest.fitness = " + bestPicture.fitness);   // TODO: test 
        return bestPicture;
    }
    
}

class FitnessCalculator extends Thread {
	int id, step;
	ArrayList<Picture> picture;
	BufferedImage realImage;
	
	public FitnessCalculator(int id, int step, ArrayList<Picture> picture, BufferedImage realImage) {
		super();
		this.id = id;
		this.step = step;
		this.picture = picture;
		this.realImage = realImage;
	}
	
	public void run() {
        for(int i=id; i<picture.size(); i+=step){
            try {
				picture.get(i).fitness= 1.0 - ImageUtil.differenceImageHSB(picture.get(i).getImage(), realImage); // HSB
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}
	
	
}
