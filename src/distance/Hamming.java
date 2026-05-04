package distance;


/* 
the Hamming distance between two strings of equal length is the number of positions at which the corresponding symbols are different.
https://en.wikipedia.org/wiki/Hamming_distance
test to see results
*/

/**
 * The Hamming class calculates the hamming metric.
 * @author Student
 * @version 14/05/2023
 */
public class Hamming implements Metric {

	/**
     * The ratio of the hamming metric.
     * */
    private Double ratio;
    
    public Hamming() {
        this.ratio = null;
    }

    /**
     * The distance method is where the hamming metric is calculated.
     * @param s1 is the first string belonging to the join field chosen by the user.
     * @param s2 is the second string belonging to the join field chosen by the user.
     * @return distance is the calculated damerau-levenshtein distance.
     * */
    public double distance(String s1, String s2){
    	int distance = 0;
      
        if (s1.length() != s2.length()) {
           // throw new IllegalArgumentException("to use hamming the strings must have the same length");
         this.ratio = 0.0;
           return 0;
        }else {
            for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i))
            distance++;
            }
            /*
            calcRatio(distance, s1.length(), s2.length());
            return distance;
*/
            }
        calcRatio(distance, s1.length(), s2.length());
        return distance;
    }
    
    @Override
    public String getMetrica(){

        return "Hamming";
    }


    @Override
    public void calcRatio(double distance,int stringLength1, int  stringLength2) {
       double ration = (double)(stringLength1 - distance) / (double)stringLength1;
       // double ration = 1.0 - ((double) distance / stringLength1); ??? I don't know if it's this one or the one above or distance/s1.length()
        this.ratio = ration;
    }

    @Override
    public Double getRatio(){

        return this.ratio;
    }
    
    @Override
    public boolean compare(int tmp1, int tmp2){

        return tmp1 <= tmp2;
    }


}