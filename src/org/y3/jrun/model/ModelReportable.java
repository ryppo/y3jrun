package org.y3.jrun.model;

import java.text.SimpleDateFormat;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.y3.jrun.control.KeywordsDictionary;

public class ModelReportable implements JRDataSource {
	
	private int cursor = -1;
	private ModelListModel listModel;
	public String title;
	
	public ModelReportable(ModelListModel _listModel, String _title) {
		listModel = _listModel;
		title = _title;
	}

	@Override
	public Object getFieldValue(JRField arg0) throws JRException {
		
		Object value = null; 
		if (listModel != null && listModel.getSize() != 0) {
			value = listModel.getElementAt(cursor);
		}
		Model model = null;
		if (value != null && value instanceof Model) {
			model = (Model) value;
		}
		
		return getValueForField(arg0, model);
	}

	@Override
	public boolean next() throws JRException {
		boolean next = false;
		cursor++;
		if (listModel != null && listModel.getSize() != 0 && cursor < listModel.getSize()) {
			next = true;
		}
		return next;
	}
	
	public Object getValueForField(JRField field, Model currentModel) {
		Object returnValue = null;
		if (field.getName().equals(KeywordsDictionary.TITLE)) {
			returnValue = title;
		} else if (currentModel != null && field.getName().equals(KeywordsDictionary.MODEL_ID)) {
			returnValue = currentModel.getId();
		} else if (currentModel != null && field.getName().equals(KeywordsDictionary.TOSTRING)) {
			returnValue = currentModel.toString();
		} else if(field.getName().equals(KeywordsDictionary.MODEL_CHANGEDATE)) {
			returnValue = new SimpleDateFormat().format(currentModel.getChangeDate());
		} else if(field.getName().equals(KeywordsDictionary.MODEL_CHANGER)) {
			returnValue = currentModel.getChangerName();
		} else if(field.getName().equals(KeywordsDictionary.MODEL_CREATIONDATE)) {
			returnValue = new SimpleDateFormat().format(currentModel.getCreationDate());
		} else if(field.getName().equals(KeywordsDictionary.MODEL_CREATOR)) {
			returnValue = currentModel.getCreatorName();
		}
		return returnValue;
	}

}
