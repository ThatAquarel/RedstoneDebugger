package com.aquarel.debugger.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class GraphHud extends DrawableHelper {
    public static boolean graphEnabled = true;
    private final MinecraftClient client;
    private final TextRenderer textRenderer;

    public static void setGraphEnabled(boolean graphEnabled_) {
        graphEnabled = graphEnabled_;
    }

    public static boolean getGraphEnabled() {
        return graphEnabled;
    }

    public GraphHud(MinecraftClient client) {
        this.client = client;
        this.textRenderer = client.textRenderer;
    }

    public void render(MatrixStack matrices) {
        this.client.getProfiler().push("graph");

        this.textRenderer.draw(matrices, "test", 2.0F, 2.0F, 14737632);

        this.client.getProfiler().pop();
    }
}
