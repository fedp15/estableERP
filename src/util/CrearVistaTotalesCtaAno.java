package util;

import Modelos.VariablesGlobales;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo
 */
public class CrearVistaTotalesCtaAno extends Modelos.conexion{
    private ResultSet rs = null, rsCampos = null;
    private String SQL, construyeVistaBalanceGeneral;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private SQLQuerys insertarconex = null;

    public CrearVistaTotalesCtaAno() {
        insertarconex = new SQLQuerys();
    }
    
    public void armarVista(){
        try {
            int orden = 1;

            SQL = "select comp_empresa,cuenta as cta, cta_nombre as ctaNombre, cta_nivel as ctaNivel,\n" +
                  "       sum(Montos_Debe*signo) as debe, sum(Montos_haber*signo) as haber,\n" +
                  "		 (sum(Montos_Debe*signo)+sum(Montos_haber*signo)) as saldoFinal,\n" +
                  "		 (sum(Montos_Debe*signo)+sum(Montos_haber*signo))*-1 as saldoFinalContrario,\n" +
                  "		 MONTH(comp_fecha) as mes,YEAR(comp_fecha) as ano, comp_fecha\n" +
                  "from asientos_contables \n" +
                  "where aprobado = 1 and doc_saldo_inicial=0\n" +
                  "group by cuenta,YEAR(comp_fecha)\n" +
                  "order by ano,mes ";
            System.out.println(SQL);
            
                
            String deleteView = "DROP VIEW if EXISTS totalizador_ctas_ano";
            insertarconex.SqlInsert(deleteView);
                
            String createView = "CREATE VIEW totalizador_ctas_ano AS\n"+SQL;
            insertarconex.SqlInsert(createView);
        } catch (Exception ex) {
            Logger.getLogger(CrearVistaTotalesCtaAno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}