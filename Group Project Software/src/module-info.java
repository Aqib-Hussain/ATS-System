module Group.Project.Software
{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    opens sample;
    opens sample.Staff;
}