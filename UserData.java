/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author robertwaters
 */
class UserData {
    private String name;
    private String password;
    private String email;
    private String address;
    
    UserData(String nm, String ps) {
        name = nm;
        password = ps;
    }
    
    boolean checkLogin(String p) {
        return p.equals(password);
    }
    
}
