package distance;

/**
 * The Levenshtein class calculates the levenshtein metric.
 * @author Student
 * @version 14/05/2023
 */
public class Levenshtein implements Metric{

	/**
     * The ratio of the levenshtein metric.
     * */
    private Double ratio;
    
    
    public Levenshtein(){
        this.ratio = null;
    }
  
    /**
     * The distance method is where the levenshtein metric is calculated.
     * @param s1 is the first string belonging to the join field chosen by the user.
     * @param s2 is the second string belonging to the join field chosen by the user.
     * @return distance is the calculated damerau-levenshtein distance.
     * */
    @Override
    public double distance(String s1, String s2) {
        /* 
        int[][] distance = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 1; j <= s2.length(); j++) {
            distance[0][j] = j;
        }

        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                distance[i][j] = Math.min(Math.min(distance[i - 1][j] + 1, distance[i][j - 1] + 1),
                        distance[i - 1][j - 1] + cost);
            }
        }

        return distance[s1.length()][s2.length()];

        */


        int m = s1.length();
        int n = s2.length();
        // create 2D array to store distances
        int[][] distances = new int[m + 1][n + 1];

        // initialize first row and column
        for (int i = 0; i <= m; i++) {
            distances[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            distances[0][j] = j;
        }

        // compute distances
        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= m; i++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    distances[i][j] = distances[i - 1][j - 1];
                } else {
                    int delete = distances[i - 1][j] + 1;
                    int insert = distances[i][j - 1] + 1;
                    int substitute = distances[i - 1][j - 1] + 1;
                    int min = Math.min(delete, Math.min(insert, substitute));
                    distances[i][j] = min;
                }
            }
        }
       // double ration= (double) (s1.length() + s2.length() -  distances[m][n])/(s1.length() + s2.length());
        
        double distance = (double)distances[m][n];
       calcRatio(distance, m, n);
        return distance;

        // return final distance
        //double ration= (double) (s1.length() + s2.length() -  distances[m][n])/(s1.length() + s2.length());
        //return ration;
    }

    @Override
    public String getMetrica(){

        return "Levenshtein";
    }

    


    @Override
    public void calcRatio(double distance,int stringLength1, int  stringLength2) {
        double ration=  ((stringLength1 + stringLength2 - distance)/(stringLength1 + stringLength2));
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

    /* 
    @Override
    public Double isMoreSimilar(int stringLength1, int  stringLength2) {

    }
*/
}
