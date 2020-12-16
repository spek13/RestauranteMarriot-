package Model;

public class MonitorMesero {

    private int numMeseros;
    private int numMeseroAsignado = 0;
    private DisponibilidadMesero disponibilidadMesero;

    public MonitorMesero(int numMeseros, DisponibilidadMesero disponibilidadMesero){
        this.numMeseros = numMeseros;
        this.disponibilidadMesero = disponibilidadMesero;
    }

    public synchronized int puedeAtender(){
        while(numMeseros == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        numMeseros--;
        System.out.println("MESERO >>>>>>>>>>>>>>>>>>>>"+numMeseros);
        int meseroLibre = disponibilidadMesero.meseroDisponible();
        System.out.println("MESERO ASIGNADO ========== "+meseroLibre);
        return meseroLibre;
    }

    public synchronized void meseroLiberado(int meseroALiberar){
        numMeseros++;
        disponibilidadMesero.liberarMesero(meseroALiberar);
        //numMeseroAsignado--;
        notifyAll();
    }

}
