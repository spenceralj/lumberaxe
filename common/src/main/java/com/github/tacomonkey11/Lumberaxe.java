package com.github.tacomonkey11;

import com.github.tacomonkey11.item.LumberaxeItem;
import com.github.tacomonkey11.item.LumberaxeItems;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import net.minecraft.tags.BlockTags;


public final class Lumberaxe {
    public static final String MOD_ID = "lumberaxe";

    public static void init() {
        LumberaxeItems.register();

        BlockEvent.BREAK.register((level, pos, state, player, xp) -> {
            if (player.getMainHandItem().is(item -> item.value() instanceof LumberaxeItem) && !player.isCrouching() && state.is(BlockTags.LOGS)) {
                new TreeObliterator().obliterateTree(pos, level, player, player.getMainHandItem());
                return EventResult.interruptFalse();
            }

            return EventResult.pass();
        });
    }
}