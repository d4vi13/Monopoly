package Nucleo.Grafico;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.*;

import Nucleo.Aux.Dupla;
import Nucleo.Aux.Posicoes;
import Nucleo.Aux.Posicoes.Posicao;

public class PropriedadesG {
    private Posicao[] posMelhorias;
    private int[] nivelMelhorias;
    private static int NUMERO_CASAS = 32;
    private Image up1, up2, up3, up4;
    private int tabX, tabY;
    private double escala;

    public PropriedadesG() {
        posMelhorias = new Posicao[NUMERO_CASAS];
        nivelMelhorias = new int[NUMERO_CASAS];
        for (int i = 0; i < NUMERO_CASAS; i++) {
            nivelMelhorias[i] = -1;
        }

        up1 = new ImageIcon("./Dados/Imagens/servidorN1.png").getImage();
        up2 = new ImageIcon("./Dados/Imagens/servidorN2.png").getImage();
        up3 = new ImageIcon("./Dados/Imagens/servidorN3.png").getImage();
        up4 = new ImageIcon("./Dados/Imagens/servidorN4.png").getImage();
    }

    public void atualizarPosicoesMelhorias(int tabPosx, int tabPosy, int tabDim) {
        tabX = tabPosx;
        tabY = tabPosy;
        escala = tabDim / 1156.0;
        for (int i = 0; i < NUMERO_CASAS; i++) {
            if (nivelMelhorias[i] != -1) {
                atualizarPosicaoMelhoria(i, posMelhorias[i]);
            }
        }
    }

    private void atualizarPosicaoMelhoria(int casa, Posicao p) {
        p.posX = (int)(escala * Posicoes.posUpgrades[casa].posX) + tabX;
        p.posY = (int)(escala * Posicoes.posUpgrades[casa].posY) + tabY;
    }

    public void adicionarMelhoria(int casa, int nivel) {
        nivelMelhorias[casa] = nivel;
        posMelhorias[casa] = new Posicao();
        atualizarPosicaoMelhoria(casa, posMelhorias[casa]);
    }

    public void atualizarMelhoria(int casa, int nivel) {
        nivelMelhorias[casa] = nivel;
    }

    public void removerMelhoria(int casa) {
        nivelMelhorias[casa] = -1;
    }

    public boolean temMelhoria(int casa) {
        return (nivelMelhorias[casa] != -1);
    }
    
    public Image obterImagemIconeUp(int casa) {
        switch (nivelMelhorias[casa]) {
            case 1: return up1;
            case 2: return up2;
            case 3: return up3;
            case 4: return up4;
            default:return null;
        }
    }

    public int obterAlt(int casa) {
        switch (nivelMelhorias[casa]) {
            case 1: return (int)(escala * 13);
            case 2: return (int)(escala * 26);
            case 3: return (int)(escala * 39);
            case 4: return (int)(escala * 51);
            default:return 0;
        }
    }

    public int obterComp(int i) {
        return (int)(escala * 45);
    }
    
    public Posicao obterPosicaoIconeUp(int casa) {
        return posMelhorias[casa];
    }
}
