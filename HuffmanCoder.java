package org.example;

import org.example.CustomPriorityQueue;
import org.example.HuffmanNode;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HuffmanCoder {

    private Map<Character, String> huffmanCodes = new HashMap<>();
    private Map<Character, Integer> frequencyMap = new HashMap<>();


    public void compressFile(String inputPath, String outputPath) {
        try {
            String content = readFile(inputPath);
            if (content.isEmpty()) {
                System.out.println("Plik jest pusty!");
                return;
            }

            frequencyMap.clear();
            for (char c : content.toCharArray()) {
                if (frequencyMap.containsKey(c)) {
                    frequencyMap.put(c, frequencyMap.get(c) + 1);
                } else {
                    frequencyMap.put(c, 1);
                }
            }

            HuffmanNode[] nodes = new HuffmanNode[frequencyMap.size()];
            int i = 0;
            for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
                nodes[i++] = new HuffmanNode(entry.getKey(), entry.getValue());
            }

            CustomPriorityQueue queue = new CustomPriorityQueue(nodes.length);
            queue.buildQueue(nodes);

            while (queue.getSize() > 1) {
                HuffmanNode left = queue.extractMin();
                HuffmanNode right = queue.extractMin();
                HuffmanNode parent = new HuffmanNode(left.frequency + right.frequency, left, right);
                queue.insert(parent);
            }
            HuffmanNode root = queue.extractMin();

            huffmanCodes.clear();
            generateCodes(root, "");

            saveCompressedFile(outputPath, content);

            System.out.println("Kompresja zakończona sukcesem!");
            System.out.println("Rozmiar wejściowy: " + content.length() + " bajtów (ok.)");
            File out = new File(outputPath);
            System.out.println("Rozmiar wyjściowy: " + out.length() + " bajtów");

        } catch (IOException e) {
            System.out.println("Błąd pliku: " + e.getMessage());
        }
    }

    private void generateCodes(HuffmanNode node, String code) {
        if (node == null) return;
        if (node.isLeaf()) {
            huffmanCodes.put(node.character, code);
        }
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    private void saveCompressedFile(String path, String content) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path))) {
            StringBuilder header = new StringBuilder();
            for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
                char key = entry.getKey();
                String keyStr = String.valueOf(key);
                if (key == '\n') keyStr = "\\n";
                else if (key == '\r') keyStr = "\\r";
                else if (key == ' ') keyStr = "\\s";

                header.append(keyStr).append(":").append(entry.getValue()).append(" ");
            }
            header.append("\n");

            bos.write(header.toString().getBytes());

            int buffer = 0;
            int bitCount = 0;

            for (char c : content.toCharArray()) {
                String code = huffmanCodes.get(c);
                for (char bit : code.toCharArray()) {
                    buffer = buffer << 1;
                    if (bit == '1') {
                        buffer = buffer | 1;
                    }
                    bitCount++;

                    if (bitCount == 8) {
                        bos.write(buffer);
                        buffer = 0;
                        bitCount = 0;
                    }
                }
            }

            if (bitCount > 0) {
                buffer = buffer << (8 - bitCount);
                bos.write(buffer);
            }
        }
    }


    public void decompressFile(String inputPath, String outputPath) {
        try {
            File file = new File(inputPath);
            FileInputStream fis = new FileInputStream(file);

            StringBuilder headerBuilder = new StringBuilder();
            int b;
            while ((b = fis.read()) != -1) {
                char c = (char) b;
                if (c == '\n') break;
                headerBuilder.append(c);
            }

            String header = headerBuilder.toString();
            Map<String, Character> reverseCodes = rebuildHuffmanTreeFromHeader(header);

            StringBuilder decodedText = new StringBuilder();
            StringBuilder currentCode = new StringBuilder();

            BufferedInputStream bis = new BufferedInputStream(fis);
            int totalChars = 0;
            for(int f : frequencyMap.values()) totalChars += f;

            int charsDecoded = 0;

            while ((b = bis.read()) != -1 && charsDecoded < totalChars) {
                for (int i = 7; i >= 0; i--) {
                    int bit = (b >> i) & 1;
                    currentCode.append(bit);

                    if (reverseCodes.containsKey(currentCode.toString())) {
                        decodedText.append(reverseCodes.get(currentCode.toString()));
                        currentCode.setLength(0); // Reset kodu
                        charsDecoded++;
                        if(charsDecoded == totalChars) break; // Koniec
                    }
                }
            }

            bis.close();
            fis.close();

            try (PrintWriter out = new PrintWriter(outputPath)) {
                out.print(decodedText.toString());
            }

            System.out.println("Dekompresja zakończona sukcesem!");

        } catch (IOException e) {
            System.out.println("Błąd podczas dekompresji: " + e.getMessage());
        }
    }

    private Map<String, Character> rebuildHuffmanTreeFromHeader(String header) {
        frequencyMap.clear();
        String[] parts = header.split(" ");

        for (String part : parts) {
            if(part.isEmpty()) continue;

            int colonIndex = part.lastIndexOf(":");
            if (colonIndex == -1) continue;

            String charStr = part.substring(0, colonIndex);
            String freqStr = part.substring(colonIndex + 1);

            char character;
            if (charStr.equals("\\n")) character = '\n';
            else if (charStr.equals("\\r")) character = '\r';
            else if (charStr.equals("\\s")) character = ' ';
            else character = charStr.charAt(0);

            frequencyMap.put(character, Integer.parseInt(freqStr));
        }

        HuffmanNode[] nodes = new HuffmanNode[frequencyMap.size()];
        int i = 0;
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            nodes[i++] = new HuffmanNode(entry.getKey(), entry.getValue());
        }

        CustomPriorityQueue queue = new CustomPriorityQueue(nodes.length);
        queue.buildQueue(nodes);

        while (queue.getSize() > 1) {
            HuffmanNode left = queue.extractMin();
            HuffmanNode right = queue.extractMin();
            HuffmanNode parent = new HuffmanNode(left.frequency + right.frequency, left, right);
            queue.insert(parent);
        }

        HuffmanNode root = queue.extractMin();
        huffmanCodes.clear();
        generateCodes(root, "");

        Map<String, Character> reverseMap = new HashMap<>();
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }
        return reverseMap;
    }

    private String readFile(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int c;
            while ((c = br.read()) != -1) {
                sb.append((char) c);
            }
        }
        return sb.toString();
    }
}