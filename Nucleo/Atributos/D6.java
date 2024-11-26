package Nucleo.Atributos;
import java.util.Random;

public class D6 {
    private Random random;
    private int[] numero;
    
    public void jogaDado() {
        numero = new int[2];
        random = new Random();
        numero[0] = random.nextInt(6) + 1;
        numero[1] = random.nextInt(6) + 1;
    }

    public int obterValorDado(int indice) {
        if (numero == null || indice < 0 || indice >= 2) {
            throw new IllegalStateException("Os dados nao foram jogados ou indice incorreto.");
        }
        return numero[indice];
    }
}
