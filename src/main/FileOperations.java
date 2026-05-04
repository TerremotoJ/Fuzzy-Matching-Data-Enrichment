package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


import distance.Metric;


/**
 * The FileOperations class is a subclass that refers to the Dataset class.
 * It is where all necessary operations for csv files are performed.
 * @author Student
 * @version 14/05/2023
 */
public class FileOperations extends Dataset {
    
	/**
	 * The ReadCsv method will read the csv files inserted by the user.
	 * The first record is the header; a record with fewer fields than the header gets "" for the missing ones,
	 * and blank lines are skipped.
	 * @param filename is the name of the csv file.
	 * @return temp
	 */
    public static Dataset ReadCsv(String filename){
    Dataset temp = new Dataset();
    ArrayList<String> values;
    ArrayList<String> header = null;
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

        while ((values = readRecord(br)) != null) {
            if (values.size() == 1 && values.get(0).isEmpty()) {
                continue;
            }
            if (header == null) {
                header = values;
            } else {
                HashMap<String, String> linha = new HashMap<String, String>();
                for (int i = 0; i < header.size(); i++) {
                    linha.put(header.get(i), i < values.size() ? values.get(i) : "");
                }
                temp.add(linha);
            }
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }   
    return temp;
    }

    /**
     * Reads one CSV record (RFC 4180): commas inside double quotes do not split a field,
     * "" inside a quoted field is one quote, and a quoted field may continue on the next line.
     * @param br the reader, positioned at the start of a record.
     * @return the fields of the record, or null at the end of the file.
     */
    static ArrayList<String> readRecord(BufferedReader br) throws IOException {
        String line = br.readLine();
        if (line == null) {
            return null;
        }
        ArrayList<String> fields = new ArrayList<String>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;
        int i = 0;
        while (true) {
            if (i == line.length()) {
                if (!inQuotes) {
                    break;
                }
                String next = br.readLine();
                if (next == null) {
                    break; // unclosed quote at end of file: keep what was read
                }
                field.append('\n');
                line = next;
                i = 0;
                continue;
            }
            char c = line.charAt(i++);
            if (inQuotes) {
                if (c != '"') {
                    field.append(c);
                } else if (i < line.length() && line.charAt(i) == '"') {
                    field.append('"');
                    i++;
                } else {
                    inQuotes = false;
                }
            } else if (c == ',') {
                fields.add(field.toString());
                field.setLength(0);
            } else if (c == '"' && field.length() == 0) {
                inQuotes = true;
            } else {
                field.append(c);
            }
        }
        fields.add(field.toString());
        return fields;
    }

    /**
     * Quotes a value for CSV output when it contains a comma, a quote or a line break.
     * @param value the value to write; null is written as an empty field.
     * @return the value as it should appear in the file.
     */
    static String quote(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }


    /**
	 * The writeCsv method will write a new csv file containing the contents of the files previously inserted by the user, adding the chosen metric.
	 * The file is output.csv in the working directory.
	 * @param fuzzyJoin.
	 * @param metricaDistancia.
	 */
public static void writeCSV(FuzzyJoin fuzzyJoin, Metric metricaDistancia){
    writeCSV(fuzzyJoin, metricaDistancia, "output.csv");
}

    /**
	 * Same as writeCSV(fuzzyJoin, metricaDistancia), written to the given file.
	 * The metric's score column is always last.
	 * @param fuzzyJoin.
	 * @param metricaDistancia.
	 * @param filename is the name of the csv file to write.
	 */
public static void writeCSV(FuzzyJoin fuzzyJoin, Metric metricaDistancia, String filename){

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        
        // Get the keys from the first record
        HashMap<String, String> firstRecord = fuzzyJoin.getData().get(0);
        String keyDistancia = metricaDistancia.getMetrica();
        ArrayList<String> keys = new ArrayList<String>();
        for (String key : firstRecord.keySet()) {
            if (!key.equals(keyDistancia)) {
                keys.add(key);
            }
        }
        keys.add(keyDistancia);
        
        // Write the keys as the first row in the CSV
        ArrayList<String> row = new ArrayList<String>();
        for (String key : keys) {
            row.add(quote(key));
        }
        writer.write(String.join(",", row));
        writer.write("\n");
        
        // Write the values for each record in the CSV
        for (HashMap<String, String> record : fuzzyJoin.getData()) {
            row.clear();
            for (String key : keys) {
                row.add(quote(record.get(key)));
            }
            writer.write(String.join(",", row));
            writer.write("\n");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

}

}
