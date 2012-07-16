package org.y3.jrun.storage.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.y3.jrun.control.KeywordsDictionary;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinition;
import org.y3.jrun.model.competition.Competition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.model.notification.Notification;
import org.y3.jrun.model.participation.Participation;

/**
 * @author ryppo
 * 
 */
public class DatabaseSession {

	private boolean debug = false;
	
	private String connectionString;
	private Connection connection;
	private Statement derby;
	
	private DBHandler_Contact CONTACT_HANDLER = new DBHandler_Contact();
	private DBHandler_Notification NOTIFICATION_HANDLER = new DBHandler_Notification();
	private DBHandler_Competition COMPETITION_HANDLER = new DBHandler_Competition();
	private DBHandler_Participation PARTICIPATION_HANDLER = new DBHandler_Participation();
	private DBHandler_Discipline DISCIPLINE_HANDLER = new DBHandler_Discipline();
	private DBHandler_AgeClass AGECLASS_HANDLER = new DBHandler_AgeClass();
	private DBHandler_AgeClassesDefinition AGECLASSESDEFINITION_HANDLER = new DBHandler_AgeClassesDefinition();
	private DBModelHandler[] modelHandlers = new DBModelHandler[]{
		CONTACT_HANDLER, NOTIFICATION_HANDLER, COMPETITION_HANDLER, 
		PARTICIPATION_HANDLER, DISCIPLINE_HANDLER, AGECLASS_HANDLER, AGECLASSESDEFINITION_HANDLER };
	
	private boolean noDatabase = false; 

	private DBModelHandler getModelHandlerForModel(Model model) {
		DBModelHandler modelHandler = null;
		if (model instanceof Contact) {
			modelHandler = CONTACT_HANDLER;
		} else if (model instanceof Notification) {
			modelHandler = NOTIFICATION_HANDLER;
		} else if (model instanceof Competition) {
			modelHandler = COMPETITION_HANDLER;
		} else if (model instanceof Participation) {
			modelHandler = PARTICIPATION_HANDLER;
		} else if (model instanceof Discipline) {
			modelHandler = DISCIPLINE_HANDLER;
		} else if (model instanceof AgeClass) {
			modelHandler = AGECLASS_HANDLER;
		} else if (model instanceof AgeClassesDefinition) {
			modelHandler = AGECLASSESDEFINITION_HANDLER;
		}
		return modelHandler;
	}
	
	public DatabaseSession() throws IOException, ClassNotFoundException {
		init();
	}

	public DatabaseSession(boolean _debug) throws IOException,
			ClassNotFoundException {
		debug = _debug;
		init();
	}

	private void init() throws IOException, ClassNotFoundException {
		// system property for database
		System.setProperty(KeywordsDictionary.DATABASE_DERBY_HOME_PROPERTY, 
				KeywordsDictionary.DATABASE_HOME + "/." + KeywordsDictionary.DATABASE_SCHEME);
		// load database driver
		Class.forName(KeywordsDictionary.DATABASE_DERBY_DRIVER);
		//set connection string
		connectionString = KeywordsDictionary.DATABASE_DERBY_CONNECTION_BRIDGE + KeywordsDictionary.DATABASE_SCHEME
				+ ";user=" + KeywordsDictionary.DATABASE_USERNAME + ";password=" + KeywordsDictionary.DATABASE_PASSWORD;
		System.out.println("Connection: " + connectionString);

	}

	public boolean connect() throws SQLException {
		if (connection == null) {
			connection = DriverManager.getConnection(connectionString + ";"
					+ KeywordsDictionary.DATABASE_DERBY_PARAM_SCHEMA_EXISTS);
			derby = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			// check schema exists
			DatabaseMetaData dmd = connection.getMetaData();
			ResultSet schemas = dmd.getSchemas();
			while (schemas.next()) {
				String actualSchema = schemas.getString(1);
				if (actualSchema.toUpperCase().equals(KeywordsDictionary.DATABASE_SCHEME.toUpperCase())) {
					return true;
				}
			}
			disconnect();
			return false;
		}
		return true;
	}

	public boolean removeDatabase() throws SQLException {
		if (connection == null) {
			connect();
		}
		boolean removed = false;
		
		// remove tables
		int successCount = 0;
		for (int tabId = 0; tabId < modelHandlers.length; tabId++) {
			if (sqlUpdate(modelHandlers[tabId].getSqlToDropModelTable(), false) == 0) {
				successCount++;
			}
		}
		if (successCount == modelHandlers.length) {
			removed = true;
		}
		
		// removed schema
		if (removed) {
			String sql = KeywordsDictionary.SQL_DROP_SCHEMA
					+ KeywordsDictionary.DATABASE_SCHEME
					+ KeywordsDictionary.SQL_RESTRICT;
			if (sqlUpdate(sql, true) == 0) {
				removed = true;
				noDatabase = true;
			}
		}
		return removed;
	}

	public boolean disconnect() throws SQLException {
		if (derby != null) {
			derby.close();
			derby = null;
		}
		if (connection != null) {
			connection.close();
			connection = null;
		}
		return true;
	}

	public boolean createDatabase() throws SQLException {
		boolean created = false;
		// create database
		if (connection != null) {
			connection.close();
		} else {
			connection = DriverManager.getConnection(connectionString + ";"
					+ KeywordsDictionary.DATABASE_DERBY_PARAM_CREATE_DATABASE);
			derby = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		// create tables
		int successCount = 0;
		for (int tabId = 0; tabId < modelHandlers.length; tabId++) {
			if (sqlUpdate(modelHandlers[tabId].getSqlToCreateModelTable(), false) == 0) {
				successCount++;
			}
		}
		if (successCount == modelHandlers.length) {
			created = true;
			noDatabase = false;
		}
		// return success
		return created;
	}
	
	public String getNameOfModel(Model model) {
		if (model instanceof Contact) {
			return KeywordsDictionary.CONTACT;
		} else if (model instanceof Notification) {
			return KeywordsDictionary.NOTIFICATION;
		} else if (model instanceof Competition) {
			return KeywordsDictionary.COMPETITION;
		} else if (model instanceof Discipline) {
			return KeywordsDictionary.DISCIPLINE;
		} else if (model instanceof Participation) {
			return KeywordsDictionary.PARTICIPATION;
		} else if (model instanceof AgeClass) {
			return KeywordsDictionary.AGECLASS;
		} else if (model instanceof AgeClassesDefinition) {
			return KeywordsDictionary.AGECLASSESDEFINITION;
		} else return null;
	}
	
	public boolean deleteModel(Model model) throws SQLException {
		if (model != null) {
			return deleteModel(getNameOfModel(model), model.getId());
		} else {
			return false;
		}
	}
	
	private boolean deleteModel(String table, int id) throws SQLException {
		boolean deleted = false;
		if (table != null && id != 0) {
			if (sqlUpdate(KeywordsDictionary.SQL_DELETE_FROM + KeywordsDictionary.DATABASE_SCHEME + "." + table
					+ KeywordsDictionary.SQL_WHERE_ID_IS
					+ id, false) == 1) {
				deleted = true;
			}
		}
		return deleted;
	}

	public boolean saveModel(Model model) throws SQLException {
		boolean saved = false;
		boolean modeINSERT = false;
		if (model != null) {
			String sql = null;
			// insert or update
			if (model.getId() != 0) {
				// update
				sql = getModelHandlerForModel(model).getSqlToUpdateModel(model);
				if (sqlUpdate(sql, false) == 1) {
					saved = true;
				}
			} else {
				// insert
				modeINSERT = true;
				sql = getModelHandlerForModel(model).getSqlToInsertModel(model);
				if (sqlInsert(sql) == 1) {
					saved = true;
				}
			}
			// save to database
			if (saved == true && modeINSERT) {
				ResultSet results = derby.getGeneratedKeys();
				if (results != null) {
					results.next();
					model.setId(results.getInt(1));
				}
			}
		}
		return saved;
	}

	public Contact[] getContactsById(int[] contactIds) throws SQLException {
		return getContacts(contactIds);
	}
	
	public Competition[] getCompetitionsById(int[] competitionIds) throws SQLException {
		return getCompetitions(competitionIds);
	}
	
	public int[] getCompetitionIdsByAgeClassesDefinitionIds(int[] ageClassesDefinitionIds) throws SQLException {
		int[] compIds = null;
		if (ageClassesDefinitionIds != null && derby != null) {
			String sql = COMPETITION_HANDLER.getSqlToGetCompetitionIdsByAgeClassesDefinitionIds(ageClassesDefinitionIds);
			ResultSet resultIds = sqlSelect(sql);
			if (resultIds != null) {
				resultIds.last();
				compIds = new int[resultIds.getRow()];
				resultIds.beforeFirst();
				int compPos = 0;
				while (resultIds.next()) {
					compIds[compPos] = resultIds.getInt(1);
					compPos++;
				}
			}
		}
		return compIds;
	}
	
	public Contact[] getAllContacts() throws SQLException {
		return getContacts(null);
	}
	
	public Model[] searchModel(String fullTextSearchString, Model exampleModel) throws SQLException {
		Model[] models = null;
		DBModelHandler modelHandler = getModelHandlerForModel(exampleModel);
		if (derby != null && modelHandler != null) {
			String sql = modelHandler.getSqlForFullTextSearch(fullTextSearchString);
			ResultSet derbyModels = sqlSelect(sql);
			models = modelHandler.mapResultSetToModels(derbyModels);
		}
		return models;
	}
	
	private Contact[] getContacts(int[] contactIds) throws SQLException {
		Contact[] contacts = null;
		if (derby != null) {
			String sql = CONTACT_HANDLER.getSqlToLoadModelsByIDs(contactIds);
			ResultSet derbyContacts = sqlSelect(sql);
			contacts = CONTACT_HANDLER.mapResultSetToModels(derbyContacts);
		}
		return contacts;
	}
	
	private Competition[] getCompetitions(int[] competitionIds) throws SQLException {
		Competition[] competitions = null;
		if (derby != null) {
			String sql = COMPETITION_HANDLER.getSqlToLoadModelsByIDs(competitionIds);
			ResultSet derbyCompetitions = sqlSelect(sql);
			competitions = COMPETITION_HANDLER.mapResultSetToModels(derbyCompetitions);
		}
		return competitions;
	}
	
	public Contact[] getContactsByFingerPrint(String fingerPrint) throws SQLException {
		Contact[] contacts = null;
		if (derby != null) {
			String sql = CONTACT_HANDLER.getSqlToLoadModelsByFingerPrint(fingerPrint);
			ResultSet derbyContacts = sqlSelect(sql);
			contacts = CONTACT_HANDLER.mapResultSetToModels(derbyContacts);
		}
		return contacts;
	}
	
	public Discipline[] getDisciplinesByFingerPrint(String fingerPrint) throws SQLException {
		Discipline[] disciplines = null;
		if (derby != null) {
			String sql = DISCIPLINE_HANDLER.getSqlToLoadModelsByFingerPrint(fingerPrint);
			ResultSet derbyDiscliplines = sqlSelect(sql);
			disciplines = DISCIPLINE_HANDLER.mapResultSetToModels(derbyDiscliplines);
		}
		return disciplines;
	}
	
	public Notification[] getAllNotifications() throws SQLException {
		return getAllNotifications(null);
	}
	
	public Notification[] getAllNotificationsForModel(Model model) throws SQLException {
		String whereClause = null;
		if (model != null) {
			whereClause = ""
					+ KeywordsDictionary.NOTIFICATION_RELATEDOBJECT_ID + "=" + model.getId() + " "
					+ KeywordsDictionary.SQL_AND
					+ KeywordsDictionary.NOTIFICATION_RELATEDOBJECT_TYPE + "='" + model.getClass().getSimpleName() + "'";
		}
		return getAllNotifications(whereClause);
	}
	
	private Notification[] getAllNotifications(String whereClause) throws SQLException {
		Notification[] notifications = null;
		if (derby != null) {
			String sql = NOTIFICATION_HANDLER.getSqlToLoadAllModels();
			if (whereClause != null) {
				sql += KeywordsDictionary.SQL_WHERE + whereClause;
			}
			ResultSet derbyNotifications = sqlSelect(sql);
			notifications = NOTIFICATION_HANDLER.mapResultSetToModels(derbyNotifications);
		}
		return notifications;
	}
	
	public Participation[] getAllParticipations() throws SQLException {
		Participation[] participations = null;
		if (derby != null) {
			String sql = PARTICIPATION_HANDLER.getSqlToLoadAllModels();
			ResultSet derbyNotifications = sqlSelect(sql);
			participations = PARTICIPATION_HANDLER.mapResultSetToModels(derbyNotifications);
		}
		return participations;
	}
	
	public Participation[] getAllParticipationsByCompetitionId(int competitionId) throws SQLException {
		Participation[] participations = null;
		if (derby != null) {
			String sql = PARTICIPATION_HANDLER.getSqlToLoadByCompetitionId(competitionId);
			ResultSet derbyNotifications = sqlSelect(sql);
			participations = PARTICIPATION_HANDLER.mapResultSetToModels(derbyNotifications);
		}
		return participations;
	}
	
	public Participation[] getAllParticipationsByContactId(int contactId) throws SQLException {
		Participation[] participations = null;
		if (derby != null) {
			String sql = PARTICIPATION_HANDLER.getSqlToLoadByContactId(contactId);
			ResultSet derbyNotifications = sqlSelect(sql);
			participations = PARTICIPATION_HANDLER.mapResultSetToModels(derbyNotifications);
		}
		return participations;
	}
	
	public Participation[] getAllParticipationsByDisciplineId(int disciplineId) throws SQLException {
		Participation[] participations = null;
		if (derby != null) {
			String sql = PARTICIPATION_HANDLER.getSqlToLoadByDisciplineId(disciplineId);
			ResultSet derbyNotifications = sqlSelect(sql);
			participations = PARTICIPATION_HANDLER.mapResultSetToModels(derbyNotifications);
		}
		return participations;
	}
	
	public Participation[] getParticipationsById(int participationId) throws SQLException {
		Participation[] participations = null;
		if (derby != null) {
			String sql = PARTICIPATION_HANDLER.getSqlToLoadByParticipationId(participationId);
			ResultSet derbyNotifications = sqlSelect(sql);
			participations = PARTICIPATION_HANDLER.mapResultSetToModels(derbyNotifications);
		}
		return participations;
	}
	
	public Participation getParticipationByImportFingerPrint(String importFingerPrint) throws SQLException {
		Participation participation = null;
		if (derby != null) {
			String sql = PARTICIPATION_HANDLER.getSqlToLoadModelsByFingerPrint(importFingerPrint);
			ResultSet derbyParticipations = sqlSelect(sql);
			Participation[] participations = PARTICIPATION_HANDLER.mapResultSetToModels(derbyParticipations);
			if (participations != null && participations.length > 0) {
				participation = participations[0];
			}
		}
		return participation;
	}
	
	public Participation[] getAllParticipationsByCompetitionIdAndContactId(int competitionId, int contactId) throws SQLException {
		Participation[] participations = null;
		if (derby != null) {
			String sql = PARTICIPATION_HANDLER.getSqlToLoadByCompetitionIdAndContactId(competitionId, contactId);
			ResultSet derbyNotifications = sqlSelect(sql);
			participations = PARTICIPATION_HANDLER.mapResultSetToModels(derbyNotifications);
		}
		return participations;
	}
	
	public Participation[] getAllParticipationsByCompetitionIdAndContactIdAndDisciplineId(int competitionId, int contactId, int disciplineId) throws SQLException {
		Participation[] participations = null;
		if (derby != null) {
			String sql = PARTICIPATION_HANDLER.getSqlToLoadByCompetitionIdAndContactIdAndDisciplineId(competitionId, contactId, disciplineId);
			ResultSet derbyNotifications = sqlSelect(sql);
			participations = PARTICIPATION_HANDLER.mapResultSetToModels(derbyNotifications);
		}
		return participations;
	}
	
	public Participation[] getAllParticipationsByCompetitionIdAndContactIdAndDisciplineIds(int competitionId, int contactId, int[] disciplineIds) throws SQLException {
		Participation[] participations = null;
		if (derby != null) {
			String sql = PARTICIPATION_HANDLER.getSqlToLoadByCompetitionIdAndContactIdAndDisciplineIds(competitionId, contactId, disciplineIds);
			ResultSet derbyNotifications = sqlSelect(sql);
			participations = PARTICIPATION_HANDLER.mapResultSetToModels(derbyNotifications);
		}
		return participations;
	}
	
	public Competition[] getAllCompetitions() throws SQLException {
		Competition[] competitions = null;
		if (derby != null) {
			String sql = COMPETITION_HANDLER.getSqlToLoadAllModels();
			ResultSet derbyCompetitions = sqlSelect(sql);
			competitions = COMPETITION_HANDLER.mapResultSetToModels(derbyCompetitions);
		}
		return competitions;
	}
	
	public Discipline[] getDisciplinesById(int[] disciplineIds) throws SQLException {
		return getDisciplines(disciplineIds);
	}
	
	public Discipline[] getDisciplinesByCompetitionId(int competitionId) throws SQLException {
		Discipline[] disciplines = null;
		if (derby != null) {
			String sql = DISCIPLINE_HANDLER.getSqlToLoadModelsByCompetitionId(competitionId);
			ResultSet derbyDisciplines = sqlSelect(sql);
			disciplines = DISCIPLINE_HANDLER.mapResultSetToModels(derbyDisciplines);
		}
		return disciplines;
	}
	
	public Discipline[] getAllDisicplines() throws SQLException {
		Discipline[] disciplines = null;
		if (derby != null) {
			String sql = DISCIPLINE_HANDLER.getSqlToLoadAllModels();
			ResultSet derbyDisciplines = sqlSelect(sql);
			disciplines = DISCIPLINE_HANDLER.mapResultSetToModels(derbyDisciplines);
		}
		return disciplines;
	}
	
	private Discipline[] getDisciplines(int[] disciplineIds) throws SQLException {
		Discipline[] disciplines = null;
		if (derby != null) {
			String sql = DISCIPLINE_HANDLER.getSqlToLoadModelsByIDs(disciplineIds);
			ResultSet derbyDisciplines = sqlSelect(sql);
			disciplines = DISCIPLINE_HANDLER.mapResultSetToModels(derbyDisciplines);
		}
		return disciplines;
	}
	
	public AgeClass[] getAllAgeClasses() throws SQLException {
		AgeClass[] ageClasses = null;
		if (derby != null) {
			String sql = AGECLASS_HANDLER.getSqlToLoadAllModels();
			ResultSet derbyAgeClasses = sqlSelect(sql);
			ageClasses = AGECLASS_HANDLER.mapResultSetToModels(derbyAgeClasses);
		}
		return ageClasses;
	}
	
	public AgeClass[] getAgeClassesByAgeClassesDefinitionId(int ageClassesDefinitionId) throws SQLException {
		AgeClass[] ageClasses = null;
		if (derby != null) {
			String sql = AGECLASS_HANDLER.getSqlToLoadModelsByAgeClassesDefinitionId(ageClassesDefinitionId);
			ResultSet derbyAgeClasses = sqlSelect(sql);
			ageClasses = AGECLASS_HANDLER.mapResultSetToModels(derbyAgeClasses);
		}
		return ageClasses;
	}
	
	public AgeClassesDefinition[] getAllAgeClassesDefinitions() throws SQLException {
		AgeClassesDefinition[] ageClassesDefinitions = null;
		if (derby != null) {
			String sql = AGECLASSESDEFINITION_HANDLER.getSqlToLoadAllModels();
			ResultSet derbyAgeClassesDefinitions = sqlSelect(sql);
			ageClassesDefinitions = AGECLASSESDEFINITION_HANDLER.mapResultSetToModels(derbyAgeClassesDefinitions);
		}
		return ageClassesDefinitions;
	}
	
	public AgeClassesDefinition getAgeClassesDefinitionByCompetition(Competition competition) throws SQLException {
		AgeClassesDefinition ageClassesDefinition = null;
		if (derby != null && competition != null) {
			String sql = AGECLASSESDEFINITION_HANDLER.getSqlToLoadById(competition.getAgeClassesDefinitionId());
			ResultSet derbyAgeClassesDefinition = sqlSelect(sql);
			AgeClassesDefinition[] ageClassesDefinitions = AGECLASSESDEFINITION_HANDLER.mapResultSetToModels(derbyAgeClassesDefinition); 
			if (ageClassesDefinitions != null && ageClassesDefinitions.length != 0) {
				ageClassesDefinition = ageClassesDefinitions[0];
			}
		}
		return ageClassesDefinition;
	}
	
	public static String dateToTimestampString(Date date) {
		String timestampString = "";
		Timestamp timestamp = null;
		if (date != null) {
			timestamp = new Timestamp(date.getTime());
			timestampString = timestamp.toString();
		} else {
			timestampString = new Timestamp(System.currentTimeMillis()).toString();
		}
		return timestampString;
	}
	
	public int sqlUpdate(String sql, boolean drop) throws SQLException {
		if (!drop) {
			debug("SQL Update : " + sql);
		}
		int updateResult = derby.executeUpdate(sql);
		if (!drop) {
			debug("[Result=" + updateResult + "]");
		}
		return updateResult;
	}

	private int sqlInsert(String sql) throws SQLException {
		if (!noDatabase) {
			debug("SQL Insert : " + sql);
			int insertResult = derby.executeUpdate(sql,
					Statement.RETURN_GENERATED_KEYS);
			debug("[Result=" + insertResult + "]");
			return insertResult;
		}
		return 0;
	}

	private ResultSet sqlSelect(String sql) throws SQLException {
		debug("SQL Select: " + sql);
		return derby.executeQuery(sql);
	}

	private void debug(String debugString) {
		if (debug) {
			System.out.println(debugString);
		}
	}
	
	public int importNotExistingModels(Model[] importModels, Model[] existingModels) throws SQLException {
		int countImported = 0;
		createMissingFingerPrints(existingModels);
		for (Model importModel: importModels) {
			boolean exists = false;
			//check exists in existing models
			for (Model existingModel: existingModels) {
				String fingerPrintIC = importModel.getImportFingerPrint();
				String fingerPrintEC = existingModel.getImportFingerPrint();
				if (fingerPrintIC.equals(fingerPrintEC)) {
					exists = true;
					break;
				}
			}
			//save it really not exists
			if (!exists) {
				saveModel(importModel);
				existingModels = (Model[]) ArrayUtils.add(existingModels, importModel);
				countImported++;
			}
		}
		return countImported;
	}
	
	private void createMissingFingerPrints(Model[] models) throws SQLException {
		for (Model model: models) {
			if (model.getImportFingerPrint() == null || model.getImportFingerPrint().length() == 0) {
				model.generateImportFingerPrint();
				saveModel(model);
			}
		}
	}

	public int countParticipationsByDisciplineIdAndCompetitionId(int disciplineId, int competitionId) throws SQLException {
		int count = 0;
		if (derby != null) {
			String sql = PARTICIPATION_HANDLER.getSqlToCountByDisciplineId(disciplineId, competitionId);
			ResultSet counted = sqlSelect(sql);
			if (counted != null) {
				counted.next();
				count = counted.getInt(1);
			}
		}
		return count;
	}
	
	public int getParticipationsHospizDonationSummaryInEuroCentByCompetitionId(int competitionId) throws SQLException {
		int donationSummaryInEuroCent = 0;
		if (derby != null) {
			String sql = PARTICIPATION_HANDLER.getSqlToGetSummaryOfHospizDonationsByCompetitionId(competitionId);
			ResultSet result = sqlSelect(sql);
			if (result != null) {
				result.next();
				donationSummaryInEuroCent = result.getInt(1);
			}
		}
		return donationSummaryInEuroCent;
	}

}
