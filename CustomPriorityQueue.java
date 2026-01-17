package org.example;
import java.util.Arrays;

import java.util.Arrays;

public class CustomPriorityQueue {
    private HuffmanNode[] heap;
    private int size;
    private int capacity;

    public CustomPriorityQueue(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new HuffmanNode[capacity];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void insert(HuffmanNode node) {
        if (size == capacity) {
            resize();
        }
        heap[size] = node;
        bubbleUp(size);
        size++;
    }

    public HuffmanNode extractMin() {
        if (isEmpty()) {
            return null;
        }
        HuffmanNode min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        if (size > 0) {
            heapify(0);
        }
        return min;
    }

    public void decreasePriority(int index, int newFrequency) {
        if (index < 0 || index >= size) {
            System.out.println("Błąd: Nieprawidłowy indeks.");
            return;
        }
        if (newFrequency > heap[index].frequency) {
            System.out.println("Błąd: Nowy priorytet musi być mniejszy (to jest Min-Heap).");
            return;
        }
        heap[index].frequency = newFrequency;
        bubbleUp(index);
        System.out.println("Priorytet zmieniony pomyślnie.");
    }

    public void buildQueue(HuffmanNode[] nodes) {
        this.size = nodes.length;
        this.capacity = nodes.length;
        if (this.capacity == 0) this.capacity = 1;

        this.heap = Arrays.copyOf(nodes, this.capacity);

        for (int i = (size / 2) - 1; i >= 0; i--) {
            heapify(i);
        }
    }

    private void heapify(int index) {
        int smallest = index;
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        if (leftChild < size && heap[leftChild].compareTo(heap[smallest]) < 0) {
            smallest = leftChild;
        }

        if (rightChild < size && heap[rightChild].compareTo(heap[smallest]) < 0) {
            smallest = rightChild;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapify(smallest);
        }
    }

    private void bubbleUp(int index) {
        int parentIndex = (index - 1) / 2;
        while (index > 0 && heap[index].compareTo(heap[parentIndex]) < 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    private void swap(int i, int j) {
        HuffmanNode temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void resize() {
        capacity = capacity * 2;
        heap = Arrays.copyOf(heap, capacity);
    }

    public void printQueue() {
        if (isEmpty()) {
            System.out.println("Kolejka jest pusta.");
            return;
        }
        System.out.println("Aktualny stan kopca (Indeks: [Znak, Częstotliwość]):");
        for (int i = 0; i < size; i++) {
            System.out.println(i + ": " + heap[i] + "  ");
            if ((i + 1) % 5 == 0) System.out.println();
        }
        System.out.println();
    }
}