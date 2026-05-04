package main;
import java.util.*;

import distance.Cosine;
import distance.DamerauLevenshtein;
import distance.Hamming;
import distance.Jaccard;
import distance.Levenshtein;
import distance.Metric;
//https://en.wikipedia.org/wiki/Normalized_compression_distance

/**
 * The app class is the main class of the project.
 * This is where input occurs.
 * Input example: fuzzy-join --filename1=teste.csv --filename2=teste2.csv --name1=country --name2=country --distance=levenshtein
 * @author Student.
 * @version 14/05/2023
 */
public class app {
	/**
	 * Main method.
	 * Where CSV files, join keys, and the metric are read.
	 * @param args, i.e., the argument written in the terminal.
	 */
    public static void main(String[] args) {

    String filename1 = null;
    String filename2 = null;
    String name1 = null;
    String name2 = null;
    String distance = null;
    Metric metricaDistancia= null ;
    Dataset db1 = null;
    Dataset db2 = null;

      if(!args[0].equals("fuzzy-join")){UnknownArg(args[0]);}   
    for (int i = 1; i<args.length; i++) {
        String[] parts = args[i].split("=", 2);
        String name = parts[0];
        String value = parts[1];
        switch (name) {
            case "--filename1":
                filename1 = value;
                db1 = FileOperations.ReadCsv(filename1);
                break;
            case "--filename2":
                filename2 = value;
                db2 = FileOperations.ReadCsv(filename2);
                break;
            case "--name1":
                name1 = value;
                break;
            case "--name2":
                name2 = value;
                break;
            case "--distance":
                distance = value;
                metricaDistancia = CheckDistance(distance,name1,name2);
                break;
            default:
                UnknownArg(args[i]);
        }
    }
    MergeTables mergeTables = new MergeTables(db1, db2, metricaDistancia, name1, name2);
    FuzzyJoin fuzzyJoin = new FuzzyJoin(mergeTables, name1, metricaDistancia);
    FileOperations.writeCSV(fuzzyJoin,metricaDistancia);

    for (String key : fuzzyJoin.getData().get(0).keySet()) {
      System.out.print(key + ", ");
    }
    System.out.println();

    for (HashMap<String, String> record : fuzzyJoin.getData()) {
      for (String key : record.keySet()) {
        System.out.print(record.get(key) + ", ");
      }
      System.out.println();
      }
   
    }

/**
 * The UnknownArg method will print an error to the terminal if the argument is written incorrectly.
 * It also prints an example of how to write the argument.
 * After the error, the program terminates.
 * @param arg is the argument written in the terminal.
 */
    public static void UnknownArg(String arg){
      System.out.println("Unknown argument: " + arg);
      System.out.println("Example of usage: fuzzy-join --filename1=filename.csv --filename2=filename.csv --name1=name1 --name2=name2 --distance=levenshtein" );

      System.exit(1);
    }

    /**
     * This method receives the names of the parameters the user wants to compare and the name of the metric to use for that comparison.
     * Then it will send the data to the class that corresponds to the chosen distance.
     * The else is equivalent to a default in a switch case.
     * @param arg is the name of the distance that the user will select.
     * @param name1 is the name of the parameter we want to compare.
     * @param name2 is the name of the other parameter we want to compare.
     * @return metricaDistancia.
     * */
public static Metric CheckDistance(String arg,String name1, String name2){
  Metric metricaDistancia= null ;

  if(arg.equals("Levenshtein")){
    metricaDistancia = new Levenshtein();
   
  }else if(arg.equals("Cosine")){
    metricaDistancia = new Cosine();
  }else if(arg.equals("Jaccard")){
    metricaDistancia = new Jaccard();
  }else if(arg.equals("Hamming")){
    metricaDistancia = new Hamming();
  }else if(arg.equals("Damerau-Levenshtein")){
     metricaDistancia = new DamerauLevenshtein();
  }else{
    metricaDistancia = new Levenshtein();
  }
return metricaDistancia;
}

}



