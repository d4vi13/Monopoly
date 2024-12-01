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
import Nucleo.Aux.Serializador;
import Nucleo.Atributos.D6;

public class Controle {
    private ListaCircular<Jogador> jogadores;
    private JogadorG[] jogadoresG;
    private Tabuleiro tabuleiro;
    private Banco banco;
    private int numeroJogadores;
    
    private Serializador serializador;
    
    private D6 d6;
    private int[] numerosD6;

    public Controle() {
        jogadores = new ListaCircular<Jogador>();
        jogadoresG = new JogadorG[6];
        banco = new Banco();
        tabuleiro = new Tabuleiro(banco);
        d6 = new D6();
        numerosD6 = new int[2];
        serializador = new Serializador();
    }

    // Codigos:
    // 0 -> Precisa vender mais, mesmo hipotecando todas as outras propriedades nao vai bastar
    // 1 -> Ja vendeu suficiente, mas ainda precisa hipotecar para ter dinheiro suficiente
    // 2 -> Ja vendeu suficiente, nao precisa mais hipotecar
    public int acaoBotaoVender(String[] propriedades) {
        return 1;
    }

    // 0 -> Precisa hipotecar menos, mesmo vendendo todas as outras propriedades nao vai bastar
    // 1 -> Hipotecou suficiente. Ainda precisa vender mais um pouco
    // 2 -> Ja hipotecou suficiente, nao eh necessario vender
    public int acaoBotaoHipotecar(String[] propriedades) {
        return 1;
    }

    public void acaoBotaoComprar() {

    }

    public void acaoBotaoJogarDados() {
        d6.jogaDado();
    }

    public int[] obterNumerosD6() {
        numerosD6[0] = d6.obterValorDado(0);
        numerosD6[1] = d6.obterValorDado(1);
        return numerosD6;
    } 

    public void carregarSaldos(int[] vet) {
        int[] saldos = banco.obterSaldos();
        for(int i = 0; i < numeroJogadores; i++){
            vet[i] = saldos[i];
        } 
    }

    public MensagemJogador decifraCasa(int casaDestino) {
        // ATUALIZAR ESTADO DO JOGADOR
        Jogador jogadorAtual = jogadores.getIteradorElem();
        int[] dados = obterNumerosD6();

        if (jogadorAtual.jogadorPreso()) {
            if (dados[0] == dados[1]) {
                // Jogador livre da prisao
                jogadorAtual.defineNovaPosicao(casaDestino);
            }
        } else {
            // Nao esta preso, atualiza normalmente
            jogadorAtual.defineNovaPosicao(casaDestino);
        }
        
        return tabuleiro.consultaTabuleiro(jogadorAtual);
    }

    // de Fernando para Davi
    public void acaoBotaoCarregarBackup(String nomeArquivo) {

    }

    // de Fernando para Davi
    public void acaoBotaoSalvarBackup(String nomeArquivo) {
        serializador.iniciarBackup(nomeArquivo);
        serializador.salvar(jogadores);        
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
        }
        if (numeroJogadores > 3) {
            iAux = new ImageIcon("./Dados/Imagens/Jogador4.png").getImage();
            jogadoresG[3] = new JogadorG(iAux, 3, vetNomes[3]);
        }
        if (numeroJogadores > 4) {
            iAux = new ImageIcon("./Dados/Imagens/Jogador5.png").getImage();
            jogadoresG[4] = new JogadorG(iAux, 4, vetNomes[4]);
        }
        if (numeroJogadores > 5) {
            iAux = new ImageIcon("./Dados/Imagens/Jogador6.png").getImage();
            jogadoresG[5] = new JogadorG(iAux, 5, vetNomes[5]);
        }

        for (int i = 0; i < numeroJogadores; i++) {
            jogadores.addLista(new Jogador(i));
        }
        
        jogadores.setIterador();
    }

    public int obterIdJogadorAtual() {
        return jogadores.getIteradorElem().obtemId();
    }

    public int obterCasaAtualJogador() {
        return jogadores.getIteradorElem().obtemPosicao();
    }

    public boolean atualStatusFalido() {
        return jogadores.getIteradorElem().estaFalido();
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
