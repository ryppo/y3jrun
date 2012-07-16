package org.y3.jrun.storage.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.control.Utils;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.competition.Competition;

public class DBHandler_Competition extends DBModelHandler {

	@Override
	public String getSqlToCreateModelTable() {
		return KeywordsDictionary.SQL_CREATE_TABLE
				+ KeywordsDictionary.DATABASE_SCHEME + "."
				+ KeywordsDictionary.COMPETITION + " ("
				+ KeywordsDictionary.SQL_ID_INTEGER_PRIMARY_KEY_NOT_NULL + ","
				+ KeywordsDictionary.COMPETITION_TITLE
				+ KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.COMPETITION_RELATED_AGECLASSESDEFINITION_ID
				+ KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.COMPETITION_WEB_PAGE + KeywordsDictionary.SQL_VARCHAR_500 + ","
				+ KeywordsDictionary.COMPETITION_REGISTRATION_PAGE + KeywordsDictionary.SQL_VARCHAR_500 + ","
				+ KeywordsDictionary.COMPETITION_REGISTRATIONS_LOG_FILE + KeywordsDictionary.SQL_VARCHAR_500 + ","
				+ KeywordsDictionary.COMPETITION_STARTOFCOMPETITION + KeywordsDictionary.SQL_TIMESTAMP + ","
				+ KeywordsDictionary.COMPETITION_ENDOFCOMPETITION + KeywordsDictionary.SQL_TIMESTAMP + ","
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES_DEFINITION 
				+ ")";
	}

	@Override
	public String getSqlToDropModelTable() {
		return KeywordsDictionary.SQL_DROP_TABLE
				+ KeywordsDictionary.DATABASE_SCHEME + "."
				+ KeywordsDictionary.COMPETITION;
	}

	@Override
	public String getSqlToInsertModel(Model model) {
		if (model == null || !(model instanceof Competition)) {
			return null;
		}
		Competition competition = (Competition) model;
		return KeywordsDictionary.SQL_INSERT_INTO
				+ KeywordsDictionary.DATABASE_SCHEME + "." 
		+ KeywordsDictionary.COMPETITION + " ("
				+ KeywordsDictionary.COMPETITION_TITLE + ", "
				+ KeywordsDictionary.COMPETITION_RELATED_AGECLASSESDEFINITION_ID + ", "
				+ KeywordsDictionary.COMPETITION_WEB_PAGE + ", "
				+ KeywordsDictionary.COMPETITION_REGISTRATION_PAGE + ", "
				+ KeywordsDictionary.COMPETITION_REGISTRATIONS_LOG_FILE + ", "
				+ KeywordsDictionary.COMPETITION_STARTOFCOMPETITION + ", "
				+ KeywordsDictionary.COMPETITION_ENDOFCOMPETITION + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_VALUES + "'" 
				+ competition.getTitle() + "', "
				+ competition.getAgeClassesDefinitionId() + ", '"
				+ competition.getWebPageToString() + "', '"
				+ competition.getRegistrationPageToString() + "', '"
				+ competition.getRegistrationsLogFileToString() + "', '"
				+ DatabaseSession.dateToTimestampString(competition.getStartOfCompetition()) + "', '"
				+ DatabaseSession.dateToTimestampString(competition.getEndOfCompetition()) + "', '"
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTE_VALUES(model)
				+ ")";
	}

	@Override
	public String getSqlToUpdateModel(Model model) {
		if (model == null || !(model instanceof Competition)) {
			return null;
		}
		Competition competition = (Competition) model;
		return KeywordsDictionary.SQL_UPDATE
				+ KeywordsDictionary.DATABASE_SCHEME + ".COMPETITION SET "
				+ KeywordsDictionary.COMPETITION_TITLE 								+ "='" + competition.getTitle() + "', "
				+ KeywordsDictionary.COMPETITION_RELATED_AGECLASSESDEFINITION_ID 	+ "=" + competition.getAgeClassesDefinitionId() + ", "
				+ KeywordsDictionary.COMPETITION_WEB_PAGE 							+ "='" + competition.getWebPageToString() + "', "
				+ KeywordsDictionary.COMPETITION_REGISTRATION_PAGE 					+ "='" + competition.getRegistrationPageToString() + "', "
				+ KeywordsDictionary.COMPETITION_REGISTRATIONS_LOG_FILE 			+ "='" + competition.getRegistrationsLogFileToString() + "', "
				+ KeywordsDictionary.COMPETITION_STARTOFCOMPETITION 				+ "='" + DatabaseSession.dateToTimestampString(competition.getStartOfCompetition())	+ "', "
				+ KeywordsDictionary.COMPETITION_ENDOFCOMPETITION 					+ "='" + DatabaseSession.dateToTimestampString(competition.getEndOfCompetition())	+ "', "
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTES_FILLED(model)
				+ KeywordsDictionary.SQL_WHERE_ID_IS 								+ competition.getId();
	}

	@Override
	public String getSqlToLoadAllModels() {
		return KeywordsDictionary.SQL_SELECT
				+ KeywordsDictionary.MODEL_ID + ", "
				+ KeywordsDictionary.COMPETITION_TITLE + ", "
				+ KeywordsDictionary.COMPETITION_RELATED_AGECLASSESDEFINITION_ID + ", "
				+ KeywordsDictionary.COMPETITION_WEB_PAGE + ", "
				+ KeywordsDictionary.COMPETITION_REGISTRATION_PAGE + ", "
				+ KeywordsDictionary.COMPETITION_REGISTRATIONS_LOG_FILE + ", "
				+ KeywordsDictionary.COMPETITION_STARTOFCOMPETITION + ", "
				+ KeywordsDictionary.COMPETITION_ENDOFCOMPETITION + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_FROM
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.COMPETITION;
	}
	
	@Override
	public Competition[] mapResultSetToModels(ResultSet resultSet) throws SQLException {
		Competition[] competitions = null;
		if (resultSet != null) {
			//create array
			resultSet.last();
			competitions = new Competition[resultSet.getRow()];
			resultSet.beforeFirst();
			//loop resultSet
			int competitionCount = 0;
			while (resultSet.next()) {
				Competition c = new Competition();
				c.setId(resultSet.getInt(1));
				c.setTitle(resultSet.getString(2));
				c.setAgeClassesDefinitionId(resultSet.getInt(3));
				c.setWebPage(resultSet.getString(4));
				c.setRegistrationPage(resultSet.getString(5));
				c.setRegistrationsLogFile(resultSet.getString(6));
				c.setStartOfCompetition(resultSet.getTimestamp(7));
				c.setEndOfCompetition(resultSet.getTimestamp(8));
				KeywordsDictionary.setMODEL_META_ATTRIBUTES(c, resultSet, 9);
				competitions[competitionCount] = c;
				competitionCount++;
			}
		}
		return competitions;
	}

	@Override
	public String getSqlForFullTextSearch(String searchString) {
		String searchIntegerString = Utils.parseIntegerFromStringAsString(searchString);
		String whereClause =
				KeywordsDictionary.COMPETITION_TITLE + KeywordsDictionary.SQL_LIKE(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.COMPETITION_REGISTRATION_PAGE) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.COMPETITION_REGISTRATIONS_LOG_FILE) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.COMPETITION_WEB_PAGE) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.COMPETITION_RELATED_AGECLASSESDEFINITION_ID + "=" + searchIntegerString;
		return getSqlToLoadModels(whereClause);
	}
	
	public String getSqlToGetCompetitionIdsByAgeClassesDefinitionIds(int[] competitionIds) {
		String sql = null;
		if (competitionIds != null && competitionIds.length > 0) {
			sql = 
				KeywordsDictionary.SQL_SELECT + KeywordsDictionary.MODEL_ID
				+ KeywordsDictionary.SQL_FROM + KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.COMPETITION
				+ KeywordsDictionary.SQL_WHERE;
			boolean first = true;
			for (int cid: competitionIds) {
				if (!first) {
					sql += KeywordsDictionary.SQL_OR;
				}
				sql += KeywordsDictionary.COMPETITION_RELATED_AGECLASSESDEFINITION_ID + "=" + cid;
				first = false;
			}
		}
		return sql;
	}

}
