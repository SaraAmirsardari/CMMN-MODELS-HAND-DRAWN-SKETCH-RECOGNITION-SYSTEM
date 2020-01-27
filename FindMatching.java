package CMMNElementsketchRecognitionSystem;



import org.opencv.core.Mat;
import org.opencv.core.Point;

/** this class represents:
 * 1. The templates that were already converted from string to Mat and stored in <code>segmentedTemplateBitMap</code> 
 * 2. The result that was already received from template matching method in order to compare the template and contour together
 * 3. The match location and maximum value for each result that were already defined by the <code>matchLoc</code> and  <code>maxValue</code> 
 * 4. The <code>idTemplate</code> is specified for identifying each template 
 * @author SaraAmirsardari
 *
 */

public class FindMatching {

	Mat segmentedTemplateBitMap;
	Mat result ;
	Point matchLoc;
	double maxValue;
	String templateName;

	public FindMatching(Mat segmentedTemplateBitMap, Mat result,Point matchLoc,double maxValue,String templateName ) {
		this.segmentedTemplateBitMap = segmentedTemplateBitMap;
		this.result = result;
		this.matchLoc=matchLoc;
		this.maxValue=maxValue;
		this.templateName=templateName;
	}

	public void setSegmentedTemplateBitMap(Mat segmentedTemplateBitMap) {
		this.segmentedTemplateBitMap = segmentedTemplateBitMap;
	}
	public Mat getSegmentedTemplateBitMap() {
		return segmentedTemplateBitMap;
	}

	public void setResult(Mat result) {
		this.result = result;
	}
	public Mat getResult() {
		return result;
	}

	public void setMatchLoc(Point matchLoc) {
		this.matchLoc = matchLoc;
	}
	public Point getMatchLoc() {
		return matchLoc;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}
	public Double getMaxValue() {
		return maxValue;
	}
	public void setTemplateName(String templateName) {

		this.templateName=templateName;
	}
	public String getTemplateName() {
		return templateName;
	}

}