package Nucleo.Controle;
import java.awt.Image;
import javax.swing.ImageIcon;

import static Nucleo.Aux.EstadosJogo.*;
import Nucleo.Atributos.Banco;
import Nucleo.Atributos.Jogador;
import Nucleo.Atributos.Tabuleiro;
import Nucleo.Aux.ListaCircular;
import Nucleo.Grafico.JogadorG;
import Nucleo.Atributos.D6;

public class Controle {
    private ListaCircular<Jogador> jogadores;
    private JogadorG[] jogadoresG;
    private int numeroJogadores;
    private Tabuleiro tabuleiro;

    private D6 d6;
    private int[] numerosD6;

    public Controle() {
        jogadores = new ListaCircular<Jogador>();
        jogadoresG = new JogadorG[6];
        tabuleiro = new Tabuleiro();
    }

    public void acaoBotaoVender() {

    }

    public void acaoBotaoHipotecar() {

    }

    public void acaoBotaoComprar() {

    }

    public int[] acaoBotaoJogarDados() {
        d6 = new D6();
        numerosD6 = d6.jogaDado();
        return numerosD6;
    }

    // de Fernando para Davi
    public void acaoBotaoCarregarBackup(String nomeArquivo) {

    }

    // de Fernando para Davi
    public void acaoBotaoSalvarBackup(String nomeArquivo) {

    }

    public void acaoBotaoNovaPartida() {
        tabuleiro.gerarVetorCasas();
    }

    public void cadastrarJogadores(String[] vetNomes, int qtdJogadores) {
        Image iAux;
        numeroJogadores = qtdJogadores;

        iAux = new ImageIcon("./Dados/Imagens/detetive.png").getImage();
        jogadoresG[0] = new JogadorG(iAux, 0, vetNomes[0]);
        iAux = new ImageIcon("./Dados/Imagens/detetive.png").getImage();
        jogadoresG[1] = new JogadorG(iAux, 1, vetNomes[1]);
        if (numeroJogadores > 2) {
            iAux = new ImageIcon("./Dados/Imagens/detetive.png").getImage();
            jogadoresG[2] = new JogadorG(iAux, 2, vetNomes[2]);
        if (numeroJogadores > 3) {
            iAux = new ImageIcon("./Dados/Imagens/detetive.png").getImage();
            jogadoresG[3] = new JogadorG(iAux, 3, vetNomes[3]);
        if (numeroJogadores > 4) {
            iAux = new ImageIcon("./Dados/Imagens/detetive.png").getImage();
            jogadoresG[4] = new JogadorG(iAux, 4, vetNomes[4]);
        if (numeroJogadores > 5) {
            iAux = new ImageIcon("./Dados/Imagens/detetive.png").getImage();
            jogadoresG[5] = new JogadorG(iAux, 5, vetNomes[5]);
        }}}}

        for (int i = 0; i < numeroJogadores; i++) {
            jogadores.addLista(new Jogador(i));
        }
        
        jogadores.setIterador();
    }

    public int obterIdJogadorAtual() {
        return jogadores.getIteradorElem().obtemId();
    }

    public void proximoJogador() {
        jogadores.iteradorProx();
    }

    public JogadorG[] obterJogadoresG() {
        return jogadoresG;
    }

    public int obterNumeroJogadores() {
        return numeroJogadores;
    }
}
