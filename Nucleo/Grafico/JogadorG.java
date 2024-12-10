package Nucleo.Grafico;

import java.awt.*;

import Nucleo.Aux.Posicoes;
import Nucleo.Aux.Posicoes.Posicao;

public class JogadorG {
    private Posicao posicaoAtual;
    private String nome;
    private int id, tabX, tabY;
    private double escala;
    private boolean faliu;
    private Image icone;

    public JogadorG(Image i, int id, String nome) {
        icone = i;
        this.nome = nome;
        this.id = id;
        posicaoAtual = new Posicao();
        faliu = false;
    }

    public void atualizarPosicao(int tabPosx, int tabPosy, int tabDim) {
        tabX = tabPosx;
        tabY = tabPosy;
        escala = tabDim / 1156.0;
    }

    public void atualizarPosicao(int casa) {
        posicaoAtual.posX = Posicoes.x[casa][id];
        posicaoAtual.posY = Posicoes.y[casa][id];
    }

    public void atualizarPosicao() {
        posicaoAtual.posX = (int)(escala * posicaoAtual.posX) + tabX;
        posicaoAtual.posY = (int)(escala * posicaoAtual.posY) + tabY;
    }

    public Posicao obterPosicaoJogador() {
        return posicaoAtual;
    }

    public Image obterIcone() {
        return icone;
    }

    public String obterNome() {
        return nome;
    }

    public void defineFalido() {
        faliu = true;
    }

    public boolean estaFalido() {
        return faliu;
    }
}
