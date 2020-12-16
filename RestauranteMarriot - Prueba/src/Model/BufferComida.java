package Model;

import java.util.ArrayList;

public class BufferComida {
    public static ArrayList comidas = new ArrayList();

    public static synchronized void agregar(String orden){
        comidas.add(orden);
    }

    public static synchronized void eliminar(){

    }

}
