package org.y3.jrun.model.participation;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.model.competition.RichCompetition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.view.i18n.Messages;

public class RichParticipation extends Participation {

	private RichCompetition relatedCompetition;
	private Contact participant;
	private Discipline discipline;
	
	public RichCompetition getRelatedCompetition() {
		return relatedCompetition;
	}

	public void setRelatedCompetition(RichCompetition relatedCompetition) {
		this.relatedCompetition = relatedCompetition;
	}
	
	public Contact getParticipant() {
		return participant;
	}
	
	public void setParticipant(Contact participant) {
		this.participant = participant;
	}
	
	public void setMetaDataByParticipation(Participation participation) {
		if (participation != null) {
			setId(participation.getId());
			setChangeDate(participation.getChangeDate());
			setChangerName(participation.getChangerName());
			setCompetitionId(participation.getCompetitionId());
			setContactId(participation.getContactId());
			setCreationDate(participation.getCreationDate());
			setCreatorName(participation.getCreatorName());
			setDisciplineId(participation.getDisciplineId());
			setParticipationNumber(participation.getParticipationNumber());
			setResultTime(participation.getResultTime());
			setRank(participation.getRank());
			setAgeClassRank(participation.getAgeClassRank());
			setGenderRank(participation.getGenderRank());
			setGenderAgeClassRank(participation.getGenderAgeClassRank());
			setPaymentDone(participation.isPaymentDone());
			setCertificationHandedOver(participation.isCertificationHandedOver());
			setNoncompetitive(participation.isNoncompetitive());
			setImportFingerPrint(participation.getImportFingerPrint());
			setComment(participation.getComment());
			setDonationHospizInEuroCent(participation.getDonationHospizInEuroCent());
			setRegisteredOnline(participation.isRegisteredOnline());
			setNotCompeted(participation.isNotCompeted());
		} else {
			setId(0);
			setChangeDate(null);
			setChangerName(null);
			setCompetitionId(0);
			setContactId(0);
			setCreationDate(null);
			setCreatorName(null);
			setDisciplineId(0);
			setParticipationNumber(null);
			setResultTime(0);
			setRank(0);
			setAgeClassRank(0);
			setGenderRank(0);
			setGenderAgeClassRank(0);
			setPaymentDone(false);
			setCertificationHandedOver(false);
			setNoncompetitive(false);
			setImportFingerPrint(null);
			setComment(null);
			setDonationHospizInEuroCent(0);
			setRegisteredOnline(false);
			setNotCompeted(false);
		}
	}

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}
	
	@Override
	public String getResultTimeAsString() {
		Date resultTime = getResultTimeAsDate();
		if (resultTime != null && discipline != null && discipline.getDurationFormat() != null) {
			return new SimpleDateFormat(Messages.getString(discipline.getDurationFormat().toString())).format(resultTime);	
		} else {
			return null;
		}
	}
	
	public AgeClass getAgeClassForParticipant() {
		AgeClass ageClass = null;
		if (relatedCompetition != null && relatedCompetition.getRelatedAgeClassDefinition() != null && participant != null) {
			ageClass = relatedCompetition.getRelatedAgeClassDefinition().getAgeClassForBirthyear(participant.getBirthyear());
		}
		return ageClass;
	}
	
	@Override
	public void generateImportFingerPrint() {
		String ifp = toString();
		if (getRelatedCompetition() != null) {
			ifp += getRelatedCompetition().getTitle();
		}
		if (getParticipant() != null) {
			ifp += getParticipant().getImportFingerPrint();
		}
		if (getDiscipline() != null) {
			ifp += getDiscipline().getImportFingerPrint();
		}
		if (getRelatedCompetition() != null) {
			ifp += getRelatedCompetition().getImportFingerPrint();
		}
		setImportFingerPrint(ifp);
	}
	
}
