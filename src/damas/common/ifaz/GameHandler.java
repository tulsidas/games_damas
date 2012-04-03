package damas.common.ifaz;

import common.ifaz.BasicGameHandler;

/**
 * Mensajes que llegan al cliente
 */
public interface GameHandler extends BasicGameHandler {

     void jugada(int from, int to);

    void tablas();

    void aceptaTablas(boolean acepta);
}
