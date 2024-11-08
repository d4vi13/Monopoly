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

    void acaoBotaoVender() {

    }

    void acaoBotaoHipotecar() {

    }

    void acaoBotaoComprar() {
        
    }
}
