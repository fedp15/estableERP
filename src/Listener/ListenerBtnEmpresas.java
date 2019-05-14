
package Listener;

import Modelos.ModelEmpresas;
import Vista.Empresas;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Andres
 */
public class ListenerBtnEmpresas implements java.awt.event.ActionListener{
    private Empresas empresa;
    private ModelEmpresas modelEmpresas = ModelEmpresas.getModelEmpresas();
    
    private static int atras=-2, adelante=0;
    private static boolean lAgregar = true;
    private static String tabla = null;
    
    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()){
            case "Agregar":
                boolean lvalidaNumEmpresas = modelEmpresas.validaLicencia();
                
                if (lvalidaNumEmpresas){
                    lAgregar = true;
                    modelEmpresas.setVista(modelEmpresas.getVistaDos());
                    modelEmpresas.setlAgregar(lAgregar);

                    modelEmpresas.habilitar("Inicializa");
                    modelEmpresas.posicion_botones_2();
    //                modelEmpresas.barButtonState2();
                    modelEmpresas.getCodConsecutivo();
                }else{
                    JOptionPane.showMessageDialog(null, "Su licencia actual no permite la creacion de mas Empresas", "Notificación", JOptionPane.WARNING_MESSAGE);
                }
                
                break;
            case "Modificar":
                lAgregar = false;
                modelEmpresas.setVista(modelEmpresas.getVistaDos());
                modelEmpresas.setlAgregar(lAgregar);
                
                modelEmpresas.habilitar("Modificar");
                modelEmpresas.posicion_botones_2();
//                modelEmpresas.barButtonState2();
                
                break;
            case "Grabar":
                modelEmpresas.setVista(modelEmpresas.getVistaDos());
                modelEmpresas.action_bt_grabar();
                                
                break;
            case "Eliminar":
                modelEmpresas.setVista(modelEmpresas.getVistaDos());
                modelEmpresas.action_bt_eliminar();
                
                break;
            case "Cancelar":
                modelEmpresas.setVista(modelEmpresas.getVistaDos());
                modelEmpresas.action_bt_cancelar();
                
                break;
            case "Buscar":
                modelEmpresas.setVista(modelEmpresas.getVistaDos());
                modelEmpresas.actionBtnBuscar();
                
                break;
            case "Anterior":
                modelEmpresas.setVista(modelEmpresas.getVistaDos());
                
                if(atras==-1 || atras==0){
                    return;
                }
                if (atras==-2){
                    atras=modelEmpresas.getTbTabla().getRowCount()-1;
                    adelante=0;
                }
                atras=atras-1;
                adelante=adelante-1;
                System.out.println("Atras: "+atras);
                
                if (atras!=-2){
                    modelEmpresas.actualizaJTable(atras);
                }
                
                break;
            case "Adelante":
                modelEmpresas.setVista(modelEmpresas.getVistaDos());
                
                int Reg=0;
                Reg=modelEmpresas.getTbTabla().getRowCount();
                if (atras==-2){
                    adelante=Reg;
                }
                if (adelante==Reg){
                    atras=Reg-1;
                    modelEmpresas.actualizaJTable(adelante-1);
                    return;
                }
                if (atras==-1 || atras==-2){
                    adelante=1;
                    atras=1;
                }else{
                    if (adelante<Reg){
                        adelante=atras+1;
                        atras=atras+1;
                    }
                }
                System.out.println("Adelante: "+adelante);
                if (adelante<Reg){
                    modelEmpresas.actualizaJTable(adelante);
                }
                
                break;
            case "Salir":
                modelEmpresas.setVista(modelEmpresas.getVistaDos());
                
                empresa = (Empresas) modelEmpresas.getVista();
                empresa.dispose();
                modelEmpresas.setVista(null);
                
                break;
          case "buscar":
              modelEmpresas.setVista(modelEmpresas.getVistaDos());
                modelEmpresas.actionBtnBuscarCarPadre();
                
                break;
        }
    }
}
