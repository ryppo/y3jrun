package org.y3.jrun.view.model.ageclass;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.y3.jrun.control.Utils;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelForm;

public class AgeClassForm extends ModelForm {

	private static final long serialVersionUID = 1L;
	
	private AgeClass model;
	private JTextField textfield_from;
	private JTextField textfield_to;

	private JLabel label_from;
	private JLabel label_to;
	private JLabel label_title;
	private JTextField textfield_title;

	public AgeClassForm(MODE mode) {
		super(mode);
	}

	@Override
	protected void initForm() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 100, 0, 100, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		label_title = new JLabel(Messages.getString(Messages.TITLE));
		GridBagConstraints gbc_label_title = new GridBagConstraints();
		gbc_label_title.anchor = GridBagConstraints.EAST;
		gbc_label_title.insets = new Insets(0, 0, 0, 5);
		gbc_label_title.gridx = 0;
		gbc_label_title.gridy = 0;
		add(label_title, gbc_label_title);
		
		textfield_title = new JTextField();
		GridBagConstraints gbc_textfield_title = new GridBagConstraints();
		gbc_textfield_title.insets = new Insets(0, 0, 0, 5);
		gbc_textfield_title.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_title.gridx = 1;
		gbc_textfield_title.gridy = 0;
		add(textfield_title, gbc_textfield_title);
		textfield_title.setColumns(10);
		
		label_from = new JLabel(Messages.getString(Messages.YEAR_FROM));
		GridBagConstraints gbc_label_from = new GridBagConstraints();
		gbc_label_from.anchor = GridBagConstraints.EAST;
		gbc_label_from.insets = new Insets(0, 0, 0, 5);
		gbc_label_from.gridx = 2;
		gbc_label_from.gridy = 0;
		add(label_from, gbc_label_from);
		
		textfield_from = new JTextField();
		GridBagConstraints gbc_textfield_from = new GridBagConstraints();
		gbc_textfield_from.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_from.insets = new Insets(0, 0, 0, 5);
		gbc_textfield_from.gridx = 3;
		gbc_textfield_from.gridy = 0;
		add(textfield_from, gbc_textfield_from);
		textfield_from.setColumns(10);
		
		label_to = new JLabel(Messages.getString(Messages.YEAR_TO));
		GridBagConstraints gbc_label_to = new GridBagConstraints();
		gbc_label_to.anchor = GridBagConstraints.EAST;
		gbc_label_to.insets = new Insets(0, 0, 0, 5);
		gbc_label_to.gridx = 4;
		gbc_label_to.gridy = 0;
		add(label_to, gbc_label_to);
		
		textfield_to = new JTextField();
		GridBagConstraints gbc_textfield_to = new GridBagConstraints();
		gbc_textfield_to.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_to.gridx = 5;
		gbc_textfield_to.gridy = 0;
		add(textfield_to, gbc_textfield_to);
		textfield_to.setColumns(10);
	}

	@Override
	public void bindData() {
	}

	@Override
	protected void switchFormToEditMode() {
		textfield_title.setEditable(true);
		textfield_from.setEditable(true);
		textfield_to.setEditable(true);
	}

	@Override
	protected void switchFormToAdministrationMode() {
		switchFormToEditMode();
	}

	@Override
	protected void switchFormToViewMode() {
		textfield_title.setEditable(false);
		textfield_from.setEditable(false);
		textfield_to.setEditable(false);
	}

	@Override
	public void setModel(Model _model) {
		if (_model != null && _model instanceof AgeClass) {
			model = (AgeClass) _model;
			textfield_title.setText(model.getTitle());
			textfield_from.setText(Utils.intToStringWith0AsEmpty(model.getYearFrom()));
			textfield_to.setText(Utils.intToStringWith0AsEmpty(model.getYearTo()));
		} else {
			model = null;
			textfield_title.setText("");
			textfield_from.setText("");
			textfield_to.setText("");
		}
	}

	@Override
	public AgeClass getModel() {
		if (model == null) {
			model = new AgeClass();
		}
		model.setTitle(textfield_title.getText());
		model.setYearFrom(Utils.parseIntValueFromStringDefault0(textfield_from.getText()));
		model.setYearTo(Utils.parseIntValueFromStringDefault0(textfield_to.getText()));
		return model;
	}
	
	public void setForegroundColor(Color color) {
		label_title.setForeground(color);
		label_from.setForeground(color);
		label_to.setForeground(color);
	}

	@Override
	public JComponent getFirstFocusableEditorComponent() {
		return textfield_title;
	}
}
