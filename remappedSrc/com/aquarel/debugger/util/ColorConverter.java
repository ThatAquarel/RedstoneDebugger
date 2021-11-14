package com.aquarel.debugger.util;


public class ColorConverter {
    private static final float ONE_THIRD = 1 / 3f;
    private static final float ONE_SIXTH = 1 / 6f;
    private static final float TWO_THIRD = 2 / 3f;

    public static float[] hslToRgb(float hue, float saturation, float lightness) {
        if (saturation == 0.0) {
            return new float[]{
                    lightness,
                    lightness,
                    lightness
            };
        }

        float m2;
        if (lightness <= 0.5) {
            m2 = lightness * (1f + saturation);
        } else {
            m2 = lightness + saturation - (lightness * saturation);
        }

        float m1 = 2f * lightness - m2;

        return new float[]{
                _v(m1, m2, hue + ONE_THIRD),
                _v(m1, m2, hue),
                _v(m1, m2, hue - ONE_THIRD)
        };
    }

    private static float _v(float m1, float m2, float hue) {
        if (hue < 0) {
            hue = (1f + hue);
        }
        hue = hue % 1f;

        if (hue < ONE_SIXTH) {
            return m1 + (m2 - m1) * hue * 6f;
        }
        if (hue < 0.5) {
            return m2;
        }
        if (hue < TWO_THIRD) {
            return m1 + (m2 - m1) * (TWO_THIRD - hue) * 6f;
        }

        return m1;
    }
}
