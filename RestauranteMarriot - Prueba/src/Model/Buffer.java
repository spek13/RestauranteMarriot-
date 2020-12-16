package Model;

import java.util.ArrayList;

public class Buffer {
    public static ArrayList<Orden> ordenes = new ArrayList();

    public static synchronized int agregar(Orden orden){
        ordenes.add(orden);
        return ordenes.size();
    }

    public static synchronized void actualizarStatus(Orden orden){
        ordenes.get(ordenes.indexOf(orden)).setStatus("LISTO");
    }

    public static synchronized boolean hayPendiente(){
        boolean encontrado = false;
        for(int i = 0; i<Buffer.ordenes.size(); i++){
            if(Buffer.ordenes.get(i).getStatus().equals("EN PROCESO")){
                encontrado = true;
            }
        }
        return encontrado;
    }

    public static synchronized boolean isVacio(){
        return ordenes.isEmpty();
    }

    public static synchronized void eliminar(){
        if(!ordenes.isEmpty()){
            ordenes.remove(0);
        }
    }

    public static synchronized void imprimirOrdenes(){
        for(int i = 0; i<ordenes.size(); i++){
            System.out.println(ordenes.get(0).getStatus());
        }
    }

}
