package Nucleo.Aux;
import Nucleo.Aux.ListaCircular;
import Nucleo.Atributos.Jogador;
import java.io.*;

public class Serializador{
    private ObjectOutputStream oos;
    private ObjectInputStream ois; 

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

}
