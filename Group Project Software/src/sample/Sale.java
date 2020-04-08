package sample;

public class Sale
{
    private int id;
    private String BlankID;
    private double local_amount;
    private String currency;
    private String paymentMethod;
    private double tax;
    private String creditcard;
    private String ticketType;
    private String origin;
    private String destination;
    private double commissionRate;
    private String customer;
    private String payBy;
    private String isPaid;
    private String soldBy;
    private String state;

    public Sale()
    {
        this.id = 0;
        this.BlankID = "";
        this.local_amount = 0.0;
        this.currency = "";
        this.paymentMethod = "";
        this.tax = 0.0;
        this.creditcard = "";
        this.ticketType = "";
        this.origin = "";
        this.destination = "";
        this.commissionRate = 0.0;
        this.customer = "";
        this.payBy = "";
        this.isPaid = "";
        this.soldBy = "";
        this.state = "";
    }

    public Sale(String blankID, double local_amount, String currency,  double tax, String paymentMethod, String customer, String origin, String destination, String soldBy, String state)
    {
        this.BlankID = blankID;
        this.local_amount = local_amount;
        this.currency = currency;
        this.tax = tax;
        this.paymentMethod = paymentMethod;
        this.customer = customer;
        this.origin = origin;
        this.destination = destination;
        this.soldBy = soldBy;
        this.state = state;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getBlankID()
    {
        return BlankID;
    }

    public void setBlankID(String blankID)
    {
        BlankID = blankID;
    }

    public double getLocal_amount()
    {
        return local_amount;
    }

    public void setLocal_amount(double local_amount)
    {
        this.local_amount = local_amount;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public double getTax()
    {
        return tax;
    }

    public void setTax(double tax)
    {
        this.tax = tax;
    }

    public String getCreditcard()
    {
        return creditcard;
    }

    public void setCreditcard(String creditcard)
    {
        this.creditcard = creditcard;
    }

    public String getTicketType()
    {
        return ticketType;
    }

    public void setTicketType(String ticketType)
    {
        this.ticketType = ticketType;
    }

    public String getOrigin()
    {
        return origin;
    }

    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public String getDestination()
    {
        return destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public double getCommissionRate()
    {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate)
    {
        this.commissionRate = commissionRate;
    }

    public String getCustomer()
    {
        return customer;
    }

    public void setCustomer(String customer)
    {
        this.customer = customer;
    }

    public String getPayBy()
    {
        return payBy;
    }

    public void setPayBy(String payBy)
    {
        this.payBy = payBy;
    }

    public String getIsPaid()
    {
        return isPaid;
    }

    public void setIsPaid(String isPaid)
    {
        this.isPaid = isPaid;
    }

    public String getSoldBy()
    {
        return soldBy;
    }

    public void setSoldBy(String soldBy)
    {
        this.soldBy = soldBy;
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
