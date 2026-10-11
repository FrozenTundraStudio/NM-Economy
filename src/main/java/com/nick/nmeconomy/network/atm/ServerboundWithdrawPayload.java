package com.nick.nmeconomy.network.atm;

import com.nick.nmeconomy.NMEconomy;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ServerboundWithdrawPayload(int amount) implements CustomPacketPayload {
    public static final Identifier WITHDRAW_PAYLOAD_ID = Identifier.fromNamespaceAndPath(NMEconomy.MOD_ID, "withdraw");
    public static final Type<ServerboundWithdrawPayload> TYPE = new Type<>(WITHDRAW_PAYLOAD_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundWithdrawPayload> CODEC = StreamCodec.composite(ByteBufCodecs.INT, ServerboundWithdrawPayload::amount, ServerboundWithdrawPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
