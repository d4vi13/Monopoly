package Nucleo.Atributos;

public class Casa {
    protected int id;
    protected int tipo;

    public class Config {
        public final static int tipoInicial = 0;
        public final static int tipoImovel = 1;
        public final static int tipoEmpresa = 2;
        public final static int tipoPrisao = 3;
        public final static int tipoCarta = 4;
        public final static int tipoCAAD = 5;
        public final static int tipoRecepcao = 6;
        public final static int tipoVazia = 7;
    }

    public Casa () { }

    public Casa (int id, int tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int obtemId() {
        return this.id;
    }    

    public int obtemTipo() {
        return this.tipo;
    }
}
