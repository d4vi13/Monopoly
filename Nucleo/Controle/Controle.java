package Nucleo.Controle;
import java.awt.Image;
import javax.swing.ImageIcon;

import static Nucleo.Aux.EstadosJogo.*;
import Nucleo.Atributos.Banco;
import Nucleo.Atributos.Jogador;
import Nucleo.Atributos.Tabuleiro;
import Nucleo.Aux.ListaCircular;
import Nucleo.Aux.Serializador;
import Nucleo.Grafico.JogadorG;
import Nucleo.Atributos.D6;

public class Controle {
    private ListaCircular<Jogador> jogadores;
    private ListaCircular<JogadorG> jogadoresG;
    private int numeroJogadores;
    private Tabuleiro tabuleiro;
    
    private Serializador serializador;

    private D6 d6;
    private int[] numerosD6;

    public Controle() {
        jogadores = new ListaCircular<Jogador>();
        jogadoresG = new ListaCircular<JogadorG>();
        tabuleiro = new Tabuleiro();
        serializador = new Serializador();
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

    public void acaoBotaoCarregarBackup(String nomeArquivo) {

    }

    public void acaoBotaoSalvarBackup(String nomeArquivo) {
        serializador.IniciarBackup(arquivo);
        serializador.salvar(jogadores); 
    }

    public void acaoBotaoNovaPartida() {
        tabuleiro.gerarVetorCasas();
    }

    public void cadastrarJogadores(String[] vetNomes, int qtdJogadores) {
        Image iAux;
        numeroJogadores = qtdJogadores;

        iAux = new ImageIcon("./Dados/Imagens/detetive.png").getImage();
        jogadoresG.addLista(new JogadorG(iAux, 0, vetNomes[0]));
        iAux = new ImageIcon("./Dados/Imagens/detetive.png").getImage();
        jogadoresG.addLista(new JogadorG(iAux, 1, vetNomes[1]));
        if (numeroJogadores > 2) {
            iAux = new ImageIcon("./Dados/Imagens/detetive.png").getImage();
            jogadoresG.addLista(new JogadorG(iAux, 2, vetNomes[2]));
        if (numeroJogadores > 3) {
            iAux = new ImageIcon("./Dados/Imagens/detetive.png").getImage();
            jogadoresG.addLista(new JogadorG(iAux, 3, vetNomes[3]));
        if (numeroJogadores > 4) {
            iAux = new ImageIcon("./Dados/Imagens/detetive.png").getImage();
            jogadoresG.addLista(new JogadorG(iAux, 4, vetNomes[4]));
        if (numeroJogadores > 5) {
            iAux = new ImageIcon("./Dados/Imagens/detetive.png").getImage();
            jogadoresG.addLista(new JogadorG(iAux, 5, vetNomes[5]));
        }}}}

        for (int i = 0; i < numeroJogadores; i++) {
            jogadores.addLista(new Jogador(i));
        }
        
        jogadores.setIterador();
        jogadoresG.setIterador();
    }

    public int obterIdJogadorAtual() {
        return jogadores.getIteradorElem().obtemId();
    }

    public void proximoJogador() {
        jogadores.iteradorProx();
    }

    public ListaCircular<JogadorG> obterJogadoresG() {
        return jogadoresG;
    }
}
