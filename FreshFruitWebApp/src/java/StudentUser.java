public class StudentUser extends User {

    public StudentUser(String username, String password) {
        super(username, password);
    }
    
    public enum Status {
        Normal, Locked, Banned
    }
    
    private int[] dob;
    private String major;
    private String interest;
    private Status status;
    private String preferences;
    
    public int[] getDob() {
        return dob;
    }
    
    public String getMajor() {
        return major;
    }
    
    public String getInterest() {
        return interest;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public String getPreferences() {
        return preferences;
    }
    
    public void setDob(int[] dob) {
        this.dob = dob;
    }
    
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
