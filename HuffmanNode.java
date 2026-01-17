package org.example;

public class HuffmanNode implements Comparable<HuffmanNode> {
    Character character;
    int frequency;
    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode(Character character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    // Konstruktor dla węzłów wewnętrznych
    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.character = null;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }

    @Override
    public String toString() {
        String charDisplay = (character == null) ? "WEZEL" : "'" + character + "'";
        return "[" + charDisplay + ", freq: " + frequency + "]";
    }
}
