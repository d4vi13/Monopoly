package Nucleo.Atributos;
import Nucleo.Atributos.Casa;

final public class Recepcao extends Casa {

    public Recepcao(int id) {
        this.id = id;
        this.tipo = Config.tipoRecepcao;
    }

    // Valor total dos imoveis
    // menor igual 220 mil imposto 0
    // menor igual 440 mil imposto 20%
    // menor igual 660 mil imposto 30%
    // maior que 660 mil imposto 40%
    public int pagarImposto (int valorTotalImoveis) {
        if (valorTotalImoveis <= 220000) {
            return 0;
        } else if (valorTotalImoveis <= 440000) {
            return valorTotalImoveis * 20 / 100;
        } else if (valorTotalImoveis <= 660000) {
            return valorTotalImoveis * 30 / 100;
        } else {
            return valorTotalImoveis * 40 / 100;
        }
    }

}
