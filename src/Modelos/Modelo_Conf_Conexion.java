package Modelos;

import util.SQLQuerys;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import util.CargaComboBox;

public class Modelo_Conf_Conexion extends conexion{
    private String IpPuertoBd, UserBd, PassBd, Bd, Manejador, MacPc, IpPc;
    private int TabManejador;
    private ResultSet rs;
    
    public void setIpPuertoBd(String ipPuertoBd){
        IpPuertoBd = ipPuertoBd;
    }
    
    public void setUserBd(String userbd){
        UserBd = userbd;
    }
    
    public void setPassBd(String passbd){
        PassBd = passbd;
    }
    
    public void setBd(String bd){
        Bd = bd;
    }
    
    public void setManejador(String manejador){
        Manejador = manejador;
    }
    
    public void setTabManejador(int Tab){
        TabManejador = Tab;
    }
    
    public void setMacPc(String macpc){
        MacPc = macpc;
    }
    
    public void setIpPc(String ippc){
        IpPc = ippc;
    }
    
    public String getIpPuertoBd(){
        return IpPuertoBd;
    }
    
    public String getUserBd(){
        return UserBd;
    }
    
    public String getPassBd(){
        return PassBd;
    }
    
    public String getBd(){
        return Bd;
    }
    
    public String getManejador(){
        return Manejador;
    }
    
    public void getConsultaConex(){
        String sql = ("SELECT * FROM DNCONEXION");
        try {
            rs = consultar(sql);
            
            if (rs.getRow()>0){
                IpPuertoBd = rs.getString("CONF_IP").trim();
                UserBd = rs.getString("CONF_USER").trim(); 
                PassBd = rs.getString("CONF_PASS").trim();
                Bd = rs.getString("CONF_BD").trim();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Modelo_Conf_Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getExisteConexion(){
        ResultSet Reg_count2;
        
        String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM dnconexion WHERE CONF_IP='"+IpPuertoBd+"' AND "+
                        "CONF_BD='"+Bd+"' AND CONF_MANEJADOR='"+Manejador+"'";

        int Reg_count = Count_Reg(SQLReg);
        try {
            Reg_count2 = consultar(SQLReg);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Modelo_Conf_Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Reg_count;
    }
    
    public int getExisteConexionLocal(){
        String SQLReg = "SELECT COUNT(*) AS REGISTROS FROM DNCONEXION WHERE CONF_MACPC='"+MacPc+"' AND CONF_IP='"+IpPuertoBd+"' AND "+
                        "CONF_USER='"+UserBd+"' AND CONF_PASS='"+PassBd+"' AND CONF_BD='"+Bd+"' AND CONF_MANEJADOR='"+Manejador+"'";
        int Reg_count = Count_Reg(SQLReg);

        return Reg_count;
    }
    
    public void listaDataBases(JComboBox cmbListDb, String ip, String user, String pass){
        String sql = "SHOW DATABASES;";
        
        DefaultComboBoxModel mdl = new DefaultComboBoxModel(CargaComboBox.ListaBaseDatos(sql, ip, user, pass));    
        cmbListDb.setModel(mdl);
        cmbListDb.setSelectedIndex(0);
    }
    
    public void setGuardaConex(){
        SQLQuerys insertarconex = new SQLQuerys();
        String SQL = "INSERT INTO DNCONEXION (CONF_IPPC,CONF_MACPC,CONF_IP,CONF_USER,CONF_PASS,CONF_BD,CONF_MANEJADOR) "+
                                     "VALUES ('"+IpPc+"','"+MacPc+"','"+IpPuertoBd+"','"+UserBd+"','"+PassBd+"','"+Bd+"','"+Manejador+"')";

        insertarconex.SqlInsert(SQL);
    }
    
    public ResultSet getArranque(Boolean lFlag){
        ResultSet rs = null;
        String sql = "";
        
        if(lFlag){
            sql = "SELECT EMP_CODIGO, base_datos_empresa FROM dnempresas WHERE EMP_WELCOME=1";
        }else{
            sql = "SELECT COUNT(*) AS CUANTOS FROM dnempresas WHERE EMP_WELCOME=1";
        }
        
        try {
            rs = consultar(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Modelo_Conf_Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
}