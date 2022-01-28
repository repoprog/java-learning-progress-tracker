package tracker;

public class Student {
    private static long counter;
    private final String id;
    private String name;
    private String lastname;
    private String email;

    public Student(String name, String lastname, String email) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        id = String.valueOf(counter++);
    }

    static {
        counter = 10000;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return id;
    }
}
