import Nucleo.Aux.ListaCircular;

public class Main {
    public static void main(String[] args) {
        ListaCircular<Integer> l = new ListaCircular<>();

        l.addLista(1);
        l.addLista(2);
        l.addLista(3);
        l.setIterador();
        int i = 4;
        while (i-- != 0) {
            System.out.println(l.getIteradorElem());
            l.iteradorProx();
        }

        System.out.println(l.getIteradorElem());
        l.iteradorProx();
        Integer I = l.getIteradorElem();
        l.tiraLista(I);
        System.out.println(l.getIteradorElem());
    }
}