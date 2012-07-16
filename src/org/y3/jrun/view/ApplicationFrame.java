package org.y3.jrun.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.notification.Notification;
import org.y3.jrun.model.notification.Notification.NotificationType;
import org.y3.jrun.model.participation.Participation;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelPanel;
import org.y3.jrun.view.model.ageclassesdefinition.AgeClassesDefinitionsPanel;
import org.y3.jrun.view.model.competition.CompetitionGridPanel;
import org.y3.jrun.view.model.competition.CompetitionPanel;
import org.y3.jrun.view.model.contact.ContactsPanel;
import org.y3.jrun.view.model.discipline.DisciplinesPanel;
import org.y3.jrun.view.model.notification.NotificationsPanel;
import org.y3.jrun.view.model.participation.ParticipationsPanel;

/**
 * @author Ryppo
 * @version $Id$
 */
public class ApplicationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private boolean debug;
	private ApplicationController controller;
	private ContactsPanel panel_contacts;
	private NotificationsPanel panel_notifications;
	private CompetitionPanel panel_competitions;
	private ParticipationsPanel panel_participations;
	private DisciplinesPanel panel_disciplines;
	private AgeClassesDefinitionsPanel panel_ageclassesdefinitions;
	private JTabbedPane tabbedpane_main;
	private ArrayList<CompetitionGridPanel> competitionGridPanels;
	private JToolBar toolbar;
	private JButton button_historyBack;
	private JButton button_historyForward;
	
	private ArrayList<Component> historyPanels = new ArrayList<Component>(0);
	private int actualHistoryPosition = -1;
	private boolean actualTabSelectionIsHistoryMove = false;
	
	private ModelPanel[] modelPanels = new ModelPanel[]{
			panel_contacts, panel_disciplines, panel_ageclassesdefinitions, panel_competitions, 
			panel_participations, panel_notifications};
	private ConsoleFrame consoleFrame;

	/**
	 * Create the frame.
	 */
	public ApplicationFrame(ApplicationController _controller, boolean _debug) {
		controller = _controller;
		debug = _debug;
		init();
		if (debug) {
			prepareConsole();
		}
	}
	

	private void init() {
		setSize(1300, 850);
		setTitle(Messages.getString(Messages.APPLICATION_NAME));
		setLocationRelativeTo(null);
		setLocation(getLocation().x, 0);
		
		setLayout(new BorderLayout());
		
		JMenuBar menubar_main = new ApplicationMenubar(controller, this);
		setJMenuBar(menubar_main);
		
		toolbar = new JToolBar();
		button_historyBack = new JButton("0", IconDictionary.getImageIcon(IconDictionary.HISTORY_BACK));
		button_historyBack.addActionListener(getActionListenerForActionHistoryBack());
		toolbar.add(button_historyBack);
		button_historyForward = new JButton("0", IconDictionary.getImageIcon(IconDictionary.HISTORY_FORWARD));
		button_historyForward.setHorizontalTextPosition(SwingConstants.LEFT);
		button_historyForward.addActionListener(getActionListenerForActionHistoryForward());
		toolbar.add(button_historyForward);
		getContentPane().add(toolbar, BorderLayout.NORTH);
		
		tabbedpane_main = new JTabbedPane();
		tabbedpane_main.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				actionTabSelected();
			}
		});
		
		modelPanels[0] = panel_contacts = new ContactsPanel(controller, this);
		modelPanels[1] = panel_disciplines = new DisciplinesPanel(controller, this);
		modelPanels[2] = panel_ageclassesdefinitions = new AgeClassesDefinitionsPanel(controller, this);
		modelPanels[3] = panel_competitions = new CompetitionPanel(controller, this);
		modelPanels[4] = panel_participations = new ParticipationsPanel(controller, this);
		modelPanels[5] = panel_notifications = new NotificationsPanel(controller, this);
		
		competitionGridPanels = new ArrayList<CompetitionGridPanel>(0);
		
		for (ModelPanel modelPanel : modelPanels) {
			tabbedpane_main.addTab(modelPanel.getPanelTitle(), modelPanel.getIcon(), modelPanel);
			modelPanel.jp_modelList.setName(modelPanel.getPanelTitle());
			JMenu menu = modelPanel.getMenu();
			if (menu == null) {
				menu = new JMenu();
			}
			menu.setText(modelPanel.getPanelTitle());
			menubar_main.add(menu);
		}

		getContentPane().add(tabbedpane_main, BorderLayout.CENTER);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				actionShutDown();
			}
		});
	}
	
	private void prepareConsole() {
		consoleFrame = new ConsoleFrame();
		PrintStream printStream = new PrintStream(new ByteArrayOutputStream(){
			public void flush() throws IOException {
				String record;
				synchronized (this) {
					super.flush();
					record = this.toString();
					super.reset();
					consoleFrame.logMessage(record);
				}
			}
		}, true);
		System.setOut(printStream);
		System.setErr(printStream);
	}
	
	public void showDebugConsole(boolean show) {
		if (consoleFrame != null) {
			consoleFrame.setVisible(show);
		}
	}

	public void actionShutDown() {
		try {
			if (controller.shutDown()) {
				setVisible(false);
				System.exit(0);
			}
		} catch (SQLException e) {
			showUserMessage(e, null);
			showUserMessage(JOptionPane.WARNING_MESSAGE, Messages.getString(Messages.SYSTEM_NOT_SHUT_DOWN), null);
		}
	}
	
	public void bindData() {
		try {
			for (ModelPanel modelPanel: modelPanels) {
				modelPanel.bindData();
			}
		} catch (Exception e) {
			showUserMessage(e, null);
		} 
	}
	
	public void showNotificationsForModel(Model model) {
		try {
			panel_notifications.bindDataForModel(model);
			tabbedpane_main.setSelectedComponent(panel_notifications);
		} catch (Exception e) {
			showUserMessage(e, model);
		}
	}
	
	public void showContactById(int contactId) {
		try {
			panel_contacts.bindDataForModelById(contactId);
			tabbedpane_main.setSelectedComponent(panel_contacts);
		} catch (Exception e) {
			try {
				showUserMessage(e, null);
			} catch (Exception e1) {
				showUserMessage(e1, null);
			}		
		}
	}
	
	public void actionTabSelected() {
		if (!actualTabSelectionIsHistoryMove) {
			historyPanels.add(tabbedpane_main.getSelectedComponent());
			actualHistoryPosition++;
			updateHistoryButtonInformation();
		}
	}
	
	public void showCompetitionById(int competitionId) {
		try {
			panel_competitions.bindDataForModelById(competitionId);
			tabbedpane_main.setSelectedComponent(panel_competitions);
		} catch (Exception e) {
			try {
				showUserMessage(e, null);
			} catch (Exception e1) {
				showUserMessage(e1, null);
			}		
		}
	}
	
	public void showCompetitionByIds(int[] competitionIds) {
		try {
			panel_competitions.bindDataForModelByIds(competitionIds);
			tabbedpane_main.setSelectedComponent(panel_competitions);
		} catch (Exception e) {
			try {
				showUserMessage(e, null);
			} catch (Exception e1) {
				showUserMessage(e1, null);
			}		
		}
	}
	
	public void historyBack() {
		if (actualHistoryPosition > 0) {
			actualHistoryPosition--;
			actualTabSelectionIsHistoryMove = true;
			tabbedpane_main.setSelectedComponent(historyPanels.get(actualHistoryPosition));
			actualTabSelectionIsHistoryMove = false;
			updateHistoryButtonInformation();
		}
	}
	
	public void historyForward() {
		if (actualHistoryPosition < historyPanels.size() -1) {
			actualHistoryPosition++;
			actualTabSelectionIsHistoryMove = true;
			tabbedpane_main.setSelectedComponent(historyPanels.get(actualHistoryPosition));
			actualTabSelectionIsHistoryMove = false;
			updateHistoryButtonInformation();
		}
	}
	
	private void updateHistoryButtonInformation() {
		button_historyBack.setText(Integer.toString(actualHistoryPosition));
		button_historyForward.setText(Integer.toString(historyPanels.size() - actualHistoryPosition - 1));
	}
	
	public void showCompetitionInstanceById(Participation participation) {
		try {
			if (participation != null) {
				int competitionId = participation.getCompetitionId();
				CompetitionGridPanel cgp = showCompetitionInstanceById(competitionId);
				cgp.setSelectedParticipation(participation);
			}
		} catch (Exception e) {
			try {
				showUserMessage(e, null);
			} catch (Exception e1) {
				showUserMessage(e1, null);
			}		
		}
	}
	
	public CompetitionGridPanel showCompetitionInstanceById(int competitionId) throws Exception {
		CompetitionGridPanel cgp = null;
		for (int i = 0; i < competitionGridPanels.size(); i++) {
			cgp = competitionGridPanels.get(i);
			if (cgp.getCompetitionId() == competitionId) {
				tabbedpane_main.setSelectedComponent(cgp);
				break;
			}
		}
		return cgp;
	}
	
	public void showParticipationById(int participationId) {
		try {
			panel_participations.bindDataForModelById(participationId);
			tabbedpane_main.setSelectedComponent(panel_participations);
		} catch (Exception e) {
			showUserMessage(e, null);
		}
	}
	
	public void showDisciplineById(int disciplineId) {
		try {
			panel_disciplines.bindDataForModelById(disciplineId);
			tabbedpane_main.setSelectedComponent(panel_disciplines);
		} catch (Exception e) {
			showUserMessage(e, null);
		}
	}
	
	public void showParticipationsByCompetitionId(int competitionId) {
		try {
			panel_participations.bindDataByCompetitionId(competitionId);
			tabbedpane_main.setSelectedComponent(panel_participations);
		} catch (Exception e) {
			showUserMessage(e, null);
		}
	}
	
	public void showParticipationsByContactId(int contactId) {
		try {
			panel_participations.bindDataByContactId(contactId);
			tabbedpane_main.setSelectedComponent(panel_participations);
		} catch (Exception e) {
			showUserMessage(e, null);
		}
	}
	
	public void showParticipationsByDisciplineId(int disciplineId) {
		try {
			panel_participations.bindDataByDisciplineId(disciplineId);
			tabbedpane_main.setSelectedComponent(panel_participations);
		} catch (Exception e) {
			showUserMessage(e, null);
		}
	}
	
	public void refreshAndShowParticipations(boolean showAfterRefresh) {
		try {
			panel_participations.bindData();
			if (showAfterRefresh) {
				tabbedpane_main.setSelectedComponent(panel_participations);
			}
		} catch (Exception e) {
			showUserMessage(e, null);
		}
	}
	
	public void refreshAndShowContacts(boolean showAfterRefresh) {
		try {
			panel_contacts.bindData();
			if (showAfterRefresh) {
				tabbedpane_main.setSelectedComponent(panel_contacts);
			}
		} catch (Exception e) {
			showUserMessage(e, null);
		}
	}
	
	public void refreshAndShowDisciplines(boolean showAfterRefresh) {
		try {
			panel_disciplines.bindData();
			if (showAfterRefresh) {
				tabbedpane_main.setSelectedComponent(panel_disciplines);
			}
		} catch (Exception e) {
			showUserMessage(e, null);
		}
	}
	
	public void showAgeClassesDefinitionById(int ageClassesDefinitionId) {
		try {
			panel_ageclassesdefinitions.bindDataForModelById(ageClassesDefinitionId);
			tabbedpane_main.setSelectedComponent(panel_ageclassesdefinitions);
		} catch (Exception e) {
			showUserMessage(e, null);
		}
	}
	
	public void logMessage(int jOptionPaneType, String message, Model model) {
		Notification notification = null;
		NotificationType nType = getNotificationType(jOptionPaneType);
		if (model != null) {
			String title = model.getNotificationTitle();
			if (message == null) {
				message = "";
			}
			String nMessage = message + "\n" + model.getNotificationMessage(); 
			notification = new Notification(nType, title, nMessage, model.getClass().getSimpleName(), model.getId());
		} else {
			notification = new Notification(nType, getTitle(), message);
		}
		try {
			controller.saveModel(notification);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public NotificationType getNotificationType(int jOptionPaneType) {
		NotificationType nType = null;
		switch (jOptionPaneType) {
		case JOptionPane.ERROR_MESSAGE:
			nType = Notification.NotificationType.error;
			break;
		case JOptionPane.INFORMATION_MESSAGE:
			nType = Notification.NotificationType.info;
			break;
		case JOptionPane.WARNING_MESSAGE:
			nType = Notification.NotificationType.warn;
			break;
		default:
			nType = Notification.NotificationType.info;
		}
		return nType;
	}
	
	public void showUserMessage(int jOptionPaneType, String message, Model model) {
		if (controller.isDebug()) {
			System.out.println(Messages.getString(Messages.USER_MESSAGE) + ": " + message);
		}
		JOptionPane.showMessageDialog(rootPane, message, getTitle(), jOptionPaneType);
		logMessage(jOptionPaneType, message, model);
		if (debug) {
			consoleFrame.logMessage(message);
		}
	}
	
	public void showUserMessage(Exception exception, Model model) {
		if (controller.isDebug()) {
			exception.printStackTrace();
		}
		showUserMessage(JOptionPane.ERROR_MESSAGE, exception.getMessage(), model);
		Notification notification = null;
		if (model != null) {
			notification = new Notification(exception, model.getClass().getSimpleName(), model.getId());
		} else {
			notification = new Notification(exception);
		}
		try {
			controller.saveModel(notification);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addCompetitionGridPanelTab(String title, CompetitionGridPanel anonymousTab) {
		competitionGridPanels.add(anonymousTab);
		tabbedpane_main.addTab(title, IconDictionary.getImageIcon(IconDictionary.COMPETITION_GRID), anonymousTab);
	}
	
	public void removeAllCompetitionGridPanelTabs() {
		for (int i=0; i < competitionGridPanels.size(); i++) {
			tabbedpane_main.remove(competitionGridPanels.get(i));
		}
	}
	
	public ActionListener getActionListenerForActionHistoryBack() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				historyBack();	
			}
		};
	}
	
	public ActionListener getActionListenerForActionHistoryForward() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				historyForward();	
			}
		};
	}
}
