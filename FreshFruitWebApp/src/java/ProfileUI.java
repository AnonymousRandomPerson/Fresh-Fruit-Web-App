import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean (name = "profileui")
@SessionScoped
/**
 * UI logic dealing with user profile and login/registration.
 */
public class ProfileUI extends UI {
         
    private String username;
    private String password;
    private String email;
    private Major major;
    private String interest;
    private String preferences;
    
    private Map<String,Map<String,String>> data = new HashMap<String, Map<String,String>>();
    private Map<String,String> majors;
    
    public ProfileUI() {
    }
    
    /**
     * Returns all users in the system.
     * @return a collection containing all users in the system
     */
    public Collection<User> getUsers() {
        return userManager.getUsers().values();
    }
    
    /**
     * Returns all existing statuses.
     * @return an array of strings representing each status
     */
    public String[] getStatuses() {
        User.Status[] statuses = User.Status.values();
        String[] statusStrings = new String[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            statusStrings[i] = statuses[i].toString();
        }
        return statusStrings;
    }
    
    /**
     * Gets the username in the UI.
     * @return the username in the UI
     */
    public String getUsername() { 
        return username;
    }
    
    /**
     * Gets the password in the UI.
     * @return the password in the UI
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Gets the email in the UI.
     * @return the email in the UI
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Gets the preferences in the UI.
     * @return the preferences in the UI
     */
    public String getPreferences() {
        return preferences;
    }
    
    /**
     * Gets the major in the UI.
     * @return the major in the UI
     */
    public Major getMajor() {
        return major;
    }
    
    /**
     * Gets major string for dropdown menu.
     * @return the major string
     */
    public String getMajorString() {
        if (major == null) {
            return Major.Un.name();
        } else {
            return major.name();
        }
    }
    
    /**
     * Gets the interests in the UI.
     * @return the interests in the UI
     */
    public String getInterest() {
        return interest;
    }
    
    /**
     * Sets the username in the UI.
     * @param u the new username in the UI
     */
    public void setUsername(String u) {
        username = u;
    }
    
    /**
     * Sets the password in the UI.
     * @param p the new password in the UI
     */
    public void setPassword(String p) {
        password = p;
    }
    
    /**
     * Sets the email in the UI.
     * @param e the new email in the UI
     */
    public void setEmail(String e) {
        email = e;
    }
    
    /**
     * Sets the major in the UI
     * @param m the new major in the UI
     */
    public void setMajor(Major m) {
        major = m;
    }
    
    /**
     * Sets the major according the provided string.
     * @param major the string to reverse lookup major from
     */
    public void setMajorString(String major) {
        this.major = Major.valueOf(major);
    }
    
    /**
     * Sets the preferences in the UI.
     * @param pref the new preferences in the UI
     */
    public void setPreferences(String pref) {
         preferences = pref;
    }
    
    /**
     * Sets the interests in the UI.
     * @param i the new interests in the UI
     */
    public void setInterest(String i) {
        interest = i;
    }
    
    /**
     * Registers a new user.
     * @return the home page if successful, null if not successful
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
        userManager.setUser(userManager.makeUser(username, password));
        context.addMessage(null, new FacesMessage("Registration successful."));
        return "home";
    }
    
    /**
     * Logs a user in.
     * @return the respective home page for the user's type if successful, null if not successful
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
        } else if (user.isBanned()) {
            context.addMessage(null, new FacesMessage("You have been banned from this application."));
        } else if (user.checkLogin(password)) {
            email = user.getEmail();
            if (user instanceof StudentUser) {
                major = ((StudentUser)user).getMajor();
                interest = ((StudentUser)user).getInterest();
                preferences = ((StudentUser)user).getPreferences();
            }
            userManager.setUser(user);
            return cancelHome();
        } else {
            context.addMessage(null, new FacesMessage("Username or password incorrect."));
        }
        return null;
    }
    
    /**
     * Logs a user out, resets all UI variables
     * @return the welcome page
     */
    public String logout() {
        username = password = email = interest = preferences = "";
        major = null;
        userManager.setUser(null);
        return "welcome";
    }
    
    /**
     * Cancels and goes back to the welcome page.
     * @return the welcome page
     */
    public String cancel() {
        username = "";
        return "welcome";
    }
    
    /**
     * Cancels and goes back to the home page.
     * @return the home page
     */
    public String cancelHome() {
        return userManager.getUser() instanceof AdminUser ? "adminhome" : "home";
    }
    
    /**
     * Goes to the profile page.
     * @return the profile page
     */
    public String profile() {
        return userManager.getUser() instanceof AdminUser ? "profileadmin" : "profile";
    }
    
    /**
     * Edits the profile of the user.
     * @return the home page if successful, null if unsuccessful
     */
    public String editProfile() {
        FacesContext context = FacesContext.getCurrentInstance();
        User user = userManager.getUser();
        user.setUserManager(userManager);
        if (!user.getUsername().equals(username) && userManager.find(username) != null) {
            System.out.println(user.getUsername() + " " +  username);
            context.addMessage(null, new FacesMessage("Username is already taken."));
            return null;
        } else if (user instanceof StudentUser) {
            ((StudentUser)user).editProfile(username, password, email, major, preferences, interest);
        } else if (user instanceof AdminUser) {
            ((AdminUser)user).editProfile(username, password, email);
        }
        return cancelHome();
    }
    
    /**
     * Makes a HashMap that maps fullName to name 
     */
    @PostConstruct
    public void init() {
        majors = new HashMap<String, String>();
        for (Major m : Major.values()) {
            majors.put(m.fullName, m.name());
        }
    }
    
    /**
     * Major Hashmap
     * @return data the data of the Hash map
     */
    public Map<String, Map<String, String>> getData() {
        return data;
    }
    
   /**
     * Major Hashmap
     * @return majors the map of majors 
     */
    public Map<String, String> getMajors() {
        return majors;
    }
}