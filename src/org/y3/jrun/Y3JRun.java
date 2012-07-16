package org.y3.jrun;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.view.ApplicationFrame;

/**
 * 
 */

/**
 * @author ryppo
 *
 */
public class Y3JRun {
	
	private static boolean DEBUG = true;
	private static boolean DEBUG_CONSOLE = false;
	
	private static final String appId = "Y3JRun";
	
	
	
	/**
	 * Constructor
	 */
	public Y3JRun() {
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Y3JRun");
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Runs Y3JavaRun
	 */
	public void run(boolean debug, boolean debugConsole) {
		ApplicationController appControl = new ApplicationController(debug);
		ApplicationFrame appFrame = new ApplicationFrame(appControl, debugConsole);
		try {
			if (appControl.connectDatabase()) {
				appFrame.bindData();
			}
		} catch (Exception e) {
			appFrame.showUserMessage(e, null);
		} finally {
			appFrame.showDebugConsole(true);
			appFrame.setVisible(true);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//check no other instance is already running
		boolean runnable = false;
		try {
			JUnique.acquireLock(appId);
			runnable = true;
		} catch (AlreadyLockedException e) {
			runnable = false;
			System.out.println("Another instance of this application is already running.");
		}
		//run application
		if (runnable) {
			parseArgs(args);
			Y3JRun y3JavaRun = new Y3JRun();
			y3JavaRun.run(DEBUG, DEBUG_CONSOLE);
		}

	}
	
	public static void parseArgs(String[] args) {
		if (args != null && args.length > 0) {
			DEBUG = Boolean.parseBoolean(args[0]);
			if (args.length> 1) {
				DEBUG_CONSOLE = Boolean.parseBoolean(args[1]);
			}
		}
	}

}
