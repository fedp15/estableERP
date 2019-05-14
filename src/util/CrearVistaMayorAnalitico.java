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
public class CrearVistaMayorAnalitico extends Modelos.conexion{
    private ResultSet rs = null, rsCampos = null;
    private String SQL, construyeVistaBalanceGeneral;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private SQLQuerys insertarconex = null;

    public CrearVistaMayorAnalitico() {
        insertarconex = new SQLQuerys();
    }
    
    public void armarVista(){
        try {
            int orden = 1;

            SQL = "select comp_empresa,ccont_transaccion,comp_fecha,MONTH(comp_fecha) as mes,YEAR(comp_fecha) as ano,cuenta as cta, cta_nombre as ctaNombre, \n" +
                  "       cta_nivel as ctaNivel, cuenta, inv_coddoc, doc_descri, inv_numdoc, compcontab,codPersona,nombPersona, \n"+
                  "       saldoInicial, \n" +
                  "       if(doc_saldo_inicial=0,sum(Montos_Debe*signo),0) as totalDebe, \n" +
                  "       if(doc_saldo_inicial=0,sum(Montos_haber*signo),0) as totalHaber,(sum(Montos_Debe*signo)+sum(Montos_haber*signo)) as saldoActual, \n" +
                  "       cta_nivel, SUBSTRING(asientos_contables.cta_nivel_2, 1,1) as cta_nivel_1,\n" +
                  "		 1 as numNivel_1, cta_nivel_2, 2 as numNivel_2,cta_nivel_3, 3 as numNivel_3,\n" +
                  "		 cta_nivel_4, 4 as numNivel_4,cta_nivel_5, 5 as numNivel_5,cta_nivel_6, 6 as numNivel_6,\n" +
                  "		 cta_nivel_7, 7 as numNivel_7,cta_nivel_8, 8 as numNivel_8 from asientos_contables \n" +
                  "where aprobado = 1\n" +
                  "group by cuenta,inv_coddoc,DAY(comp_fecha),ccont_transaccion,compcontab\n" +
                  "order by ano,mes ";
            System.out.println(SQL);
            
                
            String deleteView = "DROP VIEW if EXISTS mayor_analitico";
            insertarconex.SqlInsert(deleteView);
                
            String createView = "CREATE VIEW mayor_analitico AS\n"+SQL;
            insertarconex.SqlInsert(createView);
        } catch (Exception ex) {
            Logger.getLogger(CrearVistaMayorAnalitico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}