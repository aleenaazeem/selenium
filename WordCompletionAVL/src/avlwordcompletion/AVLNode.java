package avlwordcompletion;

class AVLNode {
    String word;
    int frequency;
    AVLNode left, right;
    int height;

    public AVLNode(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
        this.height = 1;
        this.left = this.right = null;
    }
}
