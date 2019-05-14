package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class PruebaMySQL {
    public static String bd = "omnix_erp", login = "root", password = "";
    public static String url = "jdbc:mysql://127.0.0.1:3306/omnix_erp";

    public PruebaMySQL() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, login, password);
            System.out.println("CONEXION EXITOSA");
            JOptionPane.showMessageDialog(null, "CONEXION EXITOSA");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("ERROR DE CONEXION: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "ERROR DE CONEXION: " + ex.getMessage());
            Logger.getLogger(PruebaMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}
