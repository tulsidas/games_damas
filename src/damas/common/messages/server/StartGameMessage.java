package damas.common.messages.server;

import org.apache.mina.common.ByteBuffer;

import common.messages.FixedLengthMessageAdapter;
import common.messages.TaringaProtocolEncoder;
import damas.common.ifaz.GameHandler;
import damas.common.ifaz.GameMessage;

public class StartGameMessage extends FixedLengthMessageAdapter implements
      GameMessage {

   private boolean start;

   public StartGameMessage() {
   }

   public StartGameMessage(boolean start) {
      this.start = start;
   }

   public void execute(GameHandler game) {
      game.startGame(start);
   }

   @Override
   public int getContentLength() {
      return 1;
   }

   @Override
   protected void encodeContent(ByteBuffer buff) {
      buff.put(start ? TaringaProtocolEncoder.TRUE
            : TaringaProtocolEncoder.FALSE);
   }

   @Override
   public void decode(ByteBuffer buff) {
      start = buff.get() == TaringaProtocolEncoder.TRUE;
   }

   @Override
   public byte getMessageId() {
      return (byte) 0x84;
   }
}