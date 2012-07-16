package org.y3.jrun.view.model.participation;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.competition.Competition;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.model.participation.Participation;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.model.ModelForm;
import org.y3.jrun.view.model.ModelFormDialog;

public class ParticipationFormDialog extends ModelFormDialog {

	private static final long serialVersionUID = 1L;
	private Discipline discipline;
	private Competition competition;
	
	public ParticipationFormDialog(Competition _competition, Discipline _discipline, Participation _participation, ApplicationFrame parent, ApplicationController _controller) {
		super(_participation, parent, _controller);
		discipline = _discipline;
		competition = _competition;
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			pack();
			setLocationRelativeTo(appFrame);
		}
		super.setVisible(visible);
	}
	
	@Override
	public void bindData() {
		UIHelper.startWaiting(appFrame);
		modelForm.bindData();
		if (model == null) {
			model = new Participation();
		}
		((Participation) model).setCompetitionId(competition.getId());
		if (discipline != null) {
			((Participation) model).setDisciplineId(discipline.getId());
		}
		modelForm.setModel(model);
		UIHelper.stopWaiting(appFrame);
	}
	
	@Override
	public boolean actionSave() {
		UIHelper.startWaiting(appFrame);
		Participation participation = (Participation) modelForm.getModel();
		try {
			controller.saveModel(participation);
			return true;
		} catch (Exception e) {
			appFrame.showUserMessage(e, participation);
		}
		UIHelper.stopWaiting(appFrame);
		return false;
	}
	
	@Override
	public ModelForm getModelForm() {
		if (modelForm == null) {
			modelForm = new ParticipationForm(controller, ModelForm.MODE.EDITOR, appFrame);
		}
		return modelForm;
	}

	@Override
	public Participation getModel() {
		if (modelForm != null) {
			return (Participation) modelForm.getModel();
		} else {
			return null;
		}
	}
}
