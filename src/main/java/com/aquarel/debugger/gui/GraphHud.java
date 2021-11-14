package com.aquarel.debugger.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.AffineTransformation;
import net.minecraft.util.math.Matrix4f;

import java.util.ArrayList;

import static com.aquarel.debugger.gui.Colors.PALETTE;

public class GraphHud extends DrawableHelper {
    private final MinecraftClient client;

    private static final String LOCATION = "graph";

    private static boolean GRAPH_ENABLED = true;
    private static int GRAPH_ENABLED_CHANNELS = 4;

    private static final int CHANNEL_HEIGHT = 30;

    public static void setGraphEnabled(boolean graphEnabled_) {
        GRAPH_ENABLED = graphEnabled_;
    }

    public static boolean getGraphEnabled() {
        return GRAPH_ENABLED;
    }

    public static void setGraphEnabledChannels(int graphEnabledChannels_) {
        GRAPH_ENABLED_CHANNELS = Math.max(1, Math.min(16, graphEnabledChannels_));
    }

    public static int getGraphEnabledChannels() {
        return GRAPH_ENABLED_CHANNELS;
    }

    public GraphHud(MinecraftClient client) {
        this.client = client;
    }

    public void render(MatrixStack matrices) {
        this.client.getProfiler().push(LOCATION);

        int window_width = this.client.getWindow().getScaledWidth();
        this.drawGraphs(matrices, window_width / 2);

        this.client.getProfiler().pop();
    }

    private void drawGraphs(MatrixStack matrices, int width) {
        RenderSystem.disableDepthTest();

        int height = GRAPH_ENABLED_CHANNELS * CHANNEL_HEIGHT;

        fill(matrices, 0, 0, width, height, Colors.BACKGROUND_COLOR);
        for (int i = 1; i <= GRAPH_ENABLED_CHANNELS; i++) {
            this.drawHorizontalLine(matrices, 0, width - 1, i * CHANNEL_HEIGHT, Colors.ACCENT_MAIN);
        }

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        Matrix4f matrix4f = AffineTransformation.identity().getMatrix();

        GraphStateManager graphStateManager = GraphStateManager.getInstance();

        float tick_width = width / 2000f;

        for (int i = 0; i < GRAPH_ENABLED_CHANNELS; i++) {
            int y_offset = i * CHANNEL_HEIGHT;

            ArrayList<GraphState> graphStates = graphStateManager.getGraph(i);
            if (graphStates.size() == 0) {
                continue;
            }

            for (int j = 0; (j + 1) < graphStates.size(); j++) {
                GraphState current_state = graphStates.get(j);
                GraphState previous_state = graphStates.get(j + 1);
                int power = previous_state.power;

                int x1 = (int) ((Util.getMeasuringTimeMs() - current_state.time_ms - 1) * tick_width);
                if (x1 > width) {
                    break;
                }
                int x2 = (int) (x1 + (current_state.time_ms - previous_state.time_ms) * tick_width);
                x2 = Math.min(width, x2);

                int y1 = 0, y2 = 0;
                if (power == 0) {
                    y1 = y_offset + CHANNEL_HEIGHT - 7;
                    y2 = y_offset + CHANNEL_HEIGHT - 5;
                } else if (power == 1) {
                    y1 = y_offset + 5;
                    y2 = y_offset + 7;
                }

                int[] rgb = PALETTE[i];
                if (previous_state.power != current_state.power) {
                    drawRectangle(bufferBuilder, matrix4f, x2, y_offset + 5, Math.min(width, x2 + 2), y_offset + CHANNEL_HEIGHT - 5, rgb);
                }
                drawRectangle(bufferBuilder, matrix4f, x1, y1, x2, y2, rgb);
            }
        }

        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    private void drawRectangle(BufferBuilder bufferBuilder, Matrix4f matrix4f, int x1, int y1, int x2, int y2, int[] rgb) {
        bufferBuilder.vertex(matrix4f, x1, y1, 0.0F).color(rgb[0], rgb[1], rgb[2], 255).next();
        bufferBuilder.vertex(matrix4f, x1, y2, 0.0F).color(rgb[0], rgb[1], rgb[2], 255).next();
        bufferBuilder.vertex(matrix4f, x2, y2, 0.0F).color(rgb[0], rgb[1], rgb[2], 255).next();
        bufferBuilder.vertex(matrix4f, x2, y1, 0.0F).color(rgb[0], rgb[1], rgb[2], 255).next();
    }
}
