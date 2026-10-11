package com.nick.nmeconomy.network.tablet;

import com.nick.nmeconomy.NMEconomy;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ServerboundEmployeePayload(String employeeName, String position, String jobType) implements CustomPacketPayload {
    public static final Identifier EMPLOYEE_PAYLOAD_ID = Identifier.fromNamespaceAndPath(NMEconomy.MOD_ID, "employee");
    public static final Type<ServerboundEmployeePayload> TYPE = new Type<>(EMPLOYEE_PAYLOAD_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundEmployeePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, ServerboundEmployeePayload::employeeName,
            ByteBufCodecs.STRING_UTF8, ServerboundEmployeePayload::position,
            ByteBufCodecs.STRING_UTF8, ServerboundEmployeePayload::jobType,
            ServerboundEmployeePayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
