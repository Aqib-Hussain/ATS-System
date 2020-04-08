module Group.Project.Software
{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires java.desktop;
    requires org.joda.time;

    opens sample;
    opens sample.Staff;
}