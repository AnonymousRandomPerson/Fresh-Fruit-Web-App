public class StudentUser extends User {

    public StudentUser(String username, String password) {
        super(username, password);
        major = Major.Un;
    }
    
    /**
    * Status of the user
    */
    public enum Status {
        Normal, Locked, Banned
    }
    
    private int[] dob;
    private Major major;
    private String interest;
    private Status status;
    private String preferences;
    
    /**
    * Gets the date of birth.
    * @return the date of birth
    */
    public int[] getDob() {
        return dob;
    }
    
    /**
    * Gets major.
    * @return the major
    */
    public Major getMajor() {
        return major;
    }
    
    /**
    * Gets interests.
    * @return the interests
    */
    public String getInterest() {
        return interest;
    }
    
    /**
     * Gets the status of the user.
     * @return the status of the user
     */
    public Status getStatus() {
        return status;
    }
    
    /**
    * Gets preferences.
    * @return the preferences 
    */
    public String getPreferences() {
        return preferences;
    }
    
    /**
     * Sets date of birth.
     * @param dob the new date of birth
     */
    public void setDob(int[] dob) {
        this.dob = dob;
    }
    
    /**
     * Sets the major
     * @param major the new major
     */
    public void setMajor(Major major) {
        this.major = major;
    }
    
    /**
     * Sets the interests.
     * @param interest the new user interests 
     */
    public void setInterest(String interest) {
        this.interest = interest;
    }
    
    /**
     * Sets the status.
     * @param status the new status
     */
    public void setStatus(Status status) {
        this.status = status;
    }
    
    /**
    * Sets the user's preferences.
    * @param preferences the new preferences
    */
    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    /**
     * Edits the profile of a user.
     * @param user the user's new username
     * @param pass the user's new password
     * @param email the user's new email
     * @param major the user's new major
     * @param preferences the user's new preferences
     * @param interest the user's new interests
     */
    public void editProfile(String user, String pass, String email, Major major, String preferences, String interest) {
        if (!username.equals(user)) {
            userManager.changeUsername(username, user);
        }
        username = user;
        password = pass;
        this.email = email;
        this.major = major;
        this.preferences = preferences;
        this.interest = interest;
    }
}
