package nucleo.grafico;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Frame {
    private JFrame janela;

    public Frame(Panel painel) {
        Dimension tamTela;

        tamTela = Toolkit.getDefaultToolkit().getScreenSize();

        janela = new JFrame();
        janela.add(painel);
        janela.setSize((int)(0.8 * tamTela.getWidth()), (int)(0.8 * tamTela.getHeight()));
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }
}