package Model;

import java.util.ArrayList;

public class ClaseArrayClientes {

    public static ArrayList<Cliente> clientesArray = new ArrayList<Cliente>();
    public static ArrayList<Mesero> meserosArray = new ArrayList<Mesero>();
    public static ArrayList<Cocinero> cocinerosArray = new ArrayList<Cocinero>();
    public static ArrayList<Orden> bufferComidas = new ArrayList<>();
    public static boolean accesoRestaurante = false;

    public static void agregarCliente(Cliente cliente) {
        clientesArray.add(cliente);
    }

    public static void agregarMesero(Mesero mesero) { meserosArray.add(mesero); }

    public static void agregarCocineros(Cocinero cocinero) { cocinerosArray.add(cocinero); }

    public static void agregarComidasLista(Orden orden) { bufferComidas.add(orden); }


    public static ArrayList<Mesero> retornarMeseros() {
        return meserosArray;
    }

    public static void despertarCliente(int mesaAsignada) throws InterruptedException {
        Thread.sleep(100);
        //clientesArray.get(mesaAsignada).consumir(true);
    }

    public static ArrayList<Cocinero> retornarCocineros() {
        return cocinerosArray;
    }

    public static ArrayList<Orden> retornarBufferComidasListas() {
        return bufferComidas;
    }

    public static boolean isAccesoRestaurante() {
        return accesoRestaurante;
    }

    public static void setAccesoRestaurante(boolean accesoRestaurante) {
        ClaseArrayClientes.accesoRestaurante = accesoRestaurante;
    }
}
