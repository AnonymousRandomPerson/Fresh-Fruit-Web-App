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
    /**
     * Get the username of a user
     * @return String username
     */
    public String getUsername() { 
        return username;
    }
    /**
     * Get the password of a user
     * @return String password
     */
    public String getPassword() {
        return password;
    }
    
    public void getDob(int[] dob) {
        this.dob = dob;
    }
    /**
     * Get the preferences of a user
     * @return String the preferences
     */
    public String getPreferences() {
        return preferences;
    }
    /**
     * Get the major of a user
     * @return String the major of the user
     */
    public String getMajor() {
        return major;
    }
    /**
     * Get the interest of a user
     * @return String interest
     */
    public String getInterest() {
        return interest;
    }
    /**
     * set the preferences of a user
     * @param pref 
     */
    public void setPreferences(String pref) {
         preferences = pref;
    }
    /**
     * set the interest of a user
     * @param i 
     */
    public void setInterest(String i){
        interest = i;
    }
    /**
     * set the date of birth of a user
     * @param dob 
     */
    public void setDob(int[] dob) {
        this.dob = dob;
    }
    /**
     * set the username of a user
     * @param u 
     */
    public void setUsername(String u) {
        username = u;
    }
    /**
     * set the password of a user
     * @param p 
     */
    public void setPassword(String p) {
        password = p;
    }
    /**
     * set the major of a user
     * @param m 
     */
    public void setMajor(String m) {
        major = m;
    }
    /**
     * register a new user
     * @return the name of the xhtml file
     */
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
        context.addMessage(null, new FacesMessage("Registration successful."));
        return "home";
    }
    /**
     * login a user
     * @return the name of the xhtml file
     */
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
    /**
     * logout a user
     * @return the name of the xhtml file
     */
    public String logout() {
        username = "";
        major = "";
        interest = "";
        preferences = "";
        return "welcome";
    }
    /**
     * cancel and go back to welcome page
     * @return name of xhtml file
     */
    public String cancel() {
        return "welcome";
    }
    /**
     * cancel and go back to home page
     * @return name of xhtml file
     */
    public String cancelHome() {
        return "home";
    }
    /**
     * go to profile page
     * @return name of xhtml file
     */
    public String profile() {
        return "profile";
    }
    /**
     * edit the profile of a user
     * @return name of xhtml file
     */
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
    /**
     * set a usermanager
     * @param um 
     */
    public void setUserManager(UserManager um) {
        userManager = um;
    }
}
