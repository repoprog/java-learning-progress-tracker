package tracker;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Learning Progress Tracker");
        boolean quit = false;
        while (!quit) {
            String input = scanner.nextLine().toLowerCase().trim();
            switch (input) {
                case "add students":
                    addStudent();
                    break;
                case "exit":
                    quit = true;
                    System.out.println("Bye!");
                    break;
                case "":
                    System.out.println("No input.");
                    break;
                case "back":
                    System.out.println("Enter 'exit' to exit the program");
                    break;
                default:
                    System.out.println("Unknown command!");
            }
        }
    }

    public static void addStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student credentials or 'back' to return.");
        boolean quit = false;
        int students = 0;

        while (!quit) {
            String student = scanner.nextLine().trim();
            if ("back".equalsIgnoreCase(student)) {
                quit = true;
            } else {
                if (isPersonalDataValid(student)) {
                    System.out.println("The student has benn added");
//                    System.out.print(student);
                    students++;
                }
            }
        }
        System.out.printf("Total %d students have benn added.", students);
    }

    public static boolean isPersonalDataValid(String student) {
        Verifier verifier = new Verifier();
        String[] nameParts = student.split(" ");
        String name = nameParts[0];
        String lastName;
        String email;

        if (nameParts.length < 3) {
            System.out.println("Incorrect credentials.");
            return false;
        } else {
            lastName = String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length - 1));
            email = nameParts[nameParts.length - 1];
        }
//        System.out.printf("name: %s last name: %s e-mail: %s", name, lastName, email);
        if (!verifier.verifyName(name)) {
            System.out.println("Incorrect first name.");
            return false;
        } else if (!verifier.verifyLastName(lastName)) {
            System.out.println("Incorrect last name.");
            return false;
        } else if (!verifier.verifyEmail(email)) {
            System.out.println("Incorrect email.");
            return false;
        }
        return true;
    }

}
