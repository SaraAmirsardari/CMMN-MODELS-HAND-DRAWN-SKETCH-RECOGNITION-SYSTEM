package CMMNElementsketchRecognitionSystem;

import org.opencv.core.Point;

/**
 * This class gets and sets the coordinate of shape and coordinate of line as well as the distance between them
 * @author SaraAmirsardari
 *
 */
public class DistanceFromContourToLine {
	CoordinatesOfContours shape;
	Point linePoint;
	double distance;
	
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
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
