package tracker;

import java.util.*;


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
                "Most popular: \n" +
                        "Least popular: DSA\n" +
                        "Highest activity: Java\n" +
                        "Lowest activity: DSA\n" +
                        "Easiest course: Java\n" +
                        "Hardest course: Spring");
    }


    public String mostPopular() {
        int java = 0, dsa = 0, database = 0, spring = 0;
        for (Courses c : coursesMap.values()) {
            java += c.getJava();
            dsa += c.getDSA();
            database += c.getDatabases();
            spring += c.getSpring();
        }
        List<CourseInfo> infoList = new ArrayList<>(List.of(new CourseInfo("java", java),
                new CourseInfo("dsa", dsa),
                new CourseInfo(" database", database),
                new CourseInfo("spring", spring)));

        infoList.stream().max(Comparator.comparingInt(CourseInfo::getParticipants));
        return "";
    }


//    public List<Courses> leastPopular() {
//
//    }
//
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

    public void setName(String name) {
        this.name = name;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }
}


