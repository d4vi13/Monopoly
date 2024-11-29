package Nucleo.Atributos;
import java.util.Random;

public class D6 {
    private Random random;
    private int[] numero;

    public D6 () {
        this.random = new Random();
        this.numero = new int[2];
    }
    
    public void jogaDado() {
        this.numero[0] = random.nextInt(6) + 1;
        this.numero[1] = random.nextInt(6) + 1;
    }

    public int obterValorDado(int indice) {
        if (numero == null || indice < 0 || indice >= 2) {
            throw new IllegalStateException("Os dados nao foram jogados ou indice incorreto.");
        }
        return this.numero[indice];
    }
}
