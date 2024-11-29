package Nucleo.Grafico;
import java.awt.*;
import java.awt.event.*;

abstract class Componente {
    protected int posx, posy, comp, alt;

    public void definirDimensoes(int comp, int alt) {
        this.comp = comp;
        this.alt = alt;
    }

    public int obterAlt() {
        return alt;
    }

    public int obterComp() {
        return comp;
    }

    public void definirLocalizacao(int posx, int posy) {
        this.posx = posx;
        this.posy = posy;
    }

    public boolean contem(int x, int y) {
        return ((x >= posx && x <= posx + comp) &&
                (y >= posy && y <= posy + alt));
    }
}

class Botao extends Componente {
    private Color corBorda, corTexto, corMouseSobre, corClicado, corPadrao, corAtual;
    private boolean mouseSobre;
    private String identificacao;
    private Image icone;
    private Font fonte;
    private int raio;
    private final int iconeOff = 10;

    public Botao(String i, Font f, int r, Color[] vetCor) {
        identificacao = i;
        icone = null;
        InicializacaoPadrao(f, r, vetCor);
    }

    public Botao(Image i, int r, Color[] vetCor) {
        icone = i;
        identificacao = null;
        InicializacaoPadrao(null, r, vetCor);
    }

    private void InicializacaoPadrao(Font f, int r, Color[] vetCor) {
        fonte = f;
        raio = r;
        mouseSobre = false;

        corBorda = corTexto = vetCor[0];
        corMouseSobre = vetCor[1];
        corClicado = vetCor[2];
        corPadrao = corAtual = vetCor[3];
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

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setColor(corBorda);
        g2D.fillRoundRect(posx, posy, comp, alt, raio, raio);
        g2D.setColor(corAtual);
        g2D.fillRoundRect(posx + 2, posy + 2, comp - 4, alt - 4, raio, raio);
        if (icone == null) {
            g2D.setFont(fonte);
            fm = g2D.getFontMetrics();
            compT = fm.stringWidth(identificacao);
            altT = fm.getAscent();

            g2D.setColor(corTexto);
            g2D.drawString(identificacao, posx + (comp - compT) / 2, posy + (alt + altT) / 2);
        } else {
            g2D.drawImage(icone, posx + iconeOff / 2, posy + iconeOff / 2, comp - iconeOff, alt - iconeOff, null);
        }
    }
}

class CaixaTexto extends Componente {
    private Color corAtual, corTexto, corLivre, corBloqueado, corBorda;
    private int raio;
    private boolean selecionado, mouseSobre;
    private StringBuilder texto;
    private Font fonte;

    public CaixaTexto(Font f, int r, Color[] vetCor) {
        fonte = f;
        raio = r;

        corBorda = corTexto = vetCor[0];
        corLivre = vetCor[1];
        corBloqueado = corAtual = vetCor[2];
        selecionado = false;
        mouseSobre = true;

        texto = new StringBuilder(14);
    }

    public void mouseMoveu(MouseEvent e) {
        if (contem(e.getX(), e.getY())) {
            mouseSobre = true;
        } else {
            mouseSobre = false;
        }
    }

    public void mouseSolto(MouseEvent e) {
        if (contem(e.getX(), e.getY())) {
            if (mouseSobre == true) {
                selecionado = true;
                corAtual = corLivre;
            }
            mouseSobre = true; 
        } else {
            mouseSobre = selecionado = false;
            corAtual = corBloqueado;
        }
    }
 
    public void teclaDigitada(KeyEvent e) {
        char c = e.getKeyChar();
        if (selecionado == true && (Character.isLetter(c) || Character.isDigit(c))) {
            if (texto.length() < 14) {
                texto.append(c);
            }
        }
    }

    public void teclaSolta(KeyEvent e) {
        if (selecionado == true) {
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (texto.length() > 0) {
                    texto.deleteCharAt(texto.length() - 1);
                }
            }
        }
    }

    public String obterString() {
        return texto.toString();
    }
 
    public void pintar(Graphics g) {
        int altT;
        Graphics2D g2D = (Graphics2D)g;
        FontMetrics fm;

        g2D.setFont(fonte);
        fm = g2D.getFontMetrics();
        altT = fm.getAscent();

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setColor(corBorda);
        g2D.fillRoundRect(posx, posy, comp, alt, raio, raio);
        g2D.setColor(corAtual);
        g2D.fillRoundRect(posx + 2, posy + 2, comp - 4, alt - 4, raio, raio);
        g2D.setColor(corTexto);
        g2D.drawString(texto.toString(), (int)(posx * 1.01), posy + (alt + altT) / 2);
    }
}