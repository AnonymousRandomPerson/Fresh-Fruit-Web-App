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
    private String major;
    private String interest;
    private String preferences; 
    private int[] dob;
    
    public UI() {
    }
    
    public String getUsername() { 
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void getDob(int[] dob) {
        this.dob = dob;
    }
    
    public String getPreferences() {
        return preferences;
    }
    
    public String getMajor() {
        return major;
    }
    
     public String getInterest() {
        return interest;
    }
    
    public void setPreferences(String pref) {
         preferences = pref;
    }
    
    public void setInterest(String i){
        interest = i;
    }
    
    public void setDob(int[] dob) {
        this.dob = dob;
    }
    
    public void setUsername(String u) {
        username = u;
    }
    
    public void setPassword(String p) {
        password = p;
    }
    
    public void setMajor(String m) {
        major = m;
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
            major = user.getMajor();
            interest = user.getInterest();
            preferences = user.getPreferences();
            return "home";
        } else {
            context.addMessage(null, new FacesMessage("Username or password incorrect."));
        }
        return null;
    }
    
    public String logout() {
        username = "";
        major = "";
        interest = "";
        preferences = "";
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
    
    public String editProfile(){
        FacesContext context = FacesContext.getCurrentInstance();
        User user = userManager.find(username);
        user.setMajor(major);
        user.setPreferences(preferences);
        user.setInterest(interest);
        user.setPassword(password);
        
        
       // user.setDob(dob);
        return "home";
    }
    
    public void setUserManager(UserManager um) {
        userManager = um;
    }
}
