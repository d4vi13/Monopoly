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

public class Partida extends Grafico {  
    private Janela janela;
    private int frameComp, frameAlt;
    // Tabuleiro
    private int tabComp, tabAlt, tabPosx, tabPosy;
    private Image tab;
    private final int NUMERO_CASAS = 32;
    // Pause
    private boolean pauseAtivado;
    private MenuPause pause;
    private Botao btPause;
    private float opacidade;
    // Botoes
    private Botao[] marcadores;
    private boolean[] estadosMarcadores;
    private ArrayList<Integer> selecoes, imoveisIDs;
    private ArrayList<String> nomesImoveis, valoresImoveis;
    private Image marcado, desmarcado;
    private Botao btDados, btVender, btComprar, btHipotecar, btMelhoria, btPular;
    private boolean dadosLigado, venderLigado, comprarLigado, hipotecarLigado, upgradeLigado;
    // Jogadores
    private PropriedadesG propriedades;
    private JogadorG[] jogadores;
    private int[] saldosInt;
    private String[] informaJogador;
    private boolean falirLigado, cartaLigado;
    private int altIcone, compIcone, idJogadorAtual, casaDestino, casaAtual, numJogadores;
    // Fontes
    private Font ftHighMount_25, ftHighMount_31, ftHighMount_45;
    private Font ftTimesNRoman_45, ftTimesNRoman_80, ftTimesNRoman_25;
    private Font ftNumberInGothic_45, ftNumberInGothic_25;
    // Timers
    private Timer tempPulos, tempGenerico, tempGanhoPerda;
    private boolean foiGanho, ligadoGanhoPerda;
    private String valorGanhoPerda, jogadorGanhoPerda;
    private int contador;
    // Dados
    private int[] valoresDados;
    private boolean valoresLigado;
    private MensagemJogador msg;
    // Estados
    private int estadoAtual;

    public Partida(Janela j) {
        janela = j;
        opacidade = 1.0f;
        pauseAtivado = ligadoGanhoPerda = false;
        pause = new MenuPause(this);
        
        carregarFontes();
        carregarImagens();
        carregarTemporizadores();
        carregarJogadores();
        carregarBotoes();
        ativarBotaoDados();
    }

    private void carregarImagens() {
        tab = new ImageIcon("./Dados/Imagens/tabuleiro.png").getImage();
        marcado = new ImageIcon("./Dados/Imagens/marcado.png").getImage();
        desmarcado = new ImageIcon("./Dados/Imagens/desmarcado.png").getImage();
    }

    private void carregarFontes() {
        File f1 = new File("./Dados/Fontes/HighMount_PersonalUse.otf");
        File f2 = new File("./Dados/Fontes/Crashnumberinggothic.ttf");
        File f3 = new File("./Dados/Fontes/times_new_roman.ttf");
        try {
            ftHighMount_45 = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(40f);
            ftHighMount_25 = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(25f);
            ftNumberInGothic_45 = Font.createFont(Font.TRUETYPE_FONT, f2).deriveFont(45f);
            ftNumberInGothic_25 = Font.createFont(Font.TRUETYPE_FONT, f2).deriveFont(25f);
            ftTimesNRoman_80 = Font.createFont(Font.TRUETYPE_FONT, f3).deriveFont(80f);
            ftHighMount_31 = Font.createFont(Font.TRUETYPE_FONT, f1).deriveFont(31f);
            ftTimesNRoman_45 = Font.createFont(Font.TRUETYPE_FONT, f3).deriveFont(45f);
            ftTimesNRoman_25 = Font.createFont(Font.TRUETYPE_FONT, f3).deriveFont(25f);
        } catch(FontFormatException | IOException e) {
            System.out.println("Erro ao carregar ft");
            System.exit(1);
        }
    }

    private void carregarBotoes() {
        Color[] cores1 = new Color[]{Color.BLACK, Color.LIGHT_GRAY, Color.GRAY, Color.WHITE};

        btPause = new Botao("Pause", ftHighMount_45, 20, cores1);
        btDados = new Botao(new ImageIcon("./Dados/Imagens/dados.png").getImage(), 20, cores1);
        btComprar = new Botao("Comprar", ftHighMount_45, 20, cores1);
        btMelhoria = new Botao("Evoluir", ftHighMount_45, 20, cores1);
        btVender = new Botao("Vender", ftHighMount_25, 20, cores1);
        btHipotecar = new Botao("Hipotecar", ftHighMount_25, 20, cores1);
        for (int i = 0; i < NUMERO_CASAS; i++) {
            marcadores[i] = new Botao("", ftHighMount_45, 10, cores1);
        }
    }

    private void carregarJogadores() {
        int idAtual, idIni, idCasa;
        Controle controle = janela.obterControle();

        numJogadores = janela.obterControle().obterNumeroJogadores();
        jogadores = janela.obterControle().obterJogadoresG();
        saldosInt = new int[numJogadores];
        informaJogador = new String[numJogadores];
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
    
        for (int i = 0; i < numJogadores; i++) {
            informaJogador[i] = jogadores[i].obterNome() + " joga";
        }
        
        idIni = idAtual = controle.obterIdJogadorAtual();
        do {
            idCasa = controle.obterCasaAtualJogador();
            jogadores[idAtual].atualizarPosicao(idCasa);
            controle.proximoJogador();
            idAtual = controle.obterIdJogadorAtual();
        } while (idIni != idAtual);
    }

    private void carregarTemporizadores() {
        contador = 0;
        tempGanhoPerda = new Timer(200, e -> {
            ligadoGanhoPerda = !ligadoGanhoPerda;
            contador++;
            if (contador == 9) {
                contador = 0;
                tempGanhoPerda.stop();
            }
        });

        tempPulos = new Timer(200, e -> {
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
                }
                casaAtual &= 0x1f;
            } else {
                casaAtual--;
                if (casaAtual == -1) casaAtual = 31; 
            }

            jogadores[idJogadorAtual].atualizarPosicao(casaAtual);
            jogadores[idJogadorAtual].atualizarPosicao();

            if (casaAtual == casaDestino) {
                tempPulos.stop();
                fimTemporizadorPulos();
            }
        });

        tempGenerico = new Timer(2200, e -> {
            fimTemporizadorGenerico();
            tempGenerico.stop();
        });
    }

    @Override
    public void setDimensoes(int comprimento, int altura) {
        this.frameComp = comprimento;
        this.frameAlt = altura;
        definirTamanhoTabuleiro();
        definirPosicaoTabuleiro();
        definirTamanhoComponentes();
        definirPosicaoComponentes();
        propriedades.atualizarPosicoesMelhorias(tabPosx, tabPosy, tabComp);
        for (int i = 0; i < numJogadores; i++) {
            jogadores[i].atualizarPosicao(tabPosx, tabPosy, tabAlt);
            jogadores[i].atualizarPosicao();
        }
        
        altIcone = compIcone = (int)(35 * tabComp / 1156.f);
    }

    public void pintar(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        int posX, posY;
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacidade));
        
        btPause.pintar(g);
        pintarTabuleiro(g);
        pintarIcones(g);
        pintarSaldoJogadores(g);
        pintarInformaJogador(g);

        if (venderLigado || hipotecarLigado) {
            posX = btComprar.obterX() + btComprar.obterComp() + 20;
            posY = pintarSeleciona(g);
            if (venderLigado) {
                btVender.definirLocalizacao(posX, posY);
                btVender.pintar(g);
                posX += btVender.obterComp() + 10;
            } 
            if (hipotecarLigado) {
                btHipotecar.definirLocalizacao(posX, posY);
                btHipotecar.pintar(g);
            }
        }
        if (dadosLigado) btDados.pintar(g);
        if (valoresLigado) pintarValoresDados(g2d);
        if (falirLigado) pintarJogadorFaliu(g);
        if (cartaLigado) pintarCarta(g2d);
        if (comprarLigado || upgradeLigado) pintarComprarMelhoria(g);
        if (pauseAtivado) pause.pintar(g);
        if (ligadoGanhoPerda) pintarGanhoPerda(g);
    }

    private void pintarGanhoPerda(Graphics g) {
        StringBuilder s = new StringBuilder();
        s.append(jogadorGanhoPerda);
        if (foiGanho) s.append(" ganha $");
        else s.append(" perde $");
        s.append(valorGanhoPerda);
        
        String str = s.toString();
        g.setFont(ftTimesNRoman_45);
        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(str, frameComp - fm.stringWidth(str) - 10, frameAlt - 10);
    }

    private void pintarComprarMelhoria(Graphics g) {
        int posY = btComprar.obterY() + btComprar.obterAlt();
        int valor = msg.obtemPropriedadeAtual().obtemValorPropriedade();

        if (upgradeLigado)
            btMelhoria.pintar(g);
        else
            btComprar.pintar(g);
        pintarValorPropriedade(g, 20, posY + 10, Integer.toString(valor));
    }

    private void pintarValorPropriedade(Graphics g, int posX, int posY, String valorStr) {
        int comp, alt;

        g.setFont(ftTimesNRoman_25);
        alt = g.getFontMetrics().getAscent();
        posY += alt;
        comp = g.getFontMetrics().stringWidth("$ ");
        g.setColor(Color.BLACK);
        g.drawString("$ ", posX, posY);
        posX += comp;
        g.setFont(ftNumberInGothic_25);
        g.drawString(valorStr, posX, posY);
    }

    private void pintarSaldoJogadores(Graphics g) {
        int x, y;
        String saldo;

        y = 15;
        for (int i = 0; i < numJogadores; i++) {
            if (jogadores[i].estaFalido()) continue;

            g.setFont(ftTimesNRoman_45);
            saldo = jogadores[i].obterNome() + ": " + "$" + Integer.toString(saldosInt[i]);
            y += g.getFontMetrics().getAscent();
            x = frameComp - g.getFontMetrics().stringWidth(saldo) - 10;
            g.drawString(saldo, x, y);
        }
    }

    private int pintarSeleciona(Graphics g) {
        int posX, posY, comp, alt;
        Image img;
        FontMetrics f;

        comp = marcadores[0].obterComp();
        alt = marcadores[0].obterAlt();
        posY = btDados.obterY();
        posX = btComprar.obterX() + btComprar.obterComp() + 20;

        g.setColor(Color.BLACK);
        for (int i = 0; i < nomesImoveis.size(); i++) {
            marcadores[i].definirLocalizacao(posX, posY);
            marcadores[i].pintar(g);
            g.setFont(ftTimesNRoman_25);
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
        int h = (int)(0.25 * frameAlt), w = (int)(1.25 * h);
        int x = (frameComp - w) / 2, y = (frameAlt - h) / 2;
        int acumulador = 0;
        String vet[];

        desenhaRetanguloBorda(g2D, w, h, x, y, 20, 4, Color.BLACK, Color.LIGHT_GRAY);
        vet = msg.obtemCartaSorteada().obtemDescricao();
        g2D.setColor(Color.BLACK);
        g2D.setFont(ftHighMount_45);
        FontMetrics fm = g2D.getFontMetrics();
        for (String s : vet) {
            g2D.drawString(s, x + (w - fm.stringWidth(s)) / 2, y + h / 2 + acumulador);
            acumulador += fm.getHeight() + 10;
        }
    }

    private void pintarTabuleiro(Graphics g) {
        Image icone;
        int x, y, w, h;

        desenhaImagem(g, tab, tabComp, tabAlt, tabPosx, tabPosy);
        for (int i = 0; i < NUMERO_CASAS; i++) {
            if (!propriedades.temMelhoria(i)) continue;

            icone = propriedades.obterImagemIconeUp(i);
            x = propriedades.obterPosicaoIconeUp(i).posX;
            y = propriedades.obterPosicaoIconeUp(i).posY;
            w = propriedades.obterComp(i);
            h = propriedades.obterAlt(i);
            desenhaImagem(g, icone, x, y, w, h);
        }
    }

    private void pintarIcones(Graphics g) {
        JogadorG j;
        Posicao p;

        for (int i = 0; i < numJogadores; i++) {
            if (jogadores[i].estaFalido()) continue;
            j = jogadores[i];
            p = j.obterPosicaoJogador();
            g.drawImage(j.obterIcone(), p.posX, p.posY, compIcone, altIcone, null);
        }
    }

    private void pintarJogadorFaliu(Graphics g) {
        FontMetrics fm;
        int comp, alt;
        String str = jogadores[idJogadorAtual].obterNome() + " acaba de falir :(";

        g.setColor(Color.BLACK);
        g.setFont(ftTimesNRoman_80);
        fm = g.getFontMetrics();
        comp = fm.stringWidth(str);
        alt = fm.getAscent();
        g.drawString(str, (frameComp - comp) / 2, frameAlt / 3 + alt / 2);
    }

    private void pintarInformaJogador(Graphics g) {
        g.setFont(ftTimesNRoman_45);
        g.setColor(Color.BLACK);
        g.drawString(informaJogador[idJogadorAtual], 20, frameAlt - 20);
    }

    private void pintarValoresDados(Graphics2D g2D) {
        Botao bd = btDados;
        FontMetrics fm;
        int x = bd.obterX(), y = bd.obterY(), w = bd.obterComp(), h = bd.obterAlt(), wF, hF;
        final int raio = 20;

        g2D.setFont(ftNumberInGothic_45);
        fm = g2D.getFontMetrics();
        hF = fm.getAscent() - fm.getDescent();
        
        g2D.setColor(Color.BLACK);
        g2D.fillRoundRect(x, y, w, h, raio, raio);
        g2D.setColor(Color.WHITE);
        g2D.fillRoundRect(x + 2, y + 2, w - 4, h - 4, raio, raio);
        g2D.setColor(Color.BLACK);
        wF = fm.stringWidth(Integer.toString(valoresDados[0]));
        g2D.drawString(Integer.toString(valoresDados[0]), x + (w - wF) / 2, y + (h + hF) / 2);

        x += w + 10;
        g2D.setColor(Color.BLACK);
        g2D.fillRoundRect(x, y, w, h, raio, raio);
        g2D.setColor(Color.WHITE);
        g2D.fillRoundRect(x + 2, y + 2, w - 4, h - 4, raio, raio);
        g2D.setColor(Color.BLACK);
        wF = fm.stringWidth(Integer.toString(valoresDados[1]));
        g2D.drawString(Integer.toString(valoresDados[1]), x + (w - wF) / 2, y + (h + hF) / 2);
    }

    @Override
    public void tecladoAtualiza(KeyEvent e) {
        if (pauseAtivado) {
            pause.tecladoAtualiza(e);
            return;
        }
    }

    @Override
    public void mouseAtualiza(MouseEvent e) {
        int acao = 0;
        boolean gastou = false;

        if (pauseAtivado == true) {
            if (e.getID() == MouseEvent.MOUSE_MOVED) {
                btPause.mouseMoveu(e);
            }
            pause.mouseAtualiza(e);
            return;
        }

        switch (e.getID()) {
            case MouseEvent.MOUSE_MOVED:
                btPause.mouseMoveu(e);
                if (dadosLigado) btDados.mouseMoveu(e);
                if (comprarLigado) btComprar.mouseMoveu(e);
                if (upgradeLigado) btMelhoria.mouseMoveu(e);
                if (venderLigado) btVender.mouseMoveu(e);
                if (hipotecarLigado) btHipotecar.mouseMoveu(e);
                if (venderLigado || hipotecarLigado) {
                    for (int i = 0; i < nomesImoveis.size(); i++)
                        marcadores[i].mouseMoveu(e);
                }
                break;
            case MouseEvent.MOUSE_PRESSED:
                btPause.mousePressionado(e);
                if (dadosLigado) btDados.mousePressionado(e);
                if (comprarLigado) btComprar.mousePressionado(e);
                if (upgradeLigado) btMelhoria.mousePressionado(e);
                if (venderLigado) btVender.mousePressionado(e);
                if (hipotecarLigado) btHipotecar.mousePressionado(e);
                if (venderLigado || hipotecarLigado) {
                    for (int i = 0; i < nomesImoveis.size(); i++)
                        marcadores[i].mousePressionado(e);
                }
                break;
            case MouseEvent.MOUSE_RELEASED:
                if (btPause.mouseSolto(e)) ativarPause();
                if (dadosLigado) {if (btDados.mouseSolto(e)) dadosJogados();}
                if (comprarLigado) {
                    if (btComprar.mouseSolto(e)) {
                        janela.obterControle().acaoBotaoComprar();
                        carregarSaldos();
                        atualizarJogador();
                        gastou = true;
                    }
                }

                if (upgradeLigado) {
                    if (btMelhoria.mouseSolto(e)) {
                        janela.obterControle().acaoBotaoEvoluir();
                        atualizarPropriedades();
                        atualizarJogador();
                        gastou = true;
                    }
                }
                
                if (venderLigado || hipotecarLigado) {
                    for (int i = 0; i < nomesImoveis.size(); i++) {
                        if (marcadores[i].mouseSolto(e)) 
                            estadosMarcadores[i] = estadosMarcadores[i] ^ true;
                        if (estadosMarcadores[i]) 
                            selecoes.add(imoveisIDs.get(i));
                    }

                    if (btVender.mouseSolto(e))
                        acao = janela.obterControle().acaoBotaoVender(selecoes);
                    else if (btHipotecar.mouseSolto(e))
                        acao = janela.obterControle().acaoBotaoHipotecar(selecoes);

                    if (acao != 0) {
                        atualizarPropriedades(); 
                        limparSelecionados();

                        if (acao == 2) {
                            venderLigado = hipotecarLigado = false; 
                            atualizarJogador();
                        }
                    }
                    selecoes.clear(); 
                }

                break;
            default:
                break;
        }

        if (gastou) {
            foiGanho = false;
            ligadoGanhoPerda = true;
            jogadorGanhoPerda = jogadores[idJogadorAtual].obterNome();
            valorGanhoPerda = Integer.toString(msg.obtemPropriedadeAtual().obtemValorPropriedade());
            tempGanhoPerda.start();
        }

        carregarSaldos();
    }

    private void limparSelecionados() {
        int aux;

        aux = imoveisIDs.size();
        for (int i = 0; i < aux; i++) {
            if (estadosMarcadores[i] == false) continue;
            imoveisIDs.remove(i);
            nomesImoveis.remove(i);
            valoresImoveis.remove(i);
        }
        Arrays.fill(estadosMarcadores, false);
    }

    private void definirTamanhoTabuleiro() {
        int tmp1 = (int)(frameComp * 0.6), tmp2 = frameAlt - 100;
        if (tmp1 <= tmp2) tabComp = tabAlt = tmp1;
        else tabComp = tabAlt = tmp2;
    }

    private void definirPosicaoTabuleiro() {
        tabPosy = (frameAlt - tabAlt) / 2;
        tabPosx = (frameComp - tabComp) / 2;
    }

    private void definirTamanhoComponentes() {
        btPause.definirDimensoes(160, 48);
        btVender.definirDimensoes(115, 33);
        btHipotecar.definirDimensoes(115, 33);
        btDados.definirDimensoes(80, 80);
        btComprar.definirDimensoes(170, 80);
        btMelhoria.definirDimensoes(170, 80);
        for (Botao b : marcadores) {b.definirDimensoes(20, 20);}
    }

    private void definirPosicaoComponentes() {
        int y = tabPosy;
        btPause.definirLocalizacao(20, y);
        y += btPause.obterAlt() + 50;
        btDados.definirLocalizacao(20, y);
        y += btDados.obterAlt() + 20;
        btComprar.definirLocalizacao(20, y);
        btMelhoria.definirLocalizacao(20, y);
    }

    private void ativarPause() {
        pause.setDimensoes(frameComp, frameAlt);
        opacidade = 0.5f;
        pauseAtivado = true;
        pause.reconfigurar();
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
                    tempPulos.start();
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

    private void carregarSaldos() {
        janela.obterControle().carregarSaldos(saldosInt);
    }

    private void atualizarPropriedades() {
        Stack<Dupla<Integer, Integer>> pilha;
        Dupla<Integer, Integer> d;
        int casa, nivelCasa, acao;
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
        valoresLigado = true;
        dadosLigado = false;
        
        casaAtual = controle.obterCasaAtualJogador();
        casaDestino = (casaAtual + valoresDados[0] + valoresDados[1]) % 32;

        msg = controle.decifraCasa(casaDestino);
        if (msg.obtemTipoEvento() == Eventos.jogadorTaPreso ||
            msg.obtemTipoEvento() == Eventos.jogadorNoCAAD) {
            tempGenerico.start();
        } else {
            tempPulos.start();
        }
    }

    public void jogadorNaCasa() {
        int tipoMsg;
        estadoAtual = JOGADOR_NA_CASA;
        switch (msg.obtemTipoEvento()) {
            case Eventos.jogadorFaliu:
                carregarSaldos();
                falirLigado = true;
                tempGenerico.start();
                break;
            case Eventos.semDonoPodeComprar:
                comprarLigado = true;
                break;
            case Eventos.ehDonoPodeEvoluir:
                upgradeLigado = true;
                break;
            case Eventos.tirouCarta:
                cartaLigado = true;
                tipoMsg = msg.obtemCartaSorteada().obtemTipo();
                if (tipoMsg == 0 || tipoMsg == 1 || tipoMsg == 6) {
                    foiGanho = (tipoMsg != 1);
                    valorGanhoPerda = Integer.toString(msg.obtemCartaSorteada().obtemValor());
                    jogadorGanhoPerda = jogadores[idJogadorAtual].obterNome();
                    ligadoGanhoPerda = true;
                    tempGanhoPerda.start();
                }
                tempGenerico.start();
                break;
            case Eventos.tirouCartaDeMovimento:
                cartaLigado = true;
                tempGenerico.start();
                break;
            case Eventos.vendaOuHipoteca:
                selecoes.clear();
                carregarSaldos();
                Arrays.fill(estadosMarcadores, false);
                carregarSaldos();
                janela.obterControle().carregarPropriedades(nomesImoveis, valoresImoveis, imoveisIDs);
                venderLigado = hipotecarLigado = true;
                break;
            case Eventos.temDonoEPodePagar:
            case Eventos.jogadorNaRecepcao:
                janela.obterControle().carregarSaldos(saldosInt);
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