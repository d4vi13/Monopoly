package Nucleo.Grafico;
import static Nucleo.Aux.EstadosJogo.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;
import java.io.*;

public class Final {
    private int frameComprimento, frameAltura;
    private Janela janela;
    private String nome;
    private Timer temporizador;
    private Image fundo;
    private Font fonte;
    private int id, contador, it;
    private Color cor;

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
        id = janela.obterControle().obterIdJogadorAtual();
        nome = janela.obterControle().obterJogadoresG()[id].obterNome();
        cor = Color.BLACK;
        it = contador = 0;

        temporizador = new Timer(200, _ -> {
            if (contador++ == 15) {System.exit(0);}

            it++;
            it %= 5;
            switch (it) {
                case 0: cor = Color.BLUE; break;
                case 1: cor = Color.RED; break;
                case 2: cor = Color.ORANGE; break;
                case 3: cor = Color.GREEN; break;
                case 4: cor = Color.YELLOW; break;
                default: break;
            }
        });

        temporizador.start();
    }

    public void setDimensoes(int comprimento, int altura) {
        frameAltura = altura;
        frameComprimento = comprimento;
    }

    public void pintar(Graphics g) {
        int altT, compT;
        String tmp = nome + " Ganhou";

        g.drawImage(fundo, 0, 0, frameComprimento, frameAltura, null);

        g.setFont(fonte);
        altT = g.getFontMetrics().getAscent();
        compT = g.getFontMetrics().stringWidth(tmp);
        g.setColor(cor);
        g.drawString(tmp, (frameComprimento - compT) / 2, (frameAltura + altT) / 2);
    }
}
