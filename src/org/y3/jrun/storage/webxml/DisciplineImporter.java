package org.y3.jrun.storage.webxml;

import org.apache.commons.lang.StringUtils;
import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.competition.RichCompetition;
import org.y3.jrun.model.discipline.Discipline;

public class DisciplineImporter extends ModelImporter {

	@Override
	protected void fillModelByType(String fieldType, String fieldValue,
			Model model, RichCompetition sourceCompetition) {
		if (fieldType != null && model != null && model instanceof Discipline) {
			Discipline discipline = (Discipline) model;
			//discipline name
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_DISCIPLINE)) {
				discipline.setName(fieldValue);
			}
			//length and fee
			if (fieldType.equals(KeywordsDictionary.REGISTRATIONS_XML_LENGTH_AND_FEE)) {
				fillLengthAndFee(fieldValue, discipline);
			}
		}
	}
	
	private static void fillLengthAndFee(String unParsedName, Discipline discipline) {
		String separator = " / ";
		String currency = " Euro";
		String length = null;
		long price = 0;
		if (unParsedName != null) {
			length = StringUtils.substringBefore(unParsedName, separator);
			String fee = null;
			fee = StringUtils.substringAfter(unParsedName, separator);
			if (fee != null) {
				fee = StringUtils.substringBefore(fee, currency);
				if (fee != null && !fee.isEmpty()) {
					fee = StringUtils.replaceChars(fee, ",", ".");
					Double priceDouble = Double.parseDouble(fee);
					price = priceDouble.longValue() * 100;
				}
			}
		}
		discipline.setLength(length);
		discipline.setPriceInEuroCent(price);
	}

	@Override
	public Model getNewModel() {
		return new Discipline();
	}

	@Override
	public Model[] getNewModelArray(int size) {
		return new Discipline[size];
	}
}
