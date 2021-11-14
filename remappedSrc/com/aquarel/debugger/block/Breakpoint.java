package com.aquarel.debugger.block;

import com.aquarel.debugger.gui.GraphState;
import com.aquarel.debugger.gui.GraphStateManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

//TODO: fix deprecation warning on neighborUpdate, scheduledTick and emitsRedstonePower methods
@SuppressWarnings("deprecation")
public class Breakpoint extends Block {
    public static int CHANNEL_COUNT = 16;

    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final IntProperty CHANNEL = IntProperty.of("channel", 0, CHANNEL_COUNT - 1);

    public Breakpoint(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState().with(POWERED, false));
        setDefaultState(getStateManager().getDefaultState().with(CHANNEL, 0));
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(POWERED, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            if (state.get(POWERED) != world.isReceivingRedstonePower(pos)) {
                world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);

                int channel = world.getBlockState(pos).get(CHANNEL);
                boolean powered = world.getBlockState(pos).get(POWERED);
                GraphStateManager.getInstance().updateState(channel, new GraphState(Util.getMeasuringTimeMs(), powered ? 1 : 0));
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        state = state.cycle(CHANNEL);
        world.setBlockState(pos, state, 2);

        return ActionResult.CONSUME;
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(POWERED);
        stateManager.add(CHANNEL);
    }
}
