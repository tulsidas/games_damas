package damas.common.messages;

import common.messages.TaringaProtocolDecoder;

import damas.common.messages.client.CreateDamasRoomMessage;
import damas.common.messages.server.FinJuegoMessage;
import damas.common.messages.server.NewGameMessage;
import damas.common.messages.server.StartGameMessage;

public class DamasProtocolDecoder extends TaringaProtocolDecoder {
    public DamasProtocolDecoder() {
        classes.put(new CreateDamasRoomMessage().getMessageId(),
                CreateDamasRoomMessage.class);
        classes.put(new AceptaTablasMessage().getMessageId(),
                AceptaTablasMessage.class);
        classes
                .put(new FinJuegoMessage().getMessageId(),
                        FinJuegoMessage.class);
        classes.put(new NewGameMessage().getMessageId(), NewGameMessage.class);
        classes.put(new StartGameMessage().getMessageId(),
                StartGameMessage.class);
        classes.put(new TablasMessage().getMessageId(), TablasMessage.class);
        classes.put(new JugadaMessage().getMessageId(), JugadaMessage.class);
    }
}
