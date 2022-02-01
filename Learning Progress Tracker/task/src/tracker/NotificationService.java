package tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationService {

    private Map<Student, Courses> studentCoursesMap = Main.getStudentsCourses();
    private Map<Student, List<String>> alreadyNotified = new HashMap<>();
    private Map<Student, List<String>> toNotify;

    public void notifyStudent() {
        toNotify = getCompletedCoursesByStudent();
            checkNotificationStatus();
            sendNotification();
    }

    public Map<Student, List<String>> getCompletedCoursesByStudent() {
        toNotify = new HashMap<>();
        for (var e : studentCoursesMap.entrySet()) {
            Student student = e.getKey();
            List<String> completed = new ArrayList<>();
            if (e.getValue().getJava() >= CompletionPoints.JAVA.getValue()) {
                completed.add("java");
            }
            if (e.getValue().getDSA() >= CompletionPoints.DSA.getValue()) {
                completed.add("dsa");
            }
            if (e.getValue().getDatabases() >= CompletionPoints.DATABASES.getValue()) {
                completed.add("databases");
            }
            if (e.getValue().getSpring() >= CompletionPoints.SPRING.getValue()) {
                completed.add("spring");
            }
            if (!completed.isEmpty()) {
                toNotify.put(student, completed);
            }
        }
        return toNotify;
    }

    public void checkNotificationStatus() {
        if(toNotify != null) {
            for (var student : toNotify.keySet()) {
                if (alreadyNotified.containsKey(student)) {
                    toNotify.get(student).removeAll(alreadyNotified.get(student));
                    if (toNotify.get(student).isEmpty()) {
                        toNotify.remove(student);
                    }
                }
            }
        }
    }

    public void sendNotification() {
        if (toNotify != null) {
            for (var e : toNotify.entrySet()) {
                Student student = e.getKey();
                String fullName = student.getName() + " " + student.getLastname();
                alreadyNotified.put(student, e.getValue());
                for (String course : toNotify.get(student)) {
                    System.out.println("To: " + student.getEmail() +
                            "\nRe: Your Learning Progress" +
                            "\nHello, " + fullName + "! You have accomplished our " + course + " course!");
                }
            }
            System.out.printf("Total %d students have been notified.\n", toNotify.size());
            toNotify.clear();
        } else {
            System.out.println("Total 0 students have been notified.");
        }
    }
}
