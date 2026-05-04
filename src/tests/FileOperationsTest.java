package tests;
import main.*;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import distance.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
class FileOperationsTest {
	
	
	    @Test
	    void testReadCsv() {
	    	File file = new File("teste.csv");
			
			assertTrue(file.exists());
	        Dataset temp = FileOperations.ReadCsv("teste.csv");
	       
	        assertEquals(5, temp.size());
	        assertEquals("England", temp.getData().get(0).get("country"));
	        assertEquals("5.45", temp.getData().get(1).get("population_in_millions"));
	    }
	    
	    @Test
	    void testWriteCSV() throws IOException {
	    	String args[] = {"fuzzy-join", "--filename1=teste.csv", "--filename2=teste2.csv", "--name1=country", "--name2=country", "--distance=Levenshtein"};
	    	app.main(args);
	    	
	    	List<String> lines = Files.readAllLines(Paths.get("output.csv"));
	    	assertEquals(6, lines.size());
	    	assertTrue(lines.get(0).endsWith(",Levenshtein"));
	    	
	    	Dataset temp = FileOperations.ReadCsv("output.csv");
	    	assertEquals(5, temp.size());
	    	HashMap<String, String> england = temp.getData().get(0);
	    	assertEquals("England", england.get("country"));
	    	assertEquals("55.98", england.get("population_in_millions"));
	    	assertEquals("45101.0", england.get("GDP_per_capita"));
	    	assertEquals("92", england.get("Levenshtein"));
	    	assertEquals(4, england.size());
	    }

	    @Test
	    void testReadCsvQuotedComma(@TempDir Path dir) throws IOException {
	    	Path file = write(dir, "name,city\n\"Smith, John\",Lisbon\n");
	    	Dataset temp = FileOperations.ReadCsv(file.toString());
	    	assertEquals(1, temp.size());
	    	assertEquals("Smith, John", temp.getData().get(0).get("name"));
	    	assertEquals("Lisbon", temp.getData().get(0).get("city"));
	    }

	    @Test
	    void testReadCsvEscapedQuotes(@TempDir Path dir) throws IOException {
	    	Path file = write(dir, "name,nick\nJohn,\"say \"\"hi\"\", ok\"\nJane,\"\"\n");
	    	Dataset temp = FileOperations.ReadCsv(file.toString());
	    	assertEquals("say \"hi\", ok", temp.getData().get(0).get("nick"));
	    	assertEquals("", temp.getData().get(1).get("nick"));
	    }

	    @Test
	    void testReadCsvTrailingEmptyFields(@TempDir Path dir) throws IOException {
	    	Path file = write(dir, "a,b,c\n1,,\n,2,\n");
	    	Dataset temp = FileOperations.ReadCsv(file.toString());
	    	assertEquals(2, temp.size());
	    	assertEquals("1", temp.getData().get(0).get("a"));
	    	assertEquals("", temp.getData().get(0).get("b"));
	    	assertEquals("", temp.getData().get(0).get("c"));
	    	assertEquals("", temp.getData().get(1).get("a"));
	    	assertEquals("2", temp.getData().get(1).get("b"));
	    	assertEquals("", temp.getData().get(1).get("c"));
	    }

	    @Test
	    void testReadCsvShortRow(@TempDir Path dir) throws IOException {
	    	Path file = write(dir, "a,b,c\n1\n");
	    	Dataset temp = FileOperations.ReadCsv(file.toString());
	    	assertEquals("1", temp.getData().get(0).get("a"));
	    	assertEquals("", temp.getData().get(0).get("b"));
	    	assertEquals("", temp.getData().get(0).get("c"));
	    }

	    @Test
	    void testReadCsvQuotedLineBreak(@TempDir Path dir) throws IOException {
	    	Path file = write(dir, "a,b\n\"line 1\nline 2\",x\ny,z\n");
	    	Dataset temp = FileOperations.ReadCsv(file.toString());
	    	assertEquals(2, temp.size());
	    	assertEquals("line 1\nline 2", temp.getData().get(0).get("a"));
	    	assertEquals("x", temp.getData().get(0).get("b"));
	    	assertEquals("y", temp.getData().get(1).get("a"));
	    }

	    @Test
	    void testReadCsvSkipsBlankLines(@TempDir Path dir) throws IOException {
	    	Path file = write(dir, "a,b\n1,2\n\n3,4\n\n");
	    	Dataset temp = FileOperations.ReadCsv(file.toString());
	    	assertEquals(2, temp.size());
	    	assertEquals("3", temp.getData().get(1).get("a"));
	    }

	    @Test
	    void testReadCsvBundledFileWithQuotes() {
	    	Dataset temp = FileOperations.ReadCsv("expedia.csv");
	    	assertEquals(103, temp.size());
	    	assertEquals("0", temp.getData().get(0).get(""));
	    	assertEquals("Deluxe Room, 1 King Bed", temp.getData().get(0).get("Expedia"));
	    	assertEquals("Standard Room, 1 King Bed, Accessible", temp.getData().get(1).get("Expedia"));
	    }

	    @Test
	    void testWriteCSVQuotesValues(@TempDir Path dir) throws IOException {
	    	Dataset db1 = new Dataset();
	    	HashMap<String, String> record1 = new HashMap<>();
	    	record1.put("name", "Smith, John");
	    	db1.add(record1);
	    	Dataset db2 = new Dataset();
	    	HashMap<String, String> record2 = new HashMap<>();
	    	record2.put("other", "Smith, Jon");
	    	record2.put("nick", "say \"hi\"");
	    	db2.add(record2);
	    	Metric metric = new Levenshtein();
	    	FuzzyJoin join = new FuzzyJoin(new MergeTables(db1, db2, metric, "name", "other"), "name", metric);
	    	
	    	Path file = dir.resolve("out.csv");
	    	FileOperations.writeCSV(join, metric, file.toString());
	    	
	    	String text = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
	    	assertTrue(text.contains("\"Smith, John\""));
	    	assertTrue(text.contains("\"say \"\"hi\"\"\""));
	    	assertFalse(text.contains(",\n"));
	    	
	    	Dataset back = FileOperations.ReadCsv(file.toString());
	    	assertEquals(1, back.size());
	    	assertEquals("Smith, John", back.getData().get(0).get("name"));
	    	assertEquals("Smith, Jon", back.getData().get(0).get("other"));
	    	assertEquals("say \"hi\"", back.getData().get(0).get("nick"));
	    }

	    private Path write(Path dir, String content) throws IOException {
	    	Path file = dir.resolve("input.csv");
	    	Files.write(file, content.getBytes(StandardCharsets.UTF_8));
	    	return file;
	    }

}
