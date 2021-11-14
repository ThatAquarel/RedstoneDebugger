package com.aquarel.debugger.gui;

import com.aquarel.debugger.block.Breakpoint;

import java.util.ArrayList;

public class GraphStateManager {
    private static final GraphStateManager INSTANCE = new GraphStateManager();

    public static final int BUFFER_SIZE = 20;
    private static final int RANGE_MS = 10;

    private final ArrayList<ArrayList<GraphState>> graphStates = new ArrayList<>();

    private GraphStateManager() {
        for (int i = 0; i < Breakpoint.CHANNEL_COUNT; i++) {
            graphStates.add(new ArrayList<>(BUFFER_SIZE));
        }
    }

    public void updateState(int id, GraphState new_state) {
        ArrayList<GraphState> current_state = graphStates.get(id);

        if (current_state.get(0).power == new_state.power) {
            return;
        }

//        for (GraphState state : current_state) {
//            if ((state.time_ms - RANGE_MS) > new_state.time_ms
//                    || new_state.time_ms > (state.time_ms + RANGE_MS)) {
//                continue;
//            }
//
//            if (state.power != new_state.power) {
//                current_state.set(current_state.indexOf(state), new_state);
//            }
//            return;
//        }

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
