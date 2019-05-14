package Modelos;

import java.awt.Component;
import java.io.File;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class jComboRenderer  extends JLabel implements ListCellRenderer{
    private ImageIcon[] items;
    private Vector elementos = new Vector();
    
    public jComboRenderer(ImageIcon[] items, Vector elementos){
        setOpaque(true);
        this.items = items; 
        this.elementos = elementos;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        int selectedIndex = ((Integer)value).intValue();

        if (isSelected){
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        ImageIcon icon = this.items[selectedIndex];
        setIcon( icon );

        File f = new File( items[selectedIndex].toString());
        setText((String) elementos.get(selectedIndex));

        return this;
    }
}