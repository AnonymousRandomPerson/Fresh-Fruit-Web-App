package FreshFruitWebApp.src.java;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author robertwaters
 */
@ManagedBean
@SessionScoped
public class User implements Serializable {
    private String username;
    private String password;
    private final int LIMITTRIES = 3;
    private int numTries;
    
    @ManagedProperty("#{userManager}")
    private UserManager userManager;
    

    /**
     * Creates a new instance of User
     */
    public User() {
        numTries = 0;
    }
    
    public String getUsername() { 
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setUsername(String u) {
        username = u;
    }
    
    public void setPassword(String p) {
        password = p;
    }
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (username.equals("")) {
            context.addMessage(null, new FacesMessage("No username entered."));
            return null;
        }
        if (numTries >= LIMITTRIES) {
            context.addMessage(null, new FacesMessage("You have exceeded your number of attempts to log in."));
            return null;
        }
        UserData data = userManager.find(username);
        if (data == null || !data.checkLogin(password)) {
            numTries++;
            context.addMessage(null, new FacesMessage("Username or password incorrect."));
            return null;
        }
        return "home";
    }
    
    public String register() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (username.equals("")) {
            context.addMessage(null, new FacesMessage("No username entered."));
            return null;
        }
        if (userManager.find(username) != null) {
            context.addMessage(null, new FacesMessage("Username already exists."));
            return null;
        }
        userManager.makeUser(username, password);
        return "home";
    }
    
    public String cancel() {
        return "welcome";
    }
    
    public void setUserManager(UserManager um) {
        userManager = um;
    }

}
