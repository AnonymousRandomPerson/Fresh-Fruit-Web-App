package FreshFruitWebApp.src.java;

class UserData {
    private String name;
    private String password;
    
    UserData(String nm, String ps) {
        name = nm;
        password = ps;
    }
    
    boolean checkLogin(String p) {
        return p.equals(password);
    }
    
}
