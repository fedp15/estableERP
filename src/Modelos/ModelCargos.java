package Modelos;

import Controlador.ControladorBarButton;
import Controlador.ControladorCargos;
import Vista.ListaDeMaestros;
import Vista.Cargos;
//import Vista.TreeCargos;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import util.Internacionalizacion;

public class ModelCargos {
    private static ModelCargos modelCargos;
    
    private JTextField tfCodCar, tfNomCar, tfImgCar;
    private JTable tbCargos;
    private JButton agregar, modificar, grabar, eliminar, cancelar, buscar, atras, adelante, salir;
    private JButton imgCar;
    private JTabbedPane jTabbedPane;
    private JLabel lbImgCar;
    private Object aThis;
    private ResultSet rs;
    private String codigo = "", nombreCar = "", rutaOrigen = "", origen;
    private Boolean lAgregar = false, lLista = false;
    private JDesktopPane desktop;
    
    private final ControladorCargos controladorCargos = new ControladorCargos();
    private final ControladorBarButton controladorBarButton = new ControladorBarButton();
    private final Internacionalizacion idioma = Internacionalizacion.geInternacionalizacion();

    //private final TreeCargos treeCargos = new TreeCargos();
    
    private int nAtras=-2, nAdelante=0;

    private ModelCargos() {
    }
    
    public static ModelCargos getModelCargos(){
        if (modelCargos == null){
            modelCargos = new ModelCargos();
        }

        return modelCargos;
    }
    
    public void setComponent(JTextField codCar, JTextField nomCar, 
                             JTable tablaCareg, 
                             JTabbedPane jTabbedPane){
        this.tfCodCar = codCar;
        this.tfNomCar = nomCar;
        this.tbCargos = tablaCareg;
        this.jTabbedPane = jTabbedPane;
      
    }
    
    public void setButton(JButton agregar, JButton modificar, JButton grabar, JButton eliminar, JButton cancelar,
                          JButton buscar, JButton atras, JButton adelante, JButton salir){
        
        this.agregar = agregar;
        this.modificar = modificar;
        this.grabar = grabar;
        this.eliminar = eliminar;
        this.cancelar = cancelar;
        this.buscar = buscar;
        this.atras = atras;
        this.adelante = adelante;
        this.salir = salir;
    }

    public void setVista(Object aThis){
        this.aThis = aThis;
    }

    public JTabbedPane getjTabbedPane() {
        return jTabbedPane;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public JDesktopPane getDesktop() {
        return desktop;
    }

    public void setDesktop(JDesktopPane desktop) {
        this.desktop = desktop;
    }

    public Object getVista() {
        return aThis;
    }
    
    public JTable getTbCargos() {
        return tbCargos;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombreCar(String nombreCar) {
        this.nombreCar = nombreCar;
    }
    
    public void cargaTablas(){
        controladorCargos.cargaTabla(tbCargos,jTabbedPane);
    }
    
    public void barButtonState1(){
        controladorBarButton.setButton(agregar, modificar, grabar, eliminar, cancelar, buscar, atras, adelante, salir);
        controladorBarButton.posicion_botones_1();
    }
    
    public void barButtonState2(){
        controladorBarButton.setButton(agregar, modificar, grabar, eliminar, cancelar, buscar, atras, adelante, salir);
        controladorBarButton.posicion_botones_2();
    }
    
    public void getCodConsecutivo(){
        tfCodCar.setText(controladorCargos.codConsecutivo());
    }

    public int getnAtras() {
        return nAtras;
    }

    public void setnAtras(int nAtras) {
        this.nAtras = nAtras;
    }

    public int getnAdelante() {
        return nAdelante;
    }

    public void setnAdelante(int nAdelante) {
        this.nAdelante = nAdelante;
    }

    public Boolean getlLista() {
        return lLista;
    }

    public void setlLista(Boolean lLista) {
        this.lLista = lLista;
    }
    
    public void habilitar(String accion){
        switch (accion){
            case "Inicializa":
                tfNomCar.setEnabled(true);
        
                limpiar();
                
                break;
            case "Modificar":
                tfNomCar.setEnabled(true); 
                grabar.setEnabled(true);

                break;
        }
    }
    
    public void deshabilitar(){
        tfCodCar.setEnabled(false); tfNomCar.setEnabled(false); 
    }
    
    private void limpiar(){
            tfNomCar.setText("");
            tfNomCar.requestFocus();
      
    }
    
    public void ejecutaHilo(Boolean lAgregar){
        rs = controladorCargos.ejecutaHilo(codigo, lAgregar, jTabbedPane);
        mostrar();
    }
    
    public void mostrar(){
        try {
            String sql_cmb="";
            
            if (rs.getRow() > 0){
                    tfCodCar.setText(rs.getString("CAR_CODIGO").trim());  tfNomCar.setText(rs.getString("CAR_DESCRI").trim());              
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelCargos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void actionBtnGrabar(Boolean lAgregar){
            if ("".equals(tfNomCar.getText())){
                JOptionPane.showMessageDialog(null, idioma.loadLangMsg().getString("MsgAddName"));
                tfNomCar.requestFocus();
                return;
            }
            
            controladorCargos.grabar(tfCodCar.getText(), tfNomCar.getText(), tbCargos, lAgregar, 
                                         jTabbedPane);
        
        barButtonState1();
        deshabilitar();
    }
    
    public void actionBtnEliminar(){
        if (jTabbedPane.getSelectedIndex()==0){
            controladorCargos.delete(tfCodCar.getText(), tfNomCar.getText(), 
                                         tbCargos, jTabbedPane);
        }
    }
    public void actionBtnCancelar(Boolean lAgregar){
        int eleccion = 0;
        
        if (lAgregar==true){
            if (jTabbedPane.getSelectedIndex()==0){
                eleccion = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgCancelAdd")+" "+
                                                               idioma.loadLangComponent().getString("tabCareg")+"?");
            }
        }else if (lAgregar==false){
            if (jTabbedPane.getSelectedIndex()==0){
                eleccion = JOptionPane.showConfirmDialog(null, idioma.loadLangMsg().getString("MsgCancelEdit")+" "+
                                                               idioma.loadLangComponent().getString("tabCareg")+"?");
            }
        }

        if (eleccion == 0) {
            if (jTabbedPane.getSelectedIndex()==0){
                if (tbCargos.getRowCount()==0){
                    Cargos c = (Cargos) aThis;
                    c.dispose();
                    setVista(null);
                }
            }            
            deshabilitar();
            barButtonState1();
            ejecutaHilo(lAgregar);
        }
    }
    
    public void actualizaJtable(int row){
        if (row>=0){
            if (jTabbedPane.getSelectedIndex()==0){
                codigo = (String) tbCargos.getValueAt(row,0).toString().trim();
                tbCargos.getSelectionModel().setSelectionInterval(row, row);   
                lAgregar=true;
            }
        }
        
        ejecutaHilo(lAgregar);
    }
    
    
    public void actionBtnBuscarCarPadre(){
        ListaDeMaestros buscarCargos = new ListaDeMaestros("DNCARGOS", "", getOrigen());
        buscarCargos.setModal(true);

        Dimension desktopSize = desktop.getSize();
        Dimension jInternalFrameSize = buscarCargos.getSize();
        buscarCargos.setLocation((desktopSize.width - jInternalFrameSize.width)/2,(desktopSize.height- jInternalFrameSize.height)/2);

//        desktop.add(buscarCargos);
        buscarCargos.toFront();
        buscarCargos.setVisible(true);
    }
    
    public void refrescarLista(){
        //treeCargos.cargaTree();
    }
    
    public void buscaCodigo(String codigo){
        int row = 0;
        for(int i=0;i<tbCargos.getRowCount();i++){
            if(codigo.equals(tbCargos.getValueAt(i,0))){
                row = i;
                tbCargos.getSelectionModel().setSelectionInterval(i, i);
            
                break;
            }
        }
        tfCodCar.setText((String)tbCargos.getValueAt(row,0));
        tfNomCar.setText((String)tbCargos.getValueAt(row,1));
    }
}