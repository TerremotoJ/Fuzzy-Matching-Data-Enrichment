package main;
import java.util.*;

import distance.Metric;


/**
 * The FuzzyJoin class is where the fuzzy join of the two user-selected tables is performed
 * @author Student
 * @version 14/05/2023
 */
public class FuzzyJoin extends Dataset {
    
	/**
     * The FuzzyJoin method
     * @param tabelaMerged
     * @param name 
     * @param metricaDistancia 
     * */
    public FuzzyJoin (MergeTables tabelaMerged, String name, Metric metricaDistancia){
        String tmp = null;
        HashMap<String, String> bestMatch = new HashMap<>();
        int tmpInt = 0;
        
        for (HashMap<String, String> line : tabelaMerged.getData()) {      
            
            if(tmp == null){
                tmp  = line.get(name); // is this when it starts to iterate getting the word for the first time?
                tmpInt =0;
                }
            
                if((line.get(name) == tmp) && ( metricaDistancia.compare(tmpInt,Integer.parseInt(line.get(metricaDistancia.getMetrica()))) )){
                bestMatch= line;
                tmpInt =Integer.parseInt(line.get(metricaDistancia.getMetrica()));
                }else if((line.get(name) != tmp)){
                this.data.add(bestMatch);
                bestMatch=line;
                tmp = line.get(name);
                tmpInt = Integer.parseInt(line.get(metricaDistancia.getMetrica()));
                }
        }
        this.data.add(bestMatch);
    }




    
}

