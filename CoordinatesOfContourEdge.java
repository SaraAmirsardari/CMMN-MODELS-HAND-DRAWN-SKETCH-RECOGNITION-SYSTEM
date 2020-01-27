package CMMNElementsketchRecognitionSystem;

import org.opencv.core.Point;


/**
 * this method gets and sets the start point and end point of each contour edge
 * @author SaraAmirsardari
 *
 */
public class CoordinatesOfContourEdge {
	private Point startLine;
	private Point endLine;
	
	
	public Point getStartLine() {
		return startLine;
	}
	public void setStartLine(Point startLine) {
		this.startLine = startLine;
	}
	public Point getEndLine() {
		return endLine;
	}
	public void setEndLine(Point endLine) {
		this.endLine = endLine;
	}
}

