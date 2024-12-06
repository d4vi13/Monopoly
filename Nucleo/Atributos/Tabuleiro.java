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
    private Cartas cartasDoTabuleiro;
    private Recepcao recepcaoDoDinf;
    private MensagemJogador mensagemJogador;

    public Tabuleiro(Banco banco) {
        this.mensagemJogador = new MensagemJogador();
        this.cartasDoTabuleiro = new Cartas();
        this.recepcaoDoDinf = new Recepcao();
    }

    public int obtemCasasTotais () {
        return totalCasas;
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

    public int patrimonioDoJogador(ArrayList<Integer> propriedades) {
        int total = 0;
        Propriedade propriedadeAtual;
        for (int i = 0; i < propriedades.size(); ++i) {
            propriedadeAtual = (Propriedade)casasTabuleiro[propriedades.get(i)];
            total += propriedadeAtual.obtemValorPropriedade();
        }
        return total;
    }

    public int patrimonioTotalJogador(Jogador jogador){
        return patrimonioDoJogador(jogador.obtemPropriedadesJogador());
    }

    public void hipotecaPropriedade(ArrayList<Integer> propriedades) {
        Propriedade propriedadeAtual;
        for (int i = 0; i < propriedades.size(); ++i) {
            propriedadeAtual = (Propriedade)casasTabuleiro[propriedades.get(i)];
            propriedadeAtual.hipotecar();
        }
    }

    public MensagemJogador consultaTabuleiro(Jogador jogadorAtual) {
        Carta cartaAtual;
        Propriedade propriedadeAtual;
        int posJogador = jogadorAtual.obtemPosicao();
        Casa casaAtual = casasTabuleiro[posJogador];

        switch (casaAtual.obtemTipo()) {
            case Config.tipoInicial:
                mensagemJogador.atualizaMensagem(mensagemJogador.obtemCartaSorteada(), null, Eventos.casaInicial);
                break;
            
            case Config.tipoImovel:            
            case Config.tipoEmpresa:

                propriedadeAtual = (Propriedade)casaAtual;
                if (propriedadeAtual.temDono()) {
                    mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.propriedadeComDono);
                } else {
                    mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.propriedadeSemDono);
                }
                break;

            case Config.tipoPrisao:
                mensagemJogador.atualizaMensagem(mensagemJogador.obtemCartaSorteada(), null, Eventos.casaPrisao);
                break;

            case Config.tipoCarta:
                cartaAtual = cartasDoTabuleiro.retiraCarta();
                mensagemJogador.atualizaMensagem(cartaAtual, null, Eventos.casaCarta);
                break;

            case Config.tipoCAAD:
                mensagemJogador.atualizaMensagem(mensagemJogador.obtemCartaSorteada(), null, Eventos.casaCAAD);
                break;

            case Config.tipoRecepcao:
                mensagemJogador.atualizaMensagem(mensagemJogador.obtemCartaSorteada(), null, Eventos.casaRecepcao);
                break;

            case Config.tipoVazia:
                mensagemJogador.atualizaMensagem(null, null, Eventos.casaVazia);
                break;

            default:
                mensagemJogador.atualizaMensagem(null, null, Eventos.casaVazia);
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

    public boolean estaHipotecada(int idPropriedade){
        Propriedade propriedade;
        propriedade = ((Propriedade) casasTabuleiro[idPropriedade]);
        return propriedade.estaHipotecada();
    }

    public void deshipotecar(int idPropriedade){
        Propriedade propriedade;
        propriedade = ((Propriedade) casasTabuleiro[idPropriedade]);
        propriedade.deshipotecar();
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

        if (propriedade.obtemTipo() == Config.tipoImovel) {
            ((Imovel) propriedade).resetarValores();
        }
    }

    public void removeDono(ArrayList<Integer> propriedades){
        for (int i = 0; i < propriedades.size(); i++){
            removeDono(i);
        }
    }

    public int calculaImposto(ArrayList<Integer> propriedades) {
        int patrimonio = patrimonioDoJogador(propriedades);
        return recepcaoDoDinf.pagarImposto(patrimonio);
    }

    public void evoluirImovel(int idPropriedade) {
        Imovel imovelAtual = ((Imovel) casasTabuleiro[idPropriedade]);
        imovelAtual.evoluirImovel();
    }
}
