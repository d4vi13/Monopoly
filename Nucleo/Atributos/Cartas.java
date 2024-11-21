package Nucleo.Atributos;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class Cartas {
    // Definir as cartas e a raridade de cada uma delas (20 a 30 cartas diferentes?)
    private Random random;
    private int sorteio;
    private Cartas carta;

    private List<String> cartasComuns = Arrays.asList(
        // sorte1,
        // sorte2,
        // sorte3,
        // sorte4,
        // sorte5,
        // sorte6,
        // sorte7,
        // sorte8,
        // sorte9,
        // reves1(),
        // reves2(),
        // reves3(),
        // reves4(),
        // reves5(),
        // reves6(),
        // reves7(),
        // reves8(),
        // reves9()
    );
    private List<String> cartasRaras = Arrays.asList(
        // sorte10(),
        // sorte11(),
        // sorte12(),
        // sorte13(),
        // reves10(),
        // reves11(),
        // reves12(),
        // reves13()
    );
    private List<String> cartasEpicas = Arrays.asList(
        // sorte14(),
        // sorte15(),
        // reves14(),
        // reves15()
    );


    private String sorteioDaCarta() {
        random = new Random();

        // Sorteio baseado em probabilidades fixas
        sorteio = random.nextInt(100);  // sorteio entre 0 e 99

        // Carta Comum tem 70% de chance (0-69)
        // Carta Rara tem 25% de chance (70-94)
        // Carta Epica tem 5% de chance (95-99)
        if (sorteio < 70) {
            return cartasComuns.get(random.nextInt(cartasComuns.size()));
        } else if (sorteio < 95) {
            return cartasRaras.get(random.nextInt(cartasRaras.size()));
        } else {
            return cartasEpicas.get(random.nextInt(cartasEpicas.size()));
        }
    }

    public String retiraCarta() {
        carta = new Cartas();
        return carta.sorteioDaCarta();
    }

}
