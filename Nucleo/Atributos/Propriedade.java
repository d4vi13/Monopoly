package Nucleo.Atributos;

public class Propriedade extends Casa {
    private boolean temDono;
    private boolean hipotecada = false;
    private int dono;
    protected int valorInicial;
    protected int valorDeVenda;
    protected int valorAluguel;
    protected final static int taxaAluguel = 5;

    public boolean temDono() {
        return this.temDono;
    }

    public void setDono(int idDono) {
        this.temDono = true;
        this.dono = idDono;
    }

    public void removeDono() {
        deshipotecar();
        this.temDono = false;
        this.dono = -1;
    }

    public void hipotecar(){
        hipotecada = true;
    }

    public void deshipotecar(){
        hipotecada = false;
    }


    public boolean estaHipotecada(){
        return hipotecada;
    }

    public int obtemIdDono() {
        return this.dono;
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
    private final static double upgradeUm = 1.2;
    private final static double upgradeDois = 1.5;
    private final static double upgradeTres = 2;
    private final static double upgradeQuatro = 2.5;

    public Imovel(String s, int id, int valor) {
        this.nome = s;
        this.id = id;
        this.tipo = Config.tipoImovel;
        this.valorInicial = valor;
        this.valorDeVenda = valor;
        this.valorAluguel = valor / taxaAluguel;
        this.nivel = 0;
    }

    private void evoluirImovel(int nivel) {
        this.nivel = nivel;
        switch (nivel) {
            case 1:
                this.valorDeVenda = (int)(valorInicial + valorInicial * upgradeUm);
                this.valorAluguel = this.valorDeVenda / taxaAluguel;
                break;
            case 2:
                this.valorDeVenda = (int)(valorInicial + valorInicial * upgradeDois);
                this.valorAluguel = this.valorDeVenda / taxaAluguel;
                break;
            case 3:
                this.valorDeVenda = (int)(valorInicial + valorInicial * upgradeTres);
                this.valorAluguel = this.valorDeVenda / taxaAluguel;
                break;
            case 4:
                this.valorDeVenda = (int)(valorInicial + valorInicial * upgradeQuatro);
                this.valorAluguel = this.valorDeVenda / taxaAluguel;
                break;
            default:
                resetarValores();
                break;
        }
    }

    public void evoluirImovel() {
        if (this.nivel < 3) {
            evoluirImovel(nivel + 1);
        }
    }

    public void resetarValores() {
        this.valorDeVenda = valorInicial;
        this.valorAluguel = valorInicial / taxaAluguel;
        this.nivel = 0;
    }

    public int obtemNivelImovel() {
        return this.nivel;
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
