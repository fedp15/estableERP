package Modelos;

import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andres
 */
public class ModelMenuERP extends conexion{
    private static ModelMenuERP modelMenuERP;
    private Object aThis;
    private boolean lReloadMenu = true;
    
    public ModelMenuERP() {
    }
    
    public static ModelMenuERP getModelMenuERP(){
        if (modelMenuERP == null){
            modelMenuERP = new ModelMenuERP();
        }

        return modelMenuERP;
    }

    public Object getVista() {
        return aThis;
    }

    public void setVista(Object aThis) {
        this.aThis = aThis;
    }

    public boolean islReloadMenu() {
        return lReloadMenu;
    }

    public void setlReloadMenu(boolean lReloadMenu) {
        this.lReloadMenu = lReloadMenu;
    }
    
    public ResultSet buscaCbtProcesando(){
        ResultSet repuestaConsulta = null;
        
        try {
            repuestaConsulta = consultar("SELECT * FROM status_cbtes_electronicos \n"+
                                         "INNER JOIN dninventario ON inv_numdoc=numdoc AND inv_empresa='"+VarGlobales.getCodEmpresa()+"'\n"+
                                         "WHERE codEmpresa='"+VarGlobales.getCodEmpresa()+"' AND (ind_estado='procesando' OR ind_estado='Pendiente') AND ISNULL(numdoc_orig)");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModelMenuERP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return repuestaConsulta;
    }    
}
