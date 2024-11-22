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
    private Cadastro instanciaCadastro;
    private Controle instanciaControle;
    private volatile float opacidade;

    public Janela(EstadosJogo e, Controle c) {
        estado = e;
        instanciaControle = c;
        instanciaMenu = new Menu(this);
        instanciaCadastro = new Cadastro(this);

        iniciarFrame();
        iniciarPanel();
        estado.atual = MENU;
        opacidade = 0.0f;
    }

    public Controle obterControle() {
        return this.instanciaControle;
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        super.paint(g);
        
        switch (estado.atual) {
            case MENU:
                instanciaMenu.pintar(g);
                break;
            case CADASTRO:
                instanciaCadastro.pintar(g);
                break;
            default:
                break;
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidade));
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        
        g.dispose();
    }

    public void atualizarEstado(int novoEstado) { 
        Timer timer = new Timer(50, e -> {
            opacidade += 0.05f;
            if (opacidade > 1.0f) {
                opacidade = 0.0f;
                ((Timer) e.getSource()).stop();
                estado.atual = novoEstado;
                atualizarDimensoes();
            }
        });
        timer.start();
    }
    
    public void atualizarDimensoes() {
        switch (estado.atual) {
            case MENU:
                instanciaMenu.setDimensoes(frame.getWidth(), frame.getHeight());
                break;
            case CADASTRO:
                instanciaCadastro.setDimensoes(frame.getWidth(), frame.getHeight());
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
            case CADASTRO:
                instanciaCadastro.mouseAtualiza(e);
            default:
                break;
        }
    }

    public void tecladoAtualiza(KeyEvent e) {
        switch (estado.atual) {
            case MENU:
                instanciaMenu.tecladoAtualiza(e);
                break;
            case CADASTRO:
                instanciaCadastro.tecladoAtualiza(e);
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
        setBackground(new Color(0xd4fcd9));
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