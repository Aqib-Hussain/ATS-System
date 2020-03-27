package sample;

import java.nio.DoubleBuffer;
import java.sql.Date;

public class Refund
{
    private String date;
    private String blank;
    private String amount;

    public Refund()
    {
        this.date = "";
        this.blank = "";
        this.amount = "";
    }

    public Refund(String date, String blank, String amount)
    {
        this.date = date;
        this.blank = blank;
        this.amount = amount;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getBlank()
    {
        return blank;
    }

    public void setBlank(String blank)
    {
        this.blank = blank;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }
}
