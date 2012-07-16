package org.y3.jrun.model.competition;

import org.y3.jrun.model.ageclassesdefinition.RichAgeClassesDefinition;

public class RichCompetition extends Competition {
	
	private RichAgeClassesDefinition relatedAgeClassDefinition;
	
	public RichCompetition(RichAgeClassesDefinition relatedAgeClassDefinition, Competition competition) {
		this.relatedAgeClassDefinition = relatedAgeClassDefinition;
		fillByCompetition(competition);
	}

	public RichAgeClassesDefinition getRelatedAgeClassDefinition() {
		return relatedAgeClassDefinition;
	}

	public void setRelatedAgeClassDefinition(RichAgeClassesDefinition relatedAgeClassDefinition) {
		this.relatedAgeClassDefinition = relatedAgeClassDefinition;
	}
	
	public void fillByCompetition(Competition competition) {
		if (competition != null) {
			setAgeClassesDefinitionId(competition.getAgeClassesDefinitionId());
			setChangeDate(competition.getChangeDate());
			setChangerName(competition.getChangerName());
			setCreationDate(competition.getCreationDate());
			setCreatorName(competition.getCreatorName());
			setEndOfCompetition(competition.getEndOfCompetition());
			setId(competition.getId());
			setStartOfCompetition(competition.getStartOfCompetition());
			setTitle(competition.getTitle());
			setRegistrationPage(competition.getRegistrationPage());
			setRegistrationsLogFile(competition.getRegistrationsLogFile());
		} else {
			setAgeClassesDefinitionId(0);
			setChangeDate(null);
			setChangerName(null);
			setCreationDate(null);
			setCreatorName(null);
			setEndOfCompetition(null);
			setId(0);
			setStartOfCompetition(null);
			setTitle(null);
			setRegistrationPageToNull();
			setRegistrationsLogFileToNull();
		}
	}

}
