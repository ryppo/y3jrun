package org.y3.jrun.view.model.ageclass;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.model.ModelForm;

public class AgeClassTableCellEditor implements TableCellEditor {
	
	private AgeClassForm editor = new AgeClassForm(ModelForm.MODE.EDITOR);
	private ApplicationFrame applicationFrame;
	private ApplicationController applicationController;
	
	public AgeClassTableCellEditor(ApplicationFrame _applicationFrame, ApplicationController _applicationController) {
		applicationFrame = _applicationFrame;
		applicationController = _applicationController;
	}

	@Override
	public AgeClass getCellEditorValue() {
		return editor.getModel();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int rowIndex, int vColIndex) {
		editor.setBackground(table.getSelectionBackground());
		if (value instanceof AgeClass) {
			editor.setOpaque(table.isOpaque());
		}
		if (value instanceof AgeClass) {
			editor.setModel((AgeClass) value);
			return editor;
		}
		return null;
	}

	@Override
	public void addCellEditorListener(CellEditorListener arg0) {
	}

	@Override
	public void cancelCellEditing() {
	}

	@Override
	public boolean isCellEditable(EventObject arg0) {
		return true;
	}

	@Override
	public void removeCellEditorListener(CellEditorListener arg0) {
		
	}

	@Override
	public boolean shouldSelectCell(EventObject arg0) {
		return true;
	}

	@Override
	public boolean stopCellEditing() {
		UIHelper.startWaiting(applicationFrame);
		AgeClass model = editor.getModel();
		if (editor != null && model != null) {
			try {
				applicationController.saveModel(model);
			} catch (Exception e) {
				applicationFrame.showUserMessage(e, model);
			}
		}
		UIHelper.stopWaiting(applicationFrame);
		return true;
	}

}
