package damas.common.messages;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;

import common.messages.TaringaProtocolEncoder;

import damas.common.ifaz.GameHandler;
import damas.common.ifaz.GameMessage;
import damas.common.ifaz.SaloonHandler;

public class JugadaMessage extends DamasClientGameMessage implements
      GameMessage {

   private int from, to;

   private Boolean gameOver;

   public JugadaMessage() {
   }

   public JugadaMessage(int from, int to) {
      this.from = from;
      this.to = to;
   }

   @Override
   public void execute(IoSession session, SaloonHandler salon) {
      salon.jugada(session, from, to);
   }

   @Override
   public String toString() {
      return "MoveMessage: " + from + " -> " + to;
   }

   public void execute(GameHandler game) {
      game.jugada(from, to);

      if (gameOver != null) {
         game.finJuego(gameOver.booleanValue());
      }
   }

   public void setGameOver(Boolean gameOver) {
      this.gameOver = gameOver;
   }

   @Override
   public void decode(ByteBuffer buff) {
      from = buff.get();
      to = buff.get();

      byte gOver = buff.get();
      if (gOver != TaringaProtocolEncoder.NULL) {
         gameOver = gOver == TaringaProtocolEncoder.TRUE;
      }
   }

   @Override
   public int getContentLength() {
      return 3;
   }

   @Override
   protected void encodeContent(ByteBuffer buff) {
      // movida
      buff.put((byte) from);
      buff.put((byte) to);

      if (gameOver != null) {
         buff.put(gameOver ? TaringaProtocolEncoder.TRUE
               : TaringaProtocolEncoder.FALSE);
      }
      else {
         buff.put(TaringaProtocolEncoder.NULL);
      }
   }

   @Override
   public byte getMessageId() {
      return (byte) 0x86;
   }
}
