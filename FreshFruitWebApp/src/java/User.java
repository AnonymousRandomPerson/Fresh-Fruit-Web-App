import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;



@ManagedBean
@SessionScoped
public class User implements Serializable {
    
    @ManagedProperty("#{userManager}")
    protected UserManager userManager;
    
    protected String username;
    protected String password;
    protected String email;
    protected int numTries;
    
    /**
     * Creates a new instance of User.
     * @param username the user's username
     * @param password the user's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        numTries = 0;
    }
    
    public String getUsername() { 
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setUsername(String u) {
        username = u;
    }
    
    public void setPassword(String p) {
        password = p;
    }
    
    public void setEmail(String e) {
        email = e;
    }
    /**
     * Checks the login information.
     * @param p the attempted password
     * @return true if the password is correct
     */
    public boolean checkLogin(String p) {
        if (p.equals(password)) {
            return true;
        } else {
            numTries++;
            return false;
        }
    }
    /**
     * Checks if the account is locked.
     * @return true if the account is locked
     */
    public boolean isLocked() {
        return numTries >= UserManager.LIMITTRIES;
    }
    /**
     * Sets the user manager.
     * @param um the user manager
     */
    public void setUserManager(UserManager um) {
        userManager = um;
    }
}
