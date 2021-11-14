package com.aquarel.debugger;

import com.aquarel.debugger.block.Breakpoint;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("redstone_debugger");
    public static final Breakpoint BREAKPOINT = new Breakpoint(AbstractBlock.Settings.of(Material.METAL).strength(0.1f));

//    public static int GAME_TICK = 0;

    @Override
    public void onInitialize() {
        Identifier identifier = new Identifier("debug", "breakpoint");

        Registry.register(Registry.BLOCK, identifier, BREAKPOINT);
//        Registry.register(Registry.ITEM, identifier, new BlockItem(BREAKPOINT, new FabricItemSettings().group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, identifier, new BlockItem(BREAKPOINT, new Item.Settings().group(ItemGroup.REDSTONE)));
    }

    public static void tick() {
//        GAME_TICK++;

//        ArrayList<GraphState> states = new ArrayList<>(GraphStateManager.getInstance().getGraph(0));
//        if (states.size() > 0) {
//            for (GraphState state : states) {
//                System.out.print(state.time_ms + ":" + state.power + " ");
//            }
//            System.out.println();
//        }
    }
}
