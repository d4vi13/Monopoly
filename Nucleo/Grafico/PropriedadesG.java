package Nucleo.Grafico;

import Nucleo.Aux.Dupla;
import java.util.ArrayList;
import java.awt.*;

public class PropriedadesG {
    private ArrayList<Dupla<Integer, Posicao>> posicoesUpgrades;
    private ArrayList<Integer> iconesUpgrades;
    private Image icone, up1, up2, up3, up4;
    private int id, tabDim, tabX, tabY;
    private double escala;

    public PropriedadesG() {
        posicoesUpgrades = new ArrayList<>();
        iconesUpgrades = new ArrayList<>();
    }

    public void atualizarPosicoesUpgrades(int tabPosx, int tabPosy, int tabDim) {
        Dupla<Integer, Posicao> d;
        
        this.tabDim = tabDim;
        tabX = tabPosx;
        tabY = tabPosy;
        escala = tabDim / 1156.0;
        for (int i = 0; i < posicoesUpgrades.size(); i++) {
            d = posicoesUpgrades.get(i);
            atualizarPosicaoUpgrade(d.primeiro, d.segundo);
        }
    }

    private void atualizarPosicaoUpgrade(int casa, Posicao p) {
        p.posX = (int)(escala * Posicoes.posUpgrades[casa].posX) + tabX;
        p.posY = (int)(escala * Posicoes.posUpgrades[casa].posY) + tabY;
    }

    public void adicionarUpgrade(int casa, int nivel) {
        Posicao p;

        posicoesUpgrades.add(new Dupla<Integer, Posicao>(casa, p = new Posicao()));
        atualizarPosicaoUpgrade(casa, p);
        iconesUpgrades.add(nivel);
    }

    public void atualizarUpgrade(int casa, int nivel) {
        for (int i = 0; i < posicoesUpgrades.size(); i++) {
            if (posicoesUpgrades.get(i).primeiro == casa) {
                iconesUpgrades.add(i, nivel);
                break;
            }
        }
    }

    public void removerUpgrade(int casa) {
        for (int i = 0; i < posicoesUpgrades.size(); i++) {
            if (posicoesUpgrades.get(i).primeiro == casa) {
                posicoesUpgrades.remove(i);
                iconesUpgrades.remove(i);
                return;
            }
        }
    }

    public int obtemNumUpgrades() {
        return iconesUpgrades.size();
    }
    
    public Image obterImagemIconeUp(int i) {
        switch (iconesUpgrades.get(i)) {
            case 0: return up1;
            case 1: return up2;
            case 2: return up3;
            case 3: return up4;
            default:return null;
        }
    }

    public int obterAlt(int i) {
        switch (iconesUpgrades.get(i)) {
            case 0: return (int)(escala * 13);
            case 1: return (int)(escala * 26);
            case 2: return (int)(escala * 39);
            case 3: return (int)(escala * 51);
            default:return 0;
        }
    }

    public int obterComp(int i) {
        return (int)(escala * 45);
    }
    
    public Posicao obterPosicaoIconeUp(int i) {
        return posicoesUpgrades.get(i).segundo;
    }
}
