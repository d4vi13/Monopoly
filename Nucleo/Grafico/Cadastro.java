package Nucleo.Grafico;
import static Nucleo.Aux.EstadosJogo.*;
import Nucleo.Grafico.Componente;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Cadastro extends Grafico {
    final private int NUMERO_CAIXAS = 6;
    private Janela janela;
    private int frameComprimento, frameAltura;
    private CaixaTexto[] caixas;
    private Font ftHighMount_40, ftHighMount_30;
    private String titulo, aviso;
    private int numeroJogadores;
    private Botao btSair;

    public Cadastro(Janela j) {
        Color[] coresCaixa = {Color.BLACK, Color.WHITE, Color.LIGHT_GRAY};
        Color[] coresBotao = {Color.BLACK, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE};
        int raio = 40;
        Font ftTimesNRoman_28;
        File f1, f2;

        janela = j;
        numeroJogadores = 0;
        titulo = "Cadastro dos Jogadores";
        aviso = "Insira pelo menos dois jogadores";
        ftHighMount_40 = ftTimesNRoman_28 = null;
        f1 = new File("./Dados/Fontes/HighMount_PersonalUse.otf");
        f2 = new File("./Dados/Fontes/times_new_roman.ttf");
        try {
            ftHighMount_40 = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(40f);
            ftHighMount_30 = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(30f);
            ftTimesNRoman_28 = Font.createFont(Font.TRUETYPE_FONT, f2).deriveFont(28f);
        } catch(FontFormatException | IOException e) {
            System.out.println("Erro ao carregar fonte");
            System.exit(1);
        }

        caixas = new CaixaTexto[NUMERO_CAIXAS];
        for (int i = 0; i < NUMERO_CAIXAS; i++) {
            caixas[i] = new CaixaTexto(ftTimesNRoman_28, raio, coresCaixa);
        }

        btSair = new Botao("Sair", ftHighMount_40, 20, coresBotao);
    }

    @Override
    public void setDimensoes(int comprimento, int altura) {
        this.frameComprimento = comprimento;
        this.frameAltura = altura;
        setDimensoesCaixas();
        btSair.definirDimensoes(160, 48);
        btSair.definirLocalizacao(20, 20);
    }

    @Override
    public void pintar(Graphics g) {
        FontMetrics fm;
        int compTitulo;

        g.setFont(ftHighMount_40);
        fm = g.getFontMetrics();
        compTitulo = fm.stringWidth(titulo);
        g.setColor(Color.BLACK);
        g.drawString(titulo, (frameComprimento - compTitulo) / 2, (frameAltura / 5) - fm.getAscent());

        if (numeroJogadores < 2) {
            g.setColor(Color.RED);
            g.setFont(ftHighMount_30);
            compTitulo = g.getFontMetrics().stringWidth(aviso);
            g.drawString(aviso, 20, frameAltura - g.getFontMetrics().getHeight() - 10);
        }

        for (int i = 0; i < NUMERO_CAIXAS; i++) {caixas[i].pintar(g);}
        btSair.pintar(g);
    }

    @Override
    public void tecladoAtualiza(KeyEvent e) {
        String[] vetNomes;

        switch (e.getID()) {
            case KeyEvent.KEY_TYPED:
                for (int i = 0; i < NUMERO_CAIXAS; i++) {
                    caixas[i].teclaDigitada(e);
                }
                break;
            case KeyEvent.KEY_RELEASED:
                if (e.getKeyCode() == KeyEvent.VK_ENTER && numeroJogadores >= 2) {
                    vetNomes = new String[NUMERO_CAIXAS];
                    for (int i = 0; i < numeroJogadores; i++) {
                        vetNomes[i] = caixas[i].obterString();
                    }
                    janela.obterControle().cadastrarJogadores(vetNomes, numeroJogadores);
                    janela.atualizarEstado(JOGATINA);
                }
                break;
            default:
                break;
        }

        numeroJogadores = 0;
        for (int i = 0; i < NUMERO_CAIXAS; i++) {
            caixas[i].teclaSolta(e);
            if (caixas[i].obterString().length() > 0) {
                numeroJogadores++;
            }
        }
    }

    @Override
    public void mouseAtualiza(MouseEvent e) {
        int anterior = (numeroJogadores == 0) ? numeroJogadores : numeroJogadores - 1;
        int atual = (numeroJogadores == 6) ? numeroJogadores - 1 : numeroJogadores;

        switch (e.getID()) {
            case MouseEvent.MOUSE_MOVED:
                caixas[anterior].mouseMoveu(e);
                caixas[atual].mouseMoveu(e);
                btSair.mouseMoveu(e);
                break;
            case MouseEvent.MOUSE_PRESSED:
                btSair.mousePressionado(e);
                break;
            case MouseEvent.MOUSE_RELEASED:
                caixas[anterior].mouseSolto(e);
                caixas[atual].mouseSolto(e);
                if (btSair.mouseSolto(e)) System.exit(0);
                break;
            default:
                break;
        }
    }

    private void setDimensoesCaixas() {
        int comp, alt, posy;

        comp = 400;
        alt = 90;

        for (int i = 0; i < NUMERO_CAIXAS; i++) {
            caixas[i].definirDimensoes(comp, alt);
            posy = (frameAltura / 4) + (i * frameAltura / 40) + alt * i;
            caixas[i].definirLocalizacao((frameComprimento - comp) / 2, posy);
        }
    }
}