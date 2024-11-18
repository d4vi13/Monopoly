package Nucleo.Controle;
import static Nucleo.Aux.EstadosJogo.*;

import Nucleo.Atributos.Banco;
import Nucleo.Atributos.Jogador;
import Nucleo.Aux.ListaCircular;

public class Controle {
    private ListaCircular<Jogador> jogadores;
    private Banco banco;

    public Controle() {
        jogadores = new ListaCircular<Jogador>();
        banco = new Banco();
    }

    public void acaoBotaoVender() {

    }

    public void acaoBotaoHipotecar() {

    }

    public void acaoBotaoComprar() {

    }

    public void acaoBotaoBackup() {
        System.out.println("Botão de backup foi pressionado"); 
    }

    public void acaoBotaoNovaPartida() {
        System.out.println("Botão nova partida foi pressionado");
    }
}
