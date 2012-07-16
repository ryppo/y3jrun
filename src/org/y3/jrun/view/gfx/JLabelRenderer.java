package org.y3.jrun.view.gfx;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class JLabelRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, 
			boolean isSelected, boolean hasFocus, int row, int column)  {
		if (value instanceof JLabel) {
			JLabel jlabel = (JLabel) value;
			if (isSelected) {
				jlabel.setOpaque(true);
				jlabel.setBackground(table.getSelectionBackground());
				jlabel.setForeground(table.getSelectionForeground());
			} else {
				jlabel.setBackground(new JLabel().getBackground());
				jlabel.setForeground(new JLabel().getForeground());
			}
			return jlabel;
		}
		return null;
	}

}
