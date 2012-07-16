package org.y3.jrun.view.model.discipline;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.model.ModelForm;
import org.y3.jrun.view.model.ModelFormDialog;

public class DisciplineFormDialog extends ModelFormDialog {

	private static final long serialVersionUID = 1L;
	
	public DisciplineFormDialog(Discipline _model,
			ApplicationFrame applicationFrame,
			ApplicationController applicationController) {
		super(_model, applicationFrame, applicationController);
	}

	@Override
	public ModelForm getModelForm() {
			modelForm = new DisciplineForm(ModelForm.MODE.EDITOR);
		return modelForm;
	}

	@Override
	public boolean actionSave() {
		Discipline discipline = (Discipline) modelForm.getModel();
		try {
			controller.saveModel(discipline);
			return true;
		} catch (Exception e) {
			appFrame.showUserMessage(e, discipline);
		}
		return false;
	}

	@Override
	public Discipline getModel() {
		if (modelForm != null) {
			return (Discipline) modelForm.getModel();
		} else {
			return null;
		}
	}

}
