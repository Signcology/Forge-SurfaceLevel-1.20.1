package com.signcology.surfacelevel.item.custom;

import com.signcology.surfacelevel.Config;
import com.signcology.surfacelevel.block.ModBlocks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ChiselItem extends Item {
    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    ModBlocks.HARDSTONE.get(), Blocks.STONE,
                    ModBlocks.HARDSLATE.get(), Blocks.DEEPSLATE,
                    ModBlocks.HARDRACK.get(), Blocks.NETHERRACK,
                    Blocks.STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS,
                    Blocks.DEEPSLATE_BRICKS, Blocks.CHISELED_DEEPSLATE,
                    Blocks.SANDSTONE, Blocks.CHISELED_SANDSTONE,
                    Blocks.RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE,
                    Blocks.NETHER_BRICKS, Blocks.CHISELED_NETHER_BRICKS,
                    Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.CHISELED_POLISHED_BLACKSTONE,
                    Blocks.QUARTZ_BLOCK, Blocks.CHISELED_QUARTZ_BLOCK
            );

    public ChiselItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Block clickedBlock = level.getBlockState(pContext.getClickedPos()).getBlock();

        if(CHISEL_MAP.containsKey(clickedBlock) && Config.allowChisel) {
            if(!level.isClientSide()) {
                level.setBlockAndUpdate(pContext.getClickedPos(), CHISEL_MAP.get(clickedBlock).defaultBlockState());

                pContext.getItemInHand().hurtAndBreak(1,
                        ((ServerPlayer) pContext.getPlayer()),
                        item -> {
                            assert pContext.getPlayer() != null;
                            pContext.getPlayer().getItemInHand(InteractionHand.MAIN_HAND);
                        });
                level.playSound(null, pContext.getClickedPos(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.BLOCKS);
            }
            ParticleUtils.spawnParticlesOnBlockFaces(level, pContext.getClickedPos(), ParticleTypes.WAX_OFF, UniformInt.of(3, 5));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        if(Screen.hasShiftDown()) {
            pTooltip.add(Component.translatable("tooltip.surfacelevel.chisel.shift_down"));
        } else {
            pTooltip.add(Component.translatable("tooltip.surfacelevel.chisel.tooltip"));
        }

        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

}
