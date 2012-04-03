package damas.common.model;

public class Jugada {
    // private boolean comida;

    private Casillero origen, destino;

    public Jugada(Casillero origen, Casillero destino
    // , boolean comida
    ) {
        this.origen = origen;
        this.destino = destino;
        // this.comida = comida;
    }

    // public boolean isComida() {
    // return comida;
    // }

    // public void setComida(boolean comida) {
    // this.comida = comida;
    // }

    public Casillero getDestino() {
        return destino;
    }

    // public void setDestino(Casillero destino) {
    // this.destino = destino;
    // }

    public Casillero getOrigen() {
        return origen;
    }

    // public void setOrigen(Casillero origen) {
    // this.origen = origen;
    // }

    @Override
    public String toString() {
        return origen + " -> " + destino;
    }
}
