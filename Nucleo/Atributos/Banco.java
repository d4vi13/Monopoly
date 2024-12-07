package Nucleo.Atributos;
import java.io.*;

public class Banco implements Serializable {
    private Cliente[] clientes; // cada cliente tem seu id identificador  
    private int numeroDeClientes;
    private final static int salarioClientes = 200000;

    public Banco(int numeroDeClientes){
        this.numeroDeClientes = numeroDeClientes;
        clientes = new Cliente[6];
        for (int i = 0; i < 6; i++) {
            clientes[i] = new Cliente(i, 500000);
        }
    }

    public void pagaSalario(int id) {
        clientes[id].receber(salarioClientes);
    }

    public void transferir(int id_remetente, int id_destinatario, int valor){
        clientes[id_remetente].debitar(valor);
        clientes[id_destinatario].receber(valor);
    }

    public void transferir(int id_remetentes[], int id_destinatario, int valor){
        for (int id = 0; id < numeroDeClientes; id++)
            if ((clientes[id].obterSaldo() - valor) > 0)
                transferir(id, id_destinatario, valor);
    }

    public void transferir(int id_destinatario, int valor){
        transferir(new int[]{1,2,3,4,5,6}, id_destinatario, valor); 
    }

    public void debitar(int id, int valor){
        clientes[id].debitar(valor);
    }    

    public void receber(int id, int valor){
        clientes[id].receber(valor);
    }    

    public boolean temSaldoSuficiente(int id, int valor){
        int saldo = clientes[id].obterSaldo();

        if (saldo < valor)
            return false;
        return true;
    }

    public int[] obterSaldos(){
        int[] saldos = new int[6];

        for(int i = 0; i < 6; i++){
            saldos[i] = clientes[i].obterSaldo();
        }

        return saldos;
    }

    public int obterSaldo(int id) {
        return this.clientes[id].obterSaldo();
    }
}

class Cliente implements Serializable {
    private int id;
    private int saldo;

    public Cliente( int id, int saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    public void debitar(int valor){
        this.saldo -= valor;
    }
   
    public void receber(int valor){
        this.saldo += valor;
    }

    public int obterId() {
        return this.id;
    }

    public int obterSaldo(){
        return saldo; 
    }

}
