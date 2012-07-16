package org.y3.jrun.view.model.competition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.competition.Competition;
import org.y3.jrun.model.competition.CompetitionListModel;
import org.y3.jrun.model.competition.CompetitionReportable;
import org.y3.jrun.model.competition.RichCompetition;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelForm;
import org.y3.jrun.view.model.ModelPanel;
import org.y3.jrun.view.reporting.ReportFactory;
import org.y3.jrun.view.reporting.ReportingController;

public class CompetitionPanel extends ModelPanel {
	
	public CompetitionPanel(ApplicationController _controller,
			ApplicationFrame _appFrame) {
		super(_controller, _appFrame);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void createModelForm() {
		modelForm = new CompetitionForm(controller, ModelForm.MODE.VIEWER, appFrame);
	}

	@Override
	public void bindData() throws IOException, ClassNotFoundException,
			SQLException {
		UIHelper.startWaiting(appFrame);
		appFrame.removeAllCompetitionGridPanelTabs();
		Competition comp = (Competition) modelForm.getModel();
		CompetitionListModel competitions = controller.getAllCompetitions(); 
		jp_modelList.setModel(competitions);
		modelForm.bindData();
		if (competitions != null && competitions.getSize() != 0) {
			Arrays.sort(competitions.getModel(), new CompetitionComparator());
			for (int i = 0; i < competitions.getSize(); i++) {
				Competition competition = (Competition) competitions.getElementAt(i);
				appFrame.addCompetitionGridPanelTab(competition.toString(), new CompetitionGridPanel(competition, controller, appFrame));
			}
			jp_modelList.setSelectedValue(comp, true);
		} else {
			modelForm.setModel(null);
		}
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	public void bindDataForModelById(int modelId) throws IOException,
			ClassNotFoundException, SQLException {
		UIHelper.startWaiting(appFrame);
		jp_modelList.setModel(controller.getCompetitionById(modelId));
		jp_modelList.setSelectedIndex(0);
		UIHelper.stopWaiting(appFrame);
	}
	
	public void bindDataForModelByIds(int[] modelIds) throws IOException,
			ClassNotFoundException, SQLException {
		UIHelper.startWaiting(appFrame);
		jp_modelList.setModel(controller.getCompetitionsByIds(modelIds));
		jp_modelList.setSelectedIndex(0);
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	protected void actionSaveModel() {
		UIHelper.startWaiting(appFrame);
		Competition model = (Competition) modelForm.getModel();
		try {
			if (controller.saveModel(model)) {
				appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE,
						Messages.getString(Messages.COMPETITION_SUCCESSFULLY_SAVED), model);

			} else {
				appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE,
						Messages.getString(Messages.COMPETITION_NOT_SAVED), model);
			}
		} catch (Exception e) {
			appFrame.showUserMessage(e, model);
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	public void actionImportContactsFromRegistrations() {
		UIHelper.startWaiting(appFrame);
		Competition model = (Competition) modelForm.getModel();
		int importedContacts = 0;
		try {
			importedContacts = controller.importRegisteredContactsForCompetition(model);
			appFrame.refreshAndShowContacts(true);
			appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE, importedContacts + " " + Messages.getString(Messages.CONTACTS_ADDED_BY_DOWNLOAD), model);
		} catch (Exception e) {
			appFrame.showUserMessage(e, model);
			appFrame.showUserMessage(JOptionPane.ERROR_MESSAGE, Messages.getString(Messages.DOWNLOAD_OF_CONTACTS_FAILED), model);
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	public void actionImportDisciplinesFromRegistrations() {
		UIHelper.startWaiting(appFrame);
		Competition model = (Competition) modelForm.getModel();
		int importedDisciplines = 0;
		try {
			importedDisciplines = controller.importRegisteredDisciplinesForCompetition(model);
			appFrame.refreshAndShowDisciplines(true);
			appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE, importedDisciplines + " " + Messages.getString(Messages.DISCIPLINES_ADDED_BY_DOWNLOAD), model);
		} catch (Exception e) {
			appFrame.showUserMessage(e, model);
			appFrame.showUserMessage(JOptionPane.ERROR_MESSAGE, Messages.getString(Messages.DOWNLOAD_OF_DISCIPLINES_FAILED), model);
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	public void actionImportParticipationsFromRegistrations() {
		UIHelper.startWaiting(appFrame);
		Competition model = (Competition) modelForm.getModel();
		RichCompetition richCompetition = new RichCompetition(null, model);
		int importedParticipations = 0;
		try {
			importedParticipations = controller.importRegisteredParticipationsForCompetition(richCompetition);
			appFrame.refreshAndShowParticipations(true);
			appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE, importedParticipations + " " + Messages.getString(Messages.PARTICIPATIONS_ADDED_BY_DOWNLOAD), model);
		} catch (Exception e) {
			appFrame.showUserMessage(e, model);
			appFrame.showUserMessage(JOptionPane.ERROR_MESSAGE, Messages.getString(Messages.DOWNLOAD_OF_PARTICIPATIONS_FAILED), model);
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	public void actionImportAllFromRegistrations() {
		UIHelper.startWaiting(appFrame);
		Competition model = (Competition) modelForm.getModel();
		int importedContacts = 0;
		int importedDisciplines = 0;
		int importedParticipations = 0;
		RichCompetition richCompetition = new RichCompetition(null, model);
		try {
			String lf = "<br>";
			String successMessage = "<html>";
			importedContacts = controller.importRegisteredContactsForCompetition(model);
			successMessage += importedContacts + " " + Messages.getString(Messages.CONTACTS_ADDED_BY_DOWNLOAD);
			importedDisciplines = controller.importRegisteredDisciplinesForCompetition(model);
			successMessage += lf + importedDisciplines + " " + Messages.getString(Messages.DISCIPLINES_ADDED_BY_DOWNLOAD);
			importedParticipations = controller.importRegisteredParticipationsForCompetition(richCompetition);
			successMessage += lf + importedParticipations + " " + Messages.getString(Messages.PARTICIPATIONS_ADDED_BY_DOWNLOAD);
			appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE, successMessage, model);
		} catch (Exception e) {
			appFrame.showUserMessage(e, model);
		}
		UIHelper.stopWaiting(appFrame);
	}

	protected void actionShowParticipations() {
		Model object = jp_modelList.getSelectedModel();
		if (object != null && object instanceof Competition) {
			Competition competition = (Competition) object;
			appFrame.showParticipationsByCompetitionId(competition.getId());
		} else {
			appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE,
					Messages.getString(Messages.NO_COMPETITION_SELECTED__PARTICIPATION_CANNOT_BE_SHOWN), null);
		}
	}
	
	protected void actionShowCompetitionGrid() {
		Model object = jp_modelList.getSelectedModel();
		try {
			if (object != null && object instanceof Competition) {
				Competition competition = (Competition) object;
				appFrame.showCompetitionInstanceById(competition.getId());
			} else {
				appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE,
						Messages.getString(Messages.NO_COMPETITION_SELECTED__PARTICIPATION_CANNOT_BE_SHOWN), null);
			}			
		} catch (Exception exception) {
			appFrame.showUserMessage(exception, object);
		}
	}

	@Override
	protected void actionSortModels() {
	}

	@Override
	public String getPanelTitle() {
		return Messages.getString(Messages.COMPETITIONS);
	}

	public ActionListener getActionListenerForActionShowParticipations() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionShowParticipations();
			}
		};
	}
	
	public ActionListener getActionListenerForActionShowCompetitionGrid() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionShowCompetitionGrid();	
			}
		};
	}
	
	public ActionListener getActionListenerForActionImportContactsForRegistrations() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionImportContactsFromRegistrations();
			}
		};
	}
	
	public ActionListener getActionListenerForActionImportDisciplinesForRegistrations() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionImportDisciplinesFromRegistrations();
			}
		};
	}
	
	public ActionListener getActionListenerForActionImportParticipationsForRegistrations() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionImportParticipationsFromRegistrations();
			}
		};
	}
	
	public ActionListener getActionListenerForActionImportAllForRegistrations() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionImportAllFromRegistrations();
			}
		};
	}

	@Override
	protected void addFunctionsToMenuAndButtonPanel() {
		addFunctionToMenuAndButtonPanelForActionNewModel();
		addFunctionToMenuAndButtonPanelForActionRemoveModel();
		addFunctionToMenuAndButtonPanelForActionSaveModel();
		addSeparatorToMenuAndButtonPanel();
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.PARTICIPATIONS), 
				Messages.getString(Messages.SHOW_PARTICIPATIONS), getActionListenerForActionShowParticipations());
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.COMPETITION_GRID), 
				Messages.getString(Messages.SHOW_COMPETITION_GRID), getActionListenerForActionShowCompetitionGrid());
		addSeparatorToMenuAndButtonPanel();
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.DOWNLOAD_CONTACTS), 
				Messages.getString(Messages.DOWNLOAD_CONTACTS), getActionListenerForActionImportContactsForRegistrations());
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.DOWNLOAD_DISCIPLINES), 
				Messages.getString(Messages.DOWNLOAD_DISCIPLINES), getActionListenerForActionImportDisciplinesForRegistrations());
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.DOWNLOAD_PARTICIPATIONS), 
				Messages.getString(Messages.DOWNLOAD_PARTICIPATIONS), getActionListenerForActionImportParticipationsForRegistrations());
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.DOWNLOAD_ALL), 
				Messages.getString(Messages.DOWNLOAD_ALL), getActionListenerForActionImportAllForRegistrations());
	}
	
	@Override
	public void actionReportModelList() {
		UIHelper.startWaiting(appFrame);
		CompetitionReportable reportable = new CompetitionReportable(jp_modelList.getModel(), getPanelTitle());
		ReportingController.report(ReportFactory.createCompetitionListReport(reportable), appFrame);
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	public JPanel getSpecificStatisticsPanel() {
		return new CompetitionStatisticsPanel(controller, (Competition) jp_modelList.getSelectedModel(), appFrame);
	}

	@Override
	public Icon getIcon() {
		return IconDictionary.getImageIcon(IconDictionary.COMPETITION);
	}

}
