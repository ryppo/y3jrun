package org.y3.jrun.model.participation;

import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.control.Utils;
import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.model.ageclassesdefinition.RichAgeClassesDefinition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.discipline.Discipline;
import org.y3.jrun.view.ApplicationFrame;
import org.y3.jrun.view.gfx.IconDictionary;
import org.y3.jrun.view.i18n.Messages;

public class RichParticipationsTableModel implements TableModel {
	
	@SuppressWarnings("rawtypes")
	private Class[] columnClasses = new Class[] {
			String.class, JLabel.class, JLabel.class, String.class, String.class, String.class, String.class, String.class, Date.class, String.class, String.class, String.class, ParticipationDuration.class
	};
	private String[] columnNames = new String[] {
			Messages.getString(Messages.START_NUMMER), "", "", Messages.getString(Messages.RANKING), Messages.getString(Messages.GENDER_RANKING),
			Messages.getString(Messages.AGECLASS_RANKING), Messages.getString(Messages.GENDER_AGECLASS_RANKING), 
			Messages.getString(Messages.CONTACT_GENDER), Messages.getString(Messages.REGISTRATION_DATE),
			Messages.getString(Messages.CONTACT), 
			Messages.getString(Messages.CONTACT_BIRTHYEAR), Messages.getString(Messages.AGECLASS), 
			Messages.getString(Messages.RESULT_TIME)
	};
	private int[] columnWidth = new int[] {
			85,25,25,50,50,50,50,75,80,200,75,150,100
	};
	
	private RichParticipation[] participations;
	private ApplicationController controller;
	private ApplicationFrame appFrame;
	
	public RichParticipationsTableModel(RichParticipation[] _participations, ApplicationController _controller, ApplicationFrame _appFrame) {
		super();
		participations = _participations;
		controller = _controller;
		appFrame = _appFrame;
	}
	
	public int getColumnWidth(int column) {
		return columnWidth[column];
	}

	@Override
	public void addTableModelListener(TableModelListener arg0) {
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		return columnClasses[arg0];
	}

	@Override
	public int getColumnCount() {
		return columnClasses.length;
	}

	@Override
	public String getColumnName(int arg0) {
		return columnNames[arg0];
	}

	@Override
	public int getRowCount() {
		if (participations != null) {
			return participations.length;
		}
		return 0;
	}
	
	private String getCompetitiveRankAsString(boolean isCompetitive, int rank) {
		if (isCompetitive) {
			return Messages.getString(Messages.NONCOMPETITIVE);
		} else {
			if (rank != 0) {
				return Integer.toString(rank);
			} else {
				return "";
			}
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (participations != null && participations.length != 0) {
			RichParticipation p = participations[row];
			switch(column) {
			case 0:
				return p.getParticipationNumber();
			case 1:
				JLabel label = new JLabel();
				if (p.getComment() != null && p.getComment().length() > 0) {
					label.setIcon(IconDictionary.getImageIcon(IconDictionary.COMMENT));
				}
				return label;
			case 2:
				JLabel label_donation = new JLabel();
				if (p.getDonationHospizInEuroCent() > 0) {
					label_donation.setIcon(IconDictionary.getImageIcon(IconDictionary.DONATION));
				}
				return label_donation;
			case 3:
				return getCompetitiveRankAsString(p.isNoncompetitive(), p.getRank());
			case 4:
				return getCompetitiveRankAsString(p.isNoncompetitive(), p.getGenderRank());
			case 5:
				return getCompetitiveRankAsString(p.isNoncompetitive(), p.getAgeClassRank());
			case 6:
				return getCompetitiveRankAsString(p.isNoncompetitive(), p.getGenderAgeClassRank());
			case 7:
				String genderString = "";
				Contact.gendertype gender = p.getParticipant().getGender();
				if (gender != null) {
					if (gender.equals(Contact.gendertype.FEMALE)) {
						genderString = Messages.getString(Messages.FEMALE);
					} else {
						genderString = Messages.getString(Messages.MALE);
					}
				}
				return genderString;
			case 8:
				return p.getCreationDate();
			case 9:
				if (p.getParticipant() != null) {
					return p.getParticipant().toString();
				}
				return p.getParticipantName();
			case 10:
				if (p.getParticipant() != null) {
					return Integer.toString(p.getParticipant().getBirthyear());
				}
			case 11:
				String value = "";
				RichAgeClassesDefinition ageClassesDefinition = p.getRelatedCompetition().getRelatedAgeClassDefinition();
				if (ageClassesDefinition !=  null && p.getParticipant() != null) {
					AgeClass ageClass = ageClassesDefinition.getAgeClassForBirthyear(p.getParticipant().getBirthyear()); 
					if (ageClass != null) {
						value = ageClass.getTitle();
					}
				}
				return value;
			case 12:
				ParticipationDuration pd = new ParticipationDuration();
				pd.setResultTimeAsDate(p.getResultTimeAsDate());
				Discipline discipline = p.getDiscipline();
				if (discipline != null && discipline.getDurationFormat() != null) {
					pd.setDurationFormat(p.getDiscipline().getDurationFormat());
				}
				return pd;
			}
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		switch(column) {
		case 0:
			return true;
		case 1:
			return false;
		case 2:
			return false;
		case 3:
			return true;
		case 4:
			return true;
		case 5:
			return true;
		case 6:
			return true;
		case 7:
			return true;
		case 8:
			return false;
		case 9:
			return false;
		case 10:
			return false;
		case 11:
			return false;
		case 12: 
			return true;
		}
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		if (participations != null && participations.length != 0) {
			boolean changed = false;
			RichParticipation p = participations[row];
			switch(column) {
			case 0:
				p.setParticipationNumber((String) value);
				changed = true;
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				p.setRank(Integer.parseInt((String) value));
				changed = true;
				break;
			case 4:
				p.setGenderRank(Utils.parseIntValueFromStringDefault0((String) value));
				changed = true;
				break;
			case 5:
				p.setAgeClassRank(Utils.parseIntValueFromStringDefault0((String) value));
				changed = true;
				break;
			case 6:
				p.setGenderAgeClassRank(Utils.parseIntValueFromStringDefault0((String) value));
				changed = true;
				break;
			case 7:
				Contact.gendertype gender = null;
				if (value != null && value instanceof String) {
					String input = (String) value;
					if (input.equals(Messages.getString(Messages.FEMALE))) {
						gender = Contact.gendertype.FEMALE;
					} else if(input.equals(Messages.getString(Messages.MALE))) {
						gender = Contact.gendertype.MALE;
					}
				}
				p.getParticipant().setGender(gender);
				break;
			case 8:
				break;
			case 9:
				p.setParticipantName((String) value);
				changed = true;
				break;
			case 10:
				p.getParticipant().setBirthyear(Integer.parseInt((String) value));
				changed = true;
				break;
			case 11:
				break;
			case 12:
				p.setResultTimeAsDate((Date) value);
				changed = true;
			}
			if (changed) {
				try {
					controller.saveModel(p);
					appFrame.showUserMessage(JOptionPane.INFORMATION_MESSAGE, Messages.getString(Messages.PARTICIPATION_SUCCESSFULLY_SAVED), p);
				} catch (Exception e1) {
					appFrame.showUserMessage(e1, p);
				}
			}
		}
	}
	
	public RichParticipation getParticipationAtRow(int row) {
		if (participations != null && participations.length > 0 && row > -1) {
			return participations[row];
		}
		return null;
	}
	
	public RichParticipation[] getParticipationsAtRows(int[] rows) {
		if (participations != null && participations.length > 0) {
			RichParticipation[] richPs = new RichParticipation[rows.length];
			int rowNo = 0;
			for (int row: rows) {
				richPs[rowNo] = getParticipationAtRow(row);
				rowNo++;
			}
			return richPs;
		}
		return null;
	}
	
	public RichParticipation[] getAllParticipations() {
		return participations;
	}
	
	public int getRowOfParticipation(RichParticipation searchRichParticipation) {
		if (participations != null) {
			for (int row = 0; row < participations.length; row++) {
				if (participations[row] == searchRichParticipation) {
					return row;
				}
			}
		}
		return -1;
	}

}
