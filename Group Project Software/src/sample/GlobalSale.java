package sample;

public class GlobalSale
{
    private String name;
    private double totalAmount;
    private double totalTax;
    private double commRate;
    private int amountSold;

    public GlobalSale(String name, double totalAmount, double totalTax, double commRate, int amountSold)
    {
        this.name = name;
        this.totalAmount = totalAmount;
        this.totalTax = totalTax;
        this.commRate = commRate;
        this.amountSold = amountSold;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public double getTotalTax()
    {
        return totalTax;
    }

    public void setTotalTax(double totalTax)
    {
        this.totalTax = totalTax;
    }

    public double getCommRate()
    {
        return commRate;
    }

    public void setCommRate(double commRate)
    {
        this.commRate = commRate;
    }

    public int getAmountSold()
    {
        return amountSold;
    }

    public void setAmountSold(int amountSold)
    {
        this.amountSold = amountSold;
    }
}
