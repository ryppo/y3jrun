package org.y3.jrun.view.model;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ModelReportable;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.reporting.ReportFactory;
import org.y3.jrun.view.reporting.ReportingController;

public abstract class ModelPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public ApplicationController controller;
	public ApplicationFrame appFrame;
	public ModelForm modelForm;
	public ModelListPanel jp_modelList;
	public ModelPanelButtonPanel jp_buttons;
	public JMenu jmenu;
	public JLabel label_sortedBy;
	private JSplitPane jsplit_content;

	/**
	 * Create the panel.
	 */
	public ModelPanel(ApplicationController _controller,
			ApplicationFrame _appFrame) {
		controller = _controller;
		appFrame = _appFrame;
		init();
	}

	private void init() {
		setLayout(new BorderLayout(0, 0));
		jsplit_content = new JSplitPane();
		add(jsplit_content, BorderLayout.CENTER);
		
		jmenu = new ModelMenu(this);
		
		jp_buttons = new ModelPanelButtonPanel(this);
		add(jp_buttons, BorderLayout.NORTH);
		
		addStandardFunctionsToMenuAndButtonPanel();
		addFunctionsToMenuAndButtonPanel();
		
		createModelForm();
		JScrollPane jscroll_modelForm = new JScrollPane(modelForm);
		jsplit_content.setRightComponent(jscroll_modelForm);
		
		jp_modelList = new ModelListPanel(appFrame, controller, modelForm);
		jsplit_content.setLeftComponent(jp_modelList);
		
		label_sortedBy = new JLabel();
		add(label_sortedBy, BorderLayout.SOUTH);
	}
	
	public abstract String getPanelTitle();

	protected abstract void createModelForm();
	
	private void addStandardFunctionsToMenuAndButtonPanel() {
		
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.REFRESH), 
				Messages.getString(Messages.REFRESH),
				getActionListenerForActionRefresh());
		
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.REPORT),
				Messages.getString(Messages.REPORT),
				getActionListenerForActionReportModelList());
		
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.HISTORY),
				Messages.getString(Messages.HISTORY),
				getActionListenerForActionHistory());
		
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.STATISTICS),
				Messages.getString(Messages.STATISTICS),
				getActionListenerForActionStatistics());
		
		addSeparatorToMenuAndButtonPanel();
	}

	protected abstract void addFunctionsToMenuAndButtonPanel();
	
	public JMenu getMenu() {
		return jmenu;
	}
	
	public abstract void bindData() throws IOException, ClassNotFoundException, SQLException;
	
	public abstract JPanel getSpecificStatisticsPanel();
	
	public abstract void bindDataForModelById(int modelId) throws IOException, ClassNotFoundException, SQLException;
	
	protected void actionSaveModel() {
		UIHelper.startWaiting(appFrame);
		Model model = modelForm.getModel();
		if (model != null) {
			try {
				if (controller.saveModel(model)) {
					appFrame.showUserMessage(
							JOptionPane.INFORMATION_MESSAGE,
							model + " " + Messages.getString(Messages.SUCCESSFULLY_SAVED),
							model);
				} else {
					appFrame.showUserMessage(
							JOptionPane.WARNING_MESSAGE,
							model + " " + Messages.getString(Messages.NOT_SAVED),
							model);
				}
			} catch (Exception e) {
				appFrame.showUserMessage(e, model);
			}
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	protected void actionDeleteModel() {
		UIHelper.startWaiting(appFrame);
		Model model = modelForm.getModel();
		if (model != null) {
			try {
				if (controller.deleteModel(model)) {
					appFrame.showUserMessage(
							JOptionPane.INFORMATION_MESSAGE,
							model + " " + Messages.getString(Messages.SUCCESSFULLY_REMOVED),
							model);
					modelForm.setModel(null);
				} else {
					appFrame.showUserMessage(
							JOptionPane.WARNING_MESSAGE,
							model + " " + Messages.getString(Messages.NOT_REMOVED),
							model);
				}
			} catch (Exception e) {
				appFrame.showUserMessage(e, model);
			}
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	protected abstract void actionSortModels();
	
	public abstract Icon getIcon();

	public void actionNewModel() {
		modelForm.setModel(null);
		modelForm.switchMode(ModelForm.MODE.EDITOR);
	}
	
	public void actionRefresh() {
		try {
			bindData();
			optimizeSplit();
		} catch (Exception e) {
			appFrame.showUserMessage(e, null);
		}
	}

	public void actionShowHistory() {
		appFrame.showNotificationsForModel(jp_modelList.getSelectedModel());
	}
	
	public void actionShowStatistics() {
		UIHelper.startWaiting(appFrame);
		ModelStatisticsDialog statisticsDialog = new ModelStatisticsDialog(appFrame, jp_modelList.getSelectedModel(), getSpecificStatisticsPanel());
		statisticsDialog.setVisible(true);
		statisticsDialog.dispose();
		UIHelper.stopWaiting(appFrame);
	}
	
	public void actionReportModelList() {
		UIHelper.startWaiting(appFrame);
		ModelReportable reportable = new ModelReportable(jp_modelList.getModel(), getPanelTitle());
		ReportingController.report(ReportFactory.createModelListReport(reportable), appFrame);
		UIHelper.stopWaiting(appFrame);
	}
	
	public void addFunctionToMenuAndButtonPanel(Icon icon, String name, ActionListener actionListener) {
		
		JMenuItem jmi = new JMenuItem(name, icon);
		jmi.addActionListener(actionListener);
		jmenu.add(jmi);
		
		JButton jb = new JButton(icon);
		if (icon == null) {
			jb.setText(name);
		} else {
			jb.setToolTipText(name);
		}
		jb.addActionListener(actionListener);
		jp_buttons.add(jb);
	}
	
	public void addSeparatorToMenuAndButtonPanel() {
		jmenu.add(new JSeparator(JSeparator.HORIZONTAL));
		jp_buttons.add(new JSeparator(JSeparator.VERTICAL));
	}
	
	public void addFunctionToMenuAndButtonPanelForActionNewModel() {
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.NEW), 
				Messages.getString(Messages.NEW), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionNewModel();
			}
		});
	}
	
	public void addFunctionToMenuAndButtonPanelForActionRemoveModel() {
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.REMOVE), 
				Messages.getString(Messages.REMOVE), new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionDeleteModel();
				try {
					bindData();
				} catch (Exception e) {
					appFrame.showUserMessage(e, null);
				}
			}
		});
	}
	
	public void addFunctionToMenuAndButtonPanelForActionSaveModel() {
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.SAVE), 
				Messages.getString(Messages.SAVE), getActionListenerForActionSaveModel());
	}
	
	public ActionListener getActionListenerForActionRefresh() {
		return new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				actionRefresh();
			}
		};
	}
	
	public ActionListener getActionListenerForActionHistory() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionShowHistory();
			}
		};
	}
	
	public ActionListener getActionListenerForActionStatistics() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				actionShowStatistics();
			}
		};
	}
	
	public ActionListener getActionListenerForActionSaveModel() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionSaveModel();
				try {
					bindData();
				} catch (Exception e) {
					appFrame.showUserMessage(e, null);
				}
			}
		};
	}
	
	public ActionListener getActionListenerForActionReportModelList() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionReportModelList();
			}
		};
	}
	
	public void optimizeSplit() {
		jsplit_content.setDividerLocation(jp_modelList.getPreferredSize().width + 5);
	}
	
}
