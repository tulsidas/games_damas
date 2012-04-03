package damas.common.messages;

import org.apache.mina.common.IoSession;

import damas.common.ifaz.GameHandler;
import damas.common.ifaz.GameMessage;
import damas.common.ifaz.SaloonHandler;

public class TablasMessage extends DamasClientGameMessage implements
      GameMessage {

   @Override
   public void execute(IoSession session, SaloonHandler salon) {
      salon.tablas(session);
   }

   @Override
   public String toString() {
      return "Tablas";
   }

   public void execute(GameHandler game) {
      game.tablas();
   }

   @Override
   public int getContentLength() {
      return 0;
   }

   @Override
   public byte getMessageId() {
      return (byte) 0x85;
   }
}
