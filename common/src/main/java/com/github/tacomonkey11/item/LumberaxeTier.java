package com.github.tacomonkey11.item;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class LumberaxeTier implements Tier {
    private final Tier base;

    public LumberaxeTier(Tier base) {
        this.base = base;
    }

    @Override
    public int getUses() {
        return base.getUses() * 3;
    }

    @Override
    public float getSpeed() {
        return base.getSpeed();
    }

    @Override
    public float getAttackDamageBonus() {
        return base.getAttackDamageBonus();
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return base.getIncorrectBlocksForDrops();
    }

    @Override
    public int getEnchantmentValue() {
        return base.getEnchantmentValue();
    }

    @Override
    public Ingredient getRepairIngredient() {
        return base.getRepairIngredient();
    }
}
