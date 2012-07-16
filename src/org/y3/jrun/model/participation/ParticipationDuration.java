package org.y3.jrun.model.participation;

import java.util.Date;

import org.y3.jrun.model.discipline.Discipline;

public class ParticipationDuration {

	private long resultTime = -3600000;
	private Discipline.durationFormat durationFormat = Discipline.durationFormat.HH_mm_ss;
	
	public Date getResultTimeAsDate() {
		return new Date(resultTime);
	}
	public void setResultTimeAsDate(Date _resultTime) {
		if (_resultTime != null) {
			resultTime = _resultTime.getTime();
		} else {
			resultTime = 0;
		}
	}
	
	public Discipline.durationFormat getDurationFormat() {
		return durationFormat;
	}
	public void setDurationFormat(Discipline.durationFormat durationFormat) {
		this.durationFormat = durationFormat;
	}
	
}
