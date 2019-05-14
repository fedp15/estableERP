package util;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author Andres
 */
public class ValidarJornada extends conexion{
    String horas, dias;
    int resultado = 1;
    
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public ValidarJornada(String hora, String dia){
        horas = hora;
        dias = dia;
    }
    
    public int consulta(){
        ResultSet rs = null;
        int respuesta;
        String codigo = "";
        String sql = "SELECT DISTINCT JOR_CODIGO,JOR_HORAINI,JOR_HORAFIN FROM dnjornada WHERE JOR_"+dias.toUpperCase()+"=1 AND JOR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' ";
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date hora = null;
        try {
            hora = format.parse(horas);
        } catch (ParseException ex) {
            Logger.getLogger(ValidarJornada.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs = consultar(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ValidarJornada.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(rs.getRow()>0){
                codigo = rs.getString("JOR_CODIGO");
                try {
                    if(hora.before(format.parse(rs.getString("JOR_HORAINI"))) || hora.after(format.parse(rs.getString("JOR_HORAFIN")))){
                        respuesta = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgFueraJornada"),idioma.loadLangMsg().getString("MsgTituloNotif"),JOptionPane.YES_NO_OPTION);
                        if(respuesta == 0){
//                            ConfigSuper supervisor = new ConfigSuper();
//                            supervisor.Verifica("EMP_NOJORNADA", "POS");
                            resultado = 0;
                        }else{
                            System.exit(0);
                        }
                    } 
                } catch (ParseException ex) {
                    Logger.getLogger(ValidarJornada.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgNoHayJornada"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ValidarJornada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
