/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Collection;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import src.ProfileUI;
import src.User;
import src.UserManager;

/**
 *
 * @author Greg
 */
public class TestEditProfile {
    /** The username that will be tested. */
    private String username;
    /** The password that will be tested. */
    private String password;


    public TestEditProfile() {
    }
    /**
     *Begin Setup
     */
    @Before
    public void setUp() {
    }
    /**
     * Test of editProfile method, Branch: Blank Username.
     */
    @Test
    public void testEditBlankUsername() {
        ProfileUI editprof = new ProfileUI() {
            private String m;
            public String getMessage() {
                return m;
            }
            @Override
            public void displayMessage(String msg) {
                m = msg;
            }
        };
        editprof.setUsername("");
        String result = editprof.register();

        assertEquals(result, null);
        assertEquals(editprof.getMessage(), "No username entered.");
    }
    /**
     * Test of editProfile method, Branch: Duplicate username.
     */
    @Test
    public void testDuplicateUsername() {
        ProfileUI editprof = new ProfileUI() {
            private String m;
            public String getMessage() {
                return m;
            }
            @Override
            public void displayMessage(String msg) {
                m = msg;
            }
        };
        editprof.setUsername("user");
        editprof.userManager = new UserManager();
        editprof.userManager.makeUser("user", "pass");
        String result = editprof.register();

        assertEquals(result, null);
        assertEquals(editprof.getMessage(), "Username already exists.");
    }
    /**
     * Test of editProfile method, Branch: Test success.
     */
    @Test
    public void testSucess() {
        ProfileUI editprof = new ProfileUI() {
            private String m;
            @Override
            public String getMessage() {
                return m;
            }
            @Override
            public void displayMessage(String msg) {
                m = msg;
            }
        };
        editprof.userManager = new UserManager();
        editprof.userManager.makeUser("user", "pass");
        String result = editprof.register();
        assertEquals(result, "home");
        assertEquals(editprof.getMessage(), "Registration successful.");
    }
//    @Test
//    public void testEditUsername() {
//        ProfileUI editprof = new ProfileUI();
//                editprof.setEmail("gportz3@gmail.com");
//                editprof.setUsername("gporz3");
//
//
//        ProfileUI instance = new ProfileUI();
//        editprof.setUsername("john");
//        String result = editprof.getUsername();
//        assertFalse(editprof.getUsername().equals("gporz3"));
//        assertEquals(result, "john");
//    }
//
//    @Test
//    public void testEditPassword() {
//        ProfileUI editprof = new ProfileUI();
//                editprof.setPassword("1234");
//                
//        ProfileUI instance = new ProfileUI();
//        editprof.setPassword("12344");
//        String expResult = "12344";
//        String result = editprof.getPassword();
//        System.out.println(result);
//        System.out.print("expected result " + expResult);
////        assertFalse(editprof.getPassword().equals("12345"));
////        assertEquals(result, "12344");
//    }
//    public void testEditPreference() {
//        ProfileUI editprof = new ProfileUI();
//                editprof.setPreferences("This Movie was bad");
//
//        ProfileUI instance = new ProfileUI();
//        editprof.setPreferences("the movie was great");
//        String expResult = "the movie was great";
//        String result = editprof.getPreferences();
//        System.out.println(result);
//        System.out.print("expected result " + expResult);
//        assertFalse(editprof.getPreferences().equals("the movie was great"));
//        assertEquals(result, "the movie was great");
//    }  
//    public void testEditInterests() {
//        ProfileUI editprof = new ProfileUI();
//                editprof.setInterest("I like action movies");
//ProfileUI instance = new ProfileUI();
//        editprof.setInterest("I don't like Tom cruise movies");
//        String expResult = "I don't like Tom cruise movies";
//        String result = editprof.getInterest();
//        System.out.println(result);
//        System.out.print("expected result " + expResult);
//        assertFalse(editprof.getInterest().equals("I don't like
//                        + Tom cruise movies"));
//        assertEquals(result, "I don't like Tom cruise movies");
//
//   
//    }
}