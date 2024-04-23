package org.dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] msgList = {
            " inserted successfully",
            " already exists",
            " doesn't exist",
            " found",
            " deleted successfully"
    };

    /**
     * Reads array of words each on separated line.
     * @return Array of words read from the file
     */
    private static String[] readFile() throws FileNotFoundException {
        File file;
        do {
            String path;
            System.out.print("Enter the File Path: ");
            scanner.nextLine();
            path = scanner.nextLine();
            file = new File(path);
            if (!file.exists())
                System.out.println("File not found!");
        } while (!file.exists());
        ArrayList<String> wordList = new ArrayList<>();
        Scanner fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            wordList.add(line);
        }
        fileScanner.close();

        return wordList.toArray(new String[0]);
    }

    private static void lower(String[] words) {
        for (int i = 0; i < words.length; ++i) {
            if (!isNumeric(words[i]))
                words[i] = words[i].toLowerCase();
        }
    }

    /**
     * Checks if a string is a number or not.
     * @param str
     * @return true if the string is
     */
    private static boolean isNumeric(String str) { // Replace it with Nan=N
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        boolean hasDictionary = false;
        Dictionary<String> dictionary = null;
        while(true){
            if (!hasDictionary){
                System.out.println("Please Enter the Method of Perfect Hashing: ");
                System.out.println("[1] Linear Space");
                System.out.println("[2] Quadratic Space");
                int typeOfHash;
                typeOfHash = scanner.nextInt();
                while (typeOfHash != 1 && typeOfHash != 2 ){
                    System.err.println("You Must Enter Either 1 or 2.");
                    typeOfHash = scanner.nextInt();
                }
                // Construct the dictionary based on the hashing method
                hasDictionary = true;
                dictionary = new Dictionary<>(typeOfHash);
            }
            else{
                System.out.println("Operations to be Applied : ");
                System.out.println("[1] Insert a String");
                System.out.println("[2] Delete a String");
                System.out.println("[3] Search for a String");
                System.out.println("[4] Insert a list.txt of Strings (Batch Insert)");
                System.out.println("[5] Delete a list.txt of Strings (Batch Delete)");
                System.out.println("[6] Exit");
                int operation = scanner.nextInt();
                while (operation <= 0 || operation > 6){
                    System.err.println("You Must Enter a Number between 1 and 6.");
                    operation = scanner.nextInt();
                }

                String pathOrElement;
                String[] words;
                boolean result;
                switch (operation){
                    case 1:
                        System.out.println("Enter String");
                        pathOrElement = scanner.next();
                        if (!isNumeric(pathOrElement))
                            pathOrElement = pathOrElement.toLowerCase();
                        result = dictionary.insert(pathOrElement);
                        System.out.println(pathOrElement + (result ? msgList[0] : msgList[1]));
                        break;
                    case 2:
                        System.out.println("Enter String");
                        pathOrElement = scanner.next();
                        if (!isNumeric(pathOrElement))
                            pathOrElement = pathOrElement.toLowerCase();
                        result = dictionary.delete(pathOrElement);
                        System.out.println(pathOrElement + (result ? msgList[4] : msgList[2]));
                        break;
                    case 3:
                        System.out.println("Enter String");
                        pathOrElement = scanner.next();
                        if (!isNumeric(pathOrElement))
                            pathOrElement = pathOrElement.toLowerCase();
                        result = dictionary.search(pathOrElement);
                        System.out.println(pathOrElement + (result ? msgList[3] : msgList[2]));
                        break;
                    case 4:
                        words = readFile();
                        lower(words);
                        // Insert
                        dictionary.batchInsert(words);
                        break;
                    case 5:
                        words = readFile();
                        lower(words);
                        // Delete
                        dictionary.batchDelete(words);
                        break;
                    case 6:
                        System.exit(0);
                }
            }
            System.out.println("************************************************");
        }
    }
}
