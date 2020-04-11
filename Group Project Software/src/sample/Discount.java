package sample;

public class Discount
{
    private int customerID;
    private String discountType;
    private double discount;

    public Discount()
    {
        this.customerID = 0;
        this.discountType = "";
        this.discount = 0.00;
    }

    public Discount(double discount)
    {
        this.discount = discount;
    }
}
