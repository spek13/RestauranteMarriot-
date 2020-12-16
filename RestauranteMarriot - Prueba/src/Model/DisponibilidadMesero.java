package Model;

public class DisponibilidadMesero {
    private int[] meseros;

    public DisponibilidadMesero(int numMeseros){
        this.meseros = new int[numMeseros];
    }

    public int meseroDisponible(){
        int index = 0;
        while (meseros[index] != 0){
            index++;
        }
        meseros[index] = 1;
        return index;
    }

    public void liberarMesero(int numMesero){
        meseros[numMesero] = 0;
    }
}
