package Nucleo.Atributos;
import Nucleo.Atributos.Casa;

final public class Receita extends Casa {
    // Seguir roteiro do readme para implementacao dos impostos;
    public Receita(int id) {
        this.id = id;
        this.tipo = Config.tipoReceita;
    }
}
