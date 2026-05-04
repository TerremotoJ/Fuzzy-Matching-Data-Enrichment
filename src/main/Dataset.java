package main;

import java.util.*;
// change Dataset name to DataSet / Table
// I don't think I need the Bag interface and to have implements Bag<HashMap<String, String>>


/**
 * The Dataset class 
 * @author Student
 * @version 14/05/2023
 */
public class Dataset {
	
	/**
	 * Main method
	 * @param args
	 */
    protected ArrayList<HashMap<String, String>> data;

    //Initially the array has capacity 1 and size 0 , when removed is it to resize? for example using the class example data.size(): 25 capacity: 32
    public Dataset() {
        this.data = new ArrayList<HashMap<String, String>>(); 
        
    }

    // I have to print to test if resize is being used
    public void add(HashMap<String, String> item) {
   
        data.add(item);

    }
   // to not add repeated items, but then maybe we have to add the column "name1" + "(2)", becoming for example England(2)

    public void remove(HashMap<String, String> item) {
        data.remove(item); // data.removeIf()??
       // data.removeIf(r -> r.equals(item)); //??? I don't know if it works
    }

    public boolean contains(HashMap<String, String> item) {
        return data.contains(item);
        // return data.stream().anyMatch(r -> r.equals(item)); //??? I don't know if it works
    }


    public int size() {
        return data.size();
    }

    public void clear() {
        data.clear();
    }

        // doing for example Dataset1.count(hashmap<String,String> resultadofinal), it will count how many times this "hashmap" exists in the arraylist
    public int count(HashMap<String, String> item) {
        int count = 0;
        for (HashMap<String, String> record : data) {
            if (record.equals(item)) {
                count++;
            }
        }
        return count;
    }


    public ArrayList<HashMap<String, String>> getData() {
        return data;
    }

    public void setData(ArrayList<HashMap<String, String>> temp) {
        this.data = temp;
    }


}
