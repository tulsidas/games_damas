package damas.common.model;

public class DamaPos {

    private boolean blanca, reina;

    private int x, y;

    public DamaPos(int x, int y, boolean blanca, boolean reina) {
        this.x = x;
        this.y = y;
        this.blanca = blanca;
        this.reina = reina;
    }

    public boolean isBlanca() {
        return blanca;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isReina() {
        return reina;
    }

    public String toString() {
        String ret = reina ? "REINA " : " DAMA ";
        ret += blanca ? "BLANCA " : "NEGRA ";
        ret += "(" + x + ", " + y + ")";

        return ret;
    }

}
