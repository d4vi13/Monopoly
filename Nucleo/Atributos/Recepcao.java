package Nucleo.Atributos;
import Nucleo.Atributos.Casa;

final public class Recepcao extends Casa {
    // Seguir roteiro do readme para implementacao dos impostos;
    public Recepcao(int id) {
        this.id = id;
        this.tipo = Config.tipoRecepcao;
    }
}
