package org.y3.jrun.model.participation;

import org.y3.jrun.model.ModelListModel;

public class ParticipationListModel extends ModelListModel {

	private static final long serialVersionUID = 1L;
	
	public ParticipationListModel(Participation[] _participations) {
		super(_participations);
	}
	
	@Override
	public Participation getElementAt(int arg0) {
		return (Participation) super.getElementAt(arg0);
	}

	public Participation[] getModel() {
		return (Participation[]) super.getModel();
	}

}
