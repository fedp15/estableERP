package Listener;

import Modelos.ModelCargos;
import Modelos.ModelListaMaestros;
import Vista.Cargos;
import java.awt.event.ActionEvent;

public class ListenerBtnCargos implements java.awt.event.ActionListener{
    private final ModelCargos modelCargos = ModelCargos.getModelCargos();
    private final ModelListaMaestros modelListaMaestros = ModelListaMaestros.getModelListaMaestros();
    
    private String num = "";
    private int tabs;
    private static boolean Bandera = false, lAgregar=true;
    private static int atras=-2, adelante=0;
    
    private Cargos cargos = null;
    
    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.println(event.getActionCommand());
        switch (event.getActionCommand()){
            case "Agregar":
                lAgregar=true;

                modelCargos.habilitar("Inicializa");
                modelCargos.barButtonState2();
                modelCargos.getCodConsecutivo();
            
                break;
            case "Modificar":
                lAgregar=false;

                modelCargos.habilitar("Modificar");
                modelCargos.barButtonState2();
                //modelCargos.action_bt_modificar();
            
                break;
            case "Grabar":
                modelCargos.actionBtnGrabar(lAgregar);
                
                break;
            case "Eliminar":
                modelCargos.actionBtnEliminar();

                break;
            case "Cancelar":
                if (lAgregar==true){
                    modelCargos.actionBtnCancelar(lAgregar);
                }else if (lAgregar==false){
                    modelCargos.actionBtnCancelar(lAgregar);
                }
                
                break;
            case "Buscar":
                modelCargos.actionBtnBuscarCarPadre();
                
                break;
            case "Anterior":
                if(atras==-1 || atras==0){
                    atras=-2;
                    return;
                }
                if (atras==-2){
                    atras=modelCargos.getTbCargos().getRowCount()-1;
                    adelante=0;
                }
                atras=atras-1;
                adelante=adelante-1;
                System.out.println("Atras: "+atras);
                
                if (atras!=-2){
                    modelCargos.actualizaJtable(atras);
                }
                
                break;
            case "Adelante":
                int Reg=0;
                Reg=modelCargos.getTbCargos().getRowCount();
                if (atras==-2){
                    adelante=Reg;
                }
                if (adelante==Reg){
                    atras=Reg-1;
                    modelCargos.actualizaJtable(adelante-1);
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
                if (adelante<Reg){
                    modelCargos.actualizaJtable(adelante);
                }
                
                break;
            case "Salir":
                cargos = (Cargos) modelCargos.getVista(); 
                modelCargos.setVista(null);
                if (modelCargos.getlLista()==true){
                    modelCargos.refrescarLista();
                }
                cargos.dispose();   
                modelCargos.setVista(null);
                
                break;
                       
        }
    }
}