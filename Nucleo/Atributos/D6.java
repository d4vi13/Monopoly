package Nucleo.Atributos;
import java.util.Random;

public class D6 {
    private Random random;
    private int[] numero;
    
    public int[] jogaDado() {
        numero = new int[2];
        random = new Random();
        numero[0] = random.nextInt(6) + 1;
        numero[1] = random.nextInt(6) + 1;
        return numero;
    }
}
