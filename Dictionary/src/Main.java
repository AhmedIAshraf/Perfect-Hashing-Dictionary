import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> msgList = new ArrayList<>(List.of(" inserted successfully", " already exists",
            " doesn't exist", " found", " deleted successfully"));

    private static String[] readFile() throws FileNotFoundException {
        File file;
        do {
            System.out.println("Enter the File Path");
            String path = scanner.nextLine();
            file = new File(path);
            if (!file.exists())
                System.err.println("File not found!");
        } while (!file.exists());
        Scanner fileScanner = new Scanner(file);
        return fileScanner.nextLine().split(", ");
    }

    public static void main(String[] args) throws FileNotFoundException {

        boolean hasDictionary = false;
        Dictionary dictionary = new Dictionary();
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
            }
            else{
                System.out.println("Operations to be Applied : ");
                System.out.println("[1] Insert a String");
                System.out.println("[2] Delete a String");
                System.out.println("[3] Search for a String");
                System.out.println("[4] Insert a list of Strings (Batch Insert)");
                System.out.println("[5] Delete a list of Strings (Batch Delete)");
                System.out.println("[7] Exit");
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
                        result = dictionary.insert(pathOrElement);
                        System.out.println(pathOrElement + (result ? msgList.get(0) : msgList.get(1)));
                        break;
                    case 2:
                        System.out.println("Enter String");
                        pathOrElement = scanner.next();
                        result = dictionary.delete(pathOrElement);
                        System.out.println(pathOrElement + (result ? msgList.get(4) : msgList.get(2)));
                        break;
                    case 3:
                        System.out.println("Enter String");
                        pathOrElement = scanner.next();
                        result = dictionary.search(pathOrElement);
                        System.out.println(pathOrElement + (result ? msgList.get(3) : msgList.get(2)));
                        break;
                    case 4:
                        words = readFile();
                        for (String word : words) {
                            System.out.println(word);
                        }
                        // Insert
                        dictionary.batchInsert(words);
                        break;
                    case 5:
                        words = readFile();
                        for (String word : words) {
                            System.out.println(word);
                        }
                        // Delete
                        dictionary.batchDelete(words);
                        break;
                    case 6:
                        System.exit(0);
                }
            }
        }
        

    }
}
