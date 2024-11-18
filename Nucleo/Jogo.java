package Nucleo;
import static Nucleo.Aux.EstadosJogo.*;

import Nucleo.Aux.EstadosJogo;
import Nucleo.Controle.*;
import Nucleo.Grafico.*;

public class Jogo implements Runnable {
    Janela janela;
    Controle controle;
    EstadosJogo estado;
    Thread thread;

    Jogo() {
        estado = new EstadosJogo();
        controle = new Controle();
        janela = new Janela(estado, controle);
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