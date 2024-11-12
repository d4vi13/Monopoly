package nucleo.grafico;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Dimension;

import nucleo.controle.Controle;
import nucleo.aux.EstadosJogo;
import static nucleo.aux.EstadosJogo.*;

public class Janela extends JPanel {
    private JFrame frame;
    private EventosTeclado eventosTeclado;
    private EventosMouse eventosMouse;
    private EventosTela eventosTela;
    private EstadosJogo estado;
    private Menu instanciaMenu;
    private Controle instanciaControle;

    //=========================================================================
    // Metodos publicos
    //=========================================================================
    public Janela(EstadosJogo e, Controle c) {
        estado = e;
        instanciaControle = c;
        instanciaMenu = new Menu(this);

        iniciarFrame();
        iniciarPanel();
        atualizarEstado(MENU);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public Controle getControle() {
        return this.instanciaControle;
    }

    public EventosMouse getEventosMouse() {
        return this.eventosMouse;
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
    }
    
    public void atualizarDimensoes(int comprimento, int altura) {
        instanciaMenu.setDimensoes(comprimento, altura);
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

    public void mouseAtualiza(MouseEvent e, int evento) {
        switch (estado.atual) {
            case MENU:
                instanciaMenu.mouseAtualiza(e, evento);
                break;
            default:
                break;
        }
    }

    //=========================================================================
    // Metodos privados
    //=========================================================================
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
        eventosTeclado = new EventosTeclado();
        eventosMouse = new EventosMouse(this);
        addKeyListener(eventosTeclado);
        addMouseListener(eventosMouse);
        addMouseMotionListener(eventosMouse);
        setLayout(null);
    }
}

class EventosTela implements ComponentListener {
    private JFrame frame;
    private Janela janela;

    public EventosTela(Janela janela) {
        this.janela = janela;
        this.frame = janela.getFrame();
    }
    
    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
        janela.atualizarDimensoes(frame.getWidth(), frame.getHeight());
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }
}

class EventosTeclado extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}

class EventosMouse extends MouseAdapter {
    private Janela janela;

    public EventosMouse(Janela janela) {
        this.janela = janela;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        janela.mouseAtualiza(e, MouseEvent.MOUSE_PRESSED);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        janela.mouseAtualiza(e, MouseEvent.MOUSE_MOVED);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        janela.mouseAtualiza(e, MouseEvent.MOUSE_RELEASED);
    }
}