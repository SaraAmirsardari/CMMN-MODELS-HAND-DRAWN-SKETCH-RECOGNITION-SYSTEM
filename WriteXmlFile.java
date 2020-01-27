package CMMNElementsketchRecognitionSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.opencv.core.Mat;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * this class start writhing the XML file according to the structure of CMMN modeler 
 * which is able to import the XML file inside the CMMN Modeler software
 * @author SaraAirsardari
 *
 */
public class WriteXmlFile {

	private Map<CoordinatesOfContours, CoordinatesOfContours> connectionsMap = new HashMap<>();
	private Map<CoordinatesOfContours, CoordinatesOfLines> shapesToLines = new HashMap<>();

	private ArrayList<CoordinatesOfContours> resultOfTasks= new ArrayList<>();
	public ArrayList<CoordinatesOfContours> getResultOfTasks(){
		System.err.println("ARRAY LIST OF TASKS-------------------"+ resultOfTasks);
		return resultOfTasks;	
	}

	private ArrayList<CoordinatesOfContours> resultOfSentries= new ArrayList<>();
	public ArrayList<CoordinatesOfContours> getResultOfSentries(){
		System.err.println("ARRAY LIST OF SENTRIES-------------------"+ resultOfSentries);
		return resultOfSentries;	
	}

	private ArrayList<CoordinatesOfLines> resultOfLines= new ArrayList<>();
	public ArrayList<CoordinatesOfLines> getResultOfLines() {
		return resultOfLines;
	}

	public void setResultOfLines(ArrayList<CoordinatesOfLines> resultOfLines) {
		this.resultOfLines = resultOfLines;
	}


	public void WriteXml(TemplateMatchingDemo md) {


		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);
			Element rootElement = doc.createElement("cmmn:definitions");
			doc.appendChild(rootElement);

			// staff elements

			Element staff1 = doc.createElement("cmmn:caseFileItemDefinition");
			rootElement.appendChild(staff1);

			Element staff = doc.createElement("cmmn:case");
			rootElement.appendChild(staff);

			Element staff2 = doc.createElement("cmmndi:CMMNDI");
			rootElement.appendChild(staff2);



			//set attribute for root element

			Attr defv1 = doc.createAttribute("author");
			defv1.setValue("");
			rootElement.setAttributeNode(defv1);

			Attr defv2 = doc.createAttribute("exporter");
			defv2.setValue("CMMN Modeler");
			rootElement.setAttributeNode(defv2);

			Attr defv3 = doc.createAttribute("id");
			defv3.setValue("_bcc573eb-adf3-4fb4-abb5-434ae50ac5ce");
			rootElement.setAttributeNode(defv3);

			Attr defv4 = doc.createAttribute("name");
			defv4.setValue("Drawing 1");
			rootElement.setAttributeNode(defv4);

			Attr defv5 = doc.createAttribute("targetNamespace");
			defv5.setValue("http://www.trisotech.com/cmmn/definitions/_bcc573eb-adf3-4fb4-abb5-434ae50ac5ce");
			rootElement.setAttributeNode(defv5);

			Attr defv6 = doc.createAttribute("xmlns");
			defv6.setValue("http://www.trisotech.com/cmmn/definitions/_bcc573eb-adf3-4fb4-abb5-434ae50ac5ce");
			rootElement.setAttributeNode(defv6);

			Attr defv7 = doc.createAttribute("xmlns:dc");
			defv7.setValue("http://www.omg.org/spec/CMMN/20151109/DC");
			rootElement.setAttributeNode(defv7);

			Attr defv8 = doc.createAttribute("xmlns:trisofeed");
			defv8.setValue("http://trisotech.com/feed");
			rootElement.setAttributeNode(defv8);

			Attr defv9 = doc.createAttribute("xmlns:triso");
			defv9.setValue("http://www.trisotech.com/2015/triso/modeling");
			rootElement.setAttributeNode(defv9);

			Attr defv10 = doc.createAttribute("xmlns:di");
			defv10.setValue("http://www.omg.org/spec/CMMN/20151109/DI");
			rootElement.setAttributeNode(defv10);

			Attr defv11 = doc.createAttribute("xmlns:rss");
			defv11.setValue("http://purl.org/rss/2.0/");
			rootElement.setAttributeNode(defv11);

			Attr defv12 = doc.createAttribute("xmlns:cmmndi");
			defv12.setValue("http://www.omg.org/spec/CMMN/20151109/CMMNDI");
			rootElement.setAttributeNode(defv12);

			Attr defv13 = doc.createAttribute("xmlns:trisob");
			defv13.setValue("http://www.trisotech.com/2014/triso/bpmn");
			rootElement.setAttributeNode(defv13);

			Attr defv14 = doc.createAttribute("xmlns:cmmn");
			defv14.setValue("http://www.omg.org/spec/CMMN/20151109/MODEL");
			rootElement.setAttributeNode(defv14);

			Attr defv15 = doc.createAttribute("xmlns:xsi");
			defv15.setValue("http://www.w3.org/2001/XMLSchema-instance");
			rootElement.setAttributeNode(defv15);

			Attr defv16 = doc.createAttribute("xmlns:trisocmmn");
			defv16.setValue("http://www.trisotech.com/2014/triso/cmmn");
			rootElement.setAttributeNode(defv16);

			//finish set attribute for root element

			// get the coordinate of contours which are matched with the template's images

			ArrayList<CoordinatesOfContours> Filelist = md.getListOfCoordinatesOfShapesOfType("file");
			if(Filelist==null) Filelist = new ArrayList<CoordinatesOfContours>();

			ArrayList<CoordinatesOfContours> squarelist = md.getListOfCoordinatesOfShapesOfType("task");
			if(squarelist==null) squarelist = new ArrayList<CoordinatesOfContours>();

			this.resultOfTasks=squarelist;


			ArrayList<CoordinatesOfContours> Sentrieslist = md.getListOfCoordinatesOfShapesOfType("sentry");
			if(Sentrieslist==null) Sentrieslist = new ArrayList<CoordinatesOfContours>();


			ArrayList<CoordinatesOfContours> Eventlist = md.getListOfCoordinatesOfShapesOfType("event");
			if(Eventlist==null) Eventlist = new ArrayList<CoordinatesOfContours>();


			System.out.println(Filelist);

			// set attribute to staff1 element(caseFileItemDefinition)
			for (CoordinatesOfContours recognizefile : Filelist) {
				writeFileItemDefinition(doc,staff1, recognizefile);	
			}

			// set attribute to staff element(case)
			Attr attr = doc.createAttribute("id");
			attr.setValue("Case_3b0a4c03-c271-47c3-9e87-30c57c034fdb");
			staff.setAttributeNode(attr);

			Attr attr1 = doc.createAttribute("name");
			attr1.setValue("Page 1");
			staff.setAttributeNode(attr1);


			// set attribute to casefilemodel
			Element casefilemodel = doc.createElement("cmmn:caseFileModel");
			staff.appendChild(casefilemodel);

			for (CoordinatesOfContours recognizefile : Filelist) {
				writeFileItem(doc, casefilemodel, recognizefile);	
			}

			Element caseplanmodel = doc.createElement("cmmn:casePlanModel");
			staff.appendChild(caseplanmodel);

			// set attribute to caseplanmodel element
			Attr caseplan = doc.createAttribute("id");
			caseplan.setValue("_3b0a4c03-c271-47c3-9e87-30c57c034fdb");
			caseplanmodel.setAttributeNode(caseplan);

			Attr caseplan1 = doc.createAttribute("autoComplete");
			caseplan1.setValue("false");
			caseplanmodel.setAttributeNode(caseplan1);

			Attr caseplan2 = doc.createAttribute("name");
			caseplan2.setValue("Page 1");
			caseplanmodel.setAttributeNode(caseplan2);



			// calculate if there is an intersection between square and diamond or not

			ArrayList <CoordinatesOfContours> intersectionSentries= new ArrayList<>();
			for (CoordinatesOfContours coordinatesOfSquare : squarelist) {
				// define the list of sentries that have intersection with squares

				for (CoordinatesOfContours coordinatesOfSentries : Sentrieslist) {
					if(CalculateIntersectionArea.recognizeIntersection(coordinatesOfSentries,coordinatesOfSquare)){
						intersectionSentries.add(coordinatesOfSentries);
						System.err.println("---------intersectionSentrie----------"+ intersectionSentries);
					}
				}

				this.resultOfSentries=intersectionSentries;
				writeplanItem(doc, caseplanmodel, coordinatesOfSquare, intersectionSentries, null);
			}


			//define the connector and the shapes connected to it

			CalculateDistanceBetweenContourAndLine connector=new CalculateDistanceBetweenContourAndLine();
			connector.setResultOfLines(this.resultOfLines);
			connector.setResultOfSentries(this.getResultOfSentries());
			connector.setResultOfTasks(this.getResultOfTasks());

			for(CoordinatesOfLines line : this.resultOfLines){
				List<CoordinatesOfContours> shapes = connector.getCloserShapeForLine(line);
				connectionsMap.put(shapes.get(0), shapes.get(1));
				connectionsMap.put(shapes.get(1), shapes.get(0));
				shapesToLines.put(shapes.get(0), line);
				shapesToLines.put(shapes.get(1), line);
			}	

			//writhing  planItem element 
			for (CoordinatesOfContours coordinationEvent : Eventlist) {
				writeplanItem(doc, caseplanmodel, null, null, coordinationEvent);	
			}
			//writhing Sentry element
			for (CoordinatesOfContours coordinationSentries : this.resultOfSentries) {

				writeSentry(doc, caseplanmodel, coordinationSentries,connectionsMap.get(coordinationSentries),shapesToLines.get(coordinationSentries));
			}

			//writhing  Event element
			for (CoordinatesOfContours coordinationEvent : Eventlist) {
				writeEvent(doc, caseplanmodel, coordinationEvent);
			}
			//writhing Task element
			for (CoordinatesOfContours coordinationSquare : squarelist) {
				writeTask(doc, caseplanmodel, coordinationSquare);
			}


			//writhing CMMN Diagram

			Element CMMNDiagram = doc.createElement("cmmndi:CMMNDiagram");
			staff2.appendChild(CMMNDiagram);

			// set attribute 
			Attr Diagramv1 = doc.createAttribute("id");
			Diagramv1.setValue("_180025a0-f126-4805-8689-7ee0a0f3c190");
			CMMNDiagram.setAttributeNode(Diagramv1);

			Attr Diagramv2 = doc.createAttribute("name");
			Diagramv2.setValue("Page 1");
			CMMNDiagram.setAttributeNode(Diagramv2);

			Attr Diagramv3 = doc.createAttribute("sharedStyle");
			Diagramv3.setValue("cb1a46a0-82e9-4c14-8495-8d3f50061e96");
			CMMNDiagram.setAttributeNode(Diagramv3);

			//writhing the size as child of CMMN Diagram

			Element cmmndiSize = doc.createElement("cmmndi:Size");
			CMMNDiagram.appendChild(cmmndiSize);
			// set attribute 
			Attr Sizev1 = doc.createAttribute("height");
			Sizev1.setValue("1050.0");
			cmmndiSize.setAttributeNode(Sizev1);

			Attr Sizev2 = doc.createAttribute("width");
			Sizev2.setValue("1485.0");
			cmmndiSize.setAttributeNode(Sizev2);

			//writhing the shape as child of CMMN Diagram

			Element CMMNShape = doc.createElement("cmmndi:CMMNShape");
			CMMNDiagram.appendChild(CMMNShape);


			// set attribute 
			Attr Shapev1 = doc.createAttribute("cmmnElementRef");
			Shapev1.setValue("_3b0a4c03-c271-47c3-9e87-30c57c034fdb");
			CMMNShape.setAttributeNode(Shapev1);

			Attr Shapev2 = doc.createAttribute("id");
			Shapev2.setValue("_d8d81e5a-d265-4ba1-9f94-4b0d47037451");
			CMMNShape.setAttributeNode(Shapev2);

			Element dcBounds  = doc.createElement("dc:Bounds");
			CMMNShape.appendChild(dcBounds);

			Attr boundv1 = doc.createAttribute("height");
			boundv1.setValue("600.0");
			dcBounds.setAttributeNode(boundv1);

			Attr boundv2 = doc.createAttribute("width");
			boundv2.setValue("800.0");
			dcBounds.setAttributeNode(boundv2);

			Attr boundv3 = doc.createAttribute("x");
			boundv3.setValue("34.0");
			dcBounds.setAttributeNode(boundv3);

			Attr boundv4 = doc.createAttribute("y");
			boundv4.setValue("34.0");
			dcBounds.setAttributeNode(boundv4);


			Element cmmndiCMMNLabe  = doc.createElement("cmmndi:CMMNLabel");
			CMMNShape.appendChild(cmmndiCMMNLabe);


			for(CoordinatesOfContours coordinateOfSentry : this.resultOfSentries){
				System.err.println("sentry y y: " + coordinateOfSentry);
				CoordinatesOfLines line =  shapesToLines.get(coordinateOfSentry);
				System.err.println("line e e e: " + line);
				writeLineValus(doc, CMMNDiagram, line, coordinateOfSentry);
			}


			for (CoordinatesOfContours coordinatesOfSquare : squarelist ) {
				writetaskValues(doc, CMMNDiagram, coordinatesOfSquare);
			}
			for (CoordinatesOfContours coordinatesOfSentries : Sentrieslist ) {
				writeEntryCriterionValus(doc, CMMNDiagram, coordinatesOfSentries);
			}
			for (CoordinatesOfContours coordinationOfEvent : Eventlist ) {
				writeEventValues(doc, CMMNDiagram, coordinationOfEvent);
			}

			for (CoordinatesOfContours coordinatesOfFile : Filelist) {
				writeFileValues(doc, CMMNDiagram, coordinatesOfFile);
			}
			//writhing the style as child of CMMN Diagram
			Element cmmndiStyle = doc.createElement("cmmndi:CMMNStyle");
			staff2.appendChild(cmmndiStyle);

			// set attribute 
			Attr Stylev1 = doc.createAttribute("fontFamily");
			Stylev1.setValue("Arial,Helvetica,sans-serif");
			cmmndiStyle.setAttributeNode(Stylev1);

			Attr Stylev2 = doc.createAttribute("id");
			Stylev2.setValue("cb1a46a0-82e9-4c14-8495-8d3f50061e96");
			cmmndiStyle.setAttributeNode(Stylev2);
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("C:/Users/SARA/Desktop/opencv/result.cmmn"));
			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	public void writeFileItemDefinition(Document doc,Element staff1, CoordinatesOfContours recognizefile){

		Attr planitemv1 = doc.createAttribute("id");
		planitemv1.setValue("fr"+ recognizefile.getid());
		staff1.setAttributeNode(planitemv1);

	}


	public void writeFileItem(Document doc,Element casefilemodel, CoordinatesOfContours recognizefile){

		Element cmmncaseFileItem = doc.createElement("cmmn:caseFileItem");
		casefilemodel.appendChild(cmmncaseFileItem);

		//set attribute
		Attr fileitemv1 = doc.createAttribute("definitionRef");
		fileitemv1.setValue("fr" + recognizefile.getid());
		cmmncaseFileItem.setAttributeNode(fileitemv1);

		Attr fileitemv2 = doc.createAttribute("multiplicity");
		fileitemv2.setValue("Unspecified");
		cmmncaseFileItem.setAttributeNode(fileitemv2);

		Attr fileitemv3 = doc.createAttribute("id");
		fileitemv3.setValue("fv" + recognizefile.getid());
		cmmncaseFileItem.setAttributeNode(fileitemv3);

	}


	public void writeplanItem(Document doc,Element caseplanmodel, CoordinatesOfContours coordinatesOfSquare,ArrayList<CoordinatesOfContours> intersectionSentries,CoordinatesOfContours  coordinationEvent){

		Element cmmnplanItem = doc.createElement("cmmn:planItem");
		caseplanmodel.appendChild(cmmnplanItem);

		// set attribute 

		Attr planitemv1 = doc.createAttribute("definitionRef");

		if(coordinatesOfSquare!= null){
			planitemv1.setValue("t" + coordinatesOfSquare.getid());
		}else if(coordinationEvent != null){
			planitemv1.setValue("e" + coordinationEvent.getid());
		}
		cmmnplanItem.setAttributeNode(planitemv1);

		Attr planitemv2 = doc.createAttribute("id");
		if(coordinatesOfSquare!= null){
			planitemv2.setValue("pi" + coordinatesOfSquare.getid() );
		} else if(coordinationEvent != null){
			planitemv2.setValue("pi" + coordinationEvent.getid() );
		}
		cmmnplanItem.setAttributeNode(planitemv2);


		if(intersectionSentries!=null){
			if(!intersectionSentries.isEmpty()){
				for (CoordinatesOfContours coordinationSentries : intersectionSentries) {

					Element entryCriterion = doc.createElement("cmmn:entryCriterion");
					cmmnplanItem.appendChild(entryCriterion);

					Attr entryCriterionv1 = doc.createAttribute("sentryRef");
					entryCriterionv1.setValue("senR" + coordinationSentries.getid());
					entryCriterion.setAttributeNode(entryCriterionv1);

					Attr entryCriterionv2 = doc.createAttribute("id");
					entryCriterionv2.setValue("Rsen"+coordinationSentries.getid());
					entryCriterion.setAttributeNode(entryCriterionv2);
				}
			}
		}
	}



	/**
	 * This method write the eventListener 
	 * @param doc
	 * @param caseplanmodel
	 * @param coordinationEvent
	 */
	public void writeEvent(Document doc,Element caseplanmodel, CoordinatesOfContours coordinationEvent){
		Element eventListener = doc.createElement("cmmn:eventListener");
		caseplanmodel.appendChild(eventListener);
		
		// set attribute 
		Attr cmmneventListenerv1 = doc.createAttribute("id");
		cmmneventListenerv1.setValue("e" + coordinationEvent.getid());
		eventListener.setAttributeNode(cmmneventListenerv1);
	}

	/**
	 * This method write the Task
	 * @param doc
	 * @param caseplanmodel
	 * @param recognizesquare
	 */

	public void writeTask(Document doc,Element caseplanmodel, CoordinatesOfContours recognizesquare){
		Element cmmntask = doc.createElement("cmmn:task");
		caseplanmodel.appendChild(cmmntask);
		// set attribute 
		Attr cmmntaskv1 = doc.createAttribute("isBlocking");
		cmmntaskv1.setValue("true");
		cmmntask.setAttributeNode(cmmntaskv1);

		Attr cmmntaskv2 = doc.createAttribute("id");
		cmmntaskv2.setValue("t" + recognizesquare.getid());
		cmmntask.setAttributeNode(cmmntaskv2);
	}


	/**
	 * This method write the Sentry as well as the connection to connector
	 * @param doc
	 * @param caseplanmodel
	 * @param coordinationSentries
	 * @param coordinatesOfSquare
	 * @param line
	 */
	public void writeSentry(Document doc,Element caseplanmodel, CoordinatesOfContours coordinationSentries,CoordinatesOfContours coordinatesOfSquare,CoordinatesOfLines line){

		System.err.println("taskkkkkk"+ coordinatesOfSquare);
		System.err.println("sentryyyyyyy"+coordinationSentries);
		System.err.println("lineeeeeeee"+ line);
		Element cmmnsentry = doc.createElement("cmmn:sentry");
		caseplanmodel.appendChild(cmmnsentry);

		if(coordinationSentries!=null){

			Attr sentryva1 = doc.createAttribute("id");
			sentryva1.setValue("senR" + coordinationSentries.getid());
			cmmnsentry.setAttributeNode(sentryva1);
		}

		Element cmmnplanItemOnPart = doc.createElement("cmmn:planItemOnPart");
		cmmnsentry.appendChild(cmmnplanItemOnPart);

		if(coordinatesOfSquare!= null){
			Attr planItemOnPart = doc.createAttribute("sourceRef");
			planItemOnPart.setValue("pi" +coordinatesOfSquare.getid());
			cmmnplanItemOnPart.setAttributeNode(planItemOnPart);
		}

		if(line!= null){
			Attr planItemOnPart1 = doc.createAttribute("id");
			planItemOnPart1.setValue("line"+line.getid());
			cmmnplanItemOnPart.setAttributeNode(planItemOnPart1);
		}

		Element cmmnifpart = doc.createElement("cmmn:ifPart");
		cmmnsentry.appendChild(cmmnifpart);
		// set attribute 
		Attr ifpartv1 = doc.createAttribute("id");
		ifpartv1.setValue("ifpa"+coordinationSentries.getid());
		cmmnifpart.setAttributeNode(ifpartv1);

	}


	/**
	 * This method writes Task specifications
	 * @param doc
	 * @param CMMNDiagram
	 * @param coordinatesOfSquare
	 */
	public void writetaskValues(Document doc, Element CMMNDiagram, CoordinatesOfContours coordinatesOfSquare){
		Element CMMNShape2 = doc.createElement("cmmndi:CMMNShape");
		CMMNDiagram.appendChild(CMMNShape2);

		// set attribute 
		Attr Shape2v1 = doc.createAttribute("cmmnElementRef");
		Shape2v1.setValue("pi"+coordinatesOfSquare.getid() );
		CMMNShape2.setAttributeNode(Shape2v1);

		Attr Shape2v2 = doc.createAttribute("id");
		Shape2v2.setValue("sh"+ coordinatesOfSquare.getid() );
		CMMNShape2.setAttributeNode(Shape2v2);


		Element dcBounds1  = doc.createElement("dc:Bounds");
		CMMNShape2.appendChild(dcBounds1);

		Attr bound2v1 = doc.createAttribute("height");
		bound2v1.setValue(Double.toString(coordinatesOfSquare.getHeight()));
		dcBounds1.setAttributeNode(bound2v1);

		Attr bound2v2 = doc.createAttribute("width");
		bound2v2.setValue(Double.toString(coordinatesOfSquare.getWidth()));
		dcBounds1.setAttributeNode(bound2v2);

		Attr bound2v3 = doc.createAttribute("x");
		bound2v3.setValue(Double.toString(coordinatesOfSquare.getX()));
		dcBounds1.setAttributeNode(bound2v3);

		Attr bound2v4 = doc.createAttribute("y");
		bound2v4.setValue(Double.toString(coordinatesOfSquare.getY()));
		dcBounds1.setAttributeNode(bound2v4);


		Element cmmndiCMMNLabel2  = doc.createElement("cmmndi:CMMNLabel");
		CMMNShape2.appendChild(cmmndiCMMNLabel2);

	}


	/**
	 * This method writes Event specifications
	 * @param doc
	 * @param CMMNDiagram
	 * @param coordinationOfEvent
	 */
	public void writeEventValues(Document doc, Element CMMNDiagram, CoordinatesOfContours coordinationOfEvent){
		Element CMMNShape2 = doc.createElement("cmmndi:CMMNShape");
		CMMNDiagram.appendChild(CMMNShape2);

		// set attribute 
		Attr Shape2v1 = doc.createAttribute("cmmnElementRef");
		Shape2v1.setValue("pi"+coordinationOfEvent.getid() );
		CMMNShape2.setAttributeNode(Shape2v1);

		Attr Shape2v2 = doc.createAttribute("id");
		Shape2v2.setValue("sh"+ coordinationOfEvent.getid() );
		CMMNShape2.setAttributeNode(Shape2v2);


		Element dcBounds1  = doc.createElement("dc:Bounds");
		CMMNShape2.appendChild(dcBounds1);

		Attr bound2v1 = doc.createAttribute("height");
		bound2v1.setValue(Double.toString(coordinationOfEvent.getHeight()));
		dcBounds1.setAttributeNode(bound2v1);

		Attr bound2v2 = doc.createAttribute("width");
		bound2v2.setValue(Double.toString(coordinationOfEvent.getWidth()));
		dcBounds1.setAttributeNode(bound2v2);

		Attr bound2v3 = doc.createAttribute("x");
		bound2v3.setValue(Double.toString(coordinationOfEvent.getX()));
		dcBounds1.setAttributeNode(bound2v3);

		Attr bound2v4 = doc.createAttribute("y");
		bound2v4.setValue(Double.toString(coordinationOfEvent.getY()));
		dcBounds1.setAttributeNode(bound2v4);


		Element cmmndiCMMNLabel2  = doc.createElement("cmmndi:CMMNLabel");
		CMMNShape2.appendChild(cmmndiCMMNLabel2);

	}

	/**
	 * This method writes sentry specifications
	 * @param doc
	 * @param CMMNDiagram
	 * @param coordinatesOfSentries
	 */
	public void writeEntryCriterionValus(Document doc, Element CMMNDiagram, CoordinatesOfContours coordinatesOfSentries){
		Element CMMNShape2 = doc.createElement("cmmndi:CMMNShape");
		CMMNDiagram.appendChild(CMMNShape2);

		// set attribute 
		Attr Shape2v1 = doc.createAttribute("cmmnElementRef");
		Shape2v1.setValue("Rsen"+coordinatesOfSentries.getid() );
		CMMNShape2.setAttributeNode(Shape2v1);

		Attr Shape2v2 = doc.createAttribute("id");
		Shape2v2.setValue("sh"+ coordinatesOfSentries.getid() );
		CMMNShape2.setAttributeNode(Shape2v2);


		Element dcBounds1  = doc.createElement("dc:Bounds");
		CMMNShape2.appendChild(dcBounds1);

		Attr bound2v1 = doc.createAttribute("height");
		bound2v1.setValue(Double.toString(coordinatesOfSentries.getHeight()));
		dcBounds1.setAttributeNode(bound2v1);

		Attr bound2v2 = doc.createAttribute("width");
		bound2v2.setValue(Double.toString(coordinatesOfSentries.getWidth()));
		dcBounds1.setAttributeNode(bound2v2);

		Attr bound2v3 = doc.createAttribute("x");
		bound2v3.setValue(Double.toString(coordinatesOfSentries.getX()));
		dcBounds1.setAttributeNode(bound2v3);

		Attr bound2v4 = doc.createAttribute("y");
		bound2v4.setValue(Double.toString(coordinatesOfSentries.getY()));
		dcBounds1.setAttributeNode(bound2v4);


		Element cmmndiCMMNLabel2  = doc.createElement("cmmndi:CMMNLabel");
		CMMNShape2.appendChild(cmmndiCMMNLabel2);

	}

	/**
	 * This method writes Line specifications
	 * @param doc
	 * @param CMMNDiagram
	 * @param line
	 * @param coordinatesOfSentries
	 */
	public void writeLineValus(Document doc, Element CMMNDiagram, CoordinatesOfLines line,CoordinatesOfContours coordinatesOfSentries){

		System.out.println("show me lines: " + line);
		Element CMMNShape2 = doc.createElement("cmmndi:CMMNEdge");
		CMMNDiagram.appendChild(CMMNShape2);

		// set attribute 
		Attr Shape2v1 = doc.createAttribute("cmmnElementRef");
		Shape2v1.setValue("line"+line.getid());
		CMMNShape2.setAttributeNode(Shape2v1);

		Attr Shape2v2 = doc.createAttribute("id");
		Shape2v2.setValue("li"+ line.getid() );
		CMMNShape2.setAttributeNode(Shape2v2);

		Attr Shape2v3 = doc.createAttribute("targetCMMNElementRef");
		Shape2v3.setValue("Rsen"+coordinatesOfSentries.getid() );
		CMMNShape2.setAttributeNode(Shape2v3);


		Attr Shape2v4 = doc.createAttribute("isStandardEventVisible");
		Shape2v4.setValue("true" );
		CMMNShape2.setAttributeNode(Shape2v4);

		Element diwaypoint  = doc.createElement("di:waypoint");
		CMMNShape2.appendChild(diwaypoint);

		Attr bound2v1 = doc.createAttribute("x");
		bound2v1.setValue(Double.toString(line.getX1()));
		diwaypoint.setAttributeNode(bound2v1);

		Attr bound2v2 = doc.createAttribute("y");
		bound2v2.setValue(Double.toString(line.getY1()));
		diwaypoint.setAttributeNode(bound2v2);

		Element diwaypoint2  = doc.createElement("di:waypoint");
		CMMNShape2.appendChild(diwaypoint2);

		Attr bound2v3 = doc.createAttribute("x");
		bound2v3.setValue(Double.toString(line.getX2()));
		diwaypoint2.setAttributeNode(bound2v3);

		Attr bound2v4 = doc.createAttribute("y");
		bound2v4.setValue(Double.toString(line.getY2()));
		diwaypoint2.setAttributeNode(bound2v4);


		Element cmmndiCMMNLabel2  = doc.createElement("cmmndi:CMMNLabel");
		CMMNShape2.appendChild(cmmndiCMMNLabel2);

	}

	/**
	 * This method writes File specifications
	 * @param doc
	 * @param CMMNDiagram
	 * @param coordinatesOfFile
	 */
	public void writeFileValues(Document doc, Element CMMNDiagram, CoordinatesOfContours coordinatesOfFile){
		Element CMMNShape2 = doc.createElement("cmmndi:CMMNShape");
		CMMNDiagram.appendChild(CMMNShape2);

		// set attribute 
		Attr Shape2v1 = doc.createAttribute("cmmnElementRef");
		Shape2v1.setValue("fv"+coordinatesOfFile.getid());
		CMMNShape2.setAttributeNode(Shape2v1);

		Attr Shape2v2 = doc.createAttribute("id");
		Shape2v2.setValue("sf"+ coordinatesOfFile.getid());
		CMMNShape2.setAttributeNode(Shape2v2);


		Element dcBounds1  = doc.createElement("dc:Bounds");
		CMMNShape2.appendChild(dcBounds1);

		Attr bound2v1 = doc.createAttribute("height");
		bound2v1.setValue(Double.toString(coordinatesOfFile.getHeight()));
		dcBounds1.setAttributeNode(bound2v1);

		Attr bound2v2 = doc.createAttribute("width");
		bound2v2.setValue(Double.toString(coordinatesOfFile.getWidth()));
		dcBounds1.setAttributeNode(bound2v2);

		Attr bound2v3 = doc.createAttribute("x");
		bound2v3.setValue(Double.toString(coordinatesOfFile.getX()));
		dcBounds1.setAttributeNode(bound2v3);

		Attr bound2v4 = doc.createAttribute("y");
		bound2v4.setValue(Double.toString(coordinatesOfFile.getY()));
		dcBounds1.setAttributeNode(bound2v4);


		Element cmmndiCMMNLabel2  = doc.createElement("cmmndi:CMMNLabel");
		CMMNShape2.appendChild(cmmndiCMMNLabel2);

	}
}


