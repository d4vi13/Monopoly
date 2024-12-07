package Nucleo.Aux;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class infoTabuleiro {

    @JsonProperty("nomes")
    private List<String> nomes;
    
    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("posicoes")
    private List<Integer> posicoes;

    @JsonProperty("temDono")
    private List<Boolean> temDono;

    @JsonProperty("donoId")
    private List<Integer> donoId;

    @JsonProperty("valores")
    private List<Integer> valores;

    @JsonProperty("niveis")
    private List<Integer> niveis;

    public infoTabuleiro() {}

    public infoTabuleiro(String tipo) {
        this.nomes = new ArrayList<>();
        this.tipo = tipo;
        this.posicoes = new ArrayList<>();
        this.temDono = new ArrayList<>();
        this.donoId = new ArrayList<>();
        this.valores = new ArrayList<>();
        this.niveis = new ArrayList<>();
    }

    public List<String> obtemNomes() {
        return this.nomes;
    }

    public String obtemTipo() {
        return this.tipo;
    }

    public List<Integer> obtemPosicoes() {
        return this.posicoes;
    }

    public List<Boolean> obtemTemDono() {
        return this.temDono;
    }

    public List<Integer> obtemDonoId() {
        return this.donoId;
    }

    public List<Integer> obtemValores() {
        return this.valores;
    }

    public List<Integer> obtemNiveis() {
        return this.niveis;
    }

    public void insereNome(String nome) {
        this.nomes.add(nome);
    }

    public void defineTipo(String tipo) {
        this.tipo = tipo;
    }

    public void inserePosicao(Integer pos) {
        this.posicoes.add(pos);
    }

    public void insereDono(Boolean temDono) {
        this.temDono.add(temDono);
    }

    public void insereDonoId(Integer donoId) {
        this.donoId.add(donoId);
    }

    public void insereValor(Integer valor) {
        this.valores.add(valor);
    }

    public void insereNivel(Integer nivel) {
        this.niveis.add(nivel);
    }
}
