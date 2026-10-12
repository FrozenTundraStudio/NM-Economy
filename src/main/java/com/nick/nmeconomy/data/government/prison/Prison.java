package com.nick.nmeconomy.data.government.prison;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nick.nmeconomy.NMEconomy;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import java.util.*;

public class Prison extends SavedData {
    public String prisonName = "";
    public List<Cell> cells = new ArrayList<>();;
    public List<Inmate> inmates = new ArrayList<>();

    public Prison() {}

    public Prison(String prisonName, List<Cell> cells, List<Inmate> inmates) {
        this.prisonName = prisonName;
        this.cells = cells;
        this.inmates = inmates;
    }

    public String getPrisonName() {
        return this.prisonName;
    }
    public void setPrisonName(String prisonName) {
        this.prisonName = prisonName;
    }

    public List<Cell> getCells() {
        return this.cells;
    }
    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public List<Inmate> getInmates() {
        return this.inmates;
    }
    public void addInmate(Inmate inmate) {
        inmates.add(inmate);
    }

    public static final Codec<Prison> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("prisonName").forGetter(Prison::getPrisonName),
            Cell.CODEC.listOf().fieldOf("cells").forGetter(Prison::getCells),
            Inmate.CODEC.listOf().fieldOf("inmates").forGetter(Prison::getInmates)
    ).apply(instance, Prison::new));

    private static final SavedDataType<Prison> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(NMEconomy.MOD_ID, "prison"),
            Prison::new,
            CODEC,
            null
    );

    public static Prison getPrison(MinecraftServer server) {
        return server.getDataStorage().computeIfAbsent(TYPE);
    }

    public static void registerPrison() {
        NMEconomy.LOGGER.info("Registering prison for " + NMEconomy.MOD_ID);
    }
}
