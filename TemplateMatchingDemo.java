package CMMNElementsketchRecognitionSystem;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;
import org.w3c.dom.css.RGBColor;

/**
 * This class does the matching process between input image and template's images that include the following steps:
 * 1) First of all, start doing pre-processing on the input image and the template's images
 * 2) extract features of each contour in the input image
 * 3) resize the size of each contour according to the width of template image as well as keeping the aspect ratio
 * 4) At the end start finding the best match between each contour and template's images
 * The OpenCV library is used in order to do the Pre-processing process as well as doing the template matching
 * @author SaraAmirsardari
 *
 */
class TemplateMatchingDemo {

	private List<MatOfPoint> parentContrours = new ArrayList<MatOfPoint>();
	private Mat cleanedUpImage;

	public Mat getCleanedUpImage() {
		return cleanedUpImage;
	}

	public List<MatOfPoint> getParentContrours() {
		return parentContrours;
	}

	public void setParentContrours(List<MatOfPoint> parentContrours) {
		this.parentContrours = parentContrours;
	}

	private int id = -1;
	public int nextId() {
		id = id + 1;
		return id;
	}

	private int sh = -1;

	public int nextShape() {
		sh = sh + 1;
		return sh;
	}

	/**
	 * this variable will hold the list of templates,organized by shape type/name
	 * 
	 */
	private HashMap<String, Mat> templateTable = new HashMap<String, Mat>();

	/**
	 * This variable will contain arrays of coordinates of the various shapes
	 * recognized in the input figure, organized by shape type/name
	 * 
	 */
	private HashMap<String, ArrayList<CoordinatesOfContours>> shapeCoordinates = null;

	public ArrayList<CoordinatesOfContours> getListOfCoordinatesOfShapesOfType(String shapeName) {
		return shapeCoordinates.get(shapeName);
	}

	/**
	 * This class represents a bitmap that was already segmented. The actual
	 * bitmap is in <code>segmentedBitMap</code> and the contours are
	 * represented in the instance variable <code>contours</code>.
	 * 
	 * @author SaraAmirsardari
	 *
	 */

	class SegmentedImage {
		public Mat segmentedBitMap;

		public ArrayList<MatOfPoint> contours;

		public SegmentedImage(Mat bitmap, ArrayList<MatOfPoint> listOfContours) {
			segmentedBitMap = bitmap;
			contours = listOfContours;
		}
	}

	/**
	 * This function does pre-processing process(gaussian blurring, thresholding) 
	 * in a picture file (JPEG or PNG) to prepare them for matching.
	 * the file name is contained in the string inFile. It first loads
	 * the "bitmap" from the file <code>inFile</code> that applies filters to it.
	 * @param
	 * @return the processed image matrix 
	 */
	public Mat preprocessImage(String inFile) {
		// load the image and convert it to gray
		Mat img = Highgui.imread(inFile, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		Highgui.imwrite("C:/Users/SARA/Desktop/opencv/step1.jpg",img);
		Mat destination = new Mat(img.rows(), img.cols(), img.type());
		//blur operation reduces noise and smoothing the grayscale image
		Imgproc.GaussianBlur(img, destination, new Size(3, 3), 0);
		Highgui.imwrite("C:/Users/SARA/Desktop/opencv/step2.jpg",destination);
		// Threshold operation which converts a grayscale image into a binary image
		Imgproc.threshold(destination, destination, -1, 255, Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);
		Highgui.imwrite("C:/Users/SARA/Desktop/opencv/step3.jpg",destination);

		this.cleanedUpImage = destination.clone();
		Highgui.imwrite("C:/Users/SARA/Desktop/opencv/anis.jpg",cleanedUpImage);

		return destination;
	}

	/**
	 * This function gets the list of templates<code>listOfTemplates</code> as a
	 * string and convert them to matrix and then store them in the ArrayList
	 * <Mat> of <code>template</code>.
	 * @param listOfTemplates is list of template's images
	 * @return the processed template matrix to the template table
	 */

	public void preprocessAllTemplates(ArrayList<String> listOfTemplates) {

		for (int i = 0; i < listOfTemplates.size(); i++) {
			String nextTemplateFileName = listOfTemplates.get(i);
			// find the name of the file, without the .jpg or .png extension. That file name will represent the name of the
			// CMMN construct (file, sentry, event, etc. Here is an example of what it looks like
			// C:\Users\SARA\Desktop\opencv\sample\template100\task\task.png. first, separate file name based on \, and remove extension

			String splitter = File.separator.replace("\\", "\\\\");
			String[] pathElements = nextTemplateFileName.split(splitter);
			String fileName = pathElements[pathElements.length - 1];
			String templateName = (fileName.split("\\."))[0];
			System.out.println("next template name is: " + templateName);
			// load the template and convert it to gray
			Mat img = Highgui.imread(nextTemplateFileName, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
			Mat destination = new Mat(img.rows(), img.cols(), img.type());
			//blur operation reduces noise and smoothing the grayscale template image
			Imgproc.GaussianBlur(img, destination, new Size(3, 3), 0);
			// Threshold operation which converts a grayscale template image into a binary template image
			Imgproc.threshold(destination, destination, -1, 255, Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);

			// add the processed template matrix to the template table
			templateTable.put(templateName, destination);

		}
	}


	/**
	 * this function takes as input the name of a graphical file (PNG or JPEG)
	 * and returns a record (an instance of <code>SegmentedImage</code>)
	 * consisting of, 1) the bitmap of the segmented image, and 2) the list of
	 * contours (each contour being a vector of points).
	 * 
	 * @param inFile is image matrix
	 * @return
	 */
	public SegmentedImage segmentImage(String inFile) {
		Mat segmentedBitMap = preprocessImage(inFile);

		// find the contours inside input image 
		Mat hierarchy = new Mat();
		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(segmentedBitMap, contours,hierarchy,Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_NONE);
		ArrayList<MatOfPoint> contoursToRemove = new ArrayList<MatOfPoint>();
		for ( int idx=0; idx<contours.size(); idx++ ){
			double[] contourHierarchy = hierarchy.get(0,idx);
			System.out.println(" Hierarchy" + contourHierarchy[0]+ " "+ contourHierarchy[1]+ " "+ contourHierarchy[2]+ " "+ contourHierarchy[3]+ " ");

			if(contourHierarchy[3]!=-1){
				Imgproc.drawContours(segmentedBitMap, contours,idx, new Scalar(255, 255, 255),2);
				this.parentContrours.add(contours.get(idx));
			}	else {
				contoursToRemove.add(contours.get(idx));
			}
		}

		for(MatOfPoint i: contoursToRemove){
			System.err.println("show me the remove contours"+i );
			contours.remove(i);
			System.err.println("show me the remove contours"+contours );
		}

		return new SegmentedImage(segmentedBitMap, contours);
	}

	/**
	 * in this function, the feature of each contour inside the image extracted
	 * @param enclosingBitmap is the input image
	 * @param shape is the contour inside input image 
	 * @return the new contour matrix that include the contour feature such as start coordinate (x,y), width and height.
	 */
	public Mat getShapeSubBitMap(Mat enclosingBitmap, MatOfPoint shape) {

		int w = Imgproc.boundingRect(shape).width;
		int h = Imgproc.boundingRect(shape).height;
		int x = Imgproc.boundingRect(shape).x;
		int y = Imgproc.boundingRect(shape).y;
		
		System.out.println(x + " " + y + " " + w + " " + h + " ");
		Range rowRange = new Range(y, y + h);
		Range colRange = new Range(x, x + w);

		return new Mat(enclosingBitmap, rowRange, colRange);
	}

	/**
	 * This function resize the contours (subShapeBitMap) according to the
	 * template size by preserving the scale of the contours.
	 * @param subShapeBitMap is contour matrix
	 * @param templateWidth is width of template image
	 * @return the new size of contour matrix
	 */

	public Size getResizeSize(Mat subShapeBitMap, double templateWidth ) {

		double scale = (double) subShapeBitMap.width() / (double) subShapeBitMap.height();
		System.err.println("Width = " + subShapeBitMap.width());
		System.err.println("Height = " + subShapeBitMap.height());
		System.err.println("Scale = " + scale);
		double newW = templateWidth;
		double newH = newW / scale;
		return new Size(newW, newH);
	}


	/**
	 * This function finds the matching between the contour that was defined in
	 * <code>currentShapeSubBitMap</code> and the template that was defined in
	 * <code>templates</code>
	 * 
	 */


	public ArrayList<FindMatching> findMatching(Mat currentShapeSubBitMap, Mat segmentedInputBitMap, int shapeId) {

		ArrayList<FindMatching> returnValue = new ArrayList<>();
		Set<String> templateNames = templateTable.keySet();
		Iterator<String> templateNameIterator = templateNames.iterator();
		ArrayList<MinMaxLocResult> results = new ArrayList<MinMaxLocResult>();

		while (templateNameIterator.hasNext()) {
			String templateName = templateNameIterator.next();

			Mat segmentedTemplateBitMap = templateTable.get(templateName);
			// resize the contour according to the template size
			Size newSize = getResizeSize(currentShapeSubBitMap, segmentedTemplateBitMap.width());
			Mat resizedImage = new Mat();

			System.out.println("Resizing to " + newSize.width + " x " + newSize.height);
			Imgproc.resize(currentShapeSubBitMap, resizedImage, newSize);
			Highgui.imwrite("C:/Users/SARA/Desktop/opencv/sample/result/Shape" + shapeId + ".png",
					currentShapeSubBitMap);
			Highgui.imwrite("C:/Users/SARA/Desktop/opencv/sample/result/Resized" + shapeId + "-toSizeOf-"+templateName+".png", resizedImage);
			Highgui.imwrite("C:/Users/SARA/Desktop/opencv/sample/result/template" + templateName + ".png",
					segmentedTemplateBitMap);
			System.err.println("new width is:" + resizedImage.width() + " and new height is:" + resizedImage.height());

			Mat resizedImage1 = new Mat();
			resizedImage1 = resizedImage;

			System.err.println("********* COMPARING SHAPE " + shapeId + " and template: " + templateName);
			Mat biggerImage, smallerImage;
			if (resizedImage1.rows() > segmentedTemplateBitMap.rows()||
					resizedImage1.cols() > segmentedTemplateBitMap.cols()) {
				System.err.println("The image is bigger");
				// the image is bigger
				biggerImage = resizedImage1;
				smallerImage = segmentedTemplateBitMap;
			} else {
				// the template is bigger
				System.err.println("The template is bigger");
				biggerImage = segmentedTemplateBitMap;
				smallerImage = resizedImage1;
			}

			int result_cols = biggerImage.cols() - smallerImage.cols() + 1;
			int result_rows = biggerImage.rows() - smallerImage.rows() + 1;
			Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
			// these two method (Imgproc.TM_SQDIFF and Imgproc.TM_SQDIFF_NORMED)give the minimum value
			Imgproc.matchTemplate(biggerImage, smallerImage, result, Imgproc.TM_CCORR_NORMED); // TM_CCOEFF_NORMED
			Highgui.imwrite(
					"C:/Users/SARA/Desktop/opencv/sample/result/" + shapeId + "_vs_" + templateName + "-result.png",
					result);

			ArrayList<Double> listOfMaxVal = new ArrayList<>();
			//The functions minMaxLoc find the minimum and maximum element values and their positions
			MinMaxLocResult mmr = Core.minMaxLoc(result);
			results.add(mmr);
			Point matchLoc = mmr.maxLoc;
			double maxValue = mmr.maxVal;
			double minValue = mmr.minVal;


			System.err.println("********MinVal is: " + minValue);
			System.err.println("********MaxVal is: " + maxValue);
			listOfMaxVal.add(maxValue);
			double globalMaximimum = MaximumValue(listOfMaxVal);
			if (maxValue > 0) {
				System.out.println("max val***" + globalMaximimum);
				FindMatching findM = new FindMatching(segmentedTemplateBitMap, result, matchLoc, maxValue,
						templateName);
				returnValue.add(findM);
			}
		}
		return returnValue;

	}

	private double MaximumValue(ArrayList<Double> listOfMaxVal) {

		double maxValue = listOfMaxVal.get(0);
		for (int s = 0; s < listOfMaxVal.size(); s++) {
			if (listOfMaxVal.get(s) > maxValue)
				maxValue = listOfMaxVal.get(s);
		}
		return maxValue;
	}

	/**
	 * runMtchingDemo finds the template in original image
	 * 
	 * @param inFile
	 *            is original image
	 * @param templateFile
	 *            is the template image
	 * @param outFile
	 * @param match_method
	 */

	public void runMatchingDemo(String inFile) {

		System.out.println("\nRunning Template Matching");
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// find the contours in the input image
		SegmentedImage segmentedInputImage = segmentImage(inFile);
		Mat segmentedInputBitMap = segmentedInputImage.segmentedBitMap;
		ArrayList<MatOfPoint> contours = segmentedInputImage.contours;

		// let us now iterate over the different shapes/contours in the input image, trying to find a match in each case

		shapeCoordinates = new HashMap<String, ArrayList<CoordinatesOfContours>>();

		for (int i = 0; i < contours.size(); i++) {
			MatOfPoint currentShape = contours.get(i);
			Mat currentShapeSubBitMap = getShapeSubBitMap(segmentedInputBitMap, currentShape);
			int x = Imgproc.boundingRect(currentShape).x;
			int y = Imgproc.boundingRect(currentShape).y;


			// Doing the template matching and returning an array list of Mat with two values:
			// 1.The template that was compared to <code>currentShapeSubBitMap</code>
			// 2.The result comparison of <code>currentShapeSubBitMap</code> and <code>segmentedTemplateBitMap</code>
			ArrayList<FindMatching> results = findMatching(currentShapeSubBitMap, segmentedInputBitMap, i);

			FindMatching bestResult = computeBestResult(results);
			System.out.println("number of result" + results.size());

			// get the shape name
			String shapeName = bestResult.getTemplateName();
			System.out.println("this is the name of shape " + shapeName);
			// add the current match to the appropriate list of shapes
			ArrayList<CoordinatesOfContours> sameShapeCoordinateArray = shapeCoordinates.get(shapeName);

			// if array is empty (this is the first shape of this type encountered in the figure) then initialize it
			if (sameShapeCoordinateArray == null) {
				sameShapeCoordinateArray = new ArrayList<CoordinatesOfContours>();
				shapeCoordinates.put(shapeName, sameShapeCoordinateArray);
			}

			CoordinatesOfContours recognizedShapeCoordinates = new CoordinatesOfContours(bestResult.getMatchLoc().x + x,
					bestResult.getMatchLoc().y + y, bestResult.getSegmentedTemplateBitMap().cols(),
					bestResult.getSegmentedTemplateBitMap().rows(), nextId());

			// Just add the recognized square to the list if it does not overlap any other
			CalculateDistanceBetweenContours t = new CalculateDistanceBetweenContours();
			if (!t.isOverlapping(recognizedShapeCoordinates, sameShapeCoordinateArray)) {
				recognizedShapeCoordinates.setType(shapeName);
				sameShapeCoordinateArray.add(recognizedShapeCoordinates);
			}


		}

		System.err.println("-------- FINAL RESULT ----------");
		Set<String> tmpKeySet = shapeCoordinates.keySet();
		for(String key : tmpKeySet){
			System.err.println("- Shapes dectected as "+key+" -");
			ArrayList<CoordinatesOfContours> tmpCordinates = shapeCoordinates.get(key);
			for(CoordinatesOfContours coordinate : tmpCordinates){
				System.err.println(coordinate);
			}
		}
	}

	FindMatching computeBestResult(ArrayList<FindMatching> results){
		FindMatching bestResult = null;
		for(FindMatching currentResult : results){
			if(bestResult==null || currentResult.getMaxValue()>bestResult.getMaxValue()){
				bestResult = currentResult;
			}
		}
		return bestResult;
	}
}
