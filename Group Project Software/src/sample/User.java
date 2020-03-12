package sample;

public class User
{
    private String email = "1";
    private String password = "2";
    private String userType;

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }

    public String getUserType()
    {
        return userType;
    }
}
