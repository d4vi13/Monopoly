package Nucleo.Grafico;
import static Nucleo.Aux.EstadosJogo.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.io.*;
import Nucleo.Grafico.Componente;

public class Cadastro {
    final private int NUMERO_CAIXAS = 6;
    private Janela janela;
    private int frame_comprimento, frame_altura;
    private CaixaTexto[] caixas;
    Font fonteTitulo, fonteAviso;
    String titulo, aviso;
    int numeroJogadores;

    public Cadastro(Janela j) {
        Color[] coresCaixa = {Color.BLACK, Color.WHITE, Color.LIGHT_GRAY};
        int raio = 40;
        Font fonteCaixa;
        File f;

        fonteTitulo = fonteCaixa = null;
        try {
            f = new File("./Dados/Fontes/HighMount_PersonalUse.otf");
            fonteTitulo = Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(40f);
            fonteAviso = Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(30f);
            f = new File("./Dados/Fontes/times_new_roman.ttf");
            fonteCaixa = Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(28f);
        } catch(FontFormatException | IOException e) {
            System.out.println("Erro ao carregar fonte");
            System.exit(1);
        }

        caixas = new CaixaTexto[NUMERO_CAIXAS];
        for (int i = 0; i < NUMERO_CAIXAS; i++) {
            caixas[i] = new CaixaTexto(fonteCaixa, raio, coresCaixa);
        }

        numeroJogadores = 0;
        titulo = "Cadastro dos Jogadores";
        aviso = "Insira pelo menos dois jogadores";
        janela = j;
    }

    public void setDimensoes(int comprimento, int altura) {
        this.frame_comprimento = comprimento;
        this.frame_altura = altura;
        setDimensoesCaixas();
    }

    public void pintar(Graphics g) {
        FontMetrics fm;
        int compTitulo;

        g.setFont(fonteTitulo);
        fm = g.getFontMetrics();
        compTitulo = fm.stringWidth(titulo);
        g.setColor(Color.BLACK);
        g.drawString(titulo, (frame_comprimento - compTitulo) / 2, (frame_altura / 5) - fm.getAscent());

        if (numeroJogadores < 2) {
            g.setFont(fonteAviso);
            fm = g.getFontMetrics();
            compTitulo = fm.stringWidth(aviso);
            g.setColor(Color.RED);
            g.drawString(aviso, 20, frame_altura - fm.getHeight() - 10);
        }

        for (int i = 0; i < NUMERO_CAIXAS; i++) {
            caixas[i].pintar(g);
        }
    }

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

    public void mouseAtualiza(MouseEvent e) {
        int aux = (numeroJogadores == 6) ? numeroJogadores - 1 : numeroJogadores;

        switch (e.getID()) {
            case MouseEvent.MOUSE_MOVED:
                for (int i = 0; i <= aux; i++) {
                    caixas[i].mouseMoveu(e);
                }
                break;
            case MouseEvent.MOUSE_RELEASED:
                for (int i = 0; i <= aux; i++) {
                    caixas[i].mouseSolto(e);
                }
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
            posy = (frame_altura / 4) + (i * frame_altura / 40) + alt * i;
            caixas[i].definirLocalizacao((frame_comprimento - comp) / 2, posy);
        }
    }
}