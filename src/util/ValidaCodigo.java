package util;

import Modelos.conexion;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ValidaCodigo extends conexion {
    public int ValidaCodigo (String Sql, boolean elmin, String cadena) {
        //***** Se declaran las variables de conexion en null
        ResultSet rs = null;
        int registros = 0;
    
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        String sql=(Sql);
        System.out.println(sql);
        
        try{
            this.creaConexion();
            this.consulta = this.conn.prepareStatement(sql);
            rs = this.consulta.executeQuery();
//            this.consulta= (com.mysql.jdbc.Statement) this.conn.createStatement();
//            rs = this.consulta.executeQuery(sql);
            
            //consulta = Conn.createStatement();
            //rs = consulta.executeQuery(sql);
  
            while(rs.next()) {
                if (rs.getInt("REGISTROS") > 0){
                    if (elmin == false){
                        if (cadena == "RIF"){
                            JOptionPane.showMessageDialog(null, "Este Numero de "+cadena+" ya Existe");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Este Numero de Código ya Existe");
                        }
                    }
                    registros = rs.getInt("REGISTROS");
                }
                else{
                    registros = 0;
                }
            }
        }catch (ClassNotFoundException |SQLException e) {
        
        }finally{
            try {
                this.consulta.close();
                conn.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ValidaCodigo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return registros;
    }    
}