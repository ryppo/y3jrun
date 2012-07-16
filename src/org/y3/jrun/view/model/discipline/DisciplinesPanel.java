package org.y3.jrun.view.model.discipline;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.model.discipline.DisciplineListModel;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelForm;
import org.y3.jrun.view.model.ModelPanel;

public class DisciplinesPanel extends ModelPanel {

	private static final long serialVersionUID = 1L;
	private DisciplineComparator disciplineComparator;
	private String sortedBy = Messages.getString(Messages.SORTED_BY) + " ";

	public DisciplinesPanel(ApplicationController _controller,
			ApplicationFrame _appFrame) {
		super(_controller, _appFrame);
	}

	@Override
	public String getPanelTitle() {
		return Messages.getString(Messages.DISCIPLINES);
	}

	@Override
	protected void createModelForm() {
		modelForm = new DisciplineForm(ModelForm.MODE.VIEWER);

	}

	@Override
	protected void addFunctionsToMenuAndButtonPanel() {
	    addFunctionToMenuAndButtonPanel(
			IconDictionary.getImageIcon(IconDictionary.SORT), Messages.getString(Messages.SORT), getActionListenerForActionSortModels());
		addFunctionToMenuAndButtonPanelForActionNewModel();
		addFunctionToMenuAndButtonPanelForActionRemoveModel();
		addFunctionToMenuAndButtonPanelForActionSaveModel();
		addSeparatorToMenuAndButtonPanel();
		addFunctionToMenuAndButtonPanel(IconDictionary.getImageIcon(IconDictionary.PARTICIPATIONS), 
				Messages.getString(Messages.SHOW_PARTICIPATIONS), 
				getActionListenerForActionShowParticipationForSelectedDiscipline());
	}

	@Override
	public void bindData() throws IOException, ClassNotFoundException,
			SQLException {
		UIHelper.startWaiting(appFrame);
		Discipline discipline = (Discipline) modelForm.getModel();
		DisciplineListModel model = controller.getAllDisciplines();
		jp_modelList.setModel(model);
		if (model == null || model.getSize() == 0) {
			modelForm.setModel(null);
		} else {
			jp_modelList.setSelectedValue(discipline, true);
		}
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	public void bindDataForModelById(int modelId) throws IOException,
			ClassNotFoundException, SQLException {
		UIHelper.startWaiting(appFrame);
		DisciplineListModel model = controller.getDisciplineById(modelId);
		jp_modelList.setModel(model);
		if (model == null || model.getSize() == 0) {
			modelForm.setModel(null);
		} else {
			jp_modelList.setSelectedValue(model.getElementAt(0), true);
		}
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	protected void actionSortModels() {
		UIHelper.startWaiting(appFrame);
		// use next sorting mode
				if (disciplineComparator == null) {
				    disciplineComparator = new DisciplineComparator();
				} else {
					if (disciplineComparator.COMPARE_MODE
							.equals(DisciplineComparator.compareBy.NAME)) {
						disciplineComparator.COMPARE_MODE = DisciplineComparator.compareBy.LENGTH;
					} else if (disciplineComparator.COMPARE_MODE
							.equals(DisciplineComparator.compareBy.LENGTH)) {
					    disciplineComparator.COMPARE_MODE = DisciplineComparator.compareBy.NAME;
					}
				}
				label_sortedBy.setText(sortedBy
						+ Messages.getString(disciplineComparator.COMPARE_MODE.toString()));
				// sort the contacts
				((DisciplineListModel) jp_modelList.getModel()).sort(disciplineComparator);
				// update UI
				jp_modelList.repaint();
				UIHelper.stopWaiting(appFrame);
	}
	
	public void actionShowParticipationForSelectedDiscipline() {
		Model object = jp_modelList.getSelectedModel();
		if (object != null && object instanceof Discipline) {
			appFrame.showParticipationsByDisciplineId(object.getId());
		} else {
			appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, 
					Messages.getString(Messages.NO_DISCIPLINE_SELECTED_SELECT_ONE), null);
		}
	}
	
	public ActionListener getActionListenerForActionShowParticipationForSelectedDiscipline() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionShowParticipationForSelectedDiscipline();
				
			}
		};
	}
	
	public ActionListener getActionListenerForActionSortModels() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionSortModels();
			}
		};
	}

	@Override
	public JPanel getSpecificStatisticsPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Icon getIcon() {
		return IconDictionary.getImageIcon(IconDictionary.DISCIPLINE);
	}

}
