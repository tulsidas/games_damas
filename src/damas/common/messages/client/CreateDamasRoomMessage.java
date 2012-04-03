package damas.common.messages.client;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;

import common.ifaz.BasicClientGameMessage;
import common.ifaz.BasicServerHandler;
import common.messages.client.CreateRoomMessage;

import damas.common.ifaz.DamasServerHandler;

/**
 * Pedido de un usuario de crear una sala
 */
public class CreateDamasRoomMessage extends CreateRoomMessage implements
      BasicClientGameMessage {

   private int tiempo;

   public CreateDamasRoomMessage() {
   }

   public CreateDamasRoomMessage(int puntos, int tiempo) {
      super(puntos);
      this.tiempo = tiempo;
   }

   @Override
   public void execute(IoSession session, BasicServerHandler server) {
      ((DamasServerHandler) server).createRoom(session, puntos, tiempo);
   }

   @Override
   public String toString() {
      return "Create Room (" + puntos + " pts), " + tiempo + "'";
   }

   @Override
   public int getContentLength() {
      return super.getContentLength() + 2;
   }

   @Override
   public void decode(ByteBuffer buff) {
      super.decode(buff);
      tiempo = buff.getShort();
   }

   @Override
   protected void encodeContent(ByteBuffer buff) {
      super.encodeContent(buff);

      buff.putShort((short) tiempo);
   }

   @Override
   public byte getMessageId() {
      return (byte) 0x80;
   }
}
