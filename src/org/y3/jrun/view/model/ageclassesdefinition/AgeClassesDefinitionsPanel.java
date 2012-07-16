package org.y3.jrun.view.model.ageclassesdefinition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinition;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinitionListModel;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinitionReportable;
import org.y3.jrun.model.ageclassesdefinition.RichAgeClassesDefinition;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelForm;
import org.y3.jrun.view.model.ModelPanel;
import org.y3.jrun.view.reporting.ReportFactory;
import org.y3.jrun.view.reporting.ReportingController;

public class AgeClassesDefinitionsPanel extends ModelPanel {

	private static final long serialVersionUID = 1L;

	public AgeClassesDefinitionsPanel(ApplicationController _controller,
			ApplicationFrame _appFrame) {
		super(_controller, _appFrame);
	}

	@Override
	public String getPanelTitle() {
		return Messages.getString(Messages.AGECLASSESDEFINITIONS);
	}

	@Override
	protected void createModelForm() {
		modelForm = new AgeClassesDefinitionForm(controller, ModelForm.MODE.VIEWER, appFrame);
	}

	@Override
	protected void addFunctionsToMenuAndButtonPanel() {
		addFunctionToMenuAndButtonPanelForActionCreationAgeClassesDefinitionReport();
		addSeparatorToMenuAndButtonPanel();
		addFunctionToMenuAndButtonPanelForActionNewModel();
		addFunctionToMenuAndButtonPanelForActionRemoveModel();
		addFunctionToMenuAndButtonPanelForActionSaveModel();
		addFunctionToMenuAndButtonPanelForActionImportAgeClassesDefinition();
		addFunctionToMenuAndButtonPanelForActionExportAgeClassesDefinition();
		addSeparatorToMenuAndButtonPanel();
		addFunctionToMenuAndButtonPanelForActionNewAgeClass();
		addFunctionToMenuAndButtonPanelForActionRemoveAgeClass();
		addSeparatorToMenuAndButtonPanel();
		addFunctionToMenuAndButtonPanelForActionShowRelatedCompetitions();
	}

	@Override
	public void bindData() throws IOException, ClassNotFoundException,
			SQLException {
		UIHelper.startWaiting(appFrame);
		AgeClassesDefinitionListModel modelList = controller.getAllAgeClassesDefinitions();
		AgeClassesDefinition model = (AgeClassesDefinition) modelForm.getModel();
		jp_modelList.setModel(modelList);
		if (modelList == null || modelList.getSize() == 0) {
			modelForm.setModel(null);
		} else {
			jp_modelList.setSelectedValue(model, true);
		}
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	public void bindDataForModelById(int modelId) throws IOException,
			ClassNotFoundException, SQLException {
	}

	@Override
	protected void actionSortModels() {
		
	}
	
	public void addFunctionToMenuAndButtonPanelForActionCreationAgeClassesDefinitionReport() {
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.REPORT), 
				Messages.getString(Messages.REPORT_AGECLASSES_DEFINITION), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionCreateAgeClassesDefinitionReport();
			}
		});
	}
	
	public void addFunctionToMenuAndButtonPanelForActionNewAgeClass() {
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.NEW), 
				Messages.getString(Messages.NEW), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionNewAgeClass();
			}
		});
	}
	
	public void addFunctionToMenuAndButtonPanelForActionRemoveAgeClass() {
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.REMOVE), 
				Messages.getString(Messages.REMOVE), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionRemoveAgeClass();
			}
		});
	}
	
	public void actionNewAgeClass() {
		((AgeClassesDefinitionForm) modelForm).actionAddNewAgeClass();
	}
	
	public void actionCreateAgeClassesDefinitionReport() {
		UIHelper.startWaiting(appFrame);
		Model selectedModel = jp_modelList.getSelectedModel();
		try {
			if (selectedModel != null
					&& selectedModel instanceof AgeClassesDefinition) {
				AgeClassesDefinition ageClassesDefinition = (AgeClassesDefinition) selectedModel;
				RichAgeClassesDefinition richAgeClassesDefinition = controller
						.getRichAgeClassesDefinitionByAgeClassesDefinition(ageClassesDefinition);
				AgeClassesDefinitionReportable reportable = new AgeClassesDefinitionReportable(richAgeClassesDefinition);
				ReportingController.report(ReportFactory.createAgeClassesDefinitionReport(reportable), appFrame);
			} else {
				appFrame.showUserMessage(
						JOptionPane.ERROR_MESSAGE,
						Messages.getString(Messages.NO_PARTICIPATION_SELECTED_SELECT_ONE_FIRST),
						selectedModel);
			}
		} catch (Exception ex) {
			appFrame.showUserMessage(ex, selectedModel);
		} finally {
			UIHelper.stopWaiting(appFrame);
		}
	}
	
	public void actionRemoveAgeClass() {
		((AgeClassesDefinitionForm) modelForm).actionRemoveAgeClass();
	}

	@Override
	public JPanel getSpecificStatisticsPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Icon getIcon() {
		return IconDictionary.getImageIcon(IconDictionary.AGECLASSES_DEFINITION);
	}
	
	public void actionImportAgeClassesDefinitions() {
		try{
			UIHelper.startWaiting(appFrame);
			AgeClassesDefinitionExchangeDialog importAgeClassesDefinitionDialog =
					new AgeClassesDefinitionExchangeDialog(appFrame, controller, AgeClassesDefinitionExchangeDialog.MODE.IMPORT);
			importAgeClassesDefinitionDialog.setVisible(true);
			if (importAgeClassesDefinitionDialog.isImported()) {
				bindData();
			}
		} catch (Exception e) {
			appFrame.showUserMessage(e, null);
		} finally {
			UIHelper.stopWaiting(appFrame);
		}
	}
	
	public void actionExportAgeClassesDefinitions() {
		try {
			UIHelper.startWaiting(appFrame);
			AgeClassesDefinition[] selectedModels = getSelectedAgeClassesDefinitions();
			if (selectedModels != null && selectedModels.length > 0) {
				RichAgeClassesDefinition[] richModels;
				richModels = controller
						.getRichAgeClassesDefinitionsByAgeClassesDefinitions(selectedModels);
				AgeClassesDefinitionExchangeDialog exportAgeClassesDefinitionDialog =
						new AgeClassesDefinitionExchangeDialog(appFrame, controller, AgeClassesDefinitionExchangeDialog.MODE.EXPORT);
				exportAgeClassesDefinitionDialog.setModel(richModels);
				exportAgeClassesDefinitionDialog.setVisible(true);
			} else {
				appFrame.showUserMessage(
						JOptionPane.WARNING_MESSAGE,
						Messages.getString(Messages.NO_AGECLASSESDEFINITION_SELECTED_SELECT_ONE_FIRST),
						null);
			}
		} catch (Exception e) {
			appFrame.showUserMessage(e, null);
		} finally {
			UIHelper.stopWaiting(appFrame);
		}
	}
	
	public void actionShowRelatedCompetitions() {
		try {
			UIHelper.startWaiting(appFrame);
			AgeClassesDefinition[] selectedModels = getSelectedAgeClassesDefinitions();
			if (selectedModels != null && selectedModels.length > 0) {
				int[] competitionIds = controller.getCompetitionIdsByAgeClassesDefinitions(selectedModels);
				appFrame.showCompetitionByIds(competitionIds);
			} else {
				appFrame.showUserMessage(
						JOptionPane.WARNING_MESSAGE,
						Messages.getString(Messages.NO_AGECLASSESDEFINITION_SELECTED_SELECT_ONE_FIRST),
						null);
			}
		} catch (Exception e) {
			appFrame.showUserMessage(e, null);
		} finally {
			UIHelper.stopWaiting(appFrame);
		}
	}
	
	public void addFunctionToMenuAndButtonPanelForActionImportAgeClassesDefinition() {
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.IMPORT), 
				Messages.getString(Messages.IMPORT), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionImportAgeClassesDefinitions();
			}
		});
	}
	
	public void addFunctionToMenuAndButtonPanelForActionExportAgeClassesDefinition() {
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.EXPORT), 
				Messages.getString(Messages.EXPORT), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionExportAgeClassesDefinitions();
			}
		});
	}
	
	public void addFunctionToMenuAndButtonPanelForActionShowRelatedCompetitions() {
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.COMPETITION), 
				Messages.getString(Messages.SHOW_COMPETITION), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionShowRelatedCompetitions();
			}
		});
	}
	
	public AgeClassesDefinition[] getSelectedAgeClassesDefinitions() {
		AgeClassesDefinition[] selectedACDs = null;
		Model[] selectedModels = jp_modelList.getSelectedModels();
		if (selectedModels != null && selectedModels.length > 0) {
			int countACDs = 0;
			for (Model model: selectedModels) {
				if (model instanceof AgeClassesDefinition) {
					countACDs++;
				}
			}
			selectedACDs = new AgeClassesDefinition[countACDs];
			int pos = 0;
			for (Model model: selectedModels) {
				if (model instanceof AgeClassesDefinition) {
					selectedACDs[pos] = (AgeClassesDefinition) model;
					pos++;
				}
			}
		}
		return selectedACDs;
	}

}
