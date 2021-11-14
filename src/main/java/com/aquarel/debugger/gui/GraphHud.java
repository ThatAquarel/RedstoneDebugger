package com.aquarel.debugger.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.AffineTransformation;
import net.minecraft.util.math.Matrix4f;

public class GraphHud extends DrawableHelper {
    private final MinecraftClient client;
//    private final TextRenderer textRenderer;

    private static final int BACKGROUND_COLOR = -0x6FAFAFB0;
    private static final String LOCATION = "graph";

    private static final int X_POS = 0;
    private static final int Y_POS = 0;

    public static boolean graphEnabled = true;

    public static void setGraphEnabled(boolean graphEnabled_) {
        graphEnabled = graphEnabled_;
    }

    public static boolean getGraphEnabled() {
        return graphEnabled;
    }

    public GraphHud(MinecraftClient client) {
        this.client = client;
//        this.textRenderer = client.textRenderer;
    }

    public void render(MatrixStack matrices) {
        this.client.getProfiler().push(LOCATION);

        int window_width = this.client.getWindow().getScaledWidth();
        this.drawGraphs(matrices, window_width / 2);

        this.client.getProfiler().pop();
    }

    private void drawGraphs(MatrixStack matrices, int width) {
        RenderSystem.disableDepthTest();

        // fill whole background
        int window_height = this.client.getWindow().getScaledHeight();
        fill(matrices, 0, window_height - 60, width, window_height, -1873784752);

        // draw lines
        this.drawHorizontalLine(matrices, 0, width - 1, window_height - 30, -1);
        this.drawHorizontalLine(matrices, 0, width - 1, window_height - 60, -1);

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

        Matrix4f matrix4f = AffineTransformation.identity().getMatrix();
        for (int vertex_left_offset = 0; vertex_left_offset < width; vertex_left_offset++) {
            bufferBuilder.vertex(matrix4f, (float) (vertex_left_offset + 1), (float) window_height, 0.0F).color(0, 255, 255, 255).next();
            bufferBuilder.vertex(matrix4f, (float) (vertex_left_offset + 1), (float) (window_height - 60 + 1), 0.0F).color(0, 255, 255, 255).next();
            bufferBuilder.vertex(matrix4f, (float) vertex_left_offset, (float) (window_height - 60 + 1), 0.0F).color(0, 255, 255, 255).next();
            bufferBuilder.vertex(matrix4f, (float) vertex_left_offset, (float) window_height, 0.0F).color(0, 255, 255, 255).next();
        }

        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }


}
