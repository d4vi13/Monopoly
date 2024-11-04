package nucleo.atributos;
import nucleo.aux.Cor;

public class Jogador {
    private String nome;
    private Cor cor;
    private int posicao = 0;
    private int id;
    private Casa[]; // Mudar para um Id/ fazer com que cada casa tenha um id 
    private boolean falido = false; 

    Jogador(int id, String nome, Cor cor){
        
    }
    
    public int obtemId();

}

