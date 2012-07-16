package org.y3.jrun.view.model;

import javax.swing.JComboBox;

import org.y3.jrun.model.Model;
import org.y3.jrun.model.ModelListModel;

public class ModelView_helper {

	public static void setSelectedModelInComboBox(JComboBox comboBox, Model model) {
		if (comboBox != null && model != null) {
			if (comboBox.getModel() instanceof ModelListModel) {
				((ModelListModel) comboBox.getModel()).setSelectedModelByID(model.getId());
			}
		}
	}
	
}
