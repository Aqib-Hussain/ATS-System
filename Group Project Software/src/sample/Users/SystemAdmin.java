package sample.Users;

import sample.Users.User;

public class SystemAdmin extends User
{
    private String email = "2";
    private String password = "2";
    private String userType = "System Administrator";

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public String getUserType()
    {
        return userType;
    }
}
