package Nucleo.Aux;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CarregaTabuleiro {
    private final int totalCasas = 32;

    @JsonProperty("casas")
    private List<infoTabuleiro> casas;

    public int obtemTotalCasas() {
        return this.totalCasas;
    }

    public List<infoTabuleiro> obtemCasas() {
        return this.casas;
    }
}