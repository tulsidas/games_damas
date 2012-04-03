package damas.client;

import org.apache.mina.common.IoSession;

import client.AbstractGameConnector;
import damas.common.ifaz.GameHandler;
import damas.common.ifaz.GameMessage;
import damas.common.messages.DamasProtocolDecoder;

public class GameConnector extends AbstractGameConnector implements GameHandler {

    public GameConnector(String host, int port, int salon, String user,
            String pass, long version) {
        super(host, port, salon, user, pass, version,
                new DamasProtocolDecoder());
    }

    @Override
    public void messageReceived(IoSession sess, Object message) {
        super.messageReceived(sess, message);

        if (message instanceof GameMessage && gameHandler != null) {
            ((GameMessage) message).execute(this);
        }
    }

    // /////////////
    // GameHandler
    // /////////////
    public void jugada(int from, int to) {
        if (gameHandler != null) {
            ((GameHandler) gameHandler).jugada(from, to);
        }
    }

    public void tablas() {
        if (gameHandler != null) {
            ((GameHandler) gameHandler).tablas();
        }
    }

    public void aceptaTablas(boolean acepta) {
        if (gameHandler != null) {
            ((GameHandler) gameHandler).aceptaTablas(acepta);
        }
    }
}
