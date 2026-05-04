package tests;
import distance.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HammingTest {


	@Test
	void testDistance() {
		Hamming hamming = new Hamming();
        assertEquals(2, hamming.distance("AGTCA", "AGGCT"));
	}

	@Test
	void testGetMetrica() {
        Hamming hamming = new Hamming();
        assertEquals("Hamming", hamming.getMetrica());
	}

	@Test
	void testCalcRatio() {
		String s1 = "GATTACA";
		String s2 = "GATTAGA";
		 Hamming hamming = new Hamming();
	     // hamming.calcRatio(hamming.distance(hamming.getColuna1(), hamming.getColuna2()), s1.length(), s2.length());
		 hamming.calcRatio(hamming.distance("GATTACA", "GATTAGA"), s1.length(), s2.length());
		 assertEquals(0.8571428571428571, hamming.getRatio());
	}

	@Test
	void testGetRatio() {
		Hamming hamming = new Hamming();
		String s1 = "GATTACA";
		String s2 = "GATTAGA";
        //hamming.calcRatio(hamming.distance(hamming.getColuna1(), hamming.getColuna2()), s1.length(), s2.length());
		hamming.calcRatio(hamming.distance("GATTACA", "GATTAGA"), s1.length(), s2.length());
        assertEquals(0.8571428571428571, hamming.getRatio());
	}

	@Test
	void testCompare() {
	       Hamming hamming = new Hamming();
	        assertTrue(hamming.compare(1, 2));
	        assertFalse(hamming.compare(2, 1));
	}

}
