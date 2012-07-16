package org.y3.jrun.control;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.model.competition.Competition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.model.participation.RichParticipation;

public class Utils {

	public static String parseIntegerFromStringAsString(String integerInString) {
		int searchInt = 9999;
		try {
			searchInt = Integer.parseInt(integerInString);
		} catch (NumberFormatException nfe) {
		}
		return Integer.toString(searchInt);
	}
	
	public static int parseIntValueFromStringDefault0(String stringValue) {
		int intValue = 0;
		try {
			if (stringValue != null) {
				intValue = Integer.parseInt(stringValue);
			}
		} catch (NumberFormatException e) {
		}
		return intValue;
	}
	
	public static String intToStringWith0AsEmpty(int integer) {
		String value = "";
		if (integer != 0) {
			value = Integer.toString(integer);
		}
		return value;
	}
	
	public static boolean isSearchStringInsideOfRichParticipation(String searchString, RichParticipation richParticipation) {
		boolean result = false;
		if (richParticipation != null) {
			if (searchString == null || searchString.length() == 0) {
				result = true;
			} else {
				//1. collect attribute values to search in
				ArrayList<String> attributeValues = new ArrayList<String>(0);
				AgeClass ageClass = richParticipation.getAgeClassForParticipant();
				if (ageClass != null) {
					attributeValues.add(ageClass.getChangerName());
					attributeValues.add(ageClass.getCreatorName());
					attributeValues.add(ageClass.getImportFingerPrint());
					attributeValues.add(ageClass.getNotificationMessage());
					attributeValues.add(ageClass.getNotificationTitle());
				}
				attributeValues.add(richParticipation.getChangerName());
				attributeValues.add(richParticipation.getCreatorName());
				attributeValues.add(richParticipation.getImportFingerPrint());
				attributeValues.add(richParticipation.getNotificationMessage());
				attributeValues.add(richParticipation.getNotificationTitle());
				attributeValues.add(richParticipation.getParticipantName());
				attributeValues.add(richParticipation.getParticipationNumber());
				Competition competition = richParticipation.getRelatedCompetition();
				if (competition != null) {
					attributeValues.add(competition.getChangerName());
					attributeValues.add(competition.getCreatorName());
					attributeValues.add(competition.getImportFingerPrint());
					attributeValues.add(competition.getNotificationMessage());
					attributeValues.add(competition.getNotificationTitle());
					attributeValues.add(competition.getRegistrationPageToString());
					attributeValues.add(competition.getRegistrationsLogFileToString());
					attributeValues.add(competition.getTitle());
					attributeValues.add(competition.getWebPageToString());
				}
				Contact contact = richParticipation.getParticipant();
				if (contact != null) {
					attributeValues.add(contact.getChangerName());
					attributeValues.add(contact.getCreatorName());
					attributeValues.add(contact.getImportFingerPrint());
					attributeValues.add(contact.getNotificationMessage());
					attributeValues.add(contact.getNotificationTitle());
					attributeValues.add(contact.getAddress1());
					attributeValues.add(contact.getAddress2());
					attributeValues.add(contact.getCity());
					attributeValues.add(contact.getComments());
					attributeValues.add(contact.getEmail());
					attributeValues.add(contact.getFirstname());
					attributeValues.add(contact.getFullName());
					attributeValues.add(contact.getLastname());
					attributeValues.add(contact.getMobilenumber());
					attributeValues.add(contact.getName());
					attributeValues.add(contact.getPhonenumber());
				}
				Discipline discipline = richParticipation.getDiscipline();
				if (discipline != null) {
					attributeValues.add(discipline.getChangerName());
					attributeValues.add(discipline.getCreatorName());
					attributeValues.add(discipline.getImportFingerPrint());
					attributeValues.add(discipline.getLength());
					attributeValues.add(discipline.getName());
					attributeValues.add(discipline.getNotificationMessage());
					attributeValues.add(discipline.getNotificationTitle());
				}
				int attributes = attributeValues.size();
				for (int aNo = 0; aNo < attributes; aNo++) {
					String attribute = attributeValues.get(aNo);
					if (attribute != null && StringUtils.contains(StringUtils.upperCase(attribute), StringUtils.upperCase(searchString))) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
	
}
