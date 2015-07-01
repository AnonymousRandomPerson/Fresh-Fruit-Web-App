import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

@ManagedBean (name = "userManager")
@ApplicationScoped
/**
 * Manages all users in the system.
 */
public class UserManager {
    
    private Map<String, User> userList = new HashMap<>();
    public static final int LIMITTRIES = 3;
    
    private User currentUser;
    
    private static final String host = "jdbc:derby://localhost:1527/fruit";
    private static final String uName = "team11";
    private static final String uPass= "fruit";
    
    /**
     * Creates a new instance of UserManager.
     */
    public UserManager() {
    }
    
    /**
     * Returns the current user of the application.
     * @return the user of the application.
     */
    public User getUser() {
        return currentUser;
    }
    
    /**
     * Sets the current application user.
     * @param user the new user
     */
    public void setUser(User user) {
        currentUser = user;
    }

    /**
     * Makes a new user and puts it in the map.
     * @param user the new user's username
     * @param pass the new user's password
     * @return the new user
     */
    public User makeUser(String user, String pass) {
        User newUser = new StudentUser(user, pass);
        newUser.setUserManager(this);
        userList.put(user, newUser);
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String SQL = "INSERT INTO USERS (USERNAME, PASSWORD)"
                    + "VALUES (\'" + user + "\',\'" + pass + "\')";
            stmt.executeUpdate( SQL );
        }
        catch (SQLException err) {
            err.printStackTrace();
        }
        
        return newUser;
    }
    
    /**
     * Changes a user's username in the hash map.
     * @param oldName the user's old name
     * @param newName the user's new name
     */
    public void changeUsername(String oldName, String newName) {
        User user = find(oldName);
        userList.put(newName, user);
        userList.remove(oldName);
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String SQL = "UPDATE USERS"
                    + " SET USERNAME=\'" + newName + "\' WHERE USERNAME=\'" + oldName + "\'";
            stmt.executeUpdate( SQL );
        }
        catch (SQLException err) {
            err.printStackTrace();
        }
    }
    
    /**
     * Finds a user in the map.
     * @param username the user to find
     * @return the User object, or null if the user does not exist
     */
    public User find(String username) {
        if (userList.get(username) != null) {
            return userList.get(username);
        }
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String SQL = "SELECT USERNAME,PASSWORD,EMAIL,MAJOR,PREFERENCES,INTEREST FROM USERS WHERE USERNAME=\'" + username + "\'";
            ResultSet rs = stmt.executeQuery( SQL );
            if (rs.next()) {
                StudentUser newUser = new StudentUser(rs.getString("USERNAME"), rs.getString("PASSWORD"));
                newUser.setUserManager(this);
                newUser.setEmail(rs.getString("EMAIL"));
                newUser.setMajor(Major.valueOf(rs.getString("MAJOR")));
                newUser.setPreferences(rs.getString("PREFERENCES"));
                newUser.setInterest(rs.getString("INTEREST"));
                userList.put(rs.getString("USERNAME"), newUser);
                return newUser;
            }
        }
        catch (SQLException err) {
            err.printStackTrace();
        }
    return null;
    }
    
    /**
     * Finds a user by his/her unique id in the database.
     * @param id the id to find a user for
     * @return the user with the id, or null if no user is found
     */
    public static User find(int id) {
        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);
            Statement stmt = con.createStatement();
            String SQL = "SELECT USERNAME,PASSWORD,EMAIL,MAJOR,PREFERENCES,INTEREST FROM USERS WHERE USERID=" + id;
            ResultSet rs = stmt.executeQuery( SQL );
            if (rs.next()) {
                StudentUser newUser = new StudentUser(rs.getString("USERNAME"), rs.getString("PASSWORD"));
                newUser.setEmail(rs.getString("EMAIL"));
                newUser.setMajor(Major.valueOf(rs.getString("MAJOR")));
                newUser.setPreferences(rs.getString("PREFERENCES"));
                newUser.setInterest(rs.getString("INTEREST"));
                return newUser;
            }
        } catch (SQLException err) {
            err.printStackTrace();
        }
        return null;
    }
}
