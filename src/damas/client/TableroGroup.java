package damas.client;

import pulpcore.Input;
import pulpcore.image.CoreImage;
import pulpcore.sprite.Group;
import pulpcore.sprite.ImageSprite;
import damas.common.model.Casillero;
import damas.common.model.Dama;
import damas.common.model.Tablero;

public class TableroGroup extends Group {

    private static final int TAM_CELDA = 45;

    private DamasScene scene;

    private Tablero tablero;

    private ImageSprite borde;

    private Casillero selected;

    // aca dibujo las fichas
    private Group fichas;

    private CoreImage[] fichasImg;

    // soy blancas?
    private boolean blancas;

    public TableroGroup(DamasScene parent, boolean blancas) {
        super(22, 70, 360, 360);

        this.tablero = new Tablero();
        tablero.init();

        this.scene = parent;
        this.blancas = blancas;

        // nada elegido
        this.selected = null;

        fichasImg = CoreImage.load("imgs/fichas.png").split(4);

        borde = new ImageSprite(CoreImage.load("imgs/borde.png"), 0, 0);
        borde.visible.set(false);
        add(borde);

        // las fichas arriba del tablero
        fichas = new Group();
        moveToTop(fichas);
        add(fichas);

        updateFichas();
    }

    @Override
    public void update(int elapsedTime) {
        if (enabled.get() && Input.isMouseReleased() && meToca()) {
            int viewX = Input.getMouseX();
            int viewY = Input.getMouseY();

            int file = (int) getLocalX(viewX, viewY) / TAM_CELDA;
            int rank = (int) getLocalY(viewX, viewY) / TAM_CELDA;

            if (contains(viewX, viewY)) {
                if (selected == null) {
                    // no hay nada seleccionado
                    if (hayFichaMia(rank, file)) {
                        markSelected(rank, file);
                    }
                }
                else {
                    // habia una pieza seleccionada
                    if (hayFichaMia(rank, file)) {
                        // volvi a cliquear en otra pieza mia, reselecciono
                        markSelected(rank, file);
                    }
                    else {
                        // cliquee en una vacia o en una del otro, muevo/como
                        Casillero destino = tablero.getCasillero(
                                (blancas ? file : 7 - file), (blancas ? rank
                                        : 7 - rank));

                        System.out.println("mover desde " + selected + " a "
                                + destino);

                        if (tablero.jugadaFactible(selected, destino)) {
                            tablero.makeMove(selected, destino);
                            updateFichas();

                            scene.makeMove(selected.getCode(), destino
                                    .getCode());
                        }
                        else {
                            System.out.println("invalid move");
                        }

                        clearSelected();
                    }
                }
            }
            else {
                clearSelected();
            }
        }
    }

    /**
     * @return si me toca seguir jugando
     */
    public boolean meToca() {
        return tablero.isTurno(blancas);
    }

    public void makeMove(int from, int to) {
        tablero.makeMove(from, to);
        updateFichas();
    }

    public void reset() {
        tablero.init();
        updateFichas();
        clearSelected();
    }

    public boolean isComida(int from, int to) {
        return tablero.comidaFactible(tablero.getCasillero(from), tablero
                .getCasillero(to));
    }

    public boolean puedeComer(int cas) {
        return tablero.puedeComer(tablero.getCasillero(cas));
    }

    private void updateFichas() {
        fichas.removeAll();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Dama dama = tablero.getCasillero(i, j).getDama();
                if (dama != null) {
                    if (blancas) {
                        fichas.add(new ImageSprite(getImgDama(dama), i
                                * TAM_CELDA, j * TAM_CELDA));
                    }
                    else {
                        fichas.add(new ImageSprite(getImgDama(dama), (7 - i)
                                * TAM_CELDA, (7 - j) * TAM_CELDA));
                    }
                }
            }
        }
    }

    /** @NonNull d */
    private CoreImage getImgDama(Dama d) {
        if (d.isBlanca()) {
            if (d.isReina()) {
                return fichasImg[1];
            }
            else {
                return fichasImg[0];
            }
        }
        else {
            if (d.isReina()) {
                return fichasImg[3];
            }
            else {
                return fichasImg[2];
            }
        }
    }

    private void markSelected(int rank, int file) {
        // seleccione una pieza mia
        borde.visible.set(true);
        borde.setLocation(file * TAM_CELDA, rank * TAM_CELDA);

        selected = tablero.getCasillero(blancas ? file : 7 - file,
                blancas ? rank : 7 - rank);
    }

    private void clearSelected() {
        selected = null;
        borde.visible.set(false);
    }

    private boolean hayFichaMia(int rank, int file) {
        if (blancas) {
            Dama dama = tablero.getCasillero(file, rank).getDama();
            return dama != null && dama.isBlanca();
        }
        else {
            Dama dama = tablero.getCasillero(7 - file, 7 - rank).getDama();
            return dama != null && dama.isNegra();
        }
    }
}