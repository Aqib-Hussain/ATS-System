package sample;

public class Blank
{
    private String id;
    private String type;
    private String assignedTo;
    private String receivedDate;
    private String assignedDate;
    private String state;

    public Blank()
    {
        this.id = "";
        this.type = "";
        this.assignedTo = "";
        this.receivedDate = "";
        this.assignedDate = "";
        this.state = "";
    }

    public Blank(String id, String type, String assignedTo, String receivedDate, String assignedDate, String state)
    {
        this.id = id;
        this.type = type;
        this.assignedTo = assignedTo;
        this.receivedDate = receivedDate;
        this.assignedDate = assignedDate;
        this.state = state;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getAssignedTo()
    {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo)
    {
        this.assignedTo = assignedTo;
    }

    public String getReceivedDate()
    {
        return receivedDate;
    }

    public void setReceivedDate(String recivedDate)
    {
        this.receivedDate = recivedDate;
    }

    public String getAssignedDate()
    {
        return assignedDate;
    }

    public void setAssignedDate(String assignedDate)
    {
        this.assignedDate = assignedDate;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
}
