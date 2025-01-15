package Nucleo.Grafico;

import Nucleo.Controle.Controle;
import static Nucleo.Aux.EstadosJogo.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

abstract class Grafico {
    abstract public void pintar(Graphics g);
    abstract public void setDimensoes(int comprimento, int altura);
    public void tecladoAtualiza(KeyEvent e) {};
    public void mouseAtualiza(MouseEvent e) {};


    public void desenhaString() {

    }

    public void desenhaVetorStrings() {

    }

    public void desenhaImagem(Graphics g, Image img, int w, int h, int x, int y) {
        g.drawImage(img, x, y, null);
    }
}

public class Janela extends JPanel {
    private JFrame frame;
    private EventosTeclado eventosTeclado;
    private EventosMouse eventosMouse;
    private Controle instanciaControle;
    private Grafico instanciaAtual;
    private Timer fade;
    private float opacidade;

    public Janela(Controle c) {
        iniciarFrame();
        iniciarPanel();
        instanciaControle = c;
        opacidade = 0.0f;
        instanciaAtual = new Menu(this);
        atualizarDimensoes();
        addKeyListener(eventosTeclado);
        addMouseListener(eventosMouse);
        addMouseMotionListener(eventosMouse);
        frame.setVisible(true);
    }

    public Controle obterControle() {
        return this.instanciaControle;
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        super.paint(g);
        
        instanciaAtual.pintar(g);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidade));
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        g2d.dispose();
    }

    public void atualizarEstado(int novoEstado) {
        fade = new Timer(30, _ -> {
            opacidade += 0.05f;
            if (opacidade > 1.0f) {
                opacidade = 0.0f;
                fade.stop();
                switch (novoEstado) {
                    case CADASTRO: instanciaAtual = new Cadastro(this); break;
                    case JOGATINA: instanciaAtual = new Partida(this); break;
                    case FINAL: instanciaAtual = new Final(this); break;
                    default: break;
                }
                atualizarDimensoes();
            }
        });
        fade.start();
    }
    
    public void atualizarDimensoes() {
        instanciaAtual.setDimensoes(frame.getWidth(), frame.getHeight());
    }

    public void mouseAtualiza(MouseEvent e) {
        instanciaAtual.mouseAtualiza(e);
    }

    public void tecladoAtualiza(KeyEvent e) {
        instanciaAtual.tecladoAtualiza(e);
    }

    private void iniciarFrame() {
        int comp = 1920, alt = 1000;

        frame = new JFrame();
        frame.setSize(comp, alt);
        frame.add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    private void iniciarPanel() {
        eventosTeclado = new EventosTeclado(this);
        eventosMouse = new EventosMouse(this);
        setLayout(null);
        setBackground(new Color(0xd4fcd9));
    }
}

class EventosTeclado extends KeyAdapter {
    private Janela janela;

    public EventosTeclado(Janela janela) {
        this.janela = janela;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        janela.tecladoAtualiza(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        janela.tecladoAtualiza(e);
    }
}

class EventosMouse extends MouseAdapter {
    private Janela janela;

    public EventosMouse(Janela janela) {
        this.janela = janela;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        janela.mouseAtualiza(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        janela.mouseAtualiza(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        janela.mouseAtualiza(e);
    }
}