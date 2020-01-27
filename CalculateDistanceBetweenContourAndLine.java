package CMMNElementsketchRecognitionSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.opencv.core.Mat;
import org.opencv.core.Point;

/**
 * This class calculates the distance of start point and end point of each line with two specified shapes( task and sentry). 
 * Hence, we need to get the coordinates of lines from <code>detectLine</code> class as well as
 * the coordinates of tasks and sentries from <code>WriteXmlFile</code> class
 * @author SaraAmirsardari
 *
 */
public class CalculateDistanceBetweenContourAndLine {

	private ArrayList<CoordinatesOfLines> resultOfLines= new ArrayList<>();
	private ArrayList<CoordinatesOfContours> resultOfTasks= new ArrayList<>();
	private ArrayList<CoordinatesOfContours> resultOfSentries= new ArrayList<>();


	public void setResultOfLines(ArrayList<CoordinatesOfLines> resultOfLines) {
		this.resultOfLines = resultOfLines;
	}
	public ArrayList<CoordinatesOfLines> getResultOfLines(){
		return resultOfLines;	
	}

	public void setResultOfTasks(ArrayList<CoordinatesOfContours> resultOfTasks) {
		this.resultOfTasks = resultOfTasks;
	}
	public ArrayList<CoordinatesOfContours> getResultOfTasks(){

		return resultOfTasks;	
	}

	public void setResultOfSentries(ArrayList<CoordinatesOfContours> resultOfSentries) {
		this.resultOfSentries = resultOfSentries;
	}

	public ArrayList<CoordinatesOfContours> getResultOfSentries(){

		return resultOfSentries;	
	}

	/**
	 * this method calls the <code>findConnexionForPoint</code> method in order to calculate 
	 * the distance of start point and end point of line with the specified shapes 
	 * @param line is the coordinate of each line
	 * @return the result which includes the list of shapes connected to the line 
	 */

	public List<CoordinatesOfContours> getCloserShapeForLine(CoordinatesOfLines line){
		List<CoordinatesOfContours> result= new ArrayList<>(); 
		result.add( findConnexionForPoint(new Point(line.getX1(), line.getY1()) ) );
		result.add( findConnexionForPoint(new Point(line.getX2(), line.getY2()) ) );
		return result;
	}

	/**
	 * 
	 * @param p is one of the start point or end point of line
	 * this method calculate the distance of start point or end point of line with the specified shapes 
	 * @return the minimum distance of each start point or end point of line with the specified shapes
	 */
	public CoordinatesOfContours findConnexionForPoint(Point p){
		List<DistanceFromContourToLine> distances = new ArrayList<>();
		for(CoordinatesOfContours task : this.resultOfTasks){
			DistanceFromContourToLine distance = new DistanceFromContourToLine();
			distance.setShape(task);
			distance.setDistance(computeDistance(task, p ));		
			distances.add(distance);
		}
		for(CoordinatesOfContours sentry : this.resultOfSentries){
			DistanceFromContourToLine distance = new DistanceFromContourToLine();
			distance.setShape(sentry);
			distance.setDistance(computeDistance(sentry, p));		
			distances.add(distance);
		}

		DistanceFromContourToLine minimalDistance = null;
		for(DistanceFromContourToLine distance : distances){
			if(minimalDistance == null || distance.getDistance()<minimalDistance.getDistance()){
				minimalDistance = distance;
			}
		}

		return minimalDistance.getShape();

	}

	/**
	 * This class computes the distance of start point and end point of line with two edges of closed counter that can be vertical or horizontal
	 */
	private double computeDistanceToLine(Point pointToCompute, CoordinatesOfContourEdge line){

		double threshold = 5;
		Point lineStart = line.getStartLine();
		Point lineEnd = line.getEndLine();
		boolean isHorizontal = (lineStart.y == lineEnd.y);
		if(isHorizontal){
			//Make sure that lineStart has smaller x
			if(lineStart.x>lineEnd.x){
				Point p = lineStart;
				lineStart = lineEnd;
				lineEnd = p ;
			}
			//If the point is not between the start point of line and end point of line then return infinite value
			if(pointToCompute.x<lineStart.x-threshold || pointToCompute.x>lineEnd.x+threshold)
				return Double.MAX_VALUE;
			//If the point is between the start point of line and end point of line  then calculate the distance
			return Math.abs(lineStart.y-pointToCompute.y);
		} else { 
			//It is a vertical line, Make sure that lineStart has smaller y
			if(lineStart.y>lineEnd.y){
				Point p = lineStart;
				lineStart = lineEnd;
				lineEnd = p ;
			}
			//If the point is not between the start point of line and end point of line  then return infinite value
			if(pointToCompute.y<lineStart.y-threshold || pointToCompute.y>lineEnd.y+threshold)
				return Double.MAX_VALUE;
			//If the point is between the start point of line and end point of line  then calculate the distance
			return Math.abs(lineStart.x-pointToCompute.x);
		}
	}

	public double computeDistance(CoordinatesOfContours shape, Point p){

		double result = Double.MAX_VALUE;
		List<Double> distances = new ArrayList<>();

		distances.add( new Double( computeDistanceToLine( p, shape.getTopLine() ) ) );
		distances.add( new Double( computeDistanceToLine( p, shape.getBottomLine() ) ) );
		distances.add( new Double( computeDistanceToLine( p, shape.getLeftLine() ) ) );
		distances.add( new Double( computeDistanceToLine( p, shape.getRightLine() ) ) );

		for(Double distance : distances){
			if(distance.doubleValue() < result )
				result = distance.doubleValue();
		}

		return result;


	}
}





















//		