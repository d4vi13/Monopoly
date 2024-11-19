package Nucleo.Atributos;
import Nucleo.Atributos.Casa;
import Nucleo.Atributos.Jogador;

public class Propriedades extends Casa {
    private boolean temDono;
    private Jogador dono;

    public void setDono(Jogador novoDono) {
        this.temDono = true;
        this.dono = novoDono;
    }

    public void removeDono() {
        this.temDono = false;
        this.dono = null;
    }
}

final class Imovel extends Propriedades {
    public Imovel(int id) {
        this.id = id;
        this.tipo = Config.tipoImovel;
    }
}

final class Companhia extends Propriedades {
    public Companhia(int id) {
        this.id = id;
        this.tipo = Config.tipoCompanhia;
    }
}