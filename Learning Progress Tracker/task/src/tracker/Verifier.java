package tracker;

public class Verifier {

    public  boolean verifyName(String name) {
        String nameRegex = "^[A-Za-z]+[-']?[A-Za-z]+";
        return name.matches(nameRegex);
    }

    public  boolean verifyLastName(String lastName) {
        String lastNameRegex = "[A-Za-z ]+([-' ][A-Za-z ])*[-' ]?[A-Za-z]+";
        return lastName.matches(lastNameRegex);
    }

    public boolean verifyEmail(String email) {
        String pattern = "[\\w.]+@\\w+\\.[a-z\\d]+";
        return email.matches(pattern);
    }
}
