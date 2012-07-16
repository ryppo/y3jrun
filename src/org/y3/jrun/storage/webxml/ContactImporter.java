package org.y3.jrun.storage.webxml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.competition.RichCompetition;
import org.y3.jrun.model.contact.Contact;

public class ContactImporter extends ModelImporter {

	public void fillModelByType(String fieldType, String fieldValue, Model model, RichCompetition sourceCompetition) {
		if (model != null && model instanceof Contact && fieldType != null) {
			Contact contact = (Contact) model;
			//contact name
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_PARTICIPANT)) {
				fillName(fieldValue, contact);
			}
			//address
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_ADDRESS)) {
				fillAddress(fieldValue, contact);
			}
			//email
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_EMAIL)) {
				contact.setEmail(fieldValue);
			}
			//phone number
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_PHONE)) {
				contact.setPhonenumber(fieldValue);
			}
			//gender
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_GENDER)) {
				fillGender(fieldValue, contact);
			}
			//birth year
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_BIRTHYEAR)) {
				if (fieldValue != null) {
					contact.setBirthyear(Integer.parseInt(fieldValue));
				} else {
					contact.setBirthyear(0);
				}
			}
		}
	}
	
	public static void fillName(String unParsedName, Contact contact) {
		String space = " ";
		String comma = ",";
		String firstname = null;
		String lastname = null;
		if (StringUtils.contains(unParsedName, ",")) {
			lastname = StringUtils.substringBeforeLast(unParsedName, comma);
			firstname = StringUtils.substringAfterLast(unParsedName, comma);
			firstname = StringUtils.substringAfter(unParsedName, space);
		} else {
			firstname = StringUtils.substringBeforeLast(unParsedName, space);
			lastname = StringUtils.substringAfterLast(unParsedName, space);
		}	
		contact.setLastname(lastname);
		contact.setFirstname(firstname);
	}
	
	private static void fillAddress(String unParsedAddress, Contact contact) {
		//strategy: find postal in the middle. if possible, take left and right rest to fill contact
		//if not possible, just take whole string and put into first address field 
		String postal = null;
		if (unParsedAddress != null) {
			String space = " ";
			Pattern pattern = Pattern.compile("[0-9]{5}");
			Matcher matcher = pattern.matcher(unParsedAddress);
			if (matcher.find()) {
				//exact parsing
				//postal
				postal = matcher.group();
				contact.setPostal(Integer.parseInt(postal));
				//street
				contact.setAddress1(StringUtils.stripEnd(StringUtils.substringBefore(unParsedAddress, postal), space));
				//village
				contact.setAddress2(StringUtils.stripStart(StringUtils.substringAfter(unParsedAddress, postal), space));
			} else {
				//stupid usage of text
				contact.setAddress1(unParsedAddress);
			}
		}
	}
	
	private static void fillGender(String unParsedGender, Contact contact) {
		if (unParsedGender.equals(KeywordsDictionary.REGISTRATIONS_XML_GENDER_M)) {
			contact.setGender(Contact.gendertype.MALE);
		} else if (unParsedGender.equals(KeywordsDictionary.REGISTRATIONS_XML_GENDER_W)) {
			contact.setGender(Contact.gendertype.FEMALE);
		}
	}

	@Override
	public Model getNewModel() {
		return new Contact();
	}

	@Override
	public Model[] getNewModelArray(int size) {
		return new Contact[size];
	}

}
