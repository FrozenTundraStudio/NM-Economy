package com.nick.nmeconomy.data.government.prison;

import com.nick.nmeconomy.NMEconomy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Prison {
    public String prisonName;
    List<Cell> cells = new ArrayList<>();
    List<Inmate> inmates = new ArrayList<>();
    Map<Inmate, Cell> inmateDetails = new HashMap<>();

    public Prison() {}

    public Prison(String prisonName, List<Cell> cells, List<Inmate> inmates, Map<Inmate, Cell> inmateDetails) {
        this.prisonName = prisonName;
        this.cells = cells;
        this.inmates = inmates;
        this.inmateDetails = inmateDetails;
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

    public Map<Inmate, Cell> getInmateDetails() {
        return this.inmateDetails;
    }
    public void addInmateDetails(Inmate inmate, Cell cell) {
        this.inmateDetails.put(inmate, cell);
    }

    public static void registerPrison() {
        NMEconomy.LOGGER.info("Registering prison for " + NMEconomy.MOD_ID);
    }
}
