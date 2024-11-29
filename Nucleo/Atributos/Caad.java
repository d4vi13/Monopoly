package Nucleo.Atributos;

public class Caad {
    // true == esta de ferias
    // false == nao esta de ferias
    public Boolean verificaFerias(int qtdRodadas) {
        if (qtdRodadas > 0) return true;
        else return false;
    }
}
