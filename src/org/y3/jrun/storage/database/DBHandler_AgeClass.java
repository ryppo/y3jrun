package org.y3.jrun.storage.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.control.Utils;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ageclass.AgeClass;

public class DBHandler_AgeClass extends DBModelHandler {

	@Override
	public String getSqlToCreateModelTable() {
		return KeywordsDictionary.SQL_CREATE_TABLE
				+ KeywordsDictionary.DATABASE_SCHEME
				+ "." + KeywordsDictionary.AGECLASS + " ("
				+ KeywordsDictionary.SQL_ID_INTEGER_PRIMARY_KEY_NOT_NULL + ","
				+ KeywordsDictionary.AGECLASS_TITLE + KeywordsDictionary.SQL_VARCHAR_100 + ","
				+ KeywordsDictionary.AGECLASS_YEAR_FROM + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.AGECLASS_YEAR_TO + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.AGECLASS_BELONGS_TO_AGECLASSESDEFINITON + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES_DEFINITION
				+ ")";
	}

	@Override
	public String getSqlToDropModelTable() {
		return KeywordsDictionary.SQL_DROP_TABLE 
				+ KeywordsDictionary.DATABASE_SCHEME 
				+ "." + KeywordsDictionary.AGECLASS;
	}

	@Override
	public String getSqlToInsertModel(Model model) {
		if (model == null || !(model instanceof AgeClass)) {
			return null;
		}
		AgeClass ageClass = (AgeClass) model;
		return KeywordsDictionary.SQL_INSERT_INTO
				+ KeywordsDictionary.DATABASE_SCHEME
				+ "." + KeywordsDictionary.AGECLASS + " ("
				+ KeywordsDictionary.AGECLASS_TITLE + ", "
				+ KeywordsDictionary.AGECLASS_YEAR_FROM + ", "
				+ KeywordsDictionary.AGECLASS_YEAR_TO + ", "
				+ KeywordsDictionary.AGECLASS_BELONGS_TO_AGECLASSESDEFINITON + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_VALUES 
				+ "'" + ageClass.getTitle() + "',"
				+ ageClass.getYearFrom() + ","
				+ ageClass.getYearTo() + ","
				+ ageClass.getRelatedAgeClassesDefinitionID() + ",'"
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTE_VALUES(model)
				+ ")";
	}

	@Override
	public String getSqlToUpdateModel(Model model) {
		if (model == null || !(model instanceof AgeClass)) {
			return null;
		}
		AgeClass ageClass = (AgeClass) model;
		return KeywordsDictionary.SQL_UPDATE + KeywordsDictionary.DATABASE_SCHEME + "." 
				+ KeywordsDictionary.AGECLASS + KeywordsDictionary.SQL_SET 
				+ KeywordsDictionary.AGECLASS_TITLE								+ "='" + ageClass.getTitle() + "',"
				+ KeywordsDictionary.AGECLASS_YEAR_FROM 						+ "=" + ageClass.getYearFrom() + ","
				+ KeywordsDictionary.AGECLASS_YEAR_TO 							+ "=" + ageClass.getYearTo() + ","
				+ KeywordsDictionary.AGECLASS_BELONGS_TO_AGECLASSESDEFINITON 	+ "=" + ageClass.getRelatedAgeClassesDefinitionID() + ", "
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTES_FILLED(model)
				+ KeywordsDictionary.SQL_WHERE_ID_IS
				+ ageClass.getId();
	}

	@Override
	public String getSqlToLoadAllModels() {
		return KeywordsDictionary.SQL_SELECT
				+ KeywordsDictionary.MODEL_ID + ", "
				+ KeywordsDictionary.AGECLASS_TITLE + ", "
				+ KeywordsDictionary.AGECLASS_YEAR_FROM + ", "
				+ KeywordsDictionary.AGECLASS_YEAR_TO + ", "
				+ KeywordsDictionary.AGECLASS_BELONGS_TO_AGECLASSESDEFINITON + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_FROM
				+ KeywordsDictionary.DATABASE_SCHEME + "."
				+ KeywordsDictionary.AGECLASS;
	}
	
	public String getSqlToLoadModelsByAgeClassesDefinitionId(int ageClassesDefinitionId) {
		return getSqlToLoadModels(
				KeywordsDictionary.AGECLASS_BELONGS_TO_AGECLASSESDEFINITON
				+ "=" + ageClassesDefinitionId);
	}

	@Override
	public AgeClass[] mapResultSetToModels(ResultSet resultSet)
			throws SQLException {
		AgeClass[] ageClasses = null;
		if (resultSet != null) {
			//create array
			resultSet.last();
			ageClasses = new AgeClass[resultSet.getRow()];
			resultSet.beforeFirst();
			//loop resultSet
			int ageClassesCount = 0;
			while (resultSet.next()) {
				AgeClass a = new AgeClass();
				a.setId(resultSet.getInt(1));
				a.setTitle(resultSet.getString(2));
				a.setYearFrom(resultSet.getInt(3));
				a.setYearTo(resultSet.getInt(4));
				a.setRelatedAgeClassesDefinitionID(resultSet.getInt(5));
				KeywordsDictionary.setMODEL_META_ATTRIBUTES(a, resultSet, 6);
				ageClasses[ageClassesCount] = a;
				ageClassesCount++;
			}
		}
		return ageClasses;
	}

	@Override
	public String getSqlForFullTextSearch(String searchString) {
		String searchIntegerString = Utils.parseIntegerFromStringAsString(searchString);
		String whereClause =
				KeywordsDictionary.AGECLASS_TITLE + "='" + searchString + "' " + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.AGECLASS_YEAR_FROM + "=" + searchIntegerString + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.AGECLASS_YEAR_TO + "=" + searchIntegerString + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.AGECLASS_BELONGS_TO_AGECLASSESDEFINITON + "=" + searchIntegerString;
		return getSqlToLoadModels(whereClause);
	}

}
