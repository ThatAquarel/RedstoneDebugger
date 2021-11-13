package com.aquarel.debugger.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.aquarel.debugger.Main.tick;

//@Mixin(TitleScreen.class)
//public class ExampleMixin {
//	@Inject(at = @At("HEAD"), method = "init()V")
//	private void init(CallbackInfo info) {
//		Main.LOGGER.info("This line is printed by an example mod mixin!");
//	}
//}


@Mixin(MinecraftClient.class)
public class ExampleMixin
{
	@Shadow
	public ClientWorld world;

//	@Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("HEAD"))
//	private void onCloseGame(Screen screen, CallbackInfo ci)
//	{
//		CarpetClient.disconnect();
//	}

	@Inject(at = @At("HEAD"), method = "tick")
	private void onClientTick(CallbackInfo info) {
		tick();
	}
}
