/**
 * 
 */
package org.y3.jrun.model.contact;

import org.y3.jrun.model.Model;


/**
 * @author Ryppo
 * 
 */
public class Contact extends Model {

	public static enum gendertype {
		FEMALE, MALE
	};

	private String name;
	private String firstname;
	private String lastname;
	private String address1;
	private String address2;
	private String city;
	private int postal;
	private String email;
	private String phonenumber;
	private String mobilenumber;
	private int birthyear;
	private gendertype gender;
	private String comments;

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		if (name == null) {
			name = "";
		}
		return name;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address
	 */
	public String getAddress1() {
		if (address1 == null) {
			address1 = "";
		}
		return address1;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		if (email == null) {
			email = "";
		}
		return email;
	}

	/**
	 * @param phonenumber
	 *            the phonenumber to set
	 */
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	/**
	 * @return the phonenumber
	 */
	public String getPhonenumber() {
		if (phonenumber == null) {
			phonenumber = "";
		}
		return phonenumber;
	}

	/**
	 * @param birthyear
	 *            the birthyear to set
	 */
	public void setBirthyear(int birthyear) {
		this.birthyear = birthyear;
	}

	/**
	 * @return the birthyear
	 */
	public int getBirthyear() {
		return birthyear;
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(gendertype gender) {
		this.gender = gender;
	}

	/**
	 * @return the gender
	 */
	public gendertype getGender() {
		if (gender == null) {
			return null;
		} else {
			return gender;
		}
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		if (comments == null) {
			comments = "";
		}
		return comments;
	}

	/**
	 * @param mobilenumber
	 *            the mobilenumber to set
	 */
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	/**
	 * @return the mobilenumber
	 */
	public String getMobilenumber() {
		if (mobilenumber == null) {
			mobilenumber = "";
		}
		return mobilenumber;
	}

	@Override
	public String toString() {
		String value = lastname + ", " + firstname;
		if (name != null && name.length() != 0) {
			value += " (" + name + ")";
		}
		return value;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		if (firstname == null) {
			firstname = "";
		}
		return firstname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		if (lastname == null) {
			lastname = "";
		}
		return lastname;
	}
	
	public String getFullName() {
		return firstname + " " + lastname;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		if (address2 == null) {
			address2 = "";
		}
		return address2;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		if (city == null) {
			city = "";
		}
		return city;
	}

	/**
	 * @param postal the postal to set
	 */
	public void setPostal(int postal) {
		this.postal = postal;
	}

	/**
	 * @return the postal
	 */
	public int getPostal() {
		return postal;
	}

	@Override
	public void generateImportFingerPrint() {
		setImportFingerPrint(firstname + lastname + birthyear);
	}

}
