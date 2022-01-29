package tracker;

import java.util.*;
import java.util.stream.Collectors;


public class Statistics {

    Map<Student, Courses> coursesMap = Main.getStudentsCourses();
    Scanner scanner = new Scanner(System.in);

    public void chooseStats() {
        System.out.println("Type the name of a course to see details or 'back' to quit");
        printCoursesInfo();
        boolean quit = false;
        while (!quit) {
            String course = scanner.nextLine().trim();
            if ("back".equalsIgnoreCase(course)) {
                quit = true;
            } else {
                printCourseStatistics(course);
            }
        }
    }

    public void printCourseStatistics(String course) {
        System.out.println(course);
        System.out.println("id    points    completed");
    }

    public void printCoursesInfo() {
        System.out.println(
                "Most popular: " + mostPopular() +
                        "\nLeast popular: " + leastPopular() +
                        "\nHighest activity: Java" +
                        "\nLowest activity: DSA" +
                        "\nEasiest course: Java" +
                        "\nHardest course: Spring");
    }


    public List<CourseInfo> getCoursesInfoList() {
        int java = 0, dsa = 0, database = 0, spring = 0;
        for (Courses c : coursesMap.values()) {
            java += c.getJava();
            dsa += c.getDSA();
            database += c.getDatabases();
            spring += c.getSpring();
        }
        List<CourseInfo> infoList = new ArrayList<>(List.of(new CourseInfo("Java", java),
                new CourseInfo("DSA", dsa),
                new CourseInfo(" Database", database),
                new CourseInfo("Spring", spring)));

        infoList.forEach(System.out::println);
        return infoList;
    }

    public String mostPopular() {
        List<CourseInfo> infoList = getCoursesInfoList();
        int max = infoList.stream()
                .mapToInt(CourseInfo::getParticipants)
                .max()
                .orElse(-1);

        return infoList.stream()
                .filter(c -> c.getParticipants() == max)
                .map(CourseInfo::getName)
                .collect(Collectors.joining(", "));
    }

    public String leastPopular() {
        List<CourseInfo> infoList = getCoursesInfoList();
        int min = infoList.stream()
                .mapToInt(CourseInfo::getParticipants)
                .min()
                .orElse(-1);

      return infoList.stream()
                .filter(c -> c.getParticipants() == min)
                .map(CourseInfo::getName)
                .collect(Collectors.joining(", "));
    }

//    public List<Courses> highestActivity() {
//
//    }
//
//    public List<Courses> lowestActivity() {
//
//    }
//
//    public List<Courses> easiest() {
//
//    }
//
//    public List<Courses> hardest() {
//
//    }

}

class CourseInfo {
    private String name;
    private int participants;

    public CourseInfo(String name, int participants) {
        this.name = name;
        this.participants = participants;
    }

    public int getParticipants() {
        return participants;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CourseInfo{" +
                "name='" + name + '\'' +
                ", participants=" + participants +
                '}';
    }
}


