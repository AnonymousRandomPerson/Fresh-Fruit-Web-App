import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean (name = "ui")
@SessionScoped
public class UI {
    
    @ManagedProperty("#{userManager}")
    protected UserManager userManager;
    
    public UI() {
    }
    
    /**
     * Sets the user manager.
     * @param um the user manager
     */
    public void setUserManager(UserManager um) {
        userManager = um;
    }
}