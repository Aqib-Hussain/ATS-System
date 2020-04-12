package sample;

public class Sale
{
    private int id;
    private String BlankID;
    private double amount;
    private String currency;
    private String paymentMethod;
    private double localTax;
    private double otherTax;
    private String creditcard;
    private String ticketType;
    private String origin;
    private String destination;
    private double commissionRate;
    private String customer;
    private String payBy;
    private String isPaid;
    private String soldBy;
    private String saleDate;
    private String state;
    private String refundDate;
    private double refundAmount;

    public Sale()
    {
        this.id = 0;
        this.BlankID = "";
        this.amount = 0.0;
        this.currency = "";
        this.paymentMethod = "";
        this.localTax = 0.0;
        this.otherTax = 0.0;
        this.creditcard = "";
        this.ticketType = "";
        this.origin = "";
        this.destination = "";
        this.commissionRate = 0.0;
        this.customer = "";
        this.payBy = "";
        this.isPaid = "";
        this.soldBy = "";
        this.saleDate = "";
        this.state = "";
        this.refundDate = "";
        this.refundAmount = 0.0;
    }

    public Sale(String blankID, double amount, String currency, double localTax, double otherTax, String paymentMethod, String customer, String origin, String destination, String soldBy, String state)
    {
        this.BlankID = blankID;
        this.amount = amount;
        this.currency = currency;
        this.localTax = localTax;
        this.otherTax = otherTax;
        this.paymentMethod = paymentMethod;
        this.customer = customer;
        this.origin = origin;
        this.destination = destination;
        this.soldBy = soldBy;
        this.state = state;
    }

    public Sale(String refundDate, String blankID, double refundAmount)
    {
        this.refundDate = refundDate;
        this.BlankID = blankID;
        this.refundAmount = refundAmount;
    }

    public Sale(int id, String blankID, double amount, String currency, double localTax, double otherTax, String paymentMethod,double commissionRate, String customer, String saleDate)
    {
        this.id = id;
        this.BlankID = blankID;
        this.amount = amount;
        this.currency = currency;
        this.localTax = localTax;
        this.otherTax = otherTax;
        this.paymentMethod = paymentMethod;
        this.commissionRate = commissionRate;
        this.customer = customer;
        this.saleDate = saleDate;
    }

    public Sale(int id, String blankID, double amount, String currency, double localTax, double otherTax, String paymentMethod,double commissionRate, String customer, String saleDate, String soldBy)
    {
        this.id = id;
        this.BlankID = blankID;
        this.amount = amount;
        this.currency = currency;
        this.localTax = localTax;
        this.otherTax = otherTax;
        this.paymentMethod = paymentMethod;
        this.commissionRate = commissionRate;
        this.customer = customer;
        this.saleDate = saleDate;
        this.soldBy = soldBy;
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

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
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

    public double getLocalTax()
    {
        return localTax;
    }

    public void setLocalTax(double localTax)
    {
        this.localTax = localTax;
    }

    public double getOtherTax()
    {
        return otherTax;
    }

    public void setOtherTax(double otherTax)
    {
        this.otherTax = otherTax;
    }

    public String getRefundDate()
    {
        return refundDate;
    }

    public void setRefundDate(String refundDate)
    {
        this.refundDate = refundDate;
    }

    public double getRefundAmount()
    {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount)
    {
        this.refundAmount = refundAmount;
    }

    public String getSaleDate()
    {
        return saleDate;
    }

    public void setSaleDate(String saleDate)
    {
        this.saleDate = saleDate;
    }
}
