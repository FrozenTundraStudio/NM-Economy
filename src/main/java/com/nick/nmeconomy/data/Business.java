package com.nick.nmeconomy.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nick.nmeconomy.NMEconomy;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.HashMap;
import java.util.Map;

public class Business extends SavedData {
    private String owner;
    private String businessName;
    private int balance;
    private Map<String, String> employees;
    private Map<String, Integer> expenses;
    private Map<String, Integer> revenue;

    public Business() {
        owner = "";
        businessName = "";
        balance = 0;
        employees = new HashMap<>();
        expenses = new HashMap<>();
        revenue = new HashMap<>();
    }

    public Business(String owner, String businessName, int balance, Map employees, Map expenses, Map revenue) {
        this.owner = owner;
        this.businessName = businessName;
        this.balance = balance;
        this.employees = employees;
        this.expenses = expenses;
        this.revenue = revenue;
    }

    public String getOwner() {
        return this.owner;
    }
    public String getBusinessName() {
        return this.businessName;
    }
    public int getBalance() {
        return this.balance;
    }
    public Map<String, String> getEmployees() {
        return this.employees;
    }
    public Map<String, Integer> getExpenses() {
        return this.expenses;
    }
    public Map<String, Integer> getRevenue() {
        return this.revenue;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    public void setBalance(int balance) {
        this.balance = balance;
    }
    public void setEmployees(Map employees) {
        this.employees = employees;
    }
    public void setExpenses(Map expenses) {
        this.expenses = expenses;
    }
    public void setRevenue(Map revenue) {
        this.revenue = revenue;
    }

    public static final Codec<Business> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("owner").forGetter(Business::getOwner),
            Codec.STRING.fieldOf("businessName").forGetter(Business::getBusinessName),
            Codec.INT.fieldOf("balance").forGetter(Business::getBalance),
            Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("employees").forGetter(Business::getEmployees),
            Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("expenses").forGetter(Business::getExpenses),
            Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("revenue").forGetter(Business::getRevenue)
    )
            .apply(instance, Business::new));

    private static final SavedDataType<Business> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(NMEconomy.MOD_ID, "business"),
            Business::new,
            CODEC,
            null
    );

    public static Business getBusiness(MinecraftServer server) {
        return server.getDataStorage().computeIfAbsent(TYPE);
    }

    public static void registerBusinesses() {
        NMEconomy.LOGGER.info("Registering businesses for " + NMEconomy.MOD_ID);
    }
}
