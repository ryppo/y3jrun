package org.y3.jrun.storage.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinition;

public class DBHandler_AgeClassesDefinition extends DBModelHandler {

	@Override
	public String getSqlToCreateModelTable() {
		return KeywordsDictionary.SQL_CREATE_TABLE
				+ KeywordsDictionary.DATABASE_SCHEME
				+ "." + KeywordsDictionary.AGECLASSESDEFINITION + " ("
				+ KeywordsDictionary.SQL_ID_INTEGER_PRIMARY_KEY_NOT_NULL + ","
				+ KeywordsDictionary.AGECLASSESDEFINITION_NAME + KeywordsDictionary.SQL_VARCHAR_100 + ","
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES_DEFINITION
				+ ")";
	}

	@Override
	public String getSqlToDropModelTable() {
		return KeywordsDictionary.SQL_DROP_TABLE 
				+ KeywordsDictionary.DATABASE_SCHEME 
				+ "." + KeywordsDictionary.AGECLASSESDEFINITION;
	}

	@Override
	public String getSqlToInsertModel(Model model) {
		if (model == null || !(model instanceof AgeClassesDefinition)) {
			return null;
		}
		AgeClassesDefinition ageClassesDefinition = (AgeClassesDefinition) model;
		return KeywordsDictionary.SQL_INSERT_INTO
				+ KeywordsDictionary.DATABASE_SCHEME
				+ "." + KeywordsDictionary.AGECLASSESDEFINITION + " ("
				+ KeywordsDictionary.AGECLASSESDEFINITION_NAME + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_VALUES + "'"
				+ ageClassesDefinition.getName() + "','"
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTE_VALUES(model)
				+ ")";
	}

	@Override
	public String getSqlToUpdateModel(Model model) {
		if (model == null || !(model instanceof AgeClassesDefinition)) {
			return null;
		}
		AgeClassesDefinition ageClassesDefinition = (AgeClassesDefinition) model;
		return KeywordsDictionary.SQL_UPDATE + KeywordsDictionary.DATABASE_SCHEME + "." 
				+ KeywordsDictionary.AGECLASSESDEFINITION + KeywordsDictionary.SQL_SET 
				+ KeywordsDictionary.AGECLASSESDEFINITION_NAME 				+ "='" + ageClassesDefinition.getName() + "', "
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTES_FILLED(model)
				+ KeywordsDictionary.SQL_WHERE_ID_IS
				+ ageClassesDefinition.getId();
	}

	@Override
	public String getSqlToLoadAllModels() {
		return KeywordsDictionary.SQL_SELECT
				+ KeywordsDictionary.MODEL_ID + ", "
				+ KeywordsDictionary.AGECLASSESDEFINITION_NAME + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_FROM
				+ KeywordsDictionary.DATABASE_SCHEME + "."
				+ KeywordsDictionary.AGECLASSESDEFINITION;
	}

	@Override
	public AgeClassesDefinition[] mapResultSetToModels(ResultSet resultSet)
			throws SQLException {
		AgeClassesDefinition[] ageClassesDefinitions = null;
		if (resultSet != null) {
			//create array
			resultSet.last();
			ageClassesDefinitions = new AgeClassesDefinition[resultSet.getRow()];
			resultSet.beforeFirst();
			//loop resultSet
			int ageClassesDefinitionCount = 0;
			while (resultSet.next()) {
				AgeClassesDefinition ad = new AgeClassesDefinition();
				ad.setId(resultSet.getInt(1));
				ad.setName(resultSet.getString(2));
				KeywordsDictionary.setMODEL_META_ATTRIBUTES(ad, resultSet, 3);
				ageClassesDefinitions[ageClassesDefinitionCount] = ad;
				ageClassesDefinitionCount++;
			}
		}
		return ageClassesDefinitions;
	}
	
	public String getSqlToLoadById(int modelId) {
		String whereClause = KeywordsDictionary.MODEL_ID + "="
				+ Integer.toString(modelId);
		return getSqlToLoadModels(whereClause);
	}

	@Override
	public String getSqlForFullTextSearch(String searchString) {
		String whereClause =
				KeywordsDictionary.SQL_UPPER(KeywordsDictionary.AGECLASSESDEFINITION_NAME) + KeywordsDictionary.SQL_LIKE_UPPER(searchString);
		return getSqlToLoadModels(whereClause);
	}

}
