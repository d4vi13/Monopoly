package Nucleo.Grafico;
import static Nucleo.Aux.EstadosJogo.*;
import Nucleo.Grafico.Componente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.io.*;

public class Final {
    private int frameComprimento, frameAltura;
    private JogadorG[] jogadores;
    private Janela janela;
    private Timer temporizador;
    private Image fundo;
    private Font fonte;
    private int id;

    public Final(Janela j) {
        File f1;

        f1 = new File("./Dados/Fontes/HighMount_PersonalUse.otf");
        try {
            fonte = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(200f);
        } catch(FontFormatException | IOException e) {
            System.out.println("Erro ao carregar fonte");
            System.exit(1);
        }

        janela = j;
        fundo = new ImageIcon("./Dados/Imagens/festa.gif").getImage();
        jogadores = janela.obterControle().obterJogadoresG();
        id = janela.obterControle().obterIdJogadorAtual();
    }

    public void setDimensoes(int comprimento, int altura) {
        frameAltura = altura;
        frameComprimento = comprimento;
    }

    public void pintar(Graphics g) {
        int altT, compT;
        String tmp = jogadores[id].obterNome() + " Ganhou";

        g.drawImage(fundo, 0, 0, frameComprimento, frameAltura, null);

        g.setFont(fonte);
        altT = g.getFontMetrics().getAscent();
        compT = g.getFontMetrics().stringWidth(tmp);
        g.setColor(Color.BLACK);
        g.drawString(tmp, (frameComprimento - compT) / 2, (frameAltura + altT) / 2);
    }
}
