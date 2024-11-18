package nucleo.grafico;
import java.awt.*;
import java.awt.event.*;

class Botao extends Componente {
    private Color corBorda, corMouseSobre, corClicado, corPadrao, corAtual;
    private boolean mouseSobre;
    private int raio;
    private String identificacao;
    private Font fonte;

    public Botao(String identificacao, Font fonte) {
        this.identificacao = identificacao;
        this.fonte = fonte;
    }

    public void definirCores(Color cB, Color cMS, Color cC, Color cP) {
        corBorda = cB;
        corMouseSobre = cMS;
        corClicado = cC;
        corPadrao = corAtual = cP;
    }

    public void definirRaio(int r) {
        raio = r;
    }

    public void mouseMoveu(MouseEvent e) {
        if (contem(e.getX(), e.getY())) {
            corAtual = corMouseSobre;
            mouseSobre = true;
        } else {
            corAtual = corPadrao;
            mouseSobre = false;
        }
    }

    public void mousePressionado(MouseEvent e) {
        if (mouseSobre) {
            corAtual = corClicado;
        }
    }
    
    /* Retorna true se botao foi ativado */
    public boolean mouseSolto(MouseEvent e) {
        if (contem(e.getX(), e.getY())) {
            corAtual = corMouseSobre;
        } else {
            mouseSobre = false;
            corAtual = corPadrao;
        }
        
        return mouseSobre;
    }

    public void pintar(Graphics g) {
        int compT, altT;
        Graphics2D g2D = (Graphics2D)g;
        FontMetrics fm;

        g2D.setFont(fonte);
        fm = g2D.getFontMetrics();
        compT = fm.stringWidth(identificacao);
        altT = fm.getAscent();

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setColor(corBorda);
        g2D.fillRoundRect(posx, posy, comp, alt, raio, raio);
        g2D.setColor(corAtual);
        g2D.fillRoundRect(posx + 2, posy + 2, comp - 4, alt - 4, raio, raio);
        g2D.setColor(corBorda);
        g2D.drawString(identificacao, posx + (comp - compT) / 2, posy + (alt + altT) / 2);
    }
}