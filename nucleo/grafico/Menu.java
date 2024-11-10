package nucleo.grafico;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import nucleo.controle.Controle;

public class Menu {
    private Janela janela;
    private int frame_comprimento, frame_altura;
    private int logo_alt, logo_comp, logo_posx, logo_posy;
    private Image monopoly_logo;
    private Botao bIni, bCont, bSair;

    //=========================================================================
    // Metodos publicos
    //=========================================================================
    public Menu(Janela j) {
        Font fonte = null;
        try {
            File f = new File("../dados/fontes/HighMount_PersonalUse.otf");
            fonte = Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(28f);
        } catch(FontFormatException | IOException e) {
            System.out.println("Erro ao carregar fonte");
            System.exit(1);
        }

        monopoly_logo = new ImageIcon("../dados/imagens/monopoly_logo.png").getImage();
        if (monopoly_logo == null) {
            System.err.println("Erro ao carregar logo");
            System.exit(1);
        }

        bIni = new Botao("Nova Partida", fonte);
        bIni.definirCores(Color.BLACK, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE);
        bIni.definirRaio(40);

        bCont = new Botao("Continuar Partida", fonte);
        bCont.definirCores(Color.BLACK, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE);
        bCont.definirRaio(40);

        bSair = new Botao("Sair", fonte);
        bSair.definirCores(Color.BLACK, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE);
        bSair.definirRaio(40);
        
        janela = j;
    }

    public void setDimensoes(int comprimento, int altura) {
        this.frame_comprimento = comprimento;
        this.frame_altura = altura;
        setDimensoesLogo();
        setDimensoesBotoes();
    }

    public void pintar(Graphics g) {
        g.drawImage(monopoly_logo, logo_posx, logo_posy, logo_comp, logo_alt, null);
        bIni.pintar(g);
        bCont.pintar(g);
        bSair.pintar(g);
    }

    public void mouseAtualiza(MouseEvent e, int evento) {
        switch (evento) {
            case MouseEvent.MOUSE_MOVED:
                bIni.mouseMoveu(e);
                bCont.mouseMoveu(e);
                bSair.mouseMoveu(e);
                break;
            case MouseEvent.MOUSE_PRESSED:
                bIni.mousePressionado(e);
                bCont.mousePressionado(e);
                bSair.mousePressionado(e);               
                break;
            case MouseEvent.MOUSE_RELEASED:
                if (bIni.mouseSolto(e)) {
                    janela.getControle().acaoBotaoNovaPartida();
                } else if (bCont.mouseSolto(e)) {
                    janela.getControle().acaoBotaoBackup();
                } else if (bSair.mouseSolto(e)) {
                    System.exit(0);
                }
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
        int comp, alt, posx, contPosy, iniPosy, sairPosy;

        comp = (int)(0.3 * frame_comprimento);
        alt = (int)(0.3 * comp);
        posx = (frame_comprimento - comp) / 2;
        iniPosy = logo_posy + logo_alt + frame_altura / 10;
        contPosy = iniPosy + alt + frame_altura / 40;
        sairPosy = contPosy + alt + frame_altura / 40;

        bIni.definirDimensoes(comp, alt);
        bIni.definirLocalizacao(posx, iniPosy);
        bCont.definirDimensoes(comp, alt);
        bCont.definirLocalizacao(posx, contPosy);
        bSair.definirDimensoes(comp, alt);
        bSair.definirLocalizacao(posx, sairPosy);
    }
}

class Botao {
    private Color corBorda, corMouseSobre, corClicado, corPadrao, corAtual;
    private boolean mouseSobre;
    private int raio, posx, posy, comp, alt;
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

    public void definirDimensoes(int comp, int alt) {
        this.comp = comp;
        this.alt = alt;
    }

    public void definirLocalizacao(int posx, int posy) {
        this.posx = posx;
        this.posy = posy;
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
    
    // Retorna true se botao foi ativado
    public boolean mouseSolto(MouseEvent e) {
        if (contem(e.getX(), e.getY())) {
            if (mouseSobre) {
            }
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

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setColor(corBorda);
        g2D.fillRoundRect(posx, posy, comp, alt, raio, raio);
        g2D.setColor(corAtual);
        g2D.fillRoundRect(posx + 2, posy + 2, comp - 4, alt - 4, raio, raio);
        
        compT = fm.stringWidth(identificacao);
        altT = fm.getAscent();
        g2D.setColor(corBorda);
        g2D.drawString(identificacao, posx + (comp - compT) / 2, posy + (alt + altT) / 2);
    }

    private boolean contem(int x, int y) {
        return ((x >= posx && x <= posx + comp) &&
                (y >= posy && y <= posy + alt));
    }
}