package org.y3.jrun.control;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.y3.jrun.model.Model;
import org.y3.jrun.storage.database.DatabaseSession;

public class KeywordsDictionary {

	//COMMON DATABASE
	public static final String DATABASE_HOME 						= System.getProperty("user.home", ".");
	public static final String DATABASE_SCHEME 						= "y3jrun";
	public static final String DATABASE_USERNAME 					= null;
	public static final String DATABASE_PASSWORD 					= null;
	//DERBY DATABASE
	public static final String DATABASE_DERBY_DRIVER 				= "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String DATABASE_DERBY_HOME_PROPERTY 		= "derby.system.home";
	public static final String DATABASE_DERBY_CONNECTION_BRIDGE 	= "jdbc:derby:";
	public static final String DATABASE_DERBY_PARAM_CREATE_DATABASE = "create=true";
	public static final String DATABASE_DERBY_PARAM_SCHEMA_EXISTS	= "ifexists=true";
	
	//SQL_COMMON
	public static final String SQL_DROP_TABLE						= "DROP TABLE ";
	public static final String SQL_DROP_SCHEMA						= "DROP SCHEMA ";
	public static final String SQL_RESTRICT							= " RESTRICT";
	public static final String SQL_CREATE_TABLE						= "CREATE TABLE ";
	public static final String SQL_COUNT_ALL						= " COUNT(*) ";
	public static final String SQL_UPDATE							= "UPDATE ";
	public static final String SQL_SET								= " SET ";
	public static final String SQL_WHERE_ID_IS						= " WHERE ID=";
	public static final String SQL_INSERT_INTO						= "INSERT INTO ";
	public static final String SQL_VALUES							= ") VALUES ( ";
	public static final String SQL_DELETE_FROM						= "DELETE FROM ";
	public static final String SQL_SELECT							= "SELECT ";
	public static final String SQL_FROM								= " FROM ";
	public static final String SQL_WHERE							= " WHERE ";
	public static final String SQL_AND								= " AND ";
	public static final String SQL_OR								= " OR ";
	public static final String SQL_SUM (String summaryColumn) {
		return " SUM(" + summaryColumn + ") ";
	}
	public static final String SQL_LIKE (String likeValue) {
		return " LIKE '%" + likeValue + "%' ";
	}
	public static final String SQL_LIKE_UPPER (String likeValueToUpper) {
		return " LIKE UPPER('%"+ likeValueToUpper + "%') ";
	}
	public static final String SQL_UPPER (String valueToUpper) {
		return " UPPER(" + valueToUpper + ") ";
	}
	public static final String SQL_SELECT_COUNT_ALL_FROM			= "SELECT COUNT(*) FROM ";
	public static final String SQL_ID_INTEGER_PRIMARY_KEY_NOT_NULL	= "ID INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)";
	public static final String SQL_VARCHAR_6						= " VARCHAR(6)";
	public static final String SQL_VARCHAR_50						= " VARCHAR(50)";
	public static final String SQL_VARCHAR_100						= " VARCHAR(100)";
	public static final String SQL_VARCHAR_500						= " VARCHAR(500)";
	public static final String SQL_VARCHAR_10000					= " VARCHAR(10000)";
	public static final String SQL_INTEGER							= " INTEGER";
	public static final String SQL_TIMESTAMP						= " TIMESTAMP";
	public static final String SQL_SMALLINT							= " SMALLINT";
	
	//MODEL_GENERIC
	public static final String MODEL_ID								= "ID";
	public static final String MODEL_IMPORT_FINGERPRINT				= "IMPORT_FINGERPRINT";
	public static final String MODEL_CREATIONDATE					= "CREATIONDATE";
	public static final String MODEL_CREATOR						= "CREATOR";
	public static final String MODEL_CHANGEDATE						= "CHANGEDATE";
	public static final String MODEL_CHANGER						= "CHANGER";
	public static final String MODEL_VERSION						= "VERSION";
	public static final String TOSTRING								= "TOSTRING";
	public static final String MODEL_META_ATTRIBUTES_DEFINITION
			= KeywordsDictionary.MODEL_IMPORT_FINGERPRINT + KeywordsDictionary.SQL_VARCHAR_500 + ","
			+ KeywordsDictionary.MODEL_CREATIONDATE + KeywordsDictionary.SQL_TIMESTAMP + ","
			+ KeywordsDictionary.MODEL_CREATOR + KeywordsDictionary.SQL_VARCHAR_100 + ","
			+ KeywordsDictionary.MODEL_CHANGEDATE + KeywordsDictionary.SQL_TIMESTAMP + ","
			+ KeywordsDictionary.MODEL_CHANGER + KeywordsDictionary.SQL_VARCHAR_100 + ","
			+ KeywordsDictionary.MODEL_VERSION + KeywordsDictionary.SQL_INTEGER;
	public static final String MODEL_META_ATTRIBUTES
			= KeywordsDictionary.MODEL_IMPORT_FINGERPRINT + ", "
			+ KeywordsDictionary.MODEL_CREATIONDATE + ", "
			+ KeywordsDictionary.MODEL_CREATOR + ", "
			+ KeywordsDictionary.MODEL_CHANGEDATE + ", "
			+ KeywordsDictionary.MODEL_CHANGER + ", "
			+ KeywordsDictionary.MODEL_VERSION;
	public static final String getMODEL_META_ATTRIBUTES_FILLED(Model model) {
		return	KeywordsDictionary.MODEL_IMPORT_FINGERPRINT	+ "='" + model.getImportFingerPrint() + "', "
				+ KeywordsDictionary.MODEL_CREATIONDATE		+ "='" + DatabaseSession.dateToTimestampString(model.getCreationDate())	+ "', "
				+ KeywordsDictionary.MODEL_CREATOR			+ "='" + model.getCreatorName()	+ "', "
				+ KeywordsDictionary.MODEL_CHANGEDATE		+ "='" + DatabaseSession.dateToTimestampString(model.getChangeDate()) + "', "
				+ KeywordsDictionary.MODEL_CHANGER			+ "='" + model.getChangerName()	+ "', "
				+ KeywordsDictionary.MODEL_VERSION			+ "="  + model.getVersion();
	}
	public static final String getMODEL_META_ATTRIBUTE_VALUES(Model model) {
		return	model.getImportFingerPrint() + "', '"
				+ DatabaseSession.dateToTimestampString(model.getCreationDate()) + "', '"
				+ model.getCreatorName() + "', '"
				+ DatabaseSession.dateToTimestampString(model.getChangeDate()) + "', '"
				+ model.getChangerName() + "', "
				+ model.getVersion();
	}
	public static void setMODEL_META_ATTRIBUTES(Model model, ResultSet resultSet, int startPosition) throws SQLException {
		model.setImportFingerPrint(resultSet.getString(startPosition));
		model.setCreationDate(resultSet.getTimestamp(startPosition+1));
		model.setCreatorName(resultSet.getString(startPosition+2));
		model.setChangeDate(resultSet.getTimestamp(startPosition+3));
		model.setChangerName(resultSet.getString(startPosition+4));
		model.setVersion(resultSet.getInt(startPosition+5));
	}
	
	//MODEL_CONTACT	
	public static final String CONTACT								= "CONTACT";
	public static final String CONTACT_FIRSTNAME 					= "FIRSTNAME";
	public static final String CONTACT_LASTNAME 					= "LASTNAME";
	public static final String CONTACT_CALLNAME 					= "CALLNAME";
	public static final String CONTACT_ADDRESS1 					= "ADDRESS1";
	public static final String CONTACT_ADDRESS2 					= "ADDRESS2";
	public static final String CONTACT_POSTAL 						= "POSTAL";
	public static final String CONTACT_CITY 						= "CITY";
	public static final String CONTACT_BIRTHYEAR 					= "BIRTHYEAR";
	public static final String CONTACT_GENDER 						= "GENDER";
	public static final String CONTACT_GENDER_RESOURCE				= "GENDER_RESOURCE";
	public static final String CONTACT_PHONENUMBER 					= "PHONENUMBER";
	public static final String CONTACT_MOBILENUMBER 				= "MOBILENUMBER";
	public static final String CONTACT_COMMENTS 					= "COMMENTS";
	public static final String CONTACT_EMAIL 						= "EMAIL";
	public static final String CONTACT_FULLNAME						= "FULLNAME";
	
	//MODEL_NOTIFICATION
	public static final String NOTIFICATION							= "NOTIFICATION";
	public static final String NOTIFICATION_TITLE					= "TITLE";
	public static final String NOTIFICATION_TYPE					= "NOTIFICATIONTYPE";
	public static final String NOTIFICATION_MESSAGE					= "MESSAGE";
	public static final String NOTIFICATION_EXCEPTIONSTACKTRACE		= "EXCEPTIONSTACKTRACE";
	public static final String NOTIFICATION_RELATEDOBJECT_ID		= "RELATEDOBJECTID";
	public static final String NOTIFICATION_RELATEDOBJECT_TYPE		= "RELATEDOBJECTTYPE";
	
	//MODEL_COMPETITION
	public static final String COMPETITION							= "COMPETITION";
	public static final String COMPETITION_TITLE					= "TITLE";
	public static final String COMPETITION_WEB_PAGE					= "WEB_PAGE";
	public static final String COMPETITION_REGISTRATION_PAGE		= "REGISTRATION_PAGE";
	public static final String COMPETITION_REGISTRATIONS_LOG_FILE	= "REGISTRATIONS_LOG_FILE";
	public static final String COMPETITION_STARTOFCOMPETITION		= "STARTOFCOMPETITION";
	public static final String COMPETITION_ENDOFCOMPETITION			= "ENDOFCOMPETITION";
	public static final String COMPETITION_RELATED_AGECLASSESDEFINITION_ID = "RELATEDAGECLASSESDEFINITION_ID";
	
	//MODEL_PARTICIPATION
	public static final String PARTICIPATION						= "PARTICIPATION";
	public static final String PARTICIPATION_RELATED_COMPETITION_ID = "RELATEDCOMPETITIONID";
	public static final String PARTICIPATION_RELATED_CONTACT_ID		= "RELATEDCONTACTID";
	public static final String PARTICIPATION_RELATED_DISCIPLIN_ID   = "RELATEDDISCIPLINEID";
	public static final String PARTICIPATION_RANK					= "RANK";
	public static final String PARTICIPATION_AGECLASS_RANK			= "PARTICIPATION_AGECLASS_RANK";
	public static final String PARTICIPATION_GENDER_RANK			= "PARTICIPATION_GENDER_RANK";
	public static final String PARTICIPATION_GENDER_AGECLASS_RANK	= "PARTICIPATION_GENDER_AGECLASS_RANK";
	public static final String PARTICIPATION_RESULTTIME				= "RESULTTIME";
	public static final String PARTICIPATION_PARTICIPATION_NUMBER   = "PARTICIPATION_PARTICIPATION_NUMBER";
	public static final String PARTICIPATION_FEE_PAID				= "FEE_PAID";
	public static final String PARTICIPATION_CERTIFICATION_HANDEDOVER = "CERTIFICATION_HANDEDOVER";
	public static final String PARTICIPATION_NONCOMPETITIVE			= "NONCOMPETITIVE";
	public static final String PARTICIPATION_COMMENT					= "PARTICIPATION_COMMENT";
	public static final String PARTICIPATION_DONATION_HOSPIZ			= "PARTICIPATION_DONATION_HOSPIZ";
	public static final String PARTICIPATION_REGISTERED_ONLINE			= "PARTICIPATION_REGISTERED_ONLINE";
	
	//MODEL_DISCIPLINE
	public static final String DISCIPLINE							= "DISCIPLINE";
	public static final String DISCIPLINE_NAME						= "NAME";
	public static final String DISCIPLINE_LENGTH					= "LENGTH";
	public static final String DISCIPLINE_DISTANCE					= "DISTANCE";
	public static final String DISCIPLINE_START						= "START";
	public static final String DISCIPLINE_TIMEFORMAT				= "DISCIPLINE_TIMEFORMAT";
	public static final String DISCIPLINE_PRICE_IN_EUROCENT			= "PRICE_IN_EUROCENT";
	
	//REPORTS
	public static final String TITLE								= "TITLE";
	
	//MODEL_AGECLASS
	public static final String AGECLASS								= "AGECLASS";
	public static final String AGECLASS_TITLE						= "AGECLASS_TITLE";
	public static final String AGECLASS_YEAR_FROM					= "AGECLASS_YEAR_FROM";
	public static final String AGECLASS_YEAR_TO						= "AGECLASS_YEAR_TO";
	public static final String AGECLASS_BELONGS_TO_AGECLASSESDEFINITON = "AGECLASSESDEFINITION";
	
	//_MODEL_AGECLASSESDEFINITION
	public static final String AGECLASSESDEFINITION					= "AGECLASSESDEFINITION";
	public static final String AGECLASSESDEFINITION_NAME			= "NAME";
	
	//REGISTRATIONS XML
	public static final String REGISTRATIONS_XML_SUBMISSION			= "submission";
	public static final String REGISTRATIONS_XML_DATE				= "date";
	public static final String REGISTRATIONS_XML_DATEFORMAT			= "yyyy-MM-dd";
	public static final String REGISTRATIONS_XML_TIME				= "time";
	public static final String REGISTRATIONS_XML_TIMEFORMAT			= "hh:mm:ss";
	public static final String REGISTRATIONS_XML_FIELD				= "field";
	public static final String REGISTRATIONS_XML_FIELD_NAME			= "name";
	public static final String REGISTRATIONS_XML_PARTICIPANT		= "Teilnehmer";
	public static final String REGISTRATIONS_XML_ADDRESS			= "Adresse";
	public static final String REGISTRATIONS_XML_EMAIL				= "EMail";
	public static final String REGISTRATIONS_XML_PHONE				= "Telefonnummer";
	public static final String REGISTRATIONS_XML_BIRTHYEAR			= "Jahrgang";
	public static final String REGISTRATIONS_XML_GENDER				= "Geschlecht";
	public static final String REGISTRATIONS_XML_GENDER_M			= "männlich";
	public static final String REGISTRATIONS_XML_GENDER_W			= "weiblich";
	public static final String REGISTRATIONS_XML_DISCIPLINE			= "Sportart";
	public static final String REGISTRATIONS_XML_LENGTH_AND_FEE		= "StreckeUndStartgeld";
	public static final String REGISTRATIONS_XML_DONATION_HOSPIZ	= "SpendeHospiz";
	public static final String REGISTRATIONS_XML_ANNOTATIONS		= "Bemerkungen";
}
