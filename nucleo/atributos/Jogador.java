package nucleo.atributos;
import nucleo.aux.Cor;

final public class Jogador {
    private String nome;
    private Cor cor;
    private int posicao;
    private int id;
    private Casa[] vet; // Mudar para um Id/ fazer com que cada casa tenha um id 
    private boolean falido; 

    public Jogador(){

    }

    public Jogador(String nome, Cor cor, int id){
        this.nome = nome;
        this.cor = cor;
        this.posicao = 0;
        this.id = id;
        this.falido = false;
    }
    
    public int obtemId() {
        return 0;
    }

}

