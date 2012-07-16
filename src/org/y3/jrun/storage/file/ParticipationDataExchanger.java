package org.y3.jrun.storage.file;

import net.sf.json.JSONObject;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.participation.Participation;

public class ParticipationDataExchanger extends ModelExchanger {

	@Override
	public String getModelClassName() {
		return KeywordsDictionary.PARTICIPATION;
	}

	@Override
	public JSONObject modelToJSON(Model _model) {
		JSONObject json = null;
		if (_model != null && _model instanceof Participation) {
			Participation model = (Participation) _model;
			json = new JSONObject();
			json.put(KeywordsDictionary.MODEL_IMPORT_FINGERPRINT, model.getImportFingerPrint());
			json.put(KeywordsDictionary.PARTICIPATION_PARTICIPATION_NUMBER, model.getParticipationNumber());
			json.put(KeywordsDictionary.PARTICIPATION_RESULTTIME, model.getResultTimeAsString());
		}
		return json;
	}

	@Override
	public Participation JSONToModel(JSONObject json) {
		Participation model = null;
		if (json != null) {
			model = new Participation();
			model.setImportFingerPrint(json.getString(KeywordsDictionary.MODEL_IMPORT_FINGERPRINT));
			model.setParticipationNumber(json.getString(KeywordsDictionary.PARTICIPATION_PARTICIPATION_NUMBER));
			model.setResultTimeAsString(json.getString(KeywordsDictionary.PARTICIPATION_RESULTTIME));
		}
		return model;
	}

}
