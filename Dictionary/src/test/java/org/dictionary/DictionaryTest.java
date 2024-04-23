package org.dictionary;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;


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
            "distinct31622words.txt",
            "distinct1000000words.txt",
    };

    private final String[] nonDistinctFileNames = {
            "non-distinct100words.txt",
            "non-distinct1000words.txt",
            "non-distinct10000words.txt",
            "non-distinct100000words.txt",
            "non-distinct1000000words.txt"
    };

    private static final String path = "C:\\Users\\WORK UEFI\\OneDrive - Alexandria University\\Desktop\\Perfect-Hashing-Dictionary\\Dictionary\\src\\test\\resources\\";
    private static final int TEST_CASES = 10;

    private static Stream<Arguments> provideInitialSizeAndMethod() {
        return Stream.of(
                Arguments.of(0, 1),
                Arguments.of(0, 2),
                Arguments.of(100, 1),
                Arguments.of(100, 2),
                Arguments.of(1000, 1),
                Arguments.of(1000, 2),
                Arguments.of(10000, 1),
                Arguments.of(10000, 2),
                Arguments.of(100000, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInitialSizeAndMethod")
    public void testInsertionQuadratic(int initialSize, int method) throws FileNotFoundException {
        long startTime, endTime, quadraticInsertionTime = 0L;
        for (int i = 0; i < TEST_CASES; ++i) {
            Dictionary<String> temp = createDictionary(initialSize, method);
            System.out.println("Start");
            startTime = System.currentTimeMillis();
            System.out.println("Inserted? : " + temp.insert(generateRandomWord()));
            endTime = System.currentTimeMillis();
            quadraticInsertionTime += endTime - startTime;
            System.out.println("Time = " + (endTime - startTime) + "ms");
            System.out.println("----------------");
        }
        System.out.println("Average insertion time : " + (double) quadraticInsertionTime / TEST_CASES);
    }

    @Test
    public void testInsertionLinear() {
        Dictionary<String> dictionary = new Dictionary<>(1);

        long startTime, endTime, quadraticInsertionTime = 0L;
        for (int i = 0; i < TEST_CASES; ++i) {
            if (i % 2 == 0) continue;
            startTime = System.currentTimeMillis();
            System.out.println(dictionary.insert(generateRandomWord()));
            endTime = System.currentTimeMillis();
            quadraticInsertionTime += endTime - startTime;
            System.out.println(endTime - startTime + "ms");
        }
        System.out.println("Average insertion time : " + (double) quadraticInsertionTime / TEST_CASES);
    }

    @Test
    public void testDeletion() {
        Dictionary<String> dictionary = new Dictionary<>(2);
        assertTrue(dictionary.delete("apple"));
        assertFalse(dictionary.search("apple"));
    }

    /**
     * Test searching for words in different dictionary sizes.
     * Expected O(1).
     **/
    @Test
    public void testSearch() {
        Dictionary<String> dictionary = new Dictionary<>(1);
        dictionary.insert("asdhais");
        assertTrue(dictionary.search("asdhais"));
    }

    /**
     * Test batch insertion with different number of words on different dictionary sizes.
     * Expected O(K).
     * K is number of not repeated words to be inserted.
     **/
    @Test
    public void testBatchInsertQuadratic() throws FileNotFoundException {
        File file = new File(path.concat(distinctFileNames[0]));
        ArrayList<String> wordList = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            wordList.add(line);
        }
        scanner.close();

        String[] words =  wordList.toArray(new String[0]);

        long startTime, endTime, quadraticBatchInsertionTime = 0L;
        for (int i = 0; i < TEST_CASES; ++i) {
            startTime = System.currentTimeMillis();
            Dictionary<String> dictionary = new Dictionary<>(2);
            dictionary.batchInsert(words);
            endTime = System.currentTimeMillis();
            quadraticBatchInsertionTime += endTime - startTime;
            System.out.println(endTime - startTime + "ms");
            for (String word : words)
                assertTrue(dictionary.search(word));

        }
        System.out.println("Average insertion time : " + (double) quadraticBatchInsertionTime / TEST_CASES);
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
    * Generates random string with max size equal 10.
    * @RETURN generated string.
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

    private Dictionary<String> createDictionary(int initialSize, int method) throws FileNotFoundException {
        Dictionary<String> dictionary = new Dictionary<>(method);
        File file = switch (initialSize) {
            case 100 -> new File(path.concat(distinctFileNames[0]));
            case 1000 -> new File(path.concat(distinctFileNames[1]));
            case 10000 -> new File(path.concat(distinctFileNames[2]));
            case 100000 -> new File(path.concat(distinctFileNames[3]));
            case 1000000 -> new File(path.concat(distinctFileNames[5]));
            default -> null;
        };
        if (file != null)
            dictionary.batchInsert(readFile(file));
        return dictionary;
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
