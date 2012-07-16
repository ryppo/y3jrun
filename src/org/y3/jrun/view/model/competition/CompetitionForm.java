package org.y3.jrun.view.model.competition;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ModelListModel;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinition;
import org.y3.jrun.model.competition.Competition;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelForm;
import org.y3.jrun.view.model.ModelView_helper;

public class CompetitionForm extends ModelForm {

	private Competition model;
	private static final long serialVersionUID = 1L;
	private JTextField textfield_ID;
	private JTextField textfield_title;
	private JTextField textfield_startOfCompetition;
	private JTextField textfield_endOfCompetition;
	private final String dummy = "";
	private JLabel label_ageClassesDefinition;
	private JComboBox combobox_ageClassesDefinition;
	private ApplicationController controller;
	private ApplicationFrame mainFrame;
	private JLabel label_webPage;
	private JLabel label_registrationPage;
	private JLabel label_registrationsLogFile;
	private JTextField textfield_webpage;
	private JTextField textfield_registrationPage;
	private JTextField textfield_registrationsLogFile;
	private JLabel label_openWebPage;
	private JLabel label_openRegistrationPage;
	
	public CompetitionForm(ApplicationController _controller, MODE mode, ApplicationFrame _mainFrame) {
		super(mode);
		controller = _controller;
		mainFrame = _mainFrame;
	}

	@Override
	protected void initForm() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel label_ID = new JLabel(Messages.getString(Messages.MODEL_ID));
		GridBagConstraints gbc_label_ID = new GridBagConstraints();
		gbc_label_ID.insets = new Insets(0, 0, 5, 5);
		gbc_label_ID.anchor = GridBagConstraints.EAST;
		gbc_label_ID.gridx = 0;
		gbc_label_ID.gridy = 0;
		add(label_ID, gbc_label_ID);
		
		textfield_ID = new JTextField();
		GridBagConstraints gbc_textfield_ID = new GridBagConstraints();
		gbc_textfield_ID.insets = new Insets(0, 0, 5, 5);
		gbc_textfield_ID.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_ID.gridx = 1;
		gbc_textfield_ID.gridy = 0;
		add(textfield_ID, gbc_textfield_ID);
		textfield_ID.setColumns(10);
		textfield_ID.setEditable(false);
		
		JLabel lblTitle = new JLabel(Messages.getString(Messages.COMPETITION_TITLE));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.EAST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 1;
		add(lblTitle, gbc_lblTitle);
		
		textfield_title = new JTextField();
		GridBagConstraints gbc_textfield_title = new GridBagConstraints();
		gbc_textfield_title.insets = new Insets(0, 0, 5, 5);
		gbc_textfield_title.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_title.gridx = 1;
		gbc_textfield_title.gridy = 1;
		add(textfield_title, gbc_textfield_title);
		textfield_title.setColumns(10);
		
		JLabel label_startOfCompetition = new JLabel(Messages.getString(Messages.COMPETITION_START));
		GridBagConstraints gbc_label_startOfCompetition = new GridBagConstraints();
		gbc_label_startOfCompetition.anchor = GridBagConstraints.EAST;
		gbc_label_startOfCompetition.insets = new Insets(0, 0, 5, 5);
		gbc_label_startOfCompetition.gridx = 0;
		gbc_label_startOfCompetition.gridy = 2;
		add(label_startOfCompetition, gbc_label_startOfCompetition);
		
		textfield_startOfCompetition = new JTextField();
		GridBagConstraints gbc_textfield_startOfCompetition = new GridBagConstraints();
		gbc_textfield_startOfCompetition.insets = new Insets(0, 0, 5, 5);
		gbc_textfield_startOfCompetition.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_startOfCompetition.gridx = 1;
		gbc_textfield_startOfCompetition.gridy = 2;
		add(textfield_startOfCompetition, gbc_textfield_startOfCompetition);
		textfield_startOfCompetition.setColumns(10);
		
		JLabel label_endOfCompetition = new JLabel(Messages.getString(Messages.COMPETITION_END));
		GridBagConstraints gbc_label_endOfCompetition = new GridBagConstraints();
		gbc_label_endOfCompetition.anchor = GridBagConstraints.EAST;
		gbc_label_endOfCompetition.insets = new Insets(0, 0, 5, 5);
		gbc_label_endOfCompetition.gridx = 0;
		gbc_label_endOfCompetition.gridy = 3;
		add(label_endOfCompetition, gbc_label_endOfCompetition);
		
		textfield_endOfCompetition = new JTextField();
		GridBagConstraints gbc_textfield_endOfCompetition = new GridBagConstraints();
		gbc_textfield_endOfCompetition.insets = new Insets(0, 0, 5, 5);
		gbc_textfield_endOfCompetition.anchor = GridBagConstraints.NORTH;
		gbc_textfield_endOfCompetition.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_endOfCompetition.gridx = 1;
		gbc_textfield_endOfCompetition.gridy = 3;
		add(textfield_endOfCompetition, gbc_textfield_endOfCompetition);
		textfield_endOfCompetition.setColumns(10);
		
		label_ageClassesDefinition = new JLabel(Messages.getString(Messages.AGECLASSESDEFINITIONS));
		GridBagConstraints gbc_label_ageClassesDefinition = new GridBagConstraints();
		gbc_label_ageClassesDefinition.anchor = GridBagConstraints.EAST;
		gbc_label_ageClassesDefinition.insets = new Insets(0, 0, 5, 5);
		gbc_label_ageClassesDefinition.gridx = 0;
		gbc_label_ageClassesDefinition.gridy = 4;
		add(label_ageClassesDefinition, gbc_label_ageClassesDefinition);
		
		combobox_ageClassesDefinition = new JComboBox();
		GridBagConstraints gbc_combobox_ageClassesDefinition = new GridBagConstraints();
		gbc_combobox_ageClassesDefinition.insets = new Insets(0, 0, 5, 5);
		gbc_combobox_ageClassesDefinition.fill = GridBagConstraints.HORIZONTAL;
		gbc_combobox_ageClassesDefinition.gridx = 1;
		gbc_combobox_ageClassesDefinition.gridy = 4;
		add(combobox_ageClassesDefinition, gbc_combobox_ageClassesDefinition);
		
		label_webPage = new JLabel(Messages.getString(Messages.COMPETITION_WEB_PAGE));
		GridBagConstraints gbc_label_webPage = new GridBagConstraints();
		gbc_label_webPage.anchor = GridBagConstraints.EAST;
		gbc_label_webPage.insets = new Insets(0, 0, 5, 5);
		gbc_label_webPage.gridx = 0;
		gbc_label_webPage.gridy = 5;
		add(label_webPage, gbc_label_webPage);
		
		textfield_webpage = new JTextField();
		GridBagConstraints gbc_textfield_webpage = new GridBagConstraints();
		gbc_textfield_webpage.insets = new Insets(0, 0, 5, 5);
		gbc_textfield_webpage.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_webpage.gridx = 1;
		gbc_textfield_webpage.gridy = 5;
		add(textfield_webpage, gbc_textfield_webpage);
		textfield_webpage.setColumns(10);
		
		label_openWebPage = new JLabel(IconDictionary.getImageIcon(IconDictionary.INTERNET));
		label_openWebPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				openWebPage(textfield_webpage.getText());
			}
		});
		GridBagConstraints gbc_label_openWebPage = new GridBagConstraints();
		gbc_label_openWebPage.insets = new Insets(0, 0, 5, 0);
		gbc_label_openWebPage.gridx = 2;
		gbc_label_openWebPage.gridy = 5;
		add(label_openWebPage, gbc_label_openWebPage);
		
		label_registrationPage = new JLabel(Messages.getString(Messages.COMPETITION_REGISTRATION_PAGE));
		GridBagConstraints gbc_label_registrationPage = new GridBagConstraints();
		gbc_label_registrationPage.anchor = GridBagConstraints.EAST;
		gbc_label_registrationPage.insets = new Insets(0, 0, 5, 5);
		gbc_label_registrationPage.gridx = 0;
		gbc_label_registrationPage.gridy = 6;
		add(label_registrationPage, gbc_label_registrationPage);
		
		textfield_registrationPage = new JTextField();
		GridBagConstraints gbc_textfield_registrationPage = new GridBagConstraints();
		gbc_textfield_registrationPage.insets = new Insets(0, 0, 5, 5);
		gbc_textfield_registrationPage.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_registrationPage.gridx = 1;
		gbc_textfield_registrationPage.gridy = 6;
		add(textfield_registrationPage, gbc_textfield_registrationPage);
		textfield_registrationPage.setColumns(10);
		
		label_openRegistrationPage = new JLabel(IconDictionary.getImageIcon(IconDictionary.INTERNET));
		label_openRegistrationPage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				openWebPage(textfield_registrationPage.getText());
			}
		});
		GridBagConstraints gbc_label_openRegistrationPage = new GridBagConstraints();
		gbc_label_openRegistrationPage.insets = new Insets(0, 0, 5, 0);
		gbc_label_openRegistrationPage.gridx = 2;
		gbc_label_openRegistrationPage.gridy = 6;
		add(label_openRegistrationPage, gbc_label_openRegistrationPage);
		
		label_registrationsLogFile = new JLabel(Messages.getString(Messages.COMPETITION_REGISTRATIONS_LOG_FILE));
		GridBagConstraints gbc_label_registrationsLogFile = new GridBagConstraints();
		gbc_label_registrationsLogFile.anchor = GridBagConstraints.EAST;
		gbc_label_registrationsLogFile.insets = new Insets(0, 0, 0, 5);
		gbc_label_registrationsLogFile.gridx = 0;
		gbc_label_registrationsLogFile.gridy = 7;
		add(label_registrationsLogFile, gbc_label_registrationsLogFile);
		
		textfield_registrationsLogFile = new JTextField();
		GridBagConstraints gbc_textfield_registrationsLogFile = new GridBagConstraints();
		gbc_textfield_registrationsLogFile.insets = new Insets(0, 0, 0, 5);
		gbc_textfield_registrationsLogFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_registrationsLogFile.gridx = 1;
		gbc_textfield_registrationsLogFile.gridy = 7;
		add(textfield_registrationsLogFile, gbc_textfield_registrationsLogFile);
		textfield_registrationsLogFile.setColumns(10);
	}

	@Override
	protected void switchFormToEditMode() {
		textfield_title.setEditable(true);
		combobox_ageClassesDefinition.setEnabled(true);
		textfield_startOfCompetition.setEditable(true);
		textfield_endOfCompetition.setEditable(true);
	}

	@Override
	protected void switchFormToAdministrationMode() {
		switchFormToEditMode();
	}

	@Override
	protected void switchFormToViewMode() {
		textfield_title.setEditable(false);
		combobox_ageClassesDefinition.setEnabled(false);
		textfield_startOfCompetition.setEditable(false);
		textfield_endOfCompetition.setEditable(false);
	}

	@Override
	public void setModel(Model _model) {
		if (_model != null && _model instanceof Competition) {
			model = (Competition) _model;
			textfield_ID.setText(Integer.toString(model.getId()));
			textfield_title.setText(model.getTitle());
			ComboBoxModel cbmodel = combobox_ageClassesDefinition.getModel();
			if (cbmodel != null && cbmodel instanceof ModelListModel) {
				((ModelListModel) cbmodel).setSelectedModelByID(model.getAgeClassesDefinitionId());
			}
			textfield_startOfCompetition.setText(Messages.getFormattedDate(model.getStartOfCompetition()));
			textfield_endOfCompetition.setText(Messages.getFormattedDate(model.getEndOfCompetition()));
			if (model.getWebPage() != null) {
				textfield_webpage.setText(model.getWebPage().toString());
			} else {
				textfield_webpage.setText(dummy);
			}
			if (model.getRegistrationPage() != null) {
				textfield_registrationPage.setText(model.getRegistrationPage().toString());
			} else {
				textfield_registrationPage.setText(dummy);
			}
			if (model.getRegistrationsLogFile() != null) {
				textfield_registrationsLogFile.setText(model.getRegistrationsLogFile().toString());
			} else {
				textfield_registrationsLogFile.setText(dummy);
			}
		} else {
			model = null;
			textfield_ID.setText(dummy);
			textfield_title.setText(dummy);
			combobox_ageClassesDefinition.setSelectedItem(null);
			textfield_startOfCompetition.setText(dummy);
			textfield_endOfCompetition.setText(dummy);
			textfield_webpage.setText(dummy);
			textfield_registrationPage.setText(dummy);
			textfield_registrationsLogFile.setText(dummy);
		}
	}

	@Override
	public Competition getModel() {
		if (model == null) {
			model = new Competition();
		}
		model.setTitle(textfield_title.getText());
		int id = 0;
		Object o = combobox_ageClassesDefinition.getSelectedItem();
		if (o != null && o instanceof AgeClassesDefinition) {
			id = ((AgeClassesDefinition) o).getId();
		}
		model.setAgeClassesDefinitionId(id);
		model.setStartOfCompetition(Messages.getFormattedDate(textfield_startOfCompetition.getText()));
		model.setEndOfCompetition(Messages.getFormattedDate(textfield_endOfCompetition.getText()));
		try {
			String urlText = textfield_webpage.getText();
			if (urlText != null && urlText.length() != 0) {
				model.setWebPage(new URL(urlText));
			} else {
				model.setWebPage("");
			}
			urlText = textfield_registrationPage.getText();
			if (urlText != null && urlText.length() != 0) {
				model.setRegistrationPage(new URL(urlText));
			} else {
				model.setRegistrationPage("");
			}
			urlText = textfield_registrationsLogFile.getText();
			if (urlText != null && urlText.length() != 0) {
				model.setRegistrationsLogFile(new URL(urlText));
			} else {
				model.setRegistrationsLogFile("");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return model;
	}

	@Override
	public void bindData() {
		try {
			if (controller != null) {
				combobox_ageClassesDefinition.setModel(controller.getAllAgeClassesDefinitions());
			}
		} catch (Exception e) {
			mainFrame.showUserMessage(e, model);
		}
	}
	
	public void setSelectedAgeClassesDefinition(AgeClassesDefinition ageClassesDefinition) {
		ModelView_helper.setSelectedModelInComboBox(combobox_ageClassesDefinition, ageClassesDefinition);
	}
	
	private void openWebPage(String url) {
		if (url != null && url.length() != 0) {
			try {
				controller.openWebPage(new URL(url));
			} catch (Exception e) {
				mainFrame.showUserMessage(e, model);
			}
		}
	}

	@Override
	public JComponent getFirstFocusableEditorComponent() {
		return textfield_title;
	}

}
