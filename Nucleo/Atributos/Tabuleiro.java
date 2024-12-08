package Nucleo.Atributos;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Nucleo.Atributos.Cartas.Carta;
import Nucleo.Atributos.Casa.Config;
import Nucleo.Aux.CarregaTabuleiro;
import Nucleo.Aux.Dupla;
import Nucleo.Aux.MensagemJogador;
import Nucleo.Aux.infoTabuleiro;
import Nucleo.Aux.MensagemJogador.Eventos;

public class Tabuleiro {
    private final static String path = "./Dados/Backups/ConfigTabuleiro/";
    private final static String backupTabuleiro = "./Dados/Backups/ConfigTabuleiro/TabuleiroPadrao.json";
    private int totalCasas;
    private Casa[] casasTabuleiro;
    private Cartas cartasDoTabuleiro;
    private Recepcao recepcaoDoDinf;
    private MensagemJogador mensagemJogador;
    private Stack<Dupla<Integer, Integer>> pilhaPropriedades;

    public Tabuleiro(Stack<Dupla<Integer, Integer>> pilha) {
        this.mensagemJogador = new MensagemJogador();
        this.cartasDoTabuleiro = new Cartas();
        this.recepcaoDoDinf = new Recepcao();
        this.pilhaPropriedades = pilha;
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
    public void gerarVetorCasas (String backup) {
        int casaId;
        int casaValor;
        String tipoCasa;
        String nomeCasa;
        String selecionaBackup;
        List<String> nomesCasas;
        List<Integer> posCasa;
        List<Integer> valoresPropriedades;
        List<Integer> niveis;
        List<Boolean> donos;
        ObjectMapper objectMapper = new ObjectMapper();
        boolean insereNaPilha = (backup != null);

        if (backup != null) {
            selecionaBackup = path.concat(backup);
            selecionaBackup = selecionaBackup.concat(".json");
        } else {
            selecionaBackup = backupTabuleiro;
        }

        try {
            CarregaTabuleiro novoTabuleiro = objectMapper.readValue(new File(selecionaBackup), CarregaTabuleiro.class);

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
                            niveis = casa.obtemNiveis();
                            donos = casa.obtemTemDono();
                            if ((casaId >= 0) && (casaId < totalCasas)) {
                                casasTabuleiro[casaId] = new Imovel(nomeCasa, casaId, casaValor);
                                ((Imovel) casasTabuleiro[casaId]).defineDono(donos.get(i));;
                                ((Imovel) casasTabuleiro[casaId]).defineNivel(niveis.get(i));
                                if (insereNaPilha) {
                                    pilhaPropriedades.push(new Dupla<Integer,Integer>(casaId, niveis.get(i)));
                                }
                            }
                        }
                        break;
                
                    case "Empresa":
                        nomesCasas = casa.obtemNomes();
                        for (int i = 0; i < posCasa.size(); ++i) {
                            casaId = posCasa.get(i);
                            nomeCasa = nomesCasas.get(i);
                            casaValor = valoresPropriedades.get(i);
                            donos = casa.obtemTemDono();
                            if ((casaId >= 0) && (casaId < totalCasas)){
                                casasTabuleiro[casaId] = new Empresa(nomeCasa, casaId, casaValor);
                                ((Propriedade) casasTabuleiro[casaId]).defineDono(donos.get(i));
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

    public void salvaTabuleiro(String backup) {
        String nomeCasa;
        Integer tipoCasa;
        Integer posicaoCasa;
        Boolean temDono;
        Integer donoId;
        Integer valor;
        Integer nivel;
        Casa casaDoTabuleiro;
        infoTabuleiro casaAtual;
        int indexLista;
        String salvarArquivo;
        CarregaTabuleiro tabuleiroAtual = new CarregaTabuleiro();
        String tipos[] = {"Inicial", "Imovel", "Empresa", "Prisao", "Carta", "CAAD", "Recepcao", "CasaVazia"};

        salvarArquivo = path.concat(backup);
        salvarArquivo = salvarArquivo.concat(".json");
        for (int i = 0; i < 8; ++i) {
            casaAtual = new infoTabuleiro(tipos[i]);
            tabuleiroAtual.insereCasa(casaAtual);
        }

        int indexInicial = tabuleiroAtual.buscaPorCasa("Inicial");
        int indexImovel = tabuleiroAtual.buscaPorCasa("Imovel");
        int indexEmpresa = tabuleiroAtual.buscaPorCasa("Empresa");
        int indexPrisao = tabuleiroAtual.buscaPorCasa("Prisao");
        int indexCarta = tabuleiroAtual.buscaPorCasa("Carta");
        int indexCAAD = tabuleiroAtual.buscaPorCasa("CAAD");
        int indexRecepcao = tabuleiroAtual.buscaPorCasa("Recepcao");
        int indexVazia = tabuleiroAtual.buscaPorCasa("CasaVazia");

        for (int i = 0; i < totalCasas; ++i) {
            casaDoTabuleiro = casasTabuleiro[i];
            nomeCasa = casaDoTabuleiro.obtemNome();
            tipoCasa = casaDoTabuleiro.obtemTipo();
            posicaoCasa = casaDoTabuleiro.obtemId();

            switch (tipoCasa) {
                case Config.tipoInicial:
                    indexLista = indexInicial;
                    casaAtual = tabuleiroAtual.buscaPorCasa(indexLista);
                    casaAtual.insereNome(nomeCasa);
                    casaAtual.inserePosicao(posicaoCasa);
                    break;
                
                case Config.tipoImovel:
                    indexLista = indexImovel;
                    casaAtual = tabuleiroAtual.buscaPorCasa(indexLista);
                    temDono = ((Propriedade) casaDoTabuleiro).temDono();
                    donoId = ((Propriedade) casaDoTabuleiro).obtemIdDono();
                    valor = ((Propriedade) casaDoTabuleiro).obtemValorPropriedade();
                    nivel = ((Imovel) casaDoTabuleiro).obtemNivelImovel();
                    casaAtual.insereNome(nomeCasa);
                    casaAtual.inserePosicao(posicaoCasa);
                    casaAtual.insereDono(temDono);
                    casaAtual.insereDonoId(donoId);
                    casaAtual.insereValor(valor);
                    casaAtual.insereNivel(nivel);
                    break;
                
                case Config.tipoEmpresa:
                    indexLista = indexEmpresa;
                    casaAtual = tabuleiroAtual.buscaPorCasa(indexLista);
                    temDono = ((Propriedade) casaDoTabuleiro).temDono();
                    donoId = ((Propriedade) casaDoTabuleiro).obtemIdDono();
                    valor = ((Propriedade) casaDoTabuleiro).obtemValorPropriedade();
                    casaAtual.insereNome(nomeCasa);
                    casaAtual.inserePosicao(posicaoCasa);
                    casaAtual.insereDono(temDono);
                    casaAtual.insereDonoId(donoId);
                    casaAtual.insereValor(valor);
                    break;

                case Config.tipoPrisao:
                    indexLista = indexPrisao;
                    casaAtual = tabuleiroAtual.buscaPorCasa(indexLista);
                    casaAtual.insereNome(nomeCasa);
                    casaAtual.inserePosicao(posicaoCasa);
                    break;

                case Config.tipoCarta:
                    indexLista = indexCarta;
                    casaAtual = tabuleiroAtual.buscaPorCasa(indexLista);
                    casaAtual.insereNome(nomeCasa);
                    casaAtual.inserePosicao(posicaoCasa);
                    break;

                case Config.tipoCAAD:
                    indexLista = indexCAAD;
                    casaAtual = tabuleiroAtual.buscaPorCasa(indexLista);
                    casaAtual.insereNome(nomeCasa);
                    casaAtual.inserePosicao(posicaoCasa);
                    break;

                case Config.tipoRecepcao:
                    indexLista = indexRecepcao;
                    casaAtual = tabuleiroAtual.buscaPorCasa(indexLista);
                    casaAtual.insereNome(nomeCasa);
                    casaAtual.inserePosicao(posicaoCasa);
                    break;

                case Config.tipoVazia:
                    indexLista = indexVazia;
                    casaAtual = tabuleiroAtual.buscaPorCasa(indexLista);
                    casaAtual.insereNome(nomeCasa);
                    casaAtual.inserePosicao(posicaoCasa);
                    break;
                default:
                    break;
            }
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(new File(salvarArquivo), tabuleiroAtual);
        } catch (IOException e) {
            e.printStackTrace();
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
                propriedadeAtual = (Propriedade)casaAtual;
                if (propriedadeAtual.temDono()) {
                    mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.propriedadeComDono);
                    mensagemJogador.defineValorEvolucao(((Imovel) propriedadeAtual).obtemPrecoEvolucao());
                } else {
                    mensagemJogador.atualizaMensagem(null, propriedadeAtual, Eventos.propriedadeSemDono);
                }
                break;
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
            removeDono(propriedades.get(i));
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

    public void inserePropriedadeNaPilha(int id) {
        int imovelId;
        int imovelNivel;
        if (casasTabuleiro[id].obtemTipo() == Config.tipoImovel) {
            Imovel imovelAtual = (Imovel)casasTabuleiro[id];
            imovelId = imovelAtual.obtemId();
            imovelNivel = imovelAtual.obtemNivelImovel();
            pilhaPropriedades.push(new Dupla<Integer,Integer>(imovelId, imovelNivel));
        }
    }
  
    public String obtemNomeCasa(int id) {
        return casasTabuleiro[id].obtemNome();
    }

    public String obtemValorPropriedade(int id) {
        return Integer.toString(((Propriedade)casasTabuleiro[id]).obtemValorPropriedade());
    }

    public int obtemNivelPropriedade(int id) {
        int imovelNivel = 0;
    
        if (casasTabuleiro[id].obtemTipo() == Config.tipoImovel) {
            Imovel imovelAtual = (Imovel)casasTabuleiro[id];
            imovelNivel = imovelAtual.obtemNivelImovel();
        }

        return imovelNivel;
    }
}
