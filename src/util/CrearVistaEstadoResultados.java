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
public class CrearVistaEstadoResultados extends Modelos.conexion{
    private ResultSet rs = null, rsCampos = null;
    private String SQL, construyeVistaBalanceGeneral;
    
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private SQLQuerys insertarconex = null;

    public CrearVistaEstadoResultados() {
        insertarconex = new SQLQuerys();
    }
    
    public void armarVista(){
        try {
            int orden = 1;

            SQL = "select comp_empresa,comp_fecha,cuenta as cta, cta_nombre as ctaNombre, cta_nivel as ctaNivel, aprobado, \n" +
                  "       (sum(Montos_Debe*signo)+sum(Montos_haber*signo)) as total, \n" +
                  "       SUBSTRING(asientos_contables.cta_nivel_2, 1,1) as cta_nivel_1,\n" +
                  "	  cta_nivel_2, cta_nivel_3, cta_nivel_4, cta_nivel_5, cta_nivel_6, cta_nivel_7, \n" +
                  "	  cta_nivel_8 from asientos_contables \n" +
                  "where cta_nivel_1 >=4 and aprobado = 1 and doc_saldo_inicial=0\n" +
                  "group by cuenta,comp_empresa\n" +
                  "order by cuenta ";
            System.out.println(SQL);
            
                
            String deleteView = "DROP VIEW if EXISTS estado_resultados";
            insertarconex.SqlInsert(deleteView);
                
            String createView = "CREATE VIEW estado_resultados AS\n"+SQL;
            insertarconex.SqlInsert(createView);
        } catch (Exception ex) {
            Logger.getLogger(CrearVistaEstadoResultados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}