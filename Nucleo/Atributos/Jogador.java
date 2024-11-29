package Nucleo.Atributos;
import Nucleo.Aux.Cor;
import java.util.ArrayList;

final public class Jogador {
    private int posicao;
    private int id;
    private ArrayList<Integer> propriedades;
    private boolean falido;
    private boolean preso;

    public Jogador(){}

    public Jogador(int id){
        this.posicao = 0;
        this.id = id;
        this.falido = false;
        this.preso = false;
        this.propriedades = new ArrayList<Integer>();
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

    public ArrayList<Integer> obtemPropriedadesJogador() {
        return this.propriedades;
    }

    public void defineJogadorPreso() {
        this.preso = true;
    }

    public void defineJogadorLivre() {
        this.preso = false;
    }

    public boolean jogadorPreso() {
        return this.preso;
    }

}

