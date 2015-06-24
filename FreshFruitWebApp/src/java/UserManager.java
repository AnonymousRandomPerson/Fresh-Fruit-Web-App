import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

@ManagedBean (name = "userManager")
@ApplicationScoped
public class UserManager {
    
    private Map<String, User> userList = new HashMap<>();
    public static final int LIMITTRIES = 3;
    
    private User currentUser;
    
    /**
     * Creates a new instance of UserManager.
     */
    public UserManager() {
    }
    
    /**
     * Returns the current user of the application.
     * @return the user of the application.
     */
    public User getUser() {
        return currentUser;
    }
    
    /**
     * Sets the current application user.
     * @param user the new user
     */
    public void setUser(User user) {
        currentUser = user;
    }

    /**
     * Makes a new user and puts it in the map.
     * @param user the new user's username
     * @param pass the new user's password
     * @return the new user
     */
    public User makeUser(String user, String pass) {
        User newUser = new StudentUser(user, pass);
        newUser.setUserManager(this);
        userList.put(user, newUser);
        return newUser;
    }
    
    /**
     * Changes a user's username in the hash map.
     * @param oldName the user's old name
     * @param newName the user's new name
     */
    public void changeUsername(String oldName, String newName) {
        User user = find(oldName);
        userList.put(newName, user);
        userList.remove(oldName);
    }
    
    /**
     * Finds a user in the map.
     * @param username the user to find
     * @return the User object, or null if the user does not exist
     */
    public User find(String username) {
       return userList.get(username);
    }
}
