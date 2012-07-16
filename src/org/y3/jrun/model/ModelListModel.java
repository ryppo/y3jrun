package org.y3.jrun.model;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ListModel;

import org.apache.commons.lang.ArrayUtils;

public abstract class ModelListModel extends DefaultComboBoxModel implements
		ListModel {

	private static final long serialVersionUID = 1L;
	
	private Model[] models;
	
	public ModelListModel(Model[] models) {
		this.models = models;
	}
	
	public void addElement(Object model) {
		if (model == null) {
			model = new Model[0];
		}
		if (model instanceof Model) {
			models = (Model[]) ArrayUtils.add(models, model);
		}
	}
	
	@Override
	public Model getElementAt(int arg0) {
		Model returnValue = null;
		if (models != null) {
			returnValue = models[arg0];
		}
		return returnValue;
	}
	
	@Override
	public int getSize() {
		int returnValue = 0;
		if (models != null) {
			returnValue = models.length;
		}
		return returnValue;
	}
	
	public Model[] getModel() {
		return models;
	}
	
	public void setSelectedModelByID(int modelID) {
		for (int i = 0; i < getSize(); i++) {
			Model model = (Model) getElementAt(i);
			if (model.getId() == modelID) {
				setSelectedItem(model);
				break;
			} else {
				setSelectedItem(null);
			}
		}
	}
	
	public Model getItemByModelId(int modelID) {
		for (int i = 0; i < getSize(); i++) {
			Model model = (Model) getElementAt(i);
			if (model.getId() == modelID) {
				return model;
			}
		}
		return null;
	}

}
