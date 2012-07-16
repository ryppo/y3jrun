/**
 * 
 */
package org.y3.jrun.model.notification;

import org.y3.jrun.model.Model;

/**
 * @author Ryppo
 * @version $Id$
 */
public class Notification extends Model {
	
	public enum NotificationType {
		fatal, error, warn, info, debug
	}
	
	private NotificationType notificationType;
	private String title;
	private String message;
	private String exceptionStackTrace;
	private String relatedObjectType;
	private int relatedObjectId;

	/**
	 * @return the notificationType
	 */
	public NotificationType getNotificationType() {
		return notificationType;
	}
	
	public Notification(NotificationType _notificationType, String _title, String _message) {
		notificationType = _notificationType;
		title = _title;
		message = _message;
	}
	
	public Notification() {
	}
	
	public Notification(Exception exception) {
		notificationType = NotificationType.error;
		setException(exception);
	}

	public Notification(Exception exception, String _relatedObjectType, int _relatedObjectId) {
		notificationType = NotificationType.error;
		setException(exception);
		relatedObjectType = _relatedObjectType;
		relatedObjectId = _relatedObjectId;
	}
	
	public Notification(Exception exception, NotificationType _notificationType, String _relatedObjectType, int _relatedObjectId) {
		notificationType = _notificationType;
		setException(exception);
		relatedObjectType = _relatedObjectType;
		relatedObjectId = _relatedObjectId;
	}
	
	public Notification(Exception exception, NotificationType _notificationType) {
		notificationType = _notificationType;
		setException(exception);
	}
	
	private void setException(Exception exception) {
		title = exception.toString();
		message = exception.getMessage();
		StackTraceElement[] stacktrace = exception.getStackTrace();
		if (stacktrace != null && stacktrace.length != 0) {
			exceptionStackTrace = "";
			for (StackTraceElement element: stacktrace) {
				exceptionStackTrace += "/n" + element;
			}
		} else {
			exceptionStackTrace = null;
		}
	}
	
	public Notification(NotificationType _notificationType, String _title, String _message, String _relatedObjectType, int _relatedObjectId) {
		notificationType = _notificationType;
		title = _title;
		message = _message;
		relatedObjectType = _relatedObjectType;
		relatedObjectId = _relatedObjectId;
	}

	/**
	 * @param notificationType the notificationType to set
	 */
	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		if (title == null) {
			return "";
		}
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		if (message == null) {
			return "";
		}
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the exceptionStackTrace
	 */
	public String getExceptionStackTrace() {
		if (exceptionStackTrace == null) {
			return "";
		}
		return exceptionStackTrace;
	}

	/**
	 * @param exceptionStackTrace the exceptionStackTrace to set
	 */
	public void setExceptionStackTrace(String exceptionStackTrace) {
		this.exceptionStackTrace = exceptionStackTrace;
	}

	/**
	 * @return the relatedObjectType
	 */
	public String getRelatedObjectType() {
		if (relatedObjectType == null) {
			return "";
		}
		return relatedObjectType;
	}

	/**
	 * @param relatedObjectType the relatedObjectType to set
	 */
	public void setRelatedObjectType(String relatedObjectType) {
		this.relatedObjectType = relatedObjectType;
	}

	/**
	 * @return the relatedObjectId
	 */
	public int getRelatedObjectId() {
		return relatedObjectId;
	}

	/**
	 * @param relatedObjectId the relatedObjectId to set
	 */
	public void setRelatedObjectId(int relatedObjectId) {
		this.relatedObjectId = relatedObjectId;
	}

	/* (non-Javadoc)
	 * @see org.y3.jrun.model.Model#toString()
	 */
	@Override
	public String toString() {
		String toString = "";
		if (relatedObjectType != null) {
			toString = "[" + getId() + "] ";
		}
		return toString + title;
	}

	@Override
	public void generateImportFingerPrint() {
		setImportFingerPrint(toString());
	}

}
