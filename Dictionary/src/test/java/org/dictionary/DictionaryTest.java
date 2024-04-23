package org.dictionary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ValueSource;


import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class DictionaryTest {

    private final String[] distinctFileNames = {
            "distinct100words.txt",
            "distinct1000words.txt",
            "distinct10000words.txt",
            "distinct100000words.txt",
            "distinct1000000words.txt"
    };

    private final String[] nonDistinctFileNames = {
            "non-distinct100words.txt",
            "non-distinct1000words.txt",
            "non-distinct10000words.txt",
            "non-distinct100000words.txt",
            "non-distinct1000000words.txt"
    };

    private static final String path = "C:\\Users\\WORK UEFI\\OneDrive - Alexandria University\\Desktop\\Perfect-Hashing-Dictionary\\Dictionary\\src\\test\\resources\\";

    private static Stream<Arguments> provideData() {
        return Stream.of(
                Arguments.of(0, 1),
                Arguments.of(0, 2),
                Arguments.of(1, 1),
                Arguments.of(1, 2),
                Arguments.of(2, 1),
                Arguments.of(2, 2),
                Arguments.of(3, 1),
                Arguments.of(3, 2),
                Arguments.of(4, 1)
        );
    }

    public static Stream<Arguments> provideBatchInsertData() {
        return Stream.of(
                Arguments.of(0, 1),
                Arguments.of(0, 2),
                Arguments.of(1, 1),
                Arguments.of(1, 2),
                Arguments.of(2, 1),
                Arguments.of(2, 2),
                Arguments.of(3, 1),
                Arguments.of(4, 1),
                Arguments.of(3, 2)
        );
    }

    @ParameterizedTest
    @MethodSource("provideData")
    public void testInsertion(int fileIndex, int method) throws FileNotFoundException {
        String word = generateRandomWord();
        long startTime, endTime;
        File file = new File(path.concat(distinctFileNames[fileIndex]));
        String[] words = readFile(file);
        Dictionary<String> dictionary = new Dictionary<>(method);
        dictionary.batchInsert(words);
        startTime = System.currentTimeMillis();
        System.out.println("Inserted? : " + dictionary.insert(word));
        endTime = System.currentTimeMillis();
        System.out.println("Time = " + (endTime - startTime) + "ms");
        assertTrue(dictionary.search(word));
    }

    @ParameterizedTest
    @MethodSource("provideData")
    public void testDeletion(int fileIndex, int method) {
        Dictionary<String> dictionary = new Dictionary<>(method);
        assertTrue(dictionary.delete("apple"));
        assertFalse(dictionary.delete("apple"));
    }

    /**
     * Test searching for words in different dictionary sizes.
     * Expected O(1).
     **/
    @ParameterizedTest
    @MethodSource("provideData")
    public void testSearch(int fileIndex, int method) {
        long startTime, endTime;
        Dictionary<String> dictionary = new Dictionary<>(method);
        startTime = System.currentTimeMillis();
        assertFalse(dictionary.search("kalolo"));
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime + "ms");

        startTime = System.currentTimeMillis();
        assertTrue(dictionary.search(""));
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime + "ms");
    }



    /**
     * Test batch deletion with different number of words on different dictionary sizes.
     * Expected O(K).
     * K is number of words to be deleted.
     **/
    @Test
    public void testBatchDelete() {

    }

    /**
     * Test batch insertion with different number of words on different dictionary sizes.
     * Expected O(K).
     * K is number of not repeated words to be inserted.
     **/
    @ParameterizedTest
    @MethodSource("provideBatchInsertData")
    public void testBatchInsert(int fileIndex, int method) throws FileNotFoundException {
        Dictionary<String> dictionary = new Dictionary<>(method);
        File file = new File(path.concat(distinctFileNames[fileIndex]));

        long startTime, endTime;
        startTime = System.currentTimeMillis();
        dictionary.batchInsert(readFile(file));
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime + "ms");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void testInsertEmptyDictionary(int method) {
        Dictionary<String> dictionary = new Dictionary<>(method);
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        assertTrue(dictionary.insert(generateRandomWord()));
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime + "ms");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void testDeleteEmptyDictionary(int method) {
        Dictionary<String> dictionary = new Dictionary<>(method);
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        assertFalse(dictionary.delete(generateRandomWord()));
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime + "ms");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void testSearchEmptyDictionary(int method) {
        Dictionary<String> dictionary = new Dictionary<>(method);
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        assertFalse(dictionary.search(generateRandomWord()));
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime + "ms");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void comprehensiveTests(int method) {
        Dictionary<String> dictionary = new Dictionary<>(method);
        int size = 0;
        for (int i = 0; i < Integer.MAX_VALUE; ++i) {
            if (i % 2 == 0) continue;
            String word = generateRandomWord();
            if (dictionary.insert(word)) size++;
            System.out.println(dictionary.search(word));
            System.out.println("Size = " + size);
            System.out.println();
        }
    }

    /**
    * Generates random string with max size equal 10.
    * @return  generated string.
     **/
    private String generateRandomWord() {
        StringBuilder word = new StringBuilder();
        int wordLength = (int) (Math.random() * 10) + 1;

        for (int i = 0; i < wordLength; i++) {
            char randomChar = (char) ('a' + (int) (Math.random() * 26));
            word.append(randomChar);
        }
        return word.toString();
    }


    private String[] readFile(File file) throws FileNotFoundException {
        if (file == null)
            return new String[]{};
        ArrayList<String> wordList = new ArrayList<>();
        Scanner fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            wordList.add(line);
        }
        fileScanner.close();

        return wordList.toArray(new String[0]);
    }
}
