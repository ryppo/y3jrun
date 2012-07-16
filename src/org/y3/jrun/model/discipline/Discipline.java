package org.y3.jrun.model.discipline;

import java.util.Date;

import org.y3.jrun.model.Model;

public class Discipline extends Model {
	
	private String name;
	private String length;
	private Date startOfDiscipline;
	private long priceInEuroCent;
	
	public static enum durationFormat {
		HH_mm_ss_SSS, HH_mm_ss, mm_ss
	}
	private durationFormat formatOfDuration = durationFormat.HH_mm_ss;

	/**
	 * @return the name
	 */
	public String getName() {
		if (name == null) {
			return "";
		}
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the length
	 */
	public String getLength() {
		if (length == null) {
			return "";
		}
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(String length) {
		this.length = length;
	}

	/**
	 * @return the startOfDiscipline
	 */
	public Date getStartOfDiscipline() {
		return startOfDiscipline;
	}

	/**
	 * @param startOfDiscipline the startOfDiscipline to set
	 */
	public void setStartOfDiscipline(Date startOfDiscipline) {
		this.startOfDiscipline = startOfDiscipline;
	}

	@Override
	public String toString() {
		String toString = "";
		if (length != null && length.length() != 0) {
			toString = length;
		}
		if (name != null && name.length() != 0) {
			toString += " " + name;
		}
		return toString;
	}

	public durationFormat getDurationFormat() {
		return formatOfDuration;
	}

	public void setDurationFormat(durationFormat formatOfDuration) {
		this.formatOfDuration = formatOfDuration;
	}

	public long getPriceInEuroCent() {
		return priceInEuroCent;
	}

	public void setPriceInEuroCent(long priceInEuroCent) {
		this.priceInEuroCent = priceInEuroCent;
	}

	@Override
	public void generateImportFingerPrint() {
		setImportFingerPrint(toString() + Long.toString(priceInEuroCent));
	}

}
