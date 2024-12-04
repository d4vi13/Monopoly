package Nucleo.Aux;

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

    public boolean iteradorEhInicio(){
        return iterador == ini;
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

    // iteradorProx NAO deve ser chamado caso elemento removido foi o mesmo
    public void tiraLista(T elemento) {
        if (iterador.getElemento() == elemento) {
            iterador = iterador.getProx();
        }

        if (ini.getElemento() == elemento) {
            fim.setProx(ini.getProx());
            ini = ini.getProx();
        } else {
            for (Nodo<T> n = ini; n != fim; n = n.getProx()) {
                if (n.getProx().getElemento() == elemento) {
                    n.setProx(n.getProx().getProx());
                    break;
                }
            }
        }

    }
}

class Nodo <T> {
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
