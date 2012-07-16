package org.y3.jrun.view.model.contact;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.contact.ContactListModel;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.ModelPanel;

public class ContactsPanel extends ModelPanel {

	private static final long serialVersionUID = 1L;
	private ContactComparator contactComparator;

	private String sortedBy = Messages.getString(Messages.SORTED_BY) + " ";

	/**
	 * Create the panel.
	 */
	public ContactsPanel(ApplicationController _controller,
			ApplicationFrame _appFrame) {
		super(_controller, _appFrame);
	}

	@Override
	public void bindData() throws IOException, ClassNotFoundException,
			SQLException {
		UIHelper.startWaiting(appFrame);
		ContactListModel model = controller.getAllContacts();
		Contact contact = (Contact) modelForm.getModel();
		jp_modelList.setModel(model);
		if (model == null || model.getSize() == 0) {
			modelForm.setModel(null);
		} else {
			jp_modelList.setSelectedValue(contact, true);
		}
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	public void bindDataForModelById(int contactId) throws IOException,
			ClassNotFoundException, SQLException {
		UIHelper.startWaiting(appFrame);
		ContactListModel model = controller.getContactById(contactId);
		jp_modelList.setModel(model);
		if (model == null || model.getSize() == 0) {
			modelForm.setModel(null);
		} else {
			jp_modelList.setSelectedValue(model.getElementAt(0), true);
		}
		UIHelper.stopWaiting(appFrame);
	}

	private void actionExportContacts() {
		UIHelper.startWaiting(appFrame);
		try {
			boolean exported = false;
			ContactListModel listModel = (ContactListModel) jp_modelList
					.getModel();
			if (listModel != null) {
				exported = controller.exportContacts(listModel.getModel());
			}
			if (exported) {
				appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE,
						Messages.getString(Messages.CONTACTS_SUCCESSFULLY_EXPORTED), null);
			} else {
				appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE,
						Messages.getString(Messages.CONTACTS_NOT_EXPORTED), null);
			}
		} catch (IOException e) {
			appFrame.showUserMessage(e, null);
		}
		UIHelper.stopWaiting(appFrame);
	}

	private void actionImportContacts() {
		UIHelper.startWaiting(appFrame);
		try {
			if (UIHelper.showSureRequest(Messages.getString(Messages.IMPORT), appFrame)) {
			Contact[] newContacts = controller.importContacts();
				if (newContacts != null) {
					for (Contact contact : newContacts) {
						controller.saveModel(contact);
					}
					// update UI
					bindData();
					appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE,
							Messages.getString(Messages.CONTACTS_SUCCESSFULLY_IMPORTED), null);
				}
			}
		} catch (IOException e) {
			appFrame.showUserMessage(e, null);
		} catch (ClassNotFoundException e) {
			appFrame.showUserMessage(e, null);
		} catch (SQLException e) {
			appFrame.showUserMessage(e, null);
		}
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	protected void actionSortModels() {
		UIHelper.startWaiting(appFrame);
		// use next sorting mode
		if (contactComparator == null) {
			contactComparator = new ContactComparator();
		} else {
			if (contactComparator.COMPARE_MODE
					.equals(ContactComparator.compareBy.FIRSTNAME_LASTNAME_ASC)) {
				contactComparator.COMPARE_MODE = ContactComparator.compareBy.FIRSTNAME_LASTNAME_DESC;
			} else if (contactComparator.COMPARE_MODE
					.equals(ContactComparator.compareBy.FIRSTNAME_LASTNAME_DESC)) {
				contactComparator.COMPARE_MODE = ContactComparator.compareBy.LASTNAME_FIRSTNAME_ASC;
			} else if (contactComparator.COMPARE_MODE
					.equals(ContactComparator.compareBy.LASTNAME_FIRSTNAME_ASC)) {
				contactComparator.COMPARE_MODE = ContactComparator.compareBy.LASTNAME_FIRSTNAME_DESC;
			} else if (contactComparator.COMPARE_MODE
					.equals(ContactComparator.compareBy.LASTNAME_FIRSTNAME_DESC)) {
				contactComparator.COMPARE_MODE = ContactComparator.compareBy.CALLNAME_ASC;
			} else if (contactComparator.COMPARE_MODE
					.equals(ContactComparator.compareBy.CALLNAME_ASC)) {
				contactComparator.COMPARE_MODE = ContactComparator.compareBy.CALLNAME_DESC;
			} else if (contactComparator.COMPARE_MODE
					.equals(ContactComparator.compareBy.CALLNAME_DESC)) {
				contactComparator.COMPARE_MODE = ContactComparator.compareBy.ID_ASC;
			} else if (contactComparator.COMPARE_MODE
					.equals(ContactComparator.compareBy.ID_ASC)) {
				contactComparator.COMPARE_MODE = ContactComparator.compareBy.ID_DESC;
			} else if (contactComparator.COMPARE_MODE
					.equals(ContactComparator.compareBy.ID_DESC)) {
				contactComparator.COMPARE_MODE = ContactComparator.compareBy.BIRTHYEAR_ASC;
			} else if (contactComparator.COMPARE_MODE
					.equals(ContactComparator.compareBy.BIRTHYEAR_ASC)) {
				contactComparator.COMPARE_MODE = ContactComparator.compareBy.BIRTHYEAR_DESC;
			} else if (contactComparator.COMPARE_MODE
					.equals(ContactComparator.compareBy.BIRTHYEAR_DESC)) {
				contactComparator.COMPARE_MODE = ContactComparator.compareBy.GENDER_ASC;
			} else if (contactComparator.COMPARE_MODE
					.equals(ContactComparator.compareBy.GENDER_ASC)) {
				contactComparator.COMPARE_MODE = ContactComparator.compareBy.GENDER_DESC;
			} else if (contactComparator.COMPARE_MODE
					.equals(ContactComparator.compareBy.GENDER_DESC)) {
				contactComparator.COMPARE_MODE = ContactComparator.compareBy.FIRSTNAME_LASTNAME_ASC;
			}
		}
		label_sortedBy.setText(sortedBy
				+ Messages.getString(contactComparator.COMPARE_MODE.toString()));
		// sort the contacts
		((ContactListModel) jp_modelList.getModel()).sort(contactComparator);
		// update UI
		jp_modelList.repaint();
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	protected void createModelForm() {
		UIHelper.startWaiting(appFrame);
		modelForm = new ContactForm(ContactForm.MODE.VIEWER);
		UIHelper.stopWaiting(appFrame);
	}
	
	protected void showParticipationsForSelectedContact() {
		UIHelper.startWaiting(appFrame);
		Model selectedContact = jp_modelList.getSelectedModel();
		int contactId = 0;
		if (selectedContact != null) {
			contactId = selectedContact.getId();
		}
		appFrame.showParticipationsByContactId(contactId);
		UIHelper.stopWaiting(appFrame);
	}

	@Override
	public String getPanelTitle() {
		return Messages.getString(Messages.CONTACTS);
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
	
	public ActionListener getActionListenerForActionSortModels() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionSortModels();
			}
		};
	}
	
	public ActionListener getActionListenerForActionExportModels() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionExportContacts();
			}
		};
	}
	
	public ActionListener getActionListenerForActionImportModels() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actionImportContacts();
			}
		};
	}
	
	public ActionListener getActionListenerForActionShowParticipations() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showParticipationsForSelectedContact();
			}
		};
	}

	@Override
	protected void addFunctionsToMenuAndButtonPanel() {
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.SORT), Messages.getString(Messages.SORT), getActionListenerForActionSortModels());
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.EXPORT), Messages.getString(Messages.EXPORT), getActionListenerForActionExportModels());
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.IMPORT), Messages.getString(Messages.IMPORT), getActionListenerForActionImportModels());
		addSeparatorToMenuAndButtonPanel();
		addFunctionToMenuAndButtonPanelForActionNewModel();
		addFunctionToMenuAndButtonPanelForActionRemoveModel();
		addFunctionToMenuAndButtonPanelForActionSaveModel();
		addSeparatorToMenuAndButtonPanel();
		addFunctionToMenuAndButtonPanel(
				IconDictionary.getImageIcon(IconDictionary.PARTICIPATIONS), Messages.getString(Messages.SHOW_PARTICIPATIONS), getActionListenerForActionShowParticipations());
	}

	@Override
	public JPanel getSpecificStatisticsPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Icon getIcon() {
		return IconDictionary.getImageIcon(IconDictionary.CONTACT);
	}

}
