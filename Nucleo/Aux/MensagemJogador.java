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
    private boolean eventoMovimento;
    private Carta cartaAtual;
    private Propriedade propriedadeAtual;
    private int tipoEvento;
    private int deslocamentoJogador;

    public class Eventos {
        // Eventos Auxiliares
        public final static int indoPreso = -9;
        public final static int podePagar = -8;
        public final static int casaRecepcao = -7;
        public final static int casaCAAD = -6;
        public final static int casaCarta = -5;
        public final static int casaPrisao = -4;
        public final static int propriedadeSemDono = -3;
        public final static int propriedadeComDono = -2;
        public final static int casaInicial = -1;

        // Eventos Definitivos
        public final static int casaVazia = 0;
        public final static int jogadorNaCasaInicial = 1;
        public final static int temDonoEPodePagar = 2;
        public final static int vendaOuHipoteca = 3;
        public final static int semDonoPodeComprar = 4;
        public final static int ehDonoPodeEvoluir = 14;
        public final static int semDonoNaoPodeComprar = 5;
        public final static int jogadorTaPreso = 6;
        public final static int jogadorEstaVisitandoPrisao = 7;
        public final static int tirouCarta = 8;
        public final static int tirouCartaEfoiPreso = 9;
        public final static int tirouCartaDeMovimento = 10;
        public final static int jogadorNoCAAD = 11;
        public final static int jogadorNaRecepcao = 12;
        public final static int jogadorFaliu = 13;
    }

    public MensagemJogador() {
        this.eventoMovimento = false;
        this.cartaAtual = null;
        this.propriedadeAtual = null;
        this.tipoEvento = 0;
        this.deslocamentoJogador = 0;
    };

    public void atualizaMensagem(Carta cartaAtual, Propriedade propriedadeAtual, int tipoEvento) {
        this.cartaAtual = cartaAtual;
        this.propriedadeAtual = propriedadeAtual;
        this.tipoEvento = tipoEvento;
    }

    public void atualizaMensagem(Carta cartaAtual, Propriedade propriedadeAtual, int tipoEvento, int deslocamento) {
        this.cartaAtual = cartaAtual;
        this.propriedadeAtual = propriedadeAtual;
        this.tipoEvento = tipoEvento;
        this.deslocamentoJogador = deslocamento;
    }
    
    public Carta obtemCartaSorteada() {
        return this.cartaAtual;
    }

    public Propriedade obtemPropriedadeAtual() {
        return this.propriedadeAtual;
    }

    public int obtemTipoEvento() {
        return this.tipoEvento;
    }

    public int obtemDeslocamentoDoJogador() {
        return this.deslocamentoJogador;
    }

    public void defineNovoEvento(int evento) {
        this.tipoEvento = evento;
    }

    public void defineDeslocamento(int deslocamento) {
        this.deslocamentoJogador = deslocamento;
    }

    public boolean obtemEventoMovimento() {
        return this.eventoMovimento;
    }

    public void defineEventoMovimento(boolean evento) {
        this.eventoMovimento = evento;
    }
}
