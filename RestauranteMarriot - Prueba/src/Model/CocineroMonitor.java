package Model;

public class CocineroMonitor {

    private int numCocineros;
    private int numCocineroAsignado = 0;

    public CocineroMonitor(int numCocineros){
        this.numCocineros = numCocineros;
    }

    public synchronized int puedeAtenderCoc(){
        while(numCocineros == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        numCocineros--;
        System.out.println(" COCINERO >>>>>>>>>>> "+numCocineros);
        //return numCocineroAsignado++;
        return numCocineros;
    }

    public synchronized void cocineroLiberado(){
        numCocineros++;
        numCocineroAsignado--;
        notifyAll();
    }

}
