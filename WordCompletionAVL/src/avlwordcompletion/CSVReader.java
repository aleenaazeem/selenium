package avlwordcompletion;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CSVReader {

    // ✅ Step 1: Generate data.csv from the input CSV files
    public static void generateWordFrequencyFile() {
        String[] inputFiles = {
            "aws_pricing_combined.csv",
            "dropbox_data.csv",
            "output.csv",
            "scraped_final_data.csv"
        };

        HashMap<String, Integer> wordCounts = new HashMap<>();

        for (String file : inputFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader("resources/" + file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] words = line.toLowerCase().split("\\s+");
                    for (String word : words) {
                        word = word.replaceAll("[^a-zA-Z]", "");
                        if (!word.isEmpty()) {
                            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("❌ Error reading file: " + file);
            }
        }

        // Save word frequencies to data.csv
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/data.csv"))) {
            for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error writing to data.csv");
        }

        System.out.println("✅ Word frequency file generated successfully as data.csv.");
    }

    // ✅ Step 2: Read words from data.csv
    public static HashMap<String, Integer> readCSV(String fileName) {
        HashMap<String, Integer> wordCounts = new HashMap<>();

        try {
            File f = new File("resources/" + fileName);
            System.out.println("🔍 Checking file path: " + f.getAbsolutePath());

            if (!f.exists()) {
                System.out.println("❌ File NOT found: " + fileName);
                return wordCounts;
            }

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String word = parts[0];
                    int frequency = Integer.parseInt(parts[1]);
                    wordCounts.put(word, frequency);
                }
            }
            br.close();
            System.out.println("✅ data.csv loaded successfully!");
        } catch (IOException e) {
            System.out.println("❌ Error reading " + fileName);
            e.printStackTrace();
        }

        return wordCounts;
    }
}
