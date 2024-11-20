package Nucleo.Controle;
import static Nucleo.Aux.EstadosJogo.*;

import Nucleo.Atributos.Banco;
import Nucleo.Atributos.Jogador;
import Nucleo.Atributos.Tabuleiro;
import Nucleo.Aux.ListaCircular;
import Nucleo.Atributos.D6;

public class Controle {
    private ListaCircular<Jogador> jogadores;
    private Tabuleiro tabuleiro;

    private D6 d6;
    private int[] numerosD6;

    public Controle() {
        jogadores = new ListaCircular<Jogador>();
        tabuleiro = new Tabuleiro();
    }

    public void acaoBotaoVender() {

    }

    public void acaoBotaoHipotecar() {

    }

    public void acaoBotaoComprar() {

    }

    public int[] acaoBotaoJogarDados() {
        d6 = new D6();
        numerosD6 = d6.jogaDado();
        return numerosD6;
    }

    public void acaoBotaoBackup(String arquivo) {

    }

    public void acaoBotaoNovaPartida() {
        tabuleiro.gerarVetorCasas();
        tabuleiro.imprimeVetor();
    }

    public void cadastrarJogadores(String[] vetNomes, int qtdJogadores) {

    }
}
