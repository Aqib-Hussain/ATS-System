package sample.Users;

import sample.Users.User;

public class OfficeManager extends User
{
    private String email = "1";
    private String password = "1";
    private String userType = "Office Manager";

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
