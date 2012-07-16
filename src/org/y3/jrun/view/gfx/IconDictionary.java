package org.y3.jrun.view.gfx;

import java.net.URL;

import javax.swing.ImageIcon;

public class IconDictionary {

	private static String location 						= "org/y3/jrun/view/resource/";
	public static String NEW 							= "edit-add-3.png";
	public static String REMOVE 						= "edit-clear-3.png";
	public static String REMOVE_ALL_FROM_LIST			= "edit-clear-list.png";
	public static String REMOVE_ALL_FROM_COMPETITION	= "view-remove.png";
	public static String SAVE 							= "games-endturn.png";
	public static String EDIT 							= "document-edit.png";
	public static String REFRESH 						= "view-refresh-3.png";
	public static String HISTORY 						= "help-hint.png";
	public static String STATISTICS						= "emblem-notice.png";
	public static String SORT 							= "view-sort-descending.png";
	public static String EXPORT 						= "document-export-4.png";
	public static String IMPORT 						= "document-import-2.png";
	public static String PARTICIPATIONS 				= "clicknrun.png";
	public static String FILTER 						= "go-jump-3.png";
	public static String AGECLASSES_DEFINITION			= "ageclassesdefinition_icon.jpg";
	public static String NOTIFICATIONS					= "book-open.png";
	public static String COMPETITION	 				= "flag-2.png";
	public static String COMPETITION_GRID				= "flag-yellow.png";
	public static String DISCIPLINE 					= "flag-blue.png";
	public static String RESET_DISCIPLINE_GRID			= "emblem-symbolic-link.png";
	public static String CONTACT 						= "identity.png";
	public static String OBJECT 						= "go-previous-9.png";
	public static String DATABASE_ADD 					= "db_add.png";
	public static String DATABASE_REMOVE 				= "db_remove.png";
	public static String CONNECT 						= "network-disconnect.png";
	public static String SHUT_DOWN 						= "system-shutdown.png";
	public static String REPORT							= "report.png";
	public static String REPORT_ALL						= "report-go.png";
	public static String CANCEL 						= "dialog-cancel-3.png";
	public static String CERTIFICATION 					= "medal-gold.png";
	public static String RANKING 						= "bookmark-toolbar-2.png";
	public static String RANKING_ALL					= "bookmark-new-list-4.png";
	public static String INTERNET 						= "go-next-9.png";
	public static String DOWNLOAD_CONTACTS 				= "group-go.png";
	public static String DOWNLOAD_DISCIPLINES 			= "time-go.png";
	public static String DOWNLOAD_PARTICIPATIONS 		= "table-go.png";
	public static String DOWNLOAD_ALL					= "arrow-right-4.png";
	public static String MISSING_ICON					= "picture-empty.png";
	public static String HISTORY_BACK					= "arrow-left-2.png";
	public static String HISTORY_FORWARD				= "arrow-right-2.png";
	public static String SEARCH							= "system-search-4.png";
	public static String COMMENT						= "user-comment.png";
	public static String DONATION						= "money-2.png";
	
	public static ImageIcon getImageIcon(String iconName) {
		URL resourceURL = IconDictionary.class.getClassLoader().getResource(location + iconName);
		if (resourceURL == null) {
			resourceURL = IconDictionary.class.getClassLoader().getResource(location + MISSING_ICON);
		}
		return new ImageIcon(resourceURL);
	}
	
}
