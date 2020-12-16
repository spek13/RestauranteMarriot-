package Model;

public class Cocinero implements Runnable {

    private Mesero[] meseros;
    private CocineroMonitor cocineroMonitor;
    int meseroActual;

    public Cocinero(Mesero[] meseros, CocineroMonitor cocineroMonitor) {
        this.meseros = meseros;
        this.cocineroMonitor = cocineroMonitor;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Cocinero está cocinando");
            //Sleep para simular al cocinero cocinando
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BufferComida.agregar("comida");
            meseros[meseroActual].ordenLista();
            cocineroMonitor.cocineroLiberado();
        }
    }


    public void despertarCocinero(int mesero) {
        synchronized (this) {
            notify();
            meseroActual = mesero;
        }
    }
}
