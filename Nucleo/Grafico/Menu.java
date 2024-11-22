package Nucleo.Grafico;
import static Nucleo.Aux.EstadosJogo.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.io.*;
import Nucleo.Grafico.Componente;

class Menu {
    private enum Estado{
        NADA_CLICADO,
        CONTINUAR_CLICADO;
    }

    private Janela janela;
    private int frame_comprimento, frame_altura;
    private int logo_alt, logo_comp, logo_posx, logo_posy;
    private Image monopoly_logo;
    private Botao bIni, bCont, bSair;
    private CaixaTexto caixaT;
    private Estado estado;

    public Menu(Janela j) {
        Color[] coresBotoes = {Color.BLACK, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE};
        Color[] coresCaixa = {Color.BLACK, Color.WHITE, Color.LIGHT_GRAY};
        int raio = 40;
        Font fonteBotoes, fonteCaixa;
        File f;

        fonteBotoes = fonteCaixa = null;
        try {
            f = new File("./Dados/Fontes/HighMount_PersonalUse.otf");
            fonteBotoes = Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(28f);
            f = new File("./Dados/Fontes/times_new_roman.ttf");
            fonteCaixa = Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(28f);
        } catch(FontFormatException | IOException e) {
            System.out.println("Erro ao carregar fonte");
            System.exit(1);
        }

        monopoly_logo = new ImageIcon("./Dados/Imagens/monopoly_logo.png").getImage();
        if (monopoly_logo == null) {
            System.err.println("Erro ao carregar logo");
            System.exit(1);
        }

        bIni = new Botao("Nova Partida", fonteBotoes, raio, coresBotoes);
        bCont = new Botao("Continuar Partida", fonteBotoes, raio, coresBotoes);
        bSair = new Botao("Sair", fonteBotoes, raio, coresBotoes);
        caixaT = new CaixaTexto(fonteCaixa, raio, coresCaixa);
        
        estado = Estado.NADA_CLICADO;
        janela = j;
    }

    public void setDimensoes(int comprimento, int altura) {
        this.frame_comprimento = comprimento;
        this.frame_altura = altura;
        setDimensoesLogo();
        setDimensoesComponentes();
    }

    public void pintar(Graphics g) {
        g.drawImage(monopoly_logo, logo_posx, logo_posy, logo_comp, logo_alt, null);
        if (estado == Estado.NADA_CLICADO) {
            bIni.pintar(g);
            bCont.pintar(g);
        } else if (estado == Estado.CONTINUAR_CLICADO) {
            caixaT.pintar(g);
        }
        
        bSair.pintar(g);
    }

    public void tecladoAtualiza(KeyEvent e) {
        if (estado == Estado.CONTINUAR_CLICADO) {
            switch (e.getID()) {
                case KeyEvent.KEY_TYPED:
                    caixaT.teclaDigitada(e);
                    break;
                case KeyEvent.KEY_RELEASED:
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        janela.obterControle().acaoBotaoBackup(caixaT.obterString());
                        janela.atualizarEstado(CADASTRO);
                        return;
                    }
                    caixaT.teclaSolta(e);
                    break;
                default:
                    break;
            }
        }
    }

    public void mouseAtualiza(MouseEvent e) {
        switch (e.getID()) {
            case MouseEvent.MOUSE_MOVED:
                if (estado == Estado.CONTINUAR_CLICADO) {
                    caixaT.mouseMoveu(e);
                } else {
                    bIni.mouseMoveu(e);
                    bCont.mouseMoveu(e);
                }
                bSair.mouseMoveu(e);
                break;
            case MouseEvent.MOUSE_PRESSED:
                bIni.mousePressionado(e);
                bCont.mousePressionado(e);
                bSair.mousePressionado(e);               
                break;
            case MouseEvent.MOUSE_RELEASED:
                if (estado == Estado.CONTINUAR_CLICADO) {
                    caixaT.mouseSolto(e);
                } else {
                    if (bIni.mouseSolto(e)) {
                        janela.obterControle().acaoBotaoNovaPartida();
                    } else if (bCont.mouseSolto(e)) {
                        estado = Estado.CONTINUAR_CLICADO;
                    }
                }
                if (bSair.mouseSolto(e)) System.exit(0);
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

    private void setDimensoesComponentes() {
        int comp, alt, posx, contPosy, iniPosy, sairPosy;

        comp = 320;
        alt = 96;
        posx = (frame_comprimento - comp) / 2;
        iniPosy = logo_posy + logo_alt + frame_altura / 10;
        contPosy = iniPosy + alt + frame_altura / 40;
        sairPosy = contPosy + alt + frame_altura / 40;
        
        caixaT.definirDimensoes(comp, alt);
        caixaT.definirLocalizacao(posx, contPosy);
        bIni.definirDimensoes(comp, alt);
        bIni.definirLocalizacao(posx, iniPosy);
        bCont.definirDimensoes(comp, alt);
        bCont.definirLocalizacao(posx, contPosy);
        bSair.definirDimensoes(comp, alt);
        bSair.definirLocalizacao(posx, sairPosy);
    }
}