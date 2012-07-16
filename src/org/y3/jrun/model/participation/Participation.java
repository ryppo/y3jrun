package org.y3.jrun.model.participation;

import java.util.Date;

import org.y3.jrun.model.Model;

public class Participation extends Model {

	private String participationNumber;
	private final long zeroResultTime = -3600000;
	private long resultTime = zeroResultTime;
	private int contactId;
	private int competitionId;
	private int disciplineId;
	private String participantName;
	private boolean paymentDone = false;
	private boolean certificationHandedOver = false;
	private boolean noncompetitive = false;
	private int rank;
	private int ageClassRank;
	private int genderRank;
	private int genderAgeClassRank;
	private String comment;
	private int donationHospizInEuroCent;
	private boolean registeredOnline;
	private boolean notCompeted = false;

	/**
	 * @return the competitionId
	 */
	public int getCompetitionId() {
		return competitionId;
	}
	
	public boolean isResultTimeSet() {
		if (getResultTime() != zeroResultTime) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param competitionId
	 *            the competitionId to set
	 */
	public void setCompetitionId(int competitionId) {
		this.competitionId = competitionId;
	}

	/**
	 * @return the participationNumber
	 */
	public String getParticipationNumber() {
		if (participationNumber == null) {
			participationNumber = "";
		}
		return participationNumber;
	}

	/**
	 * @param participationNumber
	 *            the participationNumber to set
	 */
	public void setParticipationNumber(String participationNumber) {
		this.participationNumber = participationNumber;
	}

	/**
	 * @return the resultTime
	 */
	public long getResultTime() {
		return resultTime;
	}

	/**
	 * @return the result time
	 */
	public String getResultTimeAsString() {
		return Long.toString(resultTime);
	}
	
	/**
	 * @return the result time
	 */
	public Date getResultTimeAsDate() {
		return new Date(resultTime);
	}

	/**
	 * @param resultTime
	 *            the resultTime to set
	 */
	public void setResultTime(long _resultTime) {
		resultTime = _resultTime;
	}

	/**
	 * @param resultTime
	 */
	public void setResultTimeAsString(String _resultTime) {
		if (_resultTime != null && _resultTime.length() != 0) {
			try {
				resultTime = Long.parseLong(_resultTime);
			} catch (NumberFormatException ex) {
				resultTime = -3600000;
			}
		} else {
			resultTime = -3600000;
		}
	}
	
	/**
	 * @param _resultTime
	 */
	public void setResultTimeAsDate(Date _resultTime) {
		if (_resultTime != null) {
			resultTime = _resultTime.getTime();
		} else {
			resultTime = 0;
		}
 	}

	/**
	 * @return the contactId
	 */
	public int getContactId() {
		return contactId;
	}

	/**
	 * @param contactId
	 *            the contactId to set
	 */
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	@Override
	public String toString() {
		String toString = "[" + participationNumber + "] ";
		if (participantName != null) {
			toString += participantName;
		}
		return toString;
	}

	public String getParticipantName() {
		return participantName;
	}

	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}

	public int getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(int disciplineId) {
		this.disciplineId = disciplineId;
	}

	public boolean isPaymentDone() {
		return paymentDone;
	}
	
	public int isPaymentDoneAsInt() {
		if (paymentDone) {
			return 1;
		} else {
			return 0;
		}
	}

	public void setPaymentDone(boolean paymentDone) {
		this.paymentDone = paymentDone;
	}
	
	public void setPaymentDone(int paymentDone) {
		if (paymentDone == 0) {
			this.paymentDone = false;
		} else if (paymentDone == 1) {
			this.paymentDone = true;
		}
	}

	public boolean isCertificationHandedOver() {
		return certificationHandedOver;
	}

	public void setCertificationHandedOver(boolean certificationHandedOver) {
		this.certificationHandedOver = certificationHandedOver;
	}
	
	public int isCertificationHandedOverAsInt() {
		if (certificationHandedOver) {
			return 1;
		} else {
			return 0;
		}
	}

	public void setCertificationHandedOver(int certificationHandedOver) {
		if (certificationHandedOver == 0) {
			this.certificationHandedOver = false;
		} else if (certificationHandedOver == 1) {
			this.certificationHandedOver = true;
		}
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public int getAgeClassRank() {
		return ageClassRank;
	}
	
	public void setAgeClassRank(int _ageClassRank) {
		ageClassRank = _ageClassRank;
	}

	@Override
	public void generateImportFingerPrint() {
		setImportFingerPrint(toString());
	}
	
	public int isNoncompetitiveAsInt() {
		if (noncompetitive) {
			return 1;
		} else {
			return 0;
		}
	}

	public boolean isNoncompetitive() {
		return noncompetitive;
	}
	
	public void setNoncompetitive(boolean noncompetitive) {
		this.noncompetitive = noncompetitive;
	}

	public void setNoncompetitive(int noncompetitive) {
		if (noncompetitive == 0) {
			this.noncompetitive = false;
		} else if (noncompetitive == 1) {
			this.noncompetitive = true;
		}
	}

	public int getGenderRank() {
		return genderRank;
	}

	public void setGenderRank(int genderRank) {
		this.genderRank = genderRank;
	}

	public int getGenderAgeClassRank() {
		return genderAgeClassRank;
	}

	public void setGenderAgeClassRank(int genderAgeClassRank) {
		this.genderAgeClassRank = genderAgeClassRank;
	}

	public String getComment() {
		if (comment == null) {
			return "";
		}
		return comment;
	}

	public void setComment(String _comment) {
		comment = _comment;
	}

	public int getDonationHospizInEuroCent() {
		return donationHospizInEuroCent;
	}

	public void setDonationHospizInEuroCent(int _donationHospiz) {
		donationHospizInEuroCent = _donationHospiz;
	}

	public boolean isRegisteredOnline() {
		return registeredOnline;
	}

	public void setRegisteredOnline(boolean registeredOnline) {
		this.registeredOnline = registeredOnline;
	}
	
	public int isRegisteredOnlineAsInt() {
		if (registeredOnline) {
			return 1;
		} else {
			return 0;
		}
	}

	public void setRegisteredOnline(int registeredOnline) {
		if (registeredOnline == 0) {
			this.registeredOnline = false;
		} else if (registeredOnline == 1) {
			this.registeredOnline = true;
		}
	}

	/**
	 * @return the notCompeted
	 */
	public boolean isNotCompeted() {
		return notCompeted;
	}

	/**
	 * @param notCompeted the notCompeted to set
	 */
	public void setNotCompeted(boolean notCompeted) {
		this.notCompeted = notCompeted;
	}
	
	public int isNotCompetedAsInt() {
		if (notCompeted) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public void setNotCompeted(int notCompeted) {
		if (notCompeted == 0) {
			this.notCompeted = false;
		} else if (notCompeted == 1) {
			this.notCompeted = true;
		}
	}

}
