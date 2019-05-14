package util;

import Modelos.VariablesGlobales;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CargaComboBox {
    private static CargaComboBox cargaComboBox;
    static VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private static Connection Conn = null;
    private static ResultSet rs = null;
    private static Statement consulta = null;
    private static int registros = 0;
    private static String bd = "";
    
    private CargaComboBox() {
    
    }
    
    public static CargaComboBox getCargaComboBox(){
        if (cargaComboBox == null){
            cargaComboBox = new CargaComboBox();
        }

        return cargaComboBox;
    }
    
    public static Vector Elementos(String Sql){
        Vector elementos = new Vector();
        
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        String sql=(Sql);
        System.out.println(sql);
        
        try{
            if (VarGlobales.getManejador().equals("PostGreSQL")){
                Class.forName("org.postgresql.Driver");
                
                Conn = DriverManager.getConnection("jdbc:postgresql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos(), VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                consulta = Conn.createStatement();
                rs = consulta.executeQuery(sql);
            }else if (VarGlobales.getManejador().equals("MySQL")){
                Class.forName("com.mysql.jdbc.Driver");
                
                if (VarGlobales.islBaseDatosConfiguracion()){
                    bd = VarGlobales.getBaseDatosConfiguracion();
                }else{
                    bd = VarGlobales.getBaseDatos();
                }
                
                Conn = DriverManager.getConnection("jdbc:mysql://"+VarGlobales.getIpServer()+"/"+bd+"?allowMultiQueries=true", VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                consulta = Conn.createStatement();
                rs = consulta.executeQuery(sql);
            }
            
            elementos.add(" ");
            
            while(rs.next()) {
                if (rs.getString("DATO1")!=null){
                    elementos.add(rs.getString("DATO1").trim());
                }
            }
        }catch (ClassNotFoundException |SQLException e) {
            Logger.getLogger(CargaComboBox.class.getName()).log(Level.SEVERE, null, e);
        }finally{  
            try{    
                rs.close();
                Conn.close();
            }catch (SQLException e){                 

            }  
        }
        
        return elementos;
    }
    
    public static Vector ElementosAnoLibros(String Sql){
        Vector elementos = new Vector();
        
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        String sql=(Sql);
        System.out.println(sql);
        
        try{
            if (VarGlobales.getManejador().equals("PostGreSQL")){
                Class.forName("org.postgresql.Driver");
                
                Conn = DriverManager.getConnection("jdbc:postgresql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos(), VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                consulta = Conn.createStatement();
                rs = consulta.executeQuery(sql);
            }else if (VarGlobales.getManejador().equals("MySQL")){
                Class.forName("com.mysql.jdbc.Driver");
                
                if (VarGlobales.islBaseDatosConfiguracion()){
                    bd = VarGlobales.getBaseDatosConfiguracion();
                }else{
                    bd = VarGlobales.getBaseDatos();
                }
                
                Conn = DriverManager.getConnection("jdbc:mysql://"+VarGlobales.getIpServer()+"/"+bd+"?allowMultiQueries=true", VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                consulta = Conn.createStatement();
                rs = consulta.executeQuery(sql);
            }
            
            elementos.add("");
            
            if (rs.getRow()>0){
                while(rs.next()) {
                    if (rs.getString("DATO1")!=null){
                        elementos.add(rs.getString("DATO1").trim());
                    }
                }
            }else{
                Calendar c2 = new GregorianCalendar();
                
                elementos.add(String.valueOf(c2.get(Calendar.YEAR)));
            }
        }catch (ClassNotFoundException |SQLException e) {
            Logger.getLogger(CargaComboBox.class.getName()).log(Level.SEVERE, null, e);
        }finally{  
            try{
                rs.close();
                Conn.close();
            }catch (SQLException e){                 

            }  
        }
        
        return elementos;
    }
    
    public static Vector ListaBaseDatos(String Sql, String ip, String user, String pass){
        ResultSet rs = null;
        int registros = 0;
    
        Vector elementos = new Vector();
        
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        String sql=(Sql);
        
        try{    
            //Conexion conet = new Conexion();
            //rs = consultarBaseDatosCombo(Sql, ip, user, pass);
            System.err.println(VarGlobales.getManejador());
            if (VarGlobales.getManejador().equals("PostGreSQL")){
                Class.forName("org.postgresql.Driver");
                
                Conn = DriverManager.getConnection("jdbc:postgresql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos(), VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                consulta = Conn.createStatement();
                rs = consulta.executeQuery(sql);
            }else if (VarGlobales.getManejador().equals("MySQL")){
                Class.forName("com.mysql.jdbc.Driver");
                
                try {
                    Conn = DriverManager.getConnection("jdbc:mysql://"+ip+"/mysql"+"?allowMultiQueries=true", user, pass);
                    consulta = Conn.createStatement();
                    rs = consulta.executeQuery(sql);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "No se establecio conexion con el servicio de MySQL", "ERRO!!", JOptionPane.ERROR_MESSAGE);
                }
            }
            
            elementos.add("");

            rs.first();
            for (int i=0; i<rs.getRow(); i++){
                System.err.println("BD: "+rs.getString("DataBase"));
                elementos.add(rs.getString("DataBase"));
                rs.next();
            }
            
            //conn.close();
        }catch (SQLException e) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CargaComboBox.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                rs.close();
                Conn.close();
            } catch (Exception e) {
                return elementos;
            }
        }
        
        return elementos;
    }
}