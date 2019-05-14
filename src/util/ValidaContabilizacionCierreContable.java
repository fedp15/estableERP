package util;

import Modelos.VariablesGlobales;
import static Vista.MenuPrincipal.escritorioERP;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ricardo
 */
public class ValidaContabilizacionCierreContable extends Modelos.conexion{
    private String sql = "";
    private final VariablesGlobales VarGlobales = VariablesGlobales.getDatosGlobales();
    private boolean lDocContabiliza, lAsientosCierre;
//    private AsientosContables asientosContables;

    public ValidaContabilizacionCierreContable(String numDoc, String tipDoc, String estadoDoc) {
        try {
            sql = "select doc_codigo,doc_descri,doc_contabiliza from dndocumentos where doc_empresa='"+VarGlobales.getCodEmpresa()+"' and "+
                  "doc_codigo='"+tipDoc+"' and doc_contabiliza=1";
            System.out.println(sql);
            
            rs = consultar(sql);
            
            if(rs.getRow()>0){
//                asientosContables = new AsientosContables(numDoc, tipDoc, estadoDoc);

                muestraForm();
            }else{
                lDocContabiliza = false;
                
                sql = "select inv_fecha, YEAR(inv_fecha) as ano, MONTH(inv_fecha) as mes from dninventario\n" +
                      "where inv_empresa='"+VarGlobales.getCodEmpresa()+"' and inv_coddoc='"+tipDoc+"' and inv_numdoc='"+numDoc+"'\n" +
                      "group by inv_fecha";
                
                rs = consultar(sql);
            
                if(rs.getRow()>0){
                    String ano =  rs.getString("ano");
                    String desde = rs.getString("ano")+"-"+rs.getString("mes")+"-01";
                    String dia = "";
                    switch(rs.getInt("mes")){
                        case 1:
                            dia = "31";
                            break;
                        case 2:
                            dia = "28";
                            break;
                        case 3:
                            dia = "31";
                            break;
                        case 4:
                            dia = "30";
                            break;
                        case 5:
                            dia = "31";
                            break;
                        case 6:
                            dia = "30";
                            break;
                        case 7:
                            dia = "31";
                            break;
                        case 8:
                            dia = "31";
                            break;
                        case 9:
                            dia = "30";
                            break;
                        case 10:
                            dia = "31";
                            break;
                        case 11:
                            dia = "30";
                            break;
                        case 12:
                            dia = "31";
                            break;
                    }
                    String hasta = rs.getString("ano")+"-"+rs.getString("mes")+"-"+dia;
                    
                    sql = "select * from cierres_contable where emp_codigo='"+VarGlobales.getCodEmpresa()+"' and "+
                          "fch_desde>='"+desde+"' and fch_hasta<='"+hasta+"' and cierre_fiscal="+ano+" and activo=1";
                
                    rs = consultar(sql);
                    
                    if(rs.getRow()>0){
//                        asientosContables = new AsientosContables(numDoc, tipDoc, estadoDoc, desde, hasta);

                        muestraForm();
                    }else{
                        JOptionPane.showMessageDialog(null, "Documento no Acepta Contabilización", "Notificacion", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ValidaContabilizacionCierreContable.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ValidaContabilizacionCierreContable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void muestraForm(){
//        try {
//            Dimension desktopSize = escritorioERP.getSize();
//            Dimension jInternalFrameSize = asientosContables.getSize();
//            asientosContables.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//            escritorioERP.add(asientosContables);
//            asientosContables.toFront();
//            asientosContables.setVisible(true);
//        } catch (Exception e) {
//            Dimension desktopSize = escritorio.getSize();
//            Dimension jInternalFrameSize = asientosContables.getSize();
//            asientosContables.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);
//
//            escritorio.add(asientosContables);
//            asientosContables.toFront();
//            asientosContables.setVisible(true);
//        }
    }
}
