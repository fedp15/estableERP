package util;

import Modelos.conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;

public class SQLSelect extends conexion{

    public  Object[][] SQLSelect (String Sql) {    
        Connection Conn = null;
        ResultSet rs = null;
        Statement consulta = null;
    
        String sql=(Sql);
        System.out.println(sql);
        
        Object elementos[][]=null;
        
        try{
            this.creaConexion();
            consulta= conn.createStatement();
            //rs = consulta.executeQuery(sql);
            rs = consultar(sql);

//            rs.last();
    
            ResultSetMetaData rsmd = rs.getMetaData();
            
            int numCols = rsmd.getColumnCount();
            int numFils =rs.getRow();
            if (numFils==0){
                numFils=1;
            }

            elementos=new Object[numFils][numCols];
            int j = 0;

//            rs.beforeFirst();
            rs.beforeFirst();
            while(rs.next()) { 
                for (int i=0;i<numCols;i++){
                    //JOptionPane.showMessageDialog(null, rs.getObject(i+1));
                    try {
                        elementos[j][i]=rs.getObject(i+1);
                    } catch (Exception e) {
                    }
                }
                j++;
            }
            
            conn.close();
        }catch (ClassNotFoundException |SQLException e) {
            //JOptionPane.showMessageDialog(null, e);
        }
        return elementos;
    }
}