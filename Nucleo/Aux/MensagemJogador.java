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
 * 6 = Jogador na prisão
 * 7 = Tirou uma carta
 * 8 = Está no CAAD
 * 9 = Está na Recepção
 * 10 = Jogador faliu
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
