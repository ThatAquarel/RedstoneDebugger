package com.aquarel.debugger.gui;

import com.aquarel.debugger.block.Breakpoint;

import java.util.ArrayList;

public class GraphStateManager {
    private static final GraphStateManager INSTANCE = new GraphStateManager();

    public static final int BUFFER_SIZE = 20;

    private final ArrayList<ArrayList<GraphState>> graphStates = new ArrayList<>();

    private GraphStateManager() {
        for (int i = 0; i < Breakpoint.CHANNEL_COUNT; i++) {
            graphStates.add(new ArrayList<>(BUFFER_SIZE));
        }
    }

    public void updateState(int id, GraphState new_state) {

        ArrayList<GraphState> current_state = graphStates.get(id);

        ArrayList<Long> current_ticks = new ArrayList<>();
        for (GraphState state_ : current_state) {
            current_ticks.add(state_.time_ms);
        }

        if (current_ticks.contains(new_state.time_ms)) {
            int i = current_ticks.indexOf(new_state.time_ms);
            if (current_state.get(i).power != new_state.power) {
                current_state.set(i, new_state);
            }
            return;
        }

        for (int removable = current_state.size() - BUFFER_SIZE + 1; removable > 0; removable--) {
            current_state.remove(current_state.size() - 1);
        }

        current_state.add(0, new_state);
    }

    public ArrayList<GraphState> getGraph(int id) {
        return graphStates.get(id);
    }

    public static GraphStateManager getInstance() {
        return INSTANCE;
    }
}
