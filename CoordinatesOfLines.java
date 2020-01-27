package CMMNElementsketchRecognitionSystem;

/**
 * 
 * @author 
 *
 */
/**
 * This class gets and sets the coordinates of lines
 * @author SaraAmirsardari
 */
public class CoordinatesOfLines{
	
	
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private int id;
	
	/**
	 * This class defines the coordinate of each line inside the input image
	 */
	
	public CoordinatesOfLines(double x1, double y1, double x2, double y2,int id) {
		
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.id = id;
	}

	public double getX1() {
		return x1;
	}

	public double getY1() {
		return y1;
	}

	public double getX2() {
		return x2;
	}

	public double getY2() {
		return y2;
	}

	public int getid() {
		
		return id;
	}
	
	public String toString(){
		return "start point: ("+this.x1+","+this.y1+"), end point: ("+this.x2+","+this.y2+")";
	}
}
