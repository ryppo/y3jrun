package org.y3.jrun.view.model.ageclassesdefinition;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.model.ageclass.AgeClassTableModel;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinition;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelForm;
import org.y3.jrun.view.model.ageclass.AgeClassTableCellEditor;
import org.y3.jrun.view.model.ageclass.AgeClassTableCellRenderer;

public class AgeClassesDefinitionForm extends ModelForm {

	private static final long serialVersionUID = 1L;
	
	private AgeClassesDefinition model;
	private JTextField textfield_name;
	private JTable ageClassesList;
	private ApplicationController controller;
	private ApplicationFrame mainFrame;
	private JScrollPane scrollPane;

	/**
	 * @wbp.parser.constructor
	 */
	public AgeClassesDefinitionForm(MODE mode) {
		super(mode);
	}
	
	public AgeClassesDefinitionForm(ApplicationController _controller, MODE mode, ApplicationFrame _mainFrame) {
		super(mode);
		controller = _controller;
		mainFrame = _mainFrame;
	}

	@Override
	protected void initForm() {
		setLayout(new BorderLayout());
		JSplitPane splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		add(splitpane, BorderLayout.CENTER);
		
		JPanel panel_ageClassesDefinitionForm = new JPanel();
		splitpane.setLeftComponent(panel_ageClassesDefinitionForm);
		GridBagLayout gbl_panel_ageClassesDefinitionForm = new GridBagLayout();
		gbl_panel_ageClassesDefinitionForm.columnWidths = new int[]{0, 0, 0};
		gbl_panel_ageClassesDefinitionForm.rowHeights = new int[]{0, 0};
		gbl_panel_ageClassesDefinitionForm.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_ageClassesDefinitionForm.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_ageClassesDefinitionForm.setLayout(gbl_panel_ageClassesDefinitionForm);
		
		JLabel label_name = new JLabel(Messages.getString(Messages.NAME));
		GridBagConstraints gbc_label_name = new GridBagConstraints();
		gbc_label_name.insets = new Insets(0, 5, 0, 5);
		gbc_label_name.gridx = 0;
		gbc_label_name.gridy = 0;
		panel_ageClassesDefinitionForm.add(label_name, gbc_label_name);
		
		textfield_name = new JTextField();
		GridBagConstraints gbc_textfield_name = new GridBagConstraints();
		gbc_textfield_name.insets = new Insets(0, 0, 0, 5);
		gbc_textfield_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_name.gridx = 1;
		gbc_textfield_name.gridy = 0;
		panel_ageClassesDefinitionForm.add(textfield_name, gbc_textfield_name);
		
		JPanel panel_ageClassesForms = new JPanel(new BorderLayout());
		splitpane.setRightComponent(panel_ageClassesForms);
		
		scrollPane = new JScrollPane();
		panel_ageClassesForms.add(scrollPane, BorderLayout.CENTER);
		
		ageClassesList = new JTable();
		ageClassesList.setAutoCreateRowSorter(true);
		ageClassesList.setDefaultRenderer(AgeClass.class, new AgeClassTableCellRenderer());
		scrollPane.setViewportView(ageClassesList);
		
	}

	@Override
	public void bindData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void switchFormToEditMode() {
		textfield_name.setEditable(true);
	}

	@Override
	protected void switchFormToAdministrationMode() {
		switchFormToEditMode();
	}

	@Override
	protected void switchFormToViewMode() {
		textfield_name.setEditable(false);

	}

	@Override
	public void setModel(Model _model) {
		UIHelper.startWaiting(mainFrame);
		try {
			if (_model != null && _model instanceof AgeClassesDefinition) {
				model = (AgeClassesDefinition) _model;
				ageClassesList
						.setModel(controller
								.getAgeClassesForAgeClassesDefinitionAsTableModel(model));
				ageClassesList
						.getColumnModel()
						.getColumn(0)
						.setCellEditor(
								new AgeClassTableCellEditor(mainFrame,
										controller));
				optimizeTableHeight();
				textfield_name.setText(model.getName());
			} else {
				model = null;
				textfield_name.setText("");
				ageClassesList
						.setModel(new DefaultTableModel());
			}
		} catch (Exception e) {
			mainFrame.showUserMessage(e, _model);
		}
		UIHelper.stopWaiting(mainFrame);
	}

	@Override
	public AgeClassesDefinition getModel() {
		if (model == null) {
			model = new AgeClassesDefinition();
		}
		model.setName(textfield_name.getText());
		return model;
	}
	
	public void actionAddNewAgeClass() {
		AgeClassTableModel ageClasses = null;
		if (ageClassesList.getModel() instanceof AgeClassTableModel) {
			ageClasses = (AgeClassTableModel) ageClassesList.getModel();
		}
		if (ageClasses == null) {
			ageClasses = new AgeClassTableModel();
		}
		AgeClass ageClass = new AgeClass();
		ageClass.setRelatedAgeClassesDefinitionID(model.getId());
		ageClasses.addRow(new AgeClass[]{ageClass});
		optimizeTableHeight();
	}
	
	public void actionRemoveAgeClass() {
		UIHelper.startWaiting(mainFrame);
		if (ageClassesList != null && ageClassesList.getModel() != null && ageClassesList.getModel() instanceof AgeClassTableModel) {
			Object model = ((AgeClassTableModel) ageClassesList.getModel()).getValueAt(ageClassesList.getSelectedRow(), 0);
			if (model != null && model instanceof AgeClass) {
				try {
					controller.deleteModel((AgeClass) model);
					this.setModel(getModel());
				} catch (Exception e) {
					mainFrame.showUserMessage(e, (AgeClass) model);
				}
			}
		}
		UIHelper.startWaiting(mainFrame);
	}
	
	private void optimizeTableHeight() {
		if (ageClassesList != null && ageClassesList.getModel() != null && ageClassesList.getModel().getRowCount() != 0) {
			int height = ageClassesList.getRowHeight();
		    TableCellRenderer renderer = ageClassesList.getCellRenderer(0, 0);
		    Component comp = ageClassesList.prepareRenderer(renderer, 0, 0);
		    int margin = 5;
		    int h = comp.getPreferredSize().height + 2*margin;
		    height = Math.max(height, h);
			ageClassesList.setRowHeight(height);
		}
	}

	@Override
	public JComponent getFirstFocusableEditorComponent() {
		return textfield_name;
	}

}
