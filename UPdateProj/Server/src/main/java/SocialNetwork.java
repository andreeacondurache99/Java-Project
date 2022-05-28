import java.util.ArrayList;
import java.util.List;

public class SocialNetwork {

    private static final List<User> usersList = new ArrayList<>();

    public static boolean addUser(User username) {
        if (!existsUser(username)) {
            usersList.add(username);
            return true; //a fost adaugat
        }

        return false;

    }

    //return true if the user already exists
    public static boolean existsUser(User username) {

        for (User user : usersList) {
            if (username.getUsername().equals(user.getUsername()))
                return true;
        }
        return false;
    }
}
