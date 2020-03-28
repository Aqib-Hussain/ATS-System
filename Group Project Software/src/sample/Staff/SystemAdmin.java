package sample.Staff;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.Arrays;

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
            String dbName = "ats";
            String dbUser = "root";
            String dbPass = "password";

            //Create the command to be executed in the terminal
            String executeCmd = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe -u" + dbUser + " -p" + dbPass + " --all-databases" + " -r" + savePath;
            System.out.println(executeCmd);

            // Command is executed here
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);

            if (runtimeProcess.waitFor() == 0) {
                //normally terminated, a way to read the output
                InputStream inputStream = runtimeProcess.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);
            } else {
                // abnormally terminated, there was a problem
                InputStream errorStream = runtimeProcess.getErrorStream();
                byte[] buffer = new byte[errorStream.available()];
                errorStream.read(buffer);

                String str = new String(buffer);
                System.out.println(str);

            }
        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "SystemBackUp Error " + ex.getMessage());
        }

    }

    public static void SystemRestore() {
        String restorePath = "C:\\Users\\Aqib\\Desktop\\ATS-System\\BackupDump\\BackUp.sql";
        try {
            String dbName = "ats";
            String dbUser = "root";
            String dbPass = "password";

            //Create the command to be executed in the terminal
            String[] executeCmd = new String[]{"mysql", dbName, "-u" + dbUser, "-p" + dbPass, "-e", " source " + restorePath};
            System.out.println(Arrays.toString(executeCmd));

            //Execute the command
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();


        } catch (IOException | InterruptedException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "SystemRestore Error" + ex.getMessage());
        }

    }
}
