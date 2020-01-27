package CMMNElementsketchRecognitionSystem;

import org.opencv.core.Point;

/**
 * this class gets and sets the coordinate of lines and the shapes that are connected to lines
 * @author SaraAmirsardari
 *
 */
public class ConnectorResult {
	
	CoordinatesOfContours shape;
	Point linePoint;
	
	public ConnectorResult(CoordinatesOfContours shape, Point linePoint){
	this.shape=shape;
	this.linePoint=linePoint;
	
	}
	public CoordinatesOfContours getShape() {
		return shape;
	}
	public void setShape(CoordinatesOfContours shape) {
		this.shape = shape;
	}
	public Point getLinePoint() {
		return linePoint;
	}
	public void setLinePoint(Point linePoint) {
		this.linePoint = linePoint;
	}
}
