package org.y3.jrun.storage.webxml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.competition.RichCompetition;

public abstract class ModelImporter {

	public void fillModel(Node submission, Model model, RichCompetition sourceCompetition) throws ParseException {
		fillCreationDate(submission, model);
		NodeList fields = submission.getChildNodes();
		int fieldCount = fields.getLength();
		for (int fieldNo = 0; fieldNo < fieldCount; fieldNo++) {
			Node field = fields.item(fieldNo);
			String nodeName = field.getNodeName();
			if (nodeName.equals(KeywordsDictionary.REGISTRATIONS_XML_FIELD)) {
				String fieldType = field.getAttributes().getNamedItem(KeywordsDictionary.REGISTRATIONS_XML_FIELD_NAME).getNodeValue();
				Node fieldValueNode = field.getFirstChild();
				String fieldValue = null;
				if (fieldValueNode != null) {
					fieldValue = field.getFirstChild().getNodeValue();
				}
				fillModelByType(fieldType, fieldValue, model, sourceCompetition);
			}
		}
	}
	
	protected abstract void fillModelByType(String fieldType, String fieldValue, Model model, RichCompetition sourceCompetition);
	
	private static void fillCreationDate(Node submission, Model model) throws ParseException {
		String submissionDateString = submission.getAttributes().getNamedItem(
				KeywordsDictionary.REGISTRATIONS_XML_DATE).getNodeValue();
		Date submissionDate = new SimpleDateFormat(
				KeywordsDictionary.REGISTRATIONS_XML_DATEFORMAT).parse(submissionDateString);
		String submissionTimeString = submission.getAttributes().getNamedItem(
				KeywordsDictionary.REGISTRATIONS_XML_TIME).getNodeValue();
		Date submissionTime = new SimpleDateFormat(
				KeywordsDictionary.REGISTRATIONS_XML_TIMEFORMAT).parse(submissionTimeString);
		GregorianCalendar time = new GregorianCalendar();
		time.setTime(submissionTime);
		GregorianCalendar date = new GregorianCalendar();
		date.setTime(submissionDate);
		GregorianCalendar calendar = new GregorianCalendar(
				date.get(Calendar.YEAR),
				date.get(Calendar.MONTH),
				date.get(Calendar.DAY_OF_MONTH),
				time.get(Calendar.HOUR_OF_DAY),
				time.get(Calendar.MINUTE),
				time.get(Calendar.SECOND));
		model.setCreationDate(calendar.getTime());
	}
	
	public abstract Model getNewModel();
	
	public abstract Model[] getNewModelArray(int size);
	
}
