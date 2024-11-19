package Nucleo.Atributos;

import Nucleo.Atributos.Casa.Config;

public class Tabuleiro {
    private final static int TOTAL_CASAS = 32;
    private Casa[] casasTabuleiro;

    public Tabuleiro() {
        this.casasTabuleiro = new Casa[TOTAL_CASAS];
    }

    /* Novo Jogo */
    public void gerarVetorCasas () {
        casasTabuleiro[0] = new Casa(0, Config.tipoInicial);
    }

    /* Continuar Jogo */
    public void carregarVetorCasas () {
        casasTabuleiro[0] = new Casa(0, Config.tipoInicial);
    }
}
