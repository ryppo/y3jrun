package org.y3.jrun.model.discipline;

import java.util.Arrays;

import org.y3.jrun.model.ModelListModel;
import org.y3.jrun.view.model.discipline.DisciplineComparator;

public class DisciplineListModel extends ModelListModel {

	private static final long serialVersionUID = 1L;
	
	public DisciplineListModel(Discipline[] model) {
		super(model);
	}
	
	@Override
	public Discipline getElementAt(int arg0) {
		return (Discipline) super.getElementAt(arg0);
	}

	public Discipline[] getModel() {
		return (Discipline[]) super.getModel();
	}
	
	public void sort(DisciplineComparator comparator) {
		Arrays.sort(getModel(), comparator);
	}
	
}
