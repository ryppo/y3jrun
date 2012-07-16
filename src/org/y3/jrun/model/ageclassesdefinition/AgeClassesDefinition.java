package org.y3.jrun.model.ageclassesdefinition;

import org.y3.jrun.model.Model;

public class AgeClassesDefinition extends Model {
	
	private String name;
	
	@Override
	public String toString() {
		return getName();
	}

	public String getName() {
		if (name == null) {
			return "";
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void generateImportFingerPrint() {
		setImportFingerPrint(toString());
	}

}
