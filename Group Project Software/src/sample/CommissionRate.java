package sample;

public class CommissionRate
{
    private String id;
    private String blankType;
    private double commissionRate;

    public CommissionRate()
    {
        this.id = "";
        this.blankType = "";
        this.commissionRate = 0.0;
    }

    public CommissionRate(String id, String blankType, double commissionRate)
    {
        this.id = id;
        this.blankType = blankType;
        this.commissionRate = commissionRate;
    }

    public CommissionRate(double commissionRate)
    {
        this.commissionRate = commissionRate;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getBlankType()
    {
        return blankType;
    }

    public void setBlankType(String blankType)
    {
        this.blankType = blankType;
    }

    public double getCommissionRate()
    {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate)
    {
        this.commissionRate = commissionRate;
    }
}
