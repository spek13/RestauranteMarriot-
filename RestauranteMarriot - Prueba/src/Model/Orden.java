package Model;

public class Orden {
    private int numOrden;
    private int idMesero;
    private String status;
    private Cliente cliente;

    public Orden(int numOrden, int idMesero, String status, Cliente cliente){
        this.numOrden = numOrden;
        this.idMesero = idMesero;
        this.status = status;
        this.cliente = cliente;
    }

    public int getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
    }

    public int getIdMesero() {
        return idMesero;
    }

    public void setIdMesero(int idMesero) {
        this.idMesero = idMesero;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
