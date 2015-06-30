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
public class UserManager {
    
    private Map<String, User> userList = new HashMap<>();
    public static final int LIMITTRIES = 3;
    
    private User currentUser;
    
    private String host = "jdbc:derby://localhost:1527/fruit";
    private String uName = "team11";
    private String uPass= "fruit";

        
 
    
    
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
            System.out.println(err.getMessage());
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
            System.out.println(err.getMessage());
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
            String SQL = "SELECT USERNAME,PASSWORD FROM USERS WHERE USERNAME=\'" + username + "\'";
            boolean result = stmt.execute( SQL );
            ResultSet rs = stmt.executeQuery( SQL );
            if (result) {
                User newUser = new StudentUser(rs.getString("USERNAME"), rs.getString("PASSWORD"));
                newUser.setUserManager(this);
                userList.put(rs.getString("USERNAME"), newUser);
                return newUser;
            }
        }
        catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    return null;
    }
}
