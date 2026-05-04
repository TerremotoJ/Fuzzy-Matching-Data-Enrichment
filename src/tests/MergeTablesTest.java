package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import main.*;
import distance.*;
import org.junit.jupiter.api.Test;

class MergeTablesTest {

	private Dataset table(String key, String... values) {
		Dataset db = new Dataset();
		for (String value : values) {
			HashMap<String, String> record = new HashMap<>();
			record.put(key, value);
			db.add(record);
		}
		return db;
	}

	@Test
	void testMergeTables() {
		Dataset db1 = table("country", "England", "Wales");
		Dataset db2 = table("name", "Englnd", "Wles", "Scotlnd");
		MergeTables merged = new MergeTables(db1, db2, new Levenshtein(), "country", "name");

		// every row of the first table against every row of the second
		assertEquals(6, merged.size());
		HashMap<String, String> first = merged.getData().get(0);
		assertEquals("England", first.get("country"));
		assertEquals("Englnd", first.get("name"));
		// (7 + 6 - 1) / (7 + 6) = 0.923, written as a 0-100 score
		assertEquals("92", first.get("Levenshtein"));
		assertEquals("Wales", merged.getData().get(3).get("country"));
		assertEquals("Englnd", merged.getData().get(3).get("name"));
	}

	@Test
	void testMergeTablesExactMatchScores100() {
		Dataset db1 = table("country", "Portugal");
		Dataset db2 = table("name", "Portugal");
		MergeTables merged = new MergeTables(db1, db2, new Cosine(), "country", "name");
		assertEquals("100", merged.getData().get(0).get("Cosine"));
	}

}
