package org.y3.jrun.view.model.participation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ComboBoxModel;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ModelListModel;
import org.y3.jrun.model.competition.Competition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.contact.ContactListModel;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.model.participation.Participation;
import org.y3.jrun.model.participation.ParticipationListModel;
import org.y3.jrun.model.participation.RichParticipation;
import org.y3.jrun.model.participation.RichParticipationReportable;
import org.y3.jrun.model.report.Report;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelForm;
import org.y3.jrun.view.model.ModelPanel;
import org.y3.jrun.view.model.ModelView_helper;
import org.y3.jrun.view.reporting.ReportFactory;
import org.y3.jrun.view.reporting.ReportingController;

public class ParticipationsPanel extends ModelPanel {

	private static final long serialVersionUID = 1L;
	
	private JComboBox jcb_competition, jcb_contact, jcb_discipline;
	ParticipationListModel model;

	public ParticipationsPanel(ApplicationController _controller,
			ApplicationFrame _appFrame) {
		super(_controller, _appFrame);
	}

	@Override
	protected void createModelForm() {
		modelForm = new ParticipationForm(controller, ModelForm.MODE.VIEWER, appFrame);
	}

	@Override
	public void bindData() throws IOException, ClassNotFoundException,
			SQLException {
		UIHelper.startWaiting(appFrame);
		Participation participation = (Participation) modelForm.getModel();
		jcb_competition.setModel((ComboBoxModel) controller
				.getAllCompetitions());
		jcb_contact.setModel((ComboBoxModel) controller.getAllContacts());
		jcb_discipline.setModel((ComboBoxModel) controller.getAllDisciplines());
		model = controller.getAllParticipations();
		fillParticipationsByMetaData();
		jp_modelList.setModel(model);
		if (model == null || model.getSize() == 0) {
			modelForm.setModel(null);
		} else {
			jp_modelList.setSelectedValue(participation, true);
		}
		modelForm.bindData();
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	public void bindDataForModelById(int modelId) throws IOException,
			ClassNotFoundException, SQLException {
		UIHelper.startWaiting(appFrame);
		ParticipationListModel model = controller.getParticipationById(modelId);
		jp_modelList.setModel(model);
		if (model == null || model.getSize() == 0) {
			modelForm.setModel(null);
		} else {
			jp_modelList.setSelectedValue(model.getElementAt(0), true);
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	public void bindDataByCompetitionId(int competitionId) throws IOException,
	ClassNotFoundException, SQLException {
		UIHelper.startWaiting(appFrame);
		ComboBoxModel cbmodel = jcb_competition.getModel();
		if (cbmodel != null && cbmodel instanceof ModelListModel) {
			((ModelListModel) cbmodel).setSelectedModelByID(competitionId);
			actionFilterModelList();
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	public void bindDataByContactId(int contactId) throws IOException,
	ClassNotFoundException, SQLException {
		UIHelper.startWaiting(appFrame);
		ComboBoxModel cbmodel = jcb_contact.getModel();
		if (cbmodel != null && cbmodel instanceof ModelListModel) {
			((ModelListModel) cbmodel).setSelectedModelByID(contactId);
			actionFilterModelList();
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	public void bindDataByDisciplineId(int disciplineId) throws IOException,
	ClassNotFoundException, SQLException {
		UIHelper.startWaiting(appFrame);
		ComboBoxModel cbmodel = jcb_discipline.getModel();
		if (cbmodel != null && cbmodel instanceof ModelListModel) {
			((ModelListModel) cbmodel).setSelectedModelByID(disciplineId);
			actionFilterModelList();
		}
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	protected void actionSortModels() {
		// TODO Auto-generated method stub

	}
	
	private void actionShowCompetition() {
		UIHelper.startWaiting(appFrame);
		Model model = jp_modelList.getSelectedModel();
		if (model != null && model instanceof Participation) {
			Participation participation = (Participation) model;
			appFrame.showCompetitionById(participation.getCompetitionId());
		} else {
			appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, Messages.getString(Messages.NO_PARTICIPATION_SELECTED_SELECT_ONE_FIRST), null);
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	private void actionShowCompetitionInstance() {
		UIHelper.startWaiting(appFrame);
		Model model = jp_modelList.getSelectedModel();
		if (model != null && model instanceof Participation) {
			Participation participation = (Participation) model;
			appFrame.showCompetitionInstanceById(participation);
		} else {
			appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, Messages.getString(Messages.NO_PARTICIPATION_SELECTED_SELECT_ONE_FIRST), null);
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	private void actionShowContact() {
		UIHelper.startWaiting(appFrame);
		Model model = jp_modelList.getSelectedModel();
		if (model != null && model instanceof Participation) {
			Participation participation = (Participation) model;
			appFrame.showContactById(participation.getContactId());
		} else {
			appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, Messages.getString(Messages.NO_PARTICIPATION_SELECTED_SELECT_ONE_FIRST), null);
		}
		UIHelper.stopWaiting(appFrame);
	}

	public void actionFilterModelList() {
		UIHelper.startWaiting(appFrame);
		try {
			Contact contact = null;
			Competition competition = null;
			Discipline discipline = null;
			Object filterCompetition = jcb_competition.getSelectedItem();
			Object filterContact = jcb_contact.getSelectedItem();
			Object filterDiscipline = jcb_discipline.getSelectedItem();
			if (filterCompetition != null
					&& filterCompetition instanceof Competition) {
				competition = (Competition) filterCompetition;
			}
			if (filterContact != null && filterContact instanceof Contact) {
				contact = (Contact) filterContact;
			}
			if (filterDiscipline != null && filterDiscipline instanceof Discipline) {
				discipline = ((Discipline) filterDiscipline);
			}
			model = controller.getAllParticipationsForCompetitionAndContactAndDiscipline(
					competition, contact, discipline);
			fillParticipationsByMetaData();
			Participation participation = (Participation) modelForm.getModel();
			jp_modelList.setModel(model);
			if (model == null || model.getSize() == 0) {
				modelForm.setModel(null);
			} else {
				jp_modelList.setSelectedValue(participation, true);
			}
			ModelView_helper.setSelectedModelInComboBox(jcb_competition, competition);
			ModelView_helper.setSelectedModelInComboBox(jcb_contact, contact);
			ModelView_helper.setSelectedModelInComboBox(jcb_discipline, discipline);
		} catch (Exception e) {
			appFrame.showUserMessage(e, null);
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	@Override
	public void actionNewModel() {
		super.actionNewModel();
		if (modelForm instanceof ParticipationForm) {
			((ParticipationForm) modelForm).setSelectedCompetitonAnContact(
					(Competition) jcb_competition.getSelectedItem(),
					(Contact) jcb_contact.getSelectedItem());
		}
	}
	
	private void fillParticipationsByMetaData() throws IOException, ClassNotFoundException, SQLException {
		UIHelper.startWaiting(appFrame);
		if (model != null && model.getSize() != 0) {
			for (int i = 0; i < model.getSize(); i++) {
				Participation participation = model.getElementAt(i);
				ContactListModel contacts = controller.getContactById(participation.getContactId());
				if (contacts != null && contacts.getSize() != 0) {
					String participantName = contacts.getElementAt(0).toString(); 
					participation.setParticipantName(participantName);
				}
			}
		}
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	public String getPanelTitle() {
		return Messages.getString(Messages.PARTICIPATIONS);
	}

	@Override
	public ActionListener getActionListenerForActionSaveModel() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionSaveModel();
				try {
					actionFilterModelList();
				} catch (Exception e) {
					appFrame.showUserMessage(e, null);
				}
			}
		};
	}
	
	public ActionListener getActionListenerForActionShowCompetition() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionShowCompetition();
			}
		};
	}
	
	public ActionListener getActionListenerForActionShowCompetitionInstance() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionShowCompetitionInstance();
			}
		};
	}
	
	public ActionListener getActionListenerForActionShowContact() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionShowContact();
			}
		};
	}
	
	public ActionListener getActionListenerForActionFilterModelList() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionFilterModelList();
			}
		};
	}

	@Override
	protected void addFunctionsToMenuAndButtonPanel() {
		
		
		addFunctionToMenuAndButtonPanel(IconDictionary.getImageIcon(IconDictionary.CERTIFICATION), Messages.getString(Messages.REPORT_CERTIFICATION), getActionListenerForActionReportCertification());
		
		jcb_competition = new JComboBox();
		jp_buttons.add(jcb_competition);
		jcb_discipline = new JComboBox();
		jp_buttons.add(jcb_discipline);
		jcb_contact = new JComboBox();
		jp_buttons.add(jcb_contact);
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.FILTER), 
				Messages.getString(Messages.FILTER_LIST), getActionListenerForActionFilterModelList());
		
		addSeparatorToMenuAndButtonPanel();
		
		addFunctionToMenuAndButtonPanelForActionNewModel();
		addFunctionToMenuAndButtonPanelForActionRemoveModel();
		addFunctionToMenuAndButtonPanelForActionSaveModel();
		
		addSeparatorToMenuAndButtonPanel();
		
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.CONTACT), 
				Messages.getString(Messages.SHOW_CONTACT), getActionListenerForActionShowContact());
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.DISCIPLINE), 
				Messages.getString(Messages.SHOW_DISCIPLINE), getActionListenerForActionShowDisciplineForSelectedParticipation());
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.COMPETITION), 
				Messages.getString(Messages.SHOW_COMPETITION), getActionListenerForActionShowCompetition());
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.COMPETITION_GRID), 
				Messages.getString(Messages.SHOW_COMPETITION_GRID), getActionListenerForActionShowCompetitionInstance());
	}
	
	protected void actionShowDisciplineForSelectedParticipation() {
		Object o = jp_modelList.getSelectedModel();
		if (o != null && o instanceof Participation) {
			Participation p = (Participation) o;
			if (p.getDisciplineId() != 0) {
				appFrame.showDisciplineById(p.getDisciplineId());
			} else {
				appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, Messages.getString(Messages.NO_DISCIPLINE_RELATED), null);
			}
		} else {
			appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, Messages.getString(Messages.NO_PARTICIPATION_SELECTED_SELECT_ONE_FIRST), null);
		}
	}
	
	private ActionListener getActionListenerForActionShowDisciplineForSelectedParticipation() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionShowDisciplineForSelectedParticipation();
			}
		};
	}
	
	private ActionListener getActionListenerForActionReportCertification() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionReportCertification();
			}
		};
	}
	
	private void actionReportCertification() {
		UIHelper.startWaiting(appFrame);
		Model[] models = jp_modelList.getSelectedModels();
		if (models != null && models.length > 0) {
			Participation[] participations = new Participation[models.length];
			int modelNo = 0;
			for (Model model: models) {
				if (model instanceof Participation) {
					participations[modelNo] = (Participation) model;
					modelNo++;
				}
			}
			try {
				RichParticipation[] richParticipations = controller.getRichParticipationsForParticipations(participations);
				if (richParticipations != null && richParticipations.length != 0) {
					Report[] reports = new Report[richParticipations.length];
					int reportNo = 0;
					for (RichParticipation richP: richParticipations) {
						Report report = ReportFactory.createCertificationReport(new RichParticipationReportable(new RichParticipation[]{richP}), richP.getParticipant());
						reports[reportNo] = report;
						reportNo++;
					}
					ReportingController.report(reports, appFrame);
				}
			} catch (SQLException e) {
				appFrame.showUserMessage(e, participations[0]);
			}
		}
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	public JPanel getSpecificStatisticsPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Icon getIcon() {
		return IconDictionary.getImageIcon(IconDictionary.PARTICIPATIONS);
	}

}
