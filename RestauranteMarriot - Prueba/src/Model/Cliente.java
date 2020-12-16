package Model;

import sample.Controller;

import java.util.Observable;
import java.util.Random;

public class Cliente extends Observable implements Runnable {
    private int idCliente;
    Recepcionista recepcionista;
    Mesero[] meseros;
    MonitorMesero monitorMesero;
    Random random = new Random();
    private int mesaAsignada;
    public int idMesa;
    public int idMesero;
    int ejeX;
    int ejeY;
    String[] estatusAnimacion = new String[4];


    public Cliente(int idCliente, Recepcionista recepcionista, Mesero[] meseros, MonitorMesero monitorMesero, int ejeX, int ejeY) {
        this.idCliente = idCliente;
        this.recepcionista = recepcionista;
        this.meseros = meseros;
        this.monitorMesero = monitorMesero;
        this.ejeX = ejeX;
        this.ejeY = ejeY;
    }

    @Override
    public void run() {
        reservacion();
        asignarMesa();
        //solicitar meserp
        int meseroAsignado = monitorMesero.puedeAtender();
        //agigna mesero
        idMesero = meseroAsignado;
        dormirHilos(100);
        meseros[meseroAsignado].despertarMesero(idCliente);//esta en meserp
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Los clientes no llegan a este punto
        monitorMesero.meseroLiberado(meseroAsignado);

        //Sleep para simular el tiempo en que los clientes consumen
        //y que el mesero regrese a su posición inicial
        estatusAnimacion[0] = "2";
        setChanged();
        notifyObservers(Thread.currentThread().getName());
        dormirHilos(3000);

        recepcionista.salir(mesaAsignada);
    }

    public void consumir() {
        synchronized (this) {
            notify();
            System.out.println(BufferComida.comidas.size() + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> SIZE BUFFER");
            if (BufferComida.comidas.size() == 40) {
                for (int i = 0; i < Controller.clientesArray.length; i++) {
                    Controller.clientesArray[i].setVisible(false);

                    Controller.meserosArray[0].setCenterX(871);
                    Controller.meserosArray[0].setCenterY(701);

                    Controller.meserosArray[1].setCenterX(959);
                    Controller.meserosArray[1].setCenterY(701);

                    Controller.meserosArray[2].setCenterX(1041);
                    Controller.meserosArray[2].setCenterY(701);

                    try {
                        //Sleep para simular tiempo de asignación de mesa
                        Thread.sleep(((5) * i) + 150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Controller.cocinerosArray[0].setCenterX(558);
                    Controller.cocinerosArray[0].setCenterY(716);

                    try {
                        //Sleep para simular tiempo de asignación de mesa
                        Thread.sleep(((5) * i) + 150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Controller.cocinerosArray[1].setCenterX(649);
                    Controller.cocinerosArray[1].setCenterY(716);

                    try {
                        //Sleep para simular tiempo de asignación de mesa
                        Thread.sleep(((5) * i) + 150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Controller.cocinerosArray[2].setCenterX(733);
                    Controller.cocinerosArray[2].setCenterY(716);
                }
            }

        }
    }
    public void dormirHilos (int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void reservacion(){
        if (random.nextBoolean()) {
            recepcionista.reservar();
            System.out.println("Cliente esperando (Proceso de reserva)");

            Controller.clientesArray[idCliente].setCenterX(206);
            Controller.clientesArray[idCliente].setCenterY(586);
            dormirHilos(2000);

            recepcionista.liberarReserva();
        } else {
            recepcionista.acceder();
        }
        //dormirHilos(200);
        try {
            //Sleep para simular tiempo de asignación de mesa
            Thread.sleep(((325) * idCliente) + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void asignarMesa(){
        estatusAnimacion[0] = "0";
        setChanged();
        notifyObservers(Thread.currentThread().getName());

        mesaAsignada = recepcionista.asignarMesa();
        idMesa = mesaAsignada;
        //Sleep para simular asignación de mesa
        dormirHilos(300);
        System.out.println("Mesa asignada en " + mesaAsignada);
        estatusAnimacion[0] = "1";
        setChanged();
        notifyObservers(Thread.currentThread().getName());
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public int getEjeX() {
        return ejeX;
    }

    public void setEjeX(int ejeX) {
        this.ejeX = ejeX;
    }

    public int getEjeY() {
        return ejeY;
    }

    public void setEjeY(int ejeY) {
        this.ejeY = ejeY;
    }

    public String[] getEstatusAnimacion() {
        return estatusAnimacion;
    }



}
