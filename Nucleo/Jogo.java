package Nucleo;

import Nucleo.Controle.*;
import Nucleo.Grafico.*;

public class Jogo implements Runnable {
    Janela janela;
    Controle controle;
    Thread thread;

    Jogo() {
        controle = new Controle();
        janela = new Janela(controle);
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