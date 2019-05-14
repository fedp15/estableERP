package CallReport;

import Modelos.VariablesGlobales;
import Modelos.conexion;
import Reportes.DatasourceReportBalanceGeneral;
import Reportes.DatosReportBalaceGeneral;
import static java.awt.Frame.MAXIMIZED_BOTH;
import static Modelos.ReporteJas.CONEXION;
import Reportes.DatasourceReportBalanceComprobacion;
import Reportes.DatasourceReportLibroDiario;
import Reportes.DatasourceReportMayorAnalitico;
import Reportes.DatosReportBalaceComprobacion;
import Reportes.DatosReportLibroDiario;
import Reportes.DatosReportMayorAnalico;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import stored_procedure.sp_asientos_contables;
import stored_procedure.sp_balance_comprobacion;
import stored_procedure.sp_balance_general_estado_resultado;
import stored_procedure.sp_mayor_analitico;
import stored_procedure.sp_saldos_iniciales;

/**
 *
 * @author Ricardo
 */
public class HiloReportContabilidad extends conexion{
    private String codCategoria = "", SQL = "", campo = "", cta="", ctaAnt="", nivel, fchDesde,
            fchHasta, reporte;
    private String nombreCtaNivel1 = "", nombreCtaNivel2 = "", nombreCtaNivel3 = "", 
                   nombreCtaNivel4 = "", nombreCtaNivel5 = "", nombreCtaNivel6 = "", 
                   nombreCtaNivel7 = "", nombreCtaNivel8 = "";
    private String total1 = "", total2 = "", total3 = "", 
                   total4 = "", total5 = "", total6 = "", 
                   total7 = "", total8 = "";
    private String descripTotal_nivel_1 = "", descripTotal_nivel_2 = "",
                   descripTotal_nivel_3 = "", descripTotal_nivel_4 = "", 
                   descripTotal_nivel_5 = "", descripTotal_nivel_6 = "", 
                   descripTotal_nivel_7 = "", descripTotal_nivel_8 = "";
    private double saldoIni= 0.00;

    private DatosReportBalaceGeneral report;
    private DatasourceReportBalanceGeneral datasource = new DatasourceReportBalanceGeneral();
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private ResultSet rs, totalCta=null, rsDetMAyorAnal, rsSaldoInicial;
    private double totalActivos=0.00, totalPasivoCapital=0.00, totalEgresoIgresos=0.00;
    private int countCta = 0;

    public HiloReportContabilidad(String reporte, String nivel, String fchDesde, String fchHasta) {
        try {
            this.nivel= nivel;
            this.fchDesde = fchDesde;
            this.fchHasta = fchHasta;
            this.reporte  = reporte;
            
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
            
            switch (reporte) {
                case "Balance General":
                    {
                        BalanceGeneral balanceGeneral = new BalanceGeneral();
                        balanceGeneral.start();
                        break;
                    }
                case "Balance de Comprobacion":
                    BalanceComprobacion balanceComprobacion = new BalanceComprobacion();
                    balanceComprobacion.start();
                    break;
                case "Mayor Analitico":
                    MayorAnalitico mayorAnalitico = new MayorAnalitico();
                    mayorAnalitico.start();
                    break;
                case "Libro Diario":
                    LibroDiario libroDiario = new LibroDiario();
                    libroDiario.start();
                    break;
                case "Estado de Resultados":
                    {
                        BalanceGeneral balanceGeneral = new BalanceGeneral();
                        balanceGeneral.start();
                        break;
                    }
                default:
                    break;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getDescripcion(boolean lSaldoIni){
        try {
            if ((lSaldoIni ? rsSaldoInicial.getString("cta_nivel_1") : rs.getString("cta_nivel_1"))!=null ){
                try{
                    campo = "cta_nivel_1";
                    cta = (lSaldoIni ? rsSaldoInicial.getString(campo) : rs.getString(campo));
                    
                    SQL = "select cta_codigo as cta,cta_nombre as ctaNombre from dncta \n" +
                          "where cta_codigo='"+cta+"' and cta_empresa='"+VarGlobales.getCodEmpresa()+"' \n";
                    
                    totalCta = consultar(SQL);
                    
                    if(totalCta.getRow()>0){
                        nombreCtaNivel1 = totalCta.getString("ctaNombre");
                        descripTotal_nivel_1 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    try {
                        totalCta.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            if ((lSaldoIni ? rsSaldoInicial.getString("cta_nivel_2") : rs.getString("cta_nivel_2"))!=null ){
                try{
                    campo = "cta_nivel_2";
                    cta = (lSaldoIni ? rsSaldoInicial.getString(campo) : rs.getString(campo));
                    
                    SQL = "select cta_codigo as cta,cta_nombre as ctaNombre from dncta \n" +
                          "where cta_codigo='"+cta+"' and cta_empresa='"+VarGlobales.getCodEmpresa()+"' \n";
                    
                    totalCta = consultar(SQL);
                    
                    if(totalCta.getRow()>0){
                        nombreCtaNivel2 = totalCta.getString("ctaNombre");
                        descripTotal_nivel_2 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    try {
                        totalCta.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            if ((lSaldoIni ? rsSaldoInicial.getString("cta_nivel_3") : rs.getString("cta_nivel_3"))!=null ){
                try{
                    campo = "cta_nivel_3";
                    cta = (lSaldoIni ? rsSaldoInicial.getString(campo) : rs.getString(campo));
                    
                    SQL = "select cta_codigo as cta,cta_nombre as ctaNombre from dncta \n" +
                          "where cta_codigo='"+cta+"' and cta_empresa='"+VarGlobales.getCodEmpresa()+"' \n";
                    
                    totalCta = consultar(SQL);
                    
                    if(totalCta.getRow()>0){
                        nombreCtaNivel3 = totalCta.getString("ctaNombre");
                        descripTotal_nivel_3 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    try {
                        totalCta.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            if ((lSaldoIni ? rsSaldoInicial.getString("cta_nivel_4") : rs.getString("cta_nivel_4"))!=null ){
                try{
                    campo = "cta_nivel_4";
                    cta = (lSaldoIni ? rsSaldoInicial.getString(campo) : rs.getString(campo));
                    
                    SQL = "select cta_codigo as cta,cta_nombre as ctaNombre from dncta \n" +
                          "where cta_codigo='"+cta+"' and cta_empresa='"+VarGlobales.getCodEmpresa()+"' \n";
                    
                    totalCta = consultar(SQL);
                    
                    if(totalCta.getRow()>0){
                        nombreCtaNivel4 = totalCta.getString("ctaNombre");
                        descripTotal_nivel_4 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    try {
                        totalCta.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            if ((lSaldoIni ? rsSaldoInicial.getString("cta_nivel_5") : rs.getString("cta_nivel_5"))!=null ){
                try{
                    campo = "cta_nivel_5";
                    cta = (lSaldoIni ? rsSaldoInicial.getString(campo) : rs.getString(campo));
                    
                    SQL = "select cta_codigo as cta,cta_nombre as ctaNombre from dncta \n" +
                            "where cta_codigo='"+cta+"' and cta_empresa='"+VarGlobales.getCodEmpresa()+"' \n";
                    
                    totalCta = consultar(SQL);
                    
                    if(totalCta.getRow()>0){
                        nombreCtaNivel5 = totalCta.getString("ctaNombre");
                        descripTotal_nivel_5 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    try {
                        totalCta.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            if ((lSaldoIni ? rsSaldoInicial.getString("cta_nivel_6") : rs.getString("cta_nivel_6"))!=null ){
                try{
                    campo = "cta_nivel_6";
                    cta = (lSaldoIni ? rsSaldoInicial.getString(campo) : rs.getString(campo));
                    
                    SQL = "select cta_codigo as cta,cta_nombre as ctaNombre from dncta \n" +
                            "where cta_codigo='"+cta+"' and cta_empresa='"+VarGlobales.getCodEmpresa()+"' \n";
                    
                    totalCta = consultar(SQL);
                    
                    if(totalCta.getRow()>0){
                        nombreCtaNivel6 = totalCta.getString("ctaNombre");
                        descripTotal_nivel_6 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    try {
                        totalCta.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            if ((lSaldoIni ? rsSaldoInicial.getString("cta_nivel_7") : rs.getString("cta_nivel_7"))!=null ){
                try{
                    campo = "cta_nivel_7";
                    cta = (lSaldoIni ? rsSaldoInicial.getString(campo) : rs.getString(campo));
                    
                    SQL = "select cta_codigo as cta,cta_nombre as ctaNombre from dncta \n" +
                            "where cta_codigo='"+cta+"' and cta_empresa='"+VarGlobales.getCodEmpresa()+"' \n";
                    
                    totalCta = consultar(SQL);
                    
                    if(totalCta.getRow()>0){
                        nombreCtaNivel7 = totalCta.getString("ctaNombre");
                        descripTotal_nivel_7 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    try {
                        totalCta.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            if ((lSaldoIni ? rsSaldoInicial.getString("cta_nivel_8") : rs.getString("cta_nivel_8"))!=null ){
                try{
                    campo = "cta_nivel_8";
                    cta = (lSaldoIni ? rsSaldoInicial.getString(campo) : rs.getString(campo));
                    
                    SQL = "select cta_codigo as cta,cta_nombre as ctaNombre from dncta \n" +
                            "where cta_codigo='"+cta+"' and cta_empresa='"+VarGlobales.getCodEmpresa()+"' \n";
                    
                    totalCta = consultar(SQL);
                    
                    if(totalCta.getRow()>0){
                        nombreCtaNivel8 = totalCta.getString("ctaNombre");
                        descripTotal_nivel_8 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    try {
                        totalCta.close();
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getTotales(){
        int y = 0;
        
        for (y = 8; y>=1; y--){        
            try {
                //for (y=1;y<8;y++){
                campo = "cta_nivel_"+y;
                cta = rs.getString(campo);
                
                if (!cta.equals("") || cta!=null){
                    try{
                        //SQL = "select * from totales_cuentas_nivel_1 where cta='"+cta+"'";
                        SQL = "select asientos_contables."+campo+" as cta,dncta.cta_nombre as ctaNombre, dncta.cta_nivel as ctaNivel,\n"+
                              "(sum(Montos_Debe*signo)+sum(Montos_haber*signo)) as total \n" +
                              "from asientos_contables \n" +
                              "inner join dncta on cta_codigo=asientos_contables."+campo+" and aprobado=1\n" +
                              "where asientos_contables."+campo+"='"+cta+"' and comp_empresa='"+VarGlobales.getCodEmpresa()+"' \n";
                        
                        System.out.println(SQL);
                        totalCta = consultar(SQL);
                        
                        if(totalCta.getRow()>0){
                            switch(totalCta.getInt("ctaNivel")){
                                case 1:
                                    if (!ctaAnt.equals(cta)){
                                        ctaAnt=cta;
                                        countCta=0;
                                    }
                                    //total1 = totalCta.getString("cta")+" "+totalCta.getString("ctaNombre")+" "+totalCta.getString("total");
                                    nombreCtaNivel1 = totalCta.getString("ctaNombre");
                                    total1 = totalCta.getString("total");
                                    descripTotal_nivel_1 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                                    
                                    if (cta.equals("1")){
                                        countCta++;
                                        
                                        if (countCta==1){
                                            totalActivos=totalCta.getDouble("total");
                                        }
                                    }
                                    if (cta.equals("2") || cta.equals("3")){
                                        countCta++;
                                        
                                        if (countCta==1){
                                            totalPasivoCapital=totalPasivoCapital+totalCta.getDouble("total");
                                        }
                                    }
                                    if (cta.equals("4") || cta.equals("5") || cta.equals("6") || cta.equals("7") || cta.equals("8")){
                                        countCta++;
                                        
                                        if (countCta==1){
                                            totalEgresoIgresos=totalEgresoIgresos+totalCta.getDouble("total");
                                        }
                                    }
                                    break;
                                case 2:
                                    //total2 = totalCta.getString("cta")+" "+totalCta.getString("ctaNombre")+" "+totalCta.getString("total");
                                    nombreCtaNivel2 = totalCta.getString("ctaNombre");
                                    total2 = totalCta.getString("total");
                                    descripTotal_nivel_2 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                                    
                                    break;
                                case 3:
                                    //total3 = totalCta.getString("cta")+" "+totalCta.getString("ctaNombre")+" "+totalCta.getString("total");
                                    nombreCtaNivel3 = totalCta.getString("ctaNombre");
                                    total3 = totalCta.getString("total");
                                    descripTotal_nivel_3 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                                    
                                    break;
                                case 4:
                                    //total4 = totalCta.getString("cta")+" "+totalCta.getString("ctaNombre")+" "+totalCta.getString("total");
                                    nombreCtaNivel4 = totalCta.getString("ctaNombre");
                                    total4 = totalCta.getString("total");
                                    descripTotal_nivel_4 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                                    
                                    break;
                                case 5:
                                    //total5 = totalCta.getString("cta")+" "+totalCta.getString("ctaNombre")+" "+totalCta.getString("total");
                                    nombreCtaNivel5 = totalCta.getString("ctaNombre");
                                    total5 = totalCta.getString("total");
                                    descripTotal_nivel_5 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                                    
                                    break;
                                case 6:
                                    //total6 = totalCta.getString("cta")+" "+totalCta.getString("ctaNombre")+" "+totalCta.getString("total");
                                    nombreCtaNivel6 = totalCta.getString("ctaNombre");
                                    total6 = totalCta.getString("total");
                                    descripTotal_nivel_6 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                                    
                                    break;
                                case 7:
                                    //total7 = totalCta.getString("cta")+" "+totalCta.getString("ctaNombre")+" "+totalCta.getString("total");
                                    nombreCtaNivel7 = totalCta.getString("ctaNombre");
                                    total7 = totalCta.getString("total");
                                    descripTotal_nivel_7 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                                    
                                    break;
                                case 8:
                                    //total8 = totalCta.getString("cta")+" "+totalCta.getString("ctaNombre")+" "+totalCta.getString("total");
                                    nombreCtaNivel8 = totalCta.getString("ctaNombre");
                                    total8 = totalCta.getString("total");
                                    descripTotal_nivel_8 = "Total de "+totalCta.getString("cta")+" "+totalCta.getString("ctaNombre");
                                    
                                    break;
                            }
                        }
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                    }finally{
                        try {
                            totalCta.close();
                            conn.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e){
                Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    public class BalanceGeneral extends Thread{
        @Override
        public void run(){  
            String sqlSaldosIniciales = "";
            
            try{
                if (reporte.equals("Balance General")){
                    SQL = "select comp_fecha,cta, ctaNombre, ctaNivel, aprobado, sum(total) as total,\n" +
                          "       cta_nivel_1, cta_nivel_2, cta_nivel_3, cta_nivel_4, cta_nivel_5, cta_nivel_6, cta_nivel_7, \n" +
                          "       cta_nivel_8 from balance_general\n" +
                          "where comp_fecha<='"+fchHasta+"' and comp_empresa='"+VarGlobales.getCodEmpresa()+"' \n" +
                          "group by cta\n" +
                          "order by cta ";
                    
                     sqlSaldosIniciales = "select comp_fecha,cta,ctaNombre,ctaNivel, sum(saldoInicial) as saldoInicial, \n" +
                                          "       (sum(totalDebe)+sum(totalHaber)) as total, \n" +
                                          "       (sum(totalDebe)+sum(totalHaber)+ sum(saldoInicial)) as saldoInicialFinal,\n" +
                                          "       cta_nivel_1, cta_nivel_2, cta_nivel_3, cta_nivel_4, cta_nivel_5, cta_nivel_6, cta_nivel_7, \n" +
                                          "       cta_nivel_8\n"+
                                          "from balance_comprobacion\n" +
                                          "where cta_nivel_1 <=3 and comp_fecha<='"+fchHasta+"' and comp_empresa='"+VarGlobales.getCodEmpresa()+"' \n" +
                                          "group by cta";
                     
                    sp_balance_general_estado_resultado spBalanceGeneral = new sp_balance_general_estado_resultado();
                    rs = spBalanceGeneral.getBalanceGeneral(VarGlobales.getCodEmpresa(), "", "", 0, "", "", "", "Balance General", fchHasta);
                    
                    sp_saldos_iniciales spSaldosIniciales = new sp_saldos_iniciales();
                    rsSaldoInicial = spSaldosIniciales.getBalanceGeneral(VarGlobales.getCodEmpresa(), 0, 3, "", "", "SaldoInicialBalanceG", fchHasta);
                }else if (reporte.equals("Estado de Resultados")){
                    SQL = "select comp_fecha,cta, ctaNombre, ctaNivel, aprobado, sum(total) as total,\n" +
                          "       cta_nivel_1, cta_nivel_2, cta_nivel_3, cta_nivel_4, cta_nivel_5, cta_nivel_6, cta_nivel_7, \n" +
                          "       cta_nivel_8 from estado_resultados\n" +
                          "where comp_fecha<='"+fchHasta+"' and comp_empresa='"+VarGlobales.getCodEmpresa()+"' \n" +
                          "group by cta\n" +
                          "order by cta ";
                    
                    sqlSaldosIniciales = "select comp_fecha,cta,ctaNombre,ctaNivel, sum(saldoInicial) as saldoInicial, \n" +
                                         "       (sum(totalDebe)+sum(totalHaber)) as total, \n" +
                                         "       (sum(totalDebe)+sum(totalHaber)+ sum(saldoInicial)) as saldoInicialFinal,\n" +
                                         "       cta_nivel_1, cta_nivel_2, cta_nivel_3, cta_nivel_4, cta_nivel_5, cta_nivel_6, cta_nivel_7, \n" +
                                         "       cta_nivel_8\n"+
                                         "from balance_comprobacion\n" +
                                         "where cta_nivel_1 >=4 and comp_fecha<='"+fchHasta+"' and comp_empresa='"+VarGlobales.getCodEmpresa()+"' \n" +
                                         "group by cta";
                    
                    sp_balance_general_estado_resultado spBalanceGeneral = new sp_balance_general_estado_resultado();
                    rs = spBalanceGeneral.getBalanceGeneral(VarGlobales.getCodEmpresa(), "", "", 1, "", "", "", "Estado de Resultados", fchHasta);
                    
                    sp_saldos_iniciales spSaldosIniciales = new sp_saldos_iniciales();
                    rsSaldoInicial = spSaldosIniciales.getBalanceGeneral(VarGlobales.getCodEmpresa(), 1, 4, "", "", "SaldoInicialEstadoR", fchHasta);
                }

                //rsSaldoInicial = consultar(sqlSaldosIniciales);
                
                System.out.println(SQL);
                //rs = consultar(SQL);
            
                int i=0, countRegSaldoIni = 0, countRegSalIni = rsSaldoInicial.getRow(), recorRegSalIni = 0;
                boolean contSaldoIni = false;
                if(rs.getRow()>0){
                    rs.beforeFirst();
                    
                    if(rsSaldoInicial.getRow()>0){
                        countRegSaldoIni = rsSaldoInicial.getRow();
                        rsSaldoInicial.beforeFirst();
                    }
                
                    while(rs.next()){
                        i++;
                        saldoIni= 0.00;
                        contSaldoIni = false;
                        
                        if (countRegSaldoIni>=i){
                            rsSaldoInicial.next();
                            recorRegSalIni++;
                            saldoIni = rsSaldoInicial.getDouble("saldoInicial");
                            
                            if (rs.getString("cta").trim().equals(rsSaldoInicial.getString("cta").trim())){
                            }else{
                                report = new DatosReportBalaceGeneral();
                                getDescripcion(true);
                                System.out.println(rsSaldoInicial.getString("cta").trim()+" - "+rs.getString("cta").trim());
                                insertReport(true);
                                
                                rsSaldoInicial.next();
                                recorRegSalIni++;
                                saldoIni = rsSaldoInicial.getDouble("saldoInicial");
                                
                                if (rs.getString("cta").trim().equals(rsSaldoInicial.getString("cta").trim())){
                                }else{
                                    contSaldoIni = true;
                                    rs.previous();
                                    i--;
                                }
                            }
                            System.out.println(rsSaldoInicial.getString("cta").trim()+" - "+rs.getString("cta").trim());
                        }else{
                            //lsaldoIni = false;
                        }
                    
                        //if (!rs.getString("cta").substring(0, 1).equals("4") && !rs.getString("cta").substring(0, 1).equals("5") && 
                        //       !rs.getString("cta").substring(0, 1).equals("6") && !rs.getString("cta").substring(0, 1).equals("7") &&
                        //       !rs.getString("cta").substring(0, 1).equals("8")){
                            
                            //getTotales();
                            report = new DatosReportBalaceGeneral();    
                            if (contSaldoIni){
                                getDescripcion(true);
                                insertReport(true);
                            }else{
                                getDescripcion(false);
                                insertReport(false);
                            }
                        //}
                    }
                }

                if (countRegSalIni > recorRegSalIni){
                    saldoIni= 0.00;
                    contSaldoIni = false;
                    
                    while(rsSaldoInicial.next()){
                        recorRegSalIni++;
                        saldoIni = rsSaldoInicial.getDouble("saldoInicial");
                        
                        report = new DatosReportBalaceGeneral();
                        getDescripcion(true);
                        insertReport(true);
                    }
                }

                File jasper = new File(System.getProperty("user.dir")+"/build/classes/Reportes/BalanceGeneral.jasper");
            
                JasperReport reporteEti;  
                reporteEti = (JasperReport) JRLoader.loadObject(jasper);
           
                 //Declaración de Parametros para consulta en IReport            
                JasperPrint jasperprinter = JasperFillManager.fillReport(reporteEti, null, datasource);  
            
                JasperViewer vista = new JasperViewer(jasperprinter, false);
                if (reporte.equals("Balance General")){
                    vista.setTitle("Balance General");
                }else if (reporte.equals("Estado de Resultados")){
                    vista.setTitle("Estado de Resultados");
                }
                vista.setExtendedState(MAXIMIZED_BOTH);
                vista.setVisible(true);
            }catch(SQLException | JRException e){
                Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, e);
                javax.swing.JOptionPane.showMessageDialog(null, e);
                System.err.println(e.getMessage());
            }finally{
                try {
                    rs.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        private void insertReport(boolean regSaldoIni){
            if (regSaldoIni){
                try {
                    report.DatosReportVentas(Integer.valueOf(nivel), rsSaldoInicial.getString("cta"),
                            rsSaldoInicial.getString("ctaNombre"), rsSaldoInicial.getString("cta_nivel_1"),
                            rsSaldoInicial.getString("cta_nivel_2"), rsSaldoInicial.getString("cta_nivel_3"),
                            rsSaldoInicial.getString("cta_nivel_4"), rsSaldoInicial.getString("cta_nivel_5"),
                            rsSaldoInicial.getString("cta_nivel_6"), rsSaldoInicial.getString("cta_nivel_7"),
                            rsSaldoInicial.getString("cta_nivel_8"), saldoIni,
                            total1, total2, total3, total4, total5, total6,
                            total7, total8, descripTotal_nivel_1, descripTotal_nivel_2,
                            descripTotal_nivel_3, descripTotal_nivel_4, descripTotal_nivel_5,
                            descripTotal_nivel_6, descripTotal_nivel_7,descripTotal_nivel_8,
                            nombreCtaNivel1, nombreCtaNivel2, nombreCtaNivel3, nombreCtaNivel4,
                            nombreCtaNivel5, nombreCtaNivel6, nombreCtaNivel7, nombreCtaNivel8,
                            reporte,totalActivos,totalPasivoCapital,totalEgresoIgresos);
                    
                    datasource.addRegistros(report);
                } catch (SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                try {
                    report.DatosReportVentas(Integer.valueOf(nivel), rs.getString("cta"),
                            rs.getString("ctaNombre"), rs.getString("cta_nivel_1"),
                            rs.getString("cta_nivel_2"), rs.getString("cta_nivel_3"),
                            rs.getString("cta_nivel_4"), rs.getString("cta_nivel_5"),
                            rs.getString("cta_nivel_6"), rs.getString("cta_nivel_7"),
                            rs.getString("cta_nivel_8"), rs.getDouble("total")+saldoIni,
                            total1, total2, total3, total4, total5, total6,
                            total7, total8, descripTotal_nivel_1, descripTotal_nivel_2,
                            descripTotal_nivel_3, descripTotal_nivel_4, descripTotal_nivel_5,
                            descripTotal_nivel_6, descripTotal_nivel_7,descripTotal_nivel_8,
                            nombreCtaNivel1, nombreCtaNivel2, nombreCtaNivel3, nombreCtaNivel4,
                            nombreCtaNivel5, nombreCtaNivel6, nombreCtaNivel7, nombreCtaNivel8,
                            reporte,totalActivos,totalPasivoCapital,totalEgresoIgresos);
                    
                    datasource.addRegistros(report);
                } catch (SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public class BalanceComprobacion extends Thread{
        @Override
        public void run(){  
            try{
                DatasourceReportBalanceComprobacion datasource = new DatasourceReportBalanceComprobacion();

                SQL = "select comp_fecha,cta,ctaNombre,ctaNivel,sum(saldoInicial) as saldoInicial,sum(totalDebe) as totalDebe,\n" +
                      "       sum(totalHaber) as totalHaber,sum(saldoActual) as saldoActual,\n" +
                      "       cta_nivel_1,cta_nivel_2,cta_nivel_3,cta_nivel_4,cta_nivel_5,cta_nivel_6,cta_nivel_7,\n" +
                      "		 cta_nivel_8 from balance_comprobacion\n" +
                      "where comp_fecha>='"+fchDesde+"' and comp_fecha<='"+fchHasta+"' and comp_empresa='"+VarGlobales.getCodEmpresa()+"' \n"+
                      "group by cta";
                
                sp_balance_comprobacion spBalanceComprobacion = new sp_balance_comprobacion();
                rs = spBalanceComprobacion.getBalanceComprobacion(VarGlobales.getCodEmpresa(), 1, "", "", "", 
                                                                  fchDesde, fchHasta);
                //rs = consultar(SQL);

                String sqlSaldosIniciales = "select comp_fecha,cta,ctaNombre,ctaNivel, sum(saldoInicial) as saldoInicial, \n" +
                                            "       (sum(totalDebe)+sum(totalHaber)) as total, \n" +
                                            "       (sum(totalDebe)+sum(totalHaber)+ sum(saldoInicial)) as saldoInicialFinal\n" +
                                            "from balance_comprobacion\n" +
                                            "where comp_fecha<'"+fchDesde+"' and comp_empresa='"+VarGlobales.getCodEmpresa()+"' \n" +
                                            "group by cta";
                    
                sp_saldos_iniciales spSaldosIniciales = new sp_saldos_iniciales();
                rsSaldoInicial = spSaldosIniciales.getBalanceGeneral(VarGlobales.getCodEmpresa(), 1, 4, "", "", "SaldoInicialBalanceC", fchDesde);
                //rsSaldoInicial = consultar(sqlSaldosIniciales);
            
                int i=0, countRegSaldoIni = 0; boolean lsaldoIni = false;
                if(rs.getRow()>0){
                    rs.beforeFirst();
                
                    if(rsSaldoInicial.getRow()>0){
                        countRegSaldoIni = rsSaldoInicial.getRow();
                        rsSaldoInicial.beforeFirst();
                        lsaldoIni = true;
                        //saldoIni = rsSaldoInicial.getDouble("saldoInicialFinal");
                    }else{
                        lsaldoIni = false;
                        //saldoIni = rs.getDouble("saldoInicial");
                    }
                    
                    while(rs.next()){
                        i++;
                        saldoIni = 0.00;
                        
                        if (countRegSaldoIni>=i){
                            rsSaldoInicial.next();
                            //saldoIni = rsSaldoInicial.getDouble("saldoInicial");
                            saldoIni = rsSaldoInicial.getDouble("saldoInicialFinal");
                        }else{
                            lsaldoIni = false;
                        }

                        DatosReportBalaceComprobacion report = new DatosReportBalaceComprobacion();
                            
                        //getTotales();
                        getDescripcion(false);
                        
                        report.DatosReportVentas(Integer.valueOf(nivel), rs.getString("cta"),
                                                 rs.getString("ctaNombre"), rs.getString("cta_nivel_1"),
                                                 rs.getString("cta_nivel_2"), rs.getString("cta_nivel_3"),
                                                 rs.getString("cta_nivel_4"), rs.getString("cta_nivel_5"),
                                                 rs.getString("cta_nivel_6"), rs.getString("cta_nivel_7"),
                                                 rs.getString("cta_nivel_8"), rs.getDouble("saldoActual"),
                                                 total1, total2, total3, total4, total5, total6,
                                                 total7, total8, descripTotal_nivel_1, descripTotal_nivel_2,
                                                 descripTotal_nivel_3, descripTotal_nivel_4, descripTotal_nivel_5,
                                                 descripTotal_nivel_6, descripTotal_nivel_7,descripTotal_nivel_8,
                                                 nombreCtaNivel1, nombreCtaNivel2, nombreCtaNivel3, nombreCtaNivel4,
                                                 nombreCtaNivel5, nombreCtaNivel6, nombreCtaNivel7, nombreCtaNivel8,
                                                 (lsaldoIni ? rsSaldoInicial.getDouble("saldoInicialFinal") : rs.getDouble("saldoInicial")),
                                                 //saldoIni,
                                                 rs.getDouble("totalDebe"), 
                                                 rs.getDouble("totalHaber"),totalActivos,totalPasivoCapital,
                                                 totalEgresoIgresos);
                        
                        datasource.addRegistros(report);
                    }
                }  

                File jasper = new File(System.getProperty("user.dir")+"/build/classes/Reportes/BalanceComprobacion.jasper");
            
                JasperReport reporteEti;  
                reporteEti = (JasperReport) JRLoader.loadObject(jasper);
           
                 //Declaración de Parametros para consulta en IReport            
                JasperPrint jasperprinter = JasperFillManager.fillReport(reporteEti, null, datasource);  
            
                JasperViewer vista = new JasperViewer(jasperprinter, false);
                vista.setTitle("Balance de Comprobación");
                vista.setExtendedState(MAXIMIZED_BOTH);
                vista.setVisible(true);
            }catch(SQLException | JRException e){
                javax.swing.JOptionPane.showMessageDialog(null, e);
            }finally{
                try {
                    rs.close();
                    rsSaldoInicial.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public class MayorAnalitico extends Thread{
        @Override
        public void run(){  
            try{
                DatasourceReportMayorAnalitico datasource = new DatasourceReportMayorAnalitico();

//                SQL = "select comp_fecha,cta,ctaNombre,ctaNivel,sum(saldoInicial) as saldoInicial,sum(totalDebe) as totalDebe,\n" +
//                      "       sum(totalHaber) as totalHaber,sum(saldoActual) as saldoActual,\n" +
//                      "       cta_nivel_1,cta_nivel_2,cta_nivel_3,cta_nivel_4,cta_nivel_5,cta_nivel_6,cta_nivel_7,\n" +
//                      "       cta_nivel_8 from mayor_analitico\n" +
//                      "where comp_fecha>='"+fchDesde+"' and comp_fecha<='"+fchHasta+"' and comp_empresa='"+VarGlobales.getCodEmpresa()+"' \n"+
//                      "group by cta order by cta";
//                rs = consultar(SQL);

                sp_mayor_analitico spMayorAnalitico = new sp_mayor_analitico();
                rs = spMayorAnalitico.getMayorAnalitico(VarGlobales.getCodEmpresa(), 1, fchDesde, fchHasta);
            
                 int i=0;
                if(rs.getRow()>0){
                    rs.beforeFirst();
                    
                    while(rs.next()){
                        i++;

                        DatosReportMayorAnalico report = new DatosReportMayorAnalico();
                        
                        //getTotales();
                        getDescripcion(false);

                        report.DatosReportMayorAnalico(Integer.valueOf(nivel), rs.getString("cta"),
                                                       rs.getString("ctaNombre"), rs.getString("cta_nivel_1"),
                                                       rs.getString("cta_nivel_2"), rs.getString("cta_nivel_3"),
                                                       rs.getString("cta_nivel_4"), rs.getString("cta_nivel_5"),
                                                       rs.getString("cta_nivel_6"), rs.getString("cta_nivel_7"),
                                                       rs.getString("cta_nivel_8"), rs.getDouble("saldoActual"),
                                                       total1, total2, total3, total4, total5, total6,
                                                       total7, total8, descripTotal_nivel_1, descripTotal_nivel_2,
                                                       descripTotal_nivel_3, descripTotal_nivel_4, descripTotal_nivel_5,
                                                       descripTotal_nivel_6, descripTotal_nivel_7,descripTotal_nivel_8,
                                                       nombreCtaNivel1, nombreCtaNivel2, nombreCtaNivel3, nombreCtaNivel4,
                                                       nombreCtaNivel5, nombreCtaNivel6, nombreCtaNivel7, nombreCtaNivel8,
                                                       rs.getDouble("saldoInicial"), rs.getDouble("totalDebe"), 
                                                       rs.getDouble("totalHaber"), "", "", "", "", 0.00, 0.00, 0.00, 0.00,
                                                       totalActivos,totalPasivoCapital,
                                                       totalEgresoIgresos);
                        
                        datasource.addRegistros(report);
 
                        try {                            
//                            SQL = "select cta,compcontab,comp_fecha,inv_numdoc, concat(inv_coddoc,' ',doc_descri,' ',ccont_transaccion) as descrip, \n" +
//                                  "       saldoInicial, totalDebe, totalHaber, saldoInicial+totalDebe+totalHaber as SaldoFinal from mayor_analitico\n"+
//                                  "where cta='"+rs.getString("cta")+"' and (comp_fecha>='"+fchDesde+"' and comp_fecha<='"+fchHasta+"') and "+
//                                  "comp_empresa='"+VarGlobales.getCodEmpresa()+"'\n"+
//                                  "order by compcontab";
                            
                            creaConexion();
                            SQL = "CALL sp_mayor_analico_detalle('"+VarGlobales.getCodEmpresa()+"', 1, '"+fchDesde+"', '"+fchHasta+"','"+rs.getString("cta")+"')";
                            CallableStatement stmt = conn.prepareCall(SQL,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    
                            rsDetMAyorAnal = stmt.executeQuery();
                            rsDetMAyorAnal.last();
//                            rsDetMAyorAnal = consultar(SQL);
            
                            if(rsDetMAyorAnal.getRow()>0){
                                rsDetMAyorAnal.beforeFirst();
                
                                while(rsDetMAyorAnal.next()){
                                    report = new DatosReportMayorAnalico();

                                    if (rsDetMAyorAnal.getString("saldoInicial").equals("0.00") && rsDetMAyorAnal.getString("totalDebe").equals("0.00") && 
                                            rsDetMAyorAnal.getString("totalHaber").equals("0.00")){
                                    
                                    }else{
                                        report.DatosReportMayorAnalico(Integer.valueOf(nivel), "",
                                                                       "", rs.getString("cta_nivel_1"),
                                                                       rs.getString("cta_nivel_2"), rs.getString("cta_nivel_3"),
                                                                       rs.getString("cta_nivel_4"), rs.getString("cta_nivel_5"),
                                                                       rs.getString("cta_nivel_6"), rs.getString("cta_nivel_7"),
                                                                       rs.getString("cta_nivel_8"), rs.getDouble("saldoActual"),
                                                                       total1, total2, total3, total4, total5, total6,
                                                                       total7, total8, descripTotal_nivel_1, descripTotal_nivel_2,
                                                                       descripTotal_nivel_3, descripTotal_nivel_4, descripTotal_nivel_5,
                                                                       descripTotal_nivel_6, descripTotal_nivel_7,descripTotal_nivel_8,
                                                                       nombreCtaNivel1, nombreCtaNivel2, nombreCtaNivel3, nombreCtaNivel4,
                                                                       nombreCtaNivel5, nombreCtaNivel6, nombreCtaNivel7, nombreCtaNivel8,
                                                                       0.00, rs.getDouble("totalDebe"), rs.getDouble("totalHaber"),
                                                                       rsDetMAyorAnal.getString("compcontab"), rsDetMAyorAnal.getString("comp_fecha"), 
                                                                       rsDetMAyorAnal.getString("inv_numdoc"), rsDetMAyorAnal.getString("descrip"), 
                                                                       rsDetMAyorAnal.getDouble("saldoInicial"), rsDetMAyorAnal.getDouble("totalDebe"), 
                                                                       rsDetMAyorAnal.getDouble("totalHaber"), rsDetMAyorAnal.getDouble("SaldoFinal"), 
                                                                       totalActivos,totalPasivoCapital, totalEgresoIgresos);

                                        datasource.addRegistros(report);
                                    }
                                }
                            }
                        } catch (Exception e) {
                        
                        }finally{
                            try {
                                rsDetMAyorAnal.close();
                                conn.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }  

                File jasper = new File(System.getProperty("user.dir")+"/build/classes/Reportes/MayorAnalitico.jasper");
            
                JasperReport reporteEti;  
                reporteEti = (JasperReport) JRLoader.loadObject(jasper);
           
                 //Declaración de Parametros para consulta en IReport            
                JasperPrint jasperprinter = JasperFillManager.fillReport(reporteEti, null, datasource);  
            
                JasperViewer vista = new JasperViewer(jasperprinter, false);
                vista.setTitle("Mayor Analitico");
                vista.setExtendedState(MAXIMIZED_BOTH);
                vista.setVisible(true);
            }catch(SQLException | JRException e){
                javax.swing.JOptionPane.showMessageDialog(null, e);
            }finally{
                try {
                    rs.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public class LibroDiario extends Thread{
        @Override
        public void run(){  
            try{
                DatasourceReportLibroDiario datasource = new DatasourceReportLibroDiario();

//                SQL = "select compcontab,inv_coddoc,inv_numdoc,cuenta,cta_nombre, Descripcion, if(Montos_Debe>0, Montos_Debe*signo, '') as Debe, \n"+
//                      "       if(Montos_Haber>0, Montos_Haber*signo, '') as Haber,\n" +
//                      "       comp_fecha as fecha, if(inv_activo=1, 'Activo', 'Anulado') as StatusDoc, Aprobado from asientos_contables\n" +
//                      "where comp_fecha>='"+fchDesde+"' and comp_fecha<='"+fchHasta+"' and comp_empresa='"+VarGlobales.getCodEmpresa()+"' \n"+
//                      "order by compcontab,Haber,Debe";
//                System.out.println(SQL);
                
                sp_asientos_contables spAsientos = new sp_asientos_contables();
                rs = spAsientos.getAsientos(VarGlobales.getCodEmpresa(), "", "", 1, "Libro Diario", "", "", "0", fchDesde, fchHasta);
//                rs = consultar(SQL);
            
                int i=0;
                if(rs.getRow()>0){
                    rs.beforeFirst();
                    
                    while(rs.next()){
                        i++;

                        DatosReportLibroDiario report = new DatosReportLibroDiario();

                        report.DatosReportLibroDiario(Integer.valueOf(nivel), rs.getString("cuenta"),
                                                       rs.getString("cta_nombre"), rs.getString("fecha"),
                                                       rs.getString("compcontab"), rs.getString("inv_numdoc"),
                                                       rs.getString("inv_coddoc"), rs.getString("Descripcion"),
                                                       rs.getDouble("Debe"), rs.getDouble("Haber"));
                        
                        datasource.addRegistros(report);
                    }
                }  

                File jasper = new File(System.getProperty("user.dir")+"/build/classes/Reportes/LibroDiario.jasper");
            
                JasperReport reporteEti;  
                reporteEti = (JasperReport) JRLoader.loadObject(jasper);
           
                 //Declaración de Parametros para consulta en IReport            
                JasperPrint jasperprinter = JasperFillManager.fillReport(reporteEti, null, datasource);  
            
                JasperViewer vista = new JasperViewer(jasperprinter, false);
                vista.setTitle("Libro Diario");
                vista.setExtendedState(MAXIMIZED_BOTH);
                vista.setVisible(true);
            }catch(SQLException | JRException e){
                javax.swing.JOptionPane.showMessageDialog(null, e);
            }finally{
                try {
                    rs.close();
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(HiloReportContabilidad.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}