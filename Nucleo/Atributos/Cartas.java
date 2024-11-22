package Nucleo.Atributos;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public class Cartas {

    private Random random;
    private int sorteio;

    public static class Carta {
        private String descricao;
        private int tipo;
        private int valor;

        private Carta (String descricao, int tipo, int valor) {
            this.descricao = descricao;
            this.tipo = tipo;
            this.valor = valor;
        }

        public String obtemDescricao() {
            return descricao;
        }

        public int obtemTipo() {
            return tipo;
        }

        public int obtemValor() {
            return valor;
        }

    }
    // tipo 0 = Ganha dinheiro
    // tipo 1 = Perde dinheiro
    // tipo 2 = Avance casas
    // tipo 3 = Volte casas
    // tipo 4 = Va para prisao
    // tipo 5 = Va para inicio
    // tipo 6 = Recebe dinheiro de todos
    

    private List<Carta> cartasComuns = Arrays.asList(
        new Carta ("Você fez um bom investimento, receba 50", 0, 50),
        new Carta ("Receba um presente de aniversário de todos jogadores", 6, 10),
        new Carta ("Receba dividendos do banco, receba 50", 0, 50),
        new Carta ("Você ganhou uma rifa no seu bairro, receba 20", 0, 20),
        new Carta ("Receba um presente inesperado de um amigo", 0, 25),
        new Carta ("Encontrou dinheiro na rua, receba 10", 0, 10),
        new Carta ("O seu curso rendeu dinheiro, receba 90", 0, 90),
        new Carta ("Você recebeu o prêmio do melhor programador, receba 40", 0, 40),
        // sorte9,
        new Carta ("Multa de excesso de valocidade, pague 15", 1, 15),
        new Carta ("Você organizou uma festa de amigos, pague 50", 1, 50),
        new Carta ("Doação a uma instituição de caridade, pague 50", 1, 50),
        new Carta ("Despesas inesperadas no carro, pague 80", 1, 80),
        new Carta ("Você saiu com amigos e gastou 75", 1, 75),
        new Carta ("Taxa de associação ao clube local, pague 60", 1, 60),
        new Carta ("Fez compra internacional, pague 50 de imposto", 1, 50),
        // reves8(),
        new Carta ("Reparo urgente no telhado de casa, pague 60", 1, 60)
    );
    private List<Carta> cartasRaras = Arrays.asList(
        new Carta ("Avance para o início e receba 200", 5, 200),
        new Carta ("Você ganhou na loteria, receba 100", 0, 100),
        new Carta ("Trabalhou muito, receba a recompensa de 150", 0, 150),
        new Carta ("Você vendeu seu carro favorito por 150", 0, 150),
        // sorte14() avance casas
        new Carta ("O juiz foi injusto, vá para a prisão", 4, 0),
        new Carta ("Você negou imposto, pague 150", 1, 150),
        new Carta ("Gastos escolares do seu filho aumentou, pague 200", 1, 200),
        new Carta ("Você acabou de ser roubado, perde 150", 1, 150)
        // reves14() volte casas
    );
    private List<Carta> cartasEpicas = Arrays.asList(
        new Carta ("Você achou petróleo no seu jardim, ganhe 400", 0, 400),
        // sorte15(),
        // reves14(),
        new Carta ("Você teve muita sorte, pague 500", 1, 500)
    );


    private Carta sorteioDaCarta() {
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

    public Carta retiraCarta() {
        return sorteioDaCarta();
    }

}
