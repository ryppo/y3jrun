package org.y3.jrun.view.model.competition;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.lang.StringUtils;
import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.competition.Competition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.contact.ContactListModel;
import org.y3.jrun.model.participation.ParticipationListModel;
import org.y3.jrun.model.participation.RichParticipation;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.UIHelper;
import org.y3.jrun.view.i18n.Messages;
import org.y3.jrun.view.model.contact.ContactComparator;

public class CompetitionStatisticsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ApplicationController controller;
	private ApplicationFrame appFrame;
	private JLabel label_countParticipationsValue;
	private Competition competition;

	private JLabel label_oldestValue;

	private JLabel label_youngestValue;

	private JLabel label_youngestYear;

	private JLabel label_oldestYear;
	private JLabel label_notPaidFee;
	private JLabel label_notPaidValue;
	private JList list_notPaidList;
	private JLabel label_noResultTime;
	private JLabel label_noResultTimeValue;
	private JList list_noResultTimeList;
	private JLabel label_noCertification;
	private JList list_noCertificationList;
	private JLabel label_countNoCertificates;
	private JLabel label_donationHospiz;
	private JLabel label_donationHospizValue;

	/**
	 * Create the panel.
	 */
	public CompetitionStatisticsPanel(ApplicationController _controller, Competition _competition, ApplicationFrame _appFrame) {
		controller = _controller;
		competition = _competition;
		appFrame = _appFrame;
		init();
		bindData();
	}
	
	private void init() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel label_countParticipations = new JLabel(Messages.getString(Messages.PARTICIPATIONS) + ":");
		GridBagConstraints gbc_label_countParticipations = new GridBagConstraints();
		gbc_label_countParticipations.insets = new Insets(5, 5, 5, 5);
		gbc_label_countParticipations.anchor = GridBagConstraints.EAST;
		gbc_label_countParticipations.gridx = 0;
		gbc_label_countParticipations.gridy = 0;
		add(label_countParticipations, gbc_label_countParticipations);
		
		label_countParticipationsValue = new JLabel();
		GridBagConstraints gbc_label_countParticipationsValue = new GridBagConstraints();
		gbc_label_countParticipationsValue.anchor = GridBagConstraints.EAST;
		gbc_label_countParticipationsValue.insets = new Insets(5, 5, 5, 5);
		gbc_label_countParticipationsValue.gridx = 2;
		gbc_label_countParticipationsValue.gridy = 0;
		add(label_countParticipationsValue, gbc_label_countParticipationsValue);	
		
		JLabel label_oldest = new JLabel(Messages.getString(Messages.OLDEST_PARTICIPANT) + ":");
		GridBagConstraints gbc_lblOldest = new GridBagConstraints();
		gbc_lblOldest.anchor = GridBagConstraints.EAST;
		gbc_lblOldest.insets = new Insets(5, 5, 5, 5);
		gbc_lblOldest.gridx = 0;
		gbc_lblOldest.gridy = 1;
		add(label_oldest, gbc_lblOldest);
		
		label_oldestYear = new JLabel("");
		GridBagConstraints gbc_label_oldestYear = new GridBagConstraints();
		gbc_label_oldestYear.insets = new Insets(5, 5, 5, 5);
		gbc_label_oldestYear.gridx = 2;
		gbc_label_oldestYear.gridy = 1;
		add(label_oldestYear, gbc_label_oldestYear);
		
		label_oldestValue = new JLabel("");
		GridBagConstraints gbc_label_oldestValue = new GridBagConstraints();
		gbc_label_oldestValue.anchor = GridBagConstraints.WEST;
		gbc_label_oldestValue.insets = new Insets(5, 5, 5, 0);
		gbc_label_oldestValue.gridx = 3;
		gbc_label_oldestValue.gridy = 1;
		add(label_oldestValue, gbc_label_oldestValue);
		
		JLabel label_youngest = new JLabel(Messages.getString(Messages.YOUNGEST_PARTICIPANT) + ":");
		GridBagConstraints gbc_lblYoungest = new GridBagConstraints();
		gbc_lblYoungest.anchor = GridBagConstraints.EAST;
		gbc_lblYoungest.insets = new Insets(5, 5, 5, 5);
		gbc_lblYoungest.gridx = 0;
		gbc_lblYoungest.gridy = 2;
		add(label_youngest, gbc_lblYoungest);
		
		label_youngestYear = new JLabel("");
		GridBagConstraints gbc_label_youngestYear = new GridBagConstraints();
		gbc_label_youngestYear.insets = new Insets(5, 5, 5, 5);
		gbc_label_youngestYear.gridx = 2;
		gbc_label_youngestYear.gridy = 2;
		add(label_youngestYear, gbc_label_youngestYear);
		
		label_youngestValue = new JLabel("");
		GridBagConstraints gbc_label_youngestValue = new GridBagConstraints();
		gbc_label_youngestValue.anchor = GridBagConstraints.WEST;
		gbc_label_youngestValue.insets = new Insets(5, 5, 5, 0);
		gbc_label_youngestValue.gridx = 3;
		gbc_label_youngestValue.gridy = 2;
		add(label_youngestValue, gbc_label_youngestValue);
		
		label_donationHospiz = new JLabel(Messages.getString(Messages.COMPETITION_DONATION_HOSPIZ_SUMMARY));
		GridBagConstraints gbc_label_donationHospiz = new GridBagConstraints();
		gbc_label_donationHospiz.anchor = GridBagConstraints.EAST;
		gbc_label_donationHospiz.insets = new Insets(0, 0, 5, 5);
		gbc_label_donationHospiz.gridx = 0;
		gbc_label_donationHospiz.gridy = 3;
		add(label_donationHospiz, gbc_label_donationHospiz);
		
		label_donationHospizValue = new JLabel();
		GridBagConstraints gbc_label_donationHospizValue = new GridBagConstraints();
		gbc_label_donationHospizValue.anchor = GridBagConstraints.WEST;
		gbc_label_donationHospizValue.insets = new Insets(0, 0, 5, 0);
		gbc_label_donationHospizValue.gridx = 2;
		gbc_label_donationHospizValue.gridy = 3;
		add(label_donationHospizValue, gbc_label_donationHospizValue);
		
		label_notPaidFee = new JLabel(Messages.getString(Messages.NOT_FEE_PAID_PARTICIPANT) + ":");
		GridBagConstraints gbc_label_notPaidFee = new GridBagConstraints();
		gbc_label_notPaidFee.anchor = GridBagConstraints.NORTHEAST;
		gbc_label_notPaidFee.insets = new Insets(5, 5, 5, 5);
		gbc_label_notPaidFee.gridx = 0;
		gbc_label_notPaidFee.gridy = 4;
		add(label_notPaidFee, gbc_label_notPaidFee);
		
		label_notPaidValue = new JLabel("");
		GridBagConstraints gbc_label_notPaidValue = new GridBagConstraints();
		gbc_label_notPaidValue.anchor = GridBagConstraints.NORTHEAST;
		gbc_label_notPaidValue.insets = new Insets(5, 5, 5, 5);
		gbc_label_notPaidValue.gridx = 2;
		gbc_label_notPaidValue.gridy = 4;
		add(label_notPaidValue, gbc_label_notPaidValue);
		
		list_notPaidList = new JList();
		GridBagConstraints gbc_list_notPaidList = new GridBagConstraints();
		gbc_list_notPaidList.insets = new Insets(5, 5, 5, 0);
		gbc_list_notPaidList.fill = GridBagConstraints.BOTH;
		gbc_list_notPaidList.gridx = 3;
		gbc_list_notPaidList.gridy = 4;
		add(new JScrollPane(list_notPaidList), gbc_list_notPaidList);
		
		label_noResultTime = new JLabel(Messages.getString(Messages.NO_RESULT_TIME_PARTICIPANT) + ":");
		GridBagConstraints gbc_label_noResultTime = new GridBagConstraints();
		gbc_label_noResultTime.anchor = GridBagConstraints.NORTHEAST;
		gbc_label_noResultTime.insets = new Insets(5, 5, 5, 5);
		gbc_label_noResultTime.gridx = 0;
		gbc_label_noResultTime.gridy = 5;
		add(label_noResultTime, gbc_label_noResultTime);
		
		label_noResultTimeValue = new JLabel();
		GridBagConstraints gbc_lblTest = new GridBagConstraints();
		gbc_lblTest.insets = new Insets(5, 5, 5, 5);
		gbc_lblTest.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblTest.gridx = 2;
		gbc_lblTest.gridy = 5;
		add(label_noResultTimeValue, gbc_lblTest);
		
		list_noResultTimeList = new JList();
		GridBagConstraints gbc_list_noResultTimeList = new GridBagConstraints();
		gbc_list_noResultTimeList.insets = new Insets(5, 5, 5, 0);
		gbc_list_noResultTimeList.fill = GridBagConstraints.BOTH;
		gbc_list_noResultTimeList.gridx = 3;
		gbc_list_noResultTimeList.gridy = 5;
		add(new JScrollPane(list_noResultTimeList), gbc_list_noResultTimeList);
		
		label_noCertification = new JLabel(Messages.getString(Messages.NO_CERTIFICATION_PARTICIPANT) + ":");
		GridBagConstraints gbc_label_noCertification = new GridBagConstraints();
		gbc_label_noCertification.anchor = GridBagConstraints.NORTHEAST;
		gbc_label_noCertification.insets = new Insets(5, 5, 0, 5);
		gbc_label_noCertification.gridx = 0;
		gbc_label_noCertification.gridy = 6;
		add(label_noCertification, gbc_label_noCertification);
		
		label_countNoCertificates = new JLabel();
		GridBagConstraints gbc_label_countNoCertificates = new GridBagConstraints();
		gbc_label_countNoCertificates.anchor = GridBagConstraints.NORTHEAST;
		gbc_label_countNoCertificates.insets = new Insets(5, 5, 0, 5);
		gbc_label_countNoCertificates.gridx = 2;
		gbc_label_countNoCertificates.gridy = 6;
		add(label_countNoCertificates, gbc_label_countNoCertificates);
		
		list_noCertificationList = new JList();
		GridBagConstraints gbc_list_noCertificationList = new GridBagConstraints();
		gbc_list_noCertificationList.insets = new Insets(5, 5, 0, 0);
		gbc_list_noCertificationList.fill = GridBagConstraints.BOTH;
		gbc_list_noCertificationList.gridx = 3;
		gbc_list_noCertificationList.gridy = 6;
		add(new JScrollPane(list_noCertificationList), gbc_list_noCertificationList);
	}
	
	private void bindData() {
		UIHelper.startWaiting(appFrame);
		if (controller != null) {
			try {
				ParticipationListModel participations = controller.getAllParticipationsForCompetition(competition);
				if (participations != null) {
					//count participations
					int countParticipations = 0;
					countParticipations = participations.getSize();
					label_countParticipationsValue.setText(Integer.toString(countParticipations));
					//find oldest and youngest participants
					RichParticipation[] richParticipations = controller.getRichParticipationsForParticipations(participations.getModel());
					int oldestBirthYear = getOldestBirthyear(richParticipations);
					label_oldestYear.setText(Integer.toString(oldestBirthYear));
					String oldestParticipants = formatAgeParticipantsListToString(oldestBirthYear, getParticipantsForBirthyear(oldestBirthYear, richParticipations));
					label_oldestValue.setText(oldestParticipants);
					int youngestBirthYear = getYoungestBirthYear(richParticipations);
					label_youngestYear.setText(Integer.toString(youngestBirthYear));
					String youngestParticipant = formatAgeParticipantsListToString(youngestBirthYear, getParticipantsForBirthyear(youngestBirthYear, richParticipations));
					label_youngestValue.setText(youngestParticipant);
					//hospiz donation summary
					int donationSummaryInEuroCent = controller.getHospizDonationSummaryInEuroCentForCompetition(competition);
					double donationSummaryInEuro = (double) donationSummaryInEuroCent / 100;
					label_donationHospizValue.setText(Double.toString(donationSummaryInEuro));
					//not paid fee
					Contact[] notPaidFeeContacts = getParticipantsWithNotPaidFee(richParticipations);
					int countNotPaidFee = 0;
					if (notPaidFeeContacts != null) {
						countNotPaidFee = notPaidFeeContacts.length;
					}
					label_notPaidValue.setText(Integer.toString(countNotPaidFee));
					list_notPaidList.setModel(new ContactListModel(notPaidFeeContacts));
					//no result time
					Contact[] noResultTimeContacts = getParticipantsWithNoResultTime(richParticipations);
					int countNoResultTime = 0;
					if (noResultTimeContacts != null) {
						countNoResultTime = noResultTimeContacts.length;
					}
					label_noResultTimeValue.setText(Integer.toString(countNoResultTime));
					list_noResultTimeList.setModel(new ContactListModel(noResultTimeContacts));
					//no certification
					Contact[] noCertificationContacts = getParticipantsWithNoCertificate(richParticipations);
					int countNoCertficate = 0;
					if (noCertificationContacts != null) {
						countNoCertficate = noCertificationContacts.length;
					}
					label_countNoCertificates.setText(Integer.toString(countNoCertficate));
					list_noCertificationList.setModel(new ContactListModel(noCertificationContacts));
				}
			} catch (Exception e) {
				appFrame.showUserMessage(e, competition);
			}
		}
		UIHelper.stopWaiting(appFrame);
	}
	
	private Contact[] getParticipantsWithNoCertificate(RichParticipation[] sourceList) {
		Contact[] targetList = null;
		int countThem = 0;
		for (RichParticipation actual: sourceList) {
			if (actual != null && !actual.isCertificationHandedOver()) {
				countThem++;
			}
		}
		if (countThem > 0) {
			targetList = new Contact[countThem];
			int targetPos = 0;
			for (RichParticipation actual: sourceList) {
				if (actual != null && !actual.isCertificationHandedOver()) {
					targetList[targetPos] = actual.getParticipant();
					targetPos++;
				}
			}
			Arrays.sort(targetList, new ContactComparator());
		}
		return targetList;
	}
	
	private Contact[] getParticipantsWithNoResultTime(RichParticipation[] sourceList) {
		Contact[] targetList = null;
		int countThem = 0;
		for (RichParticipation actual: sourceList) {
			if (actual != null && !actual.isResultTimeSet()) {
				countThem++;
			}
		}
		if (countThem > 0) {
			targetList = new Contact[countThem];
			int targetPos = 0;
			for (RichParticipation actual: sourceList) {
				if (actual != null && !actual.isResultTimeSet()) {
					targetList[targetPos] = actual.getParticipant();
					targetPos++;
				}
			}
			Arrays.sort(targetList, new ContactComparator());
		}
		return targetList;
	}
	
	private Contact[] getParticipantsWithNotPaidFee(RichParticipation[] sourceList) {
		Contact[] targetList = null;
		int countThem = 0;
		for (RichParticipation actual: sourceList) {
			if (actual != null && !actual.isPaymentDone()) {
				countThem++;
			}
		}
		if (countThem > 0) {
			targetList = new Contact[countThem];
			int targetPos = 0;
			for (RichParticipation actual: sourceList) {
				if (actual != null && !actual.isPaymentDone()) {
					targetList[targetPos] = actual.getParticipant();
					targetPos++;
				}
			}
			Arrays.sort(targetList, new ContactComparator());
		}
		return targetList;
	}
	
	private String formatAgeParticipantsListToString(int birthYear, Contact[] ageParticipants) {
		String toString = null;
		toString = StringUtils.join(ageParticipants, " / ");
		return toString;
	}
	
	private int getOldestBirthyear(RichParticipation[] richParticipations) {
		int oldestBirthYear = 3000;
		for (RichParticipation actualParticipation: richParticipations) {
			if (actualParticipation != null && actualParticipation.getParticipant() != null) {
				int checkBirthYear = actualParticipation.getParticipant().getBirthyear(); 
				if (checkBirthYear < oldestBirthYear) {
					oldestBirthYear = checkBirthYear;
				}
			}
		}
		return oldestBirthYear;
	}
	
	private Contact[] getParticipantsForBirthyear(int birthYear, RichParticipation[] sourceList) {
		Contact[] targetList = null;
		int countThem = 0;
		for (RichParticipation actual: sourceList) {
			if (actual != null && actual.getParticipant() != null && actual.getParticipant().getBirthyear() == birthYear) {
				countThem++;
			}
		}
		if (countThem > 0) {
			targetList = new Contact[countThem];
			int targetPos = 0;
			for (RichParticipation actual: sourceList) {
				if (actual != null && actual.getParticipant() != null && actual.getParticipant().getBirthyear() == birthYear) {
					targetList[targetPos] = actual.getParticipant();
					targetPos++;
				}
			}
		}
		return targetList;
	}
	
	private int getYoungestBirthYear(RichParticipation[] richParticipations) {
		int youngestBirthYear = 0;
		for (RichParticipation actualParticipation: richParticipations) {
			if (actualParticipation != null && actualParticipation.getParticipant() != null) {
				int checkBirthYear = actualParticipation.getParticipant().getBirthyear(); 
				if (checkBirthYear > youngestBirthYear) {
					youngestBirthYear = checkBirthYear;
				}
			}
		}
		return youngestBirthYear;
	}

}
