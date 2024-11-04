package nucleo.controle;
import nucleo.grafico.Frame;
import nucleo.grafico.Panel;

public class Controle {
    Panel painel; 
    Frame janela;

    public Controle() {
        painel = new Panel(this);
        janela = new Frame(painel);
        painel.requestFocus();
    }
}
