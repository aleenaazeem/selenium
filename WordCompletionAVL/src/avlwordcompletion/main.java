package avlwordcompletion;

import java.util.List;
import java.util.Map;

public class main {
    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();

        // Step 1: Generate data.csv (only needed the first time)
        CSVReader.generateWordFrequencyFile();

        // Step 2: Read words from data.csv and load into memory
        Map<String, Integer> wordCounts = CSVReader.readCSV("data.csv");

        // Step 3: Insert words into AVL Tree
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            avlTree.addWord(entry.getKey(), entry.getValue());
        }

        // Step 4: Test Autocomplete
        String prefix = "cl";  // Change this to test different prefixes
        List<String> suggestions = avlTree.autocomplete(prefix);

        // Step 5: Display autocomplete results
        System.out.println("\n🔎 Autocomplete Suggestions for '" + prefix + "':");
        for (String suggestion : suggestions) {
            System.out.println(suggestion);
        }
    }
}
