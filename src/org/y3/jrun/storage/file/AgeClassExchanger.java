package org.y3.jrun.storage.file;

import net.sf.json.JSONObject;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ageclass.AgeClass;

public class AgeClassExchanger extends ModelExchanger {

	@Override
	public String getModelClassName() {
		return KeywordsDictionary.AGECLASS;
	}

	@Override
	public JSONObject modelToJSON(Model _model) {
		JSONObject json = null;
		if (_model != null && _model instanceof AgeClass) {
			AgeClass model = (AgeClass) _model;
			json = new JSONObject();
			json.put(KeywordsDictionary.MODEL_ID, model.getId());
			json.put(KeywordsDictionary.AGECLASS_TITLE, model.getTitle());
			json.put(KeywordsDictionary.AGECLASS_YEAR_FROM, model.getYearFrom());
			json.put(KeywordsDictionary.AGECLASS_YEAR_TO, model.getYearTo());
		}
		return json;
	}

	@Override
	public Model JSONToModel(JSONObject json) {
		AgeClass ageClass = null;
		if (json != null) {
			ageClass = new AgeClass();
			ageClass.setTitle(json.getString(KeywordsDictionary.AGECLASS_TITLE));
			ageClass.setYearFrom(json.getInt(KeywordsDictionary.AGECLASS_YEAR_FROM));
			ageClass.setYearTo(json.getInt(KeywordsDictionary.AGECLASS_YEAR_TO));
		}
		return ageClass;
	}

}
