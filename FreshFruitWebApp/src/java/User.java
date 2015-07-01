import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;



@ManagedBean
@SessionScoped
/**
 * A generic user. Can be either a StudentUser or an AdminUser.
 */
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
    
    /**
     * Returns the user's username.
     * @return the user's username
     */
    public String getUsername() { 
        return username;
    }
    
    /**
     * Returns the user's password.
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Returns the user's email address.
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Sets the user's username.
     * @param u the new username
     */
    public void setUsername(String u) {
        username = u;
    }
    
    /**
     * Sets the user's password.
     * @param p the new password
     */
    public void setPassword(String p) {
        password = p;
    }
    
    /**
     * Sets the user's email address.
     * @param e the new email address
     */
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
