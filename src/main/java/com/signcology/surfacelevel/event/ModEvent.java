package com.signcology.surfacelevel.event;

import com.signcology.surfacelevel.Config;
import com.signcology.surfacelevel.SurfaceLevel;
import com.signcology.surfacelevel.block.ModBlocks;
import com.signcology.surfacelevel.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = SurfaceLevel.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvent {

    private static boolean canBeSeen(Level level, BlockPos pPos) {
        for(int x = -Config.airSearchDistance; x <= Config.airSearchDistance; x++) {
            for(int y = -Config.airSearchDistance; y <= Config.airSearchDistance; y++) {
                for(int z = -Config.airSearchDistance; z <= Config.airSearchDistance; z++) {
                    BlockPos pos = new BlockPos(pPos.getX() + x, pPos.getY() + y, pPos.getZ() + z);
                    if (!level.getBlockState(pos).is(Blocks.STONE) &&
                            !level.getBlockState(pos).is(Blocks.DEEPSLATE) &&
                            !level.getBlockState(pos).is(Blocks.NETHERRACK) &&
                            !level.getBlockState(pos).is(ModBlocks.HARDSTONE.get()) &&
                            !level.getBlockState(pos).is(ModBlocks.HARDSLATE.get()) &&
                            !level.getBlockState(pos).is(ModBlocks.HARDRACK.get()) ) {
                        //!level.getBlockState(pos).canOcclude()
                        return true;
                    }

                    /* TAGS NOT WORKING FOR SOME REASON ??????
                    if (!level.getBlockState(pos).is(ModTags.Blocks.STONE_BLOCK)) {
                        //!level.getBlockState(pos).canOcclude()
                        return true;
                    }
                    */
                }
            }
        }
        return false;
    }

    private static void generateHardBlock(Level level,BlockState block, BlockPos pos) {
        System.out.println(canBeSeen(level, pos));
        if(block.getBlock() == Blocks.STONE && !canBeSeen(level, pos)) {
            level.setBlockAndUpdate(pos, ModBlocks.HARDSTONE.get().defaultBlockState());
        } else if(block.getBlock() == Blocks.DEEPSLATE && !canBeSeen(level, pos)) {
            level.setBlockAndUpdate(pos, ModBlocks.HARDSLATE.get().defaultBlockState());
        } else if(block.getBlock() == Blocks.NETHERRACK && !canBeSeen(level, pos)) {
            level.setBlockAndUpdate(pos, ModBlocks.HARDRACK.get().defaultBlockState());
        }
    }
    private static void generateTempBlock(Level level,BlockState block, BlockPos pos) {
        if(block.getBlock() == Blocks.STONE) {
            level.setBlockAndUpdate(pos, ModBlocks.TEMPSTONE.get().defaultBlockState());
        } else if(block.getBlock() == Blocks.DEEPSLATE) {
            level.setBlockAndUpdate(pos, ModBlocks.TEMPSLATE.get().defaultBlockState());
        } else if(block.getBlock() == Blocks.NETHERRACK) {
            level.setBlockAndUpdate(pos, ModBlocks.TEMPRACK.get().defaultBlockState());
        }
    }

    private static void degenerateTempBlock(Level level,BlockState block, BlockPos pos) {
        if(block.getBlock() == ModBlocks.TEMPSTONE.get()) {
            level.setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
        } else if(block.getBlock() == ModBlocks.TEMPSLATE.get()) {
            level.setBlockAndUpdate(pos, Blocks.DEEPSLATE.defaultBlockState());
        } else if(block.getBlock() == ModBlocks.TEMPRACK.get()) {
            level.setBlockAndUpdate(pos, Blocks.NETHERRACK.defaultBlockState());
        }
    }

    @SubscribeEvent
    public static void onHardBlockCreate(BlockEvent.BreakEvent event) {
        Level level = event.getPlayer().level();

        for(int x = -Config.updateDistance; x <= Config.updateDistance; x++) {
            for(int y = -Config.updateDistance; y <= Config.updateDistance; y++) {
                for(int z = -Config.updateDistance; z <= Config.updateDistance; z++) {
                    BlockPos pos = new BlockPos(event.getPos().getX() + x, event.getPos().getY() + y, event.getPos().getZ() + z);
                    generateHardBlock(level, level.getBlockState(pos), pos);
                    //System.out.println("Attempt Generation");
                }
            }
        }
    }

    @SubscribeEvent
    public static void onExploisonHardBlockCreate(ExplosionEvent.Start event) {
        Level level = event.getLevel();
        Explosion explosion = event.getExplosion();

        for(int x = -Config.updateExplosionDistance; x <= Config.updateExplosionDistance; x++) {
            for(int y = -Config.updateExplosionDistance; y <= Config.updateExplosionDistance; y++) {
                for(int z = -Config.updateExplosionDistance; z <= Config.updateExplosionDistance; z++) {
                    BlockPos pos = new BlockPos((int) explosion.getPosition().x + x, (int) explosion.getPosition().y + y, (int) explosion.getPosition().z + z);
                    generateTempBlock(level, level.getBlockState(pos), pos);
                }
            }
        }
    }
    @SubscribeEvent
    public static void onExploisonHardBlockCreate(ExplosionEvent.Detonate event) {
        Level level = event.getLevel();
        Explosion explosion = event.getExplosion();

        for(int x = -Config.updateExplosionDistance; x <= Config.updateExplosionDistance; x++) {
            for(int y = -Config.updateExplosionDistance; y <= Config.updateExplosionDistance; y++) {
                for(int z = -Config.updateExplosionDistance; z <= Config.updateExplosionDistance; z++) {
                    BlockPos pos = new BlockPos((int) explosion.getPosition().x + x, (int) explosion.getPosition().y + y, (int) explosion.getPosition().z + z);
                    degenerateTempBlock(level, level.getBlockState(pos), pos);
                }
            }
        }
    }


}
