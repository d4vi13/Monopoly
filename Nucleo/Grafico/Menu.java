package Nucleo.Grafico;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.io.*;

class Menu {
    private Janela janela;
    private int frame_comprimento, frame_altura;
    private int logo_alt, logo_comp, logo_posx, logo_posy;
    private Image monopoly_logo;
    private Botao bIni, bCont, bSair;

    public Menu(Janela j) {
        Font fonte = null;
        try {
            File f = new File("./Dados/Fontes/HighMount_PersonalUse.otf");
            fonte = Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(28f);
        } catch(FontFormatException | IOException e) {
            System.out.println("Erro ao carregar fonte");
            System.exit(1);
        }

        monopoly_logo = new ImageIcon("./Dados/Imagens/monopoly_logo.png").getImage();
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

        comp = 320;
        alt = 96;
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

class CaixaTexto extends Componente {
    
    public void pintar(Graphics g) {
        
    }
}