package org.y3.jrun.model.competition;

import org.y3.jrun.model.ModelListModel;

public class CompetitionListModel extends ModelListModel {
	
	private static final long serialVersionUID = 1L;
	
	public CompetitionListModel(Competition[] _competitions) {
		super(_competitions);
	}

	@Override
	public Competition getElementAt(int index) {
		return (Competition) super.getElementAt(index);
	}
	
	public Competition[] getModel() {
		return (Competition[]) super.getModel();
	}

}
