package test.org.y3.jrun.model.participation;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.y3.jrun.model.participation.Participation;

public class TestParticiation extends TestCase{
	
	private Participation participation;

	@Before
	public void setUp() throws Exception {
		participation = new Participation();
	}

	@After
	public void tearDown() throws Exception {
		participation = null;
	}

	@Test
	public void testSetEmptyResultTime() {
		String resultTime = null;
		long expectedResult = -3600000;
		participation.setResultTimeAsString(resultTime);
		assertEquals(Long.toString(expectedResult), participation.getResultTimeAsString());
		assertEquals(expectedResult, participation.getResultTime());
	}
	
	public void testSetResultTimeAsString() {
		String resultTime = Long.toString(System.currentTimeMillis());
		participation.setResultTimeAsString(resultTime);
		String resultTimeAsString = participation.getResultTimeAsString();
		assertEquals(resultTime, resultTimeAsString);
		assertNotNull(participation.getResultTime());
	}
	
	public void testSetResultTimeAsLong() {
		long resultTime = System.currentTimeMillis();
		participation.setResultTime(resultTime);
		assertEquals(resultTime, participation.getResultTime());
		assertNotNull(participation.getResultTime());
	}

}
