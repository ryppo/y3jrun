package org.y3.jrun.view.model.participation;

import java.util.Comparator;

import org.y3.jrun.model.participation.Participation;

public class ParticipationResultTimeComparator implements
		Comparator<Participation> {
	
	private final long zeroResultTime = -3600000;

	@Override
	public int compare(Participation p1, Participation p2) {
		if (p1 == null && p2 == null) {
			return 0;
		} else if (p1 == null) {
			return -1;
		} else if (p2 == null) {
			return 1;
		} else {
			long l1 = p1.getResultTime();
			long l2 = p2.getResultTime();
			if (l1 == zeroResultTime) l1 = 3600000;
			if (l2 == zeroResultTime) l2 = 3600000;
			if (l1 == l2) {
				return 0;
			} else if (l1 < l2) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}
