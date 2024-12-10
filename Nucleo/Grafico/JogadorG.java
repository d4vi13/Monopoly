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

    public void atualizarPosicoes(int casa, int tabPosx, int tabPosy, int tabDim) {
        tabX = tabPosx;
        tabY = tabPosy;
        escala = tabDim / 1156.0;
        atualizarPosicaoJogador(casa);
    }

    public void atualizarPosicaoJogador(int casa) {
        posicaoAtual.posX = (int)(escala * Posicoes.x[casa][id]) + tabX;
        posicaoAtual.posY = (int)(escala * Posicoes.y[casa][id]) + tabY;
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
