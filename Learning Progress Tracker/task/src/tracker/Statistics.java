package tracker;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class Statistics {

    private final Map<Student, Courses> studentResultsMap = Main.getStudentsCourses();
    private final List<Courses> taskPointsList = Courses.getTaskPoints();
    private List<CourseInfo> activeParticipants;
    private List<String> mostPopularCurses;
    private List<String> mostActive;
    private List<CourseInfo> taskCompletion;
    private List<CourseInfo> averageDifficulty;


    Scanner scanner = new Scanner(System.in);

    public void chooseStats() {
        activeParticipants = getCoursesInfoList(new ArrayList<>(studentResultsMap.values()));
        taskCompletion = getCoursesInfoList(taskPointsList);
        averageDifficulty = getAverageDifficulty();
        System.out.println("Type the name of a course to see details or 'back' to quit");
        printCoursesInfo();
        boolean quit = false;
        List<String> choices = new ArrayList<>(List.of("JAVA", "DSA", "DATABASES", "SPRING"));
        while (!quit) {
            String course = scanner.nextLine().trim().toUpperCase();
            if ("BACK".equals(course)) {
                quit = true;
            } else if (!choices.contains(course)) {
                System.out.println("Unknown course.");
            } else {
                printCourseStatistics(course);
            }
        }
    }

    public void printCourseStatistics(String course) {
        Map<String, Integer> statsSorted = generateStatistics(course);
        System.out.println(course);
        System.out.println("id    points    completed");
        statsSorted.forEach((k, v) -> System.out.printf("%s %d        %.1f%%\n",
                k, v, calculateCompletion(v, course)));
    }

    public void printCoursesInfo() {
        String most = mostPopular().isEmpty() ? "n/a" : mostPopular();
        String least = leastPopular().isEmpty() ? "n/a" : leastPopular();
        String highest = highestActivity().isEmpty() ? "n/a" : highestActivity();
        String lowest = lowestActivity().isEmpty() ? "n/a" : highestActivity();
        String easiest = easiest().isEmpty() ? "n/a" : easiest();
        String hardest = hardest().isEmpty() ? "n/a" : hardest();
        System.out.println("Most popular: " + most +
                "\nLeast popular: " + least +
                "\nHighest activity: " + highest +
                "\nLowest activity: " + lowest +
                "\nEasiest course:" + easiest +
                "\nHardest course:" + hardest);
    }


    public List<CourseInfo> getCoursesInfoList(List<Courses> courses) {
        int java = 0, dsa = 0, database = 0, spring = 0;
        for (Courses c : courses) {
            if (c.getJava() != 0) java += 1;
            if (c.getDSA() != 0) dsa += 1;
            if (c.getDatabases() != 0) database += 1;
            if (c.getSpring() != 0) spring += 1;
        }

        return new ArrayList<>(List.of(
                new CourseInfo("Java", java),
                new CourseInfo("DSA", dsa),
                new CourseInfo("Databases", database),
                new CourseInfo("Spring", spring)));
    }

    public String mostPopular() {
        mostPopularCurses = getBestCurses(activeParticipants);
        return String.join(", ", mostPopularCurses);
    }

    public String leastPopular() {
        List<String> leastPopularCourses = getWorstCourses(activeParticipants);
        leastPopularCourses.removeAll(mostPopularCurses);
        return String.join(", ", leastPopularCourses);
    }

    public String highestActivity() {
        mostActive = getBestCurses(taskCompletion);
        return String.join(", ", mostActive);
    }

    public String lowestActivity() {
        List<String> lowActivity = getWorstCourses(taskCompletion);
        lowActivity.removeAll(mostActive);
        return String.join(", ", lowActivity);
    }

    public String easiest() {
        return String.join(", ", getBestCurses(averageDifficulty));
    }

    public String hardest() {
        return String.join(", ", getWorstCourses(averageDifficulty));
    }

    public List<String> getBestCurses(List<CourseInfo> list) {
        double max = list.stream()
                .mapToDouble(CourseInfo::getPoints)
                .max()
                .orElse(0);

        return list.stream()
                .filter(c -> c.getPoints() == max & c.getPoints() != 0)
                .map(CourseInfo::getName)
                .collect(Collectors.toList());
    }

    public List<String> getWorstCourses(List<CourseInfo> list) {
        double min = list.stream()
                .mapToDouble(CourseInfo::getPoints)
                .min()
                .orElse(0);

        return list.stream()
                .filter(c -> c.getPoints() == min & c.getPoints() != 0)
                .map(CourseInfo::getName)
                .collect(Collectors.toList());
    }

    public List<CourseInfo> getAverageDifficulty() {
        double java = taskPointsList.stream()
                .filter(points -> points.getJava() > 0)
                .mapToInt(Courses::getJava)
                .average()
                .orElse(0);

        double dsa = taskPointsList.stream()
                .filter(points -> points.getDSA() > 0)
                .mapToInt(Courses::getDSA)
                .average()
                .orElse(0);

        double database = taskPointsList.stream()
                .filter(points -> points.getDatabases() > 0)
                .mapToInt(Courses::getDatabases)
                .average()
                .orElse(0);

        double spring = taskPointsList.stream()
                .filter(points -> points.getSpring() > 0)
                .mapToInt(Courses::getSpring)
                .average()
                .orElse(0);

        return new ArrayList<>(List.of(
                new CourseInfo("Java", java),
                new CourseInfo("DSA", dsa),
                new CourseInfo("Database", database),
                new CourseInfo("Spring", spring)));
    }

    public Map<String, Integer> generateStatistics(String course) {
        Map<String, Integer> courseResults = switch (course) {
            case "JAVA" -> studentResultsMap.entrySet().stream()
                    .filter(e -> e.getValue().getJava() > 0)
                    .collect(Collectors.toMap(e -> e.getKey().getId(), e -> e.getValue().getJava()));
            case "DSA" -> studentResultsMap.entrySet().stream()
                    .filter(e -> e.getValue().getDSA() > 0)
                    .collect(Collectors.toMap(e -> e.getKey().getId(), e -> e.getValue().getDSA()));
            case "DATABASES" -> studentResultsMap.entrySet().stream()
                    .filter(e -> e.getValue().getDatabases() > 0)
                    .collect(Collectors.toMap(e -> e.getKey().getId(), e -> e.getValue().getDatabases()));
            case "SPRING" -> studentResultsMap.entrySet().stream()
                    .filter(e -> e.getValue().getSpring() > 0)
                    .collect(Collectors.toMap(e -> e.getKey().getId(), e -> e.getValue().getSpring()));
            default -> new HashMap<>();
        };

        return courseResults.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }

    public double calculateCompletion(int points, String course) {
        return points * 100 / (double) CompletionPoints.valueOf(course).getValue();
    }
}

enum CompletionPoints {
    JAVA(600),
    DSA(400),
    DATABASES(480),
    SPRING(550);

    public final int value;

    CompletionPoints(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

class CourseInfo {
    private final String name;
    private final double points;

    public CourseInfo(String name, double points) {
        this.name = name;
        this.points = points;
    }

    public double getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }
}


