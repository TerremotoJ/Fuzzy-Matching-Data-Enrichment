package tests;

import main.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DatasetTest {


	@Test
	void testAdd() {
		Dataset db = new Dataset();
		HashMap<String,String> teste = new HashMap<>();
		teste.put("country", "England");
		teste.put("population", "5009");
		db.add(teste);
	
		HashMap<String,String> teste2 = new HashMap<>();
		teste2.put("country", "Portugal");
		teste2.put("population", "6009");
		db.add(teste2);		
	    
		StringBuilder resultado = new StringBuilder();
		for (HashMap<String, String> record : db.getData()) {
	        for (String key : record.keySet()) {	        
	          resultado.append(record.get(key) + ", ");
	        		  //System.out.print(record.get(key) + ", ");
	        }
	        resultado.append("\n");
	        //System.out.println();

	    }
		
		String resultadoEsperado = "England, 5009, \n" + "Portugal, 6009, \n";
        assertEquals(resultadoEsperado,resultado.toString());
        
	}
	
	@Test
	void testRemove() {
		Dataset db = new Dataset();
		HashMap<String,String> teste = new HashMap<>();
		teste.put("country", "England");
		teste.put("population", "5009");
		db.add(teste);
		HashMap<String,String> teste2 = new HashMap<>();
		teste2.put("country", "Portugal");
		teste2.put("population", "6009");
		db.add(teste2);		
		
		db.remove(teste);
		assertEquals(1, db.size());
		assertEquals("Portugal", db.getData().get(0).get("country"));
	}

	@Test
	void testContains() {
		Dataset db = new Dataset();
		HashMap<String,String> teste = new HashMap<>();
		teste.put("country", "England");
		db.add(teste);
		
		HashMap<String,String> igual = new HashMap<>();
		igual.put("country", "England");
		HashMap<String,String> outro = new HashMap<>();
		outro.put("country", "Portugal");
		assertTrue(db.contains(igual));
		assertFalse(db.contains(outro));
	}

	@Test
	void testSize() {
		Dataset db = new Dataset();
		HashMap<String,String> teste = new HashMap<>();
		teste.put("country", "England");
		teste.put("population", "5009");
		db.add(teste);
		assertEquals(1,db.size());
		HashMap<String,String> teste2 = new HashMap<>();
		teste2.put("country", "Portugal");
		teste2.put("population", "6009");
		db.add(teste2);		
		assertEquals(2,db.size());
		ArrayList<HashMap<String, String>> data = db.getData();
	
	}
	@Test
	void testClear() {
		Dataset db = new Dataset();
		HashMap<String,String> teste = new HashMap<>();
		teste.put("country", "England");
		db.add(teste);
		db.clear();
		assertEquals(0, db.size());
	}

	@Test
	void testCount() {
		Dataset db = new Dataset();
		HashMap<String,String> teste = new HashMap<>();
		teste.put("country", "England");
		HashMap<String,String> teste2 = new HashMap<>();
		teste2.put("country", "Portugal");
		db.add(teste);
		db.add(teste2);
		db.add(teste);
		assertEquals(2, db.count(teste));
		assertEquals(1, db.count(teste2));
	}

	@Test
	void testGetData() {
		Dataset db = new Dataset();
		HashMap<String,String> teste = new HashMap<>();
		teste.put("country", "England");
		teste.put("population", "5009");
		db.add(teste);
		
	
		 ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
	      arrayList.add(teste);
		assertEquals(db.getData(),arrayList);
	}

	@Test
	void testSetData() {

		HashMap<String,String> teste = new HashMap<>();
		teste.put("country", "England");
		teste.put("population", "5009");
		 ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
	     arrayList.add(teste);
	      
	     Dataset db = new Dataset();
	     db.setData(arrayList);
		
	
		assertEquals(db.getData(),arrayList);
	}

}
