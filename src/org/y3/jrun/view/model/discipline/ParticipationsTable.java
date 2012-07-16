package org.y3.jrun.view.model.discipline;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.y3.jrun.model.participation.Participation;
import org.y3.jrun.model.participation.RichParticipationsTableModel;

public class ParticipationsTable extends JTable {
	
	public ParticipationsTable() {
		super();
		setAutoCreateRowSorter(true);
//		setDefaultRenderer(Date.class, new TableCellRenderer)
	}
	
	private static final long serialVersionUID = 1L;
	
	public RichParticipationsTableModel getParticipationsTableModel() {
		TableModel model = getModel();
		if (model instanceof RichParticipationsTableModel) {
			return (RichParticipationsTableModel) model;
	 	}
		return null;
	}
	
	public Participation getSelectedParticipation() {
		RichParticipationsTableModel tableModel = getParticipationsTableModel();
		if (tableModel != null) {
			int viewRow = getSelectedRow();
			int dataRow = convertRowIndexToModel(viewRow);
			Participation selection = tableModel.getParticipationAtRow(dataRow);
			return selection;
		} else {
			return null;
		}
	}

}
