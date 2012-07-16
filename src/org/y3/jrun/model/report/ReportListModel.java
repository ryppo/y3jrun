package org.y3.jrun.model.report;

import org.y3.jrun.model.ModelListModel;

public class ReportListModel extends ModelListModel {

	private static final long serialVersionUID = 1L;

	public ReportListModel(Report[] models) {
		super(models);
	}
	
	@Override
	public Report getElementAt(int index) {
		return (Report) super.getElementAt(index);
	}
	
	public Report[] getModel() {
		return (Report[]) super.getModel();
	}

}
