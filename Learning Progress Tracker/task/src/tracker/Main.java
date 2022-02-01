package tracker;

import com.sun.source.tree.NewArrayTree;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Statistics stats = new Statistics();
        NotificationService service = new NotificationService();
        System.out.println("Learning Progress Tracker");
        boolean quit = false;
        while (!quit) {
            String action = scanner.nextLine().toLowerCase().trim();
            switch (action) {
                case "add students" -> showSubMenu(action, "student credentials");
                case "add points" -> showSubMenu(action, "an id and points");
                case "find" -> showSubMenu(action, "an id");
                case "statistics" -> stats.chooseStats();
                case "" -> System.out.println("No input.");
                case "back" -> System.out.println("Enter 'exit' to exit the program");
                case "list" -> showStudentsList();
                case "notify" -> service.notifyStudent();
                case "exit" -> {
                    quit = true;
                    System.out.println("Bye!");
                }
                default -> System.out.println("Unknown command!");
            }
        }
    }

    public static void showSubMenu(String action, String msg) {
        boolean quit = false;
        System.out.println("Enter " + msg + " or 'back' to return:");
        int students = 0;
        while (!quit) {
            String input = scanner.nextLine().trim();
            if ("back".equalsIgnoreCase(input)) {
                quit = true;
            } else {
                switch (action) {
                    case "add students" -> students += addStudent(input);
                    case "add points" -> addPoints(input);
                    case "find" -> findStudent(input);
                    default -> throw new IllegalStateException("Unexpected value");
                }
            }
        }
        if ("add students".equals(action)) {
            System.out.printf("Total %d students have been added.\n", students);
        }
    }

    static Scanner scanner = new Scanner(System.in);
    static Map<Student, Courses> studentsCourses = new HashMap<>();

    public static Map<Student, Courses> getStudentsCourses() {
        return studentsCourses;
    }

    public static int addStudent(String student) {
        if (isPersonalDataValid(student)) {
            System.out.println("The student has benn added.");
            return 1;
        }
        return 0;
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
        studentsCourses.put(new Student(name, lastname, email), new Courses(0, 0, 0, 0));
        return true;
    }

    public static boolean isEmailUnique(String email) {
        return studentsCourses.keySet().stream()
                .noneMatch(student -> student.getEmail().equals(email));
    }

    public static void showStudentsList() {
        if (studentsCourses.size() == 0) {
            System.out.println("No students found");
        } else {
            System.out.println("Students: ");
            studentsCourses.keySet().forEach(System.out::println);
        }
    }

    public static void addPoints(String inputPoints) {
        if (isPointsValid(inputPoints)) {
            System.out.println("Points updated.");
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
        List<Integer> parsed = Arrays.stream(points).map(Integer::parseInt).toList();
        Courses courses = studentsCourses.get(studentById);
        courses.updatePoints(parsed.get(0), parsed.get(1), parsed.get(2), parsed.get(3));
        studentsCourses.put(studentById, courses);
        return true;
    }

    public static void findStudent(String studentId) {
        Student studentById = findStudentById(studentId);
        Courses courses = studentsCourses.get(studentById);
        System.out.println(studentById == null ? "No student is found for id=" + studentId
                : studentId + courses);
    }

    public static Student findStudentById(String id) {
        return studentsCourses.keySet().stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
