package org.y3.jrun.model.competition;

import java.text.SimpleDateFormat;

import net.sf.jasperreports.engine.JRField;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ModelListModel;
import org.y3.jrun.model.ModelReportable;

public class CompetitionReportable extends ModelReportable {

	public CompetitionReportable(ModelListModel _listModel, String _title) {
		super(_listModel, _title);
	}
	
	public Object getValueForField(JRField field, Model currentModel) {
		Object returnValue = null;
		
		Competition competition = null;
		if (currentModel instanceof Competition) {
			competition = (Competition) currentModel;
		} else {
			return null;
		}
		
		if (field.getName().equals(KeywordsDictionary.TITLE)) {
			returnValue = title;
		} else if (field.getName().equals(KeywordsDictionary.MODEL_ID)) {
			returnValue = competition.getId();
		} else if(field.getName().equals(KeywordsDictionary.TOSTRING)) {
			returnValue = competition.toString();
		} else if(field.getName().equals(KeywordsDictionary.MODEL_CHANGEDATE)) {
			returnValue = new SimpleDateFormat().format(competition.getChangeDate());
		} else if(field.getName().equals(KeywordsDictionary.MODEL_CHANGER)) {
			returnValue = competition.getChangerName();
		} else if(field.getName().equals(KeywordsDictionary.MODEL_CREATIONDATE)) {
			returnValue = new SimpleDateFormat().format(competition.getCreationDate());
		} else if(field.getName().equals(KeywordsDictionary.MODEL_CREATOR)) {
			returnValue = competition.getCreatorName();
		} else if(field.getName().equals(KeywordsDictionary.COMPETITION_STARTOFCOMPETITION)) {
			returnValue = new SimpleDateFormat().format(competition.getStartOfCompetition());
		} else if(field.getName().equals(KeywordsDictionary.COMPETITION_ENDOFCOMPETITION)) {
			returnValue = new SimpleDateFormat().format(competition.getEndOfCompetition());
		}
		
		return returnValue;
	}

}
