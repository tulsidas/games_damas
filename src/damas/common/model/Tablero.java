package damas.common.model;

import java.util.ArrayList;
import java.util.Collection;

public class Tablero {

    private Casillero[] casilleros;

    private boolean turnoBlancas;

    public Tablero() {
        casilleros = new Casillero[64];

        init();

        turnoBlancas = true;
    }

    /**
     * Pone las damas en la posici�n de inicio
     */
    public void init() {
        // creo los 64 casilleros;
        for (int i = 0; i < 8; i++) { // ITERO Y
            // bajo de fila, a la izq de todo
            for (int j = 0; j < 8; j++) { // ITERO X
                Casillero c = new Casillero(j, i);

                if (i <= 2 && ((i + j) % 2) != 0) {
                    // pongo una dama negra
                    c.setDama(new Dama(false));
                }
                if (i >= 5 && ((i + j) % 2) != 0) {
                    // pongo una dama blanca
                    c.setDama(new Dama(true));
                }

                // casilleros.add(c);
                casilleros[i * 8 + j] = c;
            }
        }
    }

    /**
     * Verifica si puede mover una dama desde <code>origen</code> a
     * <code>destino</code>
     * 
     * @param origen
     *            PRE: tiene dama
     * @param destino
     * @return <code>false</code> si la jugada es invalida
     */
    public boolean movidaFactible(Casillero origen, Casillero destino) {
        // no puedo mover a un casillero inexistente ni a una casilla ocupada
        if (destino != null && destino.getDama() == null) {

            if (origen.getDama().isReina()) {
                // estoy moviendome en diagonal?
                if (Math.abs(destino.getTx() - origen.getTx()) == Math
                        .abs(destino.getTy() - origen.getTy())) {
                    // est� el camino libre hasta destino?
                    int dx = (int) Math
                            .signum(destino.getTx() - origen.getTx());
                    int dy = (int) Math
                            .signum(destino.getTy() - origen.getTy());

                    int x = origen.getTx();
                    int y = origen.getTy();

                    do {
                        x += dx;
                        y += dy;

                        Casillero cas = getCasillero(x, y);
                        if (cas.getDama() != null) {
                            return false;
                        }
                    }
                    while (x != destino.getTx() && y != destino.getTy());

                    // via libre
                    return true;
                }
            }
            else {// no es reina
                // estoy moviendo UN casillero a la izq o der? y avanzando UN
                // casillero?
                if (destino.getTx() == origen.getTx() - 1
                        || destino.getTx() == origen.getTx() + 1) {
                    // estoy avanzando UN casillero?
                    return destino.getTy() == origen.getTy()
                            + (origen.getDama().isBlanca() ? -1 : 1);
                }
            }
        }
        return false;
    }

    /**
     * Verifica si puede comer una dama desde <code>origen</code> a
     * <code>destino</code>
     * 
     * @param origen
     *            PRE: tiene dama
     * @param destino
     * @return <code>false</code> si la jugada es invalida
     */
    public boolean comidaFactible(Casillero origen, Casillero destino) {
        if (destino != null && destino.getDama() == null) {
            if (origen.getDama().isReina()) {
                // estoy moviendome en diagonal?
                if (Math.abs(destino.getTx() - origen.getTx()) == Math
                        .abs(destino.getTy() - origen.getTy())) {

                    // esta el camino libre hasta la dama a comer?
                    int dx = (int) Math
                            .signum(destino.getTx() - origen.getTx());
                    int dy = (int) Math
                            .signum(destino.getTy() - origen.getTy());

                    int x = origen.getTx();
                    int y = origen.getTy();

                    // hasta el antepenultimo
                    while (x != destino.getTx() - 2 * dx
                            && y != destino.getTy() - 2 * dy) {
                        x += dx;
                        y += dy;

                        Casillero cas = getCasillero(x, y);
                        if (cas == null || cas.getDama() != null) {
                            return false;
                        }
                    }
                    // via libre!

                    // en la anteultima casilla hay una dama del otro?
                    Dama d = getCasillero(destino.getTx() - dx,
                            destino.getTy() - dy).getDama();

                    return d != null
                            && d.isBlanca() != origen.getDama().isBlanca();
                }
            }
            else {
                if (origen.getDama().isBlanca()) {
                    //
                    // blanca avanza arriba derecha
                    //
                    if (destino.getTx() == origen.getTx() + 2
                            && destino.getTy() == origen.getTy() - 2) {
                        // en la que salto hay una del oponente
                        Dama saltada = getCasillero(origen.getTx() + 1,
                                origen.getTy() - 1).getDama();

                        // si la que salte es del oponente, puedo
                        return (saltada != null && saltada.isBlanca() != origen
                                .getDama().isBlanca());
                    }

                    //
                    // blanca avanza arriba izquierda
                    //
                    if (destino.getTx() == origen.getTx() - 2
                            && destino.getTy() == origen.getTy() - 2) {
                        // en la que salto hay una del oponente
                        Dama saltada = getCasillero(origen.getTx() - 1,
                                origen.getTy() - 1).getDama();

                        // si la que salte es del oponente, puedo
                        return (saltada != null && saltada.isBlanca() != origen
                                .getDama().isBlanca());
                    }
                }

                if (origen.getDama().isNegra()) {
                    //
                    // negra avanza abajo derecha
                    //
                    if (destino.getTx() == origen.getTx() + 2
                            && destino.getTy() == origen.getTy() + 2) {
                        // en la que salto hay una del oponente
                        Dama saltada = getCasillero(origen.getTx() + 1,
                                origen.getTy() + 1).getDama();

                        // si la que salte es del oponente, puedo
                        return (saltada != null && saltada.isBlanca() != origen
                                .getDama().isBlanca());
                    }

                    //
                    // negra avanza abajo izquierda
                    //
                    if (destino.getTx() == origen.getTx() - 2
                            && destino.getTy() == origen.getTy() + 2) {
                        // en la que salto hay una del oponente
                        Dama saltada = getCasillero(origen.getTx() - 1,
                                origen.getTy() + 1).getDama();

                        // si la que salte es del oponente, puedo
                        return (saltada != null && saltada.isBlanca() != origen
                                .getDama().isBlanca());
                    }
                }
            }
        }
        return false;
    }

    /**
     * Verifica si puede mover o comer una dama desde <code>origen</code> a
     * <code>destino</code>
     * 
     * @param origen
     *            PRE: tiene dama
     * @param destino
     * @return <code>false</code> si la jugada es invalida
     */
    public boolean jugadaFactible(Casillero origen, Casillero destino) {
        if (puedeComer(turnoBlancas)) {
            return comidaFactible(origen, destino);
        }
        else {
            return movidaFactible(origen, destino);
        }
    }

    /**
     * Saca del juego a la dama entre <code>origen</code> y
     * <code>destino</code>
     * 
     * @param origen
     *            un casillero con una dama
     * @param destino
     *            un casillero vacio <br/>PRE: en el medio hay una dama del
     *            oponente
     */
    public void comerDama(Casillero origen, Casillero destino) {
        getCasilleroComestible(origen, destino).setDama(null);
    }

    /**
     * Mueve una dama
     * 
     * @param origen
     *            el casillero origen
     * @param destino
     *            el casillero destino
     */
    public void moverDama(Casillero origen, Casillero destino) {
        destino.setDama(origen.getDama());
        origen.setDama(null);

        // conversion en reina!
        if (destino.getDama().isBlanca() && destino.getTy() == 0) {
            // REINA
            destino.getDama().setReina(true);
        }
        else if (destino.getDama().isNegra() && destino.getTy() == 7) {
            // REINA
            destino.getDama().setReina(true);
        }
    }

    /**
     * @param tx
     *            posicion x del casillero (0-8)
     * @param ty
     *            posicion y del casillero (0-8)
     * @return el casillero
     */
    public Casillero getCasillero(int tx, int ty) {
        // in range
        if (tx >= 0 && tx < 8 && ty >= 0 && ty < 8) {
            return casilleros[ty * 8 + tx];
        }
        else {
            return null;
        }
    }

    public Casillero getCasillero(int code) {
        return casilleros[code];
    }

    /**
     * @return si el jugador tiene jugadas comidas disponibles
     */
    public boolean puedeComer(boolean blancas) {
        for (int i = 0; i < casilleros.length; i++) {
            Casillero cas = casilleros[i];
            if (cas.getDama() != null && cas.getDama().isBlanca() == blancas) {
                // puede esta dama comer?
                if (puedeComer(cas)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @param cas
     *            el casillero <b>PRE</b>: tiene una dama
     * @return si la dama del casillero puede realizar una comida
     */
    public boolean puedeComer(Casillero cas) {
        // tengo que ver tooooodas las posibles diagonales...
        return esComidaFactible(cas, +1, +1) || esComidaFactible(cas, +1, -1)
                || esComidaFactible(cas, -1, +1)
                || esComidaFactible(cas, -1, -1);
    }

    /**
     * Busca en la diagonal si hay comida factible <br/> <b>PRE:</b>
     * <code>cas.getDama() != null</code>
     * 
     * @param cas
     *            casillero origen
     * @param dx
     *            direccion hacia donde avanza diagonalmente x: +/-1
     * @param dy
     *            direccion hacia donde avanza diagonalmente x: +/-1
     * @return si la reina tiene comidas factibles
     */
    private boolean esComidaFactible(Casillero cas, int dx, int dy) {
        return comidaDiagonal(cas, dx, dy) != null;
    }

    /**
     * <code>cas.getDama() != null</code>
     * 
     * @param cas
     *            casillero origen
     * @param dx
     *            direccion hacia donde avanza diagonalmente x: +/-1
     * @param dy
     *            direccion hacia donde avanza diagonalmente y: +/-1
     * @return las comidas factibles en esta diagonal
     */
    private Jugada comidaDiagonal(Casillero cas, int dx, int dy) {
        int x = cas.getTx();
        int y = cas.getTy();

        // salto simple
        if (comidaFactible(cas, getCasillero(x + 2 * dx, y + 2 * dy))) {
            return new Jugada(cas, getCasillero(x + 2 * dx, y + 2 * dy)/*
                                                                         * ,
                                                                         * true
                                                                         */);
        }

        // salto real
        if (cas.getDama().isReina()) {
            // avanzo mientras tenga via libre o me vaya del tablero
            Casillero prox = null;
            do {
                x += dx;
                y += dy;
                prox = getCasillero(x, y);
            }
            while (prox != null && prox.getDama() == null);

            if (prox == null) {
                // me fui del tablero
                return null;
            }
            else { // prox.getDama != null

                // si esta dama es mia, chau
                if (prox.getDama().isBlanca() == cas.getDama().isBlanca()) {
                    return null;
                }
                else {
                    // es del otro!
                    // si el casillero siguiente existe y esta vacio, puedo
                    // comer
                    Casillero sig = getCasillero(x + dx, y + dy);

                    if (sig != null && sig.getDama() == null) {
                        return new Jugada(cas, sig/* , true */);
                    }
                }
            }
        }

        return null;
    }

    /**
     * @return una Collection<Movida> con las movidas disponibles (si hay
     *         comidas solo comidas, caso contrario las movidas)
     */
    private Collection<Jugada> jugadas(boolean blancas) {
        Collection<Jugada> ret = new ArrayList<Jugada>(); // new
        // NonNullVector();

        if (puedeComer(blancas)) {
            // devuelvo solo comidas
            for (int i = 0; i < casilleros.length; i++) {
                Casillero cas = casilleros[i];
                if (cas.getDama() != null
                        && cas.getDama().isBlanca() == blancas) {
                    // puede mi dama comer?

                    ret.add(comidaDiagonal(cas, +1, +1));
                    ret.add(comidaDiagonal(cas, -1, +1));
                    ret.add(comidaDiagonal(cas, +1, -1));
                    ret.add(comidaDiagonal(cas, -1, -1));
                }
            }
        }
        else {
            // devuelvo las movidas
            for (int i = 0; i < casilleros.length; i++) {
                Casillero cas = casilleros[i];
                if (cas.getDama() != null
                        && cas.getDama().isBlanca() == blancas) {
                    // puede esta dama mover?
                    if (movidaFactible(cas, getCasillero(cas.getTx() + 1, cas
                            .getTy() + 1))) {
                        ret.add(new Jugada(cas, getCasillero(cas.getTx() + 1,
                                cas.getTy() + 1)/* , false */));
                    }
                    else if (movidaFactible(cas, getCasillero(cas.getTx() + 1,
                            cas.getTy() - 1))) {
                        ret.add(new Jugada(cas, getCasillero(cas.getTx() + 1,
                                cas.getTy() - 1)/* , false */));
                    }
                    else if (movidaFactible(cas, getCasillero(cas.getTx() - 1,
                            cas.getTy() + 1))) {
                        ret.add(new Jugada(cas, getCasillero(cas.getTx() - 1,
                                cas.getTy() + 1)/* , false */));

                    }
                    else if (movidaFactible(cas, getCasillero(cas.getTx() - 1,
                            cas.getTy() - 1))) {
                        ret.add(new Jugada(cas, getCasillero(cas.getTx() - 1,
                                cas.getTy() - 1)/* , false */));
                    }
                }
            }
        }

        return ret;
    }

    /**
     * @return Si hay movidas posibles (caso contrario perdio el juego)
     */
    public boolean hayJugadas(boolean blancas) {
        return !jugadas(blancas).isEmpty();
    }

    /**
     * @return Si le quedan damas
     */
    private boolean quedanDamas(boolean blancas) {
        for (int i = 0; i < casilleros.length; i++) {
            Casillero cas = casilleros[i];
            if (cas.getDama() != null && cas.getDama().isBlanca() == blancas) {
                return true;
            }
        }

        return false;
    }

    /**
     * 
     * @return si perdio la partida (se quedo sin fichas o no puede mover)
     */
    public boolean perdioPartida(boolean blancas) {
        return !hayJugadas(blancas) || !quedanDamas(blancas);
    }

    /**
     * @param origen
     * @param destino
     * @return el casillero anterior a <code>destino</code> desde
     *         <code>origen</code>
     */
    public Casillero getCasilleroComestible(Casillero origen, Casillero destino) {
        int dx = (int) Math.signum(destino.getTx() - origen.getTx());
        int dy = (int) Math.signum(destino.getTy() - origen.getTy());

        return getCasillero(destino.getTx() - dx, destino.getTy() - dy);
    }

    /**
     * @param origen
     *            casillero origen
     * @param destino
     *            casillero destino
     * @return todos los casilleros en fila desde origen a destino (incluyendo a
     *         estos)
     */
    public Collection<Casillero> getCasillerosIntermedios(Casillero origen,
            Casillero destino) {
        int dx = (int) Math.signum(destino.getTx() - origen.getTx());
        int dy = (int) Math.signum(destino.getTy() - origen.getTy());

        int x = origen.getTx();
        int y = origen.getTy();

        Collection<Casillero> ret = new ArrayList<Casillero>(); // NonNullVector();
        ret.add(origen);

        do {
            x += dx;
            y += dy;

            ret.add(getCasillero(x, y));
        }
        while (x != destino.getTx() && y != destino.getTy());

        return ret;
    }

    public void makeMove(int from, int to) {
        makeMove(casilleros[from], casilleros[to]);
    }

    public void makeMove(Casillero origen, Casillero destino) {
        if (comidaFactible(origen, destino)) {
            comerDama(origen, destino);
            moverDama(origen, destino);

            // si la que comio puede seguir comiendo, debe
            if (puedeComer(destino)) {
                // mantengo el turno
            }
            else {
                // cambio el turno
                turnoBlancas = !turnoBlancas;
            }
        }
        else {
            moverDama(origen, destino);
            // movida siempre cambia el turno
            turnoBlancas = !turnoBlancas;
        }

    }

    public boolean isTurno(boolean blancas) {
        if (blancas) {
            return turnoBlancas;
        }
        else {
            return !turnoBlancas;
        }
    }
}