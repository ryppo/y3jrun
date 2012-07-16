package org.y3.jrun.view.model.contact;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.y3.jrun.model.Model;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelForm;

import eu.hansolo.stickynote.Note;

public class ContactForm extends ModelForm {

	private static final long serialVersionUID = 1L;

	public ContactForm(MODE mode) {
		super(mode);
	}

	private Contact model;
	private JTextField textfield_ID;
	private JTextField textfield_firstname;
	private JTextField textField_lastname;
	private JTextField textField_callname;
	private JTextField textField_address1;
	private JTextField textField_address2;
	private JTextField textField_postal;
	private JTextField textField_city;
	private JTextField textField_birthyear;
	private JTextField textField_phone;
	private JTextField textField_mobile;
	private JTextField textField_email;
	private JComboBox comboBox_gender;
	private Note textArea_comment;
	private JLabel label_comment;

	@Override
	public void setModel(Model _model) {
		if (_model != null && _model instanceof Contact) {
			model = (Contact) _model;
			textfield_ID.setText(Integer.toString(model.getId()));
			textField_address1.setText(model.getAddress1());
			textField_address2.setText(model.getAddress2());
			textField_birthyear.setText(Integer.toString(model.getBirthyear()));
			textfield_firstname.setText(model.getFirstname());
			textField_lastname.setText(model.getLastname());
			textField_callname.setText(model.getName());
			textField_city.setText(model.getCity());
			textField_postal.setText(Integer.toString(model.getPostal()));
			textField_email.setText(model.getEmail());
			textField_phone.setText(model.getPhonenumber());
			textField_mobile.setText(model.getMobilenumber());
			if (model.getGender() != null) {
				if (model.getGender().equals(Contact.gendertype.FEMALE)) {
					comboBox_gender.setSelectedItem(Messages.getString(Messages.GENDER_FEMALE));
				} else if (model.getGender().equals(Contact.gendertype.MALE)) {
					comboBox_gender.setSelectedItem(Messages.getString(Messages.GENDER_MALE));
				}
			}
			textArea_comment.setText(model.getComments());
		} else {
			model = null;
			String dummy = "";
			textfield_ID.setText(dummy);
			textField_address1.setText(dummy);
			textField_address2.setText(dummy);
			textField_birthyear.setText(dummy);
			textField_callname.setText(dummy);
			textField_city.setText(dummy);
			textField_email.setText(dummy);
			textfield_firstname.setText(dummy);
			textField_lastname.setText(dummy);
			textField_phone.setText(dummy);
			textField_mobile.setText(dummy);
			textField_postal.setText(dummy);
			comboBox_gender.setSelectedItem(dummy);
			textArea_comment.setText(dummy);
		}
	}

	@Override
	public Contact getModel() {
		if (model == null) {
			model = new Contact();
		}
		model.setAddress1(textField_address1.getText());
		model.setAddress2(textField_address2.getText());
		String birthyear = textField_birthyear.getText();
		if (birthyear != null && birthyear.length() != 0) {
			model.setBirthyear(Integer.parseInt(birthyear));
		}
		model.setCity(textField_city.getText());
		model.setEmail(textField_email.getText());
		model.setFirstname(textfield_firstname.getText());
		model.setLastname(textField_lastname.getText());
		model.setName(textField_callname.getText());
		String gender = (String) comboBox_gender.getSelectedItem();
		if (gender != null && gender.length() != 0) {
			if (gender.equals(Messages.getString(Messages.GENDER_FEMALE))) {
				model.setGender(Contact.gendertype.FEMALE);
			} else if (gender.equals(Messages.getString(Messages.GENDER_MALE))) {
				model.setGender(Contact.gendertype.MALE);
			}
		}
		model.setPhonenumber(textField_phone.getText());
		model.setMobilenumber(textField_mobile.getText());
		String postal = textField_postal.getText();
		if (postal != null && postal.length() != 0) {
			model.setPostal(Integer.parseInt(postal));
		}
		model.setComments(textArea_comment.getText());
		return model;
	}

	@Override
	protected void initForm() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		gbc_textfield_ID.insets = new Insets(0, 0, 5, 0);
		gbc_textfield_ID.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_ID.gridx = 1;
		gbc_textfield_ID.gridy = 0;
		add(textfield_ID, gbc_textfield_ID);
		textfield_ID.setColumns(10);

		JLabel label_firstname = new JLabel(Messages.getString(Messages.CONTACT_FIRSTNAME));
		GridBagConstraints gbc_label_firstname = new GridBagConstraints();
		gbc_label_firstname.anchor = GridBagConstraints.EAST;
		gbc_label_firstname.insets = new Insets(0, 0, 5, 5);
		gbc_label_firstname.gridx = 0;
		gbc_label_firstname.gridy = 1;
		add(label_firstname, gbc_label_firstname);

		textfield_firstname = new JTextField();
		GridBagConstraints gbc_textfield_firstname = new GridBagConstraints();
		gbc_textfield_firstname.insets = new Insets(0, 0, 5, 0);
		gbc_textfield_firstname.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_firstname.gridx = 1;
		gbc_textfield_firstname.gridy = 1;
		add(textfield_firstname, gbc_textfield_firstname);
		textfield_firstname.setColumns(10);

		JLabel label_lastname = new JLabel(Messages.getString(Messages.CONTACT_LASTNAME));
		GridBagConstraints gbc_label_lastname = new GridBagConstraints();
		gbc_label_lastname.anchor = GridBagConstraints.EAST;
		gbc_label_lastname.insets = new Insets(0, 0, 5, 5);
		gbc_label_lastname.gridx = 0;
		gbc_label_lastname.gridy = 2;
		add(label_lastname, gbc_label_lastname);

		textField_lastname = new JTextField();
		GridBagConstraints gbc_textField_lastname = new GridBagConstraints();
		gbc_textField_lastname.insets = new Insets(0, 0, 5, 0);
		gbc_textField_lastname.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_lastname.gridx = 1;
		gbc_textField_lastname.gridy = 2;
		add(textField_lastname, gbc_textField_lastname);
		textField_lastname.setColumns(10);

		JLabel label_callname = new JLabel(Messages.getString(Messages.CONTACT_CALLNAME));
		GridBagConstraints gbc_label_callname = new GridBagConstraints();
		gbc_label_callname.anchor = GridBagConstraints.EAST;
		gbc_label_callname.insets = new Insets(0, 0, 5, 5);
		gbc_label_callname.gridx = 0;
		gbc_label_callname.gridy = 3;
		add(label_callname, gbc_label_callname);

		textField_callname = new JTextField();
		GridBagConstraints gbc_textField_callname = new GridBagConstraints();
		gbc_textField_callname.insets = new Insets(0, 0, 5, 0);
		gbc_textField_callname.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_callname.gridx = 1;
		gbc_textField_callname.gridy = 3;
		add(textField_callname, gbc_textField_callname);
		textField_callname.setColumns(10);

		JLabel label_address1 = new JLabel(Messages.getString(Messages.CONTACT_ADDRESS_1));
		GridBagConstraints gbc_label_address1 = new GridBagConstraints();
		gbc_label_address1.anchor = GridBagConstraints.EAST;
		gbc_label_address1.insets = new Insets(0, 0, 5, 5);
		gbc_label_address1.gridx = 0;
		gbc_label_address1.gridy = 4;
		add(label_address1, gbc_label_address1);

		textField_address1 = new JTextField();
		GridBagConstraints gbc_textField_address1 = new GridBagConstraints();
		gbc_textField_address1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_address1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_address1.gridx = 1;
		gbc_textField_address1.gridy = 4;
		add(textField_address1, gbc_textField_address1);
		textField_address1.setColumns(10);

		JLabel label_address2 = new JLabel(Messages.getString(Messages.CONTACT_ADDRESS_2));
		GridBagConstraints gbc_label_address2 = new GridBagConstraints();
		gbc_label_address2.anchor = GridBagConstraints.EAST;
		gbc_label_address2.insets = new Insets(0, 0, 5, 5);
		gbc_label_address2.gridx = 0;
		gbc_label_address2.gridy = 5;
		add(label_address2, gbc_label_address2);

		textField_address2 = new JTextField();
		GridBagConstraints gbc_textField_address2 = new GridBagConstraints();
		gbc_textField_address2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_address2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_address2.gridx = 1;
		gbc_textField_address2.gridy = 5;
		add(textField_address2, gbc_textField_address2);
		textField_address2.setColumns(10);

		JLabel label_postalcode = new JLabel(Messages.getString(Messages.CONTACT_POSTALCODE));
		GridBagConstraints gbc_label_postalcode = new GridBagConstraints();
		gbc_label_postalcode.anchor = GridBagConstraints.EAST;
		gbc_label_postalcode.insets = new Insets(0, 0, 5, 5);
		gbc_label_postalcode.gridx = 0;
		gbc_label_postalcode.gridy = 6;
		add(label_postalcode, gbc_label_postalcode);

		textField_postal = new JTextField();
		GridBagConstraints gbc_textField_postal = new GridBagConstraints();
		gbc_textField_postal.insets = new Insets(0, 0, 5, 0);
		gbc_textField_postal.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_postal.gridx = 1;
		gbc_textField_postal.gridy = 6;
		add(textField_postal, gbc_textField_postal);
		textField_postal.setColumns(10);

		JLabel label_city = new JLabel(Messages.getString(Messages.CONTACT_CITY));
		GridBagConstraints gbc_label_city = new GridBagConstraints();
		gbc_label_city.anchor = GridBagConstraints.EAST;
		gbc_label_city.insets = new Insets(0, 0, 5, 5);
		gbc_label_city.gridx = 0;
		gbc_label_city.gridy = 7;
		add(label_city, gbc_label_city);

		textField_city = new JTextField();
		GridBagConstraints gbc_textField_city = new GridBagConstraints();
		gbc_textField_city.insets = new Insets(0, 0, 5, 0);
		gbc_textField_city.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_city.gridx = 1;
		gbc_textField_city.gridy = 7;
		add(textField_city, gbc_textField_city);
		textField_city.setColumns(10);

		JLabel label_birthyear = new JLabel(Messages.getString(Messages.CONTACT_BIRTHYEAR));
		GridBagConstraints gbc_label_birthyear = new GridBagConstraints();
		gbc_label_birthyear.anchor = GridBagConstraints.EAST;
		gbc_label_birthyear.insets = new Insets(0, 0, 5, 5);
		gbc_label_birthyear.gridx = 0;
		gbc_label_birthyear.gridy = 8;
		add(label_birthyear, gbc_label_birthyear);

		textField_birthyear = new JTextField();
		GridBagConstraints gbc_textField_birthyear = new GridBagConstraints();
		gbc_textField_birthyear.insets = new Insets(0, 0, 5, 0);
		gbc_textField_birthyear.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_birthyear.gridx = 1;
		gbc_textField_birthyear.gridy = 8;
		add(textField_birthyear, gbc_textField_birthyear);
		textField_birthyear.setColumns(10);

		JLabel label_gender = new JLabel(Messages.getString(Messages.CONTACT_GENDER));
		GridBagConstraints gbc_label_gender = new GridBagConstraints();
		gbc_label_gender.anchor = GridBagConstraints.EAST;
		gbc_label_gender.insets = new Insets(0, 0, 5, 5);
		gbc_label_gender.gridx = 0;
		gbc_label_gender.gridy = 9;
		add(label_gender, gbc_label_gender);

		comboBox_gender = new JComboBox();
		comboBox_gender.setModel(new DefaultComboBoxModel(new String[] { "",
				Messages.getString(Messages.GENDER_FEMALE), Messages.getString(Messages.GENDER_MALE) }));
		GridBagConstraints gbc_comboBox_gender = new GridBagConstraints();
		gbc_comboBox_gender.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_gender.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_gender.gridx = 1;
		gbc_comboBox_gender.gridy = 9;
		add(comboBox_gender, gbc_comboBox_gender);

		JLabel label_phonenumber = new JLabel(Messages.getString(Messages.CONTACT_PHONENUMBER));
		GridBagConstraints gbc_label_phonenumber = new GridBagConstraints();
		gbc_label_phonenumber.anchor = GridBagConstraints.EAST;
		gbc_label_phonenumber.insets = new Insets(0, 0, 5, 5);
		gbc_label_phonenumber.gridx = 0;
		gbc_label_phonenumber.gridy = 10;
		add(label_phonenumber, gbc_label_phonenumber);

		textField_phone = new JTextField();
		GridBagConstraints gbc_textField_phone = new GridBagConstraints();
		gbc_textField_phone.insets = new Insets(0, 0, 5, 0);
		gbc_textField_phone.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_phone.gridx = 1;
		gbc_textField_phone.gridy = 10;
		add(textField_phone, gbc_textField_phone);
		textField_phone.setColumns(10);

		JLabel label_mobilenumber = new JLabel(Messages.getString(Messages.CONTACT_MOBILENUMBER));
		GridBagConstraints gbc_label_mobilenumber = new GridBagConstraints();
		gbc_label_mobilenumber.anchor = GridBagConstraints.EAST;
		gbc_label_mobilenumber.insets = new Insets(0, 0, 5, 5);
		gbc_label_mobilenumber.gridx = 0;
		gbc_label_mobilenumber.gridy = 11;
		add(label_mobilenumber, gbc_label_mobilenumber);

		textField_mobile = new JTextField();
		GridBagConstraints gbc_textField_mobile = new GridBagConstraints();
		gbc_textField_mobile.insets = new Insets(0, 0, 5, 0);
		gbc_textField_mobile.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_mobile.gridx = 1;
		gbc_textField_mobile.gridy = 11;
		add(textField_mobile, gbc_textField_mobile);
		textField_mobile.setColumns(10);

		JLabel lblEmail = new JLabel(Messages.getString(Messages.CONTACT_EMAIL));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 12;
		add(lblEmail, gbc_lblEmail);

		textField_email = new JTextField();
		GridBagConstraints gbc_textField_email = new GridBagConstraints();
		gbc_textField_email.insets = new Insets(0, 0, 5, 0);
		gbc_textField_email.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_email.gridx = 1;
		gbc_textField_email.gridy = 12;
		add(textField_email, gbc_textField_email);
		textField_email.setColumns(10);
		
		label_comment = new JLabel(Messages.getString(Messages.CONTACT_COMMENT));
		GridBagConstraints gbc_label_comment = new GridBagConstraints();
		gbc_label_comment.anchor = GridBagConstraints.EAST;
		gbc_label_comment.insets = new Insets(0, 0, 0, 5);
		gbc_label_comment.gridx = 0;
		gbc_label_comment.gridy = 13;
		add(label_comment, gbc_label_comment);
		
		textArea_comment = new Note();
		GridBagConstraints gbc_textArea_comment = new GridBagConstraints();
		gbc_textArea_comment.anchor = GridBagConstraints.NORTH;
		gbc_textArea_comment.gridheight = 2;
		gbc_textArea_comment.fill = GridBagConstraints.BOTH;
		gbc_textArea_comment.gridx = 1;
		gbc_textArea_comment.gridy = 13;
		add(textArea_comment, gbc_textArea_comment);
	}

	@Override
	protected void switchFormToEditMode() {
		switchFormToAdministrationMode();
	}

	@Override
	protected void switchFormToAdministrationMode() {
		textField_address1.setEditable(true);
		textField_address2.setEditable(true);
		textField_birthyear.setEditable(true);
		textField_callname.setEditable(true);
		textField_city.setEditable(true);
		textField_email.setEditable(true);
		textfield_firstname.setEditable(true);
		textField_lastname.setEditable(true);
		textField_mobile.setEditable(true);
		textField_phone.setEditable(true);
		textField_postal.setEditable(true);
		comboBox_gender.setEnabled(true);
	}

	@Override
	protected void switchFormToViewMode() {
		textField_address1.setEditable(false);
		textField_address2.setEditable(false);
		textField_birthyear.setEditable(false);
		textField_callname.setEditable(false);
		textField_city.setEditable(false);
		textField_email.setEditable(false);
		textfield_firstname.setEditable(false);
		textField_lastname.setEditable(false);
		textField_mobile.setEditable(false);
		textField_phone.setEditable(false);
		textField_postal.setEditable(false);
		comboBox_gender.setEnabled(false);
	}

	@Override
	public void bindData() {
	}

	@Override
	public JComponent getFirstFocusableEditorComponent() {
		return textfield_firstname;
	}

}
