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
    private ProfileUI ui;
    private UserManager um;
    private StudentUser user;
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
        ui = new ProfileUI();
        um = new UserManager();
        user = (StudentUser) um.makeUser("user", "pass");
        ui.setUserManager(um);
        um.setUser(user);
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of login method
     */
    @Test 
    public void testLoginEmpty() {
        ui.setUsername("");
        ui.setPassword("");
        assertEquals("No username entered.", ui.getMessage());
        assertNull(ui.login());
    }
    
    @Test
    public void testLoginLocked() {
        user.setStatus(User.Status.Locked);
        um.setUser(user);
        ui.setUsername("user");
        ui.setPassword("pass");
        ui.setUserManager(um);
        assertNull(ui.login());
        assertEquals("You have exceeded your number of attempts to log in.", ui.getMessage());
    }
    
    @Test
    public void testLoginBanned() {
        user.setStatus(User.Status.Banned);
        um.setUser(user);
        ui.setUsername("user");
        ui.setPassword("pass");
        ui.setUserManager(um);
        assertNull(ui.login());
        assertEquals("You have been banned from this application.", ui.getMessage());
    }
    
    @Test
    public void testLoginSuccess() {
        ui.setUsername("user");
        ui.setUsername("pass");
        assertEquals("home", ui.login());
        assertEquals(true, user.checkLogin("pass"));
    }
    
    @Test
    public void testLoginFail() {
        ui.setUsername("user");
        ui.setUsername("wrongpass");
        assertNull(ui.login());
        assertEquals(false, user.checkLogin("pass"));
    }
}