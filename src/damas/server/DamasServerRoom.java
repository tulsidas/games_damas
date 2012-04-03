package damas.server;

import org.apache.mina.common.IoSession;

import server.TwoPlayersServerRoom;

import common.messages.server.UpdatedPointsMessage;
import common.model.AbstractRoom;

import damas.common.messages.AceptaTablasMessage;
import damas.common.messages.JugadaMessage;
import damas.common.messages.TablasMessage;
import damas.common.messages.server.FinJuegoMessage;
import damas.common.messages.server.NewGameMessage;
import damas.common.messages.server.StartGameMessage;
import damas.common.model.DamasRoom;
import damas.common.model.Tablero;

public class DamasServerRoom extends TwoPlayersServerRoom {

    private Tablero tablero;

    private int movidas;

    private int minutos;

    public DamasServerRoom(DamasSaloon saloon, IoSession session, int puntos,
            int minutos) {
        super(saloon, session, puntos);

        this.minutos = minutos;

        movidas = 0;
        tablero = new Tablero();
        tablero.init();

        setEnJuego(true);
    }

    @Override
    public AbstractRoom createRoom() {
        return new DamasRoom(getId(), puntosApostados, minutos, getUsers());
    }

    @Override
    public void startNuevoJuego() {
        movidas = 0;
        tablero.init();

        player1.write(new NewGameMessage());
        player2.write(new NewGameMessage());

        setEnJuego(true);

        startGame();
    }

    @Override
    public void startGame() {
        player1.write(new StartGameMessage(true));
        player2.write(new StartGameMessage(false));
    }

    @Override
    public boolean isGameOn() {
        return movidas >= 2;
    }

    public void jugada(IoSession session, int from, int to) {
        tablero.makeMove(from, to);
        movidas++;

        boolean blancas = session == player1;

        JugadaMessage jm = new JugadaMessage(from, to);

        // perdio el otro ?
        if (tablero.perdioPartida(!blancas)) {

            // el que hizo la movida gano
            session.write(new FinJuegoMessage(true));

            IoSession otro = getOtherPlayer(session);

            jm.setGameOver(false);
            otro.write(jm);

            // transfiero puntos
            int newPoints[] = saloon.transferPoints(session, otro,
                    puntosApostados);

            // mando puntos (si siguen conectados)
            if (session != null) {
                session.write(new UpdatedPointsMessage(newPoints[0]));
            }
            if (otro != null) {
                otro.write(new UpdatedPointsMessage(newPoints[1]));
            }

            setEnJuego(false);
        }
        else {
            getOtherPlayer(session).write(jm);
        }
    }

    public void tablas(IoSession session) {
        getOtherPlayer(session).write(new TablasMessage());
    }

    public void aceptaTablas(IoSession session, boolean acepta) {
        if (true) {
            // game over, sin transferencia
            setEnJuego(false);
        }

        // reenvio al otro jugador
        getOtherPlayer(session).write(new AceptaTablasMessage(acepta));
    }
}