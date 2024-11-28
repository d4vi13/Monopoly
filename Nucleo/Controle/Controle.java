package Nucleo.Controle;
import java.awt.Image;
import javax.swing.ImageIcon;

import static Nucleo.Aux.EstadosJogo.*;
import Nucleo.Atributos.Banco;
import Nucleo.Atributos.Jogador;
import Nucleo.Atributos.Tabuleiro;
import Nucleo.Aux.ListaCircular;
import Nucleo.Aux.MensagemJogador;
import Nucleo.Grafico.JogadorG;
import Nucleo.Atributos.D6;

public class Controle {
    private ListaCircular<Jogador> jogadores;
    private JogadorG[] jogadoresG;
    private int numeroJogadores;
    private Tabuleiro tabuleiro;
    private Banco banco;

    private D6 d6;
    private int[] numerosD6;

    public Controle() {
        jogadores = new ListaCircular<Jogador>();
        jogadoresG = new JogadorG[6];
        banco = new Banco();
        tabuleiro = new Tabuleiro(banco);
        d6 = new D6();
        numerosD6 = new int[2];
    }

    public void acaoBotaoVender() {

    }

    public void acaoBotaoHipotecar() {

    }

    public void acaoBotaoComprar() {
        int valorPropriedade, idPropriedade; // valor a ser debitado

        Jogador jogadorAtual = jogadores.getIteradorElem();
        valorPropriedade = tabuleiro.obtemValorPropriedade(jogadorAtual); 
        idPropriedade = tabuleiro.obtemIdCasaAtual(jogadorAtual);

        banco.debitar(jogadorAtual.obtemId(), valorPropriedade);

        jogadorAtual.apropriaPropriedade(idPropriedade); 

        // Atualiza no tabuleiro
        tabuleiro.atualizaDono(idPropriedade, jogadorAtual.obtemId());
    }

    public void acaoBotaoJogarDados() {
        d6.jogaDado();
    }

    public int[] obterNumerosD6() {
        numerosD6[0] = d6.obterValorDado(0);
        numerosD6[1] = d6.obterValorDado(1);
        return numerosD6;
    } 

    // Valor não inicializado impede funcionamento do jogo
    public void carregarSaldos(int[] vet) {
        int[] saldos = banco.obterSaldos();
        for(int i = 0; i < numeroJogadores; i++){
            vet[i] = saldos[i];
    }

    // Retorna um codigo de tipo da casa
    // Seria interessante se o codigo informasse
    // possibilidade de comprar/vender/hipotecar
    // Talvez seja necessario alterar tipo do retorno
    public MensagemJogador decifraCasa(int somaDados) {
        return tabuleiro.consultaTabuleiro(jogadores.getIteradorElem());
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

        iAux = new ImageIcon("./Dados/Imagens/Jogador1.png").getImage();
        jogadoresG[0] = new JogadorG(iAux, 0, vetNomes[0]);
        iAux = new ImageIcon("./Dados/Imagens/Jogador2.png").getImage();
        jogadoresG[1] = new JogadorG(iAux, 1, vetNomes[1]);
        if (numeroJogadores > 2) {
            iAux = new ImageIcon("./Dados/Imagens/Jogador3.png").getImage();
            jogadoresG[2] = new JogadorG(iAux, 2, vetNomes[2]);
        if (numeroJogadores > 3) {
            iAux = new ImageIcon("./Dados/Imagens/Jogador4.png").getImage();
            jogadoresG[3] = new JogadorG(iAux, 3, vetNomes[3]);
        if (numeroJogadores > 4) {
            iAux = new ImageIcon("./Dados/Imagens/Jogador5.png").getImage();
            jogadoresG[4] = new JogadorG(iAux, 4, vetNomes[4]);
        if (numeroJogadores > 5) {
            iAux = new ImageIcon("./Dados/Imagens/Jogador6.png").getImage();
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
