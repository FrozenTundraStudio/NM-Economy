package com.nick.nmeconomy.data.business.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.nick.nmeconomy.NMEconomy;
import com.nick.nmeconomy.data.business.Business;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import java.util.HashMap;
import java.util.Map;

public class FishingBusiness extends SavedData implements Business {
    private BusinessType type = BusinessType.FISHING;
    private String owner = "";
    private String businessName = "";
    private int balance = 0;
    private Map<String, String> employees = new HashMap<>();
    private Map<String, Integer> expenses = new HashMap<>();
    private Map<String, Integer> revenue = new HashMap<>();

    public FishingBusiness() {
    }

    public FishingBusiness(BusinessType type, String owner, String businessName, int balance, Map employees, Map expenses, Map revenue) {
        this.type = type;
        this.owner = owner;
        this.businessName = businessName;
        this.balance = balance;
        this.employees = employees;
        this.expenses = expenses;
        this.revenue = revenue;
    }

    @Override
    public BusinessType getBusinessType() {
        return type;
    }
    @Override
    public String getOwner() {
        return owner;
    }
    @Override
    public String getBusinessName() {
        return businessName;
    }
    @Override
    public int getBalance() {
        return balance;
    }
    @Override
    public Map<String, String> getEmployees() {
        return employees;
    }
    @Override
    public Map<String, Integer> getExpenses() {
        return expenses;
    }
    @Override
    public Map<String, Integer> getRevenue() {
        return revenue;
    }

    @Override
    public void setBusinessType(BusinessType type) {
        this.type = type;
    }
    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }
    @Override
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    @Override
    public void setBalance(int balance) {
        this.balance = balance;
    }
    @Override
    public void setEmployees(Map<String, String> employees) {
        this.employees = employees;
    }
    @Override
    public void setExpenses(Map<String, Integer> expenses) {
        this.expenses = expenses;
    }
    @Override
    public void setRevenue(Map<String, Integer> revenue) {
        this.revenue = revenue;
    }

    public static final Codec<FishingBusiness> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.xmap(BusinessType::valueOf, BusinessType::name)
                    .fieldOf("type").forGetter(FishingBusiness::getBusinessType),
            Codec.STRING.fieldOf("owner").forGetter(FishingBusiness::getOwner),
            Codec.STRING.fieldOf("businessName").forGetter(FishingBusiness::getBusinessName),
            Codec.INT.fieldOf("balance").forGetter(FishingBusiness::getBalance),
            Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("employees").forGetter(FishingBusiness::getEmployees),
            Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("expenses").forGetter(FishingBusiness::getExpenses),
            Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("revenue").forGetter(FishingBusiness::getRevenue)
    ).apply(instance, FishingBusiness::new));

    private static final SavedDataType<FishingBusiness> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(NMEconomy.MOD_ID, "fishing_business"),
            FishingBusiness::new,
            CODEC,
            null
    );

    public static FishingBusiness getBusiness(MinecraftServer server) {
        return server.getDataStorage().computeIfAbsent(TYPE);
    }

    public static void registerBusiness() {
        NMEconomy.LOGGER.info("Registering fishing businesses for " + NMEconomy.MOD_ID);
    }
}
