package org.y3.jrun.storage.file;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinition;
import org.y3.jrun.model.ageclassesdefinition.RichAgeClassesDefinition;

public class AgeClassesDefinitionExchanger extends ModelExchanger {
	
	@Override
	public JSONObject modelToJSON(Model _model) {
		JSONObject json = null;
		if (_model != null && _model instanceof RichAgeClassesDefinition) {
			RichAgeClassesDefinition model = (RichAgeClassesDefinition) _model;
			json = new JSONObject();
			json.put(KeywordsDictionary.MODEL_ID, model.getId());
			json.put(KeywordsDictionary.AGECLASSESDEFINITION_NAME, model.getName());
			System.out.println(model.toString());
			AgeClass[] ageClasses = model.getAgeClasses();
			if (ageClasses != null && ageClasses.length > 0) {
				AgeClassExchanger ageClassExchanger = new AgeClassExchanger();
				JSONArray ageClassesJSON = new JSONArray();
				for (AgeClass ageClass: ageClasses) {
					JSONObject ageClassJSON = ageClassExchanger.modelToJSON(ageClass);
					ageClassesJSON.add(ageClassJSON);
				}
				json.put(KeywordsDictionary.AGECLASS, ageClassesJSON);
			}
		}
		return json;
	}

	@Override
	public RichAgeClassesDefinition JSONToModel(JSONObject json) {
		RichAgeClassesDefinition richAgeClassesDefinition = null;
		if (json != null) {
			AgeClassesDefinition ageClassesDefinition = new AgeClassesDefinition();
			ageClassesDefinition.setName(json.getString(KeywordsDictionary.AGECLASSESDEFINITION_NAME));
			//age classes
			AgeClass[] ageClasses = null;
			JSONArray ageClassesJSON = json.getJSONArray(KeywordsDictionary.AGECLASS);
			if (ageClassesJSON != null) {
				AgeClassExchanger ageClassExchanger = new AgeClassExchanger();
				int countAgeClasses = ageClassesJSON.size();
				ageClasses = new AgeClass[countAgeClasses];
				for (int no = 0; no < countAgeClasses; no++) {
					Object o = ageClassesJSON.get(no);
					if (o instanceof JSONObject) {
						Model model = ageClassExchanger.JSONToModel((JSONObject) o);
						if (model instanceof AgeClass) {
							ageClasses[no] = (AgeClass) model;
						}
					}
				}
			}
			richAgeClassesDefinition = new RichAgeClassesDefinition(ageClassesDefinition, ageClasses);
		}
		return richAgeClassesDefinition;
	}

	@Override
	public String getModelClassName() {
		return KeywordsDictionary.AGECLASSESDEFINITION;
	}
	
}
