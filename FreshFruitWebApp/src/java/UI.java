import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean (name = "ui")
@SessionScoped
public class UI {
    
    @ManagedProperty("#{userManager}")
    private UserManager userManager;
    
    private String username;
    private String password;
    
    public UI() {
    }
    
    public String getUsername() { 
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setUsername(String u) {
        username = u;
    }
    
    public void setPassword(String p) {
        password = p;
    }
    
    public String register() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (username.equals("")) {
            context.addMessage(null, new FacesMessage("No username entered."));
            return null;
        } else if (userManager.find(username) != null) {
            context.addMessage(null, new FacesMessage("Username already exists."));
            return null;
        }
        userManager.makeUser(username, password);
        return "home";
    }
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (username.equals("")) {
            context.addMessage(null, new FacesMessage("No username entered."));
            return null;
        }
        User user = userManager.find(username);
        if (user == null) {
            context.addMessage(null, new FacesMessage("Username or password incorrect."));
        } else if (user.isLocked()) {
            context.addMessage(null, new FacesMessage("You have exceeded your number of attempts to log in."));
        } else if (user.checkLogin(password)) {
            return "home";
        } else {
            context.addMessage(null, new FacesMessage("Username or password incorrect."));
        }
        return null;
    }
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "welcome";
    }
    
    public String cancel() {
        return "welcome";
    }
    
    public String cancelHome() {
        return "home";
    }
    
    public String profile() {
        return "profile";
    }
    
    public String updateProfile() {
        return "home";
    }
    
    public void setUserManager(UserManager um) {
        userManager = um;
    }
}
