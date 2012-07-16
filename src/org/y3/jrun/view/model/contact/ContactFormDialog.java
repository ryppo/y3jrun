package org.y3.jrun.view.model.contact;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.model.ModelForm;
import org.y3.jrun.view.model.ModelFormDialog;

public class ContactFormDialog extends ModelFormDialog {

	private static final long serialVersionUID = 1L;
	
	public ContactFormDialog(Contact _contact, ApplicationFrame applicationFrame, ApplicationController applicationController) {
		super(_contact, applicationFrame, applicationController);
	}
	
	@Override
	public ModelForm getModelForm() {
		if (modelForm == null) {
			modelForm = new ContactForm(ModelForm.MODE.EDITOR);
		}
		return modelForm;
	}

	@Override
	public boolean actionSave() {
		Contact contact = (Contact) modelForm.getModel();
		try {
			controller.saveModel(contact);
			return true;
		} catch (Exception e) {
			appFrame.showUserMessage(e, contact);
		}
		return false;
	}

	@Override
	public Contact getModel() {
		if (modelForm != null) {
			return (Contact) modelForm.getModel();
		} else {
			return null;
		}
	}

}
