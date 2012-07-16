package org.y3.jrun.storage.file;

import net.sf.json.JSONObject;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.contact.Contact;

public class ContactExchanger extends ModelExchanger {
	
	@Override
	public JSONObject modelToJSON(Model model) {
		JSONObject json = null;
		if (model != null && model instanceof Contact) {
			Contact contact = (Contact) model;
			json = new JSONObject();
			json.put(KeywordsDictionary.MODEL_ID, contact.getId());
			json.put(KeywordsDictionary.CONTACT_FIRSTNAME, contact.getFirstname());
			json.put(KeywordsDictionary.CONTACT_LASTNAME, contact.getLastname());
			json.put(KeywordsDictionary.CONTACT_CALLNAME, contact.getName());
			json.put(KeywordsDictionary.CONTACT_ADDRESS1, contact.getAddress1());
			json.put(KeywordsDictionary.CONTACT_ADDRESS2, contact.getAddress2());
			json.put(KeywordsDictionary.CONTACT_POSTAL, contact.getPostal());
			json.put(KeywordsDictionary.CONTACT_CITY, contact.getCity());
			json.put(KeywordsDictionary.CONTACT_BIRTHYEAR, contact.getBirthyear());
			json.put(KeywordsDictionary.CONTACT_COMMENTS, contact.getComments());
			json.put(KeywordsDictionary.CONTACT_EMAIL, contact.getEmail());
			json.put(KeywordsDictionary.CONTACT_GENDER, contact.getGender());
			json.put(KeywordsDictionary.CONTACT_MOBILENUMBER, contact.getMobilenumber());
			json.put(KeywordsDictionary.CONTACT_PHONENUMBER, contact.getPhonenumber());
			return json;
		}
		return null;
	}

	@Override
	public Model JSONToModel(JSONObject json) {
		Contact contact = new Contact();
		contact.setFirstname(json.getString(KeywordsDictionary.CONTACT_FIRSTNAME));
		contact.setLastname(json.getString(KeywordsDictionary.CONTACT_LASTNAME));
		contact.setName(json.getString(KeywordsDictionary.CONTACT_CALLNAME));
		contact.setAddress1(json.getString(KeywordsDictionary.CONTACT_ADDRESS1));
		contact.setAddress2(json.getString(KeywordsDictionary.CONTACT_ADDRESS2));
		contact.setPostal(json.getInt(KeywordsDictionary.CONTACT_POSTAL));
		contact.setCity(json.getString(KeywordsDictionary.CONTACT_CITY));
		contact.setBirthyear(json.getInt(KeywordsDictionary.CONTACT_BIRTHYEAR));
		contact.setComments(json.getString(KeywordsDictionary.CONTACT_COMMENTS));
		contact.setEmail(json.getString(KeywordsDictionary.CONTACT_EMAIL));
		String gender = json.getString(KeywordsDictionary.CONTACT_GENDER);
		if (gender.equals(Contact.gendertype.FEMALE.toString())) {
			contact.setGender(Contact.gendertype.FEMALE);
		} else if (gender.equals(Contact.gendertype.MALE.toString())) {
			contact.setGender(Contact.gendertype.MALE);
		}
		contact.setMobilenumber(json.getString(KeywordsDictionary.CONTACT_MOBILENUMBER));
		contact.setPhonenumber(json.getString(KeywordsDictionary.CONTACT_PHONENUMBER));
		return contact;
	}

	@Override
	public String getModelClassName() {
		return KeywordsDictionary.CONTACT;
	}
	
}
