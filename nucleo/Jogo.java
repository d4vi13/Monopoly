package nucleo;
import nucleo.grafico.*;
import nucleo.controle.*;
import static nucleo.aux.EstadosJogo.*;


public class Jogo implements Runnable {
    Panel painel; 
    Frame janela; 
    Controle controle;
    Partida tela;
    Menu menu;
    Integer estadoJogo;
    Thread thread;

    Jogo() {
        controle = new Controle();
        tela = new Partida();
        painel = new Panel(estadoJogo, menu);
        janela = new Frame(painel);
        painel.requestFocus();
    }

    void IniciarJogo() {
        estadoJogo = MENU;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        final double tempoFrame = 1000000000.0 / 30;
        long ultimoFrame = System.nanoTime();

        while (true) {
            if (System.nanoTime() - ultimoFrame >= tempoFrame) {
                painel.repaint();
                ultimoFrame = System.nanoTime();
            }
        }
    }

    public static void main(String[] args) {
        Jogo jogatina = new Jogo();    
        jogatina.IniciarJogo();
    }
}