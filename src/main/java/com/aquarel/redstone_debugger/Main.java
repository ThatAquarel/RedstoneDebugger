package com.aquarel.redstone_debugger;

import com.aquarel.redstone_debugger.block.Breakpoint;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {
    //    public static final Logger LOGGER = LogManager.getLogger("modid");
    public static final Breakpoint BREAKPOINT = new Breakpoint(FabricBlockSettings.of(Material.METAL).strength(0.1f));

    @Override
    public void onInitialize() {
        Identifier identifier = new Identifier("redstone_debugger", "breakpoint");

        Registry.register(Registry.BLOCK, identifier, BREAKPOINT);
        Registry.register(Registry.ITEM, identifier, new BlockItem(BREAKPOINT, new FabricItemSettings().group(ItemGroup.REDSTONE)));
    }
}
