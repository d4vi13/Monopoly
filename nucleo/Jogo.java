package nucleo;
import nucleo.grafico.*;
import nucleo.controle.*;
import static nucleo.aux.EstadosJogo.*;

public class Jogo implements Runnable {
    Janela janela;
    Controle controle;
    
    Integer estadoJogo;
    Thread thread;

    Jogo() {
        estadoJogo = Integer.valueOf(MENU);
        controle = new Controle();
        janela = new Janela(estadoJogo);
        janela.requestFocus();
    }

    void IniciarJogo() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        final double tempoFrame = 1000000000.0 / 30;
        long ultimoFrame = System.nanoTime();

        while (true) {
            if (System.nanoTime() - ultimoFrame >= tempoFrame) {
                janela.repaint();
                ultimoFrame = System.nanoTime();
            }
        }
    }

    public static void main(String[] args) {
        Jogo jogatina = new Jogo();    
        jogatina.IniciarJogo();
    }
}