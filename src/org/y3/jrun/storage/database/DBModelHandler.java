package org.y3.jrun.storage.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;

public abstract class DBModelHandler {

	public abstract String getSqlToCreateModelTable();
	
	public abstract String getSqlToDropModelTable();
	
	public abstract String getSqlToInsertModel(Model model);
	
	public abstract String getSqlToUpdateModel(Model model);
	
	public abstract String getSqlForFullTextSearch(String searchString);
	
	public String getSqlToLoadModels(String whereClause) {
		String sql = getSqlToLoadAllModels();
		if (whereClause != null) {
			 sql += KeywordsDictionary.SQL_WHERE + whereClause;
		}
		return sql;
	}
	
	public abstract String getSqlToLoadAllModels();
	
	public String getSqlToLoadModelsByIDs(int[] IDs) {
		String whereClause = null;
		if (IDs != null && IDs.length != 0) {
			whereClause = KeywordsDictionary.MODEL_ID + "=" + IDs[0];
			if (IDs.length > 1) {
				for (int i = 1; i < IDs.length; i++) {
					whereClause += KeywordsDictionary.SQL_OR
							+ KeywordsDictionary.MODEL_ID + "=" + IDs[i];
				}
			}
		}
		return getSqlToLoadModels(whereClause);
	}
	
	public String getSqlToLoadModelsByFingerPrint(String fingerPrint) {
		if (fingerPrint == null) {
			fingerPrint = "";
		}
		String whereClause = KeywordsDictionary.MODEL_IMPORT_FINGERPRINT + "='" + fingerPrint + "'";
		return getSqlToLoadModels(whereClause);
	}
	
	public abstract Model[] mapResultSetToModels(ResultSet resultSet) throws SQLException;
	
}
