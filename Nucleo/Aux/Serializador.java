package Nucleo.Aux

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
            ois = new ObjectInpuStream(new FileInputStream(backup)); 
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }
    

}
