import src.StudentUser;
import src.ProfileUI;
import src.User;
import src.UserManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.faces.context.FacesContext;
/**
 * JUnit Test for login()
 * @author Hongrui Zheng
 */
public class LoginTest {
    public LoginTest() {
    }
    
    @BeforeClass
    public static void setUpClass() { 
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of login method
     */
    @Test
    public void testLoginNull() {
        UserManager um = new UserManager();
        ProfileUI ui = new ProfileUI();
        ui.setUserManager(um);
        ui.setUsername(null);
        ui.setPassword(null);
        um.setUser(null);
        assertNull(ui.login());
        assertEquals("Username or password incorrect.", FacesContext.getCurrentInstance().getMessages().next().getDetail());
    }
    
    @Test 
    public void testLoginEmpty() {
        UserManager um = new UserManager();
        ProfileUI ui = new ProfileUI();
        ui.setUserManager(um);
        ui.setUsername("");
        ui.setPassword(null);
        um.setUser(null);
        ui.setUserManager(um);
        
        assertEquals("No username entered.", FacesContext.getCurrentInstance().getMessages().next().getDetail());
        assertNull(ui.login());
    }
    
    @Test
    public void testLoginLocked() {
        UserManager um = new UserManager();
        ProfileUI ui = new ProfileUI();
        StudentUser user = new StudentUser("user", "pass");
        user.setStatus(User.Status.Locked);
        um.setUser(user);
        ui.setUsername("user");
        ui.setPassword("pass");
        ui.setUserManager(um);
        assertNull(ui.login());
        assertEquals("You have exceeded your number of attempts to log in.", FacesContext.getCurrentInstance().getMessages().next().getDetail());
    }
    
    @Test
    public void testLoginBanned() {
        UserManager um = new UserManager();
        ProfileUI ui = new ProfileUI();
        StudentUser user = new StudentUser("user", "pass");
        user.setStatus(User.Status.Banned);
        um.setUser(user);
        ui.setUsername("user");
        ui.setPassword("pass");
        ui.setUserManager(um);
        assertNull(ui.login());
        assertEquals("You have been banned from this application.", FacesContext.getCurrentInstance().getMessages().next().getDetail());
    }
    
    @Test
    public void testLoginSuccess() {
        UserManager um = new UserManager();
        ProfileUI ui = new ProfileUI();
        ui.setUserManager(um);
        StudentUser user = new StudentUser("user", "pass");
        user.setStatus(User.Status.Normal);
        um.setUser(user);
        ui.setUsername("user");
        ui.setUsername("pass");
        assertEquals("home", ui.login());
        assertEquals(true, user.checkLogin("pass"));
    }
    
    @Test
    public void testLoginFail() {
        UserManager um = new UserManager();
        ProfileUI ui = new ProfileUI();
        StudentUser user = new StudentUser("user", "Wrongpass");
        user.setStatus(User.Status.Normal);
        um.setUser(user);
        ui.setUsername("user");
        ui.setUsername("pass");
        ui.setUserManager(um);
        assertNull(ui.login());
        assertEquals(false, user.checkLogin("pass"));
    }
}