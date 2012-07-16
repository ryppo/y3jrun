package org.y3.jrun.model.ageclassesdefinition;

import org.y3.jrun.model.ModelListModel;

public class AgeClassesDefinitionListModel extends ModelListModel {

	private static final long serialVersionUID = 1L;

	public AgeClassesDefinitionListModel(AgeClassesDefinition[] models) {
		super(models);
	}
	
	@Override
	public AgeClassesDefinition getElementAt(int arg0) {
		return (AgeClassesDefinition) super.getElementAt(arg0);
	}
	
	@Override
	public AgeClassesDefinition[] getModel() {
		return (AgeClassesDefinition[]) super.getModel();
	}

}
