package org.y3.jrun.storage.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.y3.jrun.model.Model;

public abstract class ModelExchanger {

	public final String EXTENSION = ".json";
	
	public boolean exportModels(Model[] models, String toLocation) throws IOException {
		boolean exported = false;
		if (models != null && toLocation != null) {
			if (!StringUtils.endsWith(toLocation, EXTENSION)) {
				toLocation += EXTENSION;
			}
			File exportFile = new File(toLocation);
			JSONObject json = new JSONObject();
			for (Model model:models) {
				json.put(getModelClassName() + model.getId(), modelToJSON(model));
			}
			FileWriter writer = new FileWriter(exportFile);
			json.write(writer);
			writer.flush();
			writer.close();
			exported = true;
		}
		return exported;
	}
	
	public abstract String getModelClassName();
	
	public abstract JSONObject modelToJSON(Model model);
	
	public Model[] importModels(String fromLocation) throws IOException {
		Model[] importedModels = null;
		if (fromLocation != null) {
			if (!StringUtils.endsWith(fromLocation, EXTENSION)) {
				fromLocation += EXTENSION;
			}
			File importFile = new File(fromLocation);
			BufferedReader reader = new BufferedReader( new FileReader(importFile));
			String line;
			String fileString = new String();
			while ((line = reader.readLine()) != null) {
				fileString += line;
			}
			reader.close();
			JSONObject json = (JSONObject) JSONSerializer.toJSON(fileString);
			JSONArray names = json.names();
			int modelsCounter = names.size();
			if (modelsCounter != 0) {
				importedModels = new Model[modelsCounter];
			}
			for (int i = 0; i < names.size(); i++) {
				JSONObject actualJSON = json.getJSONObject(names.getString(i));
				if (actualJSON != null && !actualJSON.isNullObject()) {
					importedModels[i] = JSONToModel(actualJSON);
				}
				modelsCounter++;
			}
		}
		return importedModels;
	}
	
	public abstract Model JSONToModel(JSONObject json);
	
}
