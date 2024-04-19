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
                System.out.println("[6] Exit");
                int operation = scanner.nextInt();
                while (operation<=0 || operation>6 ){
                    System.out.println("You Must Enter a Number between 1 and 6.");
                    operation = scanner.nextInt();
                }

                String pathOrElement;
                switch (operation){
                    case 1:
                    case 2:
                    case 3:
                        System.out.println("Enter String");
                        pathOrElement = scanner.next();
                        //Add || Delete || Search
                        break;
                    case 4:
                    case 5:
                        System.out.println("Enter the File Path");
                        pathOrElement = scanner.next();
                        //Read the file and Parse it
                        break;
                    case 6:
                        System.exit(0);
                }
            }
        }
        

    }
}
