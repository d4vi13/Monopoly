package Nucleo.Atributos;
import Nucleo.Aux.Cor;

final public class Jogador {
    private int posicao;
    private int id;
    private Casa[] vet; // Mudar para um Id/ fazer com que cada casa tenha um id 
    private boolean falido; 

    public Jogador(){}

    public Jogador(int id){
        this.posicao = 0;
        this.id = id;
        this.falido = false;
    }
    
    public int obtemPosicao() {
        return this.posicao;
    }

    public int obtemId() {
        return this.id;
    }
}

