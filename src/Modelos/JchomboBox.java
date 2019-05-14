package Modelos;

import java.awt.Dimension;
import javax.swing.JComboBox;

public class JchomboBox extends JComboBox {    
    public JchomboBox(int num_items){
        Dimension d = new Dimension(206,26);
        this.setSize(d);
        this.setPreferredSize(d);
        
        for (int i=0; i<num_items; i++){
            this.addItem(i);
        }
        this.setVisible(true);
    }
}