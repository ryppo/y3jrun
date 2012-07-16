package org.y3.jrun.view.model.ageclassesdefinition;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.ageclassesdefinition.RichAgeClassesDefinition;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;

public class AgeClassesDefinitionExchangeDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private ApplicationFrame parent;
	private ApplicationController applicationController;
	private final JPanel contentPanel = new JPanel();
	private JTextField textfield_storageLocation;
	private RichAgeClassesDefinition[] dataModel;
	private MODE actualMode = MODE.EXPORT;
	
	private boolean imported = false;
	
	public enum MODE { EXPORT, IMPORT }

	public AgeClassesDefinitionExchangeDialog(ApplicationFrame _parent,
			ApplicationController _controller, MODE _mode) {
		super(_parent, true);
		parent = _parent;
		actualMode = _mode;
		applicationController = _controller;
		init();
	}

	public void setModel(RichAgeClassesDefinition[] _dataModel) {
		dataModel = _dataModel;
	}
	
	private void init() {
		setBounds(100, 100, 500, 300);
		if (actualMode == MODE.EXPORT) {
			setTitle(Messages.getString(Messages.EXPORT));			
		} else if (actualMode == MODE.IMPORT) {
			setTitle(Messages.getString(Messages.IMPORT));
		}
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 1.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel label_saveLocation = new JLabel(
					Messages.getString(Messages.SAVE_LOCATION));
			GridBagConstraints gbc_label_saveLocation = new GridBagConstraints();
			gbc_label_saveLocation.anchor = GridBagConstraints.EAST;
			gbc_label_saveLocation.insets = new Insets(0, 0, 0, 5);
			gbc_label_saveLocation.gridx = 0;
			gbc_label_saveLocation.gridy = 2;
			contentPanel.add(label_saveLocation, gbc_label_saveLocation);
		}
		{
			textfield_storageLocation = new JTextField();
			textfield_storageLocation.setEditable(false);
			GridBagConstraints gbc_textfield_storageLocation = new GridBagConstraints();
			gbc_textfield_storageLocation.insets = new Insets(0, 0, 0, 5);
			gbc_textfield_storageLocation.fill = GridBagConstraints.HORIZONTAL;
			gbc_textfield_storageLocation.gridx = 1;
			gbc_textfield_storageLocation.gridy = 2;
			contentPanel.add(textfield_storageLocation,
					gbc_textfield_storageLocation);
			textfield_storageLocation.setColumns(10);
		}
		{
			JButton button_selectStorageLocation = new JButton("...");
			button_selectStorageLocation
					.addActionListener(getActionListenerForActionToSelectStorageLocation());
			GridBagConstraints gbc_button_selectStorageLocation = new GridBagConstraints();
			gbc_button_selectStorageLocation.gridx = 2;
			gbc_button_selectStorageLocation.gridy = 2;
			contentPanel.add(button_selectStorageLocation,
					gbc_button_selectStorageLocation);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			JButton button_execute = new JButton();
			if (actualMode == MODE.EXPORT) {
				button_execute.setText(Messages.getString(Messages.EXPORT));
			} else if (actualMode == MODE.IMPORT) {
				button_execute.setText(Messages.getString(Messages.IMPORT));
			}
			button_execute
					.addActionListener(getActionListenerForActionToExport());
			buttonPane.add(button_execute);
			JButton cancelButton = new JButton(
					Messages.getString(Messages.CANCEL));
			cancelButton.addActionListener(getActionListenerForActionCancel());
			buttonPane.add(cancelButton);
		}
		pack();
		setSize(600, getSize().height);
		setLocationRelativeTo(parent);
	}

	private ActionListener getActionListenerForActionCancel() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		};
	}

	private ActionListener getActionListenerForActionToSelectStorageLocation() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String actualLocation = textfield_storageLocation.getText();
				final JFileChooser fc = new JFileChooser();
				if (actualLocation != null && actualLocation.length() > 0) {
					fc.setSelectedFile(new File(actualLocation));
				}
				int returnValue = fc
						.showSaveDialog(AgeClassesDefinitionExchangeDialog.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					textfield_storageLocation.setText(fc.getSelectedFile()
							.getAbsolutePath());
				}
			}
		};
	}

	private ActionListener getActionListenerForActionToExport() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UIHelper.startWaiting(AgeClassesDefinitionExchangeDialog.this);
					boolean succcess = false;
					String successMessage = null;
					String noSuccessMessage = null;
					if (actualMode == MODE.EXPORT) {
						successMessage = Messages.getString(Messages.SUCCESSFULLY_EXPORTED);
						noSuccessMessage = Messages.getString(Messages.NOT_SUCCESSFULLY_EXPORTED);
						succcess = applicationController
								.exportRichAgeClassesDefinitions(dataModel, textfield_storageLocation.getText());			
					} else if (actualMode == MODE.IMPORT) {
						imported = false;
						successMessage = Messages.getString(Messages.SUCCESSFULLY_IMPORTED);
						noSuccessMessage = Messages.getString(Messages.NOT_SUCCESSFULLY_IMPORTED);
						succcess = applicationController
								.importRichAgeClassesDefinitions(textfield_storageLocation.getText());
					}
					if (succcess) {
						imported = true;
						parent.showUserMessage(
								JOptionPane.INFORMATION_MESSAGE,
								successMessage,
								null);
					} else {
						parent.showUserMessage(
								JOptionPane.ERROR_MESSAGE,
								noSuccessMessage,
								null);
					}
				} catch (Exception e1) {
					parent.showUserMessage(e1, null);
				} finally {
					UIHelper.stopWaiting(AgeClassesDefinitionExchangeDialog.this);
					setVisible(false);
				}
			}
		};
	};
	
	public boolean isImported() {
		return imported;
	}
}