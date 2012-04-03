package damas.common.model;

public class Dama {

    private boolean blanca, reina;

    public Dama(boolean blanca) {
        this.blanca = blanca;
        this.reina = false;
    }

    public boolean isBlanca() {
        return blanca;
    }

    public boolean isNegra() {
        return !blanca;
    }

    public boolean isReina() {
        return reina;
    }

    public void setReina(boolean reina) {
        this.reina = reina;
    }
}