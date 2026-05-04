package main;
import java.util.*;

import distance.Metric;


/**
 * The MergeTables class is a subclass that refers to the Dataset class.
 * It is in this class where the two tables are joined for the fuzzy join.
 * @author Student
 * @version 14/05/2023
 */
public class MergeTables extends Dataset {
    //private Dataset resultado;
	
    protected Metric metricaDistancia;
    /**
     * The mergeTables method is where the tables are joined.
     * @param Dataset1
     * @param Dataset2
     * @param metricaDistancia 
     * @param name1
     * @param name2
     * */
    public MergeTables(Dataset Dataset1, Dataset Dataset2, Metric metricaDistancia, String name1, String name2) {
        //resultado= new Dataset();
        //do I have to use super?
        
        double distance = 0.0;
        for (HashMap<String, String> record1 : Dataset1.getData()) {
            String tmp1 = record1.get(name1); // is .toString() necessary?
   
            for (HashMap<String, String> record2 : Dataset2.getData()) {
;
            String tmp2 = record2.get(name2); // is .toString() necessary?
         
                 distance = metricaDistancia.distance(tmp1,tmp2);
 
                    HashMap<String, String> mergedRecord = mergeRecords(record1, record2,name2,metricaDistancia);
                   

                   this.data.add(mergedRecord); 

            }
        }
    }
  
    
    private HashMap<String, String> mergeRecords(HashMap<String, String> record1, HashMap<String, String> record2, String name2, Metric metricaDistancia) {
        HashMap<String, String> mergedRecord = new HashMap<>();
 

        mergedRecord.putAll(record2);

        mergedRecord.putAll(record1);
        mergedRecord.put(metricaDistancia.getMetrica(), String.format("%.0f", metricaDistancia.getRatio() * 100));

     
        return mergedRecord;
    }
    

   
}