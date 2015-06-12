public class StudentUser extends User {

    
    public StudentUser(String username, String password) {
        super(username, password);
    }
    
 /**
 * Status of the user 
 *
 */
    public enum Status {
        Normal, Locked, Banned
    }
    
    private int[] dob;
    private String major;
    private String interest;
    private Status status;
    private String preferences;
    
 /**
 * Method that gets the date of birth 
 * @return the DOB
 */
    public int[] getDob() {
        return dob;
    }
    
 /**
 * Method that gets major
 * @return the major
 */
    public String getMajor() {
        return major;
    }
    
 /**
 * Method that gets interest
 * @return the interests
 */
    public String getInterest() {
        return interest;
    }
    
    /**
 * Method that gets the status of user 
 * @return the status
 */
    public Status getStatus() {
        return status;
    }
 /**
 * Method that gets preferences
 * @return the preferences 
 */
    public String getPreferences() {
        return preferences;
    }
    
/**
 * Method that sets Dob
 * @param dob takes in a DOB
 */
    public void setDob(int[] dob) {
        this.dob = dob;
    }
/**
 * Method that sets the major
 * @param major takes in a major
 */
    public void setMajor(String major) {
        this.major = major;
    }
    
    public void setInterest(String interest) {
        this.interest = interest;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
    
    public void reviewMovie() {
        
    }
    
    //@Override
    public void editProfile(String user, String pass, String major, String preferences) {
            
            
            if (!major.equals("")){
                this.major = major;
            }
            
    }
    
}
