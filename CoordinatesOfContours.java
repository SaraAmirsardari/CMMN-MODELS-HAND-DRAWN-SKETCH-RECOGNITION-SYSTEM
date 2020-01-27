package CMMNElementsketchRecognitionSystem;

import org.opencv.core.Point;

public class CoordinatesOfContours implements Coordinates {

	private double x;
	private double y;
	private double width;
	private double height;
	private int id;
	private String type = "";
	
	/**
	 * This class defines the coordinate of  shapes inside the input image
	 */
	public CoordinatesOfContours(double x, double y, double width, double height, int id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	public int getid() {
		return id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String toString(){
		return "Coordinate: ("+this.x+","+this.y+"), Width:"+this.width+", Height:"+this.height;
	}
	
	public CoordinatesOfContourEdge getTopLine(){
		CoordinatesOfContourEdge result = new CoordinatesOfContourEdge();
		result.setStartLine(new Point(x,y));
		result.setEndLine(new Point(x+width, y));
		return result;
	}
	
	public CoordinatesOfContourEdge getBottomLine(){
		CoordinatesOfContourEdge result = new CoordinatesOfContourEdge();
		result.setStartLine(new Point(x,y+height));
		result.setEndLine(new Point(x+width, y+height));
		return result;
	}
	
	public CoordinatesOfContourEdge getLeftLine(){
		CoordinatesOfContourEdge result = new CoordinatesOfContourEdge();
		result.setStartLine(new Point(x,y));
		result.setEndLine(new Point(x, y+height));
		return result;
	}
	
	public CoordinatesOfContourEdge getRightLine(){
		CoordinatesOfContourEdge result = new CoordinatesOfContourEdge();
		result.setStartLine(new Point(x+width,y));
		result.setEndLine(new Point(x+width, y+height));
		return result;
	}

}
