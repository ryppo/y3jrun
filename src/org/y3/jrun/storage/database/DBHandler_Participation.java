package org.y3.jrun.storage.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.control.Utils;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.participation.Participation;

public class DBHandler_Participation extends DBModelHandler {

	@Override
	public String getSqlToCreateModelTable() {
		return KeywordsDictionary.SQL_CREATE_TABLE
				+ KeywordsDictionary.DATABASE_SCHEME + "."
				+ KeywordsDictionary.PARTICIPATION + " ("
				+ KeywordsDictionary.SQL_ID_INTEGER_PRIMARY_KEY_NOT_NULL + ","
				+ KeywordsDictionary.PARTICIPATION_RESULTTIME + KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.PARTICIPATION_PARTICIPATION_NUMBER + KeywordsDictionary.SQL_VARCHAR_50 + ","
				+ KeywordsDictionary.PARTICIPATION_FEE_PAID + KeywordsDictionary.SQL_SMALLINT + ","
				+ KeywordsDictionary.PARTICIPATION_CERTIFICATION_HANDEDOVER + KeywordsDictionary.SQL_SMALLINT + ","
				+ KeywordsDictionary.PARTICIPATION_NONCOMPETITIVE + KeywordsDictionary.SQL_SMALLINT + ","
				+ KeywordsDictionary.PARTICIPATION_RELATED_COMPETITION_ID + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.PARTICIPATION_RELATED_CONTACT_ID + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.PARTICIPATION_RELATED_DISCIPLIN_ID + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.PARTICIPATION_RANK + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.PARTICIPATION_AGECLASS_RANK + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.PARTICIPATION_GENDER_RANK + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.PARTICIPATION_GENDER_AGECLASS_RANK + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.PARTICIPATION_COMMENT + KeywordsDictionary.SQL_VARCHAR_500 + ","
				+ KeywordsDictionary.PARTICIPATION_DONATION_HOSPIZ + KeywordsDictionary.SQL_INTEGER + ","
				+ KeywordsDictionary.PARTICIPATION_REGISTERED_ONLINE + KeywordsDictionary.SQL_SMALLINT + ","
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES_DEFINITION 
				+ ")";
	}

	@Override
	public String getSqlToDropModelTable() {
		return KeywordsDictionary.SQL_DROP_TABLE
				+ KeywordsDictionary.DATABASE_SCHEME + "."
				+ KeywordsDictionary.PARTICIPATION;
	}

	@Override
	public String getSqlToInsertModel(Model model) {
		if (model == null || !(model instanceof Participation)) {
			return null;
		}
		Participation participation = (Participation) model;
		return KeywordsDictionary.SQL_INSERT_INTO
				+ KeywordsDictionary.DATABASE_SCHEME + "." 
		+ KeywordsDictionary.PARTICIPATION + " ("
				+ KeywordsDictionary.PARTICIPATION_RESULTTIME + ", "
				+ KeywordsDictionary.PARTICIPATION_PARTICIPATION_NUMBER + ", "
				+ KeywordsDictionary.PARTICIPATION_FEE_PAID + ", "
				+ KeywordsDictionary.PARTICIPATION_CERTIFICATION_HANDEDOVER + ", "
				+ KeywordsDictionary.PARTICIPATION_NONCOMPETITIVE + ", "
				+ KeywordsDictionary.PARTICIPATION_RELATED_COMPETITION_ID + ", "
				+ KeywordsDictionary.PARTICIPATION_RELATED_CONTACT_ID + ", "
				+ KeywordsDictionary.PARTICIPATION_RELATED_DISCIPLIN_ID + ", "
				+ KeywordsDictionary.PARTICIPATION_RANK + ", "
				+ KeywordsDictionary.PARTICIPATION_AGECLASS_RANK + ", "
				+ KeywordsDictionary.PARTICIPATION_GENDER_RANK + ", "
				+ KeywordsDictionary.PARTICIPATION_GENDER_AGECLASS_RANK + ", "
				+ KeywordsDictionary.PARTICIPATION_COMMENT + ", "
				+ KeywordsDictionary.PARTICIPATION_DONATION_HOSPIZ + ", "
				+ KeywordsDictionary.PARTICIPATION_REGISTERED_ONLINE + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_VALUES + "'" 
				+ participation.getResultTimeAsString() + "', '"
				+ participation.getParticipationNumber() + "', "
				+ participation.isPaymentDoneAsInt() + ", "
				+ participation.isCertificationHandedOverAsInt() + ", "
				+ participation.isNoncompetitiveAsInt() + ", "
				+ Integer.toString(participation.getCompetitionId()) + ", "
				+ Integer.toString(participation.getContactId()) + ", "
				+ Integer.toString(participation.getDisciplineId()) + ", "
				+ Integer.toString(participation.getRank()) + ", "
				+ Integer.toString(participation.getAgeClassRank()) + ", "
				+ Integer.toString(participation.getGenderRank()) + ", "
				+ Integer.toString(participation.getGenderAgeClassRank()) + ", '"
				+ participation.getComment() + "', "
				+ participation.getDonationHospizInEuroCent() + ", "
				+ participation.isRegisteredOnlineAsInt() + ", '"
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTE_VALUES(model)
				+ ")";
	}

	@Override
	public String getSqlToUpdateModel(Model model) {
		if (model == null || !(model instanceof Participation)) {
			return null;
		}
		Participation participation = (Participation) model;
		return KeywordsDictionary.SQL_UPDATE
				+ KeywordsDictionary.DATABASE_SCHEME 						+ "." + KeywordsDictionary.PARTICIPATION + " SET "
				+ KeywordsDictionary.PARTICIPATION_RESULTTIME 				+ "='" + participation.getResultTime() + "', "
				+ KeywordsDictionary.PARTICIPATION_PARTICIPATION_NUMBER 	+ "='" + participation.getParticipationNumber() + "', "
				+ KeywordsDictionary.PARTICIPATION_FEE_PAID 				+ "=" + participation.isPaymentDoneAsInt() + ", "
				+ KeywordsDictionary.PARTICIPATION_CERTIFICATION_HANDEDOVER + "=" + participation.isCertificationHandedOverAsInt() + ", "
				+ KeywordsDictionary.PARTICIPATION_NONCOMPETITIVE			+ "=" + participation.isNoncompetitiveAsInt() + ", "
				+ KeywordsDictionary.PARTICIPATION_RELATED_COMPETITION_ID 	+ "=" + Integer.toString(participation.getCompetitionId())	+ ", "
				+ KeywordsDictionary.PARTICIPATION_RELATED_CONTACT_ID 		+ "=" + Integer.toString(participation.getContactId())	+ ", "
				+ KeywordsDictionary.PARTICIPATION_RELATED_DISCIPLIN_ID 	+ "=" + Integer.toString(participation.getDisciplineId()) + ", "
				+ KeywordsDictionary.PARTICIPATION_RANK 					+ "=" + Integer.toString(participation.getRank()) + ", "
				+ KeywordsDictionary.PARTICIPATION_AGECLASS_RANK			+ "=" + Integer.toString(participation.getAgeClassRank()) + ", "
				+ KeywordsDictionary.PARTICIPATION_GENDER_RANK				+ "=" + Integer.toString(participation.getGenderRank()) + ", "
				+ KeywordsDictionary.PARTICIPATION_GENDER_AGECLASS_RANK		+ "=" + Integer.toString(participation.getGenderAgeClassRank()) + ", "
				+ KeywordsDictionary.PARTICIPATION_COMMENT						+ "='" + participation.getComment() + "', "
				+ KeywordsDictionary.PARTICIPATION_DONATION_HOSPIZ			+ "=" + participation.getDonationHospizInEuroCent() + ", "
				+ KeywordsDictionary.PARTICIPATION_REGISTERED_ONLINE		+ "=" + participation.isRegisteredOnlineAsInt() + ", "
				+ KeywordsDictionary.getMODEL_META_ATTRIBUTES_FILLED(model)
				+ KeywordsDictionary.SQL_WHERE_ID_IS + participation.getId();
	}
	
	public String getSqlToGetSummaryOfHospizDonationsByCompetitionId(int competitionId) {
		return KeywordsDictionary.SQL_SELECT
				+ KeywordsDictionary.SQL_SUM(KeywordsDictionary.PARTICIPATION_DONATION_HOSPIZ)
				+ KeywordsDictionary.SQL_FROM
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.PARTICIPATION
				+ KeywordsDictionary.SQL_WHERE + KeywordsDictionary.PARTICIPATION_RELATED_COMPETITION_ID + "=" + competitionId;
	}

	@Override
	public String getSqlToLoadAllModels() {
		return KeywordsDictionary.SQL_SELECT
				+ KeywordsDictionary.MODEL_ID + ", "
				+ KeywordsDictionary.PARTICIPATION_RESULTTIME + ", "
				+ KeywordsDictionary.PARTICIPATION_PARTICIPATION_NUMBER + ", "
				+ KeywordsDictionary.PARTICIPATION_FEE_PAID + ", "
				+ KeywordsDictionary.PARTICIPATION_CERTIFICATION_HANDEDOVER + ", "
				+ KeywordsDictionary.PARTICIPATION_NONCOMPETITIVE + ", "
				+ KeywordsDictionary.PARTICIPATION_RELATED_COMPETITION_ID + ", "
				+ KeywordsDictionary.PARTICIPATION_RELATED_CONTACT_ID + ", "
				+ KeywordsDictionary.PARTICIPATION_RELATED_DISCIPLIN_ID + ", "
				+ KeywordsDictionary.PARTICIPATION_RANK + ", "
				+ KeywordsDictionary.PARTICIPATION_AGECLASS_RANK + ", "
				+ KeywordsDictionary.PARTICIPATION_GENDER_RANK + ", "
				+ KeywordsDictionary.PARTICIPATION_GENDER_AGECLASS_RANK + ", "
				+ KeywordsDictionary.PARTICIPATION_COMMENT + ", "
				+ KeywordsDictionary.PARTICIPATION_DONATION_HOSPIZ + ", "
				+ KeywordsDictionary.PARTICIPATION_REGISTERED_ONLINE + ", "
				+ KeywordsDictionary.MODEL_META_ATTRIBUTES
				+ KeywordsDictionary.SQL_FROM
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.PARTICIPATION;
	}
	
	public String getSqlToCountByDisciplineId(int disciplineId, int competitionId) {
		return KeywordsDictionary.SQL_SELECT
				+ KeywordsDictionary.SQL_COUNT_ALL
				+ KeywordsDictionary.SQL_FROM
				+ KeywordsDictionary.DATABASE_SCHEME + "." + KeywordsDictionary.PARTICIPATION
				+ KeywordsDictionary.SQL_WHERE
				+ KeywordsDictionary.PARTICIPATION_RELATED_DISCIPLIN_ID + "="
				+ Integer.toString(disciplineId)
				+ KeywordsDictionary.SQL_AND
				+ KeywordsDictionary.PARTICIPATION_RELATED_COMPETITION_ID + "="
				+ Integer.toString(competitionId);
	}
	
	public String getSqlToLoadByCompetitionId(int competitionId) {
		String whereClause = KeywordsDictionary.PARTICIPATION_RELATED_COMPETITION_ID + "=" 
				+ Integer.toString(competitionId);
		return getSqlToLoadModels(whereClause);
	}
	
	public String getSqlToLoadByContactId(int contactId) {
		String whereClause = KeywordsDictionary.PARTICIPATION_RELATED_CONTACT_ID + "=" 
				+ Integer.toString(contactId);
		return getSqlToLoadModels(whereClause);
	}
	
	public String getSqlToLoadByDisciplineId(int disciplineId) {
		String whereClause = KeywordsDictionary.PARTICIPATION_RELATED_DISCIPLIN_ID + "="
				+ Integer.toString(disciplineId);
		return getSqlToLoadModels(whereClause);
	}
	
	public String getSqlToLoadByParticipationId(int participationId) {
		String whereClause = KeywordsDictionary.MODEL_ID + "="
				+ Integer.toString(participationId);
		return getSqlToLoadModels(whereClause);
	}
	
	public String getSqlToLoadByCompetitionIdAndContactId(int competitionId, int contactId) {
		String whereClause = "";
		if (competitionId != 0) {
			whereClause += KeywordsDictionary.PARTICIPATION_RELATED_COMPETITION_ID + "="
					+ Integer.toString(competitionId); 
		}
		if (competitionId != 0 && contactId != 0) {
			whereClause += KeywordsDictionary.SQL_AND; 
		}
		if (contactId != 0) {
			whereClause += KeywordsDictionary.PARTICIPATION_RELATED_CONTACT_ID + "=" 
					+ Integer.toString(contactId); 
		}
		return getSqlToLoadModels(whereClause);
	}
	
	public String getSqlToLoadByCompetitionIdAndContactIdAndDisciplineId(int competitionId, int contactId, int disciplineId) {
		String whereClause = "";
		if (competitionId != 0) {
			whereClause += KeywordsDictionary.PARTICIPATION_RELATED_COMPETITION_ID + "="
					+ Integer.toString(competitionId); 
		}
		if (competitionId != 0 && contactId != 0) {
			whereClause += KeywordsDictionary.SQL_AND; 
		}
		if (contactId != 0) {
			whereClause += KeywordsDictionary.PARTICIPATION_RELATED_CONTACT_ID + "=" 
					+ Integer.toString(contactId); 
		}
		if ((competitionId != 0 || contactId != 0) && disciplineId != 0) {
			whereClause += KeywordsDictionary.SQL_AND;
			whereClause += KeywordsDictionary.PARTICIPATION_RELATED_DISCIPLIN_ID + "="
					+ Integer.toString(disciplineId);
		}
		return getSqlToLoadModels(whereClause);
	}
	
	public String getSqlToLoadByCompetitionIdAndContactIdAndDisciplineIds(int competitionId, int contactId, int[] disciplineIds) {
		String whereClause = "";
		if (competitionId != 0) {
			whereClause += KeywordsDictionary.PARTICIPATION_RELATED_COMPETITION_ID + "="
					+ Integer.toString(competitionId); 
		}
		if (competitionId != 0 && contactId != 0) {
			whereClause += KeywordsDictionary.SQL_AND; 
		}
		if (contactId != 0) {
			whereClause += KeywordsDictionary.PARTICIPATION_RELATED_CONTACT_ID + "=" 
					+ Integer.toString(contactId); 
		}
		if ((competitionId != 0 || contactId != 0) && disciplineIds != null && disciplineIds.length > 0) {
			whereClause += KeywordsDictionary.SQL_AND + "(";
			boolean first = true;
			for (int dNo = 0; dNo < disciplineIds.length; dNo++) {
				if (!first) {
					whereClause += KeywordsDictionary.SQL_OR;
				} else {
					first = false;
				}
				whereClause += KeywordsDictionary.PARTICIPATION_RELATED_DISCIPLIN_ID + "="
						+ Integer.toString(disciplineIds[dNo]);
			}
			whereClause += ")";
		}
		return getSqlToLoadModels(whereClause);
	}

	@Override
	public Participation[] mapResultSetToModels(ResultSet resultSet)
			throws SQLException {
		Participation[] participations = null;
		if (resultSet != null) {
			//create array
			resultSet.last();
			participations = new Participation[resultSet.getRow()];
			resultSet.beforeFirst();
			//loop resultSet
			int participationCount = 0;
			while (resultSet.next()) {
				Participation p = new Participation();
				p.setId(resultSet.getInt(1));
				p.setResultTimeAsString(resultSet.getString(2));
				p.setParticipationNumber(resultSet.getString(3));
				p.setPaymentDone(resultSet.getInt(4));
				p.setCertificationHandedOver(resultSet.getInt(5));
				p.setNoncompetitive(resultSet.getInt(6));
				p.setCompetitionId(resultSet.getInt(7));
				p.setContactId(resultSet.getInt(8));
				p.setDisciplineId(resultSet.getInt(9));
				p.setRank(resultSet.getInt(10));
				p.setAgeClassRank(resultSet.getInt(11));
				p.setGenderRank(resultSet.getInt(12));
				p.setGenderAgeClassRank(resultSet.getInt(13));
				p.setComment(resultSet.getString(14));
				p.setDonationHospizInEuroCent(resultSet.getInt(15));
				p.setRegisteredOnline(resultSet.getInt(16));
				KeywordsDictionary.setMODEL_META_ATTRIBUTES(p, resultSet, 17);
				participations[participationCount] = p;
				participationCount++;
			}
		}
		return participations;
	}

	@Override
	public String getSqlForFullTextSearch(String searchString) {
		String searchIntegerString = Utils.parseIntegerFromStringAsString(searchString);
		String whereClause =
				KeywordsDictionary.SQL_UPPER(KeywordsDictionary.PARTICIPATION_PARTICIPATION_NUMBER) + KeywordsDictionary.SQL_LIKE_UPPER(searchString) + KeywordsDictionary.SQL_OR
				+ KeywordsDictionary.PARTICIPATION_RANK + "=" + searchIntegerString + KeywordsDictionary.SQL_OR + KeywordsDictionary.PARTICIPATION_AGECLASS_RANK + "=" + searchIntegerString;
		return getSqlToLoadModels(whereClause);
	}

}
