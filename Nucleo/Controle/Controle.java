package Nucleo.Controle;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Stack;
import Nucleo.Atributos.Banco;
import Nucleo.Atributos.Cartas.Carta;
import Nucleo.Atributos.Casa.Config;
import Nucleo.Atributos.Jogador;
import Nucleo.Atributos.Propriedade;
import Nucleo.Atributos.Tabuleiro;
import Nucleo.Grafico.JogadorG;
import Nucleo.Atributos.D6;
import Nucleo.Aux.MensagemJogador.Eventos;
import Nucleo.Aux.*;

public class Controle {
    // Jogadores
    private int numeroJogadoresInicial, numeroJogadores;
    private ListaCircular<Jogador> jogadores;
    private JogadorG[] jogadoresG;
    // Pilha com posicao da casa e nivel da casa
    private Stack<Dupla<Integer, Integer>> propriedades;
    private int operacaoPropriedades;
    // Tabuleiro
    private Tabuleiro tabuleiro;
    // Banco
    private Banco banco;
    // Dado
    private int[] numerosD6;
    private D6 d6;
    // Backup
    private Serializador serializador;

    private String caminhoBackup = "./Dados/Backups/";

    public Controle() {
        jogadores = new ListaCircular<Jogador>();
        propriedades = new Stack<Dupla<Integer, Integer>>();
        jogadoresG = new JogadorG[6];
        banco = new Banco(numeroJogadoresInicial);
        tabuleiro = new Tabuleiro(propriedades);
        d6 = new D6();
        numerosD6 = new int[2];
        serializador = new Serializador();
        operacaoPropriedades = 0;
    }

    // 0 -> Precisa vender mais, mesmo hipotecando todas as outras propriedades nao vai bastar
    // 1 -> Ja vendeu suficiente, mas ainda precisa hipotecar para ter dinheiro suficiente
    // 2 -> Ja vendeu suficiente, nao precisa mais hipotecar
    public int acaoBotaoVender(ArrayList<Integer> propriedades) {
        int divida, valorTotalVenda, patrimonioTotal, patrimonioRestante;
        Jogador jogador = jogadores.getIteradorElem();

        divida = banco.obterSaldo(jogador.obtemId());

        valorTotalVenda = tabuleiro.patrimonioDoJogador(propriedades);
        patrimonioTotal = tabuleiro.patrimonioTotalJogador(jogador); 
        patrimonioRestante =  patrimonioTotal - valorTotalVenda; 
    
        divida += valorTotalVenda; 

        valorTotalVenda = (valorTotalVenda * 75)/100;
        banco.receber(jogador.obtemId(), valorTotalVenda);
        tabuleiro.removeDono(propriedades);
        jogador.desapropriaPropriedade(propriedades);
        tabuleiro.inserePropriedadeNaPilha(jogador.obtemPosicao());
		operacaoPropriedades = 1;

        if (divida >= 0)
            return 2;

        if (divida + 0.5*patrimonioRestante < 0)
            return 0;

        return 1;
    }

    // 0 -> Precisa hipotecar menos, mesmo vendendo todas as outras propriedades nao vai bastar
    // 1 -> Hipotecou suficiente. Ainda precisa vender mais um pouco
    // 2 -> Ja hipotecou suficiente, nao eh necessario vender
    public int acaoBotaoHipotecar(ArrayList<Integer> propriedades) {
        int divida, valorTotalVenda, patrimonioTotal, patrimonioRestante;
        Jogador jogador = jogadores.getIteradorElem();

        divida = banco.obterSaldo(jogador.obtemId());

        valorTotalVenda = tabuleiro.patrimonioDoJogador(propriedades);
        patrimonioTotal = tabuleiro.patrimonioTotalJogador(jogador); 
        patrimonioRestante =  patrimonioTotal - valorTotalVenda; 
    
    
        divida += valorTotalVenda; 
        
        valorTotalVenda = (valorTotalVenda * 50)/100;
        banco.receber(jogador.obtemId(), valorTotalVenda);
        tabuleiro.hipotecaPropriedade(propriedades);
	tabuleiro.inserePropriedadeNaPilha(jogador.obtemPosicao());
        operacaoPropriedades = 1;

        if (divida >= 0)
            return 2;

        if (divida + 0.75*patrimonioRestante < 0)
            return 0;

        return 1;
    }

    public void acaoBotaoComprar() {                                                                                                                                      
        int valorPropriedade, idPropriedade; // valor a ser debitado

        Jogador jogadorAtual = jogadores.getIteradorElem();
        valorPropriedade = tabuleiro.obtemValorPropriedade(jogadorAtual);
        idPropriedade = tabuleiro.obtemIdCasaAtual(jogadorAtual);
    	tabuleiro.inserePropriedadeNaPilha(jogadorAtual.obtemPosicao());
        operacaoPropriedades = 3;

        if (!tabuleiro.estaHipotecada(idPropriedade)){
            banco.debitar(jogadorAtual.obtemId(), valorPropriedade);
            jogadorAtual.apropriaPropriedade(idPropriedade);
            tabuleiro.defineDono(idPropriedade, jogadorAtual.obtemId());
        } else { 
            if (jogadorAtual.ehDono(idPropriedade)){
                banco.debitar(jogadorAtual.obtemId(), valorPropriedade*50/100);
                tabuleiro.deshipotecar(idPropriedade); 
            }
        }
    }

    public void acaoBotaoEvoluir() {
        Jogador jogadorAtual = jogadores.getIteradorElem();

        tabuleiro.evoluirImovel(jogadorAtual.obtemPosicao());
        tabuleiro.inserePropriedadeNaPilha(jogadorAtual.obtemPosicao());

        operacaoPropriedades = 2;
    }

    public void acaoBotaoJogarDados() {
        d6.jogaDado();
    }

    public int[] obterNumerosD6() {
        numerosD6[0] = d6.obterValorDado(0);
        numerosD6[1] = d6.obterValorDado(1);
        return numerosD6;
    } 

    public void carregarSaldos(int[] vet) {
        int[] saldos = banco.obterSaldos();
        for (int i = 0; i < numeroJogadoresInicial; i++){
            vet[i] = saldos[i];
        } 
    }

    // Passa os valores, nomes e IDs das propriedades do jogador atual
    public void carregarPropriedades(ArrayList<String> nomes, ArrayList<String> valores, ArrayList<Integer> IDs) {
        Jogador j = jogadores.getIteradorElem();
        ArrayList<Integer> propriedadeIDs = j.obtemPropriedadesJogador();
        
        nomes.clear();
        valores.clear();
        IDs.clear();
        for (int i = 0; i < propriedadeIDs.size(); i++) {
            nomes.add(tabuleiro.obtemNomeCasa(propriedadeIDs.get(i)));
            valores.add(tabuleiro.obtemValorPropriedade(propriedadeIDs.get(i)));
            IDs.add(propriedadeIDs.get(i));
        }
    }

    // Ao iniciar um backup, pilha deve conter todas as propriedades
    // Caso seja novo jogo, pilha deve estar vazia
    public Stack<Dupla<Integer, Integer>> obtemAtualizacoesPropriedades() {
        return propriedades;
    }

    // 0 -> Nada muda
    // 1 -> Remover propriedades (vender/hipotecar)
    // 2 -> Atualizar propriedade (evoluir)
    // 3 -> Adicionar propriedade (comprar)
    public int statusAtualizacoesPropriedades() {
        int op = operacaoPropriedades;
        operacaoPropriedades = 0;
        return op;
    }

    // Define os eventos monetários relacionados ao jogador dependendo do valor cobrado
    private int defineEventosMonetarios(Jogador jogadorAtual, int valorCobrado) {
        int divida;
        int patrimonioTotal;
        int saldoJogador = banco.obterSaldo(jogadorAtual.obtemId());

        // É possível pagar
        if (saldoJogador >= valorCobrado) {
            return Eventos.podePagar;
        }

        patrimonioTotal = tabuleiro.patrimonioTotalJogador(jogadorAtual);
        divida = valorCobrado - saldoJogador;
        
        // Precisa vender
        if (patrimonioTotal >= divida) {
            return Eventos.vendaOuHipoteca;
        }

        return Eventos.jogadorFaliu;
    }

    private int calculaDeslocamento(int casaInicial, int casaFinal) {
        int totalCasas = tabuleiro.obtemCasasTotais();

        if (casaFinal != 0) {
            return (casaFinal - casaInicial) % totalCasas;
        }

        return totalCasas - casaFinal;
    }

    // Reseta as propriedades do jogador e define que ele faliu
    private void jogadorDeclaraFalencia(Jogador jogadorAtual) {
        tabuleiro.removeDono(jogadorAtual.obtemPropriedadesJogador());
        jogadorAtual.desapropriaPropriedade(jogadorAtual.obtemPropriedadesJogador());
        jogadorAtual.declaraFalencia();
        jogadores.tiraLista(jogadorAtual);
        numeroJogadores--;
    }

    // Apartir da casa destino do jogador, define os eventos e atualiza o estado do jogador
    public MensagemJogador decifraCasa(int casaDestino) {
        MensagemJogador mensagemJogador;
        Jogador jogadorAtual = jogadores.getIteradorElem();
        int deslocamento, casaInicial, casaFinal, imposto, evento;
        Propriedade propriedadeAtual;
        Carta cartaSorteada;

        // Se o jogador não está preso e não está de férias, pode se mover
        if (!(jogadorAtual.jogadorPreso()) && !(jogadorAtual.jogadorDeFerias())) {
            jogadorAtual.defineNovaPosicao(casaDestino);
        } else {
            if ((jogadorAtual.jogadorPreso()) && (d6.dadosIguais())) {
                // Se o jogador obtem dados iguais, consegue sair da prisão
                jogadorAtual.defineJogadorLivre();
                jogadorAtual.defineNovaPosicao(casaDestino);
            }
        }
        
        mensagemJogador = tabuleiro.consultaTabuleiro(jogadorAtual);
        propriedadeAtual = mensagemJogador.obtemPropriedadeAtual();
        cartaSorteada = mensagemJogador.obtemCartaSorteada();

        switch (mensagemJogador.obtemTipoEvento()) {
            case Eventos.casaInicial:
                // Jogador recebe salário do banco
                banco.pagaSalario(jogadorAtual.obtemId());
                mensagemJogador.defineNovoEvento(Eventos.jogadorNaCasaInicial);
                break;

            case Eventos.propriedadeComDono:
                propriedadeAtual = mensagemJogador.obtemPropriedadeAtual();

                if (jogadorAtual.ehDono(propriedadeAtual.obtemId())) {
                    if (propriedadeAtual.estaHipotecada()) {
                        int valorPropriedade = propriedadeAtual.obtemValorPropriedade();
                        valorPropriedade = valorPropriedade / 2;
                        if (banco.temSaldoSuficiente(jogadorAtual.obtemId(), valorPropriedade)) {
                            mensagemJogador.defineNovoEvento(Eventos.semDonoPodeComprar);
                        } else {
                            mensagemJogador.defineNovoEvento(Eventos.casaVazia);
                        }
                    } else {
                        if (propriedadeAtual.obtemTipo() == Config.tipoImovel) {
                            int valorEvolucao = mensagemJogador.obtemValorEvolucao();
                            if (banco.temSaldoSuficiente(jogadorAtual.obtemId(), valorEvolucao)) {
                                mensagemJogador.defineNovoEvento(Eventos.ehDonoPodeEvoluir);
                            } else {
                                mensagemJogador.defineNovoEvento(Eventos.casaVazia);
                            }
                        }
                    }
                } else {
                    // Não é o dono e precisa pagar aluguel
                    evento = defineEventosMonetarios(jogadorAtual, propriedadeAtual.obtemAluguel());
                    banco.debitar(jogadorAtual.obtemId(), propriedadeAtual.obtemAluguel());
                    if (!propriedadeAtual.estaHipotecada()) {
                        banco.receber(propriedadeAtual.obtemIdDono(), propriedadeAtual.obtemAluguel());
                    }

                    if (evento == Eventos.podePagar) {
                        // Define evento que pode pagar
                        mensagemJogador.defineNovoEvento(Eventos.temDonoEPodePagar);
                    } else if (evento == Eventos.vendaOuHipoteca) {
                        // Define que precisa vender ou hipotecar
                        mensagemJogador.defineNovoEvento(evento);
                    } else {
                        // Jogador faliu
                        mensagemJogador.defineNovoEvento(evento);
                        jogadorDeclaraFalencia(jogadorAtual);
                    }
                }
                break;

            case Eventos.propriedadeSemDono:
                propriedadeAtual = mensagemJogador.obtemPropriedadeAtual();
                if (banco.temSaldoSuficiente(jogadorAtual.obtemId(), propriedadeAtual.obtemValorPropriedade())) {
                    // Jogador é capaz de comprar a propriedade
                    mensagemJogador.defineNovoEvento(Eventos.semDonoPodeComprar);
                } else {
                    // Jogador não é capaz de comprar a propriedade
                    mensagemJogador.defineNovoEvento(Eventos.semDonoNaoPodeComprar);
                }
                break;

            case Eventos.casaPrisao:
                if (jogadorAtual.jogadorPreso()) {
                    if (jogadorAtual.retornaRodadasPreso() == 0) {
                        // Libera o jogador da prisão
                        jogadorAtual.defineJogadorLivre();
                        mensagemJogador.defineNovoEvento(Eventos.jogadorEstaVisitandoPrisao);
                    } else {
                        // Jogador continua preso
                        jogadorAtual.diminuiRodadasPreso();
                        mensagemJogador.defineNovoEvento(Eventos.jogadorTaPreso);
                    }
                } else {
                    // Jogador está visitando a prisão
                    mensagemJogador.defineNovoEvento(Eventos.jogadorEstaVisitandoPrisao);
                }
                break;

            case Eventos.indoPreso:
                // Jogador está indo para a cadeia
                jogadorAtual.defineJogadorPreso();
                mensagemJogador.defineNovoEvento(Eventos.jogadorTaPreso);
                break;

            case Eventos.casaCarta:
                casaInicial = jogadorAtual.obtemPosicao();
                cartaSorteada = mensagemJogador.obtemCartaSorteada();
                mensagemJogador.defineNovoEvento(Eventos.tirouCarta);

                switch (cartaSorteada.obtemTipo()) {
                    case 0:
                        // Jogador recebe valor da carta
                        banco.receber(jogadorAtual.obtemId(), cartaSorteada.obtemValor());
                        break;

                    case 1:
                        // Jogador paga o valor da carta
                        evento = defineEventosMonetarios(jogadorAtual, cartaSorteada.obtemValor());
                        banco.debitar(jogadorAtual.obtemId(), cartaSorteada.obtemValor());
                        if (evento == Eventos.vendaOuHipoteca) {
                            // Jogador precisa vender ou hipotecar para pagar
                            mensagemJogador.defineNovoEvento(evento);
                        } else if (evento == Eventos.jogadorFaliu) {
                            // Jogador faliu
                            mensagemJogador.defineNovoEvento(evento);
                            jogadorDeclaraFalencia(jogadorAtual);
                        }
                        break;

                        /*
                         * Eventos 2 ~ 5
                         * Obtem a localização da casa indicada pela carta
                         * Obtem a distância do jogador até ela
                         * Define o deslocamento e novo evento
                         * Retorna para preencher o jogador com informações novas
                         */
                    case 2:
                        casaFinal = tabuleiro.buscaPorCasa(Config.tipoCAAD);
                        deslocamento = calculaDeslocamento(casaInicial, casaFinal);
                        mensagemJogador.defineEventoMovimento(true);
                        mensagemJogador.defineDeslocamento(deslocamento);
                        mensagemJogador.defineNovoEvento(Eventos.casaCAAD);
                        return decifraCasa(casaFinal);

                    case 3:
                        casaFinal = tabuleiro.buscaPorCasa(Config.tipoRecepcao);
                        deslocamento = calculaDeslocamento(casaInicial, casaFinal);
                        mensagemJogador.defineEventoMovimento(true);
                        mensagemJogador.defineDeslocamento(deslocamento);
                        mensagemJogador.defineNovoEvento(Eventos.casaRecepcao);
                        return decifraCasa(casaFinal);
                        
                    case 4:
                        casaFinal = tabuleiro.buscaPorCasa(Config.tipoPrisao);
                        deslocamento = calculaDeslocamento(casaInicial, casaFinal);
                        mensagemJogador.defineEventoMovimento(true);
                        mensagemJogador.defineDeslocamento(deslocamento);
                        mensagemJogador.defineNovoEvento(Eventos.indoPreso);
                        return decifraCasa(casaFinal);

                    case 5:
                        casaFinal = tabuleiro.buscaPorCasa(Config.tipoInicial);
                        deslocamento = calculaDeslocamento(casaInicial, casaFinal);
                        mensagemJogador.defineEventoMovimento(true);
                        mensagemJogador.defineDeslocamento(deslocamento);
                        mensagemJogador.defineNovoEvento(Eventos.casaInicial);
                        return decifraCasa(casaFinal);

                    case 6:
                        // Carta onde todos os jogadores mandam dinheiro para um jogador
                        banco.transferir(jogadorAtual.obtemId(), cartaSorteada.obtemValor());
                        break;
                        
                    default:
                        mensagemJogador.defineNovoEvento(Eventos.casaVazia);
                        break;
                }
                break;

            case Eventos.casaCAAD:
                // Jogador entra ou sai de férias 
                if (jogadorAtual.jogadorDeFerias()) {
                    jogadorAtual.defineJogadorSaiuDeFerias();
                } else {
                    jogadorAtual.defineJogadorEntrouDeFerias();
                }
                mensagemJogador.defineNovoEvento(Eventos.jogadorNoCAAD);
                break;

            case Eventos.casaRecepcao:
                imposto = tabuleiro.calculaImposto(jogadorAtual.obtemPropriedadesJogador());
                evento = defineEventosMonetarios(jogadorAtual, imposto);

                banco.debitar(jogadorAtual.obtemId(), imposto);
                if (evento == Eventos.podePagar) {
                    // Jogador pode pagar imposto
                    mensagemJogador.defineNovoEvento(Eventos.jogadorNaRecepcao);
                } else if (evento == Eventos.vendaOuHipoteca) {
                    // Jogador precisa vender ou hipotecar
                    mensagemJogador.defineNovoEvento(evento);
                } else {
                    // Jogador faliu
                    mensagemJogador.defineNovoEvento(evento);
                    jogadorDeclaraFalencia(jogadorAtual);
                }
                break;
            
            default:
                break;
        }

        /*
         * Se é uma carta de movimento, define como falso pois já foi tratado
         * Redefine o evento passado na classe mensagemJogador
         */
        if (mensagemJogador.obtemEventoMovimento()) {
            mensagemJogador.defineEventoMovimento(false);
            mensagemJogador.defineNovoEvento(Eventos.tirouCartaDeMovimento);
        }

        return mensagemJogador;
    }

    private String[] obterVetorNomes(){
        Jogador jogador = jogadores.getIteradorElem();
        int id = jogador.obtemId();
        String[] nomes = new String[numeroJogadores];

        for(int i = 0 ; i < numeroJogadores ; i++){
            nomes[i] =  jogador.obtemNome();
            jogadores.iteradorProx();
            jogador = jogadores.getIteradorElem();
        }
        return nomes;
    }

    public void acaoBotaoCarregarBackup(String nomeArquivo) {
        tabuleiro.gerarVetorCasas(nomeArquivo);
        serializador.restaurarBackup(caminhoBackup + nomeArquivo);
        numeroJogadores = serializador.carregar(numeroJogadores);
        numeroJogadoresInicial = serializador.carregar(numeroJogadores);
        jogadores = serializador.carregar(jogadores);        
        banco = serializador.carregar(banco);
        criarJogadoresG(obterVetorNomes());
    }

    public void acaoBotaoSalvarBackup(String nomeArquivo) {
        tabuleiro.salvaTabuleiro(nomeArquivo);
        serializador.iniciarBackup(caminhoBackup + nomeArquivo);
        serializador.salvar(numeroJogadores);
        serializador.salvar(numeroJogadoresInicial);
        serializador.salvar(jogadores);        
        serializador.salvar(banco);

    }

    public void acaoBotaoNovaPartida() {
        tabuleiro.gerarVetorCasas(null);
    }

    private void criarJogadoresG(String vetNomes[]){
        Image iAux;
        String caminhoImagem = "./Dados/Imagens/Jogador%d.png";
        for (int i = 0; i < numeroJogadores || i < 2; i++){
            iAux = new ImageIcon(String.format(caminhoImagem,i+1)).getImage();
            jogadoresG[i] = new JogadorG(iAux, i, vetNomes[i]);
          
        }
    }

    private void criarJogadores(String vetNomes[]){
        for (int i = 0; i < numeroJogadores; i++) {
            jogadores.addLista(new Jogador(i, vetNomes[i]));
        }
        
        jogadores.setIterador();
    }

    public void cadastrarJogadores(String[] vetNomes, int qtdJogadores) {

        numeroJogadores = numeroJogadoresInicial = qtdJogadores;
        criarJogadoresG(vetNomes);
        criarJogadores(vetNomes); 
        banco.atualizaQtdClientes(qtdJogadores);

    }

    public int obterIdJogadorAtual() {
        return jogadores.getIteradorElem().obtemId();
    }

    public int obterCasaAtualJogador() {
        return jogadores.getIteradorElem().obtemPosicao();
    }

    public void proximoJogador() {
        jogadores.iteradorProx();
    }

    public JogadorG[] obterJogadoresG() {
        return jogadoresG;
    }

    public int obterNumeroJogadores() {
        return numeroJogadores;
    }
}
