package org.y3.jrun.view.model.ageclass;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.view.model.ModelForm;

public class AgeClassTableCellRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table,
            Object object,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
		if (object instanceof AgeClass) {
			AgeClassForm cell = new AgeClassForm(ModelForm.MODE.VIEWER);
			cell.setModel((AgeClass) object);
			if (isSelected) {
				cell.switchMode(ModelForm.MODE.EDITOR);
				cell.setBackground(table.getSelectionBackground());
				cell.setForegroundColor(table.getSelectionForeground());
			} else {
				cell.switchMode(ModelForm.MODE.VIEWER);
				cell.setBackground(new JLabel().getBackground());
				cell.setForeground(new JLabel().getForeground());
			}
			cell.setOpaque(table.isOpaque());
			return cell;
		}
		return new JLabel();
	}

}
