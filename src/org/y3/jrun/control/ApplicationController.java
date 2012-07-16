/**
 * 
 */
package org.y3.jrun.control;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.y3.jrun.model.DefaultModelListModel;
import org.y3.jrun.model.Model;
import org.y3.jrun.model.ModelListModel;
import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.model.ageclass.AgeClassListModel;
import org.y3.jrun.model.ageclass.AgeClassTableModel;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinition;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinitionListModel;
import org.y3.jrun.model.ageclassesdefinition.RichAgeClassesDefinition;
import org.y3.jrun.model.competition.Competition;
import org.y3.jrun.model.competition.CompetitionListModel;
import org.y3.jrun.model.competition.RichCompetition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.contact.ContactListModel;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.model.discipline.DisciplineListModel;
import org.y3.jrun.model.notification.Notification;
import org.y3.jrun.model.notification.NotificationListModel;
import org.y3.jrun.model.participation.Participation;
import org.y3.jrun.model.participation.ParticipationListModel;
import org.y3.jrun.model.participation.RichParticipation;
import org.y3.jrun.model.participation.RichParticipationsTableModel;
import org.y3.jrun.storage.database.DatabaseSession;
import org.y3.jrun.storage.file.FileSession;
import org.y3.jrun.storage.webxml.RegistrationsImporter;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.participation.ParticipationResultTimeComparator;

/**
 * 
 * @author ryppo
 *
 */
public class ApplicationController {
	
	private final String userHome = System.getProperty("user.home", ".");
	
	private DatabaseSession dbSession = null;
	private FileSession fileSession = new FileSession();
	private boolean debug = false;

	public ApplicationController() {
	}
	
	public ApplicationController(boolean _debug) {
		debug = _debug;
	}
	
	public boolean isDebug() {
		return debug;
	}
	
	public ContactListModel getAllContacts() throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new ContactListModel((Contact[]) sortModels(dbSession.getAllContacts()));
	}
	
	public ContactListModel getContactsById(int[] contactIds) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new ContactListModel(dbSession.getContactsById(contactIds));
	}
	
	public ContactListModel getContactById(int contactId) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new ContactListModel(dbSession.getContactsById(new int[]{contactId}));
	}
	
	public Contact getContactByFingerPrint(String fingerPrint) throws IOException, ClassNotFoundException, SQLException {
		Contact contact = null;
		ensureDBSession();
		ContactListModel listModel = new ContactListModel(dbSession.getContactsByFingerPrint(fingerPrint));
		if (listModel != null && listModel.getSize() != 0) {
			contact = listModel.getElementAt(0);
		}
		return contact;
	}
	
	public Discipline getDisciplineByFingerPrint(String fingerPrint) throws IOException, ClassNotFoundException, SQLException {
		Discipline discipline = null;
		ensureDBSession();
		DisciplineListModel listModel = new DisciplineListModel(dbSession.getDisciplinesByFingerPrint(fingerPrint));
		if (listModel != null && listModel.getSize() != 0) {
			discipline = listModel.getElementAt(0);
		}
		return discipline;
	}
	
	public DisciplineListModel getAllDisciplines() throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new DisciplineListModel((Discipline[]) sortModels(dbSession.getAllDisicplines()));
	}
	
	public DisciplineListModel getDisciplinesByCompetitionId(int competitionId) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new DisciplineListModel(dbSession.getDisciplinesByCompetitionId(competitionId));
	}
	
	public DisciplineListModel getDisciplinesById(int[] disciplineIds) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new DisciplineListModel(dbSession.getDisciplinesById(disciplineIds));
	}
	
	public DisciplineListModel getDisciplineById(int disciplineId) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return getDisciplinesById(new int[]{disciplineId});
	}
	
	public Discipline getDisciplineModelById(int disciplineId) throws IOException, ClassNotFoundException, SQLException {
		Discipline discipline = null;
		ensureDBSession();
		DisciplineListModel listModel = getDisciplineById(disciplineId);
		if (listModel != null && listModel.getSize() != 0) {
			discipline = listModel.getElementAt(0);
		}
		return discipline;
	}
	
	public CompetitionListModel getCompetitionById(int competitionId) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new CompetitionListModel(dbSession.getCompetitionsById(new int[]{competitionId}));
	}
	
	public CompetitionListModel getCompetitionsByIds(int[] competitionId) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new CompetitionListModel(dbSession.getCompetitionsById(competitionId));
	}
	
	public int[] getCompetitionIdsByAgeClassesDefinitions(AgeClassesDefinition[] ageClassesDefinitions) throws IOException, ClassNotFoundException, SQLException {
		int[] competitionsIds = null;
		if (ageClassesDefinitions != null && ageClassesDefinitions.length > 0) {
			ensureDBSession();
			int[] ageClassesDefinitionIds = new int[ageClassesDefinitions.length];
			for (int acdNo = 0; acdNo < ageClassesDefinitions.length; acdNo++) {
				ageClassesDefinitionIds[acdNo] = ageClassesDefinitions[acdNo].getId();
			}
			competitionsIds = dbSession.getCompetitionIdsByAgeClassesDefinitionIds(ageClassesDefinitionIds);
		}
		return competitionsIds;
	}
	
	public AgeClassesDefinition getAgeClassesDefinitionByCompetition(Competition competition) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return dbSession.getAgeClassesDefinitionByCompetition(competition);
	}
	
	public CompetitionListModel getAllCompetitions() throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new CompetitionListModel((Competition[]) sortModels(dbSession.getAllCompetitions()));
	}
	
	public NotificationListModel getAllNotifications() throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new NotificationListModel(dbSession.getAllNotifications());
	}
	
	public NotificationListModel getAllNotificationsForModel(Model model) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new NotificationListModel(dbSession.getAllNotificationsForModel(model));
	}
	
	public ParticipationListModel getAllParticipations()  throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new ParticipationListModel(dbSession.getAllParticipations());
	}
	
	public ParticipationListModel getParticipationById(int modelId) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new ParticipationListModel(dbSession.getParticipationsById(modelId));
	}
	
	public Participation getParticipationByImportFingerPrint(String importFingerPrint) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return dbSession.getParticipationByImportFingerPrint(importFingerPrint);
	}
	
	public ParticipationListModel getAllParticipationsForCompetition(Competition competition) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		if (competition != null) {
			return new ParticipationListModel(dbSession.getAllParticipationsByCompetitionId(competition.getId()));
		}
		return null;
	}
	
	public ParticipationListModel getAllParticipationsForContact(Contact contact) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		if (contact != null) {
			return new ParticipationListModel(dbSession.getAllParticipationsByContactId(contact.getId()));
		}
		return null;
	}
	
	public ParticipationListModel getAllParticipationsForDiscipline(Discipline discipline) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		if (discipline != null) {
			return new ParticipationListModel(dbSession.getAllParticipationsByDisciplineId(discipline.getId()));
		}
		return null;
	}
	
	public RichParticipationsTableModel getParticipationsTableForDiscipline(Competition competition, Discipline discipline, ApplicationFrame appFrame) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		if (discipline != null) {
			Participation[] participations = dbSession.getAllParticipationsByCompetitionIdAndContactIdAndDisciplineId(competition.getId(), 0, discipline.getId());
			Arrays.sort(participations, new ParticipationResultTimeComparator());
			return new RichParticipationsTableModel(
					getRichParticipationsForParticipations(
							participations), this, appFrame);
		}
		return null;
	}
	
	public RichParticipationsTableModel getParticipationsTableForDisciplines(Competition competition, Discipline[] disciplines, ApplicationFrame appFrame) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		if (disciplines != null && disciplines.length > 0) {
			int[] disciplineIds = new int[disciplines.length];
			for (int dNo = 0; dNo < disciplineIds.length; dNo++) {
				disciplineIds[dNo] = disciplines[dNo].getId();
			}
			Participation[] participations = dbSession.getAllParticipationsByCompetitionIdAndContactIdAndDisciplineIds(competition.getId(), 0, disciplineIds);
			Arrays.sort(participations, new ParticipationResultTimeComparator());
			return new RichParticipationsTableModel(
					getRichParticipationsForParticipations(
							participations), this, appFrame);
		}
		return null;
	}
	
	public int countParticipationsForDiscipline(Discipline discipline, Competition competition) throws SQLException, ClassNotFoundException, IOException {
		ensureDBSession();
		int count = 0;
		if (discipline != null && competition != null) {
			return dbSession.countParticipationsByDisciplineIdAndCompetitionId(discipline.getId(), competition.getId());
		}
		return count;
	}
	
	public RichParticipation[] getRichParticipationsForParticipations(Participation[] participations) throws SQLException {
		RichParticipation[] richP = null;
		if (participations != null) {
			richP = new RichParticipation[participations.length];
			for (int i = 0; i < participations.length; i++) {
				Participation p = participations[i];
				RichParticipation rP = new RichParticipation();
				rP.setMetaDataByParticipation(p);
				Contact[] contacts = dbSession.getContactsById(new int[]{rP.getContactId()});
				if (contacts != null && contacts.length != 0) {
					rP.setParticipant(contacts[0]);
				}
				Competition[] competitions = dbSession.getCompetitionsById(new int[]{rP.getCompetitionId()});
				if (competitions != null && competitions.length != 0) {
					Competition competition = competitions[0];
					RichAgeClassesDefinition richAgeClassesDefinition = null;
					if (competition != null) {
						AgeClassesDefinition ageClassesDefinition = dbSession.getAgeClassesDefinitionByCompetition(competition);
						AgeClass[] ageClasses = null;
						if (ageClassesDefinition != null) {
							ageClasses = dbSession.getAgeClassesByAgeClassesDefinitionId(ageClassesDefinition.getId());
						}
						richAgeClassesDefinition = new RichAgeClassesDefinition(ageClassesDefinition, ageClasses);
					}
					rP.setRelatedCompetition(new RichCompetition(richAgeClassesDefinition, competition));
				}
				Discipline[] disciplines = dbSession.getDisciplinesById(new int[]{rP.getDisciplineId()});
				if (disciplines != null && disciplines.length != 0) {
					rP.setDiscipline(disciplines[0]);
				}
				richP[i] = rP;
			}
		}
		return richP;
	}
	
	public int getHospizDonationSummaryInEuroCentForCompetition(Competition competition) throws SQLException, IOException, ClassNotFoundException {
		ensureDBSession();
		if (competition != null) {
			return dbSession.getParticipationsHospizDonationSummaryInEuroCentByCompetitionId(competition.getId());
		} else {
			return 0;
		}
	}
	
	public ParticipationListModel getAllParticipationsForCompetitionAndContact(Competition competition, Contact contact) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		if (competition == null && contact == null) {
			return getAllParticipations();
		} else if (competition == null && contact != null) {
			return getAllParticipationsForContact(contact);
		} else if (competition != null && contact == null) {
			return getAllParticipationsForCompetition(competition);
		} else {
			return new ParticipationListModel(dbSession.getAllParticipationsByCompetitionIdAndContactId(competition.getId(), contact.getId()));
		}	
	}
	
	public ModelListModel searchModels(String fullTextSearchString, Model exampleModel) throws SQLException {
		Model[] models = dbSession.searchModel(fullTextSearchString, exampleModel);
		ModelListModel modelListModel = modelArrayToModelListModel(models);
		return modelListModel;
	}
	
	public ParticipationListModel getAllParticipationsForCompetitionAndContactAndDiscipline(Competition competition, Contact contact, Discipline discipline) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		if (competition == null && contact == null && discipline == null) {
			return getAllParticipations();
		} else if (competition == null && contact != null && discipline == null) {
			return getAllParticipationsForContact(contact);
		} else if (competition != null && contact == null && discipline == null) {
			return getAllParticipationsForCompetition(competition);
		} else if (competition == null && contact == null && discipline != null) {
			return getAllParticipationsForDiscipline(discipline);
		}	
		else {
			int competitionId = 0;
			if (competition != null) {
				competitionId = competition.getId();
			}
			int contactId = 0;
			if (contact != null) {
				contactId = contact.getId();
			}
			int disciplineId = 0;
			if (discipline != null) {
				disciplineId = discipline.getId();
			}
			return new ParticipationListModel(dbSession.getAllParticipationsByCompetitionIdAndContactIdAndDisciplineId(competitionId, contactId, disciplineId));
		}	
	}
	
	public AgeClassListModel getAllAgeClasses() throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new AgeClassListModel(dbSession.getAllAgeClasses());
	}
	
	public AgeClassListModel getAgeClassesForAgeClassesDefinition(AgeClassesDefinition ageClassesDefinition) throws IOException, ClassNotFoundException, SQLException {
		AgeClassListModel list = null;
		if (ageClassesDefinition != null) {
			ensureDBSession();
			list = new AgeClassListModel(dbSession.getAgeClassesByAgeClassesDefinitionId(ageClassesDefinition.getId()));
		}
		return list;
	}
	
	public AgeClassTableModel getAgeClassesForAgeClassesDefinitionAsTableModel(AgeClassesDefinition ageClassesDefinition) throws IOException, ClassNotFoundException, SQLException {
		AgeClassTableModel model = null;
		if (ageClassesDefinition != null) {
			ensureDBSession();
			model = new AgeClassTableModel();
			model.addColumn(Messages.getString(Messages.AGECLASSES), dbSession.getAgeClassesByAgeClassesDefinitionId(ageClassesDefinition.getId()));
		}
		return model;
	}
	
	public AgeClassesDefinitionListModel getAllAgeClassesDefinitions() throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return new AgeClassesDefinitionListModel(dbSession.getAllAgeClassesDefinitions());
	}
	
	public RichAgeClassesDefinition getRichAgeClassesDefinitionForCompetition(Competition competition) throws IOException, ClassNotFoundException, SQLException {
		RichAgeClassesDefinition model = null;
		if (competition != null) {
			ensureDBSession();
			AgeClassesDefinition ageClassesDefinition = dbSession.getAgeClassesDefinitionByCompetition(competition);
			AgeClass[] ageClasses = null;
			if (ageClassesDefinition != null) {
				ageClasses = dbSession.getAgeClassesByAgeClassesDefinitionId(ageClassesDefinition.getId());
			}
			model = new RichAgeClassesDefinition(ageClassesDefinition, ageClasses);
		}
		return model;
	}
	
	public RichAgeClassesDefinition getRichAgeClassesDefinitionByAgeClassesDefinition(AgeClassesDefinition ageClassesDefinition) throws IOException, ClassNotFoundException, SQLException {
		RichAgeClassesDefinition model = null;
		ensureDBSession();
		AgeClass[] ageClasses = null;
		if (ageClassesDefinition != null) {
			ageClasses = dbSession.getAgeClassesByAgeClassesDefinitionId(ageClassesDefinition.getId());
		}
		model = new RichAgeClassesDefinition(ageClassesDefinition, ageClasses);
		return model;
	}
	
	public RichAgeClassesDefinition[] getRichAgeClassesDefinitionsByAgeClassesDefinitions(AgeClassesDefinition[] ageClassesDefinitions) throws IOException,  SQLException, ClassNotFoundException {
		RichAgeClassesDefinition[] models = null;
		ensureDBSession();
		if (ageClassesDefinitions != null && ageClassesDefinitions.length > 0) {
			models = new RichAgeClassesDefinition[ageClassesDefinitions.length];
			for (int no = 0; no < ageClassesDefinitions.length; no++) {
				AgeClassesDefinition model = ageClassesDefinitions[no];
				RichAgeClassesDefinition richModel = null;
				if (model != null) {
					richModel = getRichAgeClassesDefinitionByAgeClassesDefinition(model);
				}
				models[no] = richModel;
			}
		}
		return models;
	}
	
	public boolean createDatabase() throws SQLException, IOException, ClassNotFoundException {
		ensureDBSession();
		return dbSession.createDatabase();
	}
	
	public boolean removedDatabase() throws SQLException, IOException, ClassNotFoundException {
		ensureDBSession();
		if (dbSession.removeDatabase()) {
			return dbSession.disconnect();
		}
		return false;
	}
	
	public boolean connectDatabase() throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return dbSession.connect();
	}
	
	public boolean reConnectDatabase() throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		if (dbSession.disconnect()) {
			return dbSession.connect();
		} else {
			return false;
		}
	}
	
	public boolean shutDown() throws SQLException {
		boolean shutDown = false;
		if (dbSession == null) {
			shutDown = true;
		} else {
			shutDown = dbSession.disconnect();
			if (shutDown) {
				dbSession = null;
			}
		}
		return shutDown;
	}
	
	public boolean saveModel(Model model) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		setModelChanged(model);
		return dbSession.saveModel(model);
	}
	
	private void setModelChanged(Model model) {
		model.setChangerName(System.getProperty("user.name"));
		model.setChangeDate(new Date());
		model.setVersion(model.getVersion() + 1);
	}
	
	public boolean deleteModel(Model model) throws IOException, ClassNotFoundException, SQLException {
		ensureDBSession();
		return dbSession.deleteModel(model);
	}
	
	public boolean importRichAgeClassesDefinitions(String location) throws IOException, ClassNotFoundException, SQLException {
		if (location == null || location.length() == 0) {
			location = userHome + "/" + KeywordsDictionary.AGECLASSESDEFINITION;
		}
		Model[] models = fileSession.getModelExchangerForModel(new AgeClassesDefinition()).importModels(location);
		if (models != null && models.length > 0) {
			for (Model model: models) {
				if (model instanceof RichAgeClassesDefinition) {
					saveRichAgeClassesDefinitionWithAgeClasses((RichAgeClassesDefinition) model);
				}
			}
		}
		return true;
	}
	
	public boolean saveRichAgeClassesDefinitionWithAgeClasses(RichAgeClassesDefinition richAgeClassesDefinition) throws IOException, ClassNotFoundException, SQLException {
		boolean success = false;
		if (richAgeClassesDefinition != null) {
			boolean richACDSaved = saveModel(richAgeClassesDefinition);
			if (richACDSaved) {
				AgeClass[] ageClasses = richAgeClassesDefinition.getAgeClasses();
				if (ageClasses != null && ageClasses.length > 0) {
					for (AgeClass ageClass: ageClasses) {
						if (ageClass != null) {
							ageClass.setRelatedAgeClassesDefinitionID(richAgeClassesDefinition.getId());
							saveModel(ageClass);
						}
					}
				}
			}
		}
		return success;
	}
	
	public boolean exportRichAgeClassesDefinitions(RichAgeClassesDefinition[] richAgeClassesDefinitions, String location) throws IOException {
		if (location == null || location.length() == 0) {
			location = userHome + "/" + KeywordsDictionary.AGECLASSESDEFINITION;
		}
		return fileSession.getModelExchangerForModel(new AgeClassesDefinition()).exportModels(richAgeClassesDefinitions, location);
	}
	
	public boolean exportParticipationsData(Participation[] participations, String location) throws IOException {
		if (location == null || location.length() == 0) {
			location = userHome + "/" + KeywordsDictionary.PARTICIPATION;
		}
		return fileSession.getModelExchangerForModel(new Participation()).exportModels(participations, location);
	}
	
	public Participation[] importParticipationsData(String location) throws IOException, ClassNotFoundException, SQLException {
		Participation[] updatedParticipations = null;
		if (location == null || location.length() == 0) {
			location = userHome + "/" + KeywordsDictionary.PARTICIPATION;
		}
		Model[] models = fileSession.getModelExchangerForModel(new Participation()).importModels(location);
		if (models != null && models.length > 0) {
			int updatedParticipationsCount = 0;
			for (Model model: models) {
				if (model instanceof Participation) {
					updatedParticipationsCount++;
				}
			}
			updatedParticipations = new Participation[updatedParticipationsCount];
			int actualUpdatedParticipation = 0;
			for (Model model: models) {
				if (model instanceof Participation) {
					Participation importParticipation = (Participation) model;
					Participation databaseParticipation = getParticipationByImportFingerPrint(importParticipation.getImportFingerPrint());
					if (databaseParticipation != null) {
						databaseParticipation.setParticipationNumber(importParticipation.getParticipationNumber());
						databaseParticipation.setResultTimeAsString(importParticipation.getResultTimeAsString());
						saveModel(databaseParticipation);
						updatedParticipations[actualUpdatedParticipation] = databaseParticipation;
						actualUpdatedParticipation++;
					}
				}
			}
		}
		return updatedParticipations;
	}
	
	public boolean exportContacts(Contact[] contacts) throws IOException {
		return fileSession.getModelExchangerForModel(new Contact()).exportModels(contacts, userHome + "/" + KeywordsDictionary.CONTACT);
	}
	
	public Contact[] importContacts() throws IOException {
		Model[] models = fileSession.getModelExchangerForModel(new Contact()).importModels(userHome + "/" + KeywordsDictionary.CONTACT);
		return getContactArray(models);
	}
	
	public Contact[] getContactArray(Model[] models) {
		Contact[] contacts = null;
		if (models != null && models.length > 0) {
			//count contacts
			int countContacts = 0;
			for (Model model: models) {
				if (model instanceof Contact) {
					countContacts++;
				}
			}
			//collect contacts
			contacts = new Contact[countContacts];
			int actualContact = 0;
			for (Model model: models) {
				if (model instanceof Contact) {
					contacts[actualContact] = (Contact) model;
					actualContact++;
				}
			}
		}
		return contacts;
	}
	
	private void ensureDBSession() throws IOException, ClassNotFoundException {
		if (dbSession == null) {
			dbSession = new DatabaseSession(debug);
		}
	}
	
	public void openWebPage(URL url) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(url.toURI());
	}
	
	public int importRegisteredDisciplinesForCompetition(Competition competition) throws IOException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, ParseException {
		int newlyImported = 0;
		if (competition != null) {
			ensureDBSession();
			newlyImported = importRegisteredNotExistingModels(
					RegistrationsImporter.importDisciplines(competition.getRegistrationsLogFile()),
					dbSession.getAllDisicplines());
		}
		return newlyImported;
	}
	
	public int importRegisteredParticipationsForCompetition(RichCompetition competition) throws IOException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException, ParseException {
		int newlyImported = 0;
		if (competition != null) {
			ensureDBSession();
			RichParticipation[] models = RegistrationsImporter.importParticipations(competition.getRegistrationsLogFile(), competition);
			if (models != null) {
				for (RichParticipation model: models) {
					//contact
					Contact new_p = null;
					Contact p_finger = model.getParticipant();
					if (p_finger != null) {
						new_p = getContactByFingerPrint(p_finger.getImportFingerPrint());
					}
					model.setParticipant(new_p);
					if (new_p != null) {
						model.setContactId(new_p.getId());
					}
					//discipline
					Discipline new_d = null;
					Discipline d_finger = model.getDiscipline();
					if (d_finger != null) {
						new_d = getDisciplineByFingerPrint(d_finger.getImportFingerPrint());
					}
					model.setDiscipline(new_d);
					if (new_d != null) {
						model.setDisciplineId(new_d.getId());
					}
					//competition
					model.setCompetitionId(competition.getId());
					model.setRelatedCompetition(new RichCompetition(null, competition));
				}
			}
			newlyImported = importRegisteredNotExistingModels(models, dbSession.getAllParticipations());
		}
		return newlyImported;
	}
	
	public int importRegisteredContactsForCompetition(Competition competition) throws ParserConfigurationException, SAXException, IOException, ParseException, ClassNotFoundException, SQLException {
		int newlyImported = 0;
		if (competition != null) {
			ensureDBSession();
			newlyImported = importRegisteredNotExistingModels(
					RegistrationsImporter.importContacts(competition.getRegistrationsLogFile()),
					dbSession.getAllContacts());
		}
		return newlyImported;
	}
	
	private int importRegisteredNotExistingModels(Model[] importModels, Model[] existingModels) throws ParserConfigurationException, SAXException, IOException, ParseException, ClassNotFoundException, SQLException {
		int newlyImported = 0;
		if (importModels != null && importModels.length != 0) {
			newlyImported = dbSession.importNotExistingModels(importModels, existingModels);
		}
		return newlyImported;
	}
	
	public ModelListModel modelArrayToModelListModel(Model[] modelArray) {
		ModelListModel returnValue = new DefaultModelListModel(modelArray);
		if (modelArray != null && modelArray.length > 0) {
			Model model = modelArray[0];
			if (model instanceof AgeClass) {
				returnValue = new AgeClassListModel((AgeClass[]) modelArray);
			} else if (model instanceof AgeClassesDefinition) {
				returnValue = new AgeClassesDefinitionListModel((AgeClassesDefinition[]) modelArray);
			} else if (model instanceof Competition) {
				returnValue = new CompetitionListModel((Competition[]) modelArray);
			} else if (model instanceof Contact) {
				returnValue = new ContactListModel((Contact[]) modelArray);
			} else if (model instanceof Discipline) {
				returnValue = new DisciplineListModel((Discipline[]) modelArray);
			} else if (model instanceof Notification) {
				returnValue = new NotificationListModel((Notification[]) modelArray);
			} else if (model instanceof Participation) {
				returnValue = new ParticipationListModel((Participation[]) modelArray);
			}
		}
		return returnValue;
	}
	
	public Model[] sortModels(Model[] models) {
		if (models != null && models.length > 0) {
			Arrays.sort(models, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					if (o1 == null && o2 == null) {
						return 0;
					} else if (o1 == null) {
						return 1;
					} else if (o2 == null) {
						return -1;
					} else {
						String s1 = o1.toString();
						String s2 = o2.toString();
						return s1.compareTo(s2);
					}
				}
			});
		}
		return models;
	}
	
	public void calculateRanking(RichParticipation[] richParticipations, boolean automaticSave) throws Exception {
		if (richParticipations != null && richParticipations.length != 0) {
			//remove non competitive
			int amount = richParticipations.length;
			for (RichParticipation participation: richParticipations) {
				if (participation.isNoncompetitive()) {
					amount--;
				}
			}
			if (amount < richParticipations.length) {
				RichParticipation[] partsWithoutNoncomp = new RichParticipation[amount];
				int pos = 0;
				for (RichParticipation participation: richParticipations) {
					if (!participation.isNoncompetitive()) {
						partsWithoutNoncomp[pos] = participation;
						pos++;
					} else {
						participation.setRank(0);
						if (automaticSave) {
							saveModel(participation);
						}
					}
				}
				richParticipations = partsWithoutNoncomp;
			}
			//sort by time
			Arrays.sort(richParticipations, new ParticipationResultTimeComparator());
			int rank = 1;
			int setRank = 0;
			long lastRankResultTime = 0l; 
			for (RichParticipation participation: richParticipations) {
				if (participation.getResultTime() != lastRankResultTime) setRank = rank;
				participation.setRank(setRank);
				lastRankResultTime = participation.getResultTime();
				if (automaticSave) {
					saveModel(participation);
				}
				rank++;
			}
		}
	}
	
	public void calculateAgeClassesRankings(RichParticipation[] richParticipations, boolean automaticSave) throws Exception {
		if (richParticipations != null && richParticipations.length > 0) {
			//create array of all distinct age classes
			AgeClass[] ACs = getDistinctAgeClasses(richParticipations);
			System.out.println("ACs.length: " + ACs.length);
			//loop over the age classes		
			if (ACs != null && ACs.length > 0) {
				for (AgeClass ac: ACs) {
					//get related participations
					RichParticipation[] acParticipations = getParticipationsForAgeClass(richParticipations, ac);
					//calculate their ranking by time
					Arrays.sort(acParticipations, new ParticipationResultTimeComparator());
					int rank = 1;
					int setRank = 0;
					long lastRankResultTime = 0l;
					for (RichParticipation participation: acParticipations) {
						if (participation.getResultTime() != lastRankResultTime) setRank = rank;
						participation.setAgeClassRank(setRank);
						lastRankResultTime = participation.getResultTime();
						if (automaticSave) {
							saveModel(participation);
						}
						rank++;
					}
				}
			}
		}
	}
	
	public void calculateGenderAgeClassRankings(RichParticipation[] richParticipations, 
			boolean automaticSave) throws Exception {
		if (richParticipations != null && richParticipations.length > 0) {
			// MALE
			RichParticipation[] acParticipations = getParticipationsForGender(
					richParticipations, Contact.gendertype.MALE);
			// calculate their ranking by time
			//create array of all distinct age classes
			AgeClass[] ACs = getDistinctAgeClasses(acParticipations);
			System.out.println("ACs.length: " + ACs.length);
			//loop over the age classes		
			if (ACs != null && ACs.length > 0) {
				for (AgeClass ac: ACs) {
					//get related participations
					RichParticipation[] gAcParticipations = getParticipationsForAgeClass(acParticipations, ac);
					//calculate their ranking by time
					Arrays.sort(gAcParticipations, new ParticipationResultTimeComparator());
					int rank = 1;
					int setRank = 0;
					long lastRankResultTime = 0l;
					for (RichParticipation participation: gAcParticipations) {
						if (participation.getResultTime() != lastRankResultTime) setRank = rank;
						participation.setGenderAgeClassRank(setRank);
						lastRankResultTime = participation.getResultTime();
						if (automaticSave) {
							saveModel(participation);
						}
						rank++;
					}
				}
			}
			// FEMALE
			acParticipations = getParticipationsForGender(richParticipations,
					Contact.gendertype.FEMALE);
			// calculate their ranking by time
			//create array of all distinct age classes
			ACs = getDistinctAgeClasses(acParticipations);
			System.out.println("ACs.length: " + ACs.length);
			//loop over the age classes		
			if (ACs != null && ACs.length > 0) {
				for (AgeClass ac: ACs) {
					//get related participations
					RichParticipation[] gAcParticipations = getParticipationsForAgeClass(acParticipations, ac);
					//calculate their ranking by time
					Arrays.sort(gAcParticipations, new ParticipationResultTimeComparator());
					int rank = 1;
					int setRank = 0;
					long lastRankResultTime = 0l;
					for (RichParticipation participation: gAcParticipations) {
						if (participation.getResultTime() != lastRankResultTime) setRank = rank;
						participation.setGenderAgeClassRank(setRank);
						lastRankResultTime = participation.getResultTime();
						if (automaticSave) {
							saveModel(participation);
						}
						rank++;
					}
				}
			}
		}
	}
	
	public void calculateGenderRankings(RichParticipation[] richParticipations,
			boolean automaticSave) throws Exception {
		if (richParticipations != null && richParticipations.length > 0) {
			// MALE
			RichParticipation[] acParticipations = getParticipationsForGender(
					richParticipations, Contact.gendertype.MALE);
			// calculate their ranking by time
			Arrays.sort(acParticipations,
					new ParticipationResultTimeComparator());
			int rank = 1;
			int setRank = 0;
			long lastRankResultTime = 0l;
			for (RichParticipation participation : acParticipations) {
				if (participation.getResultTime() != lastRankResultTime)
					setRank = rank;
				participation.setGenderRank(setRank);
				lastRankResultTime = participation.getResultTime();
				if (automaticSave) {
					saveModel(participation);
				}
				rank++;
			}
			// FEMALE
			acParticipations = getParticipationsForGender(richParticipations,
					Contact.gendertype.FEMALE);
			// calculate their ranking by time
			Arrays.sort(acParticipations,
					new ParticipationResultTimeComparator());
			rank = 1;
			setRank = 0;
			lastRankResultTime = 0l;
			for (RichParticipation participation : acParticipations) {
				if (participation.getResultTime() != lastRankResultTime)
					setRank = rank;
				participation.setGenderRank(setRank);
				lastRankResultTime = participation.getResultTime();
				if (automaticSave) {
					saveModel(participation);
				}
				rank++;
			}
		}
	}
	
	public RichParticipation[] getParticipationsForAgeClass(RichParticipation[] richParticipations, AgeClass ageClass) {
		RichParticipation[] participations = null;
		ArrayList<RichParticipation> participationsList = new ArrayList<RichParticipation>(0);
		for (RichParticipation richParticipation: richParticipations) {
			AgeClass ac = richParticipation.getAgeClassForParticipant();
			if (ac != null && ageClass != null && ac.equals(ageClass)) {
				participationsList.add(richParticipation);
			}
		}
		participations = new RichParticipation[participationsList.size()];
		return participationsList.toArray(participations);
	}
	
	public AgeClass[] getDistinctAgeClasses(RichParticipation[] richParticipations) {
		Set<AgeClass> ageClassesSet = new HashSet<AgeClass>();
		for (RichParticipation participation: richParticipations) {
			AgeClass ac = participation.getAgeClassForParticipant();
			ageClassesSet.add(ac);
		}
		AgeClass[] distincAgeClassesArray = new AgeClass[ageClassesSet.size()];
		distincAgeClassesArray = ageClassesSet.toArray(distincAgeClassesArray);
		return distincAgeClassesArray;
	}
	
	public RichParticipation[] getParticipationsForGender(RichParticipation[] richParticipations, Contact.gendertype gender) {
		RichParticipation[] participations = null;
		ArrayList<RichParticipation> participationsList = new ArrayList<RichParticipation>(0);
		for (RichParticipation richParticipation: richParticipations) {
			Contact contact = richParticipation.getParticipant();
			if (contact != null && contact.getGender().equals(gender)) {
				participationsList.add(richParticipation);
			}
		}
		participations = new RichParticipation[participationsList.size()];
		return participationsList.toArray(participations);
	}
	
}
