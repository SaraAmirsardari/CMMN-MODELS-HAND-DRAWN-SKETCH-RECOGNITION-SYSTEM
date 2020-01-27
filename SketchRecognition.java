
package CMMNElementsketchRecognitionSystem;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Range;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


/**
 *  This class composed of the main method, start listing all files 
 *  from the main directory and its sub directories and then calling the class <code>TemplateMatchingDemo</code>
 *  in order to use the template matching method in the OpenCV library and comparing each shape 
 *  of the original image to the template images that is specified in the main directory 
 *  and its sub directories. in the following the class of WriteXmlFile start writhing the XML file 
 *  according to the structure of CMMN modeler which is able to import the XML file inside the CMMN Modeler software
 *  @author SaraAmirsardari
 *
 */
public class SketchRecognition {

	public static final int LINE_DETECTION_TRESHOLD = 50;

	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// reading the folders and sub folders
		ReadFolders tc = new ReadFolders();
		File MainDirectory = new File("C:/Users/SARA/Desktop/opencv/sample/template100");
		ArrayList<String> pathsList = new ArrayList<>();
		pathsList = tc.readDir(MainDirectory);
		TemplateMatchingDemo md = new TemplateMatchingDemo();
		// define for loop in order to read the files with the suffix of JPG or PNG
		ArrayList<String> listOfTemplates=new ArrayList<>();
		for (int i = 0; i < pathsList.size(); i++) {
			if (pathsList.get(i).contains("png") || pathsList.get(i).contains("jpg")) {
				listOfTemplates.add(pathsList.get(i));
				System.out.println("file path name" +pathsList.get(i) );
			}
		}

		md.preprocessAllTemplates(listOfTemplates);

		String sketchFileName = "C:/Users/SARA/Desktop/opencv/sample/lines/test8.png";
		md.runMatchingDemo(sketchFileName);
		DetectLine dl = new DetectLine();
		dl.setInitialImage(md.getCleanedUpImage());
		dl.setShapesToRemove(md.getParentContrours());
		dl.detectLine();
		WriteXmlFile writeXmlShapes = new WriteXmlFile();
		writeXmlShapes.setResultOfLines(dl.getResultOfLines());
		writeXmlShapes.WriteXml(md);



	}
}

