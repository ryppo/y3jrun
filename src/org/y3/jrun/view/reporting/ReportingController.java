package org.y3.jrun.view.reporting;

import javax.swing.JFrame;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.swing.JRViewer;

import org.y3.jrun.model.report.Report;
import org.y3.jrun.model.report.ReportListModel;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;

public class ReportingController {
	
	public static void report(ReportListModel reportListModel, ApplicationFrame parent) {
		UIHelper.startWaiting(parent);
		ReportCreationDialog reportCreationDialog = new ReportCreationDialog(parent);
		reportCreationDialog.setModel(reportListModel);
		reportCreationDialog.setVisible(true);
		UIHelper.stopWaiting(parent);
	}
	
	public static void report(Report[] report, ApplicationFrame parent) {
		UIHelper.startWaiting(parent);
		ReportListModel reportListModel = new ReportListModel(report);
		report(reportListModel, parent);
		UIHelper.stopWaiting(parent);
	}
	
	public static void report(Report report, ApplicationFrame parent) {
		UIHelper.startWaiting(parent);
		ReportListModel reportListModel = new ReportListModel(new Report[]{report});
		report(reportListModel, parent);
		UIHelper.stopWaiting(parent);
	}
	
	public static void preview(Report report, JFrame parent) {
		UIHelper.startWaiting(parent);
		try {
			JasperPrint print = JasperFillManager.fillReport(
					ReportsDictionary.getReport(report.getReportFile()), report.getParameters(), report.getReportableData());
			JRViewer viewer = new JRViewer(print);
			JFrame jf = new JFrame(Messages.getString(Messages.PREVIEW));
			jf.getContentPane().add(viewer);
			jf.validate();
			if(print.getOrientationValue().getName().equals(OrientationEnum.LANDSCAPE.getName())) {
				jf.setSize(1000, 800);
			} else {
				jf.setSize(800, 1000);
			}
			jf.setLocationRelativeTo(parent);
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		UIHelper.stopWaiting(parent);
	}
	
	public static boolean print(Report report, boolean printWithDialog) {
		boolean success = false;
		try {
			JasperPrint print = JasperFillManager.fillReport(
					ReportsDictionary.getReport(report.getReportFile()), report.getParameters(), report.getReportableData());
			success = JasperPrintManager.printReport(print, printWithDialog);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return success;
	}
	
	public static void exportToHTML(Report report) {
		try {
			String reportName = report.toString() + ".html";
			JasperPrint print = JasperFillManager.fillReport(
					ReportsDictionary.getReport(report.getReportFile()), report.getParameters(), report.getReportableData());
			JasperExportManager.exportReportToHtmlFile(print, reportName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void exportToPDF(Report report) {
		try {
			String reportName = report.toString() + ".pdf";
			JasperPrint print = JasperFillManager.fillReport(
					ReportsDictionary.getReport(report.getReportFile()), report.getParameters(), report.getReportableData());
			JasperExportManager.exportReportToPdfFile(print, reportName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
