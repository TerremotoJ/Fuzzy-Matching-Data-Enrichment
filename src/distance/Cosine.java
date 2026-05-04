package distance;
import java.util.*;


/**
 * The Cosine class calculates the cosine metric.
 * @author Student
 * @version 14/05/2023
 */
public class Cosine implements Metric {
    
	/**
     * The ratio of the cosine metric.
     * */
	private Double ratio;

    public Cosine() {
        this.ratio = null;
    }

    /**
     * The distance method is where the cosine metric is calculated.
     * @param s1 is the first string belonging to the join field chosen by the user.
     * @param s2 is the second string belonging to the join field chosen by the user.
     * @return distance is the calculated cosine distance.
     * */
    public double distance(String s1, String s2){
    
        Map<Character,Integer> freq1 = new HashMap<>();
        Map<Character,Integer> freq2 = new HashMap<>();



         for( char c : s1.toCharArray()){
            freq1.put(c,freq1.getOrDefault(c, 0)+ 1);           //if there are 0 occurrences of c it returns 0 and puts 1 for key c
        }
        for( char c : s2.toCharArray()){
            freq2.put(c, freq2.getOrDefault(c, 0) + 1);
        }

        // product between each occurrence, map.entry to access keys
        double produto = 0;
       //for (Map.Entry<Character, Integer> entry : freq1.entrySet()) {
        for(char c : freq1.keySet()){
          /*   char c = entry.getKey(); // here it gets the current key/character
            int temp1= entry.getValue(); // here it gets the number of times
            int temp2 =  freq2.getOrDefault(c, 0); // will look for the number of times character c from s1 exists in s2
            produto += temp1 * temp2;
            */
            if (freq2.containsKey(c)) {
                produto += freq1.get(c) * freq2.get(c);
            }


        }

                // I think values() gets the number of times each character appears, stream() transforms that collection into a stream, 
                //maptodouble() to transform values to double and return count * count, I think the result is the sum of the times each character appears in the string ^2


        double length1 = Math.sqrt(freq1.values().stream().mapToDouble(count -> count * count).sum());
        double length2 = Math.sqrt(freq2.values().stream().mapToDouble(count -> count * count).sum());


            double distance = 1.0 - produto / (length1 * length2);
            calcRatio(distance, s1.length(), s2. length());
        return  distance;
            
    }

    public String getMetrica(){
        return "Cosine";
    }

    
    @Override
    public void calcRatio(double distance, int stringLength1, int stringLength2) {
        double ratio = 1.0 - distance;
        this.ratio = ratio;
    }

    @Override
    public Double getRatio() {
        return this.ratio;
    }

    @Override
    public boolean compare(int tmp1, int tmp2) {
      return tmp1 <= tmp2;
    }

}