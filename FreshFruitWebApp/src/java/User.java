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
    protected Status status;
    protected int numTries;
    
    protected static final String host = "jdbc:derby://localhost:1527/fruit";
    protected static final String uName = "team11";
    protected static final String uPass= "fruit";
    
    /**
    * Status of the user
    */
    public enum Status {
        Normal, Locked, Banned, Admin
    }
    
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
     * Gets the status of the user.
     * @return the status of the user
     */
    public Status getStatus() {
        return status;
    }
    
    /**
     * Gets the status of the user as a string.
     * @return a string representing the user's status
     */
    public String getStatusString() {
        if (status != null) {
            return status.toString();
        } else {
            return "";
        }
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
     * Sets the status.
     * @param status the new status
     */
    public void setStatus(Status status) {
        this.status = status;
    }
    
    /**
     * Sets the user's status and updates it in the database.
     * @param status The new status
     */
    public void setStatusString(String status) {
        this.status = Status.valueOf(status);
        UserManager.updateSQL("UPDATE USERS SET STATUS = \'" + status + "\' WHERE USERNAME = \'" + username + "\'");
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
            if (numTries >= UserManager.LIMITTRIES && this instanceof StudentUser) {
                setStatus(Status.Locked);
            }
            return false;
        }
    }
    
    /**
     * Checks if the account is locked.
     * @return true if the account is locked
     */
    public boolean isLocked() {
        return getStatus() == Status.Locked;
    }
    
    /**
     * Checks if the account is banned.
     * @return true if the account is banned
     */
    public boolean isBanned() {
        return getStatus() == Status.Banned;
    }
    
    /**
     * Sets the user manager.
     * @param um the user manager
     */
    public void setUserManager(UserManager um) {
        userManager = um;
    }
}
