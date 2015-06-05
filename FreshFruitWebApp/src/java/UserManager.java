package FreshFruitWebApp.src.java;

import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

@ManagedBean (name = "userManager")
@ApplicationScoped
public class UserManager {
    
    private Map<String, UserData> users = new HashMap<>();

    /**
     * Creates a new instance of UserManager
     */
    public UserManager() {
        makeSomeUsers();
    }

    private void makeSomeUsers() {
        makeUser("user", "pass");
    }
    
    public void makeUser(String user, String pass) {
        users.put(user, new UserData(user, pass));
    }

    UserData find(String username) {
       return users.get(username);
    }
    
}
