package org.y3.jrun.model.competition;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import org.y3.jrun.model.Model;

public class Competition extends Model {
	
	private String title;
	private Date startOfCompetition;
	private int ageClassesDefinitionId;
	private URL webPage;
	private URL registrationPage;
	private URL registrationsLogFile;
	
	/**
	 * @return the webPage
	 */
	public URL getWebPage() {
		return webPage;
	}
	
	public String getWebPageToString() {
		if (webPage != null) {
			return webPage.toString();
		} else {
			return "";
		}
	}

	/**
	 * @param webPage the webPage to set
	 */
	public void setWebPage(URL webPage) {
		this.webPage = webPage;
	}
	
	public void setWebPage(String webPage) {
		if (webPage != null && webPage.length() != 0) {
			try {
				this.webPage = new URL(webPage);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the registrationPage
	 */
	public URL getRegistrationPage() {
		return registrationPage;
	}
	
	public String getRegistrationPageToString() {
		if (registrationPage != null) {
			return registrationPage.toString();
		} else {
			return "";
		}
	}

	/**
	 * @param registrationPage the registrationPage to set
	 */
	public void setRegistrationPage(URL registrationPage) {
		this.registrationPage = registrationPage;
	}
	
	public void setRegistrationPageToNull() {
		this.registrationPage = null;
	}
	
	public void setRegistrationPage(String registrationPage) {
		if (registrationPage != null && registrationPage.length() != 0) {
			try {
				this.registrationPage = new URL(registrationPage);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {
			this.registrationPage = null;
		}
	}

	/**
	 * @return the registrationsLogFile
	 */
	public URL getRegistrationsLogFile() {
		return registrationsLogFile;
	}
	
	public String getRegistrationsLogFileToString() {
		if (registrationsLogFile != null) {
			return registrationsLogFile.toString();
		} else {
			return "";
		}
	}
	
	public void setRegistrationsLogFile(String registrationsLogFile) {
		if (registrationsLogFile != null && registrationsLogFile.length() != 0) {
			try {
				this.registrationsLogFile = new URL(registrationsLogFile);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {
			this.registrationsLogFile = null;
		}
	}
	
	public void setRegistrationsLogFileToNull() {
		this.registrationsLogFile = null;
	}

	/**
	 * @param registrationsLogFile the registrationsLogFile to set
	 */
	public void setRegistrationsLogFile(URL registrationsLogFile) {
		this.registrationsLogFile = registrationsLogFile;
	}

	public String getTitle() {
		if (title == null) {
			return "";
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartOfCompetition() {
		return startOfCompetition;
	}

	public void setStartOfCompetition(Date startOfCompetition) {
		this.startOfCompetition = startOfCompetition;
	}

	public Date getEndOfCompetition() {
		return endOfCompetition;
	}

	public void setEndOfCompetition(Date endOfCompetition) {
		this.endOfCompetition = endOfCompetition;
	}

	private Date endOfCompetition;

	@Override
	public String toString() {
		String toString = title;
		if (startOfCompetition != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(startOfCompetition.getTime());
			toString += " " +  cal.get(Calendar.YEAR);
		}
		return toString;
	}

	public int getAgeClassesDefinitionId() {
		return ageClassesDefinitionId;
	}

	public void setAgeClassesDefinitionId(int ageClassesDefinitionId) {
		this.ageClassesDefinitionId = ageClassesDefinitionId;
	}

	@Override
	public void generateImportFingerPrint() {
		setImportFingerPrint(toString());
	}

}
