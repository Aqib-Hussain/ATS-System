package sample.Staff;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class SystemAdmin extends StaffAccount {
    private String email = "2";
    private String password = "2";
    private String userType = "System Administrator";


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public static void SystemBackUp() {
        String savePath = "C:\\Users\\Aqib\\Desktop\\ATS-System\\BackupDump\\BackUp.sql";
        try {

            /*NOTE: Creating Database Constraints*/
            String dbName = "ats";
            String dbUser = "root";
            String dbPass = "Edward220600!!";

            /*NOTE: Used to create a cmd command*/
            String executeCmd = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump -u" + dbUser + " -p" + dbPass + " --database" + dbName + " -r" + savePath;
            System.out.println(executeCmd);

            /*NOTE: Executing the command here*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);

            int processComplete = runtimeProcess.waitFor();

            // If backup is successful processComplete returns 0
            if (processComplete == 0) {
                System.out.println("Backup Complete");
            } else {
                System.out.println("Backup Failure");
            }

        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "SystemBackUp Error " + ex.getMessage());
        }

    }

    public static void SystemRestore() {
        String restorePath = "C:\\Users\\edwar\\Desktop\\ATS-System\\BackupDump\\BackUp.sql";
        try {

            /*NOTE: Creating Database Constraints*/
            String dbName = "ats";
            String dbUser = "root";
            String dbPass = "Edward220600!!";

            // Used to create a cmd command
            String[] executeCmd = new String[]{"mysql", dbName, "-u" + dbUser, "-p" + dbPass, "-e", " source " + restorePath};


            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            // If restore is successful processComplete returns 0
            if (processComplete == 0) {
                JOptionPane.showMessageDialog(null, "Successfully restored from SQL");
            } else {
                JOptionPane.showMessageDialog(null, "Error at restoring");
            }


        } catch (IOException | InterruptedException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "SystemRestore Error" + ex.getMessage());
        }

    }
}
