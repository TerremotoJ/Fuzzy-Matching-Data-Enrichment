package distance;


/**
 * The DamerauLevenshtein class will calculate the Damerau-Levenshtein metric.
 * @author Student
 * @version 14/05/2023
 */
public class DamerauLevenshtein  implements Metric {

	/**
     * The ratio of the damerau-levenshtein. metric.
     * */
    private Double ratio;

    public DamerauLevenshtein() {
        this.ratio = null;
    }

    /**
     * The distance method is where the damerau-levenshtein. metric is calculated.
     * @param s1 is the first string belonging to the join field chosen by the user.
     * @param s2 is the second string belonging to the join field chosen by the user.
     * @return distance is the calculated damerau-levenshtein distance.
     * */
    public double distance(String s1, String s2){
    
        int m = s1.length();
        int n = s2.length();
    
        int[][] dist = new int[m + 1][n + 1];
    
        for (int i = 0; i <= m; i++) {
            dist[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dist[0][j] = j;
        }
    
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                dist[i][j] = Math.min(dist[i - 1][j] + 1,
                        Math.min(dist[i][j - 1] + 1, dist[i - 1][j - 1] + cost));
    
                if (i > 1 && j > 1 && s1.charAt(i - 1) == s2.charAt(j - 2) && s1.charAt(i - 2) == s2.charAt(j - 1)) {
                    dist[i][j] = Math.min(dist[i][j], dist[i - 2][j - 2] + cost);
                }
            }
        }
        double distance= dist[m][n];
        calcRatio(distance, m, n);
        return distance;
    }
            
    

    public String getMetrica(){

        return "Damerau-Levenshtein";
    }

  
    @Override
    public void calcRatio(double distance, int stringLength1, int stringLength2) {
        double ration=  ((stringLength1 + stringLength2 - distance)/(stringLength1 + stringLength2));
        this.ratio = ration;
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
