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
        Jogador jogador = jogadores.getIteradorElem();
        int id = jogador.obtemId();
        do{
            System.out.println("ta salvando");
            this.salvar(jogadores.getIteradorElem());
            jogadores.iteradorProx();
            jogador = jogadores.getIteradorElem();
        }while(id != jogador.obtemId()); 
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
    }

    public void carregar(Banco banco){
        try{
            banco = (Banco) ois.readObject();
        }catch(IOException | ClassNotFoundException exception){
            exception.printStackTrace();
        }
    }

    public int carregar(int numeroDeJogadores){
        try{
            this.numeroDeJogadores = ois.readInt();
            return this.numeroDeJogadores;
        }catch(IOException exception){
            exception.printStackTrace();
            return 0;
        }
    }
}
