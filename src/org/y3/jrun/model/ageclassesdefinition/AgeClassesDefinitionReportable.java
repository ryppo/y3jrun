package org.y3.jrun.model.ageclassesdefinition;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.ageclass.AgeClass;

public class AgeClassesDefinitionReportable implements JRDataSource {

	private int cursor = -1;
	private RichAgeClassesDefinition richAgeClassesDefinition;
	
	public AgeClassesDefinitionReportable(RichAgeClassesDefinition _richAgeClassesDefinition) {
		richAgeClassesDefinition = _richAgeClassesDefinition;
	}
	
	@Override
	public Object getFieldValue(JRField arg0) throws JRException {
		AgeClass model = null; 
		if (richAgeClassesDefinition != null) {
			if (richAgeClassesDefinition.getAgeClasses() != null)
			model = richAgeClassesDefinition.getAgeClasses()[cursor];
		}
		return getValueForField(arg0, model);
	}

	@Override
	public boolean next() throws JRException {
		boolean next = false;
		cursor++;
		if (richAgeClassesDefinition != null && richAgeClassesDefinition.getAgeClasses() != null
				&&  cursor < richAgeClassesDefinition.getAgeClasses().length) {
			next = true;
		}
		return next;
	}
	
	public Object getValueForField(JRField field, AgeClass ageClass) {
		Object returnValue = "";
		if (ageClass != null && field.getName().equals(KeywordsDictionary.AGECLASS_TITLE)) {
			returnValue = ageClass.getTitle();
		} else if (ageClass != null && field.getName().equals(KeywordsDictionary.AGECLASS_YEAR_FROM)) {
			int year = ageClass.getYearFrom();
			if (year != 0) {
				returnValue = year;
			}
		} else if (ageClass != null && field.getName().equals(KeywordsDictionary.AGECLASS_YEAR_TO)) {
			int year = ageClass.getYearTo();
			if (year != 0) {
				returnValue = year;
			}
		}return returnValue;
	}

}
