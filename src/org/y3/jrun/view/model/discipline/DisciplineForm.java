package org.y3.jrun.view.model.discipline;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.y3.jrun.model.Model;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelForm;

public class DisciplineForm extends ModelForm {

	private static final long serialVersionUID = 1L;
	private Discipline discipline;
	
	private JTextField textfield_ID;
	private JTextField textfield_name;
	private JTextField textfield_length;
	private JTextField textfield_start;
	private JComboBox combobox_format_resulttime;
	private JLabel label_priceInEurocent;
	private JTextField textfield_priceInEurocent;

	public DisciplineForm(MODE mode) {
		super(mode);
	}

	@Override
	protected void initForm() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel label_ID = new JLabel(Messages.getString(Messages.DISCIPLINE_ID));
		GridBagConstraints gbc_label_ID = new GridBagConstraints();
		gbc_label_ID.insets = new Insets(0, 0, 5, 5);
		gbc_label_ID.anchor = GridBagConstraints.EAST;
		gbc_label_ID.gridx = 0;
		gbc_label_ID.gridy = 0;
		add(label_ID, gbc_label_ID);
		
		textfield_ID = new JTextField();
		textfield_ID.setEditable(false);
		GridBagConstraints gbc_textfield_ID = new GridBagConstraints();
		gbc_textfield_ID.insets = new Insets(0, 0, 5, 0);
		gbc_textfield_ID.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_ID.gridx = 1;
		gbc_textfield_ID.gridy = 0;
		add(textfield_ID, gbc_textfield_ID);
		textfield_ID.setColumns(10);
		
		JLabel label_name = new JLabel(Messages.getString(Messages.DISCIPLINE_NAME));
		GridBagConstraints gbc_label_name = new GridBagConstraints();
		gbc_label_name.anchor = GridBagConstraints.EAST;
		gbc_label_name.insets = new Insets(0, 0, 5, 5);
		gbc_label_name.gridx = 0;
		gbc_label_name.gridy = 1;
		add(label_name, gbc_label_name);
		
		textfield_name = new JTextField();
		GridBagConstraints gbc_textfield_name = new GridBagConstraints();
		gbc_textfield_name.insets = new Insets(0, 0, 5, 0);
		gbc_textfield_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_name.gridx = 1;
		gbc_textfield_name.gridy = 1;
		add(textfield_name, gbc_textfield_name);
		textfield_name.setColumns(10);
		
		JLabel label_length = new JLabel(Messages.getString(Messages.DISCIPLINE_LENGTH));
		GridBagConstraints gbc_label_length = new GridBagConstraints();
		gbc_label_length.anchor = GridBagConstraints.EAST;
		gbc_label_length.insets = new Insets(0, 0, 5, 5);
		gbc_label_length.gridx = 0;
		gbc_label_length.gridy = 2;
		add(label_length, gbc_label_length);
		
		textfield_length = new JTextField();
		GridBagConstraints gbc_textfield_length = new GridBagConstraints();
		gbc_textfield_length.insets = new Insets(0, 0, 5, 0);
		gbc_textfield_length.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_length.gridx = 1;
		gbc_textfield_length.gridy = 2;
		add(textfield_length, gbc_textfield_length);
		textfield_length.setColumns(10);
		
		JLabel label_start = new JLabel(Messages.getString(Messages.DISCIPLINE_START));
		GridBagConstraints gbc_label_start = new GridBagConstraints();
		gbc_label_start.anchor = GridBagConstraints.EAST;
		gbc_label_start.insets = new Insets(0, 0, 5, 5);
		gbc_label_start.gridx = 0;
		gbc_label_start.gridy = 3;
		add(label_start, gbc_label_start);
		
		textfield_start = new JTextField();
		GridBagConstraints gbc_textfield_start = new GridBagConstraints();
		gbc_textfield_start.insets = new Insets(0, 0, 5, 0);
		gbc_textfield_start.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_start.gridx = 1;
		gbc_textfield_start.gridy = 3;
		add(textfield_start, gbc_textfield_start);
		textfield_start.setColumns(10);
		
		JLabel label_resulttime_format = new JLabel(Messages.getString(Messages.RESULT_TIME_FORMAT));
		GridBagConstraints gbc_label_resulttime_format = new GridBagConstraints();
		gbc_label_resulttime_format.anchor = GridBagConstraints.EAST;
		gbc_label_resulttime_format.insets = new Insets(0, 0, 5, 5);
		gbc_label_resulttime_format.gridx = 0;
		gbc_label_resulttime_format.gridy = 4;
		add(label_resulttime_format, gbc_label_resulttime_format);
		
		combobox_format_resulttime = new JComboBox();
		combobox_format_resulttime.setModel(new DefaultComboBoxModel(new String[] { "",
				Messages.getString(Messages.HH_mm_ss_SSS), Messages.getString(Messages.HH_mm_ss),
				Messages.getString(Messages.mm_ss)}));
		GridBagConstraints gbc_combobox_format_resulttime = new GridBagConstraints();
		gbc_combobox_format_resulttime.insets = new Insets(0, 0, 5, 0);
		gbc_combobox_format_resulttime.fill = GridBagConstraints.HORIZONTAL;
		gbc_combobox_format_resulttime.gridx = 1;
		gbc_combobox_format_resulttime.gridy = 4;
		add(combobox_format_resulttime, gbc_combobox_format_resulttime);
		
		label_priceInEurocent = new JLabel(Messages.getString(Messages.DISCIPLINE_PRICE_IN_EUROCENT));
		GridBagConstraints gbc_label_priceInEurocent = new GridBagConstraints();
		gbc_label_priceInEurocent.anchor = GridBagConstraints.EAST;
		gbc_label_priceInEurocent.insets = new Insets(0, 0, 0, 5);
		gbc_label_priceInEurocent.gridx = 0;
		gbc_label_priceInEurocent.gridy = 5;
		add(label_priceInEurocent, gbc_label_priceInEurocent);
		
		textfield_priceInEurocent = new JTextField();
		GridBagConstraints gbc_textfield_priceInEurocent = new GridBagConstraints();
		gbc_textfield_priceInEurocent.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_priceInEurocent.gridx = 1;
		gbc_textfield_priceInEurocent.gridy = 5;
		add(textfield_priceInEurocent, gbc_textfield_priceInEurocent);
		textfield_priceInEurocent.setColumns(10);
	}

	@Override
	public void bindData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void switchFormToEditMode() {
		textfield_name.setEditable(true);
		textfield_length.setEditable(true);
		textfield_start.setEditable(true);

	}

	@Override
	protected void switchFormToAdministrationMode() {
		switchFormToEditMode();

	}

	@Override
	protected void switchFormToViewMode() {
		textfield_name.setEditable(false);
		textfield_length.setEditable(false);
		textfield_start.setEditable(false);
	}

	@Override
	public void setModel(Model _model) {
		if (_model != null && _model instanceof Discipline) {
			discipline = (Discipline) _model;
			textfield_ID.setText(Integer.toString(discipline.getId()));
			textfield_name.setText(discipline.getName());
			textfield_length.setText(discipline.getLength());
			textfield_start.setText(Messages.getFormattedDate(discipline.getStartOfDiscipline()));
			if (discipline.getDurationFormat() != null) {
				if (discipline.getDurationFormat().equals(Discipline.durationFormat.HH_mm_ss_SSS)) {
					combobox_format_resulttime.setSelectedItem(Messages.getString(Messages.HH_mm_ss_SSS));
				} else if (discipline.getDurationFormat().equals(Discipline.durationFormat.HH_mm_ss)) {
					combobox_format_resulttime.setSelectedItem(Messages.getString(Messages.HH_mm_ss));
				} else if (discipline.getDurationFormat().equals(Discipline.durationFormat.mm_ss)) {
					combobox_format_resulttime.setSelectedItem(Messages.getString(Messages.mm_ss));
				}
			}
			double euroValue = (double) discipline.getPriceInEuroCent() / 100;
			textfield_priceInEurocent.setText(Double.toString(euroValue));
		} else {
			discipline = null;
			String dummy = "";
			textfield_ID.setText(dummy);
			textfield_name.setText(dummy);
			textfield_length.setText(dummy);
			textfield_start.setText(dummy);
			combobox_format_resulttime.setSelectedItem(dummy);
		}
	}

	@Override
	public Discipline getModel() {
		if (discipline == null) {
			discipline = new Discipline();
		}
		discipline.setName(textfield_name.getText());
		discipline.setLength(textfield_length.getText());
		discipline.setStartOfDiscipline(Messages.getFormattedDate(textfield_start.getText()));
		if (combobox_format_resulttime.getSelectedItem().toString().equals(Messages.getString(Messages.HH_mm_ss_SSS))) {
			discipline.setDurationFormat(Discipline.durationFormat.HH_mm_ss_SSS);
		} else if (combobox_format_resulttime.getSelectedItem().toString().equals(Messages.getString(Messages.HH_mm_ss))) {
			discipline.setDurationFormat(Discipline.durationFormat.HH_mm_ss);
		} else if (combobox_format_resulttime.getSelectedItem().toString().equals(Messages.getString(Messages.mm_ss))) {
			discipline.setDurationFormat(Discipline.durationFormat.mm_ss);
		}
		String euroValueText = textfield_priceInEurocent.getText();
		if (euroValueText != null && euroValueText.length() != 0) {
			double euroValue = Double.parseDouble(euroValueText.toString());
			discipline.setPriceInEuroCent((int) (euroValue * 100));
		} else {
			discipline.setPriceInEuroCent(0);
		}
		return discipline;
	}

	@Override
	public JComponent getFirstFocusableEditorComponent() {
		return textfield_name;
	}

}
