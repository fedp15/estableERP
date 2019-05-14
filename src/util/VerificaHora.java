
package util;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andres
 */
public class VerificaHora extends conexion{
    Date hora = new Date(), horaInicial = null;
    String horaFinal = "";
    Calendar horario = Calendar.getInstance();
    SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    
    public VerificaHora(){
        int h = horario.get(Calendar.HOUR_OF_DAY);
        int m = horario.get(Calendar.MINUTE);
        String horas = String.valueOf(h)+":"+String.valueOf(m);
        try {
            hora = formato.parse(horas);
        } catch (ParseException ex) {
            Logger.getLogger(VerificaHora.class.getName()).log(Level.SEVERE, null, ex);
        }
        BuscarTurno();
        if(horaInicial!=null){
            long repetir = 5000;
        
            Timer temporizador = new Timer();
            temporizador.scheduleAtFixedRate(new Temporizador(horaFinal,hora), 0, repetir);
        }
    }
    
    private void BuscarTurno(){
        ResultSet rs = null;
        String dia = "";
        String horaActual = "", horaInicio = "";
        int h = 0,minutos = 0;
        horaActual = formato.format(hora);
        
        if(horario.get(Calendar.DAY_OF_WEEK)==1){
            dia = "domingo";
        }
        if(horario.get(Calendar.DAY_OF_WEEK)==2){
            dia = "lunes";
        }
        if(horario.get(Calendar.DAY_OF_WEEK)==3){
            dia = "martes";
        }
        if(horario.get(Calendar.DAY_OF_WEEK)==4){
            dia = "miercoles";
        }
        if(horario.get(Calendar.DAY_OF_WEEK)==5){
            dia = "jueves";
        }
        if(horario.get(Calendar.DAY_OF_WEEK)==6){
            dia = "viernes";
        }
        if(horario.get(Calendar.DAY_OF_WEEK)==7){
            dia = "sabado";
        }
        String sql = "SELECT JOR_CODIGO,JOR_TURDESCRI,JOR_TURINI,JOR_TURFIN FROM dnjornada WHERE "+
                     "JOR_EMPRESA='"+VarGlobales.getCodEmpresa()+"' AND "+
                     "JOR_"+dia.toUpperCase()+"=1";
        System.out.println(sql);             
        try {
            rs = consultar(sql);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VerificaHora.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if(rs.getRow()>0){
                rs.beforeFirst();
                while(rs.next()){
                //if(!rs.isLast()){
                    try {
                        if(!rs.getString("JOR_TURINI").equals("") && !rs.getString("JOR_TURFIN").equals("")){
                            if(hora.after(formato.parse(rs.getString("JOR_TURINI"))) && hora.before(formato.parse(rs.getString("JOR_TURFIN")))){
                                h = Integer.valueOf(horaActual.substring(0,2));
                                minutos = Integer.valueOf(horaActual.substring(3, 5));
//                                if(minutos>45){
//                                    minutos = minutos+15-60;
//                                    if(h==23){
//                                        h=0;
//                                        horaInicio="00:"+String.valueOf(minutos);
//                                    }else{
//                                        h = h+1;
//                                        horaInicio=String.valueOf(h)+":"+String.valueOf(minutos);
//                                    }
//                                }else{
//                                    minutos = minutos+15;
//                                    horaInicio=String.valueOf(h)+":"+String.valueOf(minutos);
//                                }
                                if(minutos==59){
                                    minutos = 0;
                                    if(h==23){
                                        h=0;
                                        horaInicio="00:"+String.valueOf(minutos);
                                    }else{
                                        h = h+1;
                                        horaInicio=String.valueOf(h)+":"+String.valueOf(minutos);
                                    }
                                }else{
                                    minutos = minutos++;
                                    horaInicio=String.valueOf(h)+":"+String.valueOf(minutos);
                                }
                                horaInicial = formato.parse(horaInicio);
                                horaFinal = rs.getString("JOR_TURFIN");
                                
                                VarGlobales.setJornada(rs.getString("JOR_CODIGO"));
                                VarGlobales.setTurno(rs.getString("JOR_TURDESCRI"));
                                
                                break;
                            }
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(VerificaHora.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(VerificaHora.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
