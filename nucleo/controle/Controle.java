package nucleo.controle;
import nucleo.atributos.Banco;
import nucleo.atributos.Jogador;
import nucleo.aux.ListaCircular;
import static nucleo.aux.EstadosJogo.*;

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
