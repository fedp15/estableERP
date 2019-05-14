package Modelos;

import javax.swing.JTable;
import javax.swing.JTextField;

public class ModelListaMaestros {
    private static ModelListaMaestros modelListaMaestros;
    private JTable tableMaestros;
    private JTextField tfId;
    
    private ModelListaMaestros() {
    }
    
    public static ModelListaMaestros getModelListaMaestros(){
        if (modelListaMaestros == null){
            modelListaMaestros = new ModelListaMaestros();
        }

        return modelListaMaestros;
    }

    public JTable getTableMaestros() {
        return tableMaestros;
    }

    public void setTableMaestros(JTable tableMaestros) {
        this.tableMaestros = tableMaestros;
    }

    public JTextField getTfId() {
        return tfId;
    }

    public void setTfId(JTextField tfId) {
        this.tfId = tfId;
    }
    
    public void focoTfId(){
        tfId.requestFocus();
    }
}
