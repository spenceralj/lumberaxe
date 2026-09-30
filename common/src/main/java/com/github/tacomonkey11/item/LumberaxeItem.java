package com.github.tacomonkey11.item;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

public class LumberaxeItem extends AxeItem {
    public LumberaxeItem(Tier material, Properties properties) {
        super(new LumberaxeTier(material), properties);
    }
}
