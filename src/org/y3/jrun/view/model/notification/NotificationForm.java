package org.y3.jrun.view.model.notification;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.apache.commons.lang.StringUtils;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.notification.Notification;
import org.y3.jrun.model.notification.Notification.NotificationType;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelForm;

public class NotificationForm extends ModelForm {

	private static final long serialVersionUID = 1L;
	private String dummy = "";
	private JTextField textfield_ID;
	private JTextField textfield_title;
	private JLabel label_messageValue;
	private JTextArea textArea_exceptionStackTrace;
	private JTextField textfield_relatedObject;
	private JLabel label_type;
	private JComboBox comboBox_type;

	/**
	 * Create the panel.
	 */
	public NotificationForm(MODE mode) {
		super(mode);
	}

	@Override
	protected void initForm() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0 };
		setLayout(gridBagLayout);

		JLabel label_id = new JLabel("ID");
		label_id.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.anchor = GridBagConstraints.EAST;
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 0;
		add(label_id, gbc_lblId);

		textfield_ID = new JTextField();
		GridBagConstraints gbc_textfield_ID = new GridBagConstraints();
		gbc_textfield_ID.insets = new Insets(0, 0, 5, 0);
		gbc_textfield_ID.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_ID.gridx = 1;
		gbc_textfield_ID.gridy = 0;
		add(textfield_ID, gbc_textfield_ID);
		textfield_ID.setColumns(10);

		label_type = new JLabel(Messages.getString(Messages.MESSAGE_TYPE));
		GridBagConstraints gbc_label_type = new GridBagConstraints();
		gbc_label_type.anchor = GridBagConstraints.EAST;
		gbc_label_type.insets = new Insets(0, 0, 5, 5);
		gbc_label_type.gridx = 0;
		gbc_label_type.gridy = 1;
		add(label_type, gbc_label_type);

		comboBox_type = new JComboBox();
		comboBox_type.setModel(new DefaultComboBoxModel(new String[] { "",
				Messages.getString(Messages.NOTIFICATION_TYPE_DEBUG), 
				Messages.getString(Messages.NOTIFICATION_TYPE_FATAL),
				Messages.getString(Messages.NOTIFICATION_TYPE_ERROR), 
				Messages.getString(Messages.NOTIFICATION_TYPE_WARN),
				Messages.getString(Messages.NOTIFICATION_TYPE_INFO)}));
		GridBagConstraints gbc_comboBox_type = new GridBagConstraints();
		gbc_comboBox_type.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_type.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_type.gridx = 1;
		gbc_comboBox_type.gridy = 1;
		add(comboBox_type, gbc_comboBox_type);

		JLabel label_title = new JLabel(Messages.getString(Messages.NOTIFICATION_TITLE));
		label_title.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.anchor = GridBagConstraints.EAST;
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 2;
		add(label_title, gbc_lblTitle);

		textfield_title = new JTextField();
		GridBagConstraints gbc_textfield_title = new GridBagConstraints();
		gbc_textfield_title.insets = new Insets(0, 0, 5, 0);
		gbc_textfield_title.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_title.gridx = 1;
		gbc_textfield_title.gridy = 2;
		add(textfield_title, gbc_textfield_title);
		textfield_title.setColumns(10);

		JLabel label_message = new JLabel(Messages.getString(Messages.MESSAGE));
		label_message.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_label_message = new GridBagConstraints();
		gbc_label_message.insets = new Insets(0, 0, 5, 5);
		gbc_label_message.anchor = GridBagConstraints.EAST;
		gbc_label_message.gridx = 0;
		gbc_label_message.gridy = 3;
		add(label_message, gbc_label_message);

		label_messageValue = new JLabel();
		label_messageValue.setBorder(new BevelBorder(BevelBorder.LOWERED, null,
				null, null, null));
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 3;
		gbc_textArea.gridheight = 3;
		add(label_messageValue, gbc_textArea);

		JLabel label_exceptionStackTrace = new JLabel(Messages.getString(Messages.EXCEPTION_STACKTRACE));
		label_exceptionStackTrace.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_label_eST = new GridBagConstraints();
		gbc_label_eST.insets = new Insets(0, 0, 5, 5);
		gbc_label_eST.anchor = GridBagConstraints.EAST;
		gbc_label_eST.gridx = 0;
		gbc_label_eST.gridy = 6;
		add(label_exceptionStackTrace, gbc_label_eST);

		textArea_exceptionStackTrace = new JTextArea();
		textArea_exceptionStackTrace.setRows(3);
		textArea_exceptionStackTrace.setColumns(3);
		textArea_exceptionStackTrace.setBorder(new BevelBorder(
				BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_textAreaExceptionST = new GridBagConstraints();
		gbc_textAreaExceptionST.insets = new Insets(0, 0, 5, 0);
		gbc_textAreaExceptionST.fill = GridBagConstraints.BOTH;
		gbc_textAreaExceptionST.gridx = 1;
		gbc_textAreaExceptionST.gridy = 6;
		gbc_textAreaExceptionST.gridheight = 3;
		add(textArea_exceptionStackTrace, gbc_textAreaExceptionST);

		JLabel label_relatedObject = new JLabel(Messages.getString(Messages.RELATED_OBJECT));
		GridBagConstraints gbc_label_relatedObject = new GridBagConstraints();
		gbc_label_relatedObject.anchor = GridBagConstraints.NORTHEAST;
		gbc_label_relatedObject.insets = new Insets(0, 0, 0, 5);
		gbc_label_relatedObject.gridx = 0;
		gbc_label_relatedObject.gridy = 9;
		add(label_relatedObject, gbc_label_relatedObject);

		textfield_relatedObject = new JTextField();
		GridBagConstraints gbc_textfield_relatedObject = new GridBagConstraints();
		gbc_textfield_relatedObject.anchor = GridBagConstraints.NORTH;
		gbc_textfield_relatedObject.weighty = 1.0;
		gbc_textfield_relatedObject.fill = GridBagConstraints.HORIZONTAL;
		gbc_textfield_relatedObject.gridx = 1;
		gbc_textfield_relatedObject.gridy = 9;
		add(textfield_relatedObject, gbc_textfield_relatedObject);
		textfield_relatedObject.setColumns(10);
	}

	@Override
	protected void switchFormToEditMode() {
		comboBox_type.setEnabled(true);
		textArea_exceptionStackTrace.setEditable(true);
		textfield_title.setEditable(true);
		textfield_relatedObject.setEditable(true);
	}

	@Override
	protected void switchFormToAdministrationMode() {
		switchFormToEditMode();
	}

	@Override
	protected void switchFormToViewMode() {
		comboBox_type.setEnabled(false);
		textArea_exceptionStackTrace.setEditable(false);
		textfield_title.setEditable(false);
		textfield_relatedObject.setEditable(false);
	}

	@Override
	public void setModel(Model _model) {
		if (_model != null && _model instanceof Notification) {
			Notification notification = (Notification) _model;
			textfield_ID.setText(Integer.toString(notification.getId()));
			textfield_title.setText(StringUtils.defaultIfEmpty(
					notification.getTitle(), dummy));
			label_messageValue.setText(StringUtils.defaultIfEmpty(
					notification.getMessage(), dummy));
			textArea_exceptionStackTrace.setText(StringUtils.defaultIfEmpty(
					notification.getExceptionStackTrace(), dummy));
			textfield_relatedObject.setText("["
					+ Integer.toString(notification.getRelatedObjectId())
					+ "] "
					+ StringUtils.defaultIfEmpty(
							notification.getRelatedObjectType(), null));
			NotificationType type = notification.getNotificationType();
			if (type != null) {
				if (type.equals(NotificationType.debug)) {
					comboBox_type.setSelectedItem(Messages.getString(Messages.NOTIFICATION_TYPE_DEBUG));
				} else if (type.equals(NotificationType.error)) {
					comboBox_type.setSelectedItem(Messages.getString(Messages.NOTIFICATION_TYPE_ERROR));
				} else if (type.equals(NotificationType.fatal)) {
					comboBox_type.setSelectedItem(Messages.getString(Messages.NOTIFICATION_TYPE_FATAL));
				} else if (type.equals(NotificationType.info)) {
					comboBox_type.setSelectedItem(Messages.getString(Messages.NOTIFICATION_TYPE_INFO));
				} else if (type.equals(NotificationType.warn)) {
					comboBox_type.setSelectedItem(Messages.getString(Messages.NOTIFICATION_TYPE_WARN));
				}
			} else {
				comboBox_type.setSelectedItem("");
			}
		} else {
			textfield_ID.setText(dummy);
			textfield_title.setText(dummy);
			label_messageValue.setText(dummy);
			textArea_exceptionStackTrace.setText(dummy);
			textfield_relatedObject.setText(dummy);
		}
	}

	@Override
	public Model getModel() {
		return new Notification();
	}

	@Override
	public void bindData() {
	}

	@Override
	public JComponent getFirstFocusableEditorComponent() {
		return textfield_title;
	}
}
