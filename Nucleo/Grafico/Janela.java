package Nucleo.Grafico;
import javax.swing.*;

import Nucleo.Aux.EstadosJogo;
import Nucleo.Controle.Controle;

import static Nucleo.Aux.EstadosJogo.*;

import java.awt.*;
import java.awt.event.*;

public class Janela extends JPanel {
    private JFrame frame;
    private EventosTeclado eventosTeclado;
    private EventosMouse eventosMouse;
    private EventosTela eventosTela;
    private EstadosJogo estado;
    private Menu instanciaMenu;
    private Controle instanciaControle;

    public Janela(EstadosJogo e, Controle c) {
        estado = e;
        instanciaControle = c;
        instanciaMenu = new Menu(this);

        iniciarFrame();
        iniciarPanel();
        atualizarEstado(MENU);
    }

    public Controle obterControle() {
        return this.instanciaControle;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        switch (estado.atual) {
            case MENU:
                instanciaMenu.pintar(g);
                break;
            default:
                break;
        }
        
        g.dispose();
    }
    
    public void atualizarDimensoes() {
        instanciaMenu.setDimensoes(frame.getWidth(), frame.getHeight());
    }

    public void atualizarEstado(int novoEstado) {
        estado.atual = novoEstado;
        switch (novoEstado) {
            case MENU:
                setBackground(new Color(0xd4fcd9));
                break;   
            default:
                break;
        }
    }

    public void mouseAtualiza(MouseEvent e) {
        switch (estado.atual) {
            case MENU:
                instanciaMenu.mouseAtualiza(e);
                break;
            default:
                break;
        }
    }

    public void tecladoAtualiza(KeyEvent e) {
        switch (estado.atual) {
            case MENU:
                instanciaMenu.tecladoAtualiza(e);
                break;
            default:
                break;
        }
    }

    private void iniciarFrame() {
        Dimension dimensaoTela = Toolkit.getDefaultToolkit().getScreenSize();
        int comp, alt;

        comp = (int)(0.8 * dimensaoTela.width);
        alt = (int)(0.8 * dimensaoTela.height);
        frame = new JFrame();
        frame.setSize(comp, alt);
        frame.add(this);
        eventosTela = new EventosTela(this);
        frame.addComponentListener(eventosTela);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void iniciarPanel() {
        eventosTeclado = new EventosTeclado(this);
        eventosMouse = new EventosMouse(this);
        addKeyListener(eventosTeclado);
        addMouseListener(eventosMouse);
        addMouseMotionListener(eventosMouse);
        setLayout(null);
    }
}

class EventosTela extends ComponentAdapter {
    private Janela janela;

    public EventosTela(Janela janela) {
        this.janela = janela;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        janela.atualizarDimensoes();
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