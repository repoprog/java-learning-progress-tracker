package tracker;

public class Courses {

    private int java;
    private int DSA;
    private int databases;
    private int spring;

    public Courses(int java, int DSA, int databases, int spring) {
        this.java = java;
        this.DSA = DSA;
        this.databases = databases;
        this.spring = spring;
    }

    public int getJava() {
        return java;
    }

    public int getDSA() {
        return DSA;
    }

    public int getDatabases() {
        return databases;
    }

    public int getSpring() {
        return spring;
    }

    public void updatePoints(int java, int dsa, int db, int spring) {
        this.java += java;
        this.DSA += dsa;
        this.databases += db;
        this.spring += spring;
    }

    @Override
    public String toString() {
        return " points: " +
                "Java=" + java +
                "; DSA=" + DSA +
                "; Databases=" + databases +
                "; Spring=" + spring;
    }
}

enum CompletionPoints {
    JAVA(600),
    DSA(400),
    DATABASE(480),
    SPRING(550);

    CompletionPoints(int points) {
    }
}
