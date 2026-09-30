package com.github.tacomonkey11;

import dev.architectury.event.events.common.TickEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;
import java.util.stream.Collectors;

public class TreeObliterator {
    public void obliterateTree(BlockPos pos, Level level, Player player, ItemStack axe) {
        Queue<BlockPos> toBreak = new ArrayDeque<>();

        findLogs(pos, level, toBreak, axe.getMaxDamage() - axe.getDamageValue());

        Queue<BlockPos> sortedQueue = toBreak.stream().sorted(Comparator.comparingInt(Vec3i::getY)).collect(Collectors.toCollection(ArrayDeque::new));

        axe.hurtAndBreak(toBreak.size(), player, player.getEquipmentSlotForItem(axe));

        TickEvent.SERVER_LEVEL_PRE.register(server -> {
            if (server.getGameTime() % 2 == 1) return;
            if (sortedQueue.isEmpty()) return;

            BlockPos curLog = sortedQueue.poll();

            level.destroyBlock(curLog, true, player);
            player.awardStat(Stats.BLOCK_MINED.get(level.getBlockState(curLog).getBlock()));
            player.causeFoodExhaustion(0.05F);
        });
    }

    public void findLogs(BlockPos pos, Level level, Queue<BlockPos> toBreak, int allowedDurability) {
        if (toBreak.size() >= allowedDurability) return;
        if (!level.getBlockState(pos).is(BlockTags.LOGS)) return;
        if (toBreak.contains(pos)) return;

        toBreak.add(pos.immutable());

        for (BlockPos newPos : BlockPos.withinManhattan(pos, 1, 1, 1)) {
            findLogs(newPos, level, toBreak, allowedDurability);
        }
    }
}
