package com.aquarel.redstone_debugger.gui;

public class GraphState {
    public long time_ms;
    public int power;

    public GraphState(long time_ms, int power) {
        this.time_ms = time_ms;
        this.power = power;
    }
}
