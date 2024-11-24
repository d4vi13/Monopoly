package Nucleo.Grafico;
import static Nucleo.Aux.EstadosJogo.*;

import Nucleo.Atributos.Jogador;
import Nucleo.Aux.EstadosJogo;
import Nucleo.Aux.ListaCircular;
import Nucleo.Grafico.Componente;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.io.*;

interface Estado {
    void atualizarEstado();
    void pintar(Graphics g);
}

public class Partida {  
    private Janela janela;
    private int frameComprimento, frameAltura;
    // Tabuleiro
    private int tabuleiroComp, tabuleiroAlt, tabuleiroPosx, tabuleiroPosy;
    private Image tabuleiro;
    // Pause
    private boolean pauseAtivado;
    private MenuPause pause;
    private Botao botaoPause;
    private Font fonteBotoes;
    private float opacidade;
    // Estados
    private Estado[] estados;
    private Estado estadoAtual;
    // Botoes
    private Botao[] botoesDoJogo;
    private boolean[] botoesEstado;
    // Jogadores
    private ListaCircular<JogadorG> jogadores;
    private final int altIcone = 35;
    private final int compIcone = 35;

    public Partida(Janela j) {
        File f1;

        janela = j;
        opacidade = 1.0f;
        fonteBotoes = null;
        pauseAtivado = false;
        f1 = new File("./Dados/Fontes/HighMount_PersonalUse.otf");
        try {
            fonteBotoes= Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(40f);
        } catch(FontFormatException | IOException e) {
            System.out.println("Erro ao carregar fonte");
            System.exit(1);
        }

        tabuleiro = new ImageIcon("./Dados/Imagens/tabuleiro.png").getImage();
        if (tabuleiro == null) {
            System.out.println("Erro ao carregar tabuleiro");
            System.exit(1);
        }

        carregarJogadores();
        carregarEstados();
        estadoAtual = estados[0];
        pause = new MenuPause(this);
        botaoPause = new Botao("Pause", fonteBotoes, 20, new Color[]{Color.BLACK, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE});
    }

    private void carregarEstados() {
        estados = new Estado[10];
        estados[0] = new mostraTabuleiro();
        estados[1] = new ativaBotaoDados();
    }

    private void carregarJogadores() {
        JogadorG ini, atual;

        jogadores = janela.obterControle().obterJogadoresG();
        ini = jogadores.getIteradorElem();
        ini.atualizarPosicao(0, tabuleiroPosx, tabuleiroPosy);
        jogadores.iteradorProx();
        atual = jogadores.getIteradorElem();
        while (atual != ini) {
            atual.atualizarPosicao(0, tabuleiroPosx, tabuleiroPosy);
            jogadores.iteradorProx();
            atual = jogadores.getIteradorElem();
        }

    }

    public void setDimensoes(int comprimento, int altura) {
        this.frameComprimento = comprimento;
        this.frameAltura = altura;
        definirTamanhoTabuleiro();
        definirPosicaoTabuleiro();
        definirTamanhoComponentes();
        definirPosicaoComponentes();
    }

    public void pintar(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        JogadorG j;
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidade));
        g.drawImage(tabuleiro, tabuleiroPosx, tabuleiroPosy, null);
        botaoPause.pintar(g);

        // j = jogadores[0];
        // g.drawImage(j.obterIcone(), j.obterX(), j.obterY(), compIcone, altIcone, null);
        // estadoAtual.pintar(g);

        if (pauseAtivado == true) {
            pause.pintar(g);
        }
    }

    public void tecladoAtualiza(KeyEvent e) {
        if (pauseAtivado == true) {
            pause.tecladoAtualiza(e);
        }
    }

    public void mouseAtualiza(MouseEvent e) {
        if (pauseAtivado == true) {
            if (e.getID() == MouseEvent.MOUSE_MOVED) {
                botaoPause.mouseMoveu(e);
            }
            pause.mouseAtualiza(e);
            return;
        }

        switch (e.getID()) {
            case MouseEvent.MOUSE_MOVED:
                botaoPause.mouseMoveu(e);
                break;
            case MouseEvent.MOUSE_PRESSED:
                botaoPause.mousePressionado(e);
                break;
            case MouseEvent.MOUSE_RELEASED:
                if (botaoPause.mouseSolto(e)) {
                    ativarPause();
                }
                break;
            default:
                break;
        }
    }

    private void definirTamanhoTabuleiro() {
        int tmp1, tmp2;

        tmp1 = (int)(frameComprimento * 0.6);
        tmp2 = frameAltura - 100;
        if (tmp1 <= tmp2) {
            tabuleiroComp = tabuleiroAlt = tmp1;
        } else {
            tabuleiroComp = tabuleiroAlt = tmp2;
        }

        System.out.println(tabuleiroComp + " " + tabuleiroAlt);
    }

    private void definirPosicaoTabuleiro() {
        tabuleiroPosy = (frameAltura - tabuleiroAlt) / 2;
        tabuleiroPosx = (frameComprimento - tabuleiroComp) / 2;
    }

    private void definirTamanhoComponentes() {
        botaoPause.definirDimensoes(160, 48);
    }

    private void definirPosicaoComponentes() {
        botaoPause.definirLocalizacao(20, tabuleiroPosy);
    }

    void ativarPause() {
        pause.setDimensoes(frameComprimento, frameAltura);
        opacidade = 0.5f;
        pauseAtivado = true;
    }

    void desativarPause() {
        opacidade = 1.0f;
        pauseAtivado = false;
    }

    public Janela obterJanela() {
        return janela;
    }

    /* Classes Internas (estados) */
    class mostraTabuleiro implements Estado {
        @Override
        public void atualizarEstado() {
            
        }
    
        @Override
        public void pintar(Graphics g) {
            
        }
    }
    
    class ativaBotaoDados implements Estado {
        @Override
        public void atualizarEstado() {
            
        }
    
        @Override
        public void pintar(Graphics g) {
            
        }
    }
    
    class mostraSomaDados implements Estado {
        @Override
        public void atualizarEstado() {
            
        }
    
        @Override
        public void pintar(Graphics g) {
            
        }
    }
}

class MenuPause {
    private enum Estado{
        NAO_SALVAR,
        SALVAR;
    }

    private Partida jogatina;
    private int frameComprimento, frameAltura;
    private Botao botaoSalvar, botaoSair;
    private int compComponentes, altComponentes;
    private CaixaTexto caixaBackup;
    private Estado estado;

    public MenuPause(Partida j) {
        Color[] coresBotoes = {Color.BLACK, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE};
        Color[] coresCaixa = {Color.BLACK, Color.WHITE, Color.LIGHT_GRAY};
        int raio = 40;
        Font fonteBotoes, fonteCaixa;
        File f;

        jogatina = j;
        estado = Estado.NAO_SALVAR;
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
        botaoSair = new Botao("Sair", fonteBotoes, raio, coresBotoes);
        caixaBackup = new CaixaTexto(fonteCaixa, raio, coresCaixa);
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
        if (estado == Estado.SALVAR) {
            caixaBackup.pintar(g);
        } else {
            botaoSalvar.pintar(g);
        }

        botaoSair.pintar(g);
    }

    public void tecladoAtualiza(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            jogatina.desativarPause();
            return;
        }

        if (estado == Estado.SALVAR) {
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
                caixaBackup.mouseMoveu(e);
                break;
            case MouseEvent.MOUSE_PRESSED:
                botaoSalvar.mousePressionado(e);
                botaoSair.mousePressionado(e);             
                break;
            case MouseEvent.MOUSE_RELEASED:
                if (estado == Estado.SALVAR) {
                    caixaBackup.mouseSolto(e);
                } else if (botaoSalvar.mouseSolto(e)) {
                    estado = Estado.SALVAR;
                }

                if (botaoSair.mouseSolto(e)) {
                    System.exit(0);
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
        caixaBackup.definirDimensoes(compComponentes, altComponentes);
    }
    
    private void definirPosicaoComponentes() {
        int posy, posx;
        int alturaTotal = 2 * altComponentes + (int)(frameAltura / 40);

        posx = (frameComprimento - compComponentes) / 2;
        posy = (frameAltura - alturaTotal) / 2;
        botaoSalvar.definirLocalizacao(posx, posy);
        caixaBackup.definirLocalizacao(posx, posy);
        posy += altComponentes + (int)(frameAltura / 40);
        botaoSair.definirLocalizacao(posx, posy);
    }
}