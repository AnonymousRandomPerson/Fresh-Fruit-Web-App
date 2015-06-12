import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

@ManagedBean (name = "userManager")
@ApplicationScoped
public class UserManager {
    
private Map<String, User> userList = new HashMap<>();
    public static final int LIMITTRIES = 3;

    /**
     * Creates a new instance of UserManager
     */
    public UserManager() {
    }
    

    /**
     * make a new user and put it in the map
     * @param user
     * @param pass 
     */
    public void makeUser(String user, String pass) {
        userList.put(user, new User(user, pass));
    }
    /**
     * find a user in the map
     * @param username
     * @return the user object
     */
    public User find(String username) {
       return userList.get(username);
    }
}
