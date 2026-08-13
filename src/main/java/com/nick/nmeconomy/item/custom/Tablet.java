package com.nick.nmeconomy.item.custom;

import com.nick.nmeconomy.render.screen.tablet_screen.TabletScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class Tablet extends Item {
    public Tablet(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if(level.isClientSide()) {
            Minecraft.getInstance().setScreenAndShow(
                    new TabletScreen(Component.empty())
            );
        }
        return InteractionResult.PASS;
    }
}
