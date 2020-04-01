package sample;

import Database.DBConnectivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {
    private String firstName;
    private String surName;
    private String type;
    private double discount;

    public Customer() {
        this.firstName = "";
        this.surName = "";
        this.type = "";
        this.discount = 0.0;
    }

    public Customer(String firstName, String surName, String type, double discount) {
        this.firstName = firstName;
        this.surName = surName;
        this.type = type;
        this.discount = discount;
    }

    public String getFirstName() {
        return firstName;
    }



    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }


}
