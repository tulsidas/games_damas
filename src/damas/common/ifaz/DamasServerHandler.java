package damas.common.ifaz;

import org.apache.mina.common.IoSession;

import common.ifaz.BasicServerHandler;

public interface DamasServerHandler extends BasicServerHandler {
    void createRoom(IoSession session, int puntos, int minutos);
}