package src;

public class AdminUser extends User {

    public AdminUser(String username, String password) {
        super(username, password);
    }
    
    /**
     * Edits the user's profile.
     * @param user the new username
     * @param pass the new password
     * @param email the new email address
     */
    public void editProfile(String user, String pass, String email) {
        String oldUsername = username;
        if (!username.equals(user)) {
            userManager.changeUsername(username, user);
        }
        username = user;
        password = pass;
        this.email = email;

        UserManager.updateSQL("UPDATE USERS"
                    + " SET USERNAME=\'" + user + "\', PASSWORD=\'" + pass + "\', EMAIL=\'" + email + "\' WHERE USERNAME=\'" + oldUsername + "\'");
    }
}
