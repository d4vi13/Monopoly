package Nucleo.Atributos;
import Nucleo.Atributos.Casa;
import Nucleo.Atributos.Jogador;

public class Propriedade extends Casa {
    private boolean temDono;
    private int dono;
    protected int valorInicial;
    protected int valorDeVenda;
    protected int valorAluguel;
    protected final static int taxaAluguel = 10;

    public boolean temDono() {
        return this.temDono;
    }

    public void setDono(int idDono) {
        this.temDono = true;
        this.dono = idDono;
    }

    public void removeDono() {
        this.temDono = false;
        this.dono = -1;
    }

    public int obtemValorPropriedade(){
        return this.valorDeVenda;
    }

    public int obtemAluguel() {
        return this.valorAluguel;
    }
}

final class Imovel extends Propriedade {
    private int nivel;
    
    public Imovel(String s, int id, int valor) {
        this.nome = s;
        this.id = id;
        this.tipo = Config.tipoImovel;
        this.valorInicial = valor;
        this.valorDeVenda = valor;
        this.valorAluguel = valor / taxaAluguel;
        this.nivel = 0;
    }

    public void resetarValores() {
        this.valorDeVenda = valorInicial;
        this.valorAluguel = valorInicial / taxaAluguel;
        this.nivel = 0;
    }
}

final class Empresa extends Propriedade {
    public Empresa(String s, int id, int valor) {
        this.nome = s;
        this.id = id;
        this.tipo = Config.tipoEmpresa;
        this.valorInicial = valor;
        this.valorDeVenda = valor;
        this.valorAluguel = valor / taxaAluguel;
    }
}