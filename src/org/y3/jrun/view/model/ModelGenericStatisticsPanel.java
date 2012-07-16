package org.y3.jrun.view.model;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.y3.jrun.model.Model;
import org.y3.jrun.view.i18n.Messages;

public class ModelGenericStatisticsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Model model;

	private JLabel labelToString;

	private JLabel label_idValue;
	private JLabel label_creatorValue;
	private JLabel label_creation;
	private JLabel label_creationValue;
	private JLabel label_changerValue;
	private JLabel label_change;
	private JLabel label_changeValue;
	private JLabel label_importFingerPrint;
	private JLabel label_importFingerPrintValue;

	/**
	 * Create the panel.
	 */
	public ModelGenericStatisticsPanel(Model _model) {
		model = _model;
		init();
		bindData();
	}
	
	private void bindData() {
		String empty = "";
		if (model != null) {
			labelToString.setText(model.toString());
			label_idValue.setText(Integer.toString(model.getId()));
			label_changeValue.setText(new SimpleDateFormat().format(model.getChangeDate()));
			label_changerValue.setText(model.getChangerName());
			label_creationValue.setText(new SimpleDateFormat().format(model.getCreationDate()));
			label_creatorValue.setText(model.getCreatorName());
			label_importFingerPrintValue.setText(model.getImportFingerPrint());
		} else {
			labelToString.setText(empty);
			label_idValue.setText(empty);
			label_changeValue.setText(empty);
			label_changerValue.setText(empty);
			label_creationValue.setText(empty);
			label_creatorValue.setText(empty);
			label_importFingerPrintValue.setText(empty);
		}
		
	}
	
	private void init() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		labelToString = new JLabel();
		labelToString.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_labelToString = new GridBagConstraints();
		gbc_labelToString.gridwidth = 4;
		gbc_labelToString.insets = new Insets(5, 5, 5, 5);
		gbc_labelToString.fill = GridBagConstraints.HORIZONTAL;
		gbc_labelToString.gridx = 0;
		gbc_labelToString.gridy = 0;
		add(labelToString, gbc_labelToString);
		
		JLabel label_id = new JLabel(Messages.getString(Messages.MODEL_ID) + ":");
		GridBagConstraints gbc_label_id = new GridBagConstraints();
		gbc_label_id.anchor = GridBagConstraints.EAST;
		gbc_label_id.insets = new Insets(5, 5, 5, 5);
		gbc_label_id.gridx = 0;
		gbc_label_id.gridy = 1;
		add(label_id, gbc_label_id);
		
		label_idValue = new JLabel();
		GridBagConstraints gbc_label_idValue = new GridBagConstraints();
		gbc_label_idValue.anchor = GridBagConstraints.WEST;
		gbc_label_idValue.insets = new Insets(5, 5, 5, 5);
		gbc_label_idValue.gridx = 2;
		gbc_label_idValue.gridy = 1;
		add(label_idValue, gbc_label_idValue);
		
		label_creatorValue = new JLabel();
		GridBagConstraints gbc_label_creatorValue = new GridBagConstraints();
		gbc_label_creatorValue.anchor = GridBagConstraints.WEST;
		gbc_label_creatorValue.insets = new Insets(5, 5, 5, 5);
		gbc_label_creatorValue.gridx = 3;
		gbc_label_creatorValue.gridy = 2;
		add(label_creatorValue, gbc_label_creatorValue);
		
		label_creation = new JLabel(Messages.getString(Messages.MODEL_CREATION) + ":");
		GridBagConstraints gbc_label_creation = new GridBagConstraints();
		gbc_label_creation.anchor = GridBagConstraints.EAST;
		gbc_label_creation.insets = new Insets(5, 5, 5, 5);
		gbc_label_creation.gridx = 0;
		gbc_label_creation.gridy = 2;
		add(label_creation, gbc_label_creation);
		
		label_creationValue = new JLabel();
		GridBagConstraints gbc_label_creationValue = new GridBagConstraints();
		gbc_label_creationValue.anchor = GridBagConstraints.WEST;
		gbc_label_creationValue.insets = new Insets(5, 5, 5, 5);
		gbc_label_creationValue.gridx = 2;
		gbc_label_creationValue.gridy = 2;
		add(label_creationValue, gbc_label_creationValue);
		
		label_changerValue = new JLabel();
		GridBagConstraints gbc_label_changerValue = new GridBagConstraints();
		gbc_label_changerValue.anchor = GridBagConstraints.WEST;
		gbc_label_changerValue.insets = new Insets(5, 5, 5, 5);
		gbc_label_changerValue.gridx = 3;
		gbc_label_changerValue.gridy = 3;
		add(label_changerValue, gbc_label_changerValue);
		
		label_change = new JLabel(Messages.getString(Messages.MODEL_CHANGE) + ":");
		GridBagConstraints gbc_label_change = new GridBagConstraints();
		gbc_label_change.anchor = GridBagConstraints.EAST;
		gbc_label_change.insets = new Insets(5, 5, 5, 5);
		gbc_label_change.gridx = 0;
		gbc_label_change.gridy = 3;
		add(label_change, gbc_label_change);
		
		label_changeValue = new JLabel();
		GridBagConstraints gbc_label_changeValue = new GridBagConstraints();
		gbc_label_changeValue.anchor = GridBagConstraints.WEST;
		gbc_label_changeValue.insets = new Insets(5, 5, 5, 5);
		gbc_label_changeValue.gridx = 2;
		gbc_label_changeValue.gridy = 3;
		add(label_changeValue, gbc_label_changeValue);
		
		label_importFingerPrint = new JLabel(Messages.getString(Messages.MODEL_IMPORTFINGERPRINT) + ":");
		GridBagConstraints gbc_label_fingerPrint = new GridBagConstraints();
		gbc_label_fingerPrint.anchor = GridBagConstraints.EAST;
		gbc_label_fingerPrint.insets = new Insets(5, 5, 5, 5);
		gbc_label_fingerPrint.gridx = 0;
		gbc_label_fingerPrint.gridy = 4;
		add(label_importFingerPrint, gbc_label_fingerPrint);
		
		label_importFingerPrintValue = new JLabel();
		GridBagConstraints gbc_label_fingerPrintValue = new GridBagConstraints();
		gbc_label_fingerPrintValue.gridwidth = 2;
		gbc_label_fingerPrintValue.anchor = GridBagConstraints.WEST;
		gbc_label_fingerPrintValue.insets = new Insets(5, 5, 5, 5);
		gbc_label_fingerPrintValue.gridx = 1;
		gbc_label_fingerPrintValue.gridy = 4;
		add(label_importFingerPrintValue, gbc_label_fingerPrintValue);
		
	}

}
