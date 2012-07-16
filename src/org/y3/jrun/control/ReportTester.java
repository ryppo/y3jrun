package org.y3.jrun.control;

import net.sf.jasperreports.engine.JRDataSource;

import org.y3.jrun.model.ModelListModel;
import org.y3.jrun.model.ModelReportable;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.contact.ContactListModel;

public class ReportTester {

	public static JRDataSource getTestableDataSource() {
		Contact c1 = new Contact();
		c1.setId(1);
		c1.setFirstname("Contact 1");
		c1.setLastname("Last1");
		c1.setGender(Contact.gendertype.MALE);
		Contact c2 = new Contact();
		c2.setId(2);
		c2.setFirstname("Contact 2");
		c2.setLastname("Last2");
		c2.setGender(Contact.gendertype.FEMALE);
		Contact[] contacts = new Contact[]{c1,c2};
		
		ModelListModel sources = new ContactListModel(contacts);
		return new ModelReportable(sources, "Kontakte");
	}
	
}
