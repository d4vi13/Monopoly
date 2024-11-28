package Nucleo.Aux;

import Nucleo.Atributos.Propriedade;
import Nucleo.Atributos.Cartas.Carta;

/* EVENTOS POSSÍVEIS */
/* 0 = Jogador na casa inicial
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

public class MensagemJogador {
    private Carta cartaAtual;
    private Propriedade propriedadeAtual;
    private int tipoEvento;

    public MensagemJogador() {};
    public void atualizaMensagem(Carta cartaAtual, Propriedade propriedadeAtual, int tipoEvento) {
        this.cartaAtual = cartaAtual;
        this.propriedadeAtual = propriedadeAtual;
        this.tipoEvento = tipoEvento;
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
}
