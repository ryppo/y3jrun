package org.y3.jrun.view.model.participation;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.DateFormatter;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.participation.Participation;
import org.y3.jrun.model.participation.ParticipationDuration;
import org.y3.jrun.model.participation.RichParticipationsTableModel;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;

public class ParticipationDurationEditor extends DefaultCellEditor {

	private static final long serialVersionUID = 1L;
	private ApplicationController controller;
	private ParticipationDuration currentParticipationDuration;
	private JFormattedTextField currentFormattedTextField;
	private JTable currentTable;
	private int currentRow;
	private ApplicationFrame appFrame;

	public ParticipationDurationEditor(JCheckBox arg0) {
		super(arg0);
	}
	
	public ParticipationDurationEditor(ApplicationController _controller, ApplicationFrame _appFrame) {
		super (new JTextField());
		controller = _controller;
		appFrame = _appFrame;
	}
	
	public boolean stopCellEditing() {
		return true;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)  {
		currentFormattedTextField = null;
		currentParticipationDuration = null;
		currentRow = row;
		currentTable = table;
		if (value != null && value instanceof ParticipationDuration) {
			currentParticipationDuration = (ParticipationDuration) value;
			if (currentParticipationDuration.getDurationFormat() != null) {
				currentFormattedTextField = new JFormattedTextField(new DateFormatter(new SimpleDateFormat(
						Messages.getString(currentParticipationDuration.getDurationFormat().toString()))));
				currentFormattedTextField.setValue(currentParticipationDuration.getResultTimeAsDate());
				currentFormattedTextField.addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							UIHelper.startWaiting(appFrame);
							if (currentTable.getModel() instanceof RichParticipationsTableModel) {
								RichParticipationsTableModel ptm = (RichParticipationsTableModel) currentTable.getModel();
								Participation participation = ptm.getParticipationAtRow(currentRow);
								try {
									currentFormattedTextField.commitEdit();
								} catch (ParseException e2) {
									appFrame.showUserMessage(e2, participation);
								}
								participation.setResultTimeAsDate((Date) currentFormattedTextField.getValue());
								try {
									controller.saveModel(participation);
									appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE, Messages.getString(Messages.PARTICIPATION_SUCCESSFULLY_SAVED), participation);
								} catch (Exception e1) {
									appFrame.showUserMessage(e1, participation);
								}
							}
							UIHelper.stopWaiting(appFrame);
						}
					}
				});
				currentFormattedTextField.setHorizontalAlignment(JTextField.RIGHT);
				currentFormattedTextField.setFocusLostBehavior(JFormattedTextField.COMMIT);
				currentFormattedTextField.validate();
			}
		}
		return currentFormattedTextField;
	}
	
	public int getClickCountToStart() {
		return 1;
	}
	
	@Override
	public Object getCellEditorValue() {
		if (currentFormattedTextField != null) {
			return (Date) currentFormattedTextField.getValue();
		} else {
			return null;
		}
    }

}
