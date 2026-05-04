package distance;

/**
 * The metric interface will 
 * @author Student
 * @version 14/05/2023
 */
public interface Metric {

    
    Double getRatio();
    void calcRatio(double distance, int stringLength1, int stringLength2);
    String getMetrica();
    boolean compare(int tmp1, int tmp2);
    
    //double distance(String s1, String s2);
    //not sure if this below works, basically if s1 is null it gives an error but metrics should override if not null?
    
    /**
     * This method will check if any of the join fields are empty.
     * If they are empty, the program will throw an exception indicating the string is invalid.
     * @param s1 is the name of the parameter we want to compare.
     * @param s2 is the name of the other parameter we want to compare.
     * @return 0.0
     * */
   default double distance(String s1, String s2) {
    
        if(s1 == null){
          throw new NullPointerException("String 1 is invalid");
      }
  
      if(s2 == null){
          throw new NullPointerException("String 2 is invalid");
      }
      return 0.0;
    }
    

}