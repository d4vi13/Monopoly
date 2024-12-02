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
import java.util.ArrayList;

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
    private Botao botaoDados, botaoVender, botaoComprar, botaoHipotecar, botaoUpgrade;
    private boolean dadosLigado, venderLigado, comprarLigado, hipotecarLigado, upgradeLigado;
    // Jogadores
    private int numeroJogadores;
    private JogadorG[] jogadores;
    private int[] saldos;
    private int altIcone, compIcone;
    private int idJogadorAtual;
    private String[] informaJogador;
    private int casaDestino, casaAtual;
    private boolean falirLigado, cartaLigado;
    private Font fonteCarta1, fonteCarta2, fonteFalir, fonteInforma;
    // Timers
    private Timer temporizadorPulos, temporizadorGenerico;
    // Dados
    private int[] valoresDados;
    private StringBuilder[] stringDados;
    private boolean valoresLigado;
    private Font fonteNumeros;
    private MensagemJogador msg;
    // Estados
    private final int ATIVA_DADOS = 0;
    private final int ATUALIZA_DADOS = 1;
    private final int JOGADOR_NA_CASA = 2;
    private final int ATUALIZA_JOGADOR = 4;
    private int estadoAtual;

    public Partida(Janela j) {
        File f1, f2, f3;

        janela = j;
        opacidade = 1.0f;
        fonteBotoes = null;
        pauseAtivado = false;
        f1 = new File("./Dados/Fontes/HighMount_PersonalUse.otf");
        f2 = new File("./Dados/Fontes/Crashnumberinggothic.ttf");
        f3 = new File("./Dados/Fontes/times_new_roman.ttf");
        try {
            fonteBotoes = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(40f);
            fonteInforma = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(34f);
            fonteNumeros = Font.createFont(Font.TRUETYPE_FONT, f2).deriveFont(45f);
            fonteFalir = Font.createFont(Font.TRUETYPE_FONT, f3).deriveFont(80f);
            fonteCarta1 = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(31f);
            fonteCarta2 = Font.createFont(Font.TRUETYPE_FONT, f3).deriveFont(45f);
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
        carregarBotoes();
        pause = new MenuPause(this);
    }

    private void carregarBotoes() {
        Color[] cores1 = new Color[]{Color.BLACK, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE};

        botaoPause = new Botao("Pause", fonteBotoes, 20, cores1);
        botaoDados = new Botao(new ImageIcon("./Dados/Imagens/dados.png").getImage(), 20, cores1);
        botaoComprar = new Botao("Comprar", fonteBotoes, 20, cores1);
        botaoUpgrade = new Botao("Pause", fonteBotoes, 20, cores1);
        botaoVender = new Botao("Comprar", fonteInforma, 20, cores1);
        botaoHipotecar = new Botao("Hipotecar", fonteInforma, 20, cores1);
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
            boolean soma;
            if (casaDestino < casaAtual) {
                if (32 - casaAtual + casaDestino > casaAtual - casaDestino) {
                    soma = false;
                } else {
                    soma = true;
                }
            } else {
                if (32 - casaAtual + casaDestino > casaAtual - casaDestino) {
                    soma = true;
                } else {
                    soma = false;
                }
            }
            if (soma) casaAtual++;
            else casaAtual--;
            casaAtual %= 32;
            jogadores[idJogadorAtual].atualizarPosicao(casaAtual, tabuleiroPosx, tabuleiroPosy, tabuleiroComp);

            if (casaAtual == casaDestino) {
                ((Timer) e.getSource()).stop();
                fimTemporizadorPulos();
            }
        });

        temporizadorGenerico = new Timer(2200, e -> {
            fimTemporizadorGenerico();
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
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidade));
        
        botaoPause.pintar(g);
        pintarTabuleiro(g);
        pintarIcones(g);
        // pintarSaldoJogadores(g);
        pintarInformaJogador(g);

        // if (venderLigado || hipotecarLigado) {
        //     pintarSeleciona(g);
        //     if (venderLigado) botaoVender.pintar(g);
        //     if (hipotecarLigado) botaoHipotecar.pintar(g);
        // }
        if (dadosLigado) botaoDados.pintar(g);
        if (valoresLigado) pintarValoresDados(g2d);
        if (falirLigado) pintarJogadorFaliu(g);
        if (cartaLigado) pintarCarta(g2d);
        // if (comprarLigado) botaoComprar.pintar(g);
        if (pauseAtivado) pause.pintar(g);
    }

    private void pintarSaldoJogadores(Graphics g) {

    }

    private void pintarSeleciona(Graphics g) {

    }

    private void pintarCarta(Graphics2D g2D) {
        int h = (int)(0.5 * frameAltura), w = (int)(0.6 * h), hTmp, wF, hF;
        int x = (frameComprimento - w) / 2, y = (frameAltura - h) / 2;
        String vetStr[], str, numero;
        int tipo = msg.obtemCartaSorteada().obtemTipo();
        final int raio = 20;

        g2D.setColor(Color.BLACK);
        g2D.fillRoundRect(x, y, w, h, raio, raio);
        g2D.setColor(Color.LIGHT_GRAY);
        g2D.fillRoundRect(x + 4, y + 4, w - 8, h - 8, raio, raio);
        g2D.setColor(Color.BLACK);

        vetStr = msg.obtemCartaSorteada().obtemDescricao();
        g2D.setFont(fonteBotoes);
        hF = g2D.getFontMetrics().getAscent() - g2D.getFontMetrics().getDescent();
        wF = g2D.getFontMetrics().stringWidth("Carta");
        g2D.drawString("Carta", x + (w - wF) / 2, y + hF + 20);
        hTmp = h;
        g2D.setFont(fonteCarta1);
        for (String s : vetStr) {
            wF = g2D.getFontMetrics().stringWidth(s);
            g2D.drawString(s, x + (w - wF) / 2, y + hTmp / 2);
            hTmp += g2D.getFontMetrics().getHeight() + 30;
        }

        if (tipo == 0 || tipo == 6 || tipo == 1) {
            if (tipo == 0 || tipo == 6) str = "+";
            else str = "-";

            numero = Integer.toString(msg.obtemCartaSorteada().obtemValor());
            g2D.setFont(fonteNumeros);
            wF = g2D.getFontMetrics().stringWidth(numero);
            g2D.setFont(fonteCarta2);
            wF += g2D.getFontMetrics().stringWidth(str);
            g2D.drawString(str, x + (w - wF) / 2, y + h - 50);
            wF -= 2 * g2D.getFontMetrics().stringWidth(str);
            g2D.setFont(fonteNumeros);
            g2D.drawString(numero, x + (w - wF) / 2, y + h - 50);
        }
    }

    private void pintarTabuleiro(Graphics g) {
        int tam;
        Image icon;
        Posicao p;
        JogadorG j;

        for (int k = 0; k < numeroJogadores; k++) {
            j = jogadores[k];
            tam = j.obtemNumUpgrades();
            for (int i = 0; i < tam; i++) {
                icon = j.consultaImagemIconeUp(i);
                p = j.consultaPosicaoIconeUp(i);
                g.drawImage(icon, p.posX, p.posY, 30, 30, null);
            }
        }
        g.drawImage(tabuleiro, tabuleiroPosx, tabuleiroPosy, tabuleiroComp, tabuleiroAlt, null);
    }

    private void pintarIcones(Graphics g) {
        JogadorG j;
        for (int i = 0; i < numeroJogadores; i++) {
            j = jogadores[i];
            g.drawImage(j.obterIcone(), j.obterX(), j.obterY(), compIcone, altIcone, null);
        }
    }

    private void pintarJogadorFaliu(Graphics g) {
        FontMetrics fm;
        int comp, alt;
        String str = jogadores[idJogadorAtual].obterNome() + " acaba de falir :(";

        g.setColor(Color.BLACK);
        g.setFont(fonteFalir);
        fm = g.getFontMetrics();
        comp = fm.stringWidth(str);
        alt = fm.getAscent();
        g.drawString(str, (frameComprimento - comp) / 2, frameAltura / 3 + alt / 2);
    }

    private void pintarInformaJogador(Graphics g) {
        g.setFont(fonteInforma);
        g.setColor(Color.BLACK);
        g.drawString(informaJogador[idJogadorAtual], 20, frameAltura  - g.getFontMetrics().getHeight() - 10);
    }

    private void pintarValoresDados(Graphics2D g2D) {
        Botao bd = botaoDados;
        FontMetrics fm;
        int x = bd.obterX(), y = bd.obterY(), w = bd.obterComp(), h = bd.obterAlt(), wF, hF;
        final int raio = 20;

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
        if (pauseAtivado == true) pause.tecladoAtualiza(e);
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
        int tmp1 = (int)(frameComprimento * 0.6), tmp2 = frameAltura - 100;
        if (tmp1 <= tmp2) tabuleiroComp = tabuleiroAlt = tmp1;
        else tabuleiroComp = tabuleiroAlt = tmp2;
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

    private void fimTemporizadorGenerico() {
        switch (estadoAtual) {
            // Jogador na prisao
            case ATUALIZA_DADOS:
                atualizarJogador();
                break;
            // Jogador faliu
            case JOGADOR_NA_CASA:
                cartaLigado = false;
                if (msg.obtemTipoEvento() == Eventos.tirouCartaDeMovimento) {
                    casaDestino += msg.obtemDeslocamentoDoJogador();
                    temporizadorPulos.start();
                } else {
                    atualizarJogador();
                }
                break;
            default:
                break;
        }
    }

    private void fimTemporizadorPulos() {
        switch (estadoAtual) {
            case ATUALIZA_DADOS:
                jogadorNaCasa();
                break;
            case JOGADOR_NA_CASA:
                atualizarJogador();
                break;
            default:
                break;
        }
    }

    /* Estados do jogo */
    public void ativarBotaoDados() {
        estadoAtual = ATIVA_DADOS;
        idJogadorAtual = janela.obterControle().obterIdJogadorAtual();
        janela.obterControle().carregarSaldos(saldos);
        dadosLigado = true;
        valoresLigado = false;
        comprarLigado = false;
        venderLigado = false;
        hipotecarLigado = false;
        falirLigado = false;
        cartaLigado = false;
    }
    
    public void dadosJogados() {
        Controle controle = janela.obterControle();
        
        estadoAtual = ATUALIZA_DADOS;
        controle.acaoBotaoJogarDados();
        valoresDados = controle.obterNumerosD6();
        if (stringDados[0].length() != 0) stringDados[0].deleteCharAt(0);
        if (stringDados[1].length() != 0) stringDados[1].deleteCharAt(0);
        stringDados[0].append(Integer.toString(valoresDados[0]));
        stringDados[1].append(Integer.toString(valoresDados[1]));
        valoresLigado = true;
        dadosLigado = false;
        
        casaAtual = controle.obterCasaAtualJogador();
        casaDestino = (11) % 32;

        msg = controle.decifraCasa(casaDestino);
        if (msg.obtemTipoEvento() == Eventos.jogadorTaPreso) {
            temporizadorGenerico.start();
        } else {
            temporizadorPulos.start();
        }
    }

    public void jogadorNaCasa() {
        estadoAtual = JOGADOR_NA_CASA;
        switch (msg.obtemTipoEvento()) {
            case Eventos.jogadorFaliu:
                falirLigado = true;
                temporizadorGenerico.start();
                break;
            case Eventos.semDonoPodeComprar:
                comprarLigado = true;
                break;
            case Eventos.tirouCarta:
                cartaLigado = true;
                temporizadorGenerico.start();
                break;
            case Eventos.tirouCartaDeMovimento:
                cartaLigado = true;
                temporizadorGenerico.start();
                break;
            case Eventos.vendaOuHipoteca:

                break;
            default:
                atualizarJogador();
                break;
        }
    }

    public void atualizarJogador() {
        estadoAtual = ATUALIZA_JOGADOR;
        do {
            janela.obterControle().proximoJogador();
        } while (janela.obterControle().atualStatusFalido());
        ativarBotaoDados();
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