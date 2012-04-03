package damas.common.model;

import java.util.List;

import org.apache.mina.common.ByteBuffer;

import common.model.TwoPlayerRoom;
import common.model.User;

public class DamasRoom extends TwoPlayerRoom {

    private int minutos;

    public DamasRoom() {
    }

    public DamasRoom(int id, int puntosApostados, int minutos,
            List<User> players) {
        super(id, puntosApostados, players);
        this.minutos = minutos;
    }

    public int getMinutos() {
        return minutos;
    }

    @Override
    public String getRoomInfo() {
        return ", " + minutos + "')";
    }

    @Override
    public ByteBuffer encode() {
        ByteBuffer parent = super.encode();

        ByteBuffer ret = ByteBuffer.allocate(parent.remaining() + 4);
        ret.put(parent);
        ret.putInt(minutos);

        return ret.flip();
    }

    @Override
    public void decode(ByteBuffer buff) {
        super.decode(buff);

        // agrego minutos
        minutos = buff.getInt();
    }
}