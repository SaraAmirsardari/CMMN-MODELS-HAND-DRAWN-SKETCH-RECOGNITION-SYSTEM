package CMMNElementsketchRecognitionSystem;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

/**
 * This class get the input image and delete all closed contour 
 * and start recognizing the contours which include lines.
 * the OpenCV library is used in order to detect lines
 * @author SaraAmirsardari
 *
 */

public class DetectLine {

	private ArrayList<CoordinatesOfLines> ResultOfLines=new ArrayList<>();


	public ArrayList<CoordinatesOfLines> getResultOfLines(){

		return ResultOfLines;
	}

	private Mat initialImage;
	private List<MatOfPoint> shapesToRemove;


	public Mat getInitialImage() {
		return initialImage;
	}

	public void setInitialImage(Mat initialImage) {
		this.initialImage = initialImage;
	}

	public List<MatOfPoint> getShapesToRemove() {
		return shapesToRemove;
	}

	public void setShapesToRemove(List<MatOfPoint> shapesToRemove) {
		this.shapesToRemove = shapesToRemove;
	}
	/**
	 * This method start defining the bounding box around each closed shape 
	 * and then using threshold in order to increase the area of each closed shape 
	 * @param shape
	 */
	private void removeShape(MatOfPoint shape){

		int x = Imgproc.boundingRect(shape).x;
		int y = Imgproc.boundingRect(shape).y;
		int width = Imgproc.boundingRect(shape).width;
		int height = Imgproc.boundingRect(shape).height;

		MatOfPoint mpoints = new MatOfPoint();
		double threshold = 8;
		List<Point> points = new ArrayList<Point>();
		points.add(new Point(x-threshold,y-threshold));
		points.add(new Point(x+width+threshold,y-threshold));
		points.add(new Point(x+width+threshold,y+height+threshold));
		points.add(new Point(x-threshold,y+height+threshold));

		mpoints.fromList(points);
		//paint all closed contours by black color
		Core.fillConvexPoly(this.initialImage, mpoints,new Scalar(0,0,0));

		Highgui.imwrite("C:/Users/SARA/Desktop/opencv/anis1.jpg",this.initialImage);
	}

	public  void detectLine() {

		this.ResultOfLines = new ArrayList<>();
		for(MatOfPoint shape: this.shapesToRemove){
			removeShape(shape);
		}

		Highgui.imwrite("C:/Users/SARA/Desktop/opencv/anis2.jpg",this.initialImage);

		//      image – 8-bit, single-channel binary source image. The image may be modified by the function.
		//		lines – Output vector of lines. Each line is represented by a 4-element vector  (x_1, y_1, x_2, y_2) , where  (x_1,y_1) and  (x_2, y_2) are the ending points of each detected line segment.
		//		rho : The resolution of the parameter r in pixels. We use 1 pixel.
		//		theta: The resolution of the parameter theta in radians. We use 1 degree (CV_PI/180)
		//		threshold: The minimum number of intersections to “detect” a line
		//		minLinLength: The minimum number of points that can form a line. Lines with less than this number of points are disregarded.
		//		maxLineGap: The maximum gap between two points to be considered in the same line.

		Mat line = new Mat();
		int threshold = SketchRecognition.LINE_DETECTION_TRESHOLD;
		int minLineLength = 30;
		int maxLineGap = 10;
		int id=0;
		Imgproc.Canny(this.initialImage, this.initialImage, 50, 200);
		Highgui.imwrite("C:/Users/SARA/Desktop/opencv/anis3.jpg",this.initialImage);
		Imgproc.HoughLinesP(this.initialImage, line, 1, Math.PI/180, threshold, minLineLength, maxLineGap);

		for(int i = 0; i < line.cols(); i++) {
			double[] val = line.get(0, i);
			double  x1 = val[0], 
					y1 = val[1],
					x2 = val[2],
					y2 = val[3];

			CoordinatesOfLines recognizeLine = new CoordinatesOfLines(x1, y1, x2, y2,id);


			System.out.println("**detect lines**"+ recognizeLine);

			CalculateDistanceBetweenLines linedistance = new CalculateDistanceBetweenLines();
			linedistance.mergingLines(recognizeLine, ResultOfLines);


		}

	}

}
