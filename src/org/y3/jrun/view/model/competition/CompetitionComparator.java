package org.y3.jrun.view.model.competition;

import java.util.Comparator;

import org.y3.jrun.model.competition.Competition;

public class CompetitionComparator implements Comparator<Competition> {

	@Override
	public int compare(Competition competition1, Competition competition2) {
		if (competition1 == null && competition2 == null) {
			return 0;
		} else if (competition1 == null) {
			return 1;
		} else if (competition2 == null) {
			return -1;
		} else {
			String c1 = competition1.toString();
			String c2 = competition2.toString();
			return c1.compareTo(c2);
		}
	}

}
