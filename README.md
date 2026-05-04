# Fuzzy Matching Data Enrichment

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

A Java command-line program that joins two CSV files on a text column when the values do not match exactly. For each row of the first file it finds the closest row in the second file by string distance and writes the joined rows, with a similarity score, to `output.csv`.

This is a 2023 university assignment for a data structures and algorithms course. The assignment set the command-line syntax, the Levenshtein score formula, the list of metrics, and the table format (an `ArrayList` of `HashMap`s, one map per row).

## How it works

1. `FileOperations.ReadCsv` reads each file into a `Dataset`, an `ArrayList<HashMap<String, String>>` keyed by the header row.
2. `MergeTables` compares every row of file 1 with every row of file 2 using the chosen metric and stores every pair with a 0-100 score. The work and memory grow with (rows in file 1) x (rows in file 2).
3. `FuzzyJoin` keeps the highest-scoring pair for each row of file 1. On a tie, the later row of file 2 wins.
4. `FileOperations.writeCSV` writes `output.csv` in the working directory, with the score as the last column, and the program prints the same rows to the console.

## The five metrics

Each metric implements the `distance.Metric` interface; `--distance` picks one per run. Strings are compared as they are: case-sensitive, no trimming.

| `--distance` | Distance | Score (0-100) |
|---|---|---|
| `Levenshtein` | insertions, deletions and substitutions | (len1 + len2 - distance) / (len1 + len2), the assignment's formula |
| `Damerau-Levenshtein` | as Levenshtein, plus swapping two neighbouring characters (optimal string alignment variant) | same formula |
| `Hamming` | positions where the characters differ | (len - distance) / len; strings of different lengths score 0 |
| `Jaccard` | 1 - Jaccard similarity of the two sets of characters | shared characters / all characters, as sets |
| `Cosine` | 1 - cosine similarity of the character-count vectors | the cosine similarity |

Any other `--distance` value falls back to Levenshtein.

## Usage

Needs a JDK 8 or newer (checked by compiling with `--release 8` and running on JDK 25). From the repository root:

```bash
javac -d bin src/main/*.java src/distance/*.java
java -cp bin main.app fuzzy-join --filename1=teste.csv --filename2=teste2.csv --name1=country --name2=country --distance=Levenshtein
```

All five flags are required:

- `--filename1`, `--filename2`: the two CSV files. The first line is the header. Quoted fields (with commas, `""` quotes or line breaks inside) and empty fields are read as in RFC 4180.
- `--name1`, `--name2`: the column to join on in each file.
- `--distance`: one of the five metric names above.

### Sample output

`teste.csv` has UK country names and populations; `teste2.csv` has misspelled names and GDP per capita. The command above writes this `output.csv`:

```
country,population_in_millions,GDP_per_capita,Levenshtein
England,55.98,45101.0,92
Scotland,5.45,37460.0,93
Wales,3.14,23882.0,89
United Kingdom,67.33,46510.28,74
Northern Ireland,1.89,24900.0,93
```

England was matched to "Englnd" (score 92) and United Kingdom to "United K." (score 74). Because both join columns are called `country`, the file 2 spelling is not in the output (see Limitations). With differently named columns both values are kept, for example `names1.csv` and `names2.csv`:

```bash
java -cp bin main.app fuzzy-join --filename1=names1.csv --filename2=names2.csv --name1=name "--name2=Person Name" --distance=Levenshtein
```

```
name,location,Person Name,Location,codename,Levenshtein
George Smiley,London,George SMILEY,London,Beggerman,81
Percy Alleline,London,Sam Collins,Vietnam,Tinker,64
...
Toby Esterhase,Vienna,Tony Esterhase,Vienna,Poorman,96
Peter Guillam,Brixton,Peter Guillam,Brixton,none,100
```

Every row of file 1 gets its best match, even when nothing in file 2 is close (Percy Alleline, 64). There is no minimum score.

`expedia.csv` (hotel room names) is used by the CSV reader tests.

## Tests

51 JUnit 5 tests cover the five metrics, `Dataset`, the CSV reader and writer (quoted commas, escaped quotes, empty and trailing-empty fields, line breaks inside quotes, a bundled file with quoted fields), `MergeTables`, `FuzzyJoin`, and a full run on `teste.csv` and `teste2.csv`. The JUnit console launcher is in `lib/`. From the repository root, after compiling the program as above:

```bash
javac -cp bin:lib/junit-platform-console-standalone-1.9.2.jar -d bin src/tests/*.java
java -jar lib/junit-platform-console-standalone-1.9.2.jar -cp bin --scan-classpath
```

On Windows, use `;` instead of `:` in the class path. Run the tests from the root: they read the sample CSV files there and write `output.csv`.

## Limitations

- All pairs are compared and kept in memory, so large files get slow and memory-heavy.
- When both files have a column with the same name, the value from file 1 replaces the one from file 2 in the joined row. This includes the join column when `--name1` and `--name2` are the same.
- Column order in `output.csv` follows `HashMap` iteration order, apart from the score, which is always last.
- Files are read with the platform's default character set (UTF-8 on Java 18 and newer).
- A missing flag or a flag without `=` stops the program with an exception instead of a usage message.

## Changes since the assignment

- The CSV reader split lines on every comma, so quoted fields such as `"Deluxe Room, 1 King Bed"` were cut in pieces and a row ending in empty fields crashed the program. It now parses quoted fields, `""` quotes, line breaks inside quotes and empty fields, and fills missing trailing fields with empty values.
- The CSV writer now quotes values that contain commas, quotes or line breaks, and no longer adds a comma at the end of every line.
- Removed a leftover debug line and a console header that was hard-coded to the sample columns; the console header now lists the real columns.
- Fixed the spelling of the `Damerau-Levenshtein` score column, and `Jaccard.calcRatio` (not used by the program), which returned distance + 1 instead of 1 - distance.
- Finished or removed the unfinished test stubs and corrected the Levenshtein tests that expected the wrong ratios.

## Project layout

```
src/main/      app (argument parsing), Dataset, FileOperations (CSV in/out), MergeTables, FuzzyJoin
src/distance/  Metric interface and the five metrics
src/tests/     JUnit 5 tests
lib/           JUnit console launcher used to run the tests
*.csv          sample inputs
```
