package tracker;

import java.util.Arrays;
import java.util.List;

public class Verifier {

    public boolean verifyName(String name) {
        String nameRegex = "^[A-Za-z]+[-']?[A-Za-z]+";
        return name.matches(nameRegex);
    }

    public boolean verifyLastName(String lastName) {
        String lastNameRegex = "[A-Za-z ]+([-' ][A-Za-z ])*[-' ]?[A-Za-z]+";
        return lastName.matches(lastNameRegex);
    }

    public boolean verifyEmail(String email) {
        String pattern = "[\\w.]+@\\w+\\.[a-z\\d]+";
        return email.matches(pattern);
    }

    public boolean validatePoints(String[] points) {
        List<String> list = Arrays.asList(points);
        return list.stream().allMatch(s -> s.matches("\\d+"))
                && list.stream().map(Integer::parseInt).allMatch(s -> s > 0);
    }
}
