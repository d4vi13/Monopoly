package Nucleo.Atributos;

import Nucleo.Atributos.Casa.Config;

public class Tabuleiro {
    private final static int TOTAL_CASAS = 32;
    private Casa[] casasTabuleiro;
    private Banco banco;

    public Tabuleiro() {
        this.casasTabuleiro = new Casa[TOTAL_CASAS];
        this.banco = new Banco();
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
