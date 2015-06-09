import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class User implements Serializable {
    private String username;
    private String password;
    private String email;
    private int numTries;
    
    @ManagedProperty("#{userManager}")
    private UserManager userManager;
    
    /**
     * Creates a new instance of User
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
    
    public boolean checkLogin(String p) {
        if (p.equals(password)) {
            return true;
        } else {
            numTries++;
            return false;
        }
    }
    
    public boolean isLocked() {
        return numTries >= UserManager.LIMITTRIES;
    }
    
    public void setUserManager(UserManager um) {
        userManager = um;
    }

    public void editProfile() {
        
    }
}
