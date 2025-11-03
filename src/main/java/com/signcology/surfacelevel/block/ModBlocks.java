package com.signcology.surfacelevel.block;

import com.signcology.surfacelevel.SurfaceLevel;
import com.signcology.surfacelevel.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, SurfaceLevel.MODID);


    public static final RegistryObject<Block> HARDSTONE = registerBlock("hardstone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(60f, 0.0f)
                    .sound(SoundType.STONE)));
    public static final RegistryObject<Block> HARDSLATE = registerBlock("hardslate",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(60f, 0.0f)
                    .sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> HARDRACK = registerBlock("hardrack",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.NETHER)
                    .strength(60f, 0.0f)
                    .sound(SoundType.NETHERRACK)));
    public static final RegistryObject<Block> TEMPSTONE = registerBlock("tempstone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(60f, 0.0f)
                    .sound(SoundType.STONE)));
    public static final RegistryObject<Block> TEMPSLATE = registerBlock("tempslate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(60f, 0.0f)
                    .sound(SoundType.STONE)));
    public static final RegistryObject<Block> TEMPRACK = registerBlock("temprack",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(60f, 0.0f)
                    .sound(SoundType.STONE)));

    //----------------------------------------------------------------------------------------------------//
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
