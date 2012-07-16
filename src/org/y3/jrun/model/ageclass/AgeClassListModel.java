package org.y3.jrun.model.ageclass;

import org.y3.jrun.model.ModelListModel;

public class AgeClassListModel extends ModelListModel {

	private static final long serialVersionUID = 1L;

	public AgeClassListModel(AgeClass[] models) {
		super(models);
	}
	
	@Override
	public AgeClass getElementAt(int arg0) {
		return (AgeClass) super.getElementAt(arg0);
	}
	
	@Override
	public AgeClass[] getModel() {
		return (AgeClass[]) super.getModel();
	}

}
