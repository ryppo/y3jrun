package org.y3.jrun.view.model.discipline;

import java.util.Comparator;

import org.y3.jrun.model.discipline.Discipline;

public class DisciplineComparator implements Comparator<Discipline> {

    public compareBy COMPARE_MODE = compareBy.NAME;
    
    public enum compareBy { NAME, LENGTH }
    
    @Override
    public int compare(Discipline d1, Discipline d2) {
	if (d1 == null && d2 == null) {
	    return 0;
	} else if (d1 == null) {
	    return 1;
	} else if (d2 == null) {
	    return -1;
	} else {
	    String s1 = null;
	    String s2 = null;
	    switch (COMPARE_MODE) {
	    case NAME:
		s1 = d1.getName();
		s2 = d2.getName();
		break;
	    case LENGTH:
		s1 = d1.getLength();
		s2 = d2.getLength();
		break;
	    default:
		return 0;
	    }
	    return s1.compareTo(s2);
	}
    }

}
