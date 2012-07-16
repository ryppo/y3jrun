package org.y3.jrun.model.participation;

import java.text.SimpleDateFormat;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.view.i18n.Messages;

public class RichParticipationReportable implements JRDataSource {

	private int cursor = -1;
	private RichParticipation[] richParticipations;
	
	public RichParticipationReportable(RichParticipation[] _richParticipations) {
		richParticipations = _richParticipations;
	}
	
	@Override
	public Object getFieldValue(JRField arg0) throws JRException {
		RichParticipation model = null; 
		if (richParticipations != null && richParticipations.length != 0) {
			model = richParticipations[cursor];
		}
		return getValueForField(arg0, model);
	}

	@Override
	public boolean next() throws JRException {
		boolean next = false;
		cursor++;
		if (richParticipations != null && richParticipations.length != 0 && cursor < richParticipations.length) {
			next = true;
		}
		return next;
	}
	
	public Object getValueForField(JRField field, RichParticipation richParticipation) {
		Object returnValue = "";
		if (richParticipation != null && field.getName().equals(KeywordsDictionary.TITLE)) {
			returnValue = richParticipation.getRelatedCompetition().getTitle() + " - " + richParticipation.getDiscipline().getName()
					+ " - " + richParticipation.getDiscipline().getLength();
		} else if (richParticipation != null && field.getName().equals(KeywordsDictionary.PARTICIPATION_PARTICIPATION_NUMBER)) {
			returnValue = richParticipation.getParticipationNumber();
		} else if (richParticipation != null && field.getName().equals(KeywordsDictionary.CONTACT)) {
			Contact contact = richParticipation.getParticipant();
			if (contact != null) {
				returnValue = contact.toString();
			}
		} else if (richParticipation != null && field.getName().equals(KeywordsDictionary.PARTICIPATION_RESULTTIME)) {
			returnValue = richParticipation.getResultTimeAsString();
		} else if (richParticipation != null && field.getName().equals(KeywordsDictionary.CONTACT_GENDER)) {
			Contact contact = richParticipation.getParticipant();
			if (contact != null) {
				Contact.gendertype gender = contact.getGender();
				if (gender != null) {
					returnValue = gender.toString();
				}
			}
		} else if (richParticipation != null && field.getName().equals(KeywordsDictionary.CONTACT_GENDER_RESOURCE)) {
			Contact contact = richParticipation.getParticipant();
			if (contact != null) {
				Contact.gendertype gender = contact.getGender();
				if (gender != null) {
					returnValue = Messages.getString(gender.toString());
				}
			}
		} else if (richParticipation != null && field.getName().equals(KeywordsDictionary.CONTACT_FULLNAME)) {
			returnValue = richParticipation.getParticipant().getFullName();
		} else if (richParticipation != null && field.getName().equals(KeywordsDictionary.DISCIPLINE_DISTANCE)) {
			returnValue = richParticipation.getDiscipline().getLength();
		} else if (richParticipation != null && field.getName().equals(KeywordsDictionary.COMPETITION_ENDOFCOMPETITION)) {
			returnValue = new SimpleDateFormat("dd.MM.yyyy").format(richParticipation.getDiscipline().getStartOfDiscipline());
		} else if (richParticipation != null && field.getName().equals(KeywordsDictionary.AGECLASS)) {
			returnValue = richParticipation.getAgeClassForParticipant().getTitle();
		} else if (richParticipation != null && field.getName().equals(KeywordsDictionary.PARTICIPATION_RANK)) {
			if (richParticipation.isNoncompetitive()) {
				return Messages.getString(Messages.NONCOMPETITIVE);
			} else {
				if (richParticipation.getRank() != 0) {
					returnValue = Integer.toString(richParticipation.getRank());
				} else {
					returnValue = "";
				}
			}
		} else if (richParticipation != null && field.getName().equals(KeywordsDictionary.PARTICIPATION_GENDER_RANK)) {
			if (richParticipation.isNoncompetitive()) {
				return Messages.getString(Messages.NONCOMPETITIVE);
			} else {
				if (richParticipation.getGenderRank() != 0) {
					returnValue = Integer.toString(richParticipation.getGenderRank());
				} else {
					returnValue = "";
				}
			}
		} else if (richParticipation != null && field.getName().equals(KeywordsDictionary.PARTICIPATION_AGECLASS_RANK)) {
			if (richParticipation.isNoncompetitive()) {
				return Messages.getString(Messages.NONCOMPETITIVE);
			} else {
				if (richParticipation.getAgeClassRank() != 0) {
					returnValue = Integer.toString(richParticipation.getAgeClassRank());
				} else {
					returnValue = "";
				}
			}
		} else if (richParticipation != null && field.getName().equals(KeywordsDictionary.PARTICIPATION_GENDER_AGECLASS_RANK)) {
			if (richParticipation.isNoncompetitive()) {
				return Messages.getString(Messages.NONCOMPETITIVE);
			} else {
				if (richParticipation.getGenderAgeClassRank() != 0) {
					returnValue = Integer.toString(richParticipation.getGenderAgeClassRank());
				} else {
					returnValue = "";
				}
			}
		}
		return returnValue;
	}

}
