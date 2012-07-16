package org.y3.jrun.storage.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.control.Utils;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.discipline.Discipline;

public class DBHandler_Discipline extends DBModelHandler {

	@Override
	public String getSqlToCreateModelTable() {
		return KeywordsDictionary.SQL_CREATE_TABLE
				+ KeywordsDictionary.DATABASE_SCHEME + "."
				+ KeywordsDictionary.DISCIPLINE + " ("
				+ KeywordsDictionary.SQL_ID_INTEGER_PRIMARY_KEY_NOT_NULL + ","
				+ KeywordsDictionary.DISCIPLINE_NAME
				+ KeywordsDictionary.SQL_VARCHAR_100 + ","
				+ KeywordsDictionary.DISCIPLINE_LENGTH 
				+ KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.DISCIPLINE_START + KeywordsDictionary.SQL_TIMESTAMP + ","
				+ KeywordsDictionary.DISCIPLINE_TIMEFORMAT + KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.DISCIPLINE_PRICE_IN_EUROCENT + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES_DEFINITION 
				+ ")";
	}

	@Override
	public String getSqlToDropModelTable() {
		return KeywordsDictionary.SQL_DROP_TABLE
				+ KeywordsDictionary.DATABASE_SCHEME + "."
				+ KeywordsDictionary.DISCIPLINE;
	}

	@Override
	public String getSqlToInsertModel(Model model) {
		if (model == null || !(model instanceof Discipline)) {
			return null;
		}
		Discipline discipline = (Discipline) model;
		return KeywordsDictionary.SQL_INSERT_INTO
				+ KeywordsDictionary.DATABASE_SCHEME + "." 
		+ KeywordsDictionary.DISCIPLINE + " ("
				+ KeywordsDictionary.DISCIPLINE_NAME + ", "
				+ KeywordsDictionary.DISCIPLINE_LENGTH + ", "
				+ KeywordsDictionary.DISCIPLINE_START + ", "
				+ KeywordsDictionary.DISCIPLINE_TIMEFORMAT + ", "
				+ KeywordsDictionary.DISCIPLINE_PRICE_IN_EUROCENT + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_VALUES + "'" 
				+ discipline.getName() + "', '"
				+ discipline.getLength() + "', '"
				+ DatabaseSession.dateToTimestampString(discipline.getStartOfDiscipline()) + "', '"
				+ discipline.getDurationFormat() + "', "
				+ discipline.getPriceInEuroCent() + ", '"
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTE_VALUES(model)
				+ ")";
	}

	@Override
	public String getSqlToUpdateModel(Model model) {
		if (model == null || !(model instanceof Discipline)) {
			return null;
		}
		Discipline discipline = (Discipline) model;
		return KeywordsDictionary.SQL_UPDATE
				+ KeywordsDictionary.DATABASE_SCHEME + ".DISCIPLINE SET "
				+ KeywordsDictionary.DISCIPLINE_NAME 				+ "='" + discipline.getName() + "', "
				+ KeywordsDictionary.DISCIPLINE_LENGTH 				+ "='" + discipline.getLength()	+ "', "
				+ KeywordsDictionary.DISCIPLINE_START 				+ "='" + DatabaseSession.dateToTimestampString(discipline.getStartOfDiscipline())	+ "', "
				+ KeywordsDictionary.DISCIPLINE_TIMEFORMAT 			+ "='"	+ discipline.getDurationFormat() + "', "
				+ KeywordsDictionary.DISCIPLINE_PRICE_IN_EUROCENT 	+ "=" + discipline.getPriceInEuroCent() + ", "
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTES_FILLED(model)
				+ KeywordsDictionary.SQL_WHERE_ID_IS 				+ discipline.getId();
	}

	@Override
	public String getSqlToLoadAllModels() {
		return KeywordsDictionary.SQL_SELECT
				+ KeywordsDictionary.MODEL_ID + ", "
				+ KeywordsDictionary.DISCIPLINE_NAME + ", "
				+ KeywordsDictionary.DISCIPLINE_LENGTH + ", "
				+ KeywordsDictionary.DISCIPLINE_START + ", "
				+ KeywordsDictionary.DISCIPLINE_TIMEFORMAT + ", "
				+ KeywordsDictionary.DISCIPLINE_PRICE_IN_EUROCENT + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_FROM
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE;
	}
	
	public String getSqlToLoadModelsByCompetitionId(int competitionId) {
		String sql = KeywordsDictionary.SQL_SELECT + " DISTINCT "
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE + "." + KeywordsDictionary.MODEL_ID + ", "
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE + "." + KeywordsDictionary.DISCIPLINE_NAME + ", "
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE + "." + KeywordsDictionary.DISCIPLINE_LENGTH + ", "
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE + "." + KeywordsDictionary.DISCIPLINE_START + ", "
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE + "." + KeywordsDictionary.DISCIPLINE_TIMEFORMAT + ", "
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE + "." + KeywordsDictionary.DISCIPLINE_PRICE_IN_EUROCENT + ", "
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE + "." + KeywordsDictionary.MODEL_IMPORT_FINGERPRINT + ", "
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE + "." + KeywordsDictionary.MODEL_CREATIONDATE + ", "
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE + "." + KeywordsDictionary.MODEL_CREATOR + ", "
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE + "." + KeywordsDictionary.MODEL_CHANGEDATE + ", "
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE + "." + KeywordsDictionary.MODEL_CHANGER + ", "
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE + "." + KeywordsDictionary.MODEL_VERSION
				+ KeywordsDictionary.SQL_FROM + KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE
				+ " INNER JOIN " + KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.PARTICIPATION
				+ " ON " + KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.DISCIPLINE + "." + KeywordsDictionary.MODEL_ID
				+ " = " + KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.PARTICIPATION + "." + KeywordsDictionary.PARTICIPATION_RELATED_DISCIPLIN_ID
				+ " INNER JOIN " + KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.COMPETITION
				+ " ON " + KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.COMPETITION + "." + KeywordsDictionary.MODEL_ID
				+ " = " + KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.PARTICIPATION + "." + KeywordsDictionary.PARTICIPATION_RELATED_COMPETITION_ID
				+ KeywordsDictionary.SQL_WHERE
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.COMPETITION + "." + KeywordsDictionary.MODEL_ID
				+ " = " + competitionId;
		return sql;
	}

	@Override
	public Discipline[] mapResultSetToModels(ResultSet resultSet)
			throws SQLException {
		Discipline[] disciplines = null;
		if (resultSet != null) {
			//create array
			resultSet.last();
			disciplines = new Discipline[resultSet.getRow()];
			resultSet.beforeFirst();
			//loop resultSet
			int disciplineCount = 0;
			while (resultSet.next()) {
				Discipline d = new Discipline();
				d.setId(resultSet.getInt(1));
				d.setName(resultSet.getString(2));
				d.setLength((resultSet.getString(3)));
				d.setStartOfDiscipline(resultSet.getTimestamp(4));
				String timeFormat = resultSet.getString(5);
				if (timeFormat.equals(Discipline.durationFormat.HH_mm_ss.toString())) {
					d.setDurationFormat(Discipline.durationFormat.HH_mm_ss);
				} else if (timeFormat.equals(Discipline.durationFormat.HH_mm_ss_SSS.toString())) {
					d.setDurationFormat(Discipline.durationFormat.HH_mm_ss_SSS);
				} else if (timeFormat.equals(Discipline.durationFormat.mm_ss.toString())) {
					d.setDurationFormat(Discipline.durationFormat.mm_ss);
				}
				d.setPriceInEuroCent(resultSet.getInt(6));
				KeywordsDictionary.setMODEL_META_ATTRIBUTES(d, resultSet, 7);
				disciplines[disciplineCount] = d;
				disciplineCount++;
			}
		}
		return disciplines;
	}

	@Override
	public String getSqlForFullTextSearch(String searchString) {
		String searchIntegerString = Utils.parseIntegerFromStringAsString(searchString);
		String whereClause =
				KeywordsDictionary.SQL_UPPER(KeywordsDictionary.DISCIPLINE_LENGTH) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.DISCIPLINE_NAME) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.SQL_UPPER(KeywordsDictionary.DISCIPLINE_TIMEFORMAT) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.DISCIPLINE_PRICE_IN_EUROCENT + "=" + searchIntegerString;
		return getSqlToLoadModels(whereClause);
	}

}
