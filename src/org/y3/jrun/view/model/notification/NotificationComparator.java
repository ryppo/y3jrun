package org.y3.jrun.view.model.notification;

import java.util.Comparator;

import org.y3.jrun.model.notification.Notification;

public class NotificationComparator implements Comparator<Notification> {
	
public compareBy COMPARE_MODE = compareBy.DESCENDING;
	
	public enum compareBy { ASCENDING, DESCENDING }

	@Override
	public int compare(Notification ac1, Notification ac2) {
		if (ac1 == null && ac2 == null) {
			return 0;
		} else if (ac1 == null) {
			return 1;
		} else if (ac2 == null) {
			return -1;
		} else {
			Integer i1 = new Integer(ac1.getId());
			Integer i2 = new Integer(ac2.getId());
			if (COMPARE_MODE == compareBy.ASCENDING) {
				return i1.compareTo(i2);
			} else {
				return i2.compareTo(i1);
			}
		}
	}
	
}
