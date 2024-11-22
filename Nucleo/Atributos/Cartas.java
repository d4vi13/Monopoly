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
        new Carta ("Você fez um bom investimento", 0, 50),
        new Carta ("Receba um presente de aniversário de todos jogadores", 6, 10),
        new Carta ("Receba dividendos do banco", 0, 50),
        new Carta ("Você ganhou uma rifa no seu bairro", 0, 20),
        new Carta ("Receba um presente inesperado de um amigo", 0, 25),
        new Carta ("Encontrou dinheiro na rua", 0, 10),
        new Carta ("O seu curso rendeu dinheiro", 0, 90),
        new Carta ("Você recebeu o prêmio do melhor programador", 0, 40),
        new Carta ("Você ganhou no jogo do Tigrinho", 0, 50),
        // sorte9,
        new Carta ("Multa de excesso de valocidade", 1, 15),
        new Carta ("Você organizou uma festa de amigos", 1, 50),
        new Carta ("Doação a uma instituição de caridade", 1, 50),
        new Carta ("Despesas inesperadas no carro", 1, 80),
        new Carta ("Você saiu com namorada(o)", 1, 75),
        new Carta ("Taxa de associação ao clube local", 1, 60),
        new Carta ("Fez compra internacional, pague imposto", 1, 50),
        new Carta ("Fez viagem para Dubai", 1, 90),
        // reves8(),
        new Carta ("Reparo urgente no telhado de casa", 1, 60)
    );
    private List<Carta> cartasRaras = Arrays.asList(
        new Carta ("Avance para o início", 5, 200),
        new Carta ("Você ganhou na loteria", 0, 100),
        new Carta ("Trabalhou muito, receba uma recompensa do patrão", 0, 150),
        new Carta ("Você vendeu sua Ferrari", 0, 150),
        // sorte14() avance casas
        new Carta ("O juiz foi injusto, vá para a prisão", 4, 0),
        new Carta ("Você sonegou imposto", 1, 150),
        new Carta ("Gastos escolares do seu filho aumentou", 1, 200),
        new Carta ("Você acabou de ser roubado", 1, 150)
        // reves14() volte casas
    );
    private List<Carta> cartasEpicas = Arrays.asList(
        new Carta ("Você achou petróleo no seu jardim", 0, 400),
        new Carta ("Você é herdeiro", 0, 500),
        // reves15(),
        new Carta ("Você teve muita sorte", 1, 500)
    );


    private Carta sorteioDaCarta() {
        random = new Random();

        // Sorteio baseado em probabilidades fixas
        sorteio = random.nextInt(100);  // sorteio entre 0 e 99

        // Carta Comum tem 85% de chance (0-84)
        // Carta Rara tem 13% de chance (85-97)
        // Carta Epica tem 2% de chance (98-99)
        if (sorteio < 84) {
            return cartasComuns.get(random.nextInt(cartasComuns.size()));
        } else if (sorteio < 98) {
            return cartasRaras.get(random.nextInt(cartasRaras.size()));
        } else {
            return cartasEpicas.get(random.nextInt(cartasEpicas.size()));
        }
    }

    public Carta retiraCarta() {
        return sorteioDaCarta();
    }

}
