package test.org.y3.jrun.model.ageclassesdefinition;


import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.y3.jrun.model.ageclass.AgeClass;
import org.y3.jrun.model.ageclassesdefinition.AgeClassesDefinition;
import org.y3.jrun.model.ageclassesdefinition.RichAgeClassesDefinition;

public class TestRichAgeClassesDefinition extends TestCase {
	
	private RichAgeClassesDefinition model = null;
	String oldest = "oldest";
	String middle = "middle";
	String youngest = "youngest";

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		AgeClass[] ageClasses = new AgeClass[3];
		ageClasses[0] = new AgeClass();
		ageClasses[0].setTitle(oldest);
		ageClasses[0].setYearTo(1950);
		ageClasses[1] = new AgeClass();
		ageClasses[1].setTitle(middle);
		ageClasses[1].setYearFrom(1951);
		ageClasses[1].setYearTo(1970);
		ageClasses[2] = new AgeClass();
		ageClasses[2].setTitle(youngest);
		ageClasses[2].setYearFrom(1971);
		model = new RichAgeClassesDefinition(new AgeClassesDefinition(), ageClasses);
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
		model = null;
	}

	@Test
	public void testGetAgeClassForBirthyearAsOldest() {
		int birthyear = 1940;
		String expected = oldest;
		String actual = "";
		AgeClass result = model.getAgeClassForBirthyear(birthyear);
		if (result != null) {
			actual = result.getTitle();
		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetAgeClassForBirthyearAsMiddle() {
		int birthyear = 1960;
		String expected = middle;
		String actual = "";
		AgeClass result = model.getAgeClassForBirthyear(birthyear);
		if (result != null) {
			actual = result.getTitle();
		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetAgeClassForBirthyearAsYoungest() {
		int birthyear = 1980;
		String expected = youngest;
		String actual = "";
		AgeClass result = model.getAgeClassForBirthyear(birthyear);
		if (result != null) {
			actual = result.getTitle();
		}
		assertEquals(expected, actual);
	}

}
