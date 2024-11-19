package Nucleo.Atributos;
import Nucleo.Atributos.Casa;

final public class Prisao extends Casa {
    private boolean[] statusPreso;
    
    public Prisao (int id) {
        this.id = id;
        this.tipo = Config.tipoPrisao;
    }
}
