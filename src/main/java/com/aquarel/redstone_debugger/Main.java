package com.aquarel.redstone_debugger;

import com.aquarel.redstone_debugger.block.Breakpoint;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {
    //    public static final Logger LOGGER = LogManager.getLogger("modid");
    public static final Breakpoint BREAKPOINT = new Breakpoint(AbstractBlock.Settings.of(Material.METAL).strength(0.1f));

    @Override
    public void onInitialize() {
        Identifier identifier = new Identifier("redstone_debugger", "breakpoint");

        Registry.register(Registry.BLOCK, identifier, BREAKPOINT);
        Registry.register(Registry.ITEM, identifier, new BlockItem(BREAKPOINT, new Item.Settings().group(ItemGroup.REDSTONE)));
    }
}
