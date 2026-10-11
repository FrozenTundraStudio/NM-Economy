package com.nick.nmeconomy.network.atm;

import com.nick.nmeconomy.NMEconomy;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ServerboundDepositPayload(int amount) implements CustomPacketPayload {
    public static final Identifier DEPOSIT_PAYLOAD_ID = Identifier.fromNamespaceAndPath(NMEconomy.MOD_ID, "deposit");
    public static final CustomPacketPayload.Type<ServerboundDepositPayload> TYPE = new CustomPacketPayload.Type<>(DEPOSIT_PAYLOAD_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundDepositPayload> CODEC = StreamCodec.composite(ByteBufCodecs.INT, ServerboundDepositPayload::amount, ServerboundDepositPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
