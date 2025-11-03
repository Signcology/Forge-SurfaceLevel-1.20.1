package com.signcology.surfacelevel.item.custom;

import com.signcology.surfacelevel.Config;
import com.signcology.surfacelevel.block.ModBlocks;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.Map;

public class ChiselItem extends Item {
    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    ModBlocks.HARDSTONE.get(), Blocks.STONE,
                    ModBlocks.HARDSLATE.get(), Blocks.DEEPSLATE,
                    ModBlocks.HARDRACK.get(), Blocks.NETHERRACK
            );

    public ChiselItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
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
        }

        return InteractionResult.SUCCESS;
    }
    /*
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        if(Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.surfacelevel.chisel.shift_down"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.surfacelevel.chisel.tooltip"));
        }

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

     */
}
