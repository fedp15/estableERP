package util;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class SQLQuerys extends conexion{
    private static PreparedStatement ps = null;
    private int success=0;
    
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    
    public int SqlInsert(String Sql){
        try {
            creaConexion();
            this.consulta = this.conn.prepareStatement(Sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            success = this.consulta.executeUpdate();
            
            this.consulta.close();
            this.stSentenciciasSQL.close();
            conn.close();
        } catch (ClassNotFoundException e){
            
	}catch (SQLException ex){
            if (lBdExist==false){
                   
            }else{
               JOptionPane.showMessageDialog(null, ex, "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
               
            String Error = ex.toString();
            System.out.println(Error);
               // Verifica el error de Conexion en la IP y Puerto del Servidor
            if (Error.endsWith("from the server.")==true){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgErrorConexMySQL"), 
                                                       idioma.loadLangMsg().getString("MsgTituloError"), JOptionPane.ERROR_MESSAGE);
            }
            // Verifica el error de Conexion en el Usuario(s) de MySQL
            if (Error.endsWith("to database 'mysql'")==true){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgErrorMySQLUser1")+" ''"+VarGlobales.getIdUser()+"'', "+idioma.loadLangMsg().getString("MsgErrorMySQLUser2"), 
                                                       idioma.loadLangMsg().getString("MsgTituloError"), JOptionPane.ERROR_MESSAGE);
            }
            // Verifica el error de Conexion en la Clave del Usuario(s) de MySQL
            if (Error.endsWith("(using password: YES)")==true){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgErrorMySQLUser1")+" ''"+VarGlobales.getIdUser()+"'', "+idioma.loadLangMsg().getString("MsgErrorMySQLPassword"), 
                                                       idioma.loadLangMsg().getString("MsgTituloError"), JOptionPane.ERROR_MESSAGE);
            }
               
//****************************** PostGreSQL ******************************
            // Verifica el error de Conexion en la Clave del Usuario(s) de MySQL
            if (Error.endsWith("contraseña.")==true){
                JOptionPane.showMessageDialog(null, "El servidor requiere autenticación basada en contraseña, para el Superusuario ''postgres'' "+
                                                       "pero no se ha provisto ninguna contraseña...!", "Error en Conexión", JOptionPane.ERROR_MESSAGE);
            }
               
            if (Error.endsWith("conexiones TCP/IP.")==true){
                JOptionPane.showMessageDialog(null, "Conexión rechazada. Verifique que el nombre del Host y el puerto sean correctos "+
                                                       "y que postmaster este aceptando conexiones TCP/IP...!", "Error en Conexión", JOptionPane.ERROR_MESSAGE);
            }
               
            if (Error.endsWith("usuario �postgres�")==true){
                JOptionPane.showMessageDialog(null, "la autentificación del password fallo para el usuario ''postgres'' ", "Error en Conexión", JOptionPane.ERROR_MESSAGE);
            }
	}finally{
            try {
                this.consulta.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQLQuerys.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return success;
    }
    
    public int SqlInsert(String Sql, String orgAccion){
        try {
            creaConexion();
            this.consulta = this.conn.prepareStatement(Sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            success = this.consulta.executeUpdate();
            
            this.consulta.close();
            this.stSentenciciasSQL.close();
            conn.close();
        } catch (ClassNotFoundException e){
            
	}catch (SQLException ex){
            if (lBdExist==false){
                   
            }else{
                if (orgAccion.equals("ProgressBarCreaBd")){
                    VarGlobales.setErrorCreaBd(ex.toString());
                }
            }
               
            String Error = ex.toString();
            System.out.println(Error);
               // Verifica el error de Conexion en la IP y Puerto del Servidor
            if (Error.endsWith("from the server.")==true){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgErrorConexMySQL"), 
                                                       idioma.loadLangMsg().getString("MsgTituloError"), JOptionPane.ERROR_MESSAGE);
            }
            // Verifica el error de Conexion en el Usuario(s) de MySQL
            if (Error.endsWith("to database 'mysql'")==true){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgErrorMySQLUser1")+" ''"+VarGlobales.getIdUser()+"'', "+idioma.loadLangMsg().getString("MsgErrorMySQLUser2"), 
                                                       idioma.loadLangMsg().getString("MsgTituloError"), JOptionPane.ERROR_MESSAGE);
            }
            // Verifica el error de Conexion en la Clave del Usuario(s) de MySQL
            if (Error.endsWith("(using password: YES)")==true){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgErrorMySQLUser1")+" ''"+VarGlobales.getIdUser()+"'', "+idioma.loadLangMsg().getString("MsgErrorMySQLPassword"), 
                                                       idioma.loadLangMsg().getString("MsgTituloError"), JOptionPane.ERROR_MESSAGE);
            }
               
//****************************** PostGreSQL ******************************
            // Verifica el error de Conexion en la Clave del Usuario(s) de MySQL
            if (Error.endsWith("contraseña.")==true){
                JOptionPane.showMessageDialog(null, "El servidor requiere autenticación basada en contraseña, para el Superusuario ''postgres'' "+
                                                       "pero no se ha provisto ninguna contraseña...!", "Error en Conexión", JOptionPane.ERROR_MESSAGE);
            }
               
            if (Error.endsWith("conexiones TCP/IP.")==true){
                JOptionPane.showMessageDialog(null, "Conexión rechazada. Verifique que el nombre del Host y el puerto sean correctos "+
                                                       "y que postmaster este aceptando conexiones TCP/IP...!", "Error en Conexión", JOptionPane.ERROR_MESSAGE);
            }
               
            if (Error.endsWith("usuario �postgres�")==true){
                JOptionPane.showMessageDialog(null, "la autentificación del password fallo para el usuario ''postgres'' ", "Error en Conexión", JOptionPane.ERROR_MESSAGE);
            }
	}finally{
            try {
                this.consulta.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQLQuerys.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return success;
    }
    
    public int SqlUpdate(String Sql){
        try {
            this.creaConexion();
            this.consulta = this.conn.prepareStatement(Sql);
            success = this.consulta.executeUpdate();
            
            conn.close();
        } catch (ClassNotFoundException e){
	
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Error SQL Exception: \n"+ex.getMessage(),"ERROR!!", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(SQLQuerys.class.getName()).log(Level.SEVERE, null, ex);
	}finally{
            try {
                this.consulta.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQLQuerys.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return success;
    }
    
    public int SqlDelete(String Sql, String org, String dest){
        try {
            this.creaConexion();
            this.consulta = this.conn.prepareStatement(Sql);
            System.out.println(consulta);
            this.consulta.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
            success = this.consulta.executeUpdate(Sql);
            this.consulta.executeUpdate("SET FOREIGN_KEY_CHECKS=1");
            
            conn.close();
        } catch (ClassNotFoundException e){
            
	} catch (SQLException ex){
            System.err.println(ex);
            //JOptionPane.showMessageDialog(null, "Error al Eliminar Registro, debe tener una llave foranea asociada");
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgErrorSQLDelete1")+" ("+org+"), "+idioma.loadLangMsg().getString("MsgErrorSQLDelete2")+" "+dest,
                                          idioma.loadLangMsg().getString("MsgTituloNotif"), JOptionPane.ERROR_MESSAGE);
        }finally{
            try {
                this.consulta.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQLQuerys.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return success;
    }
    
    public void SqlInsertImagen(String Sql,String ruta){
        FileInputStream fis;

        try {
            //conn.setAutoCommit(false);
            this.creaConexion();
            
            File file = new File(ruta);
            fis = new FileInputStream(file);
            
            this.consulta = this.conn.prepareStatement(Sql);

            //this.consulta.setBinaryStream(1,fis,fis.available());
            this.consulta.setBlob(1, fis);
            this.consulta.executeUpdate();

            //conexion.commit();
        } catch(ClassNotFoundException | SQLException | FileNotFoundException e){
            System.out.println(e.getMessage());
        }
    }
    
    public int SqlInsertUpdateStruct(String Sql){
        try {
            creaConexion();
            
            if (Sql.startsWith("drop table")==true){
            //if (Sql.equals("drop table dncomprobante;")){
                //ResultSet rs = consultar("show tables like 'dncomprobante_temp'");
                try {
                    int existTabla = Count_Reg("SELECT COUNT(*) AS REGISTROS FROM information_schema.tables\n" +
                                               "WHERE table_schema = '"+VarGlobales.getBaseDatos()+"' AND table_name = 'dncomprobante_temp'");

                    if (existTabla>0){
                        creaConexion();
                        
                        Statement s = this.conn.createStatement();
                        s.execute("SET FOREIGN_KEY_CHECKS=0");
                        s.executeUpdate(Sql);
                        s.execute("SET FOREIGN_KEY_CHECKS=1");
                        s.close();
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }if (Sql.startsWith("TRUNCATE")==true){
                try {
                    creaConexion();
                        
                    Statement s = this.conn.createStatement();
                    s.execute("SET FOREIGN_KEY_CHECKS=0");
                    s.executeUpdate(Sql);
                    s.execute("SET FOREIGN_KEY_CHECKS=1");
                    s.close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }else{
                this.consulta = this.conn.prepareStatement(Sql,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                success = this.consulta.executeUpdate();
            
                this.consulta.close();
                this.stSentenciciasSQL.close();
                conn.close();
            }
        }catch (ClassNotFoundException | SQLException e){
            Logger.getLogger(SQLQuerys.class.getName()).log(Level.SEVERE, null, e);
            VarGlobales.setErrorCreaBd(e.toString());
	}finally{
            try {
                //this.consulta.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQLQuerys.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return success;
    }
    
    public int SqlInsertUpdateStructExecute(String Sql){
        try {
            creaConexion();
            
//            Statement stmtConsulta = this.conn.createStatement();
//            stmtConsulta.execute(Sql);
//            stmtConsulta.close();
            
            String sql = "SET FOREIGN_KEY_CHECKS=0;";
            //PreparedStatement ps = this.conn.prepareStatement(Sql);
            PreparedStatement ps = null;
            
            if (Sql.equals("SET FOREIGN_KEY_CHECKS=0;")){
                Statement s = this.conn.createStatement();
                s.execute("SET FOREIGN_KEY_CHECKS=0");
                //s.executeUpdate("DELETE FROM flights");
                //s.execute("SET FOREIGN_KEY_CHECKS=1");
                s.close();
            }else if (Sql.equals("SET FOREIGN_KEY_CHECKS=1;")){
                Statement s = this.conn.createStatement();
                //s.execute("SET FOREIGN_KEY_CHECKS=0");
                //s.executeUpdate("DELETE FROM flights");
                s.execute("SET FOREIGN_KEY_CHECKS=1");
                s.close();
            }else{
                ps = this.conn.prepareStatement(Sql);
                ps.execute();
                ps.close();
            }
        }catch (ClassNotFoundException | SQLException e){
            Logger.getLogger(e.getMessage());
            Logger.getLogger(SQLQuerys.class.getName()).log(Level.SEVERE, null, e);
	}finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SQLQuerys.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return success;
    }
}