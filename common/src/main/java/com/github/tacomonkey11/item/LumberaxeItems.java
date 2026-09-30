package com.github.tacomonkey11.item;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;

import static com.github.tacomonkey11.Lumberaxe.MOD_ID;

public class LumberaxeItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> IRON_LUMBERAXE = register("iron_lumberaxe", Tiers.IRON);
    public static final RegistrySupplier<Item> GOLD_LUMBERAXE = register("gold_lumberaxe", Tiers.GOLD);
    public static final RegistrySupplier<Item> DIAMOND_LUMBERAXE = register("diamond_lumberaxe", Tiers.DIAMOND);
    public static final RegistrySupplier<Item> NETHERITE_LUMBERAXE = register("netherite_lumberaxe", Tiers.NETHERITE);

    private static RegistrySupplier<Item> register(String id, Tier material) {
        return ITEMS.register(id, () -> new LumberaxeItem(material, new Item.Properties().arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES)));
    }

    public static void register() {
        ITEMS.register();
    }
}
