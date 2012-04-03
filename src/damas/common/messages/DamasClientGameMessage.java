package damas.common.messages;

import org.apache.mina.common.IoSession;

import common.ifaz.BasicServerHandler;
import common.messages.FixedLengthMessageAdapter;

import damas.common.ifaz.ClientGameMessage;
import damas.common.ifaz.SaloonHandler;

public abstract class DamasClientGameMessage extends FixedLengthMessageAdapter
        implements ClientGameMessage {

    public abstract void execute(IoSession session, SaloonHandler salon);

    public void execute(IoSession session, BasicServerHandler serverHandler) {
        execute(session, (SaloonHandler) serverHandler);
    }
}
