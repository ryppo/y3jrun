package org.y3.jrun.storage.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.notification.Notification;
import org.y3.jrun.model.notification.Notification.NotificationType;

public class DBHandler_Notification extends DBModelHandler {

	@Override
	public String getSqlToCreateModelTable() {
		return KeywordsDictionary.SQL_CREATE_TABLE
				+ KeywordsDictionary.DATABASE_SCHEME + "."
				+ KeywordsDictionary.NOTIFICATION + " ("
				+ KeywordsDictionary.SQL_ID_INTEGER_PRIMARY_KEY_NOT_NULL + ","
				+ KeywordsDictionary.NOTIFICATION_TITLE
				+ KeywordsDictionary.SQL_VARCHAR_500 + ","
				+ KeywordsDictionary.NOTIFICATION_TYPE
				+ KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.NOTIFICATION_MESSAGE
				+ KeywordsDictionary.SQL_VARCHAR_500 + ","
				+ KeywordsDictionary.NOTIFICATION_EXCEPTIONSTACKTRACE
				+ KeywordsDictionary.SQL_VARCHAR_10000 + ","
				+ KeywordsDictionary.NOTIFICATION_RELATEDOBJECT_ID
				+ KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.NOTIFICATION_RELATEDOBJECT_TYPE
				+ KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES_DEFINITION 
				+ ")";
	}

	@Override
	public String getSqlToDropModelTable() {
		return KeywordsDictionary.SQL_DROP_TABLE
				+ KeywordsDictionary.DATABASE_SCHEME + "."
				+ KeywordsDictionary.NOTIFICATION;
	}

	@Override
	public String getSqlToInsertModel(Model model) {
		if (model == null || !(model instanceof Notification)) {
			return null;
		}
		Notification notification = (Notification) model;
		return KeywordsDictionary.SQL_INSERT_INTO
				+ KeywordsDictionary.DATABASE_SCHEME + "." 
		+ KeywordsDictionary.NOTIFICATION + " ("
				+ KeywordsDictionary.NOTIFICATION_TITLE + ", "
				+ KeywordsDictionary.NOTIFICATION_TYPE + ", "
				+ KeywordsDictionary.NOTIFICATION_MESSAGE + ", "
				+ KeywordsDictionary.NOTIFICATION_EXCEPTIONSTACKTRACE + ", "
				+ KeywordsDictionary.NOTIFICATION_RELATEDOBJECT_ID + ", "
				+ KeywordsDictionary.NOTIFICATION_RELATEDOBJECT_TYPE + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_VALUES + "'" 
				+ notification.getTitle() + "', '"
				+ notification.getNotificationType().toString() + "', '" 
				+ notification.getMessage() + "', '"
				+ notification.getExceptionStackTrace() + "',"
				+ Integer.toString(notification.getRelatedObjectId()) + ",'"
				+ notification.getRelatedObjectType() + "','"
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTE_VALUES(model)
				+ ")";
	}

	@Override
	public String getSqlToUpdateModel(Model model) {
		if (model == null || !(model instanceof Notification)) {
			return null;
		}
		Notification notification = (Notification) model;
		return KeywordsDictionary.SQL_UPDATE
				+ KeywordsDictionary.DATABASE_SCHEME + ".NOTIFICATION SET "
				+ KeywordsDictionary.NOTIFICATION_TITLE 				+ "='" + notification.getTitle() + "', "
				+ KeywordsDictionary.NOTIFICATION_TYPE 					+ "='" + notification.getNotificationType().toString() + "', "
				+ KeywordsDictionary.NOTIFICATION_MESSAGE 				+ "='" + notification.getMessage() + "', "
				+ KeywordsDictionary.NOTIFICATION_EXCEPTIONSTACKTRACE 	+ "='" + notification.getExceptionStackTrace() + "', "
				+ KeywordsDictionary.NOTIFICATION_RELATEDOBJECT_ID 		+ "='" + Integer.toString(notification.getRelatedObjectId()) + "', "
				+ KeywordsDictionary.NOTIFICATION_RELATEDOBJECT_TYPE 	+ "=" + notification.getRelatedObjectType() + "', "
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTES_FILLED(model)
				+ KeywordsDictionary.SQL_WHERE_ID_IS + notification.getId();
	}

	@Override
	public String getSqlToLoadAllModels() {
		return getSqlToLoadAllNotifications();
	}
	
	private String getSqlToLoadAllNotifications() {
		return KeywordsDictionary.SQL_SELECT
				+ KeywordsDictionary.MODEL_ID + ", "
				+ KeywordsDictionary.NOTIFICATION_TITLE + ", "
				+ KeywordsDictionary.NOTIFICATION_TYPE + ", "
				+ KeywordsDictionary.NOTIFICATION_MESSAGE + ", "
				+ KeywordsDictionary.NOTIFICATION_EXCEPTIONSTACKTRACE + ", "
				+ KeywordsDictionary.NOTIFICATION_RELATEDOBJECT_ID + ", "
				+ KeywordsDictionary.NOTIFICATION_RELATEDOBJECT_TYPE + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_FROM
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.NOTIFICATION;
	}

	@Override
	public Notification[] mapResultSetToModels(ResultSet resultSet) throws SQLException {
		Notification[] notifications = null;
		if (resultSet != null) {
			//create array
			resultSet.last();
			notifications = new Notification[resultSet.getRow()];
			resultSet.beforeFirst();
			//loop resultSet
			int notificationsCount = 0;
			while (resultSet.next()) {
				Notification n = new Notification();
				n.setId(resultSet.getInt(1));
				n.setTitle(resultSet.getString(2));
				String notificationType = resultSet.getString(3);
				if (notificationType.equals(Notification.NotificationType.debug.toString())) {
					n.setNotificationType(NotificationType.debug);
				} else {
					if (notificationType.equals(Notification.NotificationType.error.toString())) {
						n.setNotificationType(NotificationType.error);
					} else {
						if (notificationType.equals(Notification.NotificationType.fatal.toString())) {
							n.setNotificationType(NotificationType.fatal);
						} else {
							if (notificationType.equals(Notification.NotificationType.info.toString())) {
								n.setNotificationType(NotificationType.info);
							} else {
								if (notificationType.equals(Notification.NotificationType.warn.toString())) {
									n.setNotificationType(NotificationType.warn);
								}
							}
						}
					}
				}
				n.setMessage(resultSet.getString(4));
				n.setExceptionStackTrace(resultSet.getString(5));
				String relatedobjectid = resultSet.getString(6);
				if (relatedobjectid != null && relatedobjectid.length() != 0 && !relatedobjectid.equals("null")) {
					n.setRelatedObjectId(Integer.parseInt(relatedobjectid));
				}
				n.setRelatedObjectType(resultSet.getString(7));
				KeywordsDictionary.setMODEL_META_ATTRIBUTES(n, resultSet, 8);
				notifications[notificationsCount] = n;
				notificationsCount++;
			}
		}
		return notifications;
	}

	@Override
	public String getSqlForFullTextSearch(String searchString) {
		String whereClause =
				KeywordsDictionary.SQL_UPPER(KeywordsDictionary.NOTIFICATION_EXCEPTIONSTACKTRACE) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.NOTIFICATION_MESSAGE) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.NOTIFICATION_TITLE) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.NOTIFICATION_TYPE) + KeywordsDictionary.SQL_LIKE_UPPER(searchString);
		return getSqlToLoadModels(whereClause);
	}
	
}
