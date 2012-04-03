package damas.server;

import server.ServerSessionHandler;

import com.google.common.collect.Lists;

import damas.common.messages.DamasProtocolDecoder;

public class DamasSessionHandler extends ServerSessionHandler {

    public DamasSessionHandler() {
        super(new DamasProtocolDecoder());

        salones = Lists.newArrayList();
        salones.add(new DamasSaloon(0, this));
        salones.add(new DamasSaloon(1, this));
        salones.add(new DamasSaloon(2, this));
    }

    @Override
    protected int getCodigoJuego() {
        // damas = 5 para la base
        return 5;
    }
}
