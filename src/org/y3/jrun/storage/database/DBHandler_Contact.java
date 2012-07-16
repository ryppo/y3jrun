package org.y3.jrun.storage.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.control.Utils;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.contact.Contact;

public class DBHandler_Contact extends DBModelHandler {

	@Override
	public String getSqlToCreateModelTable() {
		return KeywordsDictionary.SQL_CREATE_TABLE
				+ KeywordsDictionary.DATABASE_SCHEME
				+ "." + KeywordsDictionary.CONTACT + " ("
				+ KeywordsDictionary.SQL_ID_INTEGER_PRIMARY_KEY_NOT_NULL + ","
				+ KeywordsDictionary.CONTACT_LASTNAME + KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.CONTACT_FIRSTNAME + KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.CONTACT_CALLNAME + KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.CONTACT_PHONENUMBER + KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.CONTACT_MOBILENUMBER + KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.CONTACT_BIRTHYEAR + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.CONTACT_EMAIL + KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.CONTACT_ADDRESS1 + KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.CONTACT_ADDRESS2 + KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.CONTACT_CITY + KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.CONTACT_POSTAL + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.CONTACT_GENDER + KeywordsDictionary.SQL_VARCHAR_6 + ","
				+ KeywordsDictionary.CONTACT_COMMENTS + KeywordsDictionary.SQL_VARCHAR_500 + ","
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES_DEFINITION
				+ ")";
	}
	
	@Override
	public String getSqlToDropModelTable() {
		return KeywordsDictionary.SQL_DROP_TABLE 
				+ KeywordsDictionary.DATABASE_SCHEME 
				+ "." + KeywordsDictionary.CONTACT;
	}

	@Override
	public String getSqlToInsertModel(Model model) {
		if (model == null || !(model instanceof Contact)) {
			return null;
		}
		Contact contact = (Contact) model;
		return KeywordsDictionary.SQL_INSERT_INTO
				+ KeywordsDictionary.DATABASE_SCHEME
				+ "." + KeywordsDictionary.CONTACT + " ("
				+ KeywordsDictionary.CONTACT_LASTNAME + ", "
				+ KeywordsDictionary.CONTACT_FIRSTNAME + ", "
				+ KeywordsDictionary.CONTACT_CALLNAME + ", "
				+ KeywordsDictionary.CONTACT_PHONENUMBER + ", "
				+ KeywordsDictionary.CONTACT_MOBILENUMBER + ", "
				+ KeywordsDictionary.CONTACT_BIRTHYEAR + ", "
				+ KeywordsDictionary.CONTACT_EMAIL + ", "
				+ KeywordsDictionary.CONTACT_ADDRESS1 + ", "
				+ KeywordsDictionary.CONTACT_ADDRESS2 + ", "
				+ KeywordsDictionary.CONTACT_CITY + ", "
				+ KeywordsDictionary.CONTACT_POSTAL + ", "
				+ KeywordsDictionary.CONTACT_GENDER + ", "
				+ KeywordsDictionary.CONTACT_COMMENTS + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_VALUES + "'" 
				+ contact.getLastname() + "', '"
				+ contact.getFirstname() + "', '" 
				+ contact.getName() + "', '"
				+ contact.getPhonenumber() + "', '"
				+ contact.getMobilenumber() + "',"
				+ contact.getBirthyear() + ", '"
				+ contact.getEmail() + "', '"
				+ contact.getAddress1() + "', '"
				+ contact.getAddress2() + "', '"
				+ contact.getCity() + "',"
				+ contact.getPostal() + ", '"
				+ contact.getGender() + "', '"
				+ contact.getComments() + "', '"
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTE_VALUES(model)
				+ ")";
	}

	@Override
	public String getSqlToUpdateModel(Model model) {
		if (model == null || !(model instanceof Contact)) {
			return null;
		}
		Contact contact = (Contact) model;
		return KeywordsDictionary.SQL_UPDATE + KeywordsDictionary.DATABASE_SCHEME + "." 
				+ KeywordsDictionary.CONTACT + KeywordsDictionary.SQL_SET 
				+ KeywordsDictionary.CONTACT_LASTNAME 			+ "='" + contact.getLastname() + "', "
				+ KeywordsDictionary.CONTACT_FIRSTNAME 			+ "='" + contact.getFirstname()+ "', "
				+ KeywordsDictionary.CONTACT_CALLNAME			+ "='" + contact.getName() + "', "
				+ KeywordsDictionary.CONTACT_PHONENUMBER		+ "='" + contact.getPhonenumber()	+ "', "
				+ KeywordsDictionary.CONTACT_MOBILENUMBER		+ "='" + contact.getMobilenumber() + "', "
				+ KeywordsDictionary.CONTACT_BIRTHYEAR			+ "=" + contact.getBirthyear() + ", "
				+ KeywordsDictionary.CONTACT_EMAIL				+ "='" + contact.getEmail() + "', "
				+ KeywordsDictionary.CONTACT_ADDRESS1			+ "='" + contact.getAddress1() + "', "
				+ KeywordsDictionary.CONTACT_ADDRESS2			+ "='" + contact.getAddress2() + "', "
				+ KeywordsDictionary.CONTACT_CITY				+ "='" + contact.getCity() + "', "
				+ KeywordsDictionary.CONTACT_POSTAL				+ "=" + contact.getPostal() + ", "
				+ KeywordsDictionary.CONTACT_GENDER				+ "='" + contact.getGender() + "', "
				+ KeywordsDictionary.CONTACT_COMMENTS			+ "='" + contact.getComments() + "', "
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTES_FILLED(model)
				+ KeywordsDictionary.SQL_WHERE_ID_IS
				+ contact.getId();
	}

	@Override
	public String getSqlToLoadAllModels() {
		return KeywordsDictionary.SQL_SELECT
				+ KeywordsDictionary.MODEL_ID + ", "
				+ KeywordsDictionary.CONTACT_LASTNAME + ", "
				+ KeywordsDictionary.CONTACT_FIRSTNAME + ", "
				+ KeywordsDictionary.CONTACT_CALLNAME + ", "
				+ KeywordsDictionary.CONTACT_PHONENUMBER + ", "
				+ KeywordsDictionary.CONTACT_MOBILENUMBER + ", "
				+ KeywordsDictionary.CONTACT_BIRTHYEAR + ", "
				+ KeywordsDictionary.CONTACT_EMAIL + ", "
				+ KeywordsDictionary.CONTACT_ADDRESS1 + ", "
				+ KeywordsDictionary.CONTACT_ADDRESS2 + ", "
				+ KeywordsDictionary.CONTACT_CITY + ", "
				+ KeywordsDictionary.CONTACT_POSTAL + ", "
				+ KeywordsDictionary.CONTACT_GENDER + ", "
				+ KeywordsDictionary.CONTACT_COMMENTS + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_FROM
				+ KeywordsDictionary.DATABASE_SCHEME + "."
				+ KeywordsDictionary.CONTACT;
	}
	
	@Override
	public Contact[] mapResultSetToModels(ResultSet resultSet) throws SQLException {
		Contact[] contacts = null;
		if (resultSet != null) {
			//create array
			resultSet.last();
			contacts = new Contact[resultSet.getRow()];
			resultSet.beforeFirst();
			//loop resultSet
			int contactsCount = 0;
			while (resultSet.next()) {
				Contact c = new Contact();
				c.setId(resultSet.getInt(1));
				c.setLastname(resultSet.getString(2));
				c.setFirstname(resultSet.getString(3));
				c.setName(resultSet.getString(4));
				c.setPhonenumber(resultSet.getString(5));
				c.setMobilenumber(resultSet.getString(6));
				String birthyear = resultSet.getString(7);
				if (birthyear != null && birthyear.length() != 0) {
					c.setBirthyear(Integer.parseInt(birthyear));
				}
				c.setEmail(resultSet.getString(8));
				c.setAddress1(resultSet.getString(9));
				c.setAddress2(resultSet.getString(10));
				c.setCity(resultSet.getString(11));
				String postal = resultSet.getString(12);
				if (postal != null && postal.length() != 0) {
					c.setPostal(Integer.parseInt(postal));
				}
				String gender = resultSet.getString(13);
				if (gender.equals(Contact.gendertype.FEMALE.toString())) {
					c.setGender(Contact.gendertype.FEMALE);
				} else {
					c.setGender(Contact.gendertype.MALE);
				}
				c.setComments(resultSet.getString(14));
				KeywordsDictionary.setMODEL_META_ATTRIBUTES(c, resultSet, 15);
				contacts[contactsCount] = c;
				contactsCount++;
			}
		}
		return contacts;
	}

	@Override
	public String getSqlForFullTextSearch(String searchString) {
		String searchIntegerString = Utils.parseIntegerFromStringAsString(searchString);
		String whereClause =
				KeywordsDictionary.SQL_UPPER(KeywordsDictionary.CONTACT_ADDRESS1) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.CONTACT_ADDRESS2) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.CONTACT_CALLNAME) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.CONTACT_BIRTHYEAR + "=" + searchIntegerString + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.CONTACT_CITY) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.CONTACT_COMMENTS) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.CONTACT_EMAIL) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.CONTACT_FIRSTNAME) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.CONTACT_GENDER) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.CONTACT_LASTNAME) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.CONTACT_MOBILENUMBER) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.CONTACT_PHONENUMBER) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.CONTACT_POSTAL + "=" + searchIntegerString;
		return getSqlToLoadModels(whereClause);
	}

}
