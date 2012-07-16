package org.y3.jrun.model.notification;

import org.y3.jrun.model.ModelListModel;

/**
 * 
 * @author Ryppo
 *
 */
public class NotificationListModel extends ModelListModel {

	private static final long serialVersionUID = 1L;
	
	public NotificationListModel(Notification[] _notifications) {
		super (_notifications);
	}
	
	@Override
	public Notification getElementAt(int arg0) {
		return (Notification) super.getElementAt(arg0);
	}

	public Notification[] getModel() {
		return (Notification[]) super.getModel();
	}

}
