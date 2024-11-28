package Nucleo.Aux;

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

    public boolean casaLivre() {
        return (temDono != null) && !(temDono.isEmpty());
    }
}
