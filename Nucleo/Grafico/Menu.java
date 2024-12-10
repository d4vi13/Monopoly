package Nucleo.Grafico;
import static Nucleo.Aux.EstadosJogo.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.io.*;

class Menu {
    private enum Estado{
        NAO_BACKUP,
        BACKUP;
    }

    private Janela janela;
    private int frameComprimento, frameAltura;
    private int logoAlt, logoComp, logoPosx, logoPosy;
    private int compComponentes, altComponentes;
    private Image monopoly_logo;
    private Botao botaoIniciar, botaoBackup, botaoSair;
    private CaixaTexto caixaBackup;
    private Estado estado;

    public Menu(Janela j) {
        Color[] coresBotoes = {Color.BLACK, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE};
        Color[] coresCaixa = {Color.BLACK, Color.WHITE, Color.LIGHT_GRAY};
        int raio = 40;
        Font fonteBotoes, fonteCaixa;
        File f1, f2;

        janela = j;
        estado = Estado.NAO_BACKUP;
        fonteBotoes = fonteCaixa = null;
        f1 = new File("./Dados/Fontes/HighMount_PersonalUse.otf");
        f2 = new File("./Dados/Fontes/times_new_roman.ttf");
        try {
            fonteBotoes = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(28f);
            fonteCaixa = Font.createFont(Font.TRUETYPE_FONT, f2).deriveFont(28f);
        } catch(FontFormatException | IOException e) {
            System.out.println("Erro ao carregar fonte");
            System.exit(1);
        }

        monopoly_logo = new ImageIcon("./Dados/Imagens/monopoly_logo.png").getImage();
        if (monopoly_logo == null) {
            System.err.println("Erro ao carregar logo");
            System.exit(1);
        }

        botaoIniciar = new Botao("Nova Partida", fonteBotoes, raio, coresBotoes);
        botaoBackup = new Botao("Continuar Partida", fonteBotoes, raio, coresBotoes);
        botaoSair = new Botao("Sair", fonteBotoes, raio, coresBotoes);
        caixaBackup = new CaixaTexto(fonteCaixa, raio, coresCaixa);
    }

    public void setDimensoes(int comprimento, int altura) {
        this.frameComprimento = comprimento;
        this.frameAltura = altura;
        definirTamanhoLogo();
        definirPosicaoLogo();
        definirTamanhoComponentes();
        definirPosicaoComponentes();
    }

    public void pintar(Graphics g) {
        g.drawImage(monopoly_logo, logoPosx, logoPosy, logoComp, logoAlt, null);
        if (estado == Estado.NAO_BACKUP) {
            botaoIniciar.pintar(g);
            botaoBackup.pintar(g);
        } else if (estado == Estado.BACKUP) {
            caixaBackup.pintar(g);
        }
        
        botaoSair.pintar(g);
    }

    public void tecladoAtualiza(KeyEvent e) {
        if (estado == Estado.BACKUP) {
            switch (e.getID()) {
                case KeyEvent.KEY_TYPED:
                    caixaBackup.teclaDigitada(e);
                    break;
                case KeyEvent.KEY_RELEASED:
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        janela.obterControle().acaoBotaoCarregarBackup(caixaBackup.obterString());
                        janela.atualizarEstado(JOGATINA);
                        return;
                    }
                    caixaBackup.teclaSolta(e);
                    break;
                default:
                    break;
            }
        }
    }

    public void mouseAtualiza(MouseEvent e) {
        switch (e.getID()) {
            case MouseEvent.MOUSE_MOVED:
                caixaBackup.mouseMoveu(e);
                botaoIniciar.mouseMoveu(e);
                botaoBackup.mouseMoveu(e);
                botaoSair.mouseMoveu(e);
                break;
            case MouseEvent.MOUSE_PRESSED:
                botaoIniciar.mousePressionado(e);
                botaoBackup.mousePressionado(e);
                botaoSair.mousePressionado(e);               
                break;
            case MouseEvent.MOUSE_RELEASED:
                if (estado == Estado.BACKUP) {
                    caixaBackup.mouseSolto(e);
                } else {
                    if (botaoIniciar.mouseSolto(e)) {   
                        janela.obterControle().acaoBotaoNovaPartida();
                        janela.atualizarEstado(CADASTRO);
                    } else if (botaoBackup.mouseSolto(e)) {
                        estado = Estado.BACKUP;
                    }
                }
                if (botaoSair.mouseSolto(e)) System.exit(0);
                break;
            default:
                break;
        }
    }

    private void definirTamanhoLogo() {
        double logo_prop;
        logo_prop = (double)monopoly_logo.getHeight(null) / monopoly_logo.getWidth(null);
        logoComp = (int)(0.6 * frameComprimento);
        logoAlt = (int)(logo_prop * logoComp);
    }
    
    private void definirPosicaoLogo() {
        logoPosx = (frameComprimento - logoComp) / 2;
        logoPosy = frameAltura / 3 - logoAlt;
    }

    private void definirTamanhoComponentes() {
        compComponentes = 320;
        altComponentes = 96;

        caixaBackup.definirDimensoes(compComponentes, altComponentes);
        botaoIniciar.definirDimensoes(compComponentes, altComponentes);
        botaoBackup.definirDimensoes(compComponentes, altComponentes);
        botaoSair.definirDimensoes(compComponentes, altComponentes);
    }

    private void definirPosicaoComponentes() {
        int posx, backupPosy, iniciarPosy, sairPosy;

        posx = (frameComprimento - compComponentes) / 2;
        iniciarPosy = logoPosy + logoAlt + frameAltura / 10;
        backupPosy = iniciarPosy + altComponentes + frameAltura / 40;
        sairPosy = backupPosy + altComponentes + frameAltura / 40;
        
        caixaBackup.definirLocalizacao(posx, backupPosy);
        botaoIniciar.definirLocalizacao(posx, iniciarPosy);
        botaoBackup.definirLocalizacao(posx, backupPosy);
        botaoSair.definirLocalizacao(posx, sairPosy);
    }
}