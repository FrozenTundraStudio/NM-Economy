package com.nick.nmeconomy.data.government.prison;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.saveddata.SavedData;

public class Cell extends SavedData {
    public int cellNumber;
    public BlockPos cellPosition;
    public boolean occupied;
    public Inmate inmate;

    public Cell() {}

    public Cell(int cellNumber, BlockPos cellPosition, boolean occupied, Inmate inmate) {
        this.cellNumber = cellNumber;
        this.cellPosition = cellPosition;
        this.occupied = occupied;
        this.inmate = inmate;
    }

    public int getCellNumber() {
        return this.cellNumber;
    }
    public void setCellNumber(int cellNumber) {
        this.cellNumber = cellNumber;
    }

    public BlockPos getCellPosition() {
        return this.cellPosition;
    }
    public void setCellPosition(BlockPos cellPosition) {
        this.cellPosition = cellPosition;
    }

    public boolean getOccupiedStatus() {
        return this.occupied;
    }
    public void setOccupiedStatus(boolean occupied) {
        this.occupied = occupied;
    }

    public Inmate getInmate() {
        return this.inmate;
    }
    public void setInmate(Inmate inmate) {
        this.inmate = inmate;
    }

    public static final Codec<Cell> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("cell_number").forGetter(Cell::getCellNumber),
            BlockPos.CODEC.fieldOf("cell_position").forGetter(Cell::getCellPosition),
            Codec.BOOL.fieldOf("occupied").forGetter(Cell::getOccupiedStatus),
            Inmate.CODEC.fieldOf("inmate").forGetter(Cell::getInmate)
    ).apply(instance, Cell::new));
}
