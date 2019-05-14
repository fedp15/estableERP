package util;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CodigoConsecutivo extends conexion{
    private String Sql="";
    //Modelos.conexion conet = new conexion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public String CodigoConsecutivoCargos(String Campo, String Tabla, int Longitud, String Filtro){
        //***** Se declaran las variables de conexion en null
        Connection Conn = null;
        ResultSet rs = null;
        Statement consulta = null;
        String registros = null;
    
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        if (Longitud==0){
            Sql = "SELECT COUNT(CAR_ID)+1 AS CODIGO FROM "+Tabla+" ORDER BY CAR_ID DESC";
        }else{
            if (VarGlobales.getManejador().equals("MySQL")){
                Sql = "SELECT CONCAT(REPEAT('0',"+Longitud+"-LENGTH(CONVERT(MAX("+Campo+")+1,CHAR("+Longitud+")))),CONVERT(MAX("+Campo+")+1,CHAR("+Longitud+"))) AS CODIGO FROM "+Tabla+" "+Filtro;
            }else if (VarGlobales.getManejador().equals("PostGreSQL")){
                Sql = "SELECT CONCAT(REPEAT('0',"+Longitud+"-LENGTH((CAST(MAX("+Campo+") AS NUMERIC)+1)::TEXT)),(CAST(MAX("+Campo+") AS NUMERIC)+1)::TEXT) AS CODIGO FROM "+Tabla+" "+Filtro;
            }
        }
        
        String sql=(Sql);
        System.out.println(sql);

        try{
            rs = consultar(sql);

            if (rs.getRow()>0){
                rs.beforeFirst();
                while(rs.next()) {
                    if (rs.getString("CODIGO")!=null){
                        registros = rs.getString("CODIGO").trim();
                    }else{
                        registros=String.format("%0"+String.valueOf(Longitud)+"d",1);
                    }
                }
            }else{
                registros=String.format("%0"+String.valueOf(Longitud)+"d",1);
            }
        }catch (ClassNotFoundException |SQLException e) {
        
        }finally{
            try {
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CodigoConsecutivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return registros;
    }
        
    public String CodigoConsecutivo(String Campo, String Tabla, int Longitud, String Filtro){
        //***** Se declaran las variables de conexion en null
        Connection Conn = null;
        ResultSet rs = null;
        Statement consulta = null;
        String registros = null;
    
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        if (Longitud==0){
            //Sql = "SELECT MAX("+Campo+")+1 AS CODIGO FROM "+Tabla+" WHERE SUB_CLA_ID='0' ORDER BY CLA_ID_CATEG DESC";
            Sql = "SELECT MAX("+Campo+")+1 AS CODIGO FROM "+Tabla+" ORDER BY "+Campo+" DESC";
        }else{
            if (VarGlobales.getManejador().equals("MySQL")){
                Sql = "SELECT CONCAT(REPEAT('0',"+Longitud+"-LENGTH(CONVERT(MAX("+Campo+")+1,CHAR("+Longitud+")))),CONVERT(MAX("+Campo+")+1,CHAR("+Longitud+"))) AS CODIGO FROM "+Tabla+" "+Filtro;
            }else if (VarGlobales.getManejador().equals("PostGreSQL")){
                Sql = "SELECT CONCAT(REPEAT('0',"+Longitud+"-LENGTH((CAST(MAX("+Campo+") AS NUMERIC)+1)::TEXT)),(CAST(MAX("+Campo+") AS NUMERIC)+1)::TEXT) AS CODIGO FROM "+Tabla+" "+Filtro;
            }
        }
        
        String sql=(Sql);
        System.out.println(sql);

        try{
            rs = consultar(sql);

            if (rs.getRow()>0){
                rs.beforeFirst();
                while(rs.next()) {
                    if (rs.getString("CODIGO")!=null){
                        registros = rs.getString("CODIGO").trim();
                    }else{
                        if (Longitud!=0){
                            registros=String.format("%0"+String.valueOf(Longitud)+"d",1);
                        }else{
                            registros="1";
                        }
                    }
                }
            }else{
                registros=String.format("%0"+String.valueOf(Longitud)+"d",1);
            }
        }catch (ClassNotFoundException |SQLException e) {
        
        }finally{
            try {
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CodigoConsecutivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return registros;
    }
    
    public String CodigoConsecutivoFamilia(String Campo, String Tabla, int Longitud, String Filtro){
        //***** Se declaran las variables de conexion en null
        Connection Conn = null;
        ResultSet rs = null;
        Statement consulta = null;
        String registros = null;
    
        //Guardo la Consulta en una variable String en este caso la llamo "sql"
        if (Longitud==0){
            //Sql = "SELECT MAX("+Campo+")+1 AS CODIGO FROM "+Tabla+" WHERE SUB_CLA_ID='0' ORDER BY CLA_ID_CATEG DESC";
            Sql = "SELECT MAX("+Campo+")+1 AS CODIGO FROM "+Tabla+" ORDER BY "+Campo+" DESC";
        }else{
            Sql = "SELECT CONCAT(codigoMarca,REPEAT('0',"+Longitud+"-LENGTH(CONVERT(MAX(SUBSTR("+Campo+","+Longitud+"+1))+1,CHAR("+Longitud+")))),"+
                  "CONVERT(MAX(SUBSTR("+Campo+","+Longitud+"+1))+1,CHAR("+Longitud+"))) AS CODIGO FROM "+Tabla+" "+Filtro;
        }
        
        String sql=(Sql);
        System.out.println(sql);

        try{
            rs = consultar(sql);

            if (rs.getRow()>0){
                rs.beforeFirst();
                while(rs.next()) {
                    if (rs.getString("CODIGO")!=null){
                        registros = rs.getString("CODIGO").trim();
                    }else{
                        if (Longitud!=0){
                            registros=String.format("%0"+String.valueOf(Longitud)+"d",1);
                        }else{
                            registros="1";
                        }
                    }
                }
            }else{
                registros=String.format("%0"+String.valueOf(Longitud)+"d",1);
            }
        }catch (ClassNotFoundException |SQLException e) {
        
        }finally{
            try {
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(CodigoConsecutivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return registros;
    }
}
