package org.dictionary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class WriteWordsToFile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of words: ");
        int numberOfWords= scanner.nextInt();
        System.out.print("Enter 0 for distinct else for non: ");
        int is_distinct = scanner.nextByte();
        String filePath = String.valueOf(numberOfWords).concat("words.txt");

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            ArrayList<String> wordList = generateUniqueWords(numberOfWords, is_distinct);

            for (String word : wordList) {
                bufferedWriter.write(word);
                bufferedWriter.write(", ");
            }

            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    private static ArrayList<String> generateUniqueWords(int numberOfWords, int is_distinct) {
        ArrayList<String> wordList = new ArrayList<>();
        while (wordList.size() < numberOfWords) {
            String word = generateRandomWord();
            if (is_distinct == 0) {
                if (!wordList.contains(word))
                    wordList.add(word);
            } else
                wordList.add(word);
        }


        return wordList;
    }

    private static String generateRandomWord() {
        StringBuilder word = new StringBuilder();
        int wordLength = (int) (Math.random() * 10) + 1; // Generate random word length between 1 and 10 characters

        for (int i = 0; i < wordLength; i++) {
            char randomChar = (char) ('a' + (int) (Math.random() * 26)); // Generate random lowercase letter
            word.append(randomChar);
        }

        return word.toString();
    }
}

