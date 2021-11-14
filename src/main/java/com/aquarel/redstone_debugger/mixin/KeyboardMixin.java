package com.aquarel.redstone_debugger.mixin;

import com.aquarel.redstone_debugger.gui.GraphHud;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(at = @At("HEAD"), method = "onKey")
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (window == client.getWindow().getHandle()) {
            if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_F6)) {
                GraphHud.setGraphEnabled(!GraphHud.getGraphEnabled());
            } else if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_F7)) {
                GraphHud.setGraphEnabledChannels(GraphHud.getGraphEnabledChannels() + 1);
            } else if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_F8)) {
                GraphHud.setGraphEnabledChannels(GraphHud.getGraphEnabledChannels() - 1);
            }
        }
    }
}
