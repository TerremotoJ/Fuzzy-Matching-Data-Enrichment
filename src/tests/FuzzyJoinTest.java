package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import main.*;
import distance.*;
import org.junit.jupiter.api.Test;

class FuzzyJoinTest {

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
	void testKeepsBestMatch() {
		Dataset db1 = table("country", "England", "Wales");
		Dataset db2 = table("name", "Wles", "Scotlnd", "Englnd");
		Metric metric = new Levenshtein();
		FuzzyJoin join = new FuzzyJoin(new MergeTables(db1, db2, metric, "country", "name"), "country", metric);

		assertEquals("Englnd", join.getData().get(0).get("name"));
		assertEquals("92", join.getData().get(0).get("Levenshtein"));
		assertEquals("Wles", join.getData().get(1).get("name"));
	}

	@Test
	void testOneRowPerRecordOfFirstTable() {
		Dataset db1 = table("country", "England", "Wales", "Scotland");
		Dataset db2 = table("name", "Englnd", "Wles");
		Metric metric = new DamerauLevenshtein();
		FuzzyJoin join = new FuzzyJoin(new MergeTables(db1, db2, metric, "country", "name"), "country", metric);

		assertEquals(3, join.size());
		assertEquals("England", join.getData().get(0).get("country"));
		assertEquals("Wales", join.getData().get(1).get("country"));
		assertEquals("Scotland", join.getData().get(2).get("country"));
	}

}
