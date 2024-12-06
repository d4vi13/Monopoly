package Nucleo.Aux;
import Nucleo.Aux.ListaCircular;
import Nucleo.Atributos.Jogador;
import Nucleo.Atributos.Banco;
import java.io.*;

public class Serializador{
    private ObjectOutputStream oos;
    private ObjectInputStream ois; 
    private int numeroDeJogadores;

    public void iniciarBackup(String backup){
        try{
            oos = new ObjectOutputStream(new FileOutputStream(backup)); 
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void restaurarBackup(String backup){
        try{
            ois = new ObjectInputStream(new FileInputStream(backup)); 
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }
    
    public void salvar(Jogador jogador){
        try{
            oos.writeObject(jogador); 
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void salvar(ListaCircular<Jogador> jogadores){
        do{
            this.salvar(jogadores.getIteradorElem());
            jogadores.iteradorProx();
        }while(!jogadores.iteradorEhInicio()); 
    }

    public void salvar(Banco banco){
        try{
            oos.writeObject(banco);
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public void salvar(int numeroDeJogadores){
        try{
            oos.writeInt(numeroDeJogadores);
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public Jogador carregarJogador(){
        Jogador jogador;
        try{
            jogador = (Jogador) ois.readObject(); 
            return jogador;
        }catch(IOException | ClassNotFoundException exception){
            exception.printStackTrace();
            return null;
        }
    }

    public void carregar(ListaCircular<Jogador> jogadores){
        for(int i = 0; i < numeroDeJogadores; i++){
            System.out.println("aqui");
            jogadores.addLista(carregarJogador());
        }
        jogadores.setIterador();
        // Ajusta o iterador, pois o iterador foi o primeiro salvo
        for(int i = 0; i < numeroDeJogadores - 1; i++)
            jogadores.iteradorProx();
    }

    public void carregar(Banco banco){
        try{
            banco = (Banco) ois.readObject();
        }catch(IOException | ClassNotFoundException exception){
            exception.printStackTrace();
        }
    }

    public void carregar(int numeroDeJogadores){
        try{
            this.numeroDeJogadores = ois.readInt();
            numeroDeJogadores = this.numeroDeJogadores;
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }
}
