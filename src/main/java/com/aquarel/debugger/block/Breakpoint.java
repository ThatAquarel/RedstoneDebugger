package com.aquarel.debugger.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static com.aquarel.debugger.Main.ZMQ_PUBLISHER;
import static com.aquarel.debugger.Main.getTick;

//TODO: fix deprecation warning on neighborUpdate, scheduledTick and emitsRedstonePower methods
@SuppressWarnings("deprecation")
public class Breakpoint extends Block {
    public static int channelCount = 16;

    public static final BooleanProperty POWERED = BooleanProperty.of("powered");
    public static final IntProperty CHANNEL = IntProperty.of("channel", 0, channelCount - 1);

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
            world.setBlockState(pos, state.with(POWERED, world.isReceivingRedstonePower(pos)));
        }

        int channel = world.getBlockState(pos).get(CHANNEL);
        boolean powered = world.getBlockState(pos).get(POWERED);
        ZMQ_PUBLISHER.send(getTick(), channel, powered ? 1 : 0);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(POWERED) && !world.isReceivingRedstonePower(pos)) {
            world.setBlockState(pos, state.cycle(POWERED), 2);
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
