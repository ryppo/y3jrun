package org.y3.jrun.storage.file;

import org.y3.jrun.model.Model;
import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.participation.Participation;

public class FileSession {

	private AgeClassExchanger AGECLASSEXCHANGER = new AgeClassExchanger();
	private AgeClassesDefinitionExchanger AGECLASSESDEFINITION_EXCHANGER = new AgeClassesDefinitionExchanger();
	private ContactExchanger CONTACT_EXCHANGER = new ContactExchanger();
	private ParticipationDataExchanger PARTICIPATION_DATA_EXCHANGER = new ParticipationDataExchanger();
	
	public ModelExchanger getModelExchangerForModel(Model model) {
		if (model instanceof AgeClassesDefinition) {
			return AGECLASSESDEFINITION_EXCHANGER;
		} else if (model instanceof Contact) {
			return CONTACT_EXCHANGER;
		} else if (model instanceof AgeClass) {
			return AGECLASSEXCHANGER;
		} else if (model instanceof Participation) {
			return PARTICIPATION_DATA_EXCHANGER;
		} else {
			return null;
		}
	}
	
}
