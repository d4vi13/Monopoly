package Nucleo.Grafico;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

class MenuPause {
    private Partida jogatina;
    private int frameComprimento, frameAltura;
    private Botao botaoSalvar, botaoSair, botaoVoltar;
    private int compComponentes, altComponentes;
    private CaixaTexto caixaBackup;
    private int estado;
    private final int NAO_SALVAR = 0;
    private final int SALVAR = 1;

    public MenuPause(Partida j) {
        Color[] coresBotoes = {Color.BLACK, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE};
        Color[] coresCaixa = {Color.BLACK, Color.WHITE, Color.LIGHT_GRAY};
        int raio = 40;
        Font fonteBotoes, fonteCaixa;
        File f;

        jogatina = j;
        estado = NAO_SALVAR;
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

        botaoSalvar = new Botao("Salvar Partida", fonteBotoes, raio, coresBotoes);
        botaoVoltar = new Botao("Voltar", fonteBotoes, raio, coresBotoes);
        botaoSair = new Botao("Sair", fonteBotoes, raio, coresBotoes);
        caixaBackup = new CaixaTexto(fonteCaixa, raio, coresCaixa);
    }

    public void reconfigurar() {
        caixaBackup.novaString();
        estado = NAO_SALVAR;
    }

    public void setDimensoes(int comprimento, int altura) {
        this.frameComprimento = comprimento;
        this.frameAltura = altura;
        definirTamanhoComponentes();
        definirPosicaoComponentes();
    }

    public void pintar(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        if (estado == SALVAR) {
            caixaBackup.pintar(g);
        }

        botaoSalvar.pintar(g);
        botaoVoltar.pintar(g);
        botaoSair.pintar(g);
    }

    public void tecladoAtualiza(KeyEvent e) {
        if (estado == SALVAR) {
            switch (e.getID()) {
                case KeyEvent.KEY_TYPED:
                    caixaBackup.teclaDigitada(e);
                    break;
                case KeyEvent.KEY_RELEASED:
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        jogatina.obterJanela().obterControle().acaoBotaoSalvarBackup(caixaBackup.obterString());
                    } else {     
                        caixaBackup.teclaSolta(e);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void mouseAtualiza(MouseEvent e) {
        switch (e.getID()) {
            case MouseEvent.MOUSE_MOVED:
                botaoSalvar.mouseMoveu(e);
                botaoSair.mouseMoveu(e);
                botaoVoltar.mouseMoveu(e);
                caixaBackup.mouseMoveu(e);
                break;
            case MouseEvent.MOUSE_PRESSED:
                botaoSalvar.mousePressionado(e);
                botaoSair.mousePressionado(e);
                botaoVoltar.mousePressionado(e);             
                break;
            case MouseEvent.MOUSE_RELEASED:
                if (estado == SALVAR) {
                    caixaBackup.mouseSolto(e);
                } else if (botaoSalvar.mouseSolto(e)) {
                    estado = SALVAR;
                }

                if (botaoSair.mouseSolto(e)) {
                    System.exit(0);
                } else if (botaoVoltar.mouseSolto(e)) {
                    jogatina.desativarPause();
                }
                break;
            default:
                break;
        }
    }

    private void definirTamanhoComponentes() {
        altComponentes = 96;
        compComponentes = 320;

        botaoSair.definirDimensoes(compComponentes, altComponentes);
        botaoSalvar.definirDimensoes(compComponentes, altComponentes);
        botaoVoltar.definirDimensoes(compComponentes, altComponentes);
        caixaBackup.definirDimensoes(compComponentes, altComponentes);
    }
    
    private void definirPosicaoComponentes() {
        int posy, posx;
        int alturaTotal = 3 * altComponentes + 2 * 30;

        posx = (frameComprimento - compComponentes) / 2;
        posy = (frameAltura - alturaTotal) / 2;
        botaoSalvar.definirLocalizacao(posx, posy);
        caixaBackup.definirLocalizacao(posx + compComponentes + 10, posy);
        posy += altComponentes + 30;
        botaoVoltar.definirLocalizacao(posx, posy);
        posy += altComponentes + 30;
        botaoSair.definirLocalizacao(posx, posy);
    }
}
