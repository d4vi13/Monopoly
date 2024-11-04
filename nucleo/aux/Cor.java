package nucleo.aux;

public enum Cor {
    VERMELHO(0xff, 0x00, 0x00),
    VERDE(0xff, 0x00, 0x00),
    AMARELO(0xff, 0x00, 0x00),
    AZUL(0xff, 0x00, 0x00),
    PRETO(0xff, 0x00, 0x00),
    BRANCO(0xff, 0x00, 0x00);

    private int rgb[];

    private Cor(int red, int green, int blue) {
        rgb = new int[3];
        this.rgb[0] = red;
        this.rgb[1] = blue;
        this.rgb[2] = green;
    }

    public int[] obterValoresRgb() {
        return this.rgb;
    } 
} 
