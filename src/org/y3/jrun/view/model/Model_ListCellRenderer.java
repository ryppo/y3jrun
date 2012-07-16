package org.y3.jrun.view.model;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.y3.jrun.model.Model;

public class Model_ListCellRenderer implements ListCellRenderer {
	
	@Override
	public Component getListCellRendererComponent(JList list, Object object,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (object instanceof Model) {
			ModelListCell cell = new ModelListCell((Model) object);
			if (isSelected) {
				cell.setBackground(list.getSelectionBackground());
				cell.setForegroundColor(list.getSelectionForeground());
			} else {
				cell.setBackground(list.getBackground());
				cell.setForeground(list.getForeground());
			}
			cell.setOpaque(list.isOpaque());
			
			return cell;
		}
		return new JLabel();
	}

}
