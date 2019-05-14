package Listener;

import Modelos.ModelMoneda;
import Vista.Empresas;
import Vista.Moneda;
import java.awt.event.ActionEvent;

public class ListenerBtnMoneda implements java.awt.event.ActionListener{
    private Moneda moneda;
    private ModelMoneda modelMoneda = ModelMoneda.getModelMoneda();
    private static int atras=-2, adelante=0;
    
    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()){
            case "Agregar":
                modelMoneda.habilitar("Inicializa");
                modelMoneda.posicion_botones_2();
                modelMoneda.setConsecutivo();
                
                break;
            case "Modificar":
                modelMoneda.habilitar("Modificar");
                modelMoneda.posicion_botones_2();
                
                break;
            case "Grabar":
                modelMoneda.action_bt_grabar();
                                
                break;
            case "Eliminar":
                modelMoneda.action_bt_eliminar();
                
                break;
            case "Cancelar":
                modelMoneda.action_bt_cancelar();
                
                break;
            case "Buscar":
                modelMoneda.actionBtnBuscar();
                
                break;
            case "Anterior":
                if(atras==-1 || atras==0){
                    return;
                }
                if (atras==-2){
                    atras=modelMoneda.getTbTabla().getRowCount()-1;
                    adelante=0;
                }
                atras=atras-1;
                adelante=adelante-1;
                System.out.println("Atras: "+atras);
                
                if (atras!=-2){
                    modelMoneda.actualizaJTable(atras);
                }
                
                break;
            case "Adelante":
                int Reg=0;
                Reg=modelMoneda.getTbTabla().getRowCount();
                if (atras==-2){
                    adelante=Reg;
                }
                if (adelante==Reg){
                    atras=Reg-1;
                    modelMoneda.actualizaJTable(adelante-1);
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
                    modelMoneda.actualizaJTable(adelante);
                }
                
                break;
            case "Salir":
                moneda = (Moneda) modelMoneda.getVista();
                modelMoneda.setVista(null);
                moneda.dispose();
                
                if (modelMoneda.getOrgCall().equals("Empresa")){
                    Empresas empresas = (Empresas) modelMoneda.getOtherThis();
                    empresas.requestFocus();
                }
                
                break;
        }
    }    
}
