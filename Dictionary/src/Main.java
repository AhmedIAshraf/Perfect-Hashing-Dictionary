import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        boolean hasDictionary = false;

        while(true){
            Scanner scanner = new Scanner(System.in);
            if (!hasDictionary){
                System.out.println("Please Enter the Method of Perfect Hashing: ");
                System.out.println("[1] Linear Space");
                System.out.println("[2] Quadratic Space");
                int typeOfHash;
                typeOfHash = scanner.nextInt();
                while (typeOfHash != 1 && typeOfHash != 2 ){
                    System.out.println("You Must Enter Either 1 or 2.");
                    typeOfHash = scanner.nextInt();
                }
                // Construct the hash object
                hasDictionary = true;
            }
            else{
                System.out.println("Operations to be Applied : ");
                System.out.println("[1] Insert a String");
                System.out.println("[2] Delete a String");
                System.out.println("[3] Search for a String");
                System.out.println("[4] Insert a list of Strings (Batch Insert)");
                System.out.println("[5] Delete a list of Strings (Batch Delete)");
                int operation = scanner.nextInt();
                while (operation<=0 || operation>=5 ){
                    System.out.println("You Must Enter a Number between 1 and 5.");
                    operation = scanner.nextInt();
                }

            }
        }
    }
}