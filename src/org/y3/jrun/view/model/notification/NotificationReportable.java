package org.y3.jrun.view.model.notification;

import java.text.SimpleDateFormat;

import net.sf.jasperreports.engine.JRField;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ModelListModel;
import org.y3.jrun.model.ModelReportable;
import org.y3.jrun.model.notification.Notification;

public class NotificationReportable extends ModelReportable {

	public NotificationReportable(ModelListModel _listModel, String _title) {
		super(_listModel, _title);
	}
	
	public Object getValueForField(JRField field, Model currentModel) {
		Object returnValue = null;
		
		Notification notification = null;
		if (currentModel instanceof Notification) {
			notification = (Notification) currentModel;
		} else {
			return null;
		}
		
		if (field.getName().equals(KeywordsDictionary.TITLE)) {
			returnValue = title;
		} else if (field.getName().equals(KeywordsDictionary.MODEL_ID)) {
			returnValue = notification.getId();
		} else if(field.getName().equals(KeywordsDictionary.TOSTRING)) {
			returnValue = notification.toString();
		} else if(field.getName().equals(KeywordsDictionary.MODEL_CHANGEDATE)) {
			returnValue = new SimpleDateFormat().format(notification.getChangeDate());
		} else if(field.getName().equals(KeywordsDictionary.MODEL_CHANGER)) {
			returnValue = notification.getChangerName();
		} else if(field.getName().equals(KeywordsDictionary.MODEL_CREATIONDATE)) {
			returnValue = new SimpleDateFormat().format(notification.getCreationDate());
		} else if(field.getName().equals(KeywordsDictionary.MODEL_CREATOR)) {
			returnValue = notification.getCreatorName();
		} else if(field.getName().equals(KeywordsDictionary.NOTIFICATION_MESSAGE)) {
			returnValue = notification.getMessage();
		} else if(field.getName().equals(KeywordsDictionary.NOTIFICATION_RELATEDOBJECT_ID)) {
			returnValue = notification.getRelatedObjectId();
		} else if(field.getName().equals(KeywordsDictionary.NOTIFICATION_RELATEDOBJECT_TYPE)) {
			returnValue = notification.getRelatedObjectType();
		}else if(field.getName().equals(KeywordsDictionary.NOTIFICATION_TYPE)) {
			returnValue = notification.getNotificationType();
		}
		
		return returnValue;
	}


}
