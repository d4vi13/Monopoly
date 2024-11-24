package Nucleo.Aux;

import Nucleo.Atributos.Propriedade;
import Nucleo.Atributos.Cartas.Carta;

public class MensagemJogador {
    private boolean casaInicial;
    private boolean botaoComprar;
    private boolean botaoVender;
    private boolean botaoHipotecar;
    private boolean casaDeCarta;
    private Carta cartaAtual;
    private Propriedade propriedadeAtual;
    private int tipoMensagem;

    public MensagemJogador() {};
    public void atualizaMensagem(boolean casaInicial, boolean botaoComprar, boolean botaoVender, boolean botaoHipotecar,
                                 boolean casaCarta, Carta cartaSorteada, Propriedade propriedadeAtual, int tipoMensagem) {
        this.casaInicial = casaInicial;
        this.botaoComprar = botaoComprar;
        this.botaoVender = botaoVender;
        this.botaoHipotecar = botaoHipotecar;
        this.casaDeCarta = casaCarta;
        this.cartaAtual = cartaSorteada;
        this.propriedadeAtual = propriedadeAtual;
        this.tipoMensagem = tipoMensagem;
    }

    public boolean obtemInfoCasaInicial() {
        return this.casaInicial;
    }

    public boolean mensagemDeComprar() {
        return this.botaoComprar;
    }

    public boolean mensagemDeVender() {
        return this.botaoVender;
    }

    public boolean mensagemHipotecar() {
        return this.botaoHipotecar;
    }

    public boolean mensagemDeCarta() {
        return this.casaDeCarta;
    }

    public Carta obtemCartaSorteada() {
        return this.cartaAtual;
    }

    public Propriedade obtemPropriedadeAtual() {
        return this.propriedadeAtual;
    }

    public int obtemTipoMensagem() {
        return this.tipoMensagem;
    }
}
