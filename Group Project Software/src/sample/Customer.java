package sample;

public class Customer
{
    private String ID;
    private String name;
    private String phoneNumber;
    private String address;
    private String type;
    private double discount;
    private double dueBalance;

    public Customer()
    {
        this.ID = "";
        this.name = "";
        this.phoneNumber = "";
        this.address = "";
        this.type = "";
        this.discount = 0.0;
        this.dueBalance = 0.0;
    }

    public Customer(String ID, String name, String phoneNumber, String type, double discount)
    {
        this.ID = ID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.discount = discount;
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public double getDiscount()
    {
        return discount;
    }

    public void setDiscount(double discount)
    {
        this.discount = discount;
    }

    public double getDueBalance()
    {
        return dueBalance;
    }

    public void setDueBalance(double dueBalance)
    {
        this.dueBalance = dueBalance;
    }
}
