package Listener;

import Modelos.ModelCreaUsuarios;
import Vista.CreaUsuarios;
import java.awt.event.ActionEvent;

public class ListenerBtnCreaUsuarios implements java.awt.event.ActionListener{
    private CreaUsuarios crearUsuarios;
    ModelCreaUsuarios modelCreaUsuarios = ModelCreaUsuarios.getModelGrupoPermisos();
    
    private static int atras=-2, adelante=0;
    private static boolean lAgregar = true;
    private static String tabla = null;
    
    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()){
            case "Agregar":
                lAgregar = true;
                
                modelCreaUsuarios.habilitar("Inicializa");
                modelCreaUsuarios.barButtonState2();
                modelCreaUsuarios.getCodConsecutivo();
                
                break;
            case "Modificar":
                lAgregar = false;
                
                modelCreaUsuarios.habilitar("Modificar");
                modelCreaUsuarios.barButtonState2();
                
                break;
            case "Grabar":
                modelCreaUsuarios.action_bt_grabar();
                                
                break;
            case "Eliminar":
                modelCreaUsuarios.action_bt_eliminar();
                
                break;
            case "Cancelar":
                modelCreaUsuarios.action_bt_cancelar();
                
                break;
            case "Buscar":
                modelCreaUsuarios.actionBtnBuscarCarPadre();
                
                break;
            case "Anterior":
                if(atras==-1 || atras==0){
                    return;
                }
                if (atras==-2){
                    atras=modelCreaUsuarios.getTbTabla().getRowCount()-1;
                    adelante=0;
                }
                atras=atras-1;
                adelante=adelante-1;
                System.out.println("Atras: "+atras);
                
                if (atras!=-2){
                    modelCreaUsuarios.actualizaJtable(atras);
                }
                
                break;
            case "Adelante":
                int Reg=0;
                Reg=modelCreaUsuarios.getTbTabla().getRowCount();
                if (atras==-2){
                    adelante=Reg;
                }
                if (adelante==Reg){
                    atras=Reg-1;
                    modelCreaUsuarios.actualizaJtable(adelante-1);
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
                    modelCreaUsuarios.actualizaJtable(adelante);
                }
                
                break;
            case "Salir":
                crearUsuarios = (CreaUsuarios) modelCreaUsuarios.getVista();
                crearUsuarios.dispose();
                modelCreaUsuarios.setVista(null);
                
                break;
        }
    }
}
