package nucleo.grafico;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class Menu {
    private Janela instanciaJanela;
    private int frame_comprimento, frame_altura;
    private int logo_alt, logo_comp, logo_posx, logo_posy;
    private Image monopoly_logo;
    private int bIni_alt, bIni_comp, bIni_posx, bIni_posy;
    private int bCont_alt, bCont_comp, bCont_posx, bCont_posy;
    private Botao bIniciar, bContinuar;
    private Color corFundo;

    //=========================================================================
    // Metodos publicos
    //=========================================================================
    public Menu() {
        monopoly_logo = new ImageIcon("../dados/imagens/monopoly_logo.png").getImage();
        if (monopoly_logo == null) {
            System.err.println("Erro ao carregar logo");
            System.exit(1);
        }

        bIniciar = new Botao("Nova Partida");
        bContinuar = new Botao("Continuar Partida");
        corFundo = new Color(0xd4fcd9);
    }

    public void setJanela(Janela janela) {
        instanciaJanela = janela;
        bIniciar.addMouseListener(janela.getEventosMouse());
        bIniciar.addMouseMotionListener(janela.getEventosMouse());
        bContinuar.addMouseMotionListener(janela.getEventosMouse());
        bContinuar.addMouseListener(janela.getEventosMouse());
        instanciaJanela.add(bContinuar);
        instanciaJanela.add(bIniciar);
        instanciaJanela.setBackground(corFundo);
    }

    public void setDimensoes(int comprimento, int altura) {
        this.frame_comprimento = comprimento;
        this.frame_altura = altura;
        setDimensoesLogo();
        setDimensoesBotoes();
    }

    public void pintar(Graphics g) {
        g.drawImage(monopoly_logo, logo_posx, logo_posy, logo_comp, logo_alt, null);
    }

    public void mouseAtualiza(MouseEvent e, int evento) {
        boolean dentroBotaoIni;
        switch (evento) {
            case MouseEvent.MOUSE_MOVED:
                dentroBotaoIni = bIniciar.mouseMoveu(e);
                if (!dentroBotaoIni) bContinuar.mouseMoveu(e);
                break;
            case MouseEvent.MOUSE_PRESSED:
                dentroBotaoIni = bIniciar.mousePressionado(e);
                if (!dentroBotaoIni) bContinuar.mousePressionado(e);
                break;
            case MouseEvent.MOUSE_RELEASED:
                dentroBotaoIni = bIniciar.mouseSolto(e);
                if (!dentroBotaoIni) bContinuar.mouseSolto(e);
                break;
            default:
                break;
        }
    }
    
    //=========================================================================
    // Metodos privados
    //=========================================================================
    private void setDimensoesLogo() {
        double logo_prop;
        logo_prop = (double)monopoly_logo.getHeight(null) / monopoly_logo.getWidth(null);
        logo_comp = (int)(0.6 * frame_comprimento);
        logo_alt = (int)(logo_prop * logo_comp);
        logo_posx = (frame_comprimento - logo_comp) / 2;
        logo_posy = frame_altura / 3 - logo_alt;
    }

    private void setDimensoesBotoes() {
        bIni_comp = bCont_comp = (int)(0.3 * frame_comprimento);
        bIni_alt = bCont_alt = (int)(0.3 * bIni_comp);
        bIni_posx = bCont_posx = (frame_comprimento - bIni_comp) / 2;
        bIni_posy = logo_posy + logo_alt + frame_altura / 10;
        bCont_posy = bIni_posy + bIni_alt + frame_altura / 20;
        bIniciar.setBounds(bIni_posx, bIni_posy, bIni_comp, bIni_alt);
        bContinuar.setBounds(bCont_posx, bCont_posy, bCont_comp, bCont_alt);
    }
}

class Botao extends JButton {
    private Color corBorda, corMouseSobre, corClicado, corPadrao;
    private int raio;
    private boolean mouseSobre;

    public Botao(String Identificacao) {
        super(Identificacao);
        setContentAreaFilled(false);
        setBorderPainted(false);

        corPadrao = Color.WHITE;
        corMouseSobre = Color.LIGHT_GRAY;
        corClicado = Color.GRAY;
        corBorda = Color.BLACK;
        raio = 40;
        setBackground(corPadrao);
    }

    public boolean mouseMoveu(MouseEvent e) {
        if (e.getComponent() == this) {
            setBackground(corMouseSobre);
            mouseSobre = true;
        } else {
            setBackground(corPadrao);
            mouseSobre = false;
        }
        return mouseSobre;
    }

    public boolean mousePressionado(MouseEvent e) {
        if (mouseSobre) {
            setBackground(corClicado);
        }
        return mouseSobre;
    }

    public void mouseSaiu(MouseEvent e) {
        if (e.getComponent() == this) {
            System.out.println("Saiu");
        }
    }

    public boolean mouseSolto(MouseEvent e) {
        if (mouseSobre) {
            System.out.println("botao pressionado");
            setBackground(corMouseSobre);
        }
            
        return mouseSobre;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setColor(corBorda);
        g2D.fillRoundRect(0, 0, getWidth(), getHeight(), raio, raio);
        g2D.setColor(getBackground());
        g2D.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, raio, raio);
        super.paint(g);
        g2D.dispose();
    }
}