package com.msp.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author jmunoz
 */
public class GetConnection_SAMPLE {

    private String nameDB;
    private static String usser = "";
    private static int port = 3306;
    private static String pass = "";

    public final static Connection RDS(String nameDB) throws Exception {

        String url = "";
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://" + url + ":" + port + "/" + nameDB;
            connection = DriverManager.getConnection(servidor, usser, pass);
//            System.out.println("Connected to: " + nameDB);
        } catch (Exception ex) {
            ex.getMessage();
            System.out.println(ex.getMessage());
            connection = null;
        } finally {
            return connection;
        }
    }

    public final static Connection AUR(String nameDB) throws Exception {

        String url = "";
        int port = 3306;
//        String name = "MDL";
        String usser = "";
        String pass = "";

        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://" + url + ":" + port + "/" + nameDB;
            connection = DriverManager.getConnection(servidor, usser, pass);
            System.out.println("Conected to " + nameDB);
        } catch (Exception ex) {
            ex.getMessage();
            connection = null;
        } finally {
            return connection;
        }
    }

    public static String getUsser() {
        return usser;
    }

}
