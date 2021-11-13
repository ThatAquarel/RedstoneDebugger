package com.aquarel.debugger.network;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class ZMQPublisher {
    private final ZMQ.Socket socket;
    private final ZContext context;

    public ZMQPublisher() {
        this.context = new ZContext();
        this.socket = context.createSocket(SocketType.PUB);
        this.socket.bind("tcp://*:25595");
//        this.socket.connect("tcp://*:25595");
    }

    public void send(int game_tick, int channel_id, int power) {
        assert this.context != null;
        this.socket.sendMore("mc-debug");
        this.socket.send("{ \"game_tick\": " + game_tick +
                ", \"channel_id\": " + channel_id +
                ", \"power\": " + power + " }");
    }
}