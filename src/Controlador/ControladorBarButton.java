package Controlador;

import Modelos.ModelEmpresas;
import Vista.Empresas;
import javax.swing.JButton;

public class ControladorBarButton {
    private JButton agregar, modificar, grabar, eliminar, cancelar, buscar, salir; //, atras, adelante
    private static ModelEmpresas modelEmpresas = ModelEmpresas.getModelEmpresas();
    private final Empresas empresas = null;

    public ControladorBarButton() {
    }
    
    public void setButton(JButton agregar, JButton modificar, JButton grabar, JButton eliminar, JButton cancelar,
                          JButton buscar, JButton atras, JButton adelante, JButton salir){    
        this.agregar = agregar;
        this.modificar = modificar;
        this.grabar = grabar;
        this.eliminar = eliminar;
        this.cancelar = cancelar;
        this.buscar = buscar;
//        this.atras = atras;
//        this.adelante = adelante;
        this.salir = salir;
    }
    
    public void setButton(JButton agregar, JButton modificar, JButton grabar, JButton eliminar, JButton cancelar,
                          JButton buscar, JButton salir){
        this.agregar = agregar;
        this.modificar = modificar;
        this.grabar = grabar;
        this.eliminar = eliminar;
        this.cancelar = cancelar;
        this.buscar = buscar;
        this.salir = salir;
    }
    
    public void posicion_botones_1(){
        agregar.setEnabled(true);
        
        if (modelEmpresas.getVista() instanceof Empresas){
            modificar.setVisible(true); buscar.setVisible(true); 
            salir.setVisible(true); eliminar.setVisible(true); 
        }else{
            modificar.setVisible(true); buscar.setVisible(true); 
//            atras.setVisible(true); adelante.setVisible(true);
            salir.setVisible(true); eliminar.setVisible(true);
        }
        
        grabar.setVisible(false); cancelar.setVisible(false);
    }
    
    public void posicion_botones_2(){
        agregar.setEnabled(false);
        
        if (modelEmpresas.getVista() instanceof Empresas){
            modificar.setVisible(false); buscar.setVisible(false);
            salir.setVisible(false); eliminar.setVisible(false);
        }else{
            modificar.setVisible(false); buscar.setVisible(false); eliminar.setVisible(false);
//            adelante.setVisible(false); salir.setVisible(false); atras.setVisible(false);
        }
        
        grabar.setVisible(true); cancelar.setVisible(true);
    }
}