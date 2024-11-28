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
        int saldoJogador = banco.obterSaldo(jogadorAtual.obtemId());
        int aluguelDaPropriedade;
        int patrimonio;

        switch (casaAtual.obtemTipo()) {
            case Config.tipoInicial:
                mensagemJogador.atualizaMensagem(null, null, 1);
                break;

            case Config.tipoImovel:
                propriedadeAtual = (Propriedade)casaAtual;
                if (propriedadeAtual.temDono()) {
                    // Propriedade tem dono
                    aluguelDaPropriedade = propriedadeAtual.obtemAluguel();
                    if (saldoJogador >= aluguelDaPropriedade) {
                        // Pode pagar o aluguel
                        mensagemJogador.atualizaMensagem(null, propriedadeAtual, 2);
                    } else {
                        // Não pode pagar o aluguel
                        saldoJogador -= aluguelDaPropriedade;
                        patrimonio = patrimonioDoJogador(jogadorAtual.obtemPropriedadesJogador());
                        // Pode vender ou hipotecar para pagar a dívida
                        if (patrimonio - saldoJogador > 0) {
                            mensagemJogador.atualizaMensagem(null, propriedadeAtual, 3);
                        } else {
                            // Faliu kk
                            mensagemJogador.atualizaMensagem(null, propriedadeAtual, 10);
                        }
                    }
                } else {
                    // Propriedade sem dono
                    if (saldoJogador >= propriedadeAtual.obtemValorPropriedade()) {
                        // Pode comprar a propriedade
                        mensagemJogador.atualizaMensagem(null, propriedadeAtual, 4);
                    } else {
                        // Não pode comprar a propriedade
                        mensagemJogador.atualizaMensagem(null, propriedadeAtual, 5);
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
                        mensagemJogador.atualizaMensagem(null, propriedadeAtual, 2);
                    } else {
                        // Não pode pagar o aluguel
                        saldoJogador -= aluguelDaPropriedade;
                        patrimonio = patrimonioDoJogador(jogadorAtual.obtemPropriedadesJogador());
                        // Pode vender ou hipotecar para pagar a dívida
                        if (patrimonio - saldoJogador > 0) {
                            mensagemJogador.atualizaMensagem(null, propriedadeAtual, 3);
                        } else {
                            // Faliu kk
                            mensagemJogador.atualizaMensagem(null, propriedadeAtual, 10);
                        }
                    }
                } else {
                    // Propriedade sem dono
                    if (saldoJogador >= propriedadeAtual.obtemValorPropriedade()) {
                        // Pode comprar a propriedade
                        mensagemJogador.atualizaMensagem(null, propriedadeAtual, 4);
                    } else {
                        // Não pode comprar a propriedade
                        mensagemJogador.atualizaMensagem(null, propriedadeAtual, 5);
                    }
                }
                break;

            case Config.tipoPrisao:
                // Jogador na prisão
                mensagemJogador.atualizaMensagem(null, null, 6);
                break;

            case Config.tipoCarta:
                // Retira uma carta
                cartaAtual = cartasDoTabuleiro.retiraCarta();
                mensagemJogador.atualizaMensagem(cartaAtual, null, 7);
                break;

            case Config.tipoCAAD:
                // Jogador no CAAD
                mensagemJogador.atualizaMensagem(null, null, 8);
                break;

            case Config.tipoRecepcao:
                // Jogador na Recepção
                mensagemJogador.atualizaMensagem(null, null, 9);
                break;

            case Config.tipoVazia:
                // Jogador em uma casa vazia
                mensagemJogador.atualizaMensagem(null, null, 0);
                break;

            default:
                mensagemJogador.atualizaMensagem(null, null, 0);
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
    public void atualizaDono(int idPropriedade, int  idJogador){
        
    }
}
