package org.y3.jrun.view.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.y3.jrun.model.Model;

public class ModelListCell extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel label_creationdate;
	private JLabel label_creator;
	private JLabel label_changedate;
	private JLabel label_changer;
	private JLabel label_title;
	private JLabel label_creation;
	private JLabel label_change;
	private JLabel label_version;
	private JLabel label_importFingerPrint;
	private JLabel label_importFingerPrintString;

	public ModelListCell(Model model) {
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		label_title = new JLabel(model.toString());
		label_title.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		GridBagConstraints gbc_label_title = new GridBagConstraints();
		gbc_label_title.insets = new Insets(5, 5, 5, 5);
		gbc_label_title.fill = GridBagConstraints.HORIZONTAL;
		gbc_label_title.gridwidth = 3;
		gbc_label_title.gridx = 0;
		gbc_label_title.gridy = 0;
		add(label_title, gbc_label_title);
		
		label_version = new JLabel("v" + Integer.toString(model.getVersion()));
		GridBagConstraints gbc_label_version = new GridBagConstraints();
		gbc_label_version.insets = new Insets(0, 5, 5, 5);
		gbc_label_version.gridx = 3;
		gbc_label_version.gridy = 0;
		add(label_version, gbc_label_version);
		
		label_creation = new JLabel("Creation");
		label_creation.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_label_creation = new GridBagConstraints();
		gbc_label_creation.anchor = GridBagConstraints.EAST;
		gbc_label_creation.insets = new Insets(0, 5, 5, 5);
		gbc_label_creation.gridx = 0;
		gbc_label_creation.gridy = 1;
		add(label_creation, gbc_label_creation);
		
		String creationdate = "";
		if (model.getCreationDate() != null) {
			creationdate = new SimpleDateFormat().format(model.getCreationDate());
		}
		label_creationdate = new JLabel(creationdate);
		label_creationdate.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_textfield_creationdate = new GridBagConstraints();
		gbc_textfield_creationdate.anchor = GridBagConstraints.NORTH;
		gbc_textfield_creationdate.insets = new Insets(0, 0, 5, 5);
		gbc_textfield_creationdate.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_creationdate.gridx = 1;
		gbc_textfield_creationdate.gridy = 1;
		add(label_creationdate, gbc_textfield_creationdate);
		
		label_creator = new JLabel(model.getCreatorName());
		label_creator.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_textfield_creator = new GridBagConstraints();
		gbc_textfield_creator.insets = new Insets(0, 5, 5, 5);
		gbc_textfield_creator.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_creator.gridx = 2;
		gbc_textfield_creator.gridy = 1;
		add(label_creator, gbc_textfield_creator);
		
		label_change = new JLabel("Change");
		label_change.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_label_change = new GridBagConstraints();
		gbc_label_change.anchor = GridBagConstraints.EAST;
		gbc_label_change.insets = new Insets(0, 5, 5, 5);
		gbc_label_change.gridx = 0;
		gbc_label_change.gridy = 2;
		add(label_change, gbc_label_change);
		
		String changedate = "";
		if (model.getChangeDate() != null) {
			changedate = new SimpleDateFormat().format(model.getChangeDate());
		}
		label_changedate = new JLabel(changedate);
		label_changedate.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_textfield_changedate = new GridBagConstraints();
		gbc_textfield_changedate.insets = new Insets(0, 0, 5, 5);
		gbc_textfield_changedate.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_changedate.gridx = 1;
		gbc_textfield_changedate.gridy = 2;
		add(label_changedate, gbc_textfield_changedate);
		
		label_changer = new JLabel(model.getChangerName());
		label_changer.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_textfield_changer = new GridBagConstraints();
		gbc_textfield_changer.insets = new Insets(0, 5, 5, 5);
		gbc_textfield_changer.anchor = GridBagConstraints.NORTH;
		gbc_textfield_changer.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_changer.gridx = 2;
		gbc_textfield_changer.gridy = 2;
		add(label_changer, gbc_textfield_changer);
		
		label_importFingerPrint = new JLabel("Fingerprint");
		label_importFingerPrint.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_label_importFingerPrint = new GridBagConstraints();
		gbc_label_importFingerPrint.anchor = GridBagConstraints.EAST;
		gbc_label_importFingerPrint.insets = new Insets(0, 5, 5, 5);
		gbc_label_importFingerPrint.gridx = 0;
		gbc_label_importFingerPrint.gridy = 3;
		add(label_importFingerPrint, gbc_label_importFingerPrint);
		
		label_importFingerPrintString = new JLabel(model.getImportFingerPrint());
		label_importFingerPrintString.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_label_importFingerPrintString = new GridBagConstraints();
		gbc_label_importFingerPrintString.gridwidth = 2;
		gbc_label_importFingerPrintString.anchor = GridBagConstraints.WEST;
		gbc_label_importFingerPrintString.insets = new Insets(0, 0, 0, 5);
		gbc_label_importFingerPrintString.gridx = 1;
		gbc_label_importFingerPrintString.gridy = 3;
		add(label_importFingerPrintString, gbc_label_importFingerPrintString);
		
	}
	
	public void setForegroundColor(Color color) {
		label_title.setForeground(color);
		label_creation.setForeground(color);
		label_creator.setForeground(color);
		label_creationdate.setForeground(color);
		label_change.setForeground(color);
		label_changer.setForeground(color);
		label_changedate.setForeground(color);
	}
	
}
