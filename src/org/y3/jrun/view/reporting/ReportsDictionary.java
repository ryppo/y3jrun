package org.y3.jrun.view.reporting;

import java.io.InputStream;

public class ReportsDictionary {

	private static String location = "org/y3/jrun/view/reporting/reports/";
	
	public static InputStream getReport(String report) {
		return ReportsDictionary.class.getClassLoader().getResourceAsStream(location + report);
	}
	
	public static String COMPETITION_LIST 						= "competitionListReport.jasper";
	public static String MODEL_LIST 							= "modelListReport.jasper";
	public static String NOTIFICATION_LIST 						= "notificationListReport.jasper";
	public static String DISCIPLINE_STARTGRID 					= "disciplineStartGridReport.jasper";
	public static String DISCIPLINE_GENDER_STARTGRID			= "disciplineGenderStartGridReport.jasper";
	public static String DISCIPLINE_AGECLASS_STARTGRID  		= "disciplineAgeClassStartGridReport.jasper";
	public static String DISCIPLINE_GENDER_AGECLASS_STARTGRID	= "disciplineGenderAgeClassStartGridReport.jasper";
	public static String CERTIFICATION							= "certification.jasper";
	public static String AGECLASSES_DEFINITION					= "ageclassesDefinitionReport.jasper";
	
}
