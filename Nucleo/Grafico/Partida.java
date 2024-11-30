package Nucleo.Grafico;
import static Nucleo.Aux.EstadosJogo.*;

import Nucleo.Atributos.Jogador;
import Nucleo.Aux.EstadosJogo;
import Nucleo.Grafico.Componente;
import Nucleo.Controle.Controle;
import Nucleo.Aux.MensagemJogador;
import Nucleo.Aux.MensagemJogador.Eventos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

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
    private float opacidade;
    // Botoes
    private Font fonteBotoes;
    private Botao botaoDados;
    private boolean dadosLigado;
    private Botao botaoVender;
    private boolean venderLigado;
    private Botao botaoComprar;
    private boolean comprarLigado;
    private Botao botaoHipotecar;
    private boolean hipotecarLigado;
    // Jogadores
    private int numeroJogadores;
    private JogadorG[] jogadores;
    private int[] saldos;
    private int altIcone;
    private int compIcone;
    private int idJogadorAtual;
    private String[] informaJogador;
    private Font fonteInforma;
    private int casaDestino;
    private int casaAtual;
    // Timers
    private Timer temporizadorPulos;
    private Timer temporizadorGenerico;
    // Dados
    private int[] valoresDados;
    private StringBuilder[] stringDados;
    private boolean valoresLigado;
    private Font fonteNumeros;
    private MensagemJogador msg;
    // Teclas
    private boolean enterLigado;

    public Partida(Janela j) {
        File f1, f2;
        Color[] cores1;

        janela = j;
        opacidade = 1.0f;
        fonteBotoes = null;
        pauseAtivado = false;
        f1 = new File("./Dados/Fontes/HighMount_PersonalUse.otf");
        f2 = new File("./Dados/Fontes/Crashnumberinggothic.ttf");
        try {
            fonteBotoes = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(40f);
            fonteInforma = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(34f);
            fonteNumeros = Font.createFont(Font.TRUETYPE_FONT, f2).deriveFont(45f);
        } catch(FontFormatException | IOException e) {
            System.out.println("Erro ao carregar fonte");
            System.exit(1);
        }

        tabuleiro = new ImageIcon("./Dados/Imagens/tabuleiro.png").getImage();
        if (tabuleiro == null) {
            System.out.println("Erro ao carregar tabuleiro");
            System.exit(1);
        }

        carregarTemporizadores();
        carregarJogadores();
        ativarBotaoDados();

        pause = new MenuPause(this);
        cores1 = new Color[]{Color.BLACK, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE};
        botaoPause = new Botao("Pause", fonteBotoes, 20, cores1);
        botaoDados = new Botao(new ImageIcon("./Dados/Imagens/dados.png").getImage(), 20, cores1);
    }

    private void carregarJogadores() {
        numeroJogadores = janela.obterControle().obterNumeroJogadores();
        jogadores = janela.obterControle().obterJogadoresG();
        saldos = new int[numeroJogadores];
        stringDados = new StringBuilder[2];
        stringDados[0] = new StringBuilder(2);
        stringDados[1] = new StringBuilder(2);
        informaJogador = new String[numeroJogadores];
        for (int i = 0; i < numeroJogadores; i++) {
            informaJogador[i] = jogadores[i].obterNome() + " joga";
        }
    }

    private void carregarTemporizadores() {
        temporizadorPulos = new Timer(200, e -> {
            casaAtual++;
            jogadores[idJogadorAtual].atualizarPosicao(casaAtual, tabuleiroPosx, tabuleiroPosy, tabuleiroComp);

            if (casaAtual % 32 == casaDestino) {
                ((Timer) e.getSource()).stop();
                jogadorNaCasa();
            }
        });

        temporizadorGenerico = new Timer(200, e -> {
            ((Timer) e.getSource()).stop();
        });
    }

    public void setDimensoes(int comprimento, int altura) {
        this.frameComprimento = comprimento;
        this.frameAltura = altura;
        definirTamanhoTabuleiro();
        definirPosicaoTabuleiro();
        definirTamanhoComponentes();
        definirPosicaoComponentes();
        for (int i = 0; i < numeroJogadores; i++) {
            jogadores[i].atualizarPosicao(0, tabuleiroPosx, tabuleiroPosy, tabuleiroComp);
        }
        altIcone = compIcone = (int)(35 * tabuleiroComp / 1156.f);
    }

    public void pintar(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        JogadorG j;
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidade));
        g.drawImage(tabuleiro, tabuleiroPosx, tabuleiroPosy, tabuleiroComp, tabuleiroAlt, null);
        
        botaoPause.pintar(g);
        if (dadosLigado) botaoDados.pintar(g);
        if (valoresLigado) pintarValoresDados(g2d);

        for (int i = 0; i < numeroJogadores; i++) {
            j = jogadores[i];
            g.drawImage(j.obterIcone(), j.obterX(), j.obterY(), compIcone, altIcone, null);
        }

        g.setFont(fonteInforma);
        g.setColor(Color.BLACK);
        g.drawString(informaJogador[idJogadorAtual], 20, frameAltura - 20);

        if (pauseAtivado == true) {pause.pintar(g);}
    }

    private void pintarValoresDados(Graphics2D g2D) {
        Botao bd = botaoDados;
        FontMetrics fm;
        int x, y, w, h, wF, hF;
        final int raio = 20;

        x = bd.obterX();
        y = bd.obterY();
        w = bd.obterComp();
        h = bd.obterAlt();
        g2D.setFont(fonteNumeros);
        fm = g2D.getFontMetrics();
        hF = fm.getAscent() - fm.getDescent();
        
        g2D.setColor(Color.BLACK);
        g2D.fillRoundRect(x, y, w, h, raio, raio);
        g2D.setColor(Color.WHITE);
        g2D.fillRoundRect(x + 2, y + 2, w - 4, h - 4, raio, raio);
        g2D.setColor(Color.BLACK);
        wF = fm.stringWidth(stringDados[0].toString());
        g2D.drawString(stringDados[0].toString(), x + (w - wF) / 2, y + (h + hF) / 2);

        x += w + 10;
        g2D.setColor(Color.BLACK);
        g2D.fillRoundRect(x, y, w, h, raio, raio);
        g2D.setColor(Color.WHITE);
        g2D.fillRoundRect(x + 2, y + 2, w - 4, h - 4, raio, raio);
        g2D.setColor(Color.BLACK);
        wF = fm.stringWidth(stringDados[1].toString());
        g2D.drawString(stringDados[1].toString(), x + (w - wF) / 2, y + (h + hF) / 2);
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
                if (dadosLigado) botaoDados.mouseMoveu(e);
                break;
            case MouseEvent.MOUSE_PRESSED:
                botaoPause.mousePressionado(e);
                if (dadosLigado) botaoDados.mousePressionado(e);
                break;
            case MouseEvent.MOUSE_RELEASED:
                if (botaoPause.mouseSolto(e)) ativarPause();
                if (dadosLigado) 
                    if (botaoDados.mouseSolto(e)) dadosJogados();
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
    }

    private void definirPosicaoTabuleiro() {
        tabuleiroPosy = (frameAltura - tabuleiroAlt) / 2;
        tabuleiroPosx = (frameComprimento - tabuleiroComp) / 2;
    }

    private void definirTamanhoComponentes() {
        botaoPause.definirDimensoes(160, 48);
        botaoDados.definirDimensoes((int)(0.0417 * frameComprimento), (int)(0.0417 * frameComprimento));
    }

    private void definirPosicaoComponentes() {
        botaoPause.definirLocalizacao(20, tabuleiroPosy);
        botaoDados.definirLocalizacao(20, tabuleiroPosy + botaoPause.obterAlt() + 50);
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
    public void ativarBotaoDados() {
        idJogadorAtual = janela.obterControle().obterIdJogadorAtual();
        janela.obterControle().carregarSaldos(saldos);
        dadosLigado = true;
        valoresLigado = false;
        comprarLigado = false;
        venderLigado = false;
        hipotecarLigado = false;
        enterLigado = false;
    }
    
    public void dadosJogados() {
        Controle controle = janela.obterControle();
        
        controle.acaoBotaoJogarDados();
        valoresDados = controle.obterNumerosD6();
        if (stringDados[0].length() != 0) stringDados[0].deleteCharAt(0);
        if (stringDados[1].length() != 0) stringDados[1].deleteCharAt(0);
        stringDados[0].append(Integer.toString(valoresDados[0]));
        stringDados[1].append(Integer.toString(valoresDados[1]));
        valoresLigado = true;
        dadosLigado = false;
        
        casaAtual = controle.obterCasaAtualJogador();
        casaDestino = (casaAtual + valoresDados[0] + valoresDados[1]) % 32;

        msg = controle.decifraCasa(casaDestino);
        if (msg.obtemTipoEvento() == Eventos.jogadorTaPreso) {
            temporizadorGenerico.start();
            jogadorNaCasa();
        } else {
            temporizadorPulos.start();
        }
    }

    public void jogadorNaCasa() {
        MensagemJogador m = janela.obterControle().decifraCasa(casaDestino);
        switch (m.obtemTipoEvento()) {
            case Eventos.jogadorFaliu:

                temporizadorGenerico.start();
                break;
            case Eventos.semDonoPodeComprar:

                break;
            case Eventos.tirouCarta:
            case Eventos.tirouCartaEfoiPreso:
                retirouCarta();
                break;
            case Eventos.vendaOuHipoteca:

                break;
            default:
                atualizarJogador();
                break;
        }
    }

    public void atualizarJogador() {
        do {
            janela.obterControle().proximoJogador();
        } while (janela.obterControle().atualStatusFalido());
    }

    public void retirouCarta() {
        
        temporizadorGenerico.start();
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