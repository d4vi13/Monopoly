package Nucleo.Atributos;
import Nucleo.Aux.Cor;
import java.util.ArrayList;

final public class Jogador {
    private int posicao;
    private int id;
    private ArrayList<Integer> propriedades;
    private boolean falido;
    private boolean preso;
    private boolean ferias;
    private int rodadasFerias;

    public Jogador(){}
    private int rodadasPreso;

    public Jogador(int id){
        this.posicao = 0;
        this.id = id;
        this.falido = false;
        this.preso = false;
        this.ferias = false;
        this.rodadasFerias = 0;
        this.rodadasPreso = 0;
        this.propriedades = new ArrayList<Integer>();
    }

    public boolean estaFalido() {
        return falido;
    }
    
    public int obtemPosicao() {
        return this.posicao;
    }

    public void defineNovaPosicao(int novaPosicao) {
        this.posicao = novaPosicao;
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


    public void defineJogadorEntrouDeFerias() {
        this.ferias = true;
    }

    public void defineJogadorSaiuDeFerias() {
        this.ferias = false;
    }

    public boolean jogadorDeFerias() {
        return this.ferias;
    }

    public void defineRodadasDeFerias() {
        this.rodadasFerias = 1;
    }

    public int retornaRodadasDeFerias() {
        return this.rodadasFerias;
    }

    public void diminuiRodadasFerias() {
        this.rodadasFerias -= 1;
    }
    public void defineRodadasPreso() {
        this.rodadasPreso = 3;
    }

    public void diminuiRodadasPreso() {
        this.rodadasPreso -= 1;
    }

    public int retornaRodadasPreso() {
        return this.rodadasPreso;
    }
}

