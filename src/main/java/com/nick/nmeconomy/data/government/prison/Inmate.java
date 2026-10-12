package com.nick.nmeconomy.data.government.prison;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.level.saveddata.SavedData;
import java.util.UUID;

public class Inmate extends SavedData {
    public String name;
    public UUID uuid;

    public Inmate() {}

    public Inmate(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return this.uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public static final Codec<Inmate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(Inmate::getName),
            UUIDUtil.CODEC.fieldOf("uuid").forGetter(Inmate::getUuid)
    ).apply(instance, Inmate::new));
}
