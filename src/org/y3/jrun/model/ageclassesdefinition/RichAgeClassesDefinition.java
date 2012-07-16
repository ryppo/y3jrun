package org.y3.jrun.model.ageclassesdefinition;

import org.y3.jrun.model.ageclass.AgeClass;

public class RichAgeClassesDefinition extends AgeClassesDefinition {

	private AgeClass[] ageClasses = null;
	
	public RichAgeClassesDefinition(AgeClassesDefinition ageClassesDefinition, AgeClass[] ageClasses) {
		fillByAgeClassesDefinition(ageClassesDefinition);
		this.ageClasses = ageClasses;
	}

	public AgeClass[] getAgeClasses() {
		return ageClasses;
	}

	public void setAgeClasses(AgeClass[] ageClasses) {
		this.ageClasses = ageClasses;
	}
	
	public void fillByAgeClassesDefinition(AgeClassesDefinition ageClassesDefinition) {
		if (ageClassesDefinition != null) {
			setChangeDate(ageClassesDefinition.getChangeDate());
			setChangerName(ageClassesDefinition.getChangerName());
			setCreationDate(ageClassesDefinition.getCreationDate());
			setCreatorName(ageClassesDefinition.getCreatorName());
			setId(ageClassesDefinition.getId());
			setName(ageClassesDefinition.getName());
		} else {
			setChangeDate(null);
			setChangerName(null);
			setCreationDate(null);
			setCreatorName(null);
			setId(0);
			setName(null);
		}
	}
	
	public AgeClass getAgeClassForBirthyear(int birthyear) {
		AgeClass ageClassForBirthyear = null;
		if (ageClasses != null) {
			for (AgeClass ageClass: ageClasses) {
				if (ageClass != null) {
					if (ageClass.getYearFrom() <= birthyear && (ageClass.getYearTo() >= birthyear || ageClass.getYearTo() == 0)) {
						ageClassForBirthyear = ageClass;
						break;
					}
				}
			}
		}
		return ageClassForBirthyear;
	}
	
}
