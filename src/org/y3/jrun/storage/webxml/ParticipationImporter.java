package org.y3.jrun.storage.webxml;

import org.apache.commons.lang.StringUtils;
import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.competition.RichCompetition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.model.participation.RichParticipation;

public class ParticipationImporter extends ModelImporter {

	@Override
	protected void fillModelByType(String fieldType, String fieldValue,
			Model model, RichCompetition sourceCompetition) {
		if (fieldType != null && model != null && model instanceof RichParticipation) {
			RichParticipation participation = (RichParticipation) model;
			participation.setRelatedCompetition(sourceCompetition);
			participation.setRegisteredOnline(true);
			//participant
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_PARTICIPANT)) {
				Contact contact = new Contact();
				participation.setParticipant(contact);
				ContactImporter.fillName(fieldValue, contact);
			}
			//participant birth year
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_BIRTHYEAR)) {
				if (fieldValue == null || fieldValue.length() == 0) {
					fieldValue = "0";
				}
				participation.getParticipant().setBirthyear(Integer.valueOf(fieldValue));
				participation.getParticipant().generateImportFingerPrint();
			}
			//discipline
			Discipline discipline = null;
			DisciplineImporter dI = new DisciplineImporter();
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_DISCIPLINE)) {
				discipline = new Discipline();
				dI.fillModelByType(fieldType, fieldValue, discipline, sourceCompetition);
				participation.setDiscipline(discipline);
			}
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_LENGTH_AND_FEE)) {
				dI.fillModelByType(fieldType, fieldValue, participation.getDiscipline(), sourceCompetition);
				participation.getDiscipline().generateImportFingerPrint();
			}
			//participation
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_ANNOTATIONS)) {
				participation.setComment(fieldValue);
			}
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_DONATION_HOSPIZ)) {
				int donation = 0;
				if (fieldValue != null && !fieldValue.isEmpty()) {
						fieldValue = StringUtils.replaceChars(fieldValue, ",", ".");
						Double donationDouble = Double.parseDouble(fieldValue);
						donation = (int) Math.round(donationDouble.doubleValue() * 100);
				}
				participation.setDonationHospizInEuroCent(donation);
			}
		}
	}

	@Override
	public Model getNewModel() {
		return new RichParticipation();
	}

	@Override
	public Model[] getNewModelArray(int size) {
		return new RichParticipation[size];
	}

}
