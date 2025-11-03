package com.signcology.surfacelevel.item;

import com.signcology.surfacelevel.SurfaceLevel;
import com.signcology.surfacelevel.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SurfaceLevel.MODID);

    public static final RegistryObject<CreativeModeTab> SURFACELEVEL = CREATIVE_MODE_TABS.register("surfacelevel_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.HARDSTONE.get()))
                    .title(Component.translatable("creativetab.surfacelevel.all"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.HARDSTONE.get());
                        output.accept(ModBlocks.HARDSLATE.get());
                        output.accept(ModBlocks.HARDRACK.get());
                        output.accept(ModItems.CHISEL.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
