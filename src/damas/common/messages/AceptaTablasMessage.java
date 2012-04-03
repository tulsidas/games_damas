package damas.common.messages;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;

import common.messages.TaringaProtocolEncoder;

import damas.common.ifaz.GameHandler;
import damas.common.ifaz.GameMessage;
import damas.common.ifaz.SaloonHandler;

public class AceptaTablasMessage extends DamasClientGameMessage implements
      GameMessage {

   private boolean acepta;

   public AceptaTablasMessage() {
   }

   public AceptaTablasMessage(boolean acepta) {
      this.acepta = acepta;
   }

   @Override
   public void execute(IoSession session, SaloonHandler salon) {
      salon.aceptaTablas(session, acepta);
   }

   @Override
   public String toString() {
      return "AceptaTablas: " + acepta;
   }

   public void execute(GameHandler game) {
      game.aceptaTablas(acepta);
   }

   @Override
   public int getContentLength() {
      return 1;
   }

   @Override
   protected void encodeContent(ByteBuffer buff) {
      buff.put(acepta ? TaringaProtocolEncoder.TRUE
            : TaringaProtocolEncoder.FALSE);
   }

   @Override
   public void decode(ByteBuffer buff) {
      acepta = buff.get() == TaringaProtocolEncoder.TRUE;
   }

   @Override
   public byte getMessageId() {
      return (byte) 0x81;
   }
}