import java.util.ArrayList;
import java.util.List;

public class User {

    private final String username;
    private final List<User> friendsList = new ArrayList<>();
    //the messages from friends
    private final List<String> messages = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


    public void addFriends(List<User> friends) {
        for (User friend : friends) {
            if (!friendsList.contains(friend))
                friendsList.add(friend);
        }
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<User> getFriendsList() {
        return friendsList;
    }

    public List<String> getMessages() {
        return messages;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + username + '\'' +
                '}';
    }
}
