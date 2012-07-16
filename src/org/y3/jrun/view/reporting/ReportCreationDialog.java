package org.y3.jrun.view.reporting;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.y3.jrun.model.report.Report;
import org.y3.jrun.model.report.ReportListModel;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;

public class ReportCreationDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JList reportsList;
	private ReportListModel reportListModel;
	private ApplicationFrame parent;
	private JTextField textfield_storageLocation;
	private JCheckBox checkbox_all;

	/**
	 * Create the dialog.
	 */
	public ReportCreationDialog(ApplicationFrame _parent) {
		parent = _parent;
		init();
	}
	
	public void setModel(ReportListModel _reportListModel) {
		reportListModel = _reportListModel;
		reportsList.setModel(reportListModel);
		if (reportListModel != null && reportListModel.getSize() > 0) {
			reportsList.setSelectedIndex(0);
		}
	}
	
	private void init() {
		setBounds(100, 100, 450, 300);
		setTitle(Messages.getString(Messages.REPORTS_CREATION));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel label_reportsToCreate = new JLabel(Messages.getString(Messages.REPORTS_TO_CREATE));
			GridBagConstraints gbc_label_reportsToCreate = new GridBagConstraints();
			gbc_label_reportsToCreate.insets = new Insets(5, 5, 5, 5);
			gbc_label_reportsToCreate.anchor = GridBagConstraints.NORTHEAST;
			gbc_label_reportsToCreate.gridx = 0;
			gbc_label_reportsToCreate.gridy = 0;
			contentPanel.add(label_reportsToCreate, gbc_label_reportsToCreate);
		}
		{
			reportsList = new JList();
			GridBagConstraints gbc_list = new GridBagConstraints();
			gbc_list.gridheight = 2;
			gbc_list.gridwidth = 2;
			gbc_list.insets = new Insets(5, 5, 5, 0);
			gbc_list.fill = GridBagConstraints.BOTH;
			gbc_list.gridx = 1;
			gbc_list.gridy = 0;
			contentPanel.add(new JScrollPane(reportsList), gbc_list);
		}
		{
			checkbox_all = new JCheckBox(Messages.getString(Messages.ALL));
			checkbox_all.addActionListener(getActionListenerForActionToSelectAllReports());
			checkbox_all.setHorizontalTextPosition(SwingConstants.LEFT);
			GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
			gbc_chckbxNewCheckBox.anchor = GridBagConstraints.EAST;
			gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxNewCheckBox.gridx = 0;
			gbc_chckbxNewCheckBox.gridy = 1;
			contentPanel.add(checkbox_all, gbc_chckbxNewCheckBox);
		}
		{
			JLabel label_saveLocation = new JLabel(Messages.getString(Messages.SAVE_LOCATION));
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
			contentPanel.add(textfield_storageLocation, gbc_textfield_storageLocation);
			textfield_storageLocation.setColumns(10);
		}
		{
			JButton button_selectStorageLocation = new JButton("...");
			button_selectStorageLocation.addActionListener(getActionListenerForActionToSelectStorageLocation());
			GridBagConstraints gbc_button_selectStorageLocation = new GridBagConstraints();
			gbc_button_selectStorageLocation.gridx = 2;
			gbc_button_selectStorageLocation.gridy = 2;
			contentPanel.add(button_selectStorageLocation, gbc_button_selectStorageLocation);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton previewButton = new JButton(Messages.getString(Messages.PREVIEW));
				previewButton.addActionListener(getActionListenerForActionToPreview());
				{
					JButton button_print = new JButton(Messages.getString(Messages.PRINT));
					button_print.addActionListener(getActionListenerForActionToPrint());
					{
						JButton button_exportToHTML = new JButton(Messages.getString(Messages.EXPORT_TO_HTML));
						button_exportToHTML.addActionListener(getActionListenerForActionToExportToHTML());
						{
							JButton button_exportToPDF = new JButton(Messages.getString(Messages.EXPORT_TO_PDF));
							button_exportToPDF.addActionListener(getActionListenerForActionToExportToPDF());
							buttonPane.add(button_exportToPDF);
						}
						buttonPane.add(button_exportToHTML);
					}
					buttonPane.add(button_print);
				}
				{
					JButton button_printWithDialog = new JButton(Messages.getString(Messages.PRINT_WITH_DIALOG));
					button_printWithDialog.addActionListener(getActionListenerForActionToPrintWithDialog());
					buttonPane.add(button_printWithDialog);
				}
				buttonPane.add(previewButton);
				getRootPane().setDefaultButton(previewButton);
			}
			{
				JButton cancelButton = new JButton(Messages.getString(Messages.CANCEL));
				cancelButton.addActionListener(getActionListenerForActionCancel());
				buttonPane.add(cancelButton);
			}
		}
		pack();
		setLocationRelativeTo(parent);
	}
	
	private ActionListener getActionListenerForActionToSelectAllReports() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkbox_all.isSelected()) {
					reportsList.setSelectionInterval(0, reportsList.getModel().getSize() - 1);
				} else {
					reportsList.setSelectedIndex(0);
				}
			}
		};
	}

	private ActionListener getActionListenerForActionToPreview() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UIHelper.startWaiting(ReportCreationDialog.this);
				Object[] selectedObjects = reportsList.getSelectedValues();
				if (selectedObjects != null && selectedObjects.length > 0) {
					for (Object selectedObject: selectedObjects) {
						if (selectedObject != null && selectedObject instanceof Report) {
							ReportingController.preview((Report) selectedObject, parent);							
						}
					}
					setVisible(false);
				}
				UIHelper.startWaiting(ReportCreationDialog.this);
			}
		};
	}
	
	private ActionListener getActionListenerForActionToPrint() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] selectedObjects = reportsList.getSelectedValues();
				if (selectedObjects != null && selectedObjects.length > 0) {
					for (Object selectedObject: selectedObjects) {
						if (selectedObject != null && selectedObject instanceof Report) {
							ReportingController.print((Report) selectedObject, false);
							parent.logMessage(JOptionPane.INFORMATION_MESSAGE, Messages.getString(Messages.REPORT_CREATED_SUCCESSFULLY), (Report) selectedObject);
						}
					}
					setVisible(false);
				}
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
				} else {
					Report report = (Report) reportsList.getModel().getElementAt(0);
					fc.setSelectedFile(new File(report.getReportName()));
				}
				int returnValue = fc.showSaveDialog(ReportCreationDialog.this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					textfield_storageLocation.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		};
	}
	
	private ActionListener getActionListenerForActionToExportToHTML() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] selectedObjects = reportsList.getSelectedValues();
				if (selectedObjects != null && selectedObjects.length > 0) {
					for (Object selectedObject: selectedObjects) {
						if (selectedObject != null && selectedObject instanceof Report) {
							Report report = (Report) selectedObject;
							report.setSaveLocation(textfield_storageLocation.getText() + " _ " + report.toString());
							ReportingController.exportToHTML(report);
							parent.logMessage(JOptionPane.INFORMATION_MESSAGE, Messages.getString(Messages.REPORT_CREATED_SUCCESSFULLY), (Report) selectedObject);
						}
					}
					setVisible(false);
				}
			}
		};
	}
	
	private ActionListener getActionListenerForActionToExportToPDF() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] selectedObjects = reportsList.getSelectedValues();
				if (selectedObjects != null && selectedObjects.length > 0) {
					for (Object selectedObject: selectedObjects) {
						if (selectedObject != null && selectedObject instanceof Report) {
							Report report = (Report) selectedObject;
							report.setSaveLocation(textfield_storageLocation.getText() + " _ " + report.toString());
							ReportingController.exportToPDF(report);
							parent.logMessage(JOptionPane.INFORMATION_MESSAGE, Messages.getString(Messages.REPORT_CREATED_SUCCESSFULLY), (Report) selectedObject);
						}
					}
					setVisible(false);
				}
			}
		};
	}
	
	private ActionListener getActionListenerForActionToPrintWithDialog() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] selectedObjects = reportsList.getSelectedValues();
				if (selectedObjects != null && selectedObjects.length > 0) {
					for (Object selectedObject: selectedObjects) {
						if (selectedObject != null && selectedObject instanceof Report) {
							boolean success = ReportingController.print((Report) selectedObject, true);
							if (success) {
								parent.logMessage(JOptionPane.INFORMATION_MESSAGE, Messages.getString(Messages.REPORT_CREATED_SUCCESSFULLY), (Report) selectedObject);
							}
						}
					}
					setVisible(false);
				}
			}
		};
	}
	
	private ActionListener getActionListenerForActionCancel() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		};
	}
	
}
