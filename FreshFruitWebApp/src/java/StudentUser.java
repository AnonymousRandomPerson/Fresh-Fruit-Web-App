
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A normal user with the ability to search and rate movies.
 */
public class StudentUser extends User {
    
    private final String host = "jdbc:derby://localhost:1527/fruit";
    private final String uName = "team11";
    private final String uPass= "fruit";

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
    
    private Major major;
    private String preferences;
    private String interest;
    private int[] dob;
    private Status status;
    
    /**
    * Gets the date of birth.
    * @return the date of birth
    */
    public int[] getDob() {
        return dob;
    }
    
    /**
     * Returns the user's major.
     * @return the user's major
     */
    public Major getMajor() {
        return major;
    }
    
    /**
     * Returns the user's interests.
     * @return the user's interests
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
     * Returns the user's preferences.
     * @return the user's preferences
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
     * Sets the user's major.
     * @param major the new major
     */
    public void setMajor(Major major) {
        this.major = major;
    }
    
    /**
     * Sets the user's interests.
     * @param interest the new interests
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
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String SQL = "UPDATE USERS"
                    + " SET PASSWORD=\'" + pass + "\', EMAIL=\'" + email + "\', MAJOR=\'" + major + "\', PREFERENCES=\'" + preferences + "\', INTEREST=\'" + interest + "\' WHERE USERNAME=\'" + user + "\'";
            stmt.executeUpdate( SQL );
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
}
