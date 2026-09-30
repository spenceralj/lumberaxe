package com.github.tacomonkey11;

import dev.architectury.event.events.common.TickEvent;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.stream.Collectors;

public class TreeObliterator {
    public void obliterateTree(BlockPos pos, Level level, Player player) {
        ItemStack axe = player.getMainHandItem();
        Queue<BlockPos> toCheck = new ArrayDeque<>();
        Set<BlockPos> toBreak = new ObjectOpenHashSet<>();

        toCheck.add(pos);

        while (!toCheck.isEmpty()) {
            BlockPos check = toCheck.remove();
            toBreak.add(check);

            for (BlockPos nextPos : BlockPos.withinManhattan(check, 1, 1 , 1)) {
                if (!toBreak.contains(nextPos)) {
                    if (level.getBlockState(nextPos).is(BlockTags.LOGS)) {
                        if (!toCheck.contains(nextPos)) {
                            BlockPos immutablePos = nextPos.immutable();
                            toCheck.add(immutablePos);
                        }
                    }
                }
            }
        }

        LinkedList<BlockPos> orderedList = toBreak.stream().sorted(Comparator.comparingInt(blockPos -> blockPos.getY() - pos.getY())).collect(Collectors.toCollection(LinkedList::new));

        TickEvent.SERVER_LEVEL_PRE.register(server -> {
            if (!orderedList.isEmpty()) {
                BlockPos logPosition = orderedList.remove();
                BlockState logState = level.getBlockState(logPosition);
                if (!player.getMainHandItem().equals(axe)) {
                    orderedList.clear();
                }
                level.destroyBlock(logPosition, true, player);
                axe.mineBlock(level, logState, logPosition, player);
            }
        });

    }

}
