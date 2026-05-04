package tests;
import distance.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CosineTest {

	@Test
	void testCosine() {
		Cosine cosine = new Cosine();
		assertNull(cosine.getRatio());
	}

	@Test
	void testDistance() {
		Cosine cosine = new Cosine();
		assertEquals(0.0, cosine.distance("ab", "ab"), 0.0001);
		assertEquals(1.0, cosine.distance("ab", "cd"), 0.0001);
		// character counts only, so the order of the characters does not matter
		assertEquals(0.0, cosine.distance("abc", "cba"), 0.0001);
		// {a:2, b:1} against {a:1, b:1}: 1 - 3 / sqrt(10)
		assertEquals(0.0513, cosine.distance("aab", "ab"), 0.0001);
	}

	@Test
	void testGetMetrica() {
		Cosine cosine = new Cosine();
		assertEquals("Cosine", cosine.getMetrica());
	}

	@Test
	void testCalcRatio() {
		Cosine cosine = new Cosine();
		cosine.calcRatio(0.25, 4, 4);
		assertEquals(0.75, cosine.getRatio(), 0.0001);
	}

	@Test
	void testGetRatio() {
		Cosine cosine = new Cosine();
		double distance = cosine.distance("aab", "ab");
		assertEquals(1.0 - distance, cosine.getRatio(), 0.0001);
		cosine.distance("ab", "cd");
		assertEquals(0.0, cosine.getRatio(), 0.0001);
	}

	@Test
	void testCompare() {
		Cosine cosine = new Cosine();
		assertTrue(cosine.compare(1, 2));
		assertTrue(cosine.compare(2, 2));
		assertFalse(cosine.compare(2, 1));
	}

}
