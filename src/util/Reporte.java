package util;

import Modelos.conexion;
import static Modelos.ReporteJas.CONEXION;
import Modelos.VariablesGlobales;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.io.File;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Reporte extends conexion{
    private ResultSet data;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
//    private final ModelPos modelPos = ModelPos.getModelPos();
//    private final ModelRepEtiquetaCaja modelRepEtiquetaCaja = ModelRepEtiquetaCaja.getModelRepEtiquetaCaja();
    
    public void reporte(String origen,String numfact,String codmae,String coddoc,String empresa){
        String SQL,title="Reporte";
        Double despro=0.00,dessub=0.00,totaldesc=0.00;
       
        try{
            switch (VarGlobales.getManejador()) {
                case "PostGreSQL":
                    Class.forName("org.postgresql.Driver");
                    CONEXION = DriverManager.getConnection("jdbc:postgresql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos(), VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                    break;
            case "MySQL":
                    Class.forName("com.mysql.jdbc.Driver");
                    CONEXION = DriverManager.getConnection("jdbc:mysql://"+VarGlobales.getIpServer()+"/"+VarGlobales.getBaseDatos()+"?allowMultiQueries=true", VarGlobales.getUserServer(), VarGlobales.getPasswServer());
                    break;
            }

//            File jasper = new File(System.getProperty("user.dir")+"/build/classes/Reportes/FactBargain.jasper");
            File jasper = null;
//            JasperReport reporte = null;
//            reporte=(JasperReport) JRLoader.loadObject(jasper);

            //Declaración de Parametros para consulta en IReport
            Map param=new HashMap();

            if(origen.equals("FACPOS")){
                String fecha;
        
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fecha = sdf.format(date);
                
                SQL = "SELECT SUM(INV_DESPRO) AS DESPRO,INV_DESDOC AS DESSUB,"+
                      "SUM(INV_DESDOC) AS DESDOC FROM DNINVENTARIO "+
                      "WHERE INV_CODMAE='"+codmae+"' "+
                      "AND INV_NUMDOC='"+numfact+"' "+
                      "AND INV_EMPRESA='"+empresa+"' "+
                      "AND INV_CODDOC='POS'";
                
                System.out.println("reporte bargain: "+SQL);
         
                data = consultar(SQL);
                
                int regist = Count_Reg("SELECT COUNT(*) AS REGISTROS FROM dninventario WHERE INV_CODMAE='"+codmae+"' "+
                                       "AND INV_NUMDOC='"+numfact+"' "+
                                       "AND INV_EMPRESA='"+empresa+"' "+
                                       "AND INV_CODDOC='POS'");
                
                System.out.println("reporte bargain: "+regist);
         
                if(data.getRow()>0){
                    despro=data.getDouble("DESPRO"); 
                    dessub=data.getDouble("DESSUB");
                    totaldesc=despro+dessub;
                }
                
                param.put("numfact"  ,numfact);
                System.out.println("params numfact: "+numfact);
                param.put("desc_subt",dessub);
                System.out.println("params dessub: "+dessub);
                param.put("desc_pro" ,despro);
                System.out.println("params despro: "+despro);
                param.put("cliente"  ,codmae);
                System.out.println("params codmae: "+codmae);
                param.put("tipdoc"   ,coddoc);
                System.out.println("params coddoc: "+coddoc);
                param.put("empresa"  ,empresa);
                System.out.println("params empresa: "+empresa);
                param.put("fecha"    ,fecha);
                System.out.println("params fecha: "+fecha);
                param.put("totaldesc", totaldesc);
                
                jasper = new File(System.getProperty("user.dir")+"/build/classes/Reportes/FactBargain.jasper");
                title = "Your Bargain Spot";
            }
            
            if(origen.equals("INV")){
                param.put("numdoc",numfact);
                param.put("tipdoc",coddoc);
                param.put("empresa",VarGlobales.getCodEmpresa());
                
                jasper = new File(System.getProperty("user.dir")+"/build/classes/Reportes/fact_codbarra.jasper");
            }
            
            if(origen.equals("EtiquetaCaja")){
                param.put("numdoc",numfact);
                param.put("tipdoc",coddoc);
                param.put("empresa",VarGlobales.getCodEmpresa());
                
                jasper = new File(System.getProperty("user.dir")+"/build/classes/Reportes/Etiqueta_Caja.jasper");
            }
            
            if(origen.equals("EtiquetaCajaVarios")){
                String desde = "", hasta = "";
//                if (modelRepEtiquetaCaja.getjTxtDesde().getText().equals("")){
//                    desde = consultar("SELECT INV_NUMDOC FROM DNINVENTARIO WHERE INV_EMPRESA='000001' AND INV_NUMDOC<>'' GROUP BY INV_NUMDOC LIMIT 1").getString("INV_NUMDOC");
//                }else{
//                    desde = modelRepEtiquetaCaja.getjTxtDesde().getText();
//                }
//                if (modelRepEtiquetaCaja.getjTxtHasta().getText().equals("")){
//                    hasta = consultar("SELECT INV_NUMDOC FROM DNINVENTARIO WHERE INV_EMPRESA='000001' AND INV_NUMDOC<>'' GROUP BY INV_NUMDOC ORDER BY INV_NUMDOC DESC LIMIT 1").getString("INV_NUMDOC");
//                }else{
//                    hasta = modelRepEtiquetaCaja.getjTxtHasta().getText();
//                }
                
                param.put("numDesde",desde);
                param.put("numHasta",hasta);
                param.put("tipdoc",coddoc);
                param.put("empresa",VarGlobales.getCodEmpresa());
                
                jasper = new File(System.getProperty("user.dir")+"/build/classes/Reportes/Etiqueta6.jasper");
            }
            
            if(origen.equals("BillofLanding")){
                param.put("numdoc",numfact);
                param.put("empresa",VarGlobales.getCodEmpresa());
                
                jasper = new File(System.getProperty("user.dir")+"/build/classes/Reportes/BillofLanding.jasper");
            }
            if(origen.equals("Superclave")){
                System.out.println(empresa);
                System.out.println(codmae);
                param.put("empresa",empresa);
                param.put("usuario",codmae);
            
                jasper = new File(System.getProperty("user.dir")+"/build/classes/Reportes/carnet_supervisor.jasper");
            }
            JasperReport reporte = null;
            //reporte = (JasperReport) JRLoader.loadObjectFromLocation(System.getProperty("user.dir")+"/build/classes/Reportes/POSFactura.jasper");
            reporte=(JasperReport) JRLoader.loadObject(jasper);

            JasperPrint jasperprinter = JasperFillManager.fillReport(reporte,param,CONEXION);
            
            JasperViewer vista = new JasperViewer(jasperprinter,false);
            vista.setTitle(title);
            vista.setExtendedState(MAXIMIZED_BOTH);
            vista.setVisible(true);
        }catch(ClassNotFoundException | SQLException | JRException e){
            javax.swing.JOptionPane.showMessageDialog(null, e);
            System.err.println(e);
        }
    }
}