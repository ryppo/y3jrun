/**
 * 
 */
package org.y3.jrun.model;

import java.util.Date;

/**
 * @author Ryppo
 *
 */
public abstract class Model {
	
	private int id;
	private String creatorName = System.getProperty("user.name");
	private Date creationDate = new Date();
	private String changerName;
	private Date changeDate;
	private String importFingerPrint;
	private int version;
	
	/**
	 * Get string to be shown in list
	 * @return list string
	 */
	public abstract String toString();

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the creatorName
	 */
	public String getCreatorName() {
		if (creatorName == null) {
			return "";
		}
		return creatorName;
	}

	/**
	 * @param creatorName the creatorName to set
	 */
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the changerName
	 */
	public String getChangerName() {
		if (changerName == null) {
			return "";
		}
		return changerName;
	}

	/**
	 * @param changerName the changerName to set
	 */
	public void setChangerName(String changerName) {
		this.changerName = changerName;
	}

	/**
	 * @return the changeDate
	 */
	public Date getChangeDate() {
		return changeDate;
	}

	/**
	 * @param changeDate the changeDate to set
	 */
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Model && ((Model) o).getId() == getId()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return the importFingerPrint
	 */
	public String getImportFingerPrint() {
		return importFingerPrint;
	}

	/**
	 * @param importFingerPrint the importFingerPrint to set
	 */
	public void setImportFingerPrint(String importFingerPrint) {
		this.importFingerPrint = importFingerPrint;
	}
	
	public abstract void generateImportFingerPrint();
	
	public String getNotificationTitle() {
		return toString();
	}
	
	public String getNotificationMessage() {
		return "";
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getId();
		return result;
	}

}
