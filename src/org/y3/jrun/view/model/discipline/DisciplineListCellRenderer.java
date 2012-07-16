package org.y3.jrun.view.model.discipline;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.competition.Competition;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.UIHelper;

public class DisciplineListCellRenderer extends DefaultListCellRenderer {
	
	private static final long serialVersionUID = 1L;
	
	private ApplicationFrame mainFrame = null;
	private ApplicationController controller = null;
	private Competition competition = null;
	
	public DisciplineListCellRenderer(ApplicationFrame appFrame, ApplicationController appController, Competition _competition) {
		controller = appController;
		competition = _competition;
	}

	public Component getListCellRendererComponent(
			JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
		
		JLabel jl_listItem =
			(JLabel) super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
		jl_listItem.setHorizontalTextPosition(JLabel.RIGHT);
		
		if (value instanceof Discipline) {
			Discipline selectedDiscipline = (Discipline) value;
			int countChilds = 0;
			if (selectedDiscipline != null && controller != null && competition != null) {
				try {
					countChilds = controller.countParticipationsForDiscipline(selectedDiscipline, competition);
				} catch (Exception e) {
					if (mainFrame != null) {
						mainFrame.showUserMessage(e, selectedDiscipline);
					} else {
						e.printStackTrace();
					}
				}
			}
			jl_listItem.setText(jl_listItem.getText());
			jl_listItem.setIcon(UIHelper.createBadge((String.valueOf(countChilds))));
		}
		
		return jl_listItem;
	}
	
}
