/**
 * 
 */
package org.y3.jrun.model.contact;

import java.util.Arrays;

import org.y3.jrun.model.ModelListModel;
import org.y3.jrun.view.model.contact.ContactComparator;

/**
 * @author ryppo
 *
 */
public class ContactListModel extends ModelListModel {
	
	private static final long serialVersionUID = 1L;
	
	public ContactListModel(Contact[] model) {
		super(model);
	}

	@Override
	public Contact getElementAt(int arg0) {
		return (Contact) super.getElementAt(arg0);
	}
	
	public void sort(ContactComparator comparator) {
		Arrays.sort(getModel(), comparator);
	}
	
	public Contact[] getModel() {
		return (Contact[]) super.getModel();
	}

}
