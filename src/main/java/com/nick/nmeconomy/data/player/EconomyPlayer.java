package com.nick.nmeconomy.data.player;

import com.mojang.serialization.Codec;
import com.nick.nmeconomy.NMEconomy;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;

public class EconomyPlayer {

    public static final AttachmentType<Integer> BALANCE = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(NMEconomy.MOD_ID, "balance"),
            builder -> builder
                    .initializer(() -> 0)
                    .syncWith(ByteBufCodecs.INT, AttachmentSyncPredicate.targetOnly())
                    .persistent(Codec.INT)
                    .copyOnDeath()
    );

    public static EconomyPlayerData get(AttachmentTarget target) {
        return new EconomyPlayerData(target);
    }

    public record EconomyPlayerData(AttachmentTarget target) {
        public int getBalance() {
            return this.target.getAttachedOrElse(BALANCE, 0);
        }

        public void setBalance(int balance) {
            this.target.setAttached(BALANCE, balance);
        }
    }

    public static void registerEconomyPlayer() {
        NMEconomy.LOGGER.info("Registering Economy Players for " + NMEconomy.MOD_ID);
    }
}
