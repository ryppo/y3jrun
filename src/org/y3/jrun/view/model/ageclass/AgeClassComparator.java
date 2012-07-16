package org.y3.jrun.view.model.ageclass;

import java.util.Comparator;

import org.y3.jrun.model.ageclass.AgeClass;

public class AgeClassComparator implements Comparator<AgeClass> {

	@Override
	public int compare(AgeClass ac1, AgeClass ac2) {
		if (ac1 == null && ac2 == null) {
			return 0;
		} else if (ac1 == null) {
			return 1;
		} else if (ac2 == null) {
			return -1;
		} else {
			String a1 = Integer.toString(ac1.getYearFrom());
			String a2 = Integer.toString(ac2.getYearFrom());
			return a1.compareTo(a2);
		}
	}

}
