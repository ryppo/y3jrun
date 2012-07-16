package org.y3.jrun.view.i18n;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	
	private static final String dummy = "";
	private static final String BUNDLE_NAME = "org/y3/jrun/view/i18n/messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static String getFormattedDate(Date date) {
		if (date != null) {
			return new SimpleDateFormat().format(date);
		} else {
			return dummy;
		}
	}
	
	public static Date getFormattedDate(String date) {
		if (date != null) {
			try {
				return new SimpleDateFormat().parse(date);
			} catch (ParseException e) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public static String APPLICATION_NAME 										= "APPLICATION_NAME";
	public static String SYSTEM_NOT_SHUT_DOWN 									= "SYSTEM_NOT_SHUT_DOWN";
	public static String USER_MESSAGE 											= "USER_MESSAGE";
	public static String FILE 													= "FILE";
	public static String CREATE_DATABASE 										= "CREATE_DATABASE";
	public static String DATABASE_SUCCESSFULLY_CREATED							= "DATABASE_SUCCESSFULLY_CREATED";
	public static String DATABASE_NOT_CREATED 									= "DATABASE_NOT_CREATED";
	public static String REMOVE_DATABASE 										= "REMOVE_DATABASE";
	public static String DATABASE_SUCCESSFULLY_REMOVED 							= "DATABASE_SUCCESSFULLY_REMOVED";
	public static String DATABASE_NOT_REMOVED 									= "DATABASE_NOT_REMOVED";
	public static String RE_CONNECT_TO_DATABASE 								= "RE_CONNECT_TO_DATABASE";
	public static String DATABASE_SUCCESSFULLY_CONNECTED 						= "DATABASE_SUCCESSFULLY_CONNECTED";
	public static String DATABASE_NOT_CONNECTED 								= "DATABASE_NOT_CONNECTED";
	public static String SHUT_DOWN 												= "SHUT_DOWN";
	public static String OK														= "OK";
	public static String SUCCESSFULLY_EXPORTED									= "SUCCESSFULLY_EXPORTED";
	public static String NOT_SUCCESSFULLY_EXPORTED								= "NOT_SUCCESSFULLY_EXPORTED";
	public static String SUCCESSFULLY_IMPORTED									= "SUCCESSFULLY_IMPORTED";
	public static String NOT_SUCCESSFULLY_IMPORTED								= "NOT_SUCCESSFULLY_IMPORTED";
	public static String CANCEL													= "CANCEL";
	public static String SEARCH													= "SEARCH";
	public static String EDIT													= "EDIT";
	public static String YOU_EXECUTE_THE_FOLLOWING_ACTIVITY						= "YOU_EXECUTE_THE_FOLLOWING_ACTIVITY";
	public static String MODEL_ID 												= "MODEL_ID";
	public static String MODEL_CREATOR											= "MODEL_CREATOR";
	public static String MODEL_CREATION											= "MODEL_CREATION";
	public static String MODEL_CHANGER											= "MODEL_CHANGER";
	public static String MODEL_CHANGE											= "MODEL_CHANGE";
	public static String MODEL_IMPORTFINGERPRINT								= "MODEL_IMPORTFINGERPRINT";
	public static String COMPETITION											= "COMPETITION";
	public static String COMPETITIONS											= "COMPETITIONS";
	public static String COMPETITION_TITLE 										= "COMPETITION_TITLE";
	public static String COMPETITION_START 										= "COMPETITION_START";
	public static String COMPETITION_DONATION_HOSPIZ_SUMMARY					= "COMPETITION_DONATION_HOSPIZ_SUMMARY";
	public static String COMPETITION_END 										= "COMPETITION_END";
	public static String COMPETITION_WEB_PAGE									= "COMPETITION_WEB_PAGE";
	public static String COMPETITION_REGISTRATIONS_LOG_FILE						= "COMPETITION_REGISTRATIONS_LOG_FILE";
	public static String COMPETITION_REGISTRATION_PAGE							= "COMPETITION_REGISTRATION_PAGE";
	public static String COMPETITION_SUCCESSFULLY_SAVED 						= "COMPETITION_SUCCESSFULLY_SAVED";
	public static String COMPETITION_NOT_SAVED 									= "COMPETITION_NOT_SAVED";
	public static String COMPETITION_SUCCESSFULLY_REMOVED						= "COMPETITION_SUCCESSFULLY_REMOVED";
	public static String COMPETITION_NOT_REMOVED								= "COMPETITION_NOT_REMOVED";
	public static String NO_COMPETITION_SELECTED__PARTICIPATION_CANNOT_BE_SHOWN = "NO_COMPETITION_SELECTED__PARTICIPATION_CANNOT_BE_SHOWN";
	public static String NO_DISCIPLINE_SELECTED_SELECT_ONE						= "NO_DISCIPLINE_SELECTED_SELECT_ONE";
	public static String SHOW_PARTICIPATIONS									= "SHOW_PARTICIPATIONS";
	public static String SHOW_DISCIPLINE										= "SHOW_DISCIPLINE";
	public static String NO_DISCIPLINE_RELATED									= "NO_DISCIPLINE_RELATED";
	public static String GENDER_FEMALE											= "GENDER_FEMALE";
	public static String GENDER_MALE											= "GENDER_MALE";
	public static String CONTACT												= "CONTACT";
	public static String CONTACTS												= "CONTACTS";
	public static String CONTACT_FIRSTNAME										= "CONTACT_FIRSTNAME";
	public static String CONTACT_LASTNAME										= "CONTACT_LASTNAME";
	public static String CONTACT_CALLNAME										= "CONTACT_CALLNAME";
	public static String CONTACT_ADDRESS_1										= "CONTACT_ADDRESS_1";
	public static String CONTACT_ADDRESS_2										= "CONTACT_ADDRESS_2";
	public static String CONTACT_POSTALCODE										= "CONTACT_POSTALCODE";
	public static String CONTACT_CITY											= "CONTACT_CITY";
	public static String CONTACT_BIRTHYEAR										= "CONTACT_BIRTHYEAR";
	public static String CONTACT_GENDER											= "CONTACT_GENDER";
	public static String CONTACT_PHONENUMBER									= "CONTACT_PHONENUMBER";
	public static String CONTACT_MOBILENUMBER									= "CONTACT_MOBILENUMBER";
	public static String CONTACT_EMAIL											= "CONTACT_EMAIL";
	public static String CONTACT_COMMENT										= "CONTACT_COMMENT";
	public static String SORT													= "SORT";
	public static String SORTED_BY												= "SORTED_BY";
	public static String CONTACTS_SUCCESSFULLY_EXPORTED							= "CONTACTS_SUCCESSFULLY_EXPORTED";
	public static String CONTACTS_NOT_EXPORTED									= "CONTACTS_NOT_EXPORTED";
	public static String CONTACTS_SUCCESSFULLY_IMPORTED							= "CONTACTS_SUCCESSFULLY_IMPORTED";
	public static String CONTACT_SUCCESSFULLY_SAVED								= "CONTACT_SUCCESSFULLY_SAVED";
	public static String CONTACT_NOT_SAVED										= "CONTACT_NOT_SAVED";
	public static String CONTACT_SUCCESSFULLY_REMOVED							= "CONTACT_SUCCESSFULLY_REMOVED";
	public static String CONTACT_NOT_REMOVED									= "CONTACT_NOT_REMOVED";
	public static String EXPORT													= "EXPORT";
	public static String IMPORT													= "IMPORT";
	public static String IMPORT_PARTICIPATION_DATA							= "IMPORT_PARTICIPATION_DATA";
	public static String EXPORT_PARTICIPATION_DATA							= "EXPORT_PARTICIPATION_DATA";
	public static String MODE_VIEW												= "MODE_VIEW";
	public static String MODE_EDIT												= "MODE_EDIT";
	public static String MODE_ADMINISTRATE										= "MODE_ADMINISTRATE";
	public static String MODELS													= "MODELS";
	public static String REFRESH												= "REFRESH";
	public static String HISTORY												= "HISTORY";
	public static String STATISTICS												= "STATISTICS";
	public static String NEW													= "NEW";
	public static String REMOVE													= "REMOVE";
	public static String REMOVE_ALL_PARTICIPATIONS_FROM_DISCIPLINE				= "REMOVE_ALL_PARTICIPATIONS_FROM_DISCIPLINE";
	public static String REMOVE_ALL_PARTICIPATIONS_FROM_COMPETITION				= "REMOVE_ALL_PARTICIPATIONS_FROM_COMPETITION";
	public static String CALCULATE_RANKING										= "CALCULATE_RANKING";
	public static String CALCULATE_AGECLASS_RANKING								= "CALCULATE_AGECLASS_RANKING";
	public static String CALCULATE_GENDER_AGECLASS_RANKING						= "CALCULATE_GENDER_AGECLASS_RANKING";
	public static String CALCULATE_GENDER_RANKING								= "CALCULATE_GENDER_RANKING";
	public static String CALCULATE_ALL_RANKINGS									= "CALCULATE_ALL_RANKINGS";
	public static String SAVE													= "SAVE";
	public static String NOTIFICATION											= "NOTIFICATION";
	public static String NOTIFICATIONS											= "NOTIFICATIONS";
	public static String NOTIFICATION_TYPE_DEBUG								= "NOTIFICATION_TYPE_DEBUG";
	public static String NOTIFICATION_TYPE_FATAL								= "NOTIFICATION_TYPE_FATAL";
	public static String NOTIFICATION_TYPE_ERROR								= "NOTIFICATION_TYPE_ERROR";
	public static String NOTIFICATION_TYPE_WARN									= "NOTIFICATION_TYPE_WARN";
	public static String NOTIFICATION_TYPE_INFO									= "NOTIFICATION_TYPE_INFO";
	public static String EXCEPTION_STACKTRACE									= "EXCEPTION_STACKTRACE";
	public static String RELATED_OBJECT											= "RELATED_OBJECT";
	public static String RELATED_OBJECT_TYPE									= "RELATED_OBJECT_TYPE";
	public static String NOT_SUPPORTED_MODEL_CANNOT_BE_SHOWN					= "NOT_SUPPORTED_MODEL_CANNOT_BE_SHOWN";
	public static String MESSAGE												= "MESSAGE";
	public static String MESSAGE_TYPE											= "MESSAGE_TYPE";										
	public static String NOTIFICATION_TITLE										= "NOTIFICATION_TITLE";
	public static String RELATED_MODEL_CANNOT_BE_SHOWN_NO_MODEL_RELATED_TO_SELECTED_NOTIFICATION
								= "RELATED_MODEL_CANNOT_BE_SHOWN_NO_MODEL_RELATED_TO_SELECTED_NOTIFICATION";
	public static String NO_AGECLASSESDEFINITION_SELECTED_SELECT_ONE_FIRST		= "NO_AGECLASSESDEFINITION_SELECTED_SELECT_ONE_FIRST";
	public static String NO_NOTIFICIATION_SELECTED_SELECT_ONE_FIRST				= "NO_NOTIFICIATION_SELECTED_SELECT_ONE_FIRST";
	public static String NO_PARTICIPATION_SELECTED_SELECT_ONE_FIRST				= "NO_PARTICIPATION_SELECTED_SELECT_ONE_FIRST";
	public static String SHOW_OBJECT											= "SHOW_OBJECT";
	public static String RANKING												= "RANKING";
	public static String AGECLASS_RANKING										= "AGECLASS_RANKING";
	public static String GENDER_RANKING											= "GENDER_RANKING";
	public static String GENDER_AGECLASS_RANKING								= "GENDER_AGECLASS_RANKING";
	public static String RANK													= "RANK";
	public static String REGISTRATION_DATE										= "REGISTRATION_DATE";
	public static String REGISTRATED_ONLINE										= "REGISTRATED_ONLINE";
	public static String START_NUMMER											= "START_NUMMER";
	public static String FEE_PAID												= "FEE_PAID";
	public static String CERTIFICATION_HANDEDOVER								= "CERTIFICATION_HANDEDOVER";
	public static String DONATION_HOSPIZ										= "DONATION_HOSPIZ";
	public static String RESET_DISCIPLINE_GRID									= "RESET_DISCIPLINE_GRID";
	public static String NONCOMPETITIVE											= "NONCOMPETITIVE";
	public static String PARTICIPANT											= "PARTICIPANT";
	public static String RESULT_TIME											= "RESULT_TIME";
	public static String RESULT_TIME_FORMAT										= "RESULT_TIME_FORMAT";
	public static String HH_mm_ss_SSS											= "HH_mm_ss_SSS";
	public static String HH_mm_ss												= "HH_mm_ss";
	public static String mm_ss													= "mm_ss";
	public static String PARTICIPATION_SUCCESSFULLY_SAVED						= "PARTICIPATION_SUCCESSFULLY_SAVED";
	public static String PARTICIPATION_NOT_SAVED								= "PARTICIPATION_NOT_SAVED";
	public static String PARTICIPATIONS											= "PARTICIPATIONS";
	public static String PARTICIPATION											= "PARTICIPATION";
	public static String FILTER_LIST											= "FILTER_LIST";
	public static String SHOW_COMPETITION										= "SHOW_COMPETITION";
	public static String SHOW_AGECLASSES_DEFINITION								= "SHOW_AGECLASSES_DEFINITION";
	public static String SHOW_COMPETITION_GRID									= "SHOW_COMPETITION_GRID";
	public static String SHOW_CONTACT											= "SHOW_CONTACT";
	public static String REPORT													= "REPORT";
	public static String AGECLASS_REPORTS										= "AGECLASS_REPORTS";
	public static String GENDER_REPORTS											= "GENDER_REPORTS";
	public static String GENDER_AGECLASS_REPORTS								= "GENDER_AGECLASS_REPORTS";
	public static String REPORT_CERTIFICATION									= "REPORT_CERTIFICATION";
	public static String REPORT_AGECLASSES_DEFINITION							= "REPORT_AGECLASSES_DEFINITION";
	public static String REPORT_ALL_START_GRIDS									= "REPORT_ALL_START_GRIDS";
	public static String PREVIEW												= "PREVIEW";
	public static String SUCCESSFULLY_REMOVED									= "SUCCESSFULLY_REMOVED";
	public static String NOT_REMOVED											= "NOT_REMOVED";
	public static String SUCCESSFULLY_SAVED										= "SUCCESSFULLY_SAVED";
	public static String NOT_SAVED												= "NOT_SAVED";
	//discipline
	public static String DISCIPLINE												= "DISCIPLINE";
	public static String DISCIPLINES											= "DISCIPLINES";
	public static String DISCIPLINE_ID											= MODEL_ID;
	public static String DISCIPLINE_NAME										= "DISCIPLINE_NAME";
	public static String DISCIPLINE_LENGTH										= "DISCIPLINE_LENGTH";
	public static String DISCIPLINE_START										= "DISCIPLINE_START";
	public static String DISCIPLINE_PRICE_IN_EUROCENT							= "DISCIPLINE_PRICE_IN_EUROCENT";
	public static String DISCIPLINE_SUCCESSFULLY_SAVED							= "DISCIPLINE_SUCCESSFULLY_SAVED";
	public static String DISCIPLINE_NOT_SAVED									= "DISCIPLINE_NOT_SAVED";
	public static String DISCIPLINE_SUCCESSFULLY_REMOVED						= "DISCIPLINE_SUCCESSFULLY_REMOVED";
	public static String DISCIPLINE_NOT_REMOVED									= "DISCIPLINE_NOT_REMOVED";
	//contact sort by
	public static String FIRSTNAME_LASTNAME_ASC									= "FIRSTNAME_LASTNAME_ASC";
	public static String FIRSTNAME_LASTNAME_DESC								= "FIRSTNAME_LASTNAME_DESC";
	public static String LASTNAME_FIRSTNAME_ASC									= "LASTNAME_FIRSTNAME_ASC";
	public static String LASTNAME_FIRSTNAME_DESC								= "LASTNAME_FIRSTNAME_DESC";
	public static String CALLNAME_ASC											= "CALLNAME_ASC";
	public static String CALLNAME_DESC											= "CALLNAME_DESC";
	public static String ID_ASC													= "ID_ASC";
	public static String ID_DESC												= "ID_DESC";
	public static String BIRTHYEAR_ASC											= "BIRTHYEAR_ASC";
	public static String BIRTHYEAR_DESC											= "BIRTHYEAR_DESC";
	public static String GENDER_ASC												= "GENDER_ASC";
	public static String GENDER_DESC											= "GENDER_DESC";
	//age class
	public static String AGECLASS												= "AGECLASS";
	public static String AGECLASSES												= "AGECLASSES";
	public static String YEAR_FROM												= "YEAR_FROM";
	public static String YEAR_TO												= "YEAR_TO";
	public static String TITLE													= "TITLE";
	public static String AND_YOUNGER											= "AND_YOUNGER";
	public static String AND_OLDER												= "AND_OLDER";
	//age classes definition
	public static String AGECLASSESDEFINITIONS									= "AGECLASSESDEFINITIONS";
	public static String NAME													= "NAME";
	public static String AGECLASSESDEFINITION_SUCCESSFULLY_SAVED				= "AGECLASSESDEFINITION_SUCCESSFULLY_SAVED";
	public static String AGECLASSESDEFINITION_NOT_SAVED							= "AGECLASSESDEFINITION_NOT_SAVED";
	public static String AGECLASSESDEFINITION_SUCCESSFULLY_REMOVED				= "AGECLASSESDEFINITION_SUCCESSFULLY_REMOVED";
	public static String AGECLASSESDEFINITION_NOT_REMOVED						= "AGECLASSESDEFINITION_NOT_REMOVED";
	//download by registrations file
	public static String DOWNLOAD_CONTACTS										= "DOWLOAD_CONTACTS";
	public static String CONTACTS_ADDED_BY_DOWNLOAD								= "CONTACTS_ADDED_BY_DOWNLOAD";
	public static String DOWNLOAD_OF_CONTACTS_FAILED							= "DOWNLOAD_OF_CONTACTS_FAILED";
	public static String DOWNLOAD_DISCIPLINES									= "DOWLOAD_DISCIPLINES";
	public static String DISCIPLINES_ADDED_BY_DOWNLOAD							= "DISCIPLINES_ADDED_BY_DOWNLOAD";
	public static String DOWNLOAD_OF_DISCIPLINES_FAILED							= "DOWNLOAD_OF_DISCIPLINES_FAILED";
	public static String DOWNLOAD_PARTICIPATIONS								= "DOWNLOAD_PARTICIPATIONS";
	public static String PARTICIPATIONS_ADDED_BY_DOWNLOAD						= "PARTICIPATIONS_ADDED_BY_DOWNLOAD";
	public static String DOWNLOAD_OF_PARTICIPATIONS_FAILED						= "DOWNLOAD_OF_PARTICIPATIONS_FAILED";
	public static String DOWNLOAD_ALL											= "DOWNLOAD_ALL";
	//statistics
	public static String STATISTIC												= "STATISTIC";
	public static String COMMON													= "COMMON";
	public static String SPECIFIC												= "SPECIFIC";
	public static String YOUNGEST_PARTICIPANT									= "YOUNGEST_PARTICIPANT";
	public static String OLDEST_PARTICIPANT										= "OLDEST_PARTICIPANT";
	public static String NOT_FEE_PAID_PARTICIPANT								= "NOT_FEE_PAID_PARTICIPANT";
	public static String NO_RESULT_TIME_PARTICIPANT								= "NO_RESULT_TIME_PARTICIPANT";
	public static String NO_CERTIFICATION_PARTICIPANT							= "NO_CERTIFICATION_PARTICIPANT";
	//reports
	public static String REPORTS_CREATION										= "REPORTS_CREATION";
	public static String REPORTS_TO_CREATE										= "REPORTS_TO_CREATE";
	public static String PRINT													= "PRINT";
	public static String PRINT_WITH_DIALOG										= "PRINT_WITH_DIALOG";
	public static String EXPORT_TO_HTML											= "EXPORT_TO_HTML";
	public static String EXPORT_TO_PDF											= "EXPORT_TO_PDF";
	public static String FEMALE													= "FEMALE";
	public static String MALE													= "MALE";
	public static String SAVE_LOCATION											= "SAVE_LOCATION";
	public static String ALL													= "ALL";
	public static String REPORT_CREATED_SUCCESSFULLY							= "REPORT_CREATED_SUCCESSFULLY";
}
