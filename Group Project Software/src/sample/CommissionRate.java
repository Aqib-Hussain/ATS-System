package sample;

import java.text.DecimalFormat;

public class CommissionRate
{
    private int id;
    private String blankType;
    private double commissionRate;

    public CommissionRate()
    {
        this.id = 0;
        this.blankType = "";
        this.commissionRate = 0.00;
    }

    public CommissionRate(int id, String blankType, double commissionRate)
    {
        this.id = id;
        this.blankType = blankType;
        this.commissionRate = commissionRate;
    }

    public CommissionRate(double commissionRate)
    {
        this.commissionRate = commissionRate;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
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
