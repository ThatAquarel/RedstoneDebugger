package com.aquarel.redstone_debugger.mixin;

import com.aquarel.redstone_debugger.gui.GraphHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(InGameHud.class)
public class InGameHudMixin {
    private GraphHud graphHud;

    //TODO: inject into the constructor instead of this method
    @Inject(at = @At("HEAD"), method = "setDefaultTitleFade")
    public void setDefaultTitleFade(CallbackInfo ci) {
        graphHud = new GraphHud(MinecraftClient.getInstance());
    }

    @Inject(at = @At("HEAD"), method = "render")
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (GraphHud.getGraphEnabled()) {
            graphHud.render(matrices);
        }
    }
}
