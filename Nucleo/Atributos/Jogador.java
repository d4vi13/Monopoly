package Nucleo.Atributos;
import Nucleo.Aux.Cor;
import java.util.ArrayList;

final public class Jogador {
    private int posicao;
    private int id;
    private ArrayList<int> propriedades;
    private boolean falido; 

    public Jogador(){}

    public Jogador(int id){
        this.posicao = 0;
        this.id = id;
        this.falido = false;
        this.propriedades = new ArrayList<int>;
    }
    
    public int obtemPosicao() {
        return this.posicao;
    }

    public int obtemId() {
        return this.id;
    }

    public void apropriaPropriedade(int idPropriedade){
        propriedades.add(idPropriedade);
    }
}

