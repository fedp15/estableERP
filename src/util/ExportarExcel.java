package util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JTable;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author RaaG
 */
public class ExportarExcel {
    private File file;
    private List<JTable> tabla;
    private List<String> nom_files;

    public ExportarExcel(File file, List<JTable> tabla, List<String> nom_files) throws Exception {
        this.file = file;
        this.tabla = tabla;
        this.nom_files = nom_files;
        if (nom_files.size()!=tabla.size()) {
            throw new Exception ("Error");
        }
    }
    
    public boolean export(int nunColum, String org) {
        try {
            DataOutputStream out=new DataOutputStream (new FileOutputStream(file));
            WritableWorkbook w = Workbook.createWorkbook(out);
            
            for (int index=0; index<tabla.size(); index++){
                JTable table=tabla.get(index);
                WritableSheet s=w.createSheet(nom_files.get(index),0);
               //Para que salga el titulo de las columnas
                //for (int i = 0; i < table.getColumnCount(); i++) {
                for (int i = 0; i < nunColum; i++) {
                    for (int j = 0; j < table.getRowCount(); j++) {
                        Object titulo = null;
                        
                        if (org.equals("Exportar Productos")){
                            if (i==0){
                                titulo = table.getColumnName(i);
                                
                                s.addCell(new Label(i+1, j, String.valueOf(titulo)));
                            }
                            
                            if (i==1){
                                titulo = "Proveedor";
                                
                                s.addCell(new Label(1, j, String.valueOf(titulo)));
                            }
                            
                            if (i==1){
                                titulo = table.getColumnName(i);
                                
                                s.addCell(new Label(i+1, j, String.valueOf(titulo)));
                            }

                            if (i==3){
                                titulo = "Descripcion";
                                
                                s.addCell(new Label(3, j, String.valueOf(titulo)));
                            }

                            if (i==4){
                                titulo = "Tipo de Precio";
                                
                                s.addCell(new Label(4, j, String.valueOf(titulo)));
                            }

                            if (i==5){
                                titulo = "Unidad de Medida";
                                
                                s.addCell(new Label(5, j, String.valueOf(titulo)));
                            }

                            if (i==6){
                                titulo = "Nombre de Unidad";
                                
                                s.addCell(new Label(6, j, String.valueOf(titulo)));
                            }

                            if (i==7){
                                titulo = "Marca";
                                
                                s.addCell(new Label(7, j, String.valueOf(titulo)));
                            }

                            if (i==8){
                                titulo = "Tipo de Impuesto";
                                
                                s.addCell(new Label(8, j, String.valueOf(titulo)));
                            }

                            if (i==9){
                                titulo = "Imagen";
                                
                                s.addCell(new Label(9, j, String.valueOf(titulo)));
                            }

                            if (i==10){
                                titulo = "Precio #1";
                                
                                s.addCell(new Label(10, j, String.valueOf(titulo)));
                            }

                            if (i==11){
                                titulo = "Precio #2";
                                
                                s.addCell(new Label(11, j, String.valueOf(titulo)));
                            }

                            if (i==12){
                                titulo = "Precio #3";
                                
                                s.addCell(new Label(12, j, String.valueOf(titulo)));
                            }

                            if (i==13){
                                titulo = "Precio #4";
                                
                                s.addCell(new Label(13, j, String.valueOf(titulo)));
                            }

                            if (i==14){
                                titulo = "Precio #5";
                                
                                s.addCell(new Label(14, j, String.valueOf(titulo)));
                            }
                        }else{
                            titulo = table.getColumnName(i);
                            
                            s.addCell(new Label(i, j, String.valueOf(titulo)));
                        }
                        
                        //s.addCell(new Label(i+1, j+1, String.valueOf(titulo)));
                    }
                }
                
                //for (int i = 0; i < table.getColumnCount(); i++) {
                for (int i = 0; i < nunColum; i++) {
                    for (int j = 0; j < table.getRowCount(); j++) {
                        Object object = null;
                        
                        if (org.equals("Exportar Productos")){
                            if (i==0){
                                object = table.getValueAt(j, i);
                                
                                s.addCell(new Label(i, j+1, String.valueOf(object)));
                            }else if (i==1){
                                s.addCell(new Label(i, j+1, ""));
                            }else if (i==2){
                                object = table.getValueAt(j, 1);
                                
                                s.addCell(new Label(i, j+1, String.valueOf(object)));
                            }else if (i==3){
                                s.addCell(new Label(i, j+1, ""));
                            }else if (i==4){
                                s.addCell(new Label(i, j+1, "A"));
                            }else if (i==5){
                                s.addCell(new Label(i, j+1, "Unid"));
                            }else if (i==6){
                                s.addCell(new Label(i, j+1, "Unidad de Medida"));
                            }else if (i==7){
                                s.addCell(new Label(i, j+1, ""));
                            }else if (i==8){
                                s.addCell(new Label(i, j+1, ""));
                            }else if (i==9){
                                s.addCell(new Label(i, j+1, ""));
                            }else if (i==10){
                                object = table.getValueAt(j, 3);
                                
                                s.addCell(new Label(i, j+1, String.valueOf(object)));
                            }else if (i==11){
                                s.addCell(new Label(i, j+1, ""));
                            }else if (i==12){
                                s.addCell(new Label(i, j+1, ""));
                            }else if (i==13){
                                s.addCell(new Label(i, j+1, ""));
                            }else if (i==14){
                                s.addCell(new Label(i, j+1, ""));
                            }
                        }else{
                            object = table.getValueAt(j, i);
                            
                            s.addCell(new Label(i, j+1, String.valueOf(object)));
                        }
                        
                        //s.addCell(new Label(i+1, j+2, String.valueOf(object)));
//                        s.addCell(new Label(i, j+1, String.valueOf(object)));
                    }
                }
             
                /*
                *    for sin titulo de columnas:
                *
                *  for (int i=0; i<table.getColumnCount(); i++){
                *     for (int j=0; j<table.getRowCount();j++){
                *         Object object=table.getValueAt(j,i);
                *         s.addCell (new Label(i,j,String.valueOf(object)));
                *        
                *     }   
                * }
                **/

            }
            w.write();
            w.close();
            return true;
        } 

        catch (IOException | WriteException e) {
            return false;
        }
    } 
}
