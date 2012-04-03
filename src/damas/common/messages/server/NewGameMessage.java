package damas.common.messages.server;

import common.messages.FixedLengthMessageAdapter;

import damas.common.ifaz.GameHandler;
import damas.common.ifaz.GameMessage;

public class NewGameMessage extends FixedLengthMessageAdapter implements
      GameMessage {

   public void execute(GameHandler game) {
      game.newGame();
   }

   @Override
   public byte getMessageId() {
      return (byte) 0x83;
   }

   @Override
   public int getContentLength() {
      return 0;
   }
}
