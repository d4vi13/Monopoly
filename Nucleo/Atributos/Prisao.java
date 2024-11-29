package Nucleo.Atributos;

public class Prisao{
    // true == esta livre
    // false == esta preso
    public Boolean verificaPreso (int[] dados, int qtdRodadas) {
        if (qtdRodadas > 0) {
            if (dados[0] == dados[1]) return true;
            else return false;
        }
        return true;
    }

}
