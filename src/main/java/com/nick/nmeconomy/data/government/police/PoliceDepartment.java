package com.nick.nmeconomy.data.government.police;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nick.nmeconomy.NMEconomy;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import java.util.HashMap;
import java.util.Map;

public class PoliceDepartment extends SavedData {
    public String chief;
    public String businessName;
    public double balance;
    Map<String, String> employees = new HashMap<>();
    Map<String, Double> expenses = new HashMap<>();
    Map<String, Double> revenue = new HashMap<>();

    public PoliceDepartment() {}

    public PoliceDepartment(String chief, String businessName, double balance, Map employees, Map expenses, Map revenue) {
        this.chief = chief;
        this.businessName = businessName;
        this.balance = balance;
        this.employees = employees;
        this.expenses = expenses;
        this.revenue = revenue;
    }

    public String getChief() {
        return chief;
    }
    public void setChief(String chief) {
        this.chief = chief;
    }

    public String getDepartmentName() {
        return businessName;
    }
    public void setDepartmentName(String businessName) {
        this.businessName = businessName;
    }

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Map<String, String> getEmployees() {
        return employees;
    }
    public void addEmployee(String employee, String position) {
        this.employees.put(employee, position);
    }

    public Map<String, Double> getExpenses() {
        return expenses;
    }
    public void setExpenses(HashMap<String, Double> expenses) {
        this.expenses = expenses;
    }

    public Map<String, Double> getRevenue() {
        return revenue;
    }
    public void setRevenue(HashMap<String, Double> revenue) {
        this.revenue = revenue;
    }

    public static final Codec<PoliceDepartment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("chief").forGetter(PoliceDepartment::getChief),
            Codec.STRING.fieldOf("departmentName").forGetter(PoliceDepartment::getDepartmentName),
            Codec.DOUBLE.fieldOf("balance").forGetter(PoliceDepartment::getBalance),
            Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("employees").forGetter(PoliceDepartment::getEmployees),
            Codec.unboundedMap(Codec.STRING, Codec.DOUBLE).fieldOf("expenses").forGetter(PoliceDepartment::getExpenses),
            Codec.unboundedMap(Codec.STRING, Codec.DOUBLE).fieldOf("revenue").forGetter(PoliceDepartment::getRevenue)
    ).apply(instance, PoliceDepartment::new));

    private static final SavedDataType<PoliceDepartment> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(NMEconomy.MOD_ID, "police_department"),
            PoliceDepartment::new,
            CODEC,
            null
    );

    public static PoliceDepartment getPoliceDepartment(MinecraftServer server) {
        return server.getDataStorage().computeIfAbsent(TYPE);
    }

    public static void registerBusiness() {
        NMEconomy.LOGGER.info("Registering police department for " + NMEconomy.MOD_ID);
    }
}
