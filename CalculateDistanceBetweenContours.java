
package CMMNElementsketchRecognitionSystem;
import java.util.ArrayList;

/**
 * This class calculates the distance between each shape and the rest of the shapes in input image
 * @author SaraAmirsardari
 */
public class CalculateDistanceBetweenContours {

	/**
	 * This method calculate the distance between two shapes
	 * @param s1 is the first shape to calculate. 
	 * @param s2 is the second shape to calculate. 
	 * @return the distance between shapes.
	 */

	public double calculateDistance(CoordinatesOfContours s1, CoordinatesOfContours s2) {

		double distance = 0;
		double dx = s1.getX() - s2.getX();
		double dy = s1.getY() - s2.getY();
		distance = Math.sqrt(dx*dx + dy*dy);
		return distance;
	}

	/**
	 * this method Verifies if the shape is overlapping with any other shape in the list
	 * @param s is the shape to compare if it is overlapping.
	 * @param list is the list of all other shape that will compare to shape.
	 * @return True if the shape overlaps any other shapes, false otherwise.
	 */
	
	public boolean isOverlapping(CoordinatesOfContours s, ArrayList<CoordinatesOfContours> list){

		boolean overlap = false;
		for (int i = 0; i < list.size(); i++) {
			// define a threshold for specifying the overlap distance between to shapes
			double threshold = 7;
			if(calculateDistance(s, list.get(i)) <= threshold) {
				overlap = true;
				return overlap;
			}
		}

		return overlap;
	}

}
