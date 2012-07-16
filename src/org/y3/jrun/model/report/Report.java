package org.y3.jrun.model.report;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

import org.y3.jrun.model.Model;
import org.y3.jrun.view.i18n.Messages;

public class Report extends Model {
	
	private String reportFile;
	private Map<String, Object> parameters;
	private JRDataSource reportableData;
	private String saveLocation;
	private String reportName;
	private boolean usePureParameterStringForName = false;
	
	public Report(String _reportName, String _reportFile, Map<String, Object> _parameters, JRDataSource _reportableData) {
		reportName = _reportName;
		reportFile = _reportFile;
		parameters = _parameters;
		reportableData = _reportableData;
	}
	
	public Report(String _reportName, String _reportFile, Map<String, Object> _parameters, JRDataSource _reportableData, boolean _usePureParameterStringForName) {
		reportName = _reportName;
		reportFile = _reportFile;
		parameters = _parameters;
		reportableData = _reportableData;
		usePureParameterStringForName = _usePureParameterStringForName;
	}

	/**
	 * @return the reportFile
	 */
	public String getReportFile() {
		return reportFile;
	}

	/**
	 * @param reportFile the reportFile to set
	 */
	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	/**
	 * @return the parameters
	 */
	public Map<String, Object> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the reportableData
	 */
	public JRDataSource getReportableData() {
		return reportableData;
	}

	/**
	 * @param reportableData the reportableData to set
	 */
	public void setReportableData(JRDataSource reportableData) {
		this.reportableData = reportableData;
	}

	@Override
	public String toString() {
		String toString;
		if (saveLocation != null && saveLocation.length() > 0) {
			toString = saveLocation;
		} else {
			toString = reportName;
		}
		if (parameters != null && !parameters.isEmpty()) {
			int paramCount = parameters.keySet().size();
			for (int paraPos = 0; paraPos < paramCount; paraPos++) {
				String paramValue = parameters.get(parameters.keySet().toArray()[paraPos].toString()).toString();
				if (!usePureParameterStringForName) {
					paramValue = Messages.getString(paramValue);
				}
				toString+= " - " + paramValue;				
			}
		}
		return toString;
	}
	
	@Override
	public String getNotificationTitle() {
		return getReportName();
	}
	
	@Override
	public String getNotificationMessage() {
		String value = "";
		if (parameters != null && !parameters.isEmpty()) {
			value = "parameters:\n";
			String paramValue = parameters.get(parameters.keySet().toArray()[0].toString()).toString();
			value+= " - " + paramValue;
		}
		return value;
	}

	@Override
	public void generateImportFingerPrint() {
	}

	public String getSaveLocation() {
		return saveLocation;
	}

	public void setSaveLocation(String saveLocation) {
		this.saveLocation = saveLocation;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

}
