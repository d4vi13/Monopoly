package Nucleo.Atributos;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Nucleo.Atributos.Cartas.Carta;
import Nucleo.Atributos.Casa.Config;
import Nucleo.Aux.CarregaTabuleiro;
import Nucleo.Aux.MensagemJogador;
import Nucleo.Aux.infoTabuleiro;
import Nucleo.Aux.MensagemJogador.Eventos;

public class Tabuleiro {
    private int totalCasas;
    private Casa[] casasTabuleiro;
    private Banco banco;
    private Cartas cartasDoTabuleiro;
    private MensagemJogador mensagemJogador;

    public Tabuleiro(Banco banco) {
        this.banco = banco;
        mensagemJogador = new MensagemJogador();
    }

    public int buscaPorCasa(int tipoCasa) {
        Casa casaAtual;
        for (int i = 0; i < totalCasas; ++i) {
            casaAtual = casasTabuleiro[i];
            if (casaAtual.obtemTipo() == tipoCasa) {
                return i;
            }
        }
        return -1;
    }

    /* Novo Jogo */
    public void gerarVetorCasas () {
        int casaId;
        int casaValor;
        String tipoCasa;
        String nomeCasa;
        List<String> nomesCasas;
        List<Integer> posCasa;
        List<Integer> valoresPropriedades;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            CarregaTabuleiro novoTabuleiro = objectMapper.readValue(new File("./Dados/Backups/ConfigTabuleiro/TabuleiroPadrao.json"), CarregaTabuleiro.class);

            /* Cria o vetor com a quantidade de casas definidas. */
            totalCasas = novoTabuleiro.obtemTotalCasas();
            casasTabuleiro = new Casa[totalCasas];
            for (infoTabuleiro casa : novoTabuleiro.obtemCasas()) {
                tipoCasa = casa.obtemTipo();
                posCasa = casa.obtemPosicoes();
                valoresPropriedades = casa.obtemValores();

                switch (tipoCasa) {
                    case "Inicial":
                        for (int i = 0; i < posCasa.size(); ++i) {
                            casaId = posCasa.get(i);
                            if ((casaId >= 0) && (casaId < totalCasas)){
                                casasTabuleiro[casaId] = new Casa("INICIAL", casaId, Config.tipoInicial);
                            }
                        }
                        break;

                    case "Imovel":
                        nomesCasas = casa.obtemNomes(); 
                        for (int i = 0; i < posCasa.size(); ++i) {
                            casaId = posCasa.get(i);
                            nomeCasa = nomesCasas.get(i);
                            casaValor = valoresPropriedades.get(i);
                            if ((casaId >= 0) && (casaId < totalCasas)) {
                                casasTabuleiro[casaId] = new Imovel(nomeCasa, casaId, casaValor);
                                ((Propriedade) casasTabuleiro[casaId]).removeDono();
                            }
                        }
                        break;
                
                    case "Empresa":
                        nomesCasas = casa.obtemNomes();
                        for (int i = 0; i < posCasa.size(); ++i) {
                            casaId = posCasa.get(i);
                            nomeCasa = nomesCasas.get(i);
                            casaValor = valoresPropriedades.get(i);
                            if ((casaId >= 0) && (casaId < totalCasas)){
                                casasTabuleiro[casaId] = new Empresa(nomeCasa, casaId, casaValor);
                                ((Propriedade) casasTabuleiro[casaId]).removeDono();
                            }
                        }
                        break;

                    case "Prisao":
                        for (int i = 0; i < posCasa.size(); ++i) {
                            casaId = posCasa.get(i);
                            if ((casaId >= 0) && (casaId < totalCasas)) {
                                casasTabuleiro[casaId] = new Casa("PRISÃO", casaId, Config.tipoPrisao);
                            }
                        }
                        break;

                    case "Carta":
                        for (int i = 0; i < posCasa.size(); ++i) {
                            casaId = posCasa.get(i);
                            if ((casaId >= 0) && (casaId < totalCasas)) {
                                casasTabuleiro[casaId] = new Casa("CARTA", casaId, Config.tipoCarta);
                            }
                        }
                        break;
                    
                    case "CAAD":
                        for (int i = 0; i < posCasa.size(); ++i) {
                            casaId = posCasa.get(i);
                            if ((casaId >= 0) && (casaId < totalCasas)) {
                                casasTabuleiro[casaId] = new Casa("CAAD", casaId, Config.tipoCAAD);
                            }
                        }
                        break;

                    case "Recepcao":
                        for (int i = 0; i < posCasa.size(); ++i) {
                            casaId = posCasa.get(i);
                            if ((casaId >= 0) && (casaId < totalCasas)) {
                                casasTabuleiro[casaId] = new Casa("RECEPÇÃO", casaId, Config.tipoRecepcao);
                            }
                        }
                        break;

                    case "CasaVazia":
                        for (int i = 0; i < posCasa.size(); ++i) {
                            casaId = posCasa.get(i);
                            if ((casaId >= 0) && (casaId < totalCasas)) {
                                casasTabuleiro[casaId] = new Casa("CASA VAZIA", casaId, Config.tipoVazia);
                            }
                        }
                    default:
                        break;
                }

                for (int i = 0; i < totalCasas; ++i) {
                    if (casasTabuleiro[i] == null) {
                        casasTabuleiro[i] = new Casa("Casa Vazia", i, Config.tipoVazia);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Continuar Jogo */
    public void carregarVetorCasas () {

    }

    public void imprimeCasas () {
        Casa casaAtual;
        int tipoAtual;
        for (int i = 0; i < totalCasas; ++i) {
            casaAtual = casasTabuleiro[i];
            tipoAtual = casaAtual.obtemTipo();
            System.out.print("Nome: " + casaAtual.obtemNome() + " Tipo: " + casaAtual.obtemTipo());
            if ((tipoAtual == Config.tipoImovel) || (tipoAtual == Config.tipoEmpresa)) {
                System.out.print(" Valor: " + ((Propriedade) casaAtual).obtemValorPropriedade() + " Dono: " + ((Propriedade) casaAtual).temDono());
            }
            System.out.println();
        }
    }

    public int patrimonioDoJogador(ArrayList<Integer> propriedades) {
        int total = 0;
        Propriedade propriedadeAtual;
        for (int i = 0; i < propriedades.size(); ++i) {
            propriedadeAtual = (Propriedade)casasTabuleiro[propriedades.get(i)];
            total += propriedadeAtual.obtemValorPropriedade();
        }
        return total;
    }

    public MensagemJogador consultaTabuleiro(Jogador jogadorAtual) {
        Carta cartaAtual;
        Propriedade propriedadeAtual;
        int posJogador = jogadorAtual.obtemPosicao();
        Casa casaAtual = casasTabuleiro[posJogador];
        int idCasaAtual = casaAtual.obtemId();
        int saldoJogador = banco.obterSaldo(jogadorAtual.obtemId());
        int aluguelDaPropriedade;
        int patrimonio;

        switch (casaAtual.obtemTipo()) {
            case Config.tipoInicial:
                mensagemJogador.atualizaMensagem(null, null, Eventos.jogadorNaCasaInicial, 0);
                break;

            case Config.tipoImovel:
                propriedadeAtual = (Propriedade)casaAtual;
                if (propriedadeAtual.temDono()) {
                    // Propriedade tem dono
                    aluguelDaPropriedade = propriedadeAtual.obtemAluguel();
                    if (saldoJogador >= aluguelDaPropriedade) {
                        // Pode pagar o aluguel
                        mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.temDonoEPodePagar, 0);
                    } else {
                        // Não pode pagar o aluguel
                        saldoJogador -= aluguelDaPropriedade;
                        patrimonio = patrimonioDoJogador(jogadorAtual.obtemPropriedadesJogador());
                        // Pode vender ou hipotecar para pagar a dívida
                        if (patrimonio - saldoJogador > 0) {
                            mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.vendaOuHipoteca, 0);
                        } else {
                            // Faliu kk
                            mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.jogadorFaliu, 0);
                        }
                    }
                } else {
                    // Propriedade sem dono
                    if (saldoJogador >= propriedadeAtual.obtemValorPropriedade()) {
                        // Pode comprar a propriedade
                        mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.semDonoPodeComprar, 0);
                    } else {
                        // Não pode comprar a propriedade
                        mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.semDonoNaoPodeComprar, 0);
                    }
                }

                break;

            case Config.tipoEmpresa:
                propriedadeAtual = (Propriedade)casaAtual;
                if (propriedadeAtual.temDono()) {
                    // Propriedade tem dono
                    aluguelDaPropriedade = propriedadeAtual.obtemAluguel();
                    if (saldoJogador >= aluguelDaPropriedade) {
                        // Pode pagar o aluguel
                        mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.temDonoEPodePagar, 0);
                    } else {
                        // Não pode pagar o aluguel
                        saldoJogador -= aluguelDaPropriedade;
                        patrimonio = patrimonioDoJogador(jogadorAtual.obtemPropriedadesJogador());
                        // Pode vender ou hipotecar para pagar a dívida
                        if (patrimonio - saldoJogador > 0) {
                            mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.vendaOuHipoteca, 0);
                        } else {
                            // Faliu kk
                            mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.jogadorFaliu, 0);
                        }
                    }
                } else {
                    // Propriedade sem dono
                    if (saldoJogador >= propriedadeAtual.obtemValorPropriedade()) {
                        // Pode comprar a propriedade
                        mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.semDonoPodeComprar, 0);
                    } else {
                        // Não pode comprar a propriedade
                        mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.semDonoNaoPodeComprar, 0);
                    }
                }
                break;

            case Config.tipoPrisao:
                // Jogador na prisão
                if (jogadorAtual.jogadorPreso()) {
                    mensagemJogador.atualizaMensagem(null, null, Eventos.jogadorTaPreso, 0);
                } else {
                    mensagemJogador.atualizaMensagem(null, null, Eventos.jogadorEstaVisitandoPrisao, 0);
                }

                break;

            case Config.tipoCarta:
                // Retira uma carta
                int idDestino;
                int deslocamento;
                cartaAtual = cartasDoTabuleiro.retiraCarta();

                switch (casaAtual.obtemTipo()) {
                    case 2:
                        // Carta de ir para o CAAD
                        idDestino = buscaPorCasa(Config.tipoCAAD);
                        deslocamento = idDestino - idCasaAtual;
                        mensagemJogador.atualizaMensagem(cartaAtual, null, Eventos.tirouCartaDeMovimento, deslocamento);
                        break;
                    case 3:
                        // Carta de ir para a Recepção
                        idDestino = buscaPorCasa(Config.tipoRecepcao);
                        deslocamento = idDestino - idCasaAtual;
                        mensagemJogador.atualizaMensagem(cartaAtual, null, Eventos.tirouCartaDeMovimento, deslocamento);
                        break;
                    case 4:
                        // Carta de ir para a Prisão
                        idDestino = buscaPorCasa(Config.tipoPrisao);
                        deslocamento = idDestino - idCasaAtual;
                        mensagemJogador.atualizaMensagem(cartaAtual, null, Eventos.tirouCartaDeMovimento, deslocamento);
                        break;
                    case 5:
                        // Carta de ir para a casa Inicial
                        idDestino = buscaPorCasa(Config.tipoInicial);
                        deslocamento = totalCasas - idDestino;
                        mensagemJogador.atualizaMensagem(cartaAtual, null, Eventos.tirouCartaDeMovimento, deslocamento);
                        break;
                    default:
                        mensagemJogador.atualizaMensagem(cartaAtual, null, Eventos.tirouCarta, 0);
                        break;
                }
                break;

            case Config.tipoCAAD:
                // Jogador no CAAD
                mensagemJogador.atualizaMensagem(null, null, Eventos.jogadorNoCAAD, 0);
                break;

            case Config.tipoRecepcao:
                // Jogador na Recepção
                mensagemJogador.atualizaMensagem(null, null, Eventos.jogadorNaRecepcao, 0);
                break;

            case Config.tipoVazia:
                // Jogador em uma casa vazia
                mensagemJogador.atualizaMensagem(null, null, Eventos.casaVazia, 0);
                break;

            default:
                // Casa desconhecida
                mensagemJogador.atualizaMensagem(null, null, Eventos.casaVazia, 0);
                break;
        }

        return mensagemJogador;
    }
    
    public int obtemValorPropriedade(Jogador jogador){
        Propriedade propriedade;
        MensagemJogador mensagemJogador;

        mensagemJogador = consultaTabuleiro(jogador);
        propriedade = mensagemJogador.obtemPropriedadeAtual();
        
        return propriedade.obtemValorPropriedade();    
    }
    
    public int obtemIdCasaAtual(Jogador jogador){
        Propriedade propriedade;
        MensagemJogador mensagemJogador;

        mensagemJogador = consultaTabuleiro(jogador);
        propriedade = mensagemJogador.obtemPropriedadeAtual();
        
        return propriedade.obtemId();
    }

    // Função para atualizar o dono da propriedade 
    public void defineDono(int idPropriedade, int  idJogador){
        Propriedade propriedade;
        propriedade = ((Propriedade) casasTabuleiro[idPropriedade]);
        propriedade.setDono(idJogador);
    }

    public void removeDono(int idPropriedade) {
        Propriedade propriedade;
        propriedade = ((Propriedade) casasTabuleiro[idPropriedade]);
        propriedade.removeDono();
    }
}
