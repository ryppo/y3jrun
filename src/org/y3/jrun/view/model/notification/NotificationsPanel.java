package org.y3.jrun.view.model.notification;

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
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinition;
import org.y3.jrun.model.competition.Competition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.model.notification.Notification;
import org.y3.jrun.model.notification.NotificationListModel;
import org.y3.jrun.model.participation.Participation;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelPanel;
import org.y3.jrun.view.reporting.ReportFactory;
import org.y3.jrun.view.reporting.ReportingController;

public class NotificationsPanel extends ModelPanel {

	private static final long serialVersionUID = 1L;

	public NotificationsPanel(ApplicationController _controller,
			ApplicationFrame _appFrame) {
		super(_controller, _appFrame);
	}

	public void bindDataForModel(Model model) throws IOException,
			ClassNotFoundException, SQLException {
		UIHelper.startWaiting(appFrame);
		NotificationListModel notificationsListModel = controller
				.getAllNotificationsForModel(model);
		jp_modelList.setModel(notificationsListModel);
		if (jp_modelList.getModel() != null
				&& jp_modelList.getModel().getElementAt(0) != null) {
			jp_modelList.setSelectedIndex(0);
		} else {
			appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE,
					"No notifications for model available.", model);
		}
		UIHelper.stopWaiting(appFrame);
	}

	public void bindData() throws IOException, ClassNotFoundException,
			SQLException {
		UIHelper.startWaiting(appFrame);
		NotificationListModel model = controller.getAllNotifications();
		Arrays.sort(model.getModel(), new NotificationComparator());
		jp_modelList.setModel(model);
		UIHelper.stopWaiting(appFrame);
	}

	private void actionShowModel() {
		Model object = jp_modelList.getSelectedModel();
		if (object != null && object instanceof Notification) {
			Notification notif = (Notification) object;
			int modelId = notif.getRelatedObjectId();
			if (notif.getRelatedObjectType() != null) {
				if (notif.getRelatedObjectType().equals(
						Contact.class.getSimpleName())) {
					appFrame.showContactById(modelId);
				} else if (notif.getRelatedObjectType().equals(
						Competition.class.getSimpleName())) {
					appFrame.showCompetitionById(modelId);
				} else if (notif.getRelatedObjectType().equals(
						Participation.class.getSimpleName())) {
					appFrame.showParticipationById(modelId);
				} else if (notif.getRelatedObjectType().equals(
						Discipline.class.getSimpleName())) {
					appFrame.showDisciplineById(modelId);
				} else if (notif.getRelatedObjectType().equals(
						AgeClassesDefinition.class.getSimpleName())) {
					appFrame.showAgeClassesDefinitionById(modelId);
				} else {
					appFrame.showUserMessage(
							JOptionPane.WARNING_MESSAGE,
							Messages.getString(Messages.RELATED_OBJECT_TYPE) + " "
									+ notif.getRelatedObjectType()
									+ " " + Messages.getString(Messages.NOT_SUPPORTED_MODEL_CANNOT_BE_SHOWN),
							notif);
				}
			} else {
				appFrame.showUserMessage(
						JOptionPane.WARNING_MESSAGE,
						Messages.getString(Messages.RELATED_MODEL_CANNOT_BE_SHOWN_NO_MODEL_RELATED_TO_SELECTED_NOTIFICATION),
						notif);
			}
		} else {
			appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE,
					Messages.getString(Messages.NO_NOTIFICIATION_SELECTED_SELECT_ONE_FIRST), null);
		}
	}

	@Override
	protected void createModelForm() {
		modelForm = new NotificationForm(NotificationForm.MODE.VIEWER);
	}

	@Override
	public void bindDataForModelById(int modelId) throws IOException,
			ClassNotFoundException, SQLException {
	}

	@Override
	protected void actionSortModels() {
	}

	@Override
	public String getPanelTitle() {
		return Messages.getString(Messages.NOTIFICATIONS);
	}

	public ActionListener getActionListenerForActionShowModel() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionShowModel();
			}
		};
	}

	@Override
	protected void addFunctionsToMenuAndButtonPanel() {
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.OBJECT), 
				Messages.getString(Messages.SHOW_OBJECT), getActionListenerForActionShowModel());
	}
	
	@Override
	public void actionReportModelList() {
		UIHelper.startWaiting(appFrame);
		NotificationReportable reportable = new NotificationReportable(jp_modelList.getModel(), getPanelTitle());
		ReportingController.report(ReportFactory.createNotificationListReport(reportable), appFrame);
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	public JPanel getSpecificStatisticsPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Icon getIcon() {
		return IconDictionary.getImageIcon(IconDictionary.NOTIFICATIONS);
	}

}
