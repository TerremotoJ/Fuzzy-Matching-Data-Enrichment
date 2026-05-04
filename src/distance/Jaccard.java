package distance;
import java.util.*;

/* 
ngrams (n=2) :  'abcde' & 'abdcde'
   ab bc cd de dc bd
A  1  1  1  1  0  0
B  1  0  1  1  1  1
J(A, B) = (A∩B) / (A∪B)

J(A, B) = (3 / 6) = 0.5
There is also the Jaccard distance which captures the dissimilarity between two sets, 
and is calculated by taking one minus the Jaccard coeeficient (in this case, 1 - 0.5 = 0.5)
*/

// jaccard is closer to 0 I think

/**
 * The Jaccard class calculates the jaccard metric.
 * @author Student
 * @version 14/05/2023
 */
public class Jaccard implements Metric {
	
	/**
     * The ratio of the jaccard metric.
     * */
    private Double ratio;
    
    public Jaccard() {
        this.ratio = null;
    }

    /**
     * The distance method is where the jaccard metric is calculated.
     * @param s1 is the first string belonging to the join field chosen by the user.
     * @param s2 is the second string belonging to the join field chosen by the user.
     * @return distance is the calculated damerau-levenshtein distance.
     * */
    public double distance(String s1, String s2) {
    
        //transform strings into character sets (A Set is a Collection that cannot contain duplicate elements.) https://docs.oracle.com/javase/8/docs/api/java/util/Set.html
    Set<Character> set1 = new HashSet<>();
    Set<Character> set2 = new HashSet<>();

    for( char c : s1.toCharArray()){
        set1.add(c);
    }
    for( char c : s2.toCharArray()){
        set2.add(c);
    }
    
        
    // character set that will keep the intersection of the two sets
    //Set<Character> intersection = new HashSet<>();
    // intersection.addAll(set1); //adds s1 
    //alternatively these two steps above can be done in just one:
    Set<Character> intersection = new HashSet<>(set1);

        intersection.retainAll(set2); //intersection


    
            // character set that will keep the union of the two sets
            Set<Character> union = new HashSet<>();
            union.addAll(set1); //adds s1 
                    //alternatively these two steps above can be done in just one:
                    //Set<Character> intersection = new HashSet<>(set1);

            union.addAll(set2); //union
      


        //  Distance is computed as 1 - similarity
        double similarity = (double) intersection.size() /(double)  union.size();
        //this.ratio = similarity;
        double distance = 1.0 - similarity;
        //here we don't need to use calcratio the ratio is the distance which is 1 - similarity ???
         this.ratio = similarity;
      //this.ratio = distance;
        // this.ratio = 1 - distance; // I don't know if it's this one or the ones above
        return distance;

    }

    public String getMetrica(){

        return "Jaccard";
    }


@Override
public void calcRatio(double distance,int stringLength1, int  stringLength2) {
             //  Distance is computed as 1 - similarity
             double ratio = 1.0 - distance;
             this.ratio = ratio;
}

@Override
public Double getRatio(){

    return this.ratio;
}


@Override
public boolean compare(int tmp1, int tmp2){
//if it's smaller (closer to 0) it is more similar, I think that's it
 return tmp2 >= tmp1;
}
}