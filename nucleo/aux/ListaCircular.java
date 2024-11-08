package nucleo.aux;

public class ListaCircular<T> {
    private Nodo<T> iterador, ini, fim;

    public void setIterador() {
        iterador = ini;
    }
    
    public T getIteradorElem() {
        return iterador.getElemento();
    }

    public void iteradorProx() {
        iterador = iterador.getProx();
    }

    public void addLista(T elemento) {
        if (ini == null) {
            ini = new Nodo<T>(elemento);
            ini.setProx(fim);
            fim = ini;
        } else {
            Nodo<T> tmp = new Nodo<T>(elemento);
            fim.setProx(tmp);
            tmp.setProx(ini);
            fim = tmp;
        }
    }
}

final class Nodo <T> {
    private Nodo<T> prox;
    private T elemento;

    public Nodo(T elemento) {
        this.elemento = elemento;
    }

    public void setProx(Nodo<T> nodo) {
        this.prox = nodo;
    }

    public Nodo<T> getProx() {
        return this.prox;
    }

    public T getElemento() {
        return this.elemento;
    }
}