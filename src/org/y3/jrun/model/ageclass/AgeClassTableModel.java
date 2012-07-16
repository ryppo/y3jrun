package org.y3.jrun.model.ageclass;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class AgeClassTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int columnIndex) {
		return AgeClass.class;
	}
	
	public AgeClass[] getModel() {
		Object objects[] = super.dataVector.toArray();
		ArrayList<AgeClass> ageClasses = new ArrayList<AgeClass>(0);
		for (Object o: objects) {
			if (o instanceof AgeClass) {
				ageClasses.add((AgeClass) o);
			}
		}
		if (ageClasses.size() != 0) {
			return (AgeClass[]) ageClasses.toArray();
		} else {
			return null;
		}
	}

}
