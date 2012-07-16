package org.y3.jrun.view.model.participation;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.control.Utils;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ModelListModel;
import org.y3.jrun.model.competition.Competition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.model.participation.Participation;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelForm;
import org.y3.jrun.view.model.ModelView_helper;
import org.y3.jrun.view.model.contact.ContactFormDialog;
import org.y3.jrun.view.model.discipline.DisciplineFormDialog;

import eu.hansolo.stickynote.Note;

public class ParticipationForm extends ModelForm {

	private static final long serialVersionUID = 1L;
	private JTextField textfield_ID;
	private JFormattedTextField textfield_resultTime;
	private JTextField textfield_participationNumber;
	private JComboBox combobox_contact;
	private JComboBox combobox_competition;
	private ApplicationController controller;
	private ApplicationFrame mainFrame;
	
	private Participation model;
	private JComboBox combobox_discipline;
	private JButton button_newContact;
	private JButton button_newDiscipline;
	private JLabel label_feePaid;
	private JCheckBox checkbox_feePaid;
	private JLabel label_certficationHandedOver;
	private JCheckBox checkbox_certificationHandedOver;
	private JLabel label_rankValue;
	private JLabel label_fingerprint;
	private JTextField textfield_fingerprint;
	private JLabel label_noncompetiti;
	private JCheckBox checkbox_noncompetitive;
	private JLabel label_rank;
	private JLabel label_ageClassRank;
	private JLabel label_ageClassRankValue;
	private JLabel label_genderRank;
	private JLabel label_genderRankValue;
	private JLabel label_genderAgeClassRank;
	private JLabel label_genderAgeClassRankValue;
	private Note textArea_comment;
	private JLabel label_donationHospiz;
	private JTextField textfield_donationHospiz;
	private JLabel label_registrationsDate;
	private JTextField textfieldRegistration_value;
	private JLabel label_registratedOnline;
	private JCheckBox checkbox_registratedOnline;

	/**
	 * @wbp.parser.constructor
	 */
	public ParticipationForm(MODE mode) {
		super(mode);
	}
	
	public ParticipationForm(ApplicationController _controller, MODE mode, ApplicationFrame _mainFrame) {
		super(mode);
		controller = _controller;
		mainFrame = _mainFrame;
	}

	@Override
	protected void initForm() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel label_ID = new JLabel("ID");
		GridBagConstraints gbc_label_ID = new GridBagConstraints();
		gbc_label_ID.insets = new Insets(0, 0, 5, 5);
		gbc_label_ID.anchor = GridBagConstraints.EAST;
		gbc_label_ID.gridx = 0;
		gbc_label_ID.gridy = 0;
		add(label_ID, gbc_label_ID);
		
		textfield_ID = new JTextField();
		textfield_ID.setEditable(false);
		GridBagConstraints gbc_textfield_ID = new GridBagConstraints();
		gbc_textfield_ID.gridwidth = 2;
		gbc_textfield_ID.insets = new Insets(0, 0, 5, 5);
		gbc_textfield_ID.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_ID.gridx = 1;
		gbc_textfield_ID.gridy = 0;
		add(textfield_ID, gbc_textfield_ID);
		textfield_ID.setColumns(10);
		
		JLabel label_participationNumber = new JLabel(Messages.getString(Messages.START_NUMMER));
		GridBagConstraints gbc_label_participationNumber = new GridBagConstraints();
		gbc_label_participationNumber.anchor = GridBagConstraints.EAST;
		gbc_label_participationNumber.insets = new Insets(0, 0, 5, 5);
		gbc_label_participationNumber.gridx = 3;
		gbc_label_participationNumber.gridy = 0;
		add(label_participationNumber, gbc_label_participationNumber);
		
		textfield_participationNumber = new JTextField();
		GridBagConstraints gbc_textfield_participationNumber = new GridBagConstraints();
		gbc_textfield_participationNumber.gridwidth = 2;
		gbc_textfield_participationNumber.insets = new Insets(0, 0, 5, 0);
		gbc_textfield_participationNumber.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_participationNumber.gridx = 4;
		gbc_textfield_participationNumber.gridy = 0;
		add(textfield_participationNumber, gbc_textfield_participationNumber);
		textfield_participationNumber.setColumns(10);
		
		JLabel lblCompetition = new JLabel(Messages.getString(Messages.COMPETITION));
		GridBagConstraints gbc_lblCompetition = new GridBagConstraints();
		gbc_lblCompetition.anchor = GridBagConstraints.EAST;
		gbc_lblCompetition.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompetition.gridx = 0;
		gbc_lblCompetition.gridy = 1;
		add(lblCompetition, gbc_lblCompetition);
		
		combobox_competition = new JComboBox();
		GridBagConstraints gbc_combobox_competition = new GridBagConstraints();
		gbc_combobox_competition.gridwidth = 2;
		gbc_combobox_competition.insets = new Insets(0, 0, 5, 5);
		gbc_combobox_competition.fill = GridBagConstraints.HORIZONTAL;
		gbc_combobox_competition.gridx = 1;
		gbc_combobox_competition.gridy = 1;
		add(combobox_competition, gbc_combobox_competition);
		
		JLabel label_contact = new JLabel(Messages.getString(Messages.PARTICIPANT));
		GridBagConstraints gbc_label_contact = new GridBagConstraints();
		gbc_label_contact.anchor = GridBagConstraints.EAST;
		gbc_label_contact.insets = new Insets(0, 0, 5, 5);
		gbc_label_contact.gridx = 3;
		gbc_label_contact.gridy = 1;
		add(label_contact, gbc_label_contact);
		
		combobox_contact = new JComboBox();
		GridBagConstraints gbc_combobox_contact = new GridBagConstraints();
		gbc_combobox_contact.insets = new Insets(0, 0, 5, 5);
		gbc_combobox_contact.fill = GridBagConstraints.HORIZONTAL;
		gbc_combobox_contact.gridx = 4;
		gbc_combobox_contact.gridy = 1;
		add(combobox_contact, gbc_combobox_contact);
		
		button_newContact = new JButton(IconDictionary.getImageIcon(IconDictionary.NEW));
		GridBagConstraints gbc_button_newContact = new GridBagConstraints();
		gbc_button_newContact.insets = new Insets(0, 0, 5, 0);
		gbc_button_newContact.gridx = 5;
		gbc_button_newContact.gridy = 1;
		button_newContact.addActionListener(getActionListenerForActionNewContact());
		add(button_newContact, gbc_button_newContact);
		
		JLabel lblDisziplin = new JLabel(Messages.getString(Messages.DISCIPLINE));
		GridBagConstraints gbc_lblDisziplin = new GridBagConstraints();
		gbc_lblDisziplin.anchor = GridBagConstraints.EAST;
		gbc_lblDisziplin.insets = new Insets(0, 0, 5, 5);
		gbc_lblDisziplin.gridx = 0;
		gbc_lblDisziplin.gridy = 2;
		add(lblDisziplin, gbc_lblDisziplin);
		
		combobox_discipline = new JComboBox();
		combobox_discipline.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				switchSelectedDiscipline((Discipline) combobox_discipline.getSelectedItem());
			}
		});
		combobox_discipline.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				switchSelectedDiscipline((Discipline) combobox_discipline.getSelectedItem());	
			}
		});
		GridBagConstraints gbc_combobox_discipline = new GridBagConstraints();
		gbc_combobox_discipline.insets = new Insets(0, 0, 5, 5);
		gbc_combobox_discipline.fill = GridBagConstraints.HORIZONTAL;
		gbc_combobox_discipline.gridx = 1;
		gbc_combobox_discipline.gridy = 2;
		add(combobox_discipline, gbc_combobox_discipline);
		
		button_newDiscipline = new JButton(IconDictionary.getImageIcon(IconDictionary.NEW));
		GridBagConstraints gbc_button_newDiscipline = new GridBagConstraints();
		gbc_button_newDiscipline.insets = new Insets(0, 0, 5, 5);
		gbc_button_newDiscipline.gridx = 2;
		gbc_button_newDiscipline.gridy = 2;
		button_newDiscipline.addActionListener(getActionListenerForActionNewDiscipline());
		add(button_newDiscipline, gbc_button_newDiscipline);
		
		JLabel lblResultTime = new JLabel(Messages.getString(Messages.RESULT_TIME));
		GridBagConstraints gbc_lblResultTime = new GridBagConstraints();
		gbc_lblResultTime.anchor = GridBagConstraints.EAST;
		gbc_lblResultTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblResultTime.gridx = 3;
		gbc_lblResultTime.gridy = 2;
		add(lblResultTime, gbc_lblResultTime);
		
		textfield_resultTime = new JFormattedTextField(new DateFormatter(new SimpleDateFormat(Messages.getString(Messages.HH_mm_ss))));
		GridBagConstraints gbc_textfield_resultTime = new GridBagConstraints();
		gbc_textfield_resultTime.insets = new Insets(0, 0, 5, 0);
		gbc_textfield_resultTime.gridwidth = 2;
		gbc_textfield_resultTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_resultTime.gridx = 4;
		gbc_textfield_resultTime.gridy = 2;
		add(textfield_resultTime, gbc_textfield_resultTime);
		textfield_resultTime.setColumns(10);
		
		label_donationHospiz = new JLabel(Messages.getString(Messages.DONATION_HOSPIZ));
		GridBagConstraints gbc_label_donationHospiz = new GridBagConstraints();
		gbc_label_donationHospiz.anchor = GridBagConstraints.EAST;
		gbc_label_donationHospiz.insets = new Insets(0, 0, 5, 5);
		gbc_label_donationHospiz.gridx = 0;
		gbc_label_donationHospiz.gridy = 3;
		add(label_donationHospiz, gbc_label_donationHospiz);
		
		textfield_donationHospiz = new JTextField();
		GridBagConstraints gbc_textfield_donationHospiz = new GridBagConstraints();
		gbc_textfield_donationHospiz.gridwidth = 2;
		gbc_textfield_donationHospiz.insets = new Insets(0, 0, 5, 5);
		gbc_textfield_donationHospiz.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_donationHospiz.gridx = 1;
		gbc_textfield_donationHospiz.gridy = 3;
		add(textfield_donationHospiz, gbc_textfield_donationHospiz);
		textfield_donationHospiz.setColumns(10);
		
		label_certficationHandedOver = new JLabel(Messages.getString(Messages.CERTIFICATION_HANDEDOVER));
		GridBagConstraints gbc_label_certficationHandedOver = new GridBagConstraints();
		gbc_label_certficationHandedOver.anchor = GridBagConstraints.EAST;
		gbc_label_certficationHandedOver.insets = new Insets(0, 0, 5, 5);
		gbc_label_certficationHandedOver.gridx = 3;
		gbc_label_certficationHandedOver.gridy = 3;
		add(label_certficationHandedOver, gbc_label_certficationHandedOver);
		
		checkbox_certificationHandedOver = new JCheckBox();
		GridBagConstraints gbc_checkbox_certificationHandedOver = new GridBagConstraints();
		gbc_checkbox_certificationHandedOver.anchor = GridBagConstraints.WEST;
		gbc_checkbox_certificationHandedOver.insets = new Insets(0, 0, 5, 5);
		gbc_checkbox_certificationHandedOver.gridx = 4;
		gbc_checkbox_certificationHandedOver.gridy = 3;
		add(checkbox_certificationHandedOver, gbc_checkbox_certificationHandedOver);
		
		label_feePaid = new JLabel(Messages.getString(Messages.FEE_PAID));
		GridBagConstraints gbc_label_feePaid = new GridBagConstraints();
		gbc_label_feePaid.anchor = GridBagConstraints.EAST;
		gbc_label_feePaid.insets = new Insets(0, 0, 5, 5);
		gbc_label_feePaid.gridx = 0;
		gbc_label_feePaid.gridy = 4;
		add(label_feePaid, gbc_label_feePaid);
		
		checkbox_feePaid = new JCheckBox();
		GridBagConstraints gbc_checkbox_feePaid = new GridBagConstraints();
		gbc_checkbox_feePaid.anchor = GridBagConstraints.WEST;
		gbc_checkbox_feePaid.insets = new Insets(0, 0, 5, 5);
		gbc_checkbox_feePaid.gridx = 1;
		gbc_checkbox_feePaid.gridy = 4;
		add(checkbox_feePaid, gbc_checkbox_feePaid);
		
		label_rank = new JLabel(Messages.getString(Messages.RANKING));
		GridBagConstraints gbc_label_rank = new GridBagConstraints();
		gbc_label_rank.anchor = GridBagConstraints.EAST;
		gbc_label_rank.insets = new Insets(0, 0, 5, 5);
		gbc_label_rank.gridx = 3;
		gbc_label_rank.gridy = 4;
		add(label_rank, gbc_label_rank);
		
		label_rankValue = new JLabel();
		label_rankValue.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		GridBagConstraints gbc_label_rankValue = new GridBagConstraints();
		gbc_label_rankValue.gridwidth = 2;
		gbc_label_rankValue.insets = new Insets(0, 0, 5, 0);
		gbc_label_rankValue.gridx = 4;
		gbc_label_rankValue.gridy = 4;
		add(label_rankValue, gbc_label_rankValue);
		
		label_noncompetiti = new JLabel(Messages.getString(Messages.NONCOMPETITIVE));
		GridBagConstraints gbc_label_noncompetiti = new GridBagConstraints();
		gbc_label_noncompetiti.anchor = GridBagConstraints.EAST;
		gbc_label_noncompetiti.insets = new Insets(0, 0, 5, 5);
		gbc_label_noncompetiti.gridx = 0;
		gbc_label_noncompetiti.gridy = 5;
		add(label_noncompetiti, gbc_label_noncompetiti);
		
		checkbox_noncompetitive = new JCheckBox();
		GridBagConstraints gbc_checkbox_noncompetitive = new GridBagConstraints();
		gbc_checkbox_noncompetitive.anchor = GridBagConstraints.WEST;
		gbc_checkbox_noncompetitive.insets = new Insets(0, 0, 5, 5);
		gbc_checkbox_noncompetitive.gridx = 1;
		gbc_checkbox_noncompetitive.gridy = 5;
		add(checkbox_noncompetitive, gbc_checkbox_noncompetitive);
		
		label_ageClassRank = new JLabel(Messages.getString(Messages.AGECLASS_RANKING));
		GridBagConstraints gbc_label_ageClassRank = new GridBagConstraints();
		gbc_label_ageClassRank.anchor = GridBagConstraints.EAST;
		gbc_label_ageClassRank.insets = new Insets(0, 0, 5, 5);
		gbc_label_ageClassRank.gridx = 3;
		gbc_label_ageClassRank.gridy = 5;
		add(label_ageClassRank, gbc_label_ageClassRank);
		
		label_ageClassRankValue = new JLabel();
		GridBagConstraints gbc_label_ageClassRankValue = new GridBagConstraints();
		gbc_label_ageClassRankValue.insets = new Insets(0, 0, 5, 0);
		gbc_label_ageClassRankValue.gridwidth = 2;
		gbc_label_ageClassRankValue.gridx = 4;
		gbc_label_ageClassRankValue.gridy = 5;
		add(label_ageClassRankValue, gbc_label_ageClassRankValue);
		
		label_registrationsDate = new JLabel(Messages.getString(Messages.REGISTRATION_DATE) + ":");
		GridBagConstraints gbc_label_registrationsDate = new GridBagConstraints();
		gbc_label_registrationsDate.anchor = GridBagConstraints.EAST;
		gbc_label_registrationsDate.insets = new Insets(0, 0, 5, 5);
		gbc_label_registrationsDate.gridx = 0;
		gbc_label_registrationsDate.gridy = 6;
		add(label_registrationsDate, gbc_label_registrationsDate);
		
		textfieldRegistration_value = new JTextField();
		GridBagConstraints gbc_textfieldRegistration_value = new GridBagConstraints();
		gbc_textfieldRegistration_value.gridwidth = 2;
		gbc_textfieldRegistration_value.insets = new Insets(0, 0, 5, 5);
		gbc_textfieldRegistration_value.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfieldRegistration_value.gridx = 1;
		gbc_textfieldRegistration_value.gridy = 6;
		add(textfieldRegistration_value, gbc_textfieldRegistration_value);
		textfieldRegistration_value.setColumns(10);
		
		label_genderRank = new JLabel(Messages.getString(Messages.GENDER_RANKING));
		GridBagConstraints gbc_label_genderRank = new GridBagConstraints();
		gbc_label_genderRank.anchor = GridBagConstraints.EAST;
		gbc_label_genderRank.insets = new Insets(0, 0, 5, 5);
		gbc_label_genderRank.gridx = 3;
		gbc_label_genderRank.gridy = 6;
		add(label_genderRank, gbc_label_genderRank);
		
		label_genderRankValue = new JLabel();
		GridBagConstraints gbc_label_genderRankValue = new GridBagConstraints();
		gbc_label_genderRankValue.insets = new Insets(0, 0, 5, 0);
		gbc_label_genderRankValue.gridwidth = 2;
		gbc_label_genderRankValue.anchor = GridBagConstraints.SOUTH;
		gbc_label_genderRankValue.gridx = 4;
		gbc_label_genderRankValue.gridy = 6;
		add(label_genderRankValue, gbc_label_genderRankValue);
		
		label_registratedOnline = new JLabel(Messages.getString(Messages.REGISTRATED_ONLINE));
		GridBagConstraints gbc_label_registratedOnline = new GridBagConstraints();
		gbc_label_registratedOnline.anchor = GridBagConstraints.EAST;
		gbc_label_registratedOnline.insets = new Insets(0, 0, 5, 5);
		gbc_label_registratedOnline.gridx = 0;
		gbc_label_registratedOnline.gridy = 7;
		add(label_registratedOnline, gbc_label_registratedOnline);
		
		checkbox_registratedOnline = new JCheckBox();
		GridBagConstraints gbc_checkbox_registratedOnline = new GridBagConstraints();
		gbc_checkbox_registratedOnline.anchor = GridBagConstraints.WEST;
		gbc_checkbox_registratedOnline.insets = new Insets(0, 0, 5, 5);
		gbc_checkbox_registratedOnline.gridx = 1;
		gbc_checkbox_registratedOnline.gridy = 7;
		add(checkbox_registratedOnline, gbc_checkbox_registratedOnline);
		
		label_fingerprint = new JLabel("Fingerprint: ");
		GridBagConstraints gbc_label_fingerprint = new GridBagConstraints();
		gbc_label_fingerprint.anchor = GridBagConstraints.EAST;
		gbc_label_fingerprint.insets = new Insets(0, 0, 5, 5);
		gbc_label_fingerprint.gridx = 0;
		gbc_label_fingerprint.gridy = 8;
		add(label_fingerprint, gbc_label_fingerprint);
		
		textfield_fingerprint = new JTextField();
		textfield_fingerprint.setEditable(false);
		GridBagConstraints gbc_textfield_fingerprint = new GridBagConstraints();
		gbc_textfield_fingerprint.gridwidth = 2;
		gbc_textfield_fingerprint.insets = new Insets(0, 0, 5, 5);
		gbc_textfield_fingerprint.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_fingerprint.gridx = 1;
		gbc_textfield_fingerprint.gridy = 8;
		add(textfield_fingerprint, gbc_textfield_fingerprint);
		textfield_fingerprint.setColumns(10);
		
		label_genderAgeClassRank = new JLabel(Messages.getString(Messages.GENDER_AGECLASS_RANKING));
		GridBagConstraints gbc_label_genderAgeClassRank = new GridBagConstraints();
		gbc_label_genderAgeClassRank.anchor = GridBagConstraints.EAST;
		gbc_label_genderAgeClassRank.insets = new Insets(0, 0, 5, 5);
		gbc_label_genderAgeClassRank.gridx = 3;
		gbc_label_genderAgeClassRank.gridy = 8;
		add(label_genderAgeClassRank, gbc_label_genderAgeClassRank);
		
		label_genderAgeClassRankValue = new JLabel();
		GridBagConstraints gbc_label_genderAgeClassRankValue = new GridBagConstraints();
		gbc_label_genderAgeClassRankValue.insets = new Insets(0, 0, 5, 0);
		gbc_label_genderAgeClassRankValue.gridwidth = 2;
		gbc_label_genderAgeClassRankValue.gridx = 4;
		gbc_label_genderAgeClassRankValue.gridy = 8;
		add(label_genderAgeClassRankValue, gbc_label_genderAgeClassRankValue);
		
		textArea_comment = new Note();
		GridBagConstraints gbc_textArea_comment = new GridBagConstraints();
		gbc_textArea_comment.insets = new Insets(0, 0, 0, 5);
		gbc_textArea_comment.gridwidth = 3;
		gbc_textArea_comment.anchor = GridBagConstraints.NORTH;
		gbc_textArea_comment.gridheight = 2;
		gbc_textArea_comment.fill = GridBagConstraints.BOTH;
		gbc_textArea_comment.gridx = 1;
		gbc_textArea_comment.gridy = 9;
		add(textArea_comment, gbc_textArea_comment);
	}

	@Override
	protected void switchFormToEditMode() {
		switchFormToAdministrationMode();
	}

	@Override
	protected void switchFormToAdministrationMode() {
		textfield_participationNumber.setEditable(true);
		textfield_resultTime.setEditable(true);
		textfield_donationHospiz.setEditable(true);
		combobox_competition.setEnabled(true);
		combobox_contact.setEnabled(true);
		combobox_discipline.setEnabled(true);
		checkbox_feePaid.setEnabled(true);
		checkbox_certificationHandedOver.setEnabled(true);
		checkbox_noncompetitive.setEnabled(true);
		textfieldRegistration_value.setEditable(true);
		checkbox_registratedOnline.setEnabled(true);
	}

	@Override
	protected void switchFormToViewMode() {
		textfield_participationNumber.setEditable(false);
		textfield_resultTime.setEditable(false);
		textfield_donationHospiz.setEditable(false);
		combobox_competition.setEnabled(false);
		combobox_contact.setEnabled(false);
		combobox_discipline.setEnabled(false);
		checkbox_feePaid.setEnabled(false);
		checkbox_certificationHandedOver.setEnabled(false);
		checkbox_noncompetitive.setEnabled(false);
		textfieldRegistration_value.setEditable(false);
		checkbox_registratedOnline.setEnabled(false);
	}

	@Override
	public void setModel(Model _model) {
		if (_model != null && _model instanceof Participation) {
			model = (Participation) _model;
			textfield_ID.setText(Integer.toString(model.getId()));
			textfield_participationNumber.setText(model.getParticipationNumber());
			textfield_resultTime.setValue(model.getResultTimeAsDate());
			textfield_fingerprint.setText(model.getImportFingerPrint());
			textfield_fingerprint.setToolTipText(model.getImportFingerPrint());
			double euroValue = (double) model.getDonationHospizInEuroCent() / 100;
			textfield_donationHospiz.setText(Double.toString(euroValue));
			ComboBoxModel cbmodel = combobox_competition.getModel();
			if (cbmodel != null && cbmodel instanceof ModelListModel) {
				((ModelListModel) cbmodel).setSelectedModelByID(model.getCompetitionId());
			}
			cbmodel = combobox_contact.getModel();
			if (cbmodel != null && cbmodel instanceof ModelListModel) {
				((ModelListModel) cbmodel).setSelectedModelByID(model.getContactId());
			}
			cbmodel = combobox_discipline.getModel();
			if (cbmodel != null && cbmodel instanceof ModelListModel) {
				((ModelListModel) cbmodel).setSelectedModelByID(model.getDisciplineId());
			}
			checkbox_feePaid.setSelected(model.isPaymentDone());
			checkbox_certificationHandedOver.setSelected(model.isCertificationHandedOver());
			checkbox_noncompetitive.setSelected(model.isNoncompetitive());
			setRankValue(label_rankValue, model.getRank());
			setRankValue(label_ageClassRankValue, model.getAgeClassRank());
			setRankValue(label_genderRankValue, model.getGenderRank());
			setRankValue(label_genderAgeClassRankValue, model.getGenderAgeClassRank());
			textfieldRegistration_value.setText(Messages.getFormattedDate(model.getCreationDate()));
			textArea_comment.setText(model.getComment());
			checkbox_registratedOnline.setSelected(model.isRegisteredOnline());
		} else {
			String dummy = "";
			model = null;
			textfield_ID.setText(dummy);
			textfield_participationNumber.setText(dummy);
			textfield_resultTime.setValue(new Date(-3600000));
			textfield_fingerprint.setText(dummy);
			textfield_fingerprint.setToolTipText(dummy);
			textfield_donationHospiz.setText(dummy);
			combobox_competition.setSelectedItem(null);
			combobox_contact.setSelectedItem(null);
			combobox_discipline.setSelectedItem(null);
			checkbox_feePaid.setSelected(false);
			checkbox_certificationHandedOver.setSelected(false);
			checkbox_noncompetitive.setSelected(false);
			setRankValue(label_rankValue, 0);
			setRankValue(label_ageClassRankValue, 0);
			setRankValue(label_genderRankValue, 0);
			setRankValue(label_genderAgeClassRankValue, 0);
			textfieldRegistration_value.setText(dummy);
			textArea_comment.setText(dummy);
			checkbox_registratedOnline.setSelected(false);
		}
	}
	
	private void setRankValue(JLabel label_rank, int rank) {
		if (rank != 0) {
			label_rank.setText(Integer.toString(rank) + ". " + Messages.getString(Messages.RANK));
		} else {
			label_rank.setText("");
		}
		if (rank == 1) {
			label_rank.setIcon(IconDictionary.getImageIcon(IconDictionary.CERTIFICATION));
		} else {
			label_rank.setIcon(null);
		}
	}

	@Override
	public Participation getModel() {
		if (model == null) {
			model = new Participation();
		}
		int id = 0;
		Object o = combobox_competition.getSelectedItem();
		if (o != null && o instanceof Competition) {
			id = ((Competition) o).getId();
		}
		model.setCompetitionId(id);
		o = combobox_contact.getSelectedItem();
		if (o != null && o instanceof Contact) {
			id = ((Contact) o).getId();
		} else {
			id = 0;
		}
		model.setContactId(id);
		o = combobox_discipline.getSelectedItem();
		if (o != null && o instanceof Discipline) {
			id = ((Discipline) o).getId();
		} else {
			id = 0;
		}
		model.setDisciplineId(id);
		model.setResultTimeAsDate((Date)textfield_resultTime.getValue());
		model.setParticipationNumber(textfield_participationNumber.getText());
		String euroValueText = textfield_donationHospiz.getText();
		if (euroValueText != null && euroValueText.length() != 0) {
			int euroValue = Utils.parseIntValueFromStringDefault0(euroValueText);
			model.setDonationHospizInEuroCent((int) (euroValue * 100));
		} else {
			model.setDonationHospizInEuroCent(0);
		}
		model.setPaymentDone(checkbox_feePaid.isSelected());
		model.setCertificationHandedOver(checkbox_certificationHandedOver.isSelected());
		model.setNoncompetitive(checkbox_noncompetitive.isSelected());
		model.setComment(textArea_comment.getText());
		model.setCreationDate(Messages.getFormattedDate(textfieldRegistration_value.getText()));
		model.setRegisteredOnline(checkbox_registratedOnline.isSelected());
		return model;
	}
	
	public void setSelectedCompetition(Competition competition) {
		ModelView_helper.setSelectedModelInComboBox(combobox_competition, competition);
	}
	
	public void setSelectedContact(Contact contact) {
		ModelView_helper.setSelectedModelInComboBox(combobox_contact, contact);
	}
	
	public void setSelectedDiscipline(Discipline discipline) {
		ModelView_helper.setSelectedModelInComboBox(combobox_discipline, discipline);
	}
	
	public void setSelectedCompetitonAnContact(Competition competition, Contact contact) {
		setSelectedCompetition(competition);
		setSelectedContact(contact);
	}
	
	public void bindData() {
		try {
			if (controller != null) {
				combobox_competition.setModel(controller.getAllCompetitions());
				combobox_contact.setModel(controller.getAllContacts());
				combobox_discipline.setModel(controller.getAllDisciplines());
				checkbox_feePaid.setSelected(false);
				checkbox_certificationHandedOver.setSelected(false);
				checkbox_noncompetitive.setSelected(false);
			}
		} catch (Exception e) {
			mainFrame.showUserMessage(e, model);
		}
	}
	
	private void actionNewContact() {
		ContactFormDialog contactFormDialog = new ContactFormDialog(null, mainFrame, controller);
		contactFormDialog.setVisible(true);
		if (contactFormDialog.isOperationSucceeded()) {
			Contact newContact = contactFormDialog.getModel();
			try {
				combobox_contact.setModel(controller.getAllContacts());
			} catch (Exception e) {
				mainFrame.showUserMessage(e, getModel());
			}
			setSelectedContact(newContact);
		}
	}
	
	private void actionNewDiscipline() {
		DisciplineFormDialog disciplineFormDialog = new DisciplineFormDialog(null, mainFrame, controller);
		disciplineFormDialog.setVisible(true);
		if (disciplineFormDialog.isOperationSucceeded()) {
			Discipline newDiscipline = disciplineFormDialog.getModel();
			try {
				combobox_discipline.setModel(controller.getAllDisciplines());
			} catch (Exception e) {
				mainFrame.showUserMessage(e, getModel());
			}
			setSelectedDiscipline(newDiscipline);
		}
	}
	
	public ActionListener getActionListenerForActionNewContact() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionNewContact();
			}
		};
	}
	
	public ActionListener getActionListenerForActionNewDiscipline() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionNewDiscipline();
			}
		};
	}
	
	private void switchSelectedDiscipline(Discipline discipline) {
		if (discipline != null && discipline.getDurationFormat() != null) {
			DateFormatter dateFormatter = (DateFormatter) textfield_resultTime.getFormatter();
			dateFormatter.setFormat(new SimpleDateFormat(Messages.getString(discipline.getDurationFormat().toString())));
			textfield_resultTime.setValue(textfield_resultTime.getValue());
		}
	}

	@Override
	public JComponent getFirstFocusableEditorComponent() {
		return textfield_participationNumber;
	}
}
