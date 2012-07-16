package org.y3.jrun.view.model.competition;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.lang.ArrayUtils;
import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.control.Utils;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinition;
import org.y3.jrun.model.competition.Competition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.model.discipline.DisciplineListModel;
import org.y3.jrun.model.participation.Participation;
import org.y3.jrun.model.participation.ParticipationDuration;
import org.y3.jrun.model.participation.ParticipationListModel;
import org.y3.jrun.model.participation.RichParticipation;
import org.y3.jrun.model.participation.RichParticipationReportable;
import org.y3.jrun.model.participation.RichParticipationsTableModel;
import org.y3.jrun.model.report.Report;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.gfx.JLabelRenderer;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.discipline.DisciplineListCellRenderer;
import org.y3.jrun.view.model.discipline.ParticipationsTable;
import org.y3.jrun.view.model.participation.ParticipationDurationEditor;
import org.y3.jrun.view.model.participation.ParticipationDurationRenderer;
import org.y3.jrun.view.model.participation.ParticipationFormDialog;
import org.y3.jrun.view.reporting.ReportFactory;
import org.y3.jrun.view.reporting.ReportingController;

public class CompetitionGridPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ApplicationFrame appFrame;
	private ApplicationController controller;
	private Competition competition;
	private JPanel panel_toolbar;
	private ParticipationsTable table_participations;
	private JList list_disciplines;
	private JTextField textfield_search;

	private JTextField label_countParticipations;
	
	private enum RANK_TYPE {RANK, AGECLASS_RANK, GENDER_RANK, GENDER_AGECLASS_RANK};

	private JSplitPane splitPane;
	
	public CompetitionGridPanel(Competition _competition, ApplicationController _controller, ApplicationFrame _appFrame) {
		appFrame = _appFrame;
		controller = _controller;
		competition = _competition;
		init();
		bindData();
	}
	
	private void init() {
		setLayout(new BorderLayout(0, 0));
		splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		JPanel jp_leftSplit = new JPanel(new BorderLayout());
		splitPane.setLeftComponent(jp_leftSplit);
		
		JPanel jp_search = new JPanel(new BorderLayout());
		textfield_search = new JTextField();
		textfield_search.addKeyListener(getKeyListenerForActionSearchModel());
		textfield_search.addKeyListener(getKeyListenerForActionEditModel());
		jp_search.add(textfield_search, BorderLayout.CENTER);
		JButton jb_search = new JButton(IconDictionary.getImageIcon(IconDictionary.SEARCH));
		jb_search.setToolTipText(Messages.getString(Messages.SEARCH));
		jb_search.addActionListener(getActionListenerForActionSearchModel());
		jp_search.add(jb_search, BorderLayout.EAST);
		jp_leftSplit.add(jp_search, BorderLayout.NORTH);
		
		list_disciplines = new JList();
		list_disciplines.setCellRenderer(new DisciplineListCellRenderer(appFrame, controller, competition));
		list_disciplines.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				UIHelper.startWaiting(appFrame);
				Discipline[] disciplines = getSelectedDisciplines();
				if (disciplines != null && disciplines.length > 0) {
					try {
						RichParticipationsTableModel tModel = controller.getParticipationsTableForDisciplines(competition, disciplines, appFrame);
						table_participations.setModel(tModel);
						int count = 0;
						if (tModel != null) {
							count = tModel.getRowCount();
							for (int colNo = 0; colNo < tModel.getColumnCount(); colNo++) {
								table_participations.getColumnModel().getColumn(colNo).setPreferredWidth(tModel.getColumnWidth(colNo));
							}
						}
						String text = Integer.toString(count) + " ";
						if (count == 1) {
							text +=  Messages.getString(Messages.PARTICIPATION);
						} else {
							text +=  Messages.getString(Messages.PARTICIPATIONS);
						}
						label_countParticipations.setText(text);
					} catch (Exception e1) {
						appFrame.showUserMessage(e1, disciplines[0]);
					}
				} else {
					table_participations.setModel(new DefaultTableModel());
				}
				UIHelper.stopWaiting(appFrame);
			}
		});
		list_disciplines.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_UP) {
					ListModel modelList = list_disciplines.getModel();
					if (modelList == null
							|| (modelList != null && (list_disciplines
									.getSelectedIndex() == 0 || modelList
									.getSize() == 0))) {
						focusSearchField();
					}
				}
			}
		});
		jp_leftSplit.add(list_disciplines, BorderLayout.CENTER);
		
		table_participations = new ParticipationsTable();
		table_participations.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_participations.setDefaultRenderer(ParticipationDuration.class, new ParticipationDurationRenderer());
		table_participations.setDefaultRenderer(JLabel.class, new JLabelRenderer());
		table_participations.setDefaultEditor(ParticipationDuration.class, new ParticipationDurationEditor(controller, appFrame));
		table_participations.setRowHeight(new JFormattedTextField("#").getPreferredSize().height);
		table_participations.addKeyListener(getKeyListenerForActionEditModel());
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(table_participations), BorderLayout.CENTER);
		label_countParticipations = new JTextField();
		panel.add(label_countParticipations, BorderLayout.SOUTH);
		splitPane.setRightComponent(panel);
		
		add(getToolbar(), BorderLayout.NORTH);
	}
	
	private void addFeatureButton(ImageIcon functionIcon, String functionName, ActionListener functionActionListener) {
		JButton button = new JButton(functionIcon);
		button.setToolTipText(functionName);
		button.addActionListener(functionActionListener);
		panel_toolbar.add(button);
	}
	
	private JPanel getToolbar() {
		if (panel_toolbar == null) {
			panel_toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		}
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.REFRESH),
				Messages.getString(Messages.REFRESH),
				getActionListenerForActionRefresh());
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.REPORT),
				Messages.getString(Messages.REPORT),
				getActionListenerForActionReportDisciplineStartGrid());
		
		panel_toolbar.add(new JSeparator(JSeparator.VERTICAL));
		
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.REPORT),
				Messages.getString(Messages.GENDER_REPORTS),
				getActionListenerForActionReportDisciplineGenderStartGrid());
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.REPORT),
				Messages.getString(Messages.AGECLASS_REPORTS),
				getActionListenerForActionReportDisciplineAgeClassStartGrid());
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.REPORT),
				Messages.getString(Messages.GENDER_AGECLASS_REPORTS),
				getActionListenerForActionReportDisciplineGenderAgeClassStartGrid());
		addFeatureButton(
				IconDictionary.getImageIcon(IconDictionary.REPORT_ALL),
				Messages.getString(Messages.REPORT_ALL_START_GRIDS),
				getActionListenerForActionCreateAllDisciplineStartGridReports());
		addFeatureButton(
				IconDictionary.getImageIcon(IconDictionary.CERTIFICATION),
				Messages.getString(Messages.REPORT_CERTIFICATION),
				getActionListenerForActionReportCertification());
		
		panel_toolbar.add(new JSeparator(JSeparator.VERTICAL));

		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.NEW),
				Messages.getString(Messages.NEW),
				getActionListenerForActionAddParticipant());		
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.EDIT),
				Messages.getString(Messages.EDIT),
				getActionListenerForActionEditParticipant());
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.REMOVE),
				Messages.getString(Messages.REMOVE),
				getActionListenerForActionRemoveParticipant());
		addFeatureButton(
				IconDictionary
						.getImageIcon(IconDictionary.REMOVE_ALL_FROM_LIST),
				Messages.getString(Messages.REMOVE_ALL_PARTICIPATIONS_FROM_DISCIPLINE),
				getActionListenerForActionRemoveAllParticipantsFromDiscipline());
		addFeatureButton(
				IconDictionary
						.getImageIcon(IconDictionary.REMOVE_ALL_FROM_COMPETITION),
				Messages.getString(Messages.REMOVE_ALL_PARTICIPATIONS_FROM_COMPETITION),
				getActionListenerForActionRemoveAllParticipantsFromCompetition());
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.IMPORT), Messages.getString(Messages.IMPORT_PARTICIPATION_DATA),
				getActionListenerForActionImportParticipationData());
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.EXPORT), Messages.getString(Messages.EXPORT_PARTICIPATION_DATA),
				getActionListenerForActionExportParticipationData());
		
		panel_toolbar.add(new JSeparator(JSeparator.VERTICAL));
		
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.RESET_DISCIPLINE_GRID), 
				Messages.getString(Messages.RESET_DISCIPLINE_GRID), 
				getActionListenerForActionResetDisciplineGrid());
		
		panel_toolbar.add(new JSeparator(JSeparator.VERTICAL));
		
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.RANKING),
				Messages.getString(Messages.CALCULATE_RANKING),
				getActionListenerForActionCalculateRanking());
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.RANKING),
				Messages.getString(Messages.CALCULATE_GENDER_RANKING),
				getActionListenerForActionCalculateGenderRanking());
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.RANKING),
				Messages.getString(Messages.CALCULATE_AGECLASS_RANKING),
				getActionListenerForActionCalculateAgeClassRanking());
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.RANKING),
				Messages.getString(Messages.CALCULATE_GENDER_AGECLASS_RANKING),
				getActionListenerForActionCalculateGenderAgeClassRanking());
		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.RANKING_ALL), 
				Messages.getString(Messages.CALCULATE_ALL_RANKINGS), 
				getActionListenerForActionCalculateAllRankings());
		
		panel_toolbar.add(new JSeparator(JSeparator.VERTICAL));

		addFeatureButton(IconDictionary.getImageIcon(IconDictionary.CONTACT),
				Messages.getString(Messages.SHOW_CONTACT),
				getActionListenerForActionShowContact());
		addFeatureButton(
				IconDictionary.getImageIcon(IconDictionary.PARTICIPATIONS),
				Messages.getString(Messages.SHOW_PARTICIPATIONS),
				getActionListenerForActionShowParticipations());
		addFeatureButton(
				IconDictionary.getImageIcon(IconDictionary.DISCIPLINE),
				Messages.getString(Messages.SHOW_DISCIPLINE),
				getActionListenerForActionShowDiscipline());
		addFeatureButton(
				IconDictionary
						.getImageIcon(IconDictionary.AGECLASSES_DEFINITION),
				Messages.getString(Messages.SHOW_AGECLASSES_DEFINITION),
				getActionListenerForActionShowAgeClassesDefinition());
		addFeatureButton(
				IconDictionary.getImageIcon(IconDictionary.COMPETITION),
				Messages.getString(Messages.SHOW_COMPETITION),
				getActionListenerForActionShowCompetition());
		
		return panel_toolbar;
	}
	
	public void bindData() {
		UIHelper.startWaiting(appFrame);
		if (competition != null) {
			try {
				Discipline discipline = getSelectedDiscipline();
				list_disciplines.setModel(controller.getDisciplinesByCompetitionId(competition.getId()));
				setSelectedDiscipline(discipline);
				optimizeSplit();
			} catch (Exception e) {
				appFrame.showUserMessage(e, competition);
			}
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	public int getCompetitionId() {
		int id = 0;
		if (competition != null) {
			id = competition.getId();
		}
		return id;
	}
	
	public void setSelectedParticipation(Participation participation) {
		ListModel listModel = list_disciplines.getModel();
		if (listModel instanceof DisciplineListModel && participation != null) {
			Discipline[] disciplines = ((DisciplineListModel) listModel).getModel();
			boolean participationIsSelected = false;
			for(Discipline discipline: disciplines) {
				if (discipline.getId() == participation.getDisciplineId()) {
					list_disciplines.setSelectedValue(discipline, true);
					if (table_participations.getModel() instanceof RichParticipationsTableModel) {
						RichParticipationsTableModel richParticipationsTableModel = (RichParticipationsTableModel) table_participations.getModel();
						for (int i = 0; i < richParticipationsTableModel.getRowCount(); i++) {
							RichParticipation richParticipation = richParticipationsTableModel.getParticipationAtRow(i);
							if (richParticipation != null) {
								if (richParticipation.getId() == participation.getId()) {
									table_participations.getSelectionModel().setSelectionInterval(i, i);
									participationIsSelected = true;
									break;
								}
							}
						}
						if (participationIsSelected) {
							break;
						}
					}
				}
			}
		}
	}
	
	private ActionListener getActionListenerForActionCalculateRanking() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIHelper.startWaiting(appFrame);
				calculateRanking();
				UIHelper.stopWaiting(appFrame);
			}
		};
	}
	
	public void calculateRanking() {
		calculateRanking(RANK_TYPE.RANK);
	}
	
	private ActionListener getActionListenerForActionCalculateAgeClassRanking() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIHelper.startWaiting(appFrame);
				calculateAgeClassRanking();
				UIHelper.stopWaiting(appFrame);
			}
		};
	}
	
	public void calculateAgeClassRanking() {
		calculateRanking(RANK_TYPE.AGECLASS_RANK);
	}
	
	private ActionListener getActionListenerForActionCalculateGenderAgeClassRanking() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIHelper.startWaiting(appFrame);
				calculateGenderAgeClassRanking();
				UIHelper.stopWaiting(appFrame);
			}
		};
	}
	
	public void calculateGenderAgeClassRanking() {
		calculateRanking(RANK_TYPE.GENDER_AGECLASS_RANK);
	}
	
	private ActionListener getActionListenerForActionCalculateGenderRanking() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIHelper.startWaiting(appFrame);
				calculateGenderRanking();
				UIHelper.stopWaiting(appFrame);
			}
		};
	}
	
	public void calculateGenderRanking() {
		calculateRanking(RANK_TYPE.GENDER_RANK);
	}
	
	private ActionListener getActionListenerForActionCalculateAllRankings() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (UIHelper.showSureRequest(Messages.getString(Messages.CALCULATE_ALL_RANKINGS), appFrame)) {
					UIHelper.startWaiting(appFrame);
					calculateRanking();
					calculateGenderRanking();
					calculateAgeClassRanking();
					calculateGenderAgeClassRanking();
					UIHelper.stopWaiting(appFrame);
				}
			}
		};
	}
	
	private void calculateRanking(RANK_TYPE rankType) {
		if (table_participations != null && table_participations.getParticipationsTableModel() != null) {
			RichParticipation[] participations = table_participations.getParticipationsTableModel().getAllParticipations();
			try {
				if (rankType == RANK_TYPE.RANK) {
					controller.calculateRanking(participations, true);
				} else if (rankType == RANK_TYPE.AGECLASS_RANK) {
					controller.calculateAgeClassesRankings(participations, true);
				} else if (rankType == RANK_TYPE.GENDER_RANK) {
					controller.calculateGenderRankings(participations, true);
				} else if (rankType == RANK_TYPE.GENDER_AGECLASS_RANK) {
					controller.calculateGenderAgeClassRankings(participations, true);
				}
			} catch (Exception e1) {
				appFrame.showUserMessage(e1, null);
			}
			bindData();
		}
	}
	
	private ActionListener getActionListenerForActionShowContact() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Participation model = table_participations.getSelectedParticipation();
				if (model != null) {
					appFrame.showContactById(model.getContactId());
				} else {
					appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, Messages.getString(Messages.NO_PARTICIPATION_SELECTED_SELECT_ONE_FIRST), null);
				}
			}
		};
	}
	
	private ActionListener getActionListenerForActionShowParticipations() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Participation model = table_participations.getSelectedParticipation();
				if (model != null) {
					appFrame.showParticipationsByContactId(model.getContactId());
				} else {
					appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, Messages.getString(Messages.NO_PARTICIPATION_SELECTED_SELECT_ONE_FIRST), null);
				}
			}
		};
	}
	
	private ActionListener getActionListenerForActionShowDiscipline() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int disciplineId = 0;
				Participation model = table_participations.getSelectedParticipation();
				if (model != null) {
					disciplineId = model.getDisciplineId();
				} else {
					Object disObject = list_disciplines.getSelectedValue();
					if (disObject != null && disObject instanceof Discipline) {
						Discipline discipline = (Discipline) disObject;
						disciplineId = discipline.getId();
					}
				}
				if (disciplineId > 0) {
					appFrame.showDisciplineById(disciplineId);
				} else {
					appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, Messages.getString(Messages.NO_PARTICIPATION_SELECTED_SELECT_ONE_FIRST), null);
				}
			}
		};
	}
	
	private ActionListener getActionListenerForActionShowCompetition() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (competition != null) {
					appFrame.showCompetitionById(competition.getId());
				}
			}
		};
	}
	
	private ActionListener getActionListenerForActionShowAgeClassesDefinition() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIHelper.startWaiting(appFrame);
				try {
					if (competition != null) {
						AgeClassesDefinition acd = controller.getAgeClassesDefinitionByCompetition(competition);
						int acdId = 0;
						if (acd != null) {
							acdId = acd.getId();
						}
						appFrame.showAgeClassesDefinitionById(acdId);
					}
				} catch (Exception ex) {
					appFrame.showUserMessage(ex, competition);
				}
				UIHelper.stopWaiting(appFrame);
			}
		};
	}
	
	private ActionListener getActionListenerForActionRefresh() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bindData();
			}
		};
	}
	
	public Report getDisciplineStartGridReport() {
		Report disciplineStartGridReport = null;
		RichParticipationsTableModel tableModel = table_participations.getParticipationsTableModel();
		if (tableModel != null) {
			RichParticipation[] participations = new RichParticipation[tableModel.getRowCount()];
			for (int row = 0; row < tableModel.getRowCount(); row++) {
				participations[row] = tableModel.getParticipationAtRow(row);
			}
			disciplineStartGridReport = ReportFactory.createDisciplineStartGridReport(new RichParticipationReportable(participations));
		}
		return disciplineStartGridReport;
	}
	
	private ActionListener getActionListenerForActionReportDisciplineStartGrid() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReportingController.report(getDisciplineStartGridReport(), appFrame);
			}
		};
	}
	
	private ActionListener getActionListenerForActionReportCertification() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RichParticipationsTableModel tableModel = table_participations.getParticipationsTableModel();
				if (tableModel != null) {
					RichParticipation[] participations = tableModel.getParticipationsAtRows(table_participations.getSelectedRows());
					if (participations != null) {
						Report[] reports = new Report[participations.length];
						int repPos = 0;
						for (RichParticipation richP: participations) {
							Report report = ReportFactory.createCertificationReport(
									new RichParticipationReportable(new RichParticipation[]{richP}), richP.getParticipant());
							reports[repPos] = report;
							repPos++;
						}
						ReportingController.report(reports, appFrame);
					}
				}
			}
		};
	}
	
	public Report[] getDisciplineGenderStartGridReports() {
		Report[] disciplineGenderStartGridReports = null;
		RichParticipationsTableModel tableModel = table_participations.getParticipationsTableModel();
		if (tableModel != null) {
			RichParticipation[] participations = new RichParticipation[tableModel.getRowCount()];
			for (int row = 0; row < tableModel.getRowCount(); row++) {
				participations[row] = tableModel.getParticipationAtRow(row);
			}
			HashMap<String, Object> parameters_female = new HashMap<String, Object>();
			parameters_female.put(KeywordsDictionary.CONTACT_GENDER, Contact.gendertype.FEMALE.toString());
			Report report_female = ReportFactory.createDisciplineGenderStartGridReport( 
					new RichParticipationReportable(participations), parameters_female);
			HashMap<String, Object> parameters_male = new HashMap<String, Object>();
			parameters_male.put(KeywordsDictionary.CONTACT_GENDER, Contact.gendertype.MALE.toString());
			Report report_male = ReportFactory.createDisciplineGenderStartGridReport( 
					new RichParticipationReportable(participations), parameters_male);
			disciplineGenderStartGridReports = new Report[]{report_female, report_male};
		}
		return disciplineGenderStartGridReports;
	}
	
	private ActionListener getActionListenerForActionReportDisciplineGenderStartGrid() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReportingController.report(getDisciplineGenderStartGridReports(), appFrame);
			}
		};
	}
	
	public Report[] getDisciplineAgeClassStartGridReports() {
		Report[] disciplineAgeClassStartGridReports = null;
		RichParticipationsTableModel tableModel = table_participations.getParticipationsTableModel();
		if (tableModel != null && tableModel instanceof RichParticipationsTableModel) {
			RichParticipation[] participations = ((RichParticipationsTableModel) tableModel).getAllParticipations();
			AgeClass[] ageClasses = controller.getDistinctAgeClasses(participations);
			Report[] reports = null;
			if (ageClasses != null && ageClasses.length > 0) {
				reports = new Report[ageClasses.length];
				int acCounter = 0;
				for (AgeClass ageClass: ageClasses) {
					if (ageClass != null) {
						HashMap<String, Object> parameters = new HashMap<String, Object>();
						String ageClassName = ageClass.toString();
						parameters.put(KeywordsDictionary.AGECLASS,
								ageClassName);
						RichParticipation[] acParticipations = controller
								.getParticipationsForAgeClass(
										participations, ageClass);
						Report acReport = ReportFactory
								.createDisciplineAgeClassStartGridReport(
										new RichParticipationReportable(
												acParticipations),
										parameters);
						reports[acCounter] = acReport;
						acCounter++;
					}
				}
			}
			disciplineAgeClassStartGridReports = reports;
		}
		return disciplineAgeClassStartGridReports;
	}
	
	private ActionListener getActionListenerForActionReportDisciplineAgeClassStartGrid() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReportingController.report(getDisciplineAgeClassStartGridReports(), appFrame);
			}
		};
	}
	
	public Report[] getDisciplineGenderAgeClassStartGridReports() {
		Report[] disciplineGenderAgeClassStartGridReports = null;
		RichParticipationsTableModel tableModel = table_participations.getParticipationsTableModel();
		if (tableModel != null && tableModel instanceof RichParticipationsTableModel) {
			ArrayList<Report> reportsList = new ArrayList<Report>(0);
			RichParticipation[] participations = ((RichParticipationsTableModel) tableModel).getAllParticipations();
			AgeClass[] ageClasses = controller.getDistinctAgeClasses(participations);
			if (ageClasses != null && ageClasses.length > 0) {
				for (AgeClass ageClass: ageClasses) {
					if (ageClass != null) {
						RichParticipation[] acParticipations =
								controller.getParticipationsForAgeClass(participations, ageClass);
						String ageClassName = ageClass.toString();
						RichParticipation[] acFemaleParticipations = controller
								.getParticipationsForGender(
										acParticipations, Contact.gendertype.FEMALE);
						if (acFemaleParticipations != null && acFemaleParticipations.length > 0) {
							HashMap<String, Object> parameters_female = new HashMap<String, Object>();
							parameters_female.put(KeywordsDictionary.AGECLASS,
									ageClassName);
							parameters_female.put(KeywordsDictionary.CONTACT_GENDER_RESOURCE, Messages.getString(Messages.FEMALE));
							Report acFemaleReport = ReportFactory
									.createDisciplineGenderAgeClassStartGridReport(
											new RichParticipationReportable(
													acFemaleParticipations),
													parameters_female);
							reportsList.add(acFemaleReport);
						}
						RichParticipation[] acMaleParticipations = controller
								.getParticipationsForGender(
										acParticipations, Contact.gendertype.MALE);
						if (acFemaleParticipations != null && acMaleParticipations.length > 0) {
							HashMap<String, Object> parameters_male = new HashMap<String, Object>();
							parameters_male.put(KeywordsDictionary.AGECLASS,
									ageClassName);
							parameters_male.put(KeywordsDictionary.CONTACT_GENDER_RESOURCE, Messages.getString(Messages.MALE));
							Report acMaleReport = ReportFactory
									.createDisciplineGenderAgeClassStartGridReport(
											new RichParticipationReportable(
													acMaleParticipations),
													parameters_male);
							reportsList.add(acMaleReport);
						}
					}
				}
			}
			Report[] reports = new Report[0];
			reports = reportsList.toArray(reports);
			disciplineGenderAgeClassStartGridReports = reports;
		}
		return disciplineGenderAgeClassStartGridReports;
	}
	
	private ActionListener getActionListenerForActionReportDisciplineGenderAgeClassStartGrid() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReportingController.report(getDisciplineGenderAgeClassStartGridReports(), appFrame);
			}
		};
	}
	
	private ActionListener getActionListenerForActionCreateAllDisciplineStartGridReports() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Report[] reports = new Report[0];
				reports = (Report[]) ArrayUtils.add(reports, getDisciplineStartGridReport());
				reports = (Report[]) ArrayUtils.addAll(reports, getDisciplineGenderStartGridReports());
				reports = (Report[]) ArrayUtils.addAll(reports, getDisciplineAgeClassStartGridReports());
				reports = (Report[]) ArrayUtils.addAll(reports, getDisciplineGenderAgeClassStartGridReports());
				ReportingController.report(reports, appFrame);
			}
		};
	}
	
	private ActionListener getActionListenerForActionAddParticipant() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ParticipationFormDialog modelEditor = new ParticipationFormDialog(competition, getSelectedDiscipline(), null, appFrame, controller);
				modelEditor.setVisible(true);
				if (modelEditor.isOperationSucceeded()) {
					bindData();
				}
			}
		};
	}
	
	private ActionListener getActionListenerForActionEditParticipant() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editSelectedParticipation();
			}
		};
	}
	
	private void editSelectedParticipation() {
		Participation participation = table_participations.getSelectedParticipation();
		if (participation != null) {
			ParticipationFormDialog modelEditor = new ParticipationFormDialog(competition, getSelectedDiscipline(), participation, appFrame, controller);
			modelEditor.setVisible(true);
			if (modelEditor.isOperationSucceeded()) {
				bindData();
			}
		} else {
			appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, Messages.getString(Messages.NO_PARTICIPATION_SELECTED_SELECT_ONE_FIRST), competition);
		}
	}
	
	private ActionListener getActionListenerForActionRemoveParticipant() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIHelper.startWaiting(appFrame);
				Participation participation = table_participations.getSelectedParticipation();
				if (participation != null) {
					try {
						controller.deleteModel(participation);
						bindData();
					} catch (Exception e1) {
						appFrame.showUserMessage(e1, participation);
					}
				} else {
					appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, Messages.getString(Messages.NO_PARTICIPATION_SELECTED_SELECT_ONE_FIRST), competition);
				}
				UIHelper.stopWaiting(appFrame);
			}
		};
	}
	
	private ActionListener getActionListenerForActionRemoveAllParticipantsFromDiscipline() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIHelper.startWaiting(appFrame);
				if (UIHelper.showSureRequest(Messages.getString(Messages.REMOVE_ALL_PARTICIPATIONS_FROM_DISCIPLINE), appFrame)) {
					RichParticipationsTableModel participationsTM = (RichParticipationsTableModel) table_participations
							.getModel();
					if (participationsTM != null
							&& participationsTM.getAllParticipations() != null) {
						try {
							Participation[] participations = participationsTM
									.getAllParticipations();
							for (Participation participation : participations) {
								controller.deleteModel(participation);
							}
							bindData();
						} catch (Exception e1) {
							appFrame.showUserMessage(e1,
									getSelectedDiscipline());
						}
					} else {
						appFrame.showUserMessage(
								JOptionPane.WARNING_MESSAGE,
								Messages.getString(Messages.NO_DISCIPLINE_SELECTED_SELECT_ONE),
								competition);
					}
				}
				UIHelper.stopWaiting(appFrame);
			}
		};
	}
	
	private ActionListener getActionListenerForActionRemoveAllParticipantsFromCompetition() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIHelper.startWaiting(appFrame);
				if (UIHelper.showSureRequest(Messages.getString(Messages.REMOVE_ALL_PARTICIPATIONS_FROM_COMPETITION), appFrame)) {
					try {
						ParticipationListModel participationListModel = controller
								.getAllParticipationsForCompetition(competition);
						if (participationListModel != null
								&& participationListModel.getModel() != null) {
							Participation[] participations = participationListModel
									.getModel();
							for (Participation participation : participations) {
								controller.deleteModel(participation);
							}
							bindData();
						}
					} catch (Exception e1) {
						appFrame.showUserMessage(e1, getSelectedDiscipline());
					}
				}
				UIHelper.stopWaiting(appFrame);
			}
		};
	}
	
	public Discipline getSelectedDiscipline() {
		Object selectedObject = list_disciplines.getSelectedValue();
		if (selectedObject != null && selectedObject instanceof Discipline) {
			return (Discipline) selectedObject;
		} else {
			return null;
		}
	}
	
	public Discipline[] getSelectedDisciplines() {
		Object[] selectedObjects = list_disciplines.getSelectedValues();
		if (selectedObjects != null && selectedObjects.length > 0) {
			Discipline[] disciplines = new Discipline[selectedObjects.length];
			for (int sNo = 0; sNo < selectedObjects.length; sNo++) {
				disciplines[sNo] = (Discipline) selectedObjects[sNo];
			}
			return disciplines;
		} else {
			return null;
		}
	}
	
	public void setSelectedDiscipline(Discipline discipline) {
		Model modelToSelect = null;
		if (discipline != null && list_disciplines != null && list_disciplines.getModel() != null && list_disciplines.getModel() instanceof DisciplineListModel) {
			modelToSelect = ((DisciplineListModel) list_disciplines.getModel()).getItemByModelId(discipline.getId());
		}
		list_disciplines.setSelectedValue(modelToSelect, true);
	}
	
	public void optimizeSplit() {
		splitPane.setDividerLocation(list_disciplines.getPreferredSize().width + 5);
	}
	
	public KeyListener getKeyListenerForActionSearchModel() {
		return new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					searchModel();
				} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					selectFirstModelFromModelList();
				}
			}
		};
	}
	
	public KeyListener getKeyListenerForActionEditModel() {
		return new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_F2) {
					editModel();
				}
			}
		};
	}
	
	public ActionListener getActionListenerForActionSearchModel() {
		return new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				searchModel();
			}
		};
	}
	
	public void selectFirstModelFromModelList() {
		list_disciplines.grabFocus();
	}
	
	public ActionListener getActionListenerForActionResetDisciplineGrid() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIHelper.startWaiting(appFrame);
				resetDisciplineGrid();
				UIHelper.stopWaiting(appFrame);
			}
		};
	}
	
	public ActionListener getActionListenerForActionImportParticipationData() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIHelper.startWaiting(appFrame);
				try {
					if (UIHelper.showSureRequest(Messages.getString(Messages.IMPORT_PARTICIPATION_DATA), appFrame)) {
						importParticipationData();
					}
				} catch (Exception e) {
					appFrame.showUserMessage(e, null);
				}
				UIHelper.stopWaiting(appFrame);
			}
		};
	}
	
	public void importParticipationData() throws Exception {
		Participation[] importedParticipations = controller.importParticipationsData(null);
		if (importedParticipations != null) {
			bindData();
			appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE, "Eingelesen: " + importedParticipations.length, importedParticipations[0]);
		} else {
			appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE, "Nix zum importieren", null);
		}
	}
	
	public ActionListener getActionListenerForActionExportParticipationData() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIHelper.startWaiting(appFrame);
				try {
					exportParticipationData();
				} catch (Exception e) {
					appFrame.showUserMessage(e, null);
				}
				UIHelper.stopWaiting(appFrame);
			}
		};
	}
	
	public void exportParticipationData() throws Exception {
		Participation[] exportParticipations = table_participations.getParticipationsTableModel().getAllParticipations();
		if (exportParticipations != null) {
			boolean success = controller.exportParticipationsData(exportParticipations, null);
			appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE, "Exportiert: " + success, exportParticipations[0]);
		} else {
			appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE, "Nix zum exportieren", null);
		}
	}
	
	public void resetDisciplineGrid() {
		if (UIHelper.showSureRequest(Messages.getString(Messages.RESET_DISCIPLINE_GRID), appFrame)) {
			TableModel tableModel = table_participations.getModel();
			if (tableModel != null && tableModel instanceof RichParticipationsTableModel) {
				RichParticipationsTableModel richParticipationsTableModel = (RichParticipationsTableModel) tableModel;
				RichParticipation[] richParticipations = richParticipationsTableModel.getAllParticipations();
				for (RichParticipation richParticipation: richParticipations) {
					richParticipation.setAgeClassRank(0);
					richParticipation.setGenderAgeClassRank(0);
					richParticipation.setGenderRank(0);
					richParticipation.setRank(0);
					richParticipation.setResultTimeAsString(null);
					try {
						controller.saveModel(richParticipation);
					} catch (Exception e) {
						appFrame.showUserMessage(e, richParticipation);
					}
				}
			}
		}
	}
	
	public void searchModel() {
		String searchValue = textfield_search.getText();
		TableModel tableModel = table_participations.getModel();
		if (tableModel != null && tableModel instanceof RichParticipationsTableModel) {
			RichParticipationsTableModel richParticipationsTableModel = (RichParticipationsTableModel) tableModel;
			//case 1: no search value given
			if (searchValue == null || searchValue.length() == 0) {
				table_participations.selectAll();
			} else {
			//case 2: search value given, execute search
				table_participations.getSelectionModel().clearSelection();
				RichParticipation[] richParticipations = richParticipationsTableModel.getAllParticipations();
				for (RichParticipation richParticipation: richParticipations) {
					if (Utils.isSearchStringInsideOfRichParticipation(searchValue, richParticipation)) {
						int rowOfParticipation = richParticipationsTableModel.getRowOfParticipation(richParticipation);
						if (rowOfParticipation >= 0) {
							table_participations.getSelectionModel().addSelectionInterval(rowOfParticipation, rowOfParticipation);							
						}
					}
				}
			}
		}
	}
	
	public void editModel() {
		if (table_participations.getSelectedRowCount() == 1) {
			editSelectedParticipation();
		}
	}
	
	public void focusSearchField() {
		textfield_search.grabFocus();
	}
	
}
