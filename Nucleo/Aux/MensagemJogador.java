package Nucleo.Aux;

import Nucleo.Atributos.Propriedade;
import Nucleo.Atributos.Cartas.Carta;

/* EVENTOS POSSÍVEIS */
/* 
 * 0 = Casa vazia
 * 1 = Jogador na casa inicial
 * 2 = Propriedade com dono e pode pagar aluguel
 * 3 = Precisa vender ou hipotecar
 * 4 = Propriedade sem dono e pode comprar
 * 5 = Propriedade sem dono e não pode comprar
 * 6 = Jogador está preso
 * 7 = Jogador está visitando a prisão
 * 8 = Tirou uma carta
 * 9 = Está no CAAD
 * 10 = Está na Recepção
 * 11 = Jogador faliu
 */

public class MensagemJogador {
    private Carta cartaAtual;
    private String propriedadeAtual;
    private int tipoEvento;
    private int deslocamentoJogador;

    public class Eventos {
        public final static int casaVazia = 0;
        public final static int jogadorNaCasaInicial = 1;
        public final static int temDonoEPodePagar = 2;
        public final static int vendaOuHipoteca = 3;
        public final static int semDonoPodeComprar = 4;
        public final static int semDonoNaoPodeComprar = 5;
        public final static int jogadorTaPreso = 6;
        public final static int jogadorEstaVisitandoPrisao = 7;
        public final static int tirouCarta = 8;
        public final static int tirouCartaEfoiPreso = 9;
        public final static int jogadorNoCAAD = 10;
        public final static int jogadorNaRecepcao = 11;
        public final static int jogadorFaliu = 12;
    }

    public MensagemJogador() {};

    public void atualizaMensagem(Carta cartaAtual, Propriedade propriedadeAtual, int tipoEvento, int deslocamento) {
        this.cartaAtual = cartaAtual;
        this.propriedadeAtual = propriedadeAtual.obtemNome();
        this.tipoEvento = tipoEvento;
        this.deslocamentoJogador = deslocamento;
    }
    
    public Carta obtemCartaSorteada() {
        return this.cartaAtual;
    }

    public String obtemPropriedadeAtual() {
        return this.propriedadeAtual;
    }

    public int obtemTipoEvento() {
        return this.tipoEvento;
    }

    public int obtemDeslocamentoDoJogador() {
        return this.deslocamentoJogador;
    }
}
