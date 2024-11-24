package Nucleo.Atributos;
import Nucleo.Atributos.Casa;
import Nucleo.Atributos.Jogador;

public class Propriedade extends Casa {
    private boolean temDono;
    private Jogador dono;
    private int valor;

    public boolean temDono() {
        return this.temDono;
    }

    public void setDono(Jogador novoDono) {
        this.temDono = true;
        this.dono = novoDono;
    }

    public void removeDono() {
        this.temDono = false;
        this.dono = null;
    }
}

final class Imovel extends Propriedade {
    public Imovel(int id) {
        this.id = id;
        this.tipo = Config.tipoImovel;
    }
}

final class Empresa extends Propriedade {
    public Empresa(int id) {
        this.id = id;
        this.tipo = Config.tipoEmpresa;
    }
}