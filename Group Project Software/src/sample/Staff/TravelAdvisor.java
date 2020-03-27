package sample.Staff;

public class TravelAdvisor
{
    private String name;
    private String StaffType;
    private String password;
    private String id;

    public TravelAdvisor()
    {
        this.name = "";
        this.password = "";
        this.StaffType = "";
        this.id = "";
    }

    public TravelAdvisor(String name, String StaffType, String password, String id)
    {
        this.name = name;
        this.StaffType = StaffType;
        this.password = password;
        this.id = id;
    }

    public TravelAdvisor(String name, String id)
    {
        this.name = name;
        this.id = id;
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
}
