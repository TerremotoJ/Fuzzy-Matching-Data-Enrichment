package tests;
import distance.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JaccardTest {

	@Test
	void testDistance() {
		Jaccard jaccard = new Jaccard();
		assertEquals(0.0, jaccard.distance("abc", "abc"), 0.0001);
		// {a,b,c} and {a,b,d}: 2 shared out of 4
		assertEquals(0.5, jaccard.distance("abc", "abd"), 0.0001);
		assertEquals(1.0, jaccard.distance("ab", "cd"), 0.0001);
		// sets of characters, so repeats do not count
		assertEquals(0.0, jaccard.distance("aab", "ab"), 0.0001);
	}

	@Test
	void testGetMetrica() {
		Jaccard jaccard = new Jaccard();
		assertEquals("Jaccard", jaccard.getMetrica());
	}

	@Test
	void testCalcRatio() {
		Jaccard jaccard = new Jaccard();
		jaccard.calcRatio(0.25, 4, 4);
		assertEquals(0.75, jaccard.getRatio(), 0.0001);
	}

	@Test
	void testGetRatio() {
		Jaccard jaccard = new Jaccard();
		jaccard.distance("abc", "abd");
		assertEquals(0.5, jaccard.getRatio(), 0.0001);
		jaccard.distance("abc", "abc");
		assertEquals(1.0, jaccard.getRatio(), 0.0001);
	}

	@Test
	void testCompare() {
		Jaccard jaccard = new Jaccard();
		assertTrue(jaccard.compare(1, 2));
		assertFalse(jaccard.compare(2, 1));
	}

}
