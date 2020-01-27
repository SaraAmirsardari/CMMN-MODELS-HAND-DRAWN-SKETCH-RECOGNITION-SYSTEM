package CMMNElementsketchRecognitionSystem;

/**
 *  This class investigates if the task and sentry have intersection area or not 
 * @author SaraAmirsardari
 */

public class CalculateIntersectionArea {
	
	/**
	 * 
	 * @param Sentrieslist composed of the coordinates of four vertices of diamond,
	 * each condition is calculated to verify whether the vertex of diamond is inside the task or not
	 * @param squarelist composed of the coordinates of four vertices of task true for each condition if the vertex of diamond be inside the task, 
	 * and totally return false if all conditions return false
	 * @return true for each condition if the vertex of diamond be inside the task, and totally return false if all conditions return false
	 */

	
	public static boolean recognizeIntersection( CoordinatesOfContours Sentrieslist,CoordinatesOfContours squarelist){

		// if vertex 1 of diamond is inside the square
		if( Sentrieslist.getX()>  squarelist.getX() &&  Sentrieslist.getX()<  squarelist.getX()+  squarelist.getWidth() && 
				Sentrieslist.getY()>  squarelist.getY() &&  Sentrieslist.getY()<  squarelist.getY()+  squarelist.getHeight()){
			return true;
		}
		// if vertex 2 of diamond is inside the square
		else if( Sentrieslist.getX()+ ( Sentrieslist.getWidth()/2) >  squarelist.getX() &&
				Sentrieslist.getX()+ ( Sentrieslist.getWidth()/2)<  squarelist.getX()+  squarelist.getWidth() &&
				Sentrieslist.getY()- ( Sentrieslist.getHeight()/2)>  squarelist.getY() && 
				Sentrieslist.getY()-(Sentrieslist.getHeight()/2)<  squarelist.getY()+  squarelist.getHeight()){
			return true;
		}
		// if vertex 3 of diamond is inside the square
		else if( Sentrieslist.getX()+ ( Sentrieslist.getWidth()) >  squarelist.getX() && 
				Sentrieslist.getX()+ ( squarelist.getWidth())<  squarelist.getX()+  squarelist.getWidth() &&
				squarelist.getY()>  squarelist.getY() && 
				squarelist.getY()<  squarelist.getY()+  squarelist.getHeight()){
			return true;
		}
		// if vertex 4 of diamond is inside the square
		else if( Sentrieslist.getX()+ ( Sentrieslist.getWidth()/2) >  squarelist.getX() && 
				Sentrieslist.getX()+ ( Sentrieslist.getWidth()/2)<  squarelist.getX()+  squarelist.getWidth() && 
				Sentrieslist.getY()+ ( Sentrieslist.getHeight()/2)>  squarelist.getY() && 
				Sentrieslist.getY()+( Sentrieslist.getHeight()/2)<  squarelist.getY()+  squarelist.getHeight()){
			return true;
		}
		return false;
	}
}
