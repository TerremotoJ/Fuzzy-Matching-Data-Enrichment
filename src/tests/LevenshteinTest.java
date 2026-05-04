package tests;
import distance.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LevenshteinTest {


	@Test
	void testDistance() {
			Metric metric = new Levenshtein();
        
        assertEquals(0.0, metric.distance("hello", "hello"));
        assertEquals(1.0, metric.distance("hello", "hallo"));
        assertEquals(1.0, metric.distance("hello", "helo"));
        assertEquals(3.0, metric.distance("hello", "he"));
        assertEquals(4.0, metric.distance("hello", "world"));
        assertEquals(4.0, metric.distance("world", "hello"));
        assertEquals(1.0, metric.distance("England", "Englnd"));
	}

	@Test
	void testGetMetrica() {
		Metric metric = new Levenshtein();
		assertEquals("Levenshtein", metric.getMetrica());
	}


	@Test
	void testCalcRatio() {
        Metric metric = new Levenshtein();
        // ratio = (len1 + len2 - distance) / (len1 + len2)
        metric.distance("hello", "hello");
        assertEquals(1.0, metric.getRatio(), 0.001);
        
        metric.distance("hello", "hallo");
        assertEquals(0.9, metric.getRatio(), 0.001);
        
        metric.distance("hello", "helo");
        assertEquals(0.8889, metric.getRatio(), 0.001);
        
        metric.distance("hello", "he");
        assertEquals(0.5714, metric.getRatio(), 0.001);
        
        metric.distance("hello", "world");
        assertEquals(0.6, metric.getRatio(), 0.001);
        
        metric.distance("world", "hello");
        assertEquals(0.6, metric.getRatio(), 0.001);
        
        metric.distance("England", "Englnd");
        assertEquals(0.9231, metric.getRatio(), 0.001);
	}

	@Test
	void testGetRatio() {
		Metric metric = new Levenshtein();
		metric.distance("hello", "helo");
		assertEquals(0.8889, metric.getRatio(), 0.001);
	}

	@Test
	void testCompare() {
		Metric metric = new Levenshtein();
		assertTrue(metric.compare(1, 2));
		assertFalse(metric.compare(2, 1));
	}

}
