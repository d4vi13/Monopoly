package Nucleo.Atributos;
import Nucleo.Aux.Cor;
import java.io.*;

final public class Jogador implements Serializable{
    private String nome;
    private Cor cor;
    private int posicao;
    private int id;
    private Casa[] vet; // Mudar para um Id/ fazer com que cada casa tenha um id 
    private boolean falido; 

    public Jogador(){

    }

    public Jogador(int id){
        this.posicao = 0;
        this.id = id;
        this.falido = false;
    }
    
    public int obtemId() {
        return 0;
    }
}

