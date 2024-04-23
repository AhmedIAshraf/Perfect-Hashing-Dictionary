import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class WriteWordsToFile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of words: ");
        int numberOfWords = scanner.nextInt();
        String filePath = "words.txt";

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            Set<String> wordSet = generateUniqueWords(numberOfWords);

            for (String word : wordSet) {
                bufferedWriter.write(word);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            System.out.println("Words written to " + filePath);

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    private static Set<String> generateUniqueWords(int numberOfWords) {
        Set<String> wordSet = new HashSet<>();
        while (wordSet.size() < numberOfWords) {
            String word = generateRandomWord();
            wordSet.add(word);
        }
        return wordSet;
    }

    private static String generateRandomWord() {
        StringBuilder word = new StringBuilder();
        int wordLength = (int) (Math.random() * 10) + 1;

        for (int i = 0; i < wordLength; i++) {
            char randomChar = (char) ('a' + (int) (Math.random() * 26));
            word.append(randomChar);
        }

        return word.toString();
    }
}
