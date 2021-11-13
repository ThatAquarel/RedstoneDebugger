package com.aquarel.debugger.mixin;

import com.aquarel.debugger.gui.GraphHud;
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

    MinecraftClient client;

    //TODO: inject into the constructor instead of this method
    @Inject(at = @At("HEAD"), method = "setDefaultTitleFade")
    public void setDefaultTitleFade(CallbackInfo ci) {
        graphHud = new GraphHud(this.client);
    }

    @Inject(at = @At("HEAD"), method = "render")
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (GraphHud.getGraphEnabled()) {
            graphHud.render(matrices);
        }
    }
}
