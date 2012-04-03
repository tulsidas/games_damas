package damas.common.ifaz;

import org.apache.mina.common.IoSession;

/**
 * Interfaz de los mensajes que recibe el Saloon de los clientes
 * 
 */
public interface SaloonHandler {

    /** una movida */
    void jugada(IoSession session, int from, int to);

    /** oferta de tablas */
    void tablas(IoSession session);

    /** respuesta de tablas */
    void aceptaTablas(IoSession session, boolean acepta);
}