package nucleo.grafico;
import javax.swing.JPanel;
import javax.print.attribute.standard.JobName;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.ComponentEvent;

import static nucleo.aux.EstadosJogo.*;

public class Janela extends JPanel {
    private JFrame frame;
    private EventosTeclado eventosTeclado;
    private EventosMouse eventosMouse;
    private EventosTela eventosTela;
    private Integer estadoJogo;
    private Menu instanciaMenu;

    //=========================================================================
    // Metodos publicos
    //=========================================================================
    public Janela(Integer estadoJogo) {
        this.estadoJogo = estadoJogo;
        instanciaMenu = new Menu();

        iniciarFrame();
        iniciarPanel();
        instanciaMenu.setJanela(this);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public EventosMouse getEventosMouse() {
        return this.eventosMouse;
    }

    public void atualizarDimensoes(int comprimento, int altura) {
        instanciaMenu.setDimensoes(comprimento, altura);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        switch (estadoJogo.intValue()) {
            case MENU:
                instanciaMenu.pintar(g);
                break;
            default:
                break;
        }
    }

    public void mouseAtualiza(MouseEvent e, int evento) {
        switch (estadoJogo.intValue()) {
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
        frame = new JFrame();
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