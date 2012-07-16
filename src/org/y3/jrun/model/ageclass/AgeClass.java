package org.y3.jrun.model.ageclass;

import org.y3.jrun.control.Utils;
import org.y3.jrun.model.Model;
import org.y3.jrun.view.i18n.Messages;

public class AgeClass extends Model {
	
	private String title;
	private int yearFrom;
	private int yearTo;
	private int relatedAgeClassesDefinitionID;

	@Override
	public String toString() {
		String from = Utils.intToStringWith0AsEmpty(yearFrom);
		String to = Utils.intToStringWith0AsEmpty(yearTo);
		if (from != null && from.length() != 0 && to != null && to.length() != 0) {
			return from + " - " + to;			
		} else if (from == null || from.length() == 0) {
			return to + " " + Messages.getString(Messages.AND_OLDER);
		} else if (to == null || to.length() == 0) {
			return from + " " + Messages.getString(Messages.AND_YOUNGER);
		} else {
			return "";
		}
	}
	
	public int getYearFrom() {
		return yearFrom;
	}

	public void setYearFrom(int yearFrom) {
		this.yearFrom = yearFrom;
	}

	public int getYearTo() {
		return yearTo;
	}

	public void setYearTo(int yearTo) {
		this.yearTo = yearTo;
	}

	public void setRelatedAgeClassesDefinitionID(int relatedAgeClassesDefinitionID) {
		this.relatedAgeClassesDefinitionID = relatedAgeClassesDefinitionID;
	}
	
	public int getRelatedAgeClassesDefinitionID() {
		return relatedAgeClassesDefinitionID;
	}

	@Override
	public void generateImportFingerPrint() {
		setImportFingerPrint(toString());
	}

	public String getTitle() {
		if (title != null) {
			return title;
		} else {
			return "";
		}
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
