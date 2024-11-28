package Nucleo.Atributos;
import Nucleo.Atributos.Casa;
import Nucleo.Atributos.Jogador;

public class Propriedade extends Casa {
    private boolean temDono;
    private Jogador dono;
    protected int valorInicial;
    protected int valorDeVenda;
    protected int valorAluguel;

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

    public int obtemValorPropriedade(){
        return this.valorDeVenda;
    }

    public int obtemAluguel() {
        return this.valorAluguel;
    }
}

final class Imovel extends Propriedade {
    public Imovel(String s, int id, int valor) {
        this.nome = s;
        this.id = id;
        this.tipo = Config.tipoImovel;
        this.valorInicial = valor;
        this.valorDeVenda = valor;
    }
}

final class Empresa extends Propriedade {
    public Empresa(String s, int id, int valor) {
        this.nome = s;
        this.id = id;
        this.tipo = Config.tipoEmpresa;
        this.valorInicial = valor;
        this.valorDeVenda = valor;
    }
}