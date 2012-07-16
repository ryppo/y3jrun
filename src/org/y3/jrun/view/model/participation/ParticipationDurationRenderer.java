package org.y3.jrun.view.model.participation;

import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DateFormatter;

import org.y3.jrun.model.participation.ParticipationDuration;
import org.y3.jrun.view.i18n.Messages;

public class ParticipationDurationRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean isSelected, boolean hasFocus, int row, int column)  {
		JFormattedTextField component = null;
		if (value != null && value instanceof ParticipationDuration) {
			ParticipationDuration pd = (ParticipationDuration) value;
			if (pd.getDurationFormat() != null) {
				component = new JFormattedTextField(new DateFormatter(new SimpleDateFormat(Messages.getString(pd.getDurationFormat().toString()))));
				component.setValue(pd.getResultTimeAsDate());
				component.setHorizontalAlignment(JTextField.RIGHT);
				component.setFocusLostBehavior(JFormattedTextField.COMMIT);
				component.validate();
			}
		} else {
			component = new JFormattedTextField();
		}
		if (isSelected) {
			component.setBackground(table.getSelectionBackground());
			component.setForeground(table.getSelectionForeground());
		} else {
			component.setBackground(new JFormattedTextField().getBackground());
			component.setForeground(new JFormattedTextField().getForeground());
		}
		return component;
	}

}
