package Nucleo.Atributos;
import java.util.Random;

public class Cartas {
    private Random random;

    public Cartas () {
        this.random = new Random();
    }

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
            return this.descricao;
        }

        public int obtemTipo() {
            return this.tipo;
        }

        public int obtemValor() {
            return this.valor;
        }

    }
    // tipo 0 = Ganha dinheiro
    // tipo 1 = Perde dinheiro
    // tipo 2 = Avance para CAAD
    // tipo 3 = Avance para Recepcao
    // tipo 4 = Va para prisao
    // tipo 5 = Va para inicio
    // tipo 6 = Recebe dinheiro de todos
    

    private Carta[] cartasComuns = {
        new Carta ("Você fez investimento na ECOMP e rendeu", 0, 50000),
        new Carta ("Recebeu um presente de aniversário de todos jogadores", 6, 20000),
        new Carta ("Recebeu dividendos do banco", 0, 50000),
        new Carta ("Você ganhou uma rifa do RU", 0, 20000),
        new Carta ("Recebeu um presente inesperado de um amigo", 0, 25000),
        new Carta ("Encontrou dinheiro no DINF", 0, 10000),
        new Carta ("O seu curso de JAVA rendeu dinheiro", 0, 90000),
        new Carta ("Você recebeu o prêmio do melhor programador", 0, 40000),
        new Carta ("Você ganhou no jogo do Tigrinho", 0, 50000),
        new Carta ("Não tinha aula hoje, mas você foi para UFPR e pagou ônibus", 1, 15000),
        new Carta ("Você pagou alguns salgados para os amigos", 1, 50000),
        new Carta ("Doação a uma instituição de caridade", 1, 50000),
        new Carta ("Despesas inesperadas no servidor do laboratório", 1, 80000),
        new Carta ("Você esqueceu de apagar as luzes", 1, 75000),
        new Carta ("Taxa de associação ao CAAD", 1, 60000),
        new Carta ("Fez compra de peça internacional, pague imposto", 1, 50000),
        new Carta ("Fez viagem para congressos", 1, 90000),
        new Carta ("Reparo urgente do servidor", 1, 60000)
    };
    private Carta[] cartasRaras = {
        new Carta ("Avance para o início", 5, 200000),
        new Carta ("Você ganhou um prêmio no congresso", 0, 100000),
        new Carta ("Trabalhou muito, receba uma recompensa do chefe", 0, 150000),
        new Carta ("Você vendeu seu computador", 0, 150000),
        new Carta ("Você merece participar de uma festa, vá para CAAD", 2, 0),
        new Carta ("Minerou Bitcoins com o servidor??? Vá para sala cofre", 4, 0),
        new Carta ("Você acabou de quebrar um disco rígido", 1, 150000),
        new Carta ("Gastos de manutenção do servidor aumentou", 1, 200000),
        new Carta ("Você acabou de ser roubado", 1, 150000),
        new Carta ("Alguém te denunciou jogando Minecraft no servidor, vá para sala cofre", 4, 0),
        new Carta ("Infelizmente você não conhece o DINF, vá para recepção", 3, 0)
    };
    private Carta[] cartasEpicas = {
        new Carta ("Você descobriu uma vulnerabilidade no servidor", 0, 400000),
        new Carta ("Você foi chamado para um projeto grande", 0, 450000),
        new Carta ("Você patrocinou a reforma do DINF", 1, 350000),
        new Carta ("Você teve muita sorte", 1, 500000)
    };


    private Carta sorteioDaCarta() {
        // Sorteio baseado em probabilidades fixas
        int sorteio = random.nextInt(100);  // sorteio entre 0 e 99

        // Carta Comum tem 80% de chance (0-79)
        // Carta Rara tem 16% de chance (80-95)
        // Carta Epica tem 4% de chance (96-99)
        if (sorteio < 80) {
            return cartasComuns[random.nextInt(cartasComuns.length)];
        } else if (sorteio < 96) {
            return cartasRaras[random.nextInt(cartasRaras.length)];
        } else {
            return cartasEpicas[random.nextInt(cartasEpicas.length)];
        }
    }

    public Carta retiraCarta() {
        return sorteioDaCarta();
    }

}
