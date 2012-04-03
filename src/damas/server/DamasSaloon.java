package damas.server;

import org.apache.mina.common.IoSession;

import server.AbstractSaloon;

import common.ifaz.POSTHandler;

import damas.common.ifaz.DamasServerHandler;
import damas.common.ifaz.SaloonHandler;

public class DamasSaloon extends AbstractSaloon implements SaloonHandler,
        DamasServerHandler {

    public DamasSaloon(int id, POSTHandler poster) {
        super(id, poster);
    }

    @Override
    protected DamasServerRoom getRoom(IoSession session) {
        return (DamasServerRoom) super.getRoom(session);
    }

    public void createRoom(IoSession session, int puntos) {
        // no se usa
        createRoom(session, puntos, 15);
    }

    public void createRoom(IoSession session, int puntos, int minutos) {
        DamasServerRoom asr = new DamasServerRoom(this, session, puntos,
                minutos);

        createRoom(session, puntos, asr);
    }

    // Damas methods
    public void jugada(IoSession session, int from, int to) {
        getRoom(session).jugada(session, from, to);
    }

    public void tablas(IoSession session) {
        getRoom(session).tablas(session);
    }

    public void aceptaTablas(IoSession session, boolean acepta) {
        getRoom(session).aceptaTablas(session, acepta);
    }
}
