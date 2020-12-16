package Model;

public class Recepcionista {
    private int[] mesas;
    private int cupoRestaurante;
    private int cupoReservacion;

    public Recepcionista(int cupoRestaurante, int cupoReservacion){
        mesas = new int[cupoRestaurante];
        this.cupoRestaurante = cupoRestaurante;
        this.cupoReservacion = cupoReservacion;
    }

    public synchronized void acceder(){
        System.out.println("Cliente quiere acceder");
        while(cupoRestaurante == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cupoRestaurante--;
        System.out.println("Un cliente ha accedido "+cupoRestaurante);
    }

    public synchronized void reservar(){
        System.out.println("Cliente quiere reservar");
        while(cupoRestaurante == 0 || cupoReservacion == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cupoRestaurante--;
        cupoReservacion--;
        System.out.println("Un cliente ha reservado "+cupoRestaurante);
    }

    public synchronized int asignarMesa(){
        int indice = 0;
        while(mesas[indice] != 0){
            indice++;
        }
        mesas[indice] = 1;
        return indice;
    }

    public synchronized void liberarReserva(){
        cupoReservacion++;
        notifyAll();
        System.out.println("Un cliente ha liberado reservacion");
    }

    public void salir(int mesaADesocupar){
        synchronized (this){
            cupoRestaurante++;
            mesas[mesaADesocupar] = 0;
            notifyAll();
            System.out.println("Un cliente ha salido "+cupoRestaurante +" y liberado la mesa "+mesaADesocupar);
        }
    }
}
