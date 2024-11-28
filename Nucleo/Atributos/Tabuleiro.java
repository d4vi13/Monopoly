package Nucleo.Atributos;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
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

    public MensagemJogador consultaTabuleiro(Jogador jogadorAtual) {
        Carta cartaAtual;
        int posJogador = jogadorAtual.obtemPosicao();
        Casa casaAtual = casasTabuleiro[posJogador];

        switch (casaAtual.obtemTipo()) {
            case Config.tipoInicial:
                mensagemJogador.atualizaMensagem(true, false, false, false, false, null, null, 0);
                break;

            case Config.tipoImovel:
                break;

            case Config.tipoEmpresa:
                break;

            case Config.tipoPrisao:
                mensagemJogador.atualizaMensagem(false, false, false, false, false, null, null, 3);
                break;

            case Config.tipoCarta:
                cartaAtual = cartasDoTabuleiro.retiraCarta();
                mensagemJogador.atualizaMensagem(false, false, false, false, true, cartaAtual, null, 4);
                break;

            case Config.tipoCAAD:
                mensagemJogador.atualizaMensagem(false, false, false, false, false, null, null, 5);
                break;

            case Config.tipoRecepcao:
                mensagemJogador.atualizaMensagem(false, false, false, false, false, null, null, 6);
                break;

            case Config.tipoVazia:
                mensagemJogador.atualizaMensagem(false, false, false, false, false, null, null, 7);
                break;

            default:
                mensagemJogador.atualizaMensagem(false, false, false, false, false, null, null, 7);
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
}
