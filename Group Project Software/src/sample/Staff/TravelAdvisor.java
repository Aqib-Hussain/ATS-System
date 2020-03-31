package sample.Staff;

public class TravelAdvisor
{
    private String name;
    private String StaffType;
    private String password;
    private String email;
    private String address;
    private String id;

    public TravelAdvisor()
    {
        this.name = "";
        this.password = "";
        this.StaffType = "";
        this.id = "";
    }

    public TravelAdvisor(String name, String id, String email, String address)
    {
        this.name = name;
        this.id = id;
        this.email = email;
        this.address = address;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getStaffType()
    {
        return StaffType;
    }

    public void setStaffType(String staffType)
    {
        StaffType = staffType;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}
