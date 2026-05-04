package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import distance.*;
class DamerauLevenshteinTest {

	@Test
	void testDamerauLevenshtein() {
		DamerauLevenshtein metric = new DamerauLevenshtein();
		assertNull(metric.getRatio());
	}

	@Test
	void testDistance() {
		Metric metric = new DamerauLevenshtein();
		assertEquals(0.0, metric.distance("hello", "hello"));
		assertEquals(1.0, metric.distance("England", "Englnd"));
		// a swap of two neighbours costs 1 here and 2 in Levenshtein
		assertEquals(1.0, metric.distance("ab", "ba"));
		assertEquals(2.0, new Levenshtein().distance("ab", "ba"));
		// optimal string alignment: a swapped pair is not edited again, so this is 3, not 2
		assertEquals(3.0, metric.distance("ca", "abc"));
	}

	@Test
	void testGetMetrica() {
		Metric metric = new DamerauLevenshtein();
		assertEquals("Damerau-Levenshtein", metric.getMetrica());
	}

	@Test
	void testCalcRatio() {
		Metric metric = new DamerauLevenshtein();
		metric.calcRatio(1, 2, 2);
		assertEquals(0.75, metric.getRatio(), 0.001);
	}

	@Test
	void testGetRatio() {
		Metric metric = new DamerauLevenshtein();
		metric.distance("ab", "ba");
		assertEquals(0.75, metric.getRatio(), 0.001);
		metric.distance("England", "Englnd");
		assertEquals(0.923, metric.getRatio(), 0.001);
	}

	@Test
	void testCompare() {
		Metric metric = new DamerauLevenshtein();
		assertTrue(metric.compare(1, 2));
		assertFalse(metric.compare(2, 1));
	}

}
