package Nucleo.Aux;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CarregaTabuleiro {
    private final int totalCasas = 32;

    @JsonProperty("casas")
    private List<infoTabuleiro> casas;

    public CarregaTabuleiro () {
        this.casas = new ArrayList<>();
    }

    public int obtemTotalCasas() {
        return this.totalCasas;
    }

    public List<infoTabuleiro> obtemCasas() {
        return this.casas;
    }

    public void insereCasa(infoTabuleiro casaAtual) {
        this.casas.add(casaAtual);
    }

    public int buscaPorCasa(String tipoCasa) {
        String tipoAtual;
        for (int i = 0; i < casas.size(); ++i) {
            tipoAtual = casas.get(i).obtemTipo();
            if (tipoAtual.equals(tipoCasa)) {
                return i;
            }
        }
        
        return -1;
    }

    public infoTabuleiro buscaPorCasa(int index) {
        return casas.get(index);
    } 
}