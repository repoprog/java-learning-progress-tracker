package tracker;

import java.util.*;
import java.util.stream.Collectors;

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
                case "list":
                    showStudentsList();
                    break;
                case "add points":
                    addPoints();
                    break;
                case "find":
                    findStudent();
                default:
                    System.out.println("Unknown command!");
            }
        }
    }

//    public static void showSubMenu(String info) {
//
//    }

    static Scanner scanner = new Scanner(System.in);
    static Map<Student, Courses> studentResultsMap = new HashMap<>();


    public static void addStudent() {
        System.out.println("Enter student credentials or 'back' to return:");
        boolean quit = false;
        int students = 0;
        while (!quit) {
            String studentInput = scanner.nextLine().trim();
            if ("back".equalsIgnoreCase(studentInput)) {
                quit = true;
            } else {
                if (isPersonalDataValid(studentInput)) {
                    System.out.println("The student has benn added.");
                    students++;
                }
            }
        }
        System.out.printf("Total %d students have been added.\n", students);
    }

    public static boolean isPersonalDataValid(String student) {
        Verifier verifier = new Verifier();
        String[] nameParts = student.split(" ");

        if (nameParts.length < 3) {
            System.out.println("Incorrect credentials.");
            return false;
        }

        String name = nameParts[0];
        String lastname = String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length - 1));
        String email = nameParts[nameParts.length - 1];

        if (!verifier.verifyName(name)) {
            System.out.println("Incorrect first name.");
            return false;
        } else if (!verifier.verifyLastName(lastname)) {
            System.out.println("Incorrect last name.");
            return false;
        } else if (!verifier.verifyEmail(email)) {
            System.out.println("Incorrect email.");
            return false;
        }
        if (!isEmailUnique(email)) {
            System.out.println("This email is already taken.");
            return false;
        }
        studentResultsMap.put(new Student(name, lastname, email), new Courses(0, 0, 0, 0));
        return true;
    }

    public static boolean isEmailUnique(String email) {
        return studentResultsMap.keySet().stream()
                .noneMatch(student -> student.getEmail().equals(email));
    }

    public static void showStudentsList() {
        if (studentResultsMap.size() == 0) {
            System.out.println("No students found");
        } else {
            System.out.println("Students: ");
           studentResultsMap.keySet().forEach(System.out::println);
        }
    }

    public static void addPoints() {
        System.out.println("Enter an id and points or 'back' to return:");
        boolean quit = false;
        while (!quit) {
            String inputPoints = scanner.nextLine().trim();
            if ("back".equalsIgnoreCase(inputPoints)) {
                quit = true;
            } else {
                if (isPointsValid(inputPoints)) {
                    System.out.println("Points updated.");
                }
            }
        }
    }

    public static boolean isPointsValid(String inputPoints) {
        Verifier verifier = new Verifier();
        String[] pointsData = inputPoints.split(" ");
        String studentId = pointsData[0];
        if (pointsData.length != 5) {
            System.out.println("Incorrect points format.");
            return false;
        }
        String[] points = Arrays.copyOfRange(pointsData, 1, pointsData.length);
        Student studentById = findStudentById(studentId);

        if (!verifier.validatePoints(points)) {
            System.out.println("Incorrect points format.");
            return false;
        } else if (studentById == null) {
            System.out.printf("No student is found for id=%s\n", studentId);
            return false;
        }
        List<Integer> parsed = Arrays.stream(points).map(String::valueOf)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        Courses courses = studentResultsMap.get(studentById);
        courses.updatePoints(parsed.get(0), parsed.get(1), parsed.get(2), parsed.get(3));
        studentResultsMap.put(studentById, courses);
        return true;
    }

    public static void findStudent() {
        System.out.println("Enter an id or 'back' to return:");
        boolean quit = false;
        while (!quit) {
            String studentId = scanner.nextLine().trim();
            if ("back".equalsIgnoreCase(studentId)) {
                quit = true;
            } else {
                Student studentById = findStudentById(studentId);
                Courses courses = studentResultsMap.get(studentById);
                System.out.println(studentById == null ? "No student is found for id=" + studentId
                        : studentId + courses);
            }
        }
    }

    public static Student findStudentById(String id) {
        return studentResultsMap.keySet().stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
