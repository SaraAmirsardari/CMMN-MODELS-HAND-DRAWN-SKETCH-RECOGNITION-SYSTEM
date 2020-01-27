package CMMNElementsketchRecognitionSystem;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class calculates the distance between each line and the rest of the lines in input image
 * @author SaraAmirsardari
 */

public class CalculateDistanceBetweenLines {

	/**
	 * This method calculate the distance between two lines.
	 * @param line1 is the first line to calculate.
	 * @param line2 is the second line to calculate. 
	 * @return
	 */
	public double calculateDistance(CoordinatesOfLines line1, CoordinatesOfLines line2) {

		double distance = 0;
		double dx = line1.getX1() - line2.getX1();
		double dy = line1.getY1() - line2.getY1();
		distance = Math.sqrt(dx*dx + dy*dy);
		return distance;
	}	


	/**
	 * this method merges the lines according to their distance
	 * @param line is the first line to compare its distance with the rest of line in list
	 * @param list is the list of all lines in input image
	 * this method verifies:
	 * first: the distance of the two lines that is less that threshold or not,
	 * second: if it is less than the threshold, it starts merging two lines based on 
	 * the minimum start point of lines and maximum end point of lines
	 * this method returns the longest line
	 */
	public void mergingLines(CoordinatesOfLines line, ArrayList<CoordinatesOfLines> list){


		ArrayList<CoordinatesOfLines> linesToRemove=new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			// define a threshold for specifying the standard distance between independent lines
			double threshold = 10;
			int id=0;

			if(calculateDistance(line, list.get(i)) <= threshold) {
				id++;
				ArrayList<Double> coordinateX=new ArrayList<>();
				coordinateX.add(line.getX1());
				coordinateX.add(list.get(i).getX1());
				coordinateX.add(line.getX2());
				coordinateX.add(list.get(i).getX2());
				Double linex1 = Collections.min(coordinateX);
				Double linex2 = Collections.max(coordinateX);

				ArrayList<Double> coordinateY=new ArrayList<>();
				coordinateY.add(line.getY1());
				coordinateY.add(list.get(i).getY1());
				coordinateY.add(line.getY2());
				coordinateY.add(list.get(i).getY2());
				Double liney1 = Collections.min(coordinateY);
				Double liney2 = Collections.max(coordinateY);

				CoordinatesOfLines newLine = new CoordinatesOfLines(linex1,  liney1, linex2, liney2,id);

				line = newLine;
				linesToRemove.add(list.get(i));
			}

		}
		list.add(line);
		for(CoordinatesOfLines lineToRemove : linesToRemove){
			list.remove(lineToRemove);
		}	
		System.out.println("**********all linessssssssssss*****************"+ list);
	}
}
