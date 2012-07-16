package test.org.y3.jrun.control;


import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.y3.jrun.control.ApplicationController;
import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinition;
import org.y3.jrun.model.ageclassesdefinition.RichAgeClassesDefinition;
import org.y3.jrun.model.competition.Competition;
import org.y3.jrun.model.competition.RichCompetition;
import org.y3.jrun.model.contact.Contact;
import org.y3.jrun.model.participation.RichParticipation;

public class TestApplicationController extends TestCase {
	
	private ApplicationController appControl;
	
	private RichParticipation[] richParticipations;
	private AgeClass ac1, ac2;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		appControl = new ApplicationController();
		
	}
	
	private void setUpRichParticipationsWithAgeClass(boolean onlyOneAgeClass) {
		ac1 = new AgeClass();
		ac1.setTitle("ac1");
		ac1.setYearFrom(1800);
		ac1.setYearTo(2100);
		ac1.setId(1);
		AgeClass[] ageClasses = new AgeClass[]{ac1};
		if (!onlyOneAgeClass) {
			ac2 = new AgeClass();
			ac2.setTitle("ac2");
			ac2.setYearFrom(2101);
			ac2.setYearTo(2400);
			ac2.setId(2);
			ageClasses = new AgeClass[]{ac1, ac2};
		}
		AgeClassesDefinition acd1 = new AgeClassesDefinition();
		acd1.setName("AgeClassesDefinition1");
		RichAgeClassesDefinition richAcd1 = new RichAgeClassesDefinition(acd1, ageClasses);
		Competition comp1 = new Competition();
		comp1.setTitle("Competition1");
		RichCompetition richComp1 = new RichCompetition(richAcd1, comp1);
		Contact c1 = new Contact();
		c1.setBirthyear(1999);
		Contact c2 = new Contact();
		c2.setBirthyear(1999);
		Contact c3 = new Contact();
		c3.setBirthyear(1999);
		if (!onlyOneAgeClass) {
			c2.setBirthyear(2300);
			c3.setBirthyear(2300);
		}
		RichParticipation richP1 = new RichParticipation();
		richP1.setParticipant(c1);
		richP1.setRelatedCompetition(richComp1);
		richP1.setResultTimeAsString("00:00:10");
		RichParticipation richP2 = new RichParticipation();
		richP2.setParticipant(c2);
		richP2.setRelatedCompetition(richComp1);
		richP2.setResultTimeAsString("00:00:20");
		RichParticipation richP3 = new RichParticipation();
		richP3.setParticipant(c3);
		richP3.setRelatedCompetition(richComp1);
		richP3.setResultTimeAsString("00:00:30");
		richParticipations = new RichParticipation[]{richP1,richP2, richP3};
	}
	
	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testCalculateRank() throws Exception {
		setUpRichParticipationsWithAgeClass(false);
		appControl.calculateRanking(richParticipations, false);
		int expected1 = 1;
		int expected2 = 2;
		int expected3 = 3;
		int actual1 = richParticipations[0].getRank();
		int actual2 = richParticipations[1].getRank();
		int actual3 = richParticipations[2].getRank();
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
		assertEquals(expected3, actual3);
	}

	@Test
	public void testCalculateAgeClassesRankings() throws Exception {
		setUpRichParticipationsWithAgeClass(false);
		appControl.calculateAgeClassesRankings(richParticipations, false);
		int expected1 = 1;
		int expected2 = 1;
		int expected3 = 2;
		int actual1 = richParticipations[0].getAgeClassRank();
		int actual2 = richParticipations[1].getAgeClassRank();
		int actual3 = richParticipations[2].getAgeClassRank();
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
		assertEquals(expected3, actual3);
	}

	@Test
	public void testGetParticipationsForAgeClassCaseOne() {
		setUpRichParticipationsWithAgeClass(true);
		RichParticipation expected = richParticipations[0];
		RichParticipation[] actualArray = appControl.getParticipationsForAgeClass(richParticipations, ac1);
		RichParticipation actual = actualArray[0];
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetParticipationsForAgeClassCaseTwo() {
		setUpRichParticipationsWithAgeClass(false);
		RichParticipation expected = richParticipations[2];
		RichParticipation[] actualArray = appControl.getParticipationsForAgeClass(richParticipations, ac2);
		RichParticipation actual = actualArray[1];
		assertEquals(expected, actual);
	}

	@Test
	public void testGetDistinctAgeClassesOneAgeClass() {
		setUpRichParticipationsWithAgeClass(true);
		AgeClass expectedAC = ac1;
		AgeClass[] ageClasses = appControl.getDistinctAgeClasses(richParticipations);
		AgeClass actualAC = ageClasses[0];
		assertEquals(expectedAC, actualAC);
	}
	
	@Test
	public void testGetDistinctAgeClassesTwoAgeClasses() {
		setUpRichParticipationsWithAgeClass(false);
		AgeClass expectedAC = ac2;
		AgeClass[] ageClasses = appControl.getDistinctAgeClasses(richParticipations);
		AgeClass actualAC = ageClasses[1];
		assertEquals(expectedAC, actualAC);
	}

}
