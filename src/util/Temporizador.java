package util;

import Modelos.VariablesGlobales;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Andres
 */
public class Temporizador extends TimerTask{
//    private POS pos;
    String horaFinal = "";
    Date horaActual = null;
    SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        
//    ModelPos modelPos = ModelPos.getModelPos();
    VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();
    
    public Temporizador(String hora, Date horaA){
        horaFinal = hora;
    }
    
    @Override
    public void run() {
        int h,m,s;
        Date horaPrecierre = null;
        Calendar horario = Calendar.getInstance();
        
        h = horario.get(Calendar.HOUR_OF_DAY);
        m = horario.get(Calendar.MINUTE);
        
        String horas = String.valueOf(h)+":"+String.valueOf(m);
        try {
            horaActual = formato.parse(horas);
        } catch (ParseException ex) {
            Logger.getLogger(Temporizador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        h = Integer.valueOf(horaFinal.substring(0,2));
        m = Integer.valueOf(horaFinal.substring(3, 5));
        if(m<15){
            m = m-15+60;
            if(h==0){
                h = 23;
            }else{
                h = h-1;
            }
        }else{
            m = m-15;
        }
        String horaCierre = String.valueOf(h)+":"+String.valueOf(m);
        try {
            horaPrecierre = formato.parse(horaCierre);
        } catch (ParseException ex) {
            Logger.getLogger(Temporizador.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(horaActual.compareTo(horaPrecierre)==0){
            JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("Msg15Min"),idioma.loadLangMsg().getString("MsgTituloNotif"),JOptionPane.YES_NO_OPTION);
        }
        try {
            if(VarGlobales.getContinuaSesion()==false){
                if(horaActual.compareTo(formato.parse(horaFinal))==0 || horaActual.compareTo(formato.parse(horaFinal))>0){
                    int respuesta;
                    respuesta = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgTurnoFin"),idioma.loadLangMsg().getString("MsgTituloNotif"),JOptionPane.YES_NO_OPTION);
                    if(respuesta==0){
//                        ConfigSuper supervisor = new ConfigSuper();
//                        supervisor.Verifica("EMP_SESION", "MantenerSesion");
                        
                        VarGlobales.setContinuaSesion(true);
                    }else{
//                        pos = (POS) modelPos.getVista();
//                        pos.dispose();
                        System.exit(0);
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(Temporizador.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
    
}
