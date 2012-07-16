/**
 * 
 */
package org.y3.jrun.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.i18n.Messages;

/**
 * @author ryppo
 *
 */
public class ApplicationMenubar extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	private ApplicationController controller;
	private ApplicationFrame appFrame;
	
	public ApplicationMenubar(ApplicationController _controller, ApplicationFrame _appFrame) {
		appFrame = _appFrame;
		controller = _controller;
		init();
	}
	
	private void init() {
		
		JMenu jmenu_file = new JMenu(Messages.getString(Messages.FILE));
		add(jmenu_file);
		
		JMenuItem jmi_createDatabase = new JMenuItem(Messages.getString(Messages.CREATE_DATABASE));
		jmi_createDatabase.setIcon(IconDictionary.getImageIcon(IconDictionary.DATABASE_ADD));
		jmenu_file.add(jmi_createDatabase);
		jmi_createDatabase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					boolean created = controller.createDatabase();
					if (created) {
						appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE, 
								Messages.getString(Messages.DATABASE_SUCCESSFULLY_CREATED), null);
					} else {
						appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, 
								Messages.getString(Messages.DATABASE_NOT_CREATED), null);
					}
				} catch (Exception e1) {
					appFrame.showUserMessage(e1, null);
				}
			}
		});
		
		JMenuItem jmi_removeDatabase = new JMenuItem(Messages.getString(Messages.REMOVE_DATABASE));
		jmi_removeDatabase.setIcon(IconDictionary.getImageIcon(IconDictionary.DATABASE_REMOVE));
		jmenu_file.add(jmi_removeDatabase);
		jmi_removeDatabase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					boolean removed = controller.removedDatabase();
					if (removed) {
						appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE, 
								Messages.getString(Messages.DATABASE_SUCCESSFULLY_REMOVED), null);
					} else {
						appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, 
								Messages.getString(Messages.DATABASE_NOT_REMOVED), null);
					}
				} catch (Exception e1) {
					appFrame.showUserMessage(e1, null);
				}
				appFrame.bindData();
			}
		});
		
		JSeparator separator = new JSeparator();
		jmenu_file.add(separator);
		
		JMenuItem jmi_connectDatabase = new JMenuItem(Messages.getString(Messages.RE_CONNECT_TO_DATABASE));
		jmi_connectDatabase.setIcon(IconDictionary.getImageIcon(IconDictionary.CONNECT));
		jmenu_file.add(jmi_connectDatabase);
		jmi_connectDatabase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					boolean connected = controller.connectDatabase();
					if (connected) {
						appFrame.bindData();
						appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE, 
								Messages.getString(Messages.DATABASE_SUCCESSFULLY_CONNECTED), null);
					} else {
						appFrame.showUserMessage(JOptionPane.WARNING_MESSAGE, 
								Messages.getString(Messages.DATABASE_NOT_CONNECTED), null);
					}
				} catch (Exception e1) {
					appFrame.showUserMessage(e1, null);
				}
			}
		});
		
		JMenuItem jmi_shutDown = new JMenuItem(Messages.getString(Messages.SHUT_DOWN));
		jmi_shutDown.setIcon(IconDictionary.getImageIcon(IconDictionary.SHUT_DOWN));
		jmenu_file.add(jmi_shutDown);
		jmi_shutDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				appFrame.actionShutDown();
			}
		});
	}	
	
}
