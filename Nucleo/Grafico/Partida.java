package Nucleo.Grafico;
import static Nucleo.Aux.EstadosJogo.*;

import Nucleo.Controle.Controle;
import Nucleo.Aux.MensagemJogador;
import Nucleo.Aux.MensagemJogador.Eventos;
import Nucleo.Aux.Dupla;
import Nucleo.Aux.Posicoes.Posicao;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Arrays;

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
    private Font fonteHighMount_45;
    private Botao[] marcadores;
    private boolean[] estadosMarcadores;
    private ArrayList<Integer> selecoes, imoveisIDs;
    private ArrayList<String> nomesImoveis, valoresImoveis;
    private Image marcado, desmarcado;
    private Botao botaoDados, botaoVender, botaoComprar, botaoHipotecar, botaoUpgrade;
    private boolean dadosLigado, venderLigado, comprarLigado, hipotecarLigado, upgradeLigado;
    // Jogadores
    private PropriedadesG propriedades;
    private int numeroJogadores;
    private JogadorG[] jogadores;
    private String[] saldos;
    private int[] saldosInt;
    private int altIcone, compIcone;
    private int idJogadorAtual;
    private String[] informaJogador;
    private int casaDestino, casaAtual;
    private boolean falirLigado, cartaLigado;
    private Font fonteHighMount_25, fonteHighMount_31, fonteHighMount_34;
    private Font fonteTimesNRoman_45, fonteTimesNRoman_80, fonteTimesNRoman_25;
    // Timers
    private Timer temporizadorPulos, temporizadorGenerico;
    // Dados
    private int[] valoresDados;
    private StringBuilder[] stringDados;
    private boolean valoresLigado;
    private Font fonteNumberInGothic_45, fonteNumberInGothic_25;
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
        fonteHighMount_45 = null;
        pauseAtivado = false;
        f1 = new File("./Dados/Fontes/HighMount_PersonalUse.otf");
        f2 = new File("./Dados/Fontes/Crashnumberinggothic.ttf");
        f3 = new File("./Dados/Fontes/times_new_roman.ttf");
        try {
            fonteHighMount_45 = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(40f);
            fonteHighMount_34 = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(34f);
            fonteHighMount_25 = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(25f);
            fonteNumberInGothic_45 = Font.createFont(Font.TRUETYPE_FONT, f2).deriveFont(45f);
            fonteNumberInGothic_25 = Font.createFont(Font.TRUETYPE_FONT, f2).deriveFont(25f);
            fonteTimesNRoman_80 = Font.createFont(Font.TRUETYPE_FONT, f3).deriveFont(80f);
            fonteHighMount_31 = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(31f);
            fonteTimesNRoman_45 = Font.createFont(Font.TRUETYPE_FONT, f3).deriveFont(45f);
            fonteTimesNRoman_25 = Font.createFont(Font.TRUETYPE_FONT, f3).deriveFont(25f);
        } catch(FontFormatException | IOException e) {
            System.out.println("Erro ao carregar fonte");
            System.exit(1);
        }

        tabuleiro = new ImageIcon("./Dados/Imagens/tabuleiro.png").getImage();
        marcado = new ImageIcon("./Dados/Imagens/marcado.png").getImage();
        desmarcado = new ImageIcon("./Dados/Imagens/desmarcado.png").getImage();

        carregarTemporizadores();
        carregarJogadores();
        ativarBotaoDados();
        carregarBotoes();
        pause = new MenuPause(this);
    }

    private void carregarBotoes() {
        Color[] cores1 = new Color[]{Color.BLACK, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE};

        botaoPause = new Botao("Pause", fonteHighMount_45, 20, cores1);
        botaoDados = new Botao(new ImageIcon("./Dados/Imagens/dados.png").getImage(), 20, cores1);
        botaoComprar = new Botao("Comprar", fonteHighMount_45, 20, cores1);
        botaoUpgrade = new Botao("Evoluir", fonteHighMount_45, 20, cores1);
        botaoVender = new Botao("Vender", fonteHighMount_25, 20, cores1);
        botaoHipotecar = new Botao("Hipotecar", fonteHighMount_25, 20, cores1);
        stringDados = new StringBuilder[2];
        stringDados[0] = new StringBuilder(2);
        stringDados[1] = new StringBuilder(2);
        for (int i = 0; i < 32; i++) {
            marcadores[i] = new Botao("", fonteHighMount_45, 10, cores1);
        }
    }

    private void carregarJogadores() {
        numeroJogadores = janela.obterControle().obterNumeroJogadores();
        jogadores = janela.obterControle().obterJogadoresG();
        saldos = new String[numeroJogadores];
        saldosInt = new int[numeroJogadores];
        informaJogador = new String[numeroJogadores];
        propriedades = new PropriedadesG();
        marcadores = new Botao[32];
        estadosMarcadores = new boolean[32];
        selecoes = new ArrayList<Integer>();
        nomesImoveis = new ArrayList<String>();
        valoresImoveis = new ArrayList<String>();
        imoveisIDs = new ArrayList<Integer>();

        // Saldos Iniciais
        carregarSaldos();
        // Propriedades Iniciais
        atualizarPropriedades();
    
        for (int i = 0; i < numeroJogadores; i++) {
            informaJogador[i] = jogadores[i].obterNome() + " joga";
        }
    }

    private void carregarTemporizadores() {
        temporizadorPulos = new Timer(200, _ -> {
            boolean soma;
            if (casaDestino < casaAtual) {
                if (32 - casaAtual + casaDestino > casaAtual - casaDestino) {
                    soma = false;
                } else {
                    soma = true;
                }
            } else {
                if (casaAtual + 32 - casaDestino > casaDestino - casaAtual) {
                    soma = true;
                } else {
                    soma = false;
                }
            }
            if (soma) {
                if (++casaAtual == 32 && msg.obtemTipoEvento() != Eventos.tirouCartaDeMovimento) {
                    janela.obterControle().jogadorRecebeSalario();
                    janela.obterControle().carregarSaldos(saldosInt);
                    intVetParaStringVet(saldos, saldosInt, numeroJogadores);
                }
                casaAtual &= 0x1f;
            } else {
                casaAtual--;
                if (casaAtual == -1) casaAtual = 31; 
            }

            jogadores[idJogadorAtual].atualizarPosicaoJogador(casaAtual);

            if (casaAtual == casaDestino) {
                temporizadorPulos.stop();
                fimTemporizadorPulos();
            }
        });

        temporizadorGenerico = new Timer(2200, _ -> {
            fimTemporizadorGenerico();
            temporizadorGenerico.stop();
        });
    }

    public void setDimensoes(int comprimento, int altura) {
        Controle controle = janela.obterControle();
        int idAtual, idIni, idCasa;

        this.frameComprimento = comprimento;
        this.frameAltura = altura;
        definirTamanhoTabuleiro();
        definirPosicaoTabuleiro();
        definirTamanhoComponentes();
        definirPosicaoComponentes();
        propriedades.atualizarPosicoesUpgrades(tabuleiroPosx, tabuleiroPosy, tabuleiroComp);
        idIni = idAtual = controle.obterIdJogadorAtual();
        do {
            idCasa = controle.obterCasaAtualJogador();
            jogadores[idAtual].atualizarPosicoes(idCasa, tabuleiroPosx, tabuleiroPosy, tabuleiroComp);
            controle.proximoJogador();
            idAtual = controle.obterIdJogadorAtual();
        } while (idIni != idAtual);

        altIcone = compIcone = (int)(35 * tabuleiroComp / 1156.f);
    }

    public void pintar(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        int posX, posY;
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidade));
        
        botaoPause.pintar(g);
        pintarTabuleiro(g);
        pintarIcones(g);
        pintarSaldoJogadores(g);
        pintarInformaJogador(g);

        if (venderLigado || hipotecarLigado) {
            posX = botaoComprar.obterX() + botaoComprar.obterComp() + 20;
            posY = pintarSeleciona(g);
            if (venderLigado) {
                botaoVender.definirLocalizacao(posX, posY);
                botaoVender.pintar(g);
                posX += botaoVender.obterComp() + 10;
            } 
            if (hipotecarLigado) {
                botaoHipotecar.definirLocalizacao(posX, posY);
                botaoHipotecar.pintar(g);
            }
        }
        if (dadosLigado) botaoDados.pintar(g);
        if (valoresLigado) pintarValoresDados(g2d);
        if (falirLigado) pintarJogadorFaliu(g);
        if (cartaLigado) pintarCarta(g2d);
        if (comprarLigado) pintarComprar(g);
        if (upgradeLigado) pintarUpgrade(g);
        if (pauseAtivado) pause.pintar(g);
    }

    private void pintarUpgrade(Graphics g) {
        int posY = botaoComprar.obterY() + botaoComprar.obterAlt();
        int valor = msg.obtemPropriedadeAtual().obtemValorPropriedade();

        botaoUpgrade.pintar(g);
        pintarValorPropriedade(g, 20, posY + 10, Integer.toString(valor));
    }

    private void pintarComprar(Graphics g) {
        int posY = botaoComprar.obterY() + botaoComprar.obterAlt();
        int valor = msg.obtemPropriedadeAtual().obtemValorPropriedade();

        botaoComprar.pintar(g);
        pintarValorPropriedade(g, 20, posY + 10, Integer.toString(valor));
    }

    private void pintarValorPropriedade(Graphics g, int posX, int posY, String valorStr) {
        int comp, alt;

        g.setFont(fonteTimesNRoman_25);
        alt = g.getFontMetrics().getAscent();
        posY += alt;
        comp = g.getFontMetrics().stringWidth("$ ");
        g.setColor(Color.BLACK);
        g.drawString("$ ", posX, posY);
        posX += comp;
        g.setFont(fonteNumberInGothic_25);
        g.drawString(valorStr, posX, posY);
    }

    private void pintarSaldoJogadores(Graphics g) {
        int comp, alt;

        alt = 15;

        for (int i = 0; i < numeroJogadores; i++) {
            if (jogadores[i].estaFalido()) continue;

            g.setFont(fonteNumberInGothic_45);
            alt += g.getFontMetrics().getAscent();
            comp = g.getFontMetrics().stringWidth(saldos[i]);
            g.drawString(saldos[i], frameComprimento - comp - 10, alt);
            
            g.setFont(fonteTimesNRoman_45);
            comp += g.getFontMetrics().stringWidth("$ ");
            g.drawString("$ ", frameComprimento - comp - 10, alt);
            
            comp += compIcone + 5;
            alt -= altIcone;
            g.drawImage(jogadores[i].obterIcone(), frameComprimento - comp - 10, alt, compIcone, altIcone, null);
            alt += altIcone;
            alt += 10;
        }
    }

    private int pintarSeleciona(Graphics g) {
        int posX, posY, comp, alt;
        Image img;
        FontMetrics f;

        comp = marcadores[0].obterComp();
        alt = marcadores[0].obterAlt();
        posY = botaoDados.obterY();
        posX = botaoComprar.obterX() + botaoComprar.obterComp() + 20;

        g.setColor(Color.BLACK);
        for (int i = 0; i < nomesImoveis.size(); i++) {
            marcadores[i].definirLocalizacao(posX, posY);
            marcadores[i].pintar(g);
            g.setFont(fonteTimesNRoman_25);
            f = g.getFontMetrics();
            g.drawString(nomesImoveis.get(i), posX + comp, posY + alt);
            if (estadosMarcadores[i]) img = marcado;
            else img = desmarcado;
            g.drawImage(img, posX + comp + f.stringWidth(nomesImoveis.get(i)) + 4, posY, comp + 2, alt, null);

            posY += marcadores[i].obterAlt() + 2;
            pintarValorPropriedade(g, posX, posY, valoresImoveis.get(i));
            posY += 30;
        }

        return posY;
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
        g2D.setFont(fonteHighMount_45);
        hF = g2D.getFontMetrics().getAscent() - g2D.getFontMetrics().getDescent();
        wF = g2D.getFontMetrics().stringWidth("Carta");
        g2D.drawString("Carta", x + (w - wF) / 2, y + hF + 20);
        hTmp = h;
        g2D.setFont(fonteHighMount_31);
        for (String s : vetStr) {
            wF = g2D.getFontMetrics().stringWidth(s);
            g2D.drawString(s, x + (w - wF) / 2, y + hTmp / 2);
            hTmp += g2D.getFontMetrics().getHeight() + 30;
        }

        if (tipo == 0 || tipo == 6 || tipo == 1) {
            if (tipo == 0 || tipo == 6) str = "+";
            else str = "-";

            numero = Integer.toString(msg.obtemCartaSorteada().obtemValor());
            g2D.setFont(fonteNumberInGothic_45);
            wF = g2D.getFontMetrics().stringWidth(numero);
            g2D.setFont(fonteTimesNRoman_45);
            wF += g2D.getFontMetrics().stringWidth(str);
            g2D.drawString(str, x + (w - wF) / 2, y + h - 50);
            wF -= 2 * g2D.getFontMetrics().stringWidth(str);
            g2D.setFont(fonteNumberInGothic_45);
            g2D.drawString(numero, x + (w - wF) / 2, y + h - 50);
        }
    }

    private void pintarTabuleiro(Graphics g) {
        Posicao p;
        int tam;

        g.drawImage(tabuleiro, tabuleiroPosx, tabuleiroPosy, tabuleiroComp, tabuleiroAlt, null);

        tam = propriedades.obtemNumUpgrades();
        for (int i = 0; i < tam; i++) {
            p = propriedades.obterPosicaoIconeUp(i);
            g.drawImage(propriedades.obterImagemIconeUp(i), p.posX, p.posY, propriedades.obterComp(i), propriedades.obterAlt(i), null);
        }
    }

    private void pintarIcones(Graphics g) {
        JogadorG j;
        Posicao p;

        for (int i = 0; i < numeroJogadores; i++) {
            j = jogadores[i];
            if (j.estaFalido()) continue;

            p = j.obterPosicaoJogador();
            g.drawImage(j.obterIcone(), p.posX, p.posY, compIcone, altIcone, null);
        }
    }

    private void pintarJogadorFaliu(Graphics g) {
        FontMetrics fm;
        int comp, alt;
        String str = jogadores[idJogadorAtual].obterNome() + " acaba de falir :(";

        g.setColor(Color.BLACK);
        g.setFont(fonteTimesNRoman_80);
        fm = g.getFontMetrics();
        comp = fm.stringWidth(str);
        alt = fm.getAscent();
        g.drawString(str, (frameComprimento - comp) / 2, frameAltura / 3 + alt / 2);
    }

    private void pintarInformaJogador(Graphics g) {
        g.setFont(fonteHighMount_34);
        g.setColor(Color.BLACK);
        g.drawString(informaJogador[idJogadorAtual], 20, frameAltura  - g.getFontMetrics().getHeight() - 10);
    }

    private void pintarValoresDados(Graphics2D g2D) {
        Botao bd = botaoDados;
        FontMetrics fm;
        int x = bd.obterX(), y = bd.obterY(), w = bd.obterComp(), h = bd.obterAlt(), wF, hF;
        final int raio = 20;

        g2D.setFont(fonteNumberInGothic_45);
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
        if (pauseAtivado) {
            pause.tecladoAtualiza(e);
            return;
        }

        if (comprarLigado || upgradeLigado) {
            if (e.getID() ==  KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_ENTER) {
                atualizarJogador();
            }
        }
    }

    public void mouseAtualiza(MouseEvent e) {
        int acao;

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
                if (comprarLigado) botaoComprar.mouseMoveu(e);
                if (upgradeLigado) botaoUpgrade.mouseMoveu(e);
                if (venderLigado) botaoVender.mouseMoveu(e);
                if (hipotecarLigado) botaoHipotecar.mouseMoveu(e);
                if (venderLigado || hipotecarLigado) {
                    for (int i = 0; i < nomesImoveis.size(); i++)
                        marcadores[i].mouseMoveu(e);
                }
                break;
            case MouseEvent.MOUSE_PRESSED:
                botaoPause.mousePressionado(e);
                if (dadosLigado) botaoDados.mousePressionado(e);
                if (comprarLigado) botaoComprar.mousePressionado(e);
                if (upgradeLigado) botaoUpgrade.mousePressionado(e);
                if (venderLigado) botaoVender.mousePressionado(e);
                if (hipotecarLigado) botaoHipotecar.mousePressionado(e);
                if (venderLigado || hipotecarLigado) {
                    for (int i = 0; i < nomesImoveis.size(); i++)
                        marcadores[i].mousePressionado(e);
                }
                break;
            case MouseEvent.MOUSE_RELEASED:
                if (botaoPause.mouseSolto(e)) ativarPause();
                if (dadosLigado) {if (botaoDados.mouseSolto(e)) dadosJogados();}
                if (comprarLigado) {
                    if (botaoComprar.mouseSolto(e)) {
                        janela.obterControle().acaoBotaoComprar();
                        carregarSaldos();
                        atualizarJogador();
                    }
                }

                if (upgradeLigado) {
                    if (botaoUpgrade.mouseSolto(e)) {
                        janela.obterControle().acaoBotaoEvoluir();
                        carregarSaldos();
                        atualizarPropriedades();
                        atualizarJogador();
                    }
                }
                
                if (venderLigado || hipotecarLigado) {
                    for (int i = 0; i < nomesImoveis.size(); i++) {
                        if (marcadores[i].mouseSolto(e)) 
                            estadosMarcadores[i] = estadosMarcadores[i] ^ true;
                    }
                }

                if (venderLigado) {
                    if (botaoVender.mouseSolto(e)) {
                        for (int i = 0; i < nomesImoveis.size(); i++) {
                            if (estadosMarcadores[i]) selecoes.add(imoveisIDs.get(i));
                        }
                        acao = janela.obterControle().acaoBotaoVender(selecoes);
                        if (acao != 0) {carregarSaldos(); atualizarPropriedades();}
                        if (acao == 1) {venderLigado = false; limparSelecoes();}
                        if (acao == 2) {venderLigado = false; hipotecarLigado = false; atualizarJogador();}
                    }
                }

                if (hipotecarLigado) {
                    if (botaoHipotecar.mouseSolto(e)) {
                        for (int i = 0; i < nomesImoveis.size(); i++) {
                            if (estadosMarcadores[i]) selecoes.add(imoveisIDs.get(i));
                        }
                        acao = janela.obterControle().acaoBotaoHipotecar(selecoes);
                        if (acao != 0) {carregarSaldos(); atualizarPropriedades();}
                        if (acao == 1) {venderLigado = false; limparSelecoes();}
                        if (acao == 2) {venderLigado = false; hipotecarLigado = false; atualizarJogador();}
                    }
                }

                break;
            default:
                break;
        }
    }

    private void limparSelecoes() {
        int aux;

        Arrays.fill(estadosMarcadores, false);
        selecoes.clear();
        aux = imoveisIDs.size();
        for (int i = 0; i < aux; i++) {
            if (estadosMarcadores[i] == false) continue;
            imoveisIDs.remove(i);
            nomesImoveis.remove(i);
            valoresImoveis.remove(i);
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
        botaoVender.definirDimensoes(115, 33);
        botaoHipotecar.definirDimensoes(115, 33);
        botaoDados.definirDimensoes((int)(0.0417 * frameComprimento), (int)(0.0417 * frameComprimento));
        botaoComprar.definirDimensoes(2 * botaoDados.obterComp() + 10, botaoDados.obterAlt());
        botaoUpgrade.definirDimensoes(2 * botaoDados.obterComp() + 10, botaoDados.obterAlt());
        for (int i = 0; i < 32; i++) {
            marcadores[i].definirDimensoes(20, 20);
        }
    }

    private void definirPosicaoComponentes() {
        int alt;
        botaoPause.definirLocalizacao(20, tabuleiroPosy);
        alt = tabuleiroPosy + botaoPause.obterAlt();
        botaoDados.definirLocalizacao(20, alt + 50);
        alt += botaoDados.obterAlt() + 50;
        botaoComprar.definirLocalizacao(20, alt + 20);
        botaoUpgrade.definirLocalizacao(20, alt + 20);
    }

    private void ativarPause() {
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
        int evento = msg.obtemTipoEvento();
        switch (estadoAtual) {
            // Jogador na prisao
            case ATUALIZA_DADOS:
                atualizarJogador();
                break;
            // Jogador faliu
            case JOGADOR_NA_CASA:
                if (evento == Eventos.tirouCartaDeMovimento) {
                    casaDestino = (casaDestino + msg.obtemDeslocamentoDoJogador()) & 0x1f;
                    temporizadorPulos.start();
                } else {
                    if (evento == Eventos.jogadorFaliu) {
                        jogadores[idJogadorAtual].defineFalido();
                    }
                    atualizarJogador();
                }

                if (evento == Eventos.tirouCartaDeMovimento || evento == Eventos.tirouCarta) {
                    carregarSaldos();
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

    private void intVetParaStringVet(String[] vet1, int[] vet2, int tam) {
        for (int i = 0; i < tam; i++) {
            vet1[i] = Integer.toString(vet2[i]);
        }
    }

    private void carregarSaldos() {
        janela.obterControle().carregarSaldos(saldosInt);
        intVetParaStringVet(saldos, saldosInt, numeroJogadores);
    }

    private void atualizarPropriedades() {
        Stack<Dupla<Integer, Integer>> pilha;
        Dupla<Integer, Integer> d;
        int casa, nivelCasa, acao;

        acao = janela.obterControle().statusAtualizacoesPropriedades();
        if (acao == 0) return;
        
        pilha = janela.obterControle().obtemAtualizacoesPropriedades();
        while (!pilha.empty()) {
            d = pilha.pop();
            casa = d.primeiro;
            nivelCasa = d.segundo;
            switch (acao) {
                case 1:
                    propriedades.removerUpgrade(casa);
                    break;
                case 2:
                    propriedades.atualizarUpgrade(casa, nivelCasa);
                    break;
                case 3:
                    propriedades.adicionarUpgrade(casa, nivelCasa);
                    break;
                default:
                    break;
            }
        }
    }

    /* Estados do jogo */
    public void ativarBotaoDados() {
        estadoAtual = ATIVA_DADOS;
        idJogadorAtual = janela.obterControle().obterIdJogadorAtual();
        dadosLigado = true;
        valoresLigado = false;
        comprarLigado = false;
        upgradeLigado = false;
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
        casaDestino = (casaAtual + valoresDados[0] + valoresDados[1]) % 32;

        msg = controle.decifraCasa(casaDestino);
        if (msg.obtemTipoEvento() == Eventos.jogadorTaPreso ||
            msg.obtemTipoEvento() == Eventos.jogadorNoCAAD) {
            temporizadorGenerico.start();
        } else {
            temporizadorPulos.start();
        }
    }

    public void jogadorNaCasa() {
        estadoAtual = JOGADOR_NA_CASA;
        switch (msg.obtemTipoEvento()) {
            case Eventos.jogadorFaliu:
                carregarSaldos();
                falirLigado = true;
                temporizadorGenerico.start();
                break;
            case Eventos.semDonoPodeComprar:
                comprarLigado = true;
                break;
            case Eventos.ehDonoPodeEvoluir:
                upgradeLigado = true;
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
                selecoes.clear();
                Arrays.fill(estadosMarcadores, false);
                janela.obterControle().carregarPropriedades(nomesImoveis, valoresImoveis, imoveisIDs);
                venderLigado = hipotecarLigado = true;
                break;
            case Eventos.temDonoEPodePagar:
            case Eventos.jogadorNaRecepcao:
                janela.obterControle().carregarSaldos(saldosInt);
                intVetParaStringVet(saldos, saldosInt, numeroJogadores);
            default:
                atualizarJogador();
                break;
        }
    }

    public void atualizarJogador() {
        estadoAtual = ATUALIZA_JOGADOR;
        if (msg.obtemTipoEvento() != Eventos.jogadorFaliu) {
            janela.obterControle().proximoJogador();
        } else if (janela.obterControle().obterNumeroJogadores() == 1) {
            janela.atualizarEstado(FINAL);
        }
        ativarBotaoDados();
    }
}