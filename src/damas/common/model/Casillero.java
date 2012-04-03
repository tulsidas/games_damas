package damas.common.model;

public class Casillero {

    // la posicion del tablero
    private int tx, ty;

    private Dama dama;

    public Casillero(int tx, int ty) {
        this.tx = tx;
        this.ty = ty;
    }

    public String toString() {
        return "[Casillero] TX:" + tx + " | TY:" + ty;
    }

    public Dama getDama() {
        return dama;
    }

    public void setDama(Dama dama) {
        this.dama = dama;
    }

    /**
     * @return la posicion X en el tablero
     */
    public int getTx() {
        return tx;
    }

    /**
     * @return la posicion Y en el tablero
     */
    public int getTy() {
        return ty;
    }

    public int getCode() {
        // return (ty - 1) * 8 + (tx - 1);
        return ty * 8 + tx;

        // 1,1 -> 0*8 + 0 = 0
        // 8,8 -> (8-1)*8 + (8-1) = 7*8+7 = 63
    }
}