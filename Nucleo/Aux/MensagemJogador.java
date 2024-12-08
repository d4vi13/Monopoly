package Nucleo.Aux;

import Nucleo.Atributos.Propriedade;
import Nucleo.Atributos.Cartas.Carta;

public class MensagemJogador {
    private boolean eventoMovimento;
    private Carta cartaAtual;
    private Propriedade propriedadeAtual;
    private int tipoEvento;
    private int deslocamentoJogador;
    private int valorEvolucao;

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
        public final static int ehDonoPodeEvoluir = 5;
        public final static int semDonoNaoPodeComprar = 6;
        public final static int jogadorTaPreso = 7;
        public final static int jogadorEstaVisitandoPrisao = 8;
        public final static int tirouCarta = 9;
        public final static int tirouCartaEfoiPreso = 10;
        public final static int tirouCartaDeMovimento = 11;
        public final static int jogadorNoCAAD = 12;
        public final static int jogadorNaRecepcao = 13;
        public final static int jogadorFaliu = 14;
    }

    public MensagemJogador() {
        this.eventoMovimento = false;
        this.cartaAtual = null;
        this.propriedadeAtual = null;
        this.tipoEvento = 0;
        this.deslocamentoJogador = 0;
        this.valorEvolucao = 0;
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
    
    public void defineValorEvolucao(int valor) {
        this.valorEvolucao = valor;
    }

    public int obtemValorEvolucao() {
        return this.valorEvolucao;
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
