import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean (name = "profileui")
@SessionScoped
public class ProfileUI extends UI {
    
    private String username;
    private String password;
    private String email;
    private String major;
    private String interest;
    private String preferences;
    
    public ProfileUI() {
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
    public String getMajor() {
        return major;
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
    public void setMajor(String m) {
        major = m;
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
    public void setInterest(String i){
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
     * @return the home page if successful, null if not successful
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
            email = user.getEmail();
            if (user instanceof StudentUser) {
                major = ((StudentUser)user).getMajor();
                interest = ((StudentUser)user).getInterest();
                preferences = ((StudentUser)user).getPreferences();
            }
            userManager.setUser(user);
            return "home";
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
        username = password = major = email = interest = preferences = "";
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
        return "home";
    }
    
    /**
     * Goes to the profile page.
     * @return the profile page
     */
    public String profile() {
        return "profile";
    }
    
    /**
     * Edits the profile of the user.
     * @return the home page if successful, null if unsuccessful
     */
    public String editProfile() {
        FacesContext context = FacesContext.getCurrentInstance();
        User user = userManager.getUser();
        if (!user.getUsername().equals(username) && userManager.find(username) != null) {
            System.out.println(user.getUsername() + " " +  username);
            context.addMessage(null, new FacesMessage("Username is already taken."));
            return null;
        } else if (user instanceof StudentUser) {
            ((StudentUser)user).editProfile(username, password, email, major, preferences, interest);
        }
        return "home";
    }
}