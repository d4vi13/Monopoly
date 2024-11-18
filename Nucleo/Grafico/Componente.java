package Nucleo.Grafico;
abstract class Componente {
    protected int posx, posy, comp, alt;

    protected void definirDimensoes(int comp, int alt) {
        this.comp = comp;
        this.alt = alt;
    }

    protected void definirLocalizacao(int posx, int posy) {
        this.posx = posx;
        this.posy = posy;
    }

    protected void obterPosX() {

    }

    protected void obterPosY() {

    }

    protected void obterComp() {

    }

    protected void obterAlt() {

    }

    protected boolean contem(int x, int y) {
        return ((x >= posx && x <= posx + comp) &&
                (y >= posy && y <= posy + alt));
    }
}
