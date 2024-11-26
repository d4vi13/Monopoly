package Nucleo.Atributos;
import java.io.*;

public class Banco implements Serializable {
    private Cliente[] clientes; // cada cliente tem seu id identificador  

    public Banco(){
        clientes = new Cliente[6];
        for (int i = 0; i < 6; i++) {
            clientes[0] = new Cliente(i, 1500);
        }
    }

    public void transferir(int id_remetente, int id_destinatario, int valor){
        clientes[id_remetente].debitar(valor);
        clientes[id_destinatario].receber(valor);
    }

    public void debitar(int id, int valor){
        clientes[id].debitar(valor);
    }

    public int[] obterSaldos(){
        int[] saldos = new int[6];

        for(int i = 0; i < 6; i++){
            saldos[i] = clientes[i].obterSaldo();
        }

        return saldos;
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

    public int obterSaldo(){
        return saldo; 
    }

}
