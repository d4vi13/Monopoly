package Nucleo.Atributos;
import java.util.ArrayList;
import java.io.*;

final public class Jogador implements Serializable{
    private String nome;
    private int posicao;
    private int id;
    private ArrayList<Integer> propriedades;
    private boolean falido;
    private boolean preso;
    private boolean ferias;
    private int rodadasPreso;
    private int rodadasFerias;

    public Jogador(){}

    public Jogador(int id, String nome){
        this.posicao = 0;
        this.id = id;
        this.falido = false;
        this.preso = false;
        this.ferias = false;
        this.rodadasPreso = 0;
        this.propriedades = new ArrayList<Integer>();
        this.nome = nome;
    }

    public boolean estaFalido() {
        return falido;
    }
    
    public int obtemPosicao() {
        return this.posicao;
    }

    public String obtemNome(){
        return nome;
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
    
    public void desapropriaPropriedade(ArrayList<Integer> propriedades){
        this.propriedades.removeAll(propriedades);
    }

    public ArrayList<Integer> obtemPropriedadesJogador() {
        return this.propriedades;
    }

    public void defineJogadorPreso() {
        this.preso = true;
        this.rodadasPreso = 3;
    }

    public void defineJogadorLivre() {
        this.preso = false;
    }

    public boolean jogadorPreso() {
        return this.preso;
    }

    public void defineJogadorEntrouDeFerias() {
        this.ferias = true;
        this.rodadasFerias = 1;
    }

    public void defineJogadorSaiuDeFerias() {
        this.ferias = false;
    }

    public boolean jogadorDeFerias() {
        return this.ferias;
    }

    public void diminuiRodadasPreso() {
        this.rodadasPreso -= 1;
    }

    public void diminuiRodadasFerias() {
        this.rodadasFerias -= 1;
    }

    public int retornaRodadasPreso() {
        return this.rodadasPreso;
    }

    public int retornaRodadasFerias() {
        return this.rodadasFerias;
    }

    public void declaraFalencia() {
        this.falido = true;
    }
  
    public boolean ehDono(int id){
        return propriedades.contains(id);
    }
}

