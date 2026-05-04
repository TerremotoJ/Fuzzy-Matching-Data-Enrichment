package tests;
import main.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import distance.*;
import org.junit.jupiter.api.Test;

class appTest {

	@Test
	void testMain() {
		String args[] = {"fuzzy-join", "--filename1=teste.csv", "--filename2=teste2.csv", "--name1=country", "--name2=country", "--distance=Levenshtein"};
		PrintStream original = System.out;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		try {
			app.main(args);
		} finally {
			System.setOut(original);
		}
		
		String[] lines = out.toString().split("\\R");
		// a header line, then one line per row of teste.csv
		assertEquals(6, lines.length);
		assertTrue(lines[0].contains("Levenshtein"));
		assertTrue(lines[1].contains("England"));
		assertTrue(lines[1].contains("45101.0"));
		assertTrue(lines[1].contains("92"));
	}

	@Test
	void testCheckDistance() {

		 Metric metric = app.CheckDistance("Levenshtein", "name1", "name2");
	        assertTrue(metric instanceof Levenshtein);


	        metric = app.CheckDistance("Cosine", "name1", "name2");
	        assertTrue(metric instanceof Cosine);
	 

	        metric = app.CheckDistance("Jaccard", "name1", "name2");
	        assertTrue(metric instanceof Jaccard);


	        metric = app.CheckDistance("Hamming", "name1", "name2");
	        assertTrue(metric instanceof Hamming);


	        metric = app.CheckDistance("Damerau-Levenshtein", "name1", "name2");
	        assertTrue(metric instanceof DamerauLevenshtein);
	

	        metric = app.CheckDistance("invalidDistance", "name1", "name2");
	        assertTrue(metric instanceof Levenshtein);
	}

}
