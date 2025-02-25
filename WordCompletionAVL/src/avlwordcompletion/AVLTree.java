package avlwordcompletion;

import java.util.ArrayList;
import java.util.List;

public class AVLTree {
    private AVLNode root;

    // Get height of a node
    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    // Get balance factor
    private int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // Right Rotate
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    // Left Rotate
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    // Insert a word into the AVL tree
    private AVLNode insert(AVLNode node, String word, int frequency) {
        if (node == null) return new AVLNode(word, frequency);

        if (word.compareTo(node.word) < 0) 
            node.left = insert(node.left, word, frequency);
        else if (word.compareTo(node.word) > 0) 
            node.right = insert(node.right, word, frequency);
        else {
            node.frequency += frequency;
            return node;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        // Balancing the AVL Tree
        if (balance > 1 && word.compareTo(node.left.word) < 0)
            return rightRotate(node);
        if (balance < -1 && word.compareTo(node.right.word) > 0)
            return leftRotate(node);
        if (balance > 1 && word.compareTo(node.left.word) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && word.compareTo(node.right.word) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Public method to add words
    public void addWord(String word, int frequency) {
        root = insert(root, word, frequency);
    }

    // Find node with given prefix
    private AVLNode findPrefixNode(AVLNode node, String prefix) {
        if (node == null || node.word.startsWith(prefix)) return node;
        return (prefix.compareTo(node.word) < 0) ? findPrefixNode(node.left, prefix) : findPrefixNode(node.right, prefix);
    }

    // Collect words from subtree
    private void collectWords(AVLNode node, List<String> words) {
        if (node == null) return;
        words.add(node.word + " (" + node.frequency + ")");
        collectWords(node.left, words);
        collectWords(node.right, words);
    }

    // Autocomplete function
    public List<String> autocomplete(String prefix) {
        List<String> suggestions = new ArrayList<>();
        AVLNode prefixNode = findPrefixNode(root, prefix);
        if (prefixNode != null) collectWords(prefixNode, suggestions);
        return suggestions;
    }
}
