package com.aquarel.debugger.gui;

import com.aquarel.debugger.util.ColorConverter;

import static com.aquarel.debugger.block.Breakpoint.CHANNEL_COUNT;

public class Colors {
    public static final int BACKGROUND_COLOR = -0x6FAFAFB0; // transparent grey
    public static final int ACCENT_MAIN = -1; // white
    public static final int[][] PALETTE; // colors of each graph

    static {
        PALETTE = new int[CHANNEL_COUNT][3];
        float step = 360f / CHANNEL_COUNT;

        for (int i = 0; i < CHANNEL_COUNT; i++) {
            float hue = i * step;
            float[] rgb = ColorConverter.hslToRgb(hue / 360, 1f, 0.5f);

            for (int j = 0; j < rgb.length; j++) {
                PALETTE[i][j] = (int) (rgb[j] * 255) & 255;
            }
        }
    }
}
