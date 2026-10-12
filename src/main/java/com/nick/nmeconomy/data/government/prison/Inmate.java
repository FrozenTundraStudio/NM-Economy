package com.nick.nmeconomy.data.government.prison;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nick.nmeconomy.NMEconomy;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.apache.logging.log4j.core.util.UuidUtil;

import java.util.List;
import java.util.UUID;

public class Inmate extends SavedData {
    public String name;
    public UUID uuid;
    public Cell cell;

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

    public Cell getCell() {
        return this.cell;
    }
    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public static final Codec<Inmate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(Inmate::getName),
            UUIDUtil.CODEC.fieldOf("uuid").forGetter(Inmate::getUuid)
//            Cell.CODEC.fieldOf("cell").forGetter(Inmate::getCell)
    ).apply(instance, Inmate::new));
}
