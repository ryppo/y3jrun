package org.y3.jrun.view.reporting;

import java.util.HashMap;

import net.sf.jasperreports.engine.JRDataSource;

import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.report.Report;
import org.y3.jrun.view.i18n.Messages;

public class ReportFactory {

	public static Report createCompetitionListReport(JRDataSource reportableData) {
		Report report = new Report(
				Messages.getString(ReportsDictionary.COMPETITION_LIST),
				ReportsDictionary.COMPETITION_LIST, null, reportableData);
		return report;
	}

	public static Report createModelListReport(JRDataSource reportableData) {
		Report report = new Report(
				Messages.getString(ReportsDictionary.MODEL_LIST),
				ReportsDictionary.MODEL_LIST, null, reportableData);
		return report;
	}

	public static Report createNotificationListReport(
			JRDataSource reportableData) {
		Report report = new Report(
				Messages.getString(ReportsDictionary.NOTIFICATION_LIST),
				ReportsDictionary.NOTIFICATION_LIST, null, reportableData);
		return report;
	}

	public static Report createDisciplineStartGridReport(
			JRDataSource reportableData) {
		Report report = new Report(
				Messages.getString(ReportsDictionary.DISCIPLINE_STARTGRID),
				ReportsDictionary.DISCIPLINE_STARTGRID, null, reportableData);
		return report;
	}
	
	public static Report createDisciplineGenderStartGridReport(
			JRDataSource reportableData, HashMap<String, Object> parameters) {
		Report report = new Report(
				Messages.getString(ReportsDictionary.DISCIPLINE_GENDER_STARTGRID),
				ReportsDictionary.DISCIPLINE_GENDER_STARTGRID, parameters, reportableData);
		return report;
	}
	
	public static Report createDisciplineAgeClassStartGridReport(
			JRDataSource reportableData, HashMap<String, Object> parameters) {
		Report report = new Report(
				Messages.getString(ReportsDictionary.DISCIPLINE_AGECLASS_STARTGRID),
				ReportsDictionary.DISCIPLINE_AGECLASS_STARTGRID, parameters, reportableData, true);
		return report;
	}
	
	public static Report createDisciplineGenderAgeClassStartGridReport(
			JRDataSource reportableData, HashMap<String, Object> parameters) {
		Report report = new Report(
				Messages.getString(ReportsDictionary.DISCIPLINE_GENDER_AGECLASS_STARTGRID),
				ReportsDictionary.DISCIPLINE_GENDER_AGECLASS_STARTGRID, parameters, reportableData, true);
		return report;
	}

	public static Report createCertificationReport(JRDataSource reportableData,
			Contact participant) {
		String extension = "";
		if (participant != null && participant.toString() != null && participant.toString().length() != 0) {
			extension += " - " + participant.toString();
		}
		Report report = new Report(
				Messages.getString(ReportsDictionary.CERTIFICATION)
						+ extension, ReportsDictionary.CERTIFICATION,
				null, reportableData);
		return report;
	}
	
	public static Report createAgeClassesDefinitionReport(JRDataSource reportableData) {
		Report report = new Report(Messages.getString(ReportsDictionary.AGECLASSES_DEFINITION), 
				ReportsDictionary.AGECLASSES_DEFINITION, null, reportableData);
		return report;
	}

}
