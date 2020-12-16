package Model;

import sample.Controller;

public class Mesero implements  Runnable{
    CocineroMonitor cocineroMonitor;
    MonitorMesero monitorMesero;
    Cocinero[] cocineros;
    Cliente[] clientes;
    private int idClienteActual = 0;
    private int idMesero;
    private Orden ordenActual;
    public int idMesa;


    int[] arrayEX = {463,650,853,1090,1300,463,650,853,1090,1300};
    int[] arrayEY = {200,200,200,200,200,480,480,480,480,480};

    int[] arrayXC = {355};
    int[] arrayYC = {710};

    int ejeX;
    int ejeY;
    String[] estatusAnimacion = new String[4];

    public Mesero(CocineroMonitor cocineroMonitor, Cocinero[] cocineros, int idMesero, Cliente[] clientes, MonitorMesero monitorMesero, int ejeX, int ejeY){
        this.cocineroMonitor = cocineroMonitor;
        this.cocineros = cocineros;
        this.idMesero = idMesero;
        this.clientes = clientes;
        this.monitorMesero = monitorMesero;
        this.ejeX = ejeX;
        this.ejeY = ejeY;
    }

    public void ordenLista(){
        //Se actualiza el status de la orden de EN PROCESO a LISTO
        Buffer.actualizarStatus(ordenActual);
        //cocineroMonitor.cocineroLiberado();
        synchronized (this){
            notify();
        }
    }

    @Override
    public void run() {
        while (true){
            bloquearMesero();
            System.out.println("Generando orden del cliente " + idClienteActual);
            ordenActual = new Orden(idClienteActual, idMesero, "EN PROCESO", clientes[idClienteActual]);
            Buffer.agregar(ordenActual);
            idMesa = clientes[idClienteActual].getIdMesa();
            //EL MESERO VA A LA MESA DEL CLIENTE
            //atender cliente
            atenderCliente();
            int numCocinero = cocineroMonitor.puedeAtenderCoc();
            dormirHilos(100);
            ordenCocinero(numCocinero);
            //EL MESERO VA A LA MESA DEL CLIENTE
            llevarOrden();
            clientes[idClienteActual].consumir();
            //monitorMesero.meseroLiberado();
        }
    }

    public void despertarMesero(int idCliente){
        synchronized (this){
            notify();
            idClienteActual = idCliente;
        }
    }
    public void dormirHilos (int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void atenderCliente (){
        //atender cliente
        Controller.meserosArray[idMesero].setCenterX(arrayEX[idMesa]);
        Controller.meserosArray[idMesero].setCenterY(arrayEY[idMesa]);

        System.out.println("Cambiar orden a proceso");
        //Sleep para simular al mesero atendiendo
        dormirHilos(3000);

    }
    public void ordenCocinero(int numCocinero){

        Controller.cocinerosArray[numCocinero].setCenterX(arrayXC[numCocinero]);
        Controller.cocinerosArray[numCocinero].setCenterY(arrayYC[numCocinero]);

        System.out.println(numCocinero);
        cocineros[numCocinero].despertarCocinero(idMesero);

        Controller.meserosArray[idMesero].setCenterX(695);
        Controller.meserosArray[idMesero].setCenterY(574);
        dormirHilos(500);
    }
    public void llevarOrden (){
        Controller.meserosArray[idMesero].setCenterX(arrayEX[idMesa]);
        Controller.meserosArray[idMesero].setCenterY(arrayEY[idMesa]);

        bloquearMesero();
        //Sleep para que el mesero entregue el platillo al cliente
        dormirHilos(3000);
    }

    public void bloquearMesero(){
        synchronized (this){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
