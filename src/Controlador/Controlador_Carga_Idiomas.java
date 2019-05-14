package Controlador;

import Modelos.Modelo_Carga_Idiomas;
import java.util.Vector;

public class Controlador_Carga_Idiomas {
    Vector ListaIdioma, FormIdioma, MsgIdioma, TablaIdioma;
    Modelo_Carga_Idiomas ModeloIdiomas = new Modelo_Carga_Idiomas();
    
    public Vector ListaIdioma(){
        ListaIdioma = ModeloIdiomas.getListaIdioma();
        
        return ListaIdioma;
    }
}