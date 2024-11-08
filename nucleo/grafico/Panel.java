package nucleo.grafico;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static nucleo.aux.EstadosJogo.*;

public class Panel extends JPanel {
    private EventosTeclado teclado;
    private EventosMouse mouse;
    private Integer estadoJogo;
    private Menu instanciaMenu;

    public Panel(Integer estadoJogo, Menu menu) {
        this.estadoJogo = estadoJogo;
        this.instanciaMenu = menu;
        
        teclado = new EventosTeclado();
        mouse = new EventosMouse();
        addKeyListener(teclado);
        addMouseListener(mouse);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        switch (estadoJogo) {
            case MENU:
                instanciaMenu.pintar(g);
                break;
            default:
                break;
        }
    }

}

class EventosTeclado implements KeyListener {

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

class EventosMouse implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
}