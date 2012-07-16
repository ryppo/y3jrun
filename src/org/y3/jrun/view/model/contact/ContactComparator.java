/**
 * 
 */
package org.y3.jrun.view.model.contact;

import java.util.Comparator;

import org.y3.jrun.model.contact.Contact;

/**
 * @author Ryppo
 *
 */
public class ContactComparator implements Comparator<Contact> {
	
	public compareBy COMPARE_MODE = compareBy.FIRSTNAME_LASTNAME_ASC;
	
	public enum compareBy { FIRSTNAME_LASTNAME_ASC, FIRSTNAME_LASTNAME_DESC, 
							LASTNAME_FIRSTNAME_ASC, LASTNAME_FIRSTNAME_DESC, 
							CALLNAME_ASC, CALLNAME_DESC, 
							ID_ASC, ID_DESC,
							BIRTHYEAR_ASC, BIRTHYEAR_DESC, 
							GENDER_ASC, GENDER_DESC }

	@Override
	public int compare(Contact contact1, Contact contact2) {
		//generic comparism upon null object(s)
		if (contact1 == null && contact2 == null) {
			return 0;
		} else if (contact1 == null) {
			return 1;
		} else if (contact2 == null) {
			return -1;
		}
		//special mode based comparism
		String c1 = null;
		String c2 = null;
		int i1 = 0;
		int i2= 0;
		switch ( COMPARE_MODE ) {
		case FIRSTNAME_LASTNAME_ASC:
			c1 = contact1.getFirstname() + contact1.getLastname();
			c2 = contact2.getFirstname() + contact2.getLastname();
			return c1.compareTo(c2);
		case FIRSTNAME_LASTNAME_DESC:
			c1 = contact1.getFirstname() + contact1.getLastname();
			c2 = contact2.getFirstname() + contact2.getLastname();
			return c2.compareTo(c1);
		case LASTNAME_FIRSTNAME_ASC:
			c1 = contact1.getLastname() + contact1.getFirstname();
			c2 = contact2.getLastname() + contact2.getFirstname();
			return c1.compareTo(c2);
		case LASTNAME_FIRSTNAME_DESC:
			c1 = contact1.getLastname() + contact1.getFirstname();
			c2 = contact2.getLastname() + contact2.getFirstname();
			return c2.compareTo(c1);
		case CALLNAME_ASC:
			c1 = contact1.getName();
			c2 = contact2.getName();
			return c1.compareTo(c2);
		case CALLNAME_DESC:
			c1 = contact1.getName();
			c2 = contact2.getName();
			return c2.compareTo(c1);
		case ID_ASC:
			i1 = contact1.getId();
			i2 = contact2.getId();
			return compareInt(i1, i2);
		case ID_DESC:
			i1 = contact1.getId();
			i2 = contact2.getId();
			return compareInt(i2, i1);
		case BIRTHYEAR_ASC:
			i1 = contact1.getBirthyear();
			i2 = contact2.getBirthyear();
			return compareInt(i1, i2);
		case BIRTHYEAR_DESC:
			i1 = contact1.getBirthyear();
			i2 = contact2.getBirthyear();
			return compareInt(i2, i1);
		case GENDER_ASC:
			c1 = contact1.getGender().name();
			c2 = contact2.getGender().name();
			return c1.compareTo(c2);
		case GENDER_DESC:
			c1 = contact1.getGender().name();
			c2 = contact2.getGender().name();
			return c2.compareTo(c1);
		default:
			return 0;
		}
	}
	
	private int compareInt(int i1, int i2) {
		if (i1 < i2) {
			return -1;
		}
		if (i1 > i2) {
			return 1;
		}
		return 0;
	}

}
