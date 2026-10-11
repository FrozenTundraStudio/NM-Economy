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

public class LoggingBusiness extends SavedData implements Business {
    private BusinessType type = BusinessType.LOGGING;
    private String owner = "";
    private String businessName = "";
    private int balance = 0;
    private Map<String, String> employees = new HashMap<>();
    private Map<String, Integer> expenses = new HashMap<>();
    private Map<String, Integer> revenue = new HashMap<>();

    public LoggingBusiness() {
    }

    public LoggingBusiness(BusinessType type, String owner, String businessName, int balance, Map employees, Map expenses, Map revenue) {
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
    public void setBusinessType(BusinessType type) {
        this.type = type;
    }

    @Override
    public String getOwner() {
        return owner;
    }
    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String getBusinessName() {
        return businessName;
    }
    @Override
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Override
    public int getBalance() {
        return balance;
    }
    @Override
    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public Map<String, String> getEmployees() {
        return employees;
    }
    @Override
    public void addEmployee(String employee, String position) {
        this.employees.put(employee, position);
    }

    @Override
    public Map<String, Integer> getExpenses() {
        return expenses;
    }
    @Override
    public void setExpenses(HashMap<String, Integer> expenses) {
        this.expenses = expenses;
    }

    @Override
    public Map<String, Integer> getRevenue() {
        return revenue;
    }
    @Override
    public void setRevenue(HashMap<String, Integer> revenue) {
        this.revenue = revenue;
    }

    public static final Codec<LoggingBusiness> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.xmap(BusinessType::valueOf, BusinessType::name)
                    .fieldOf("type").forGetter(LoggingBusiness::getBusinessType),
            Codec.STRING.fieldOf("owner").forGetter(LoggingBusiness::getOwner),
            Codec.STRING.fieldOf("businessName").forGetter(LoggingBusiness::getBusinessName),
            Codec.INT.fieldOf("balance").forGetter(LoggingBusiness::getBalance),
            Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("employees").forGetter(LoggingBusiness::getEmployees),
            Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("expenses").forGetter(LoggingBusiness::getExpenses),
            Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("revenue").forGetter(LoggingBusiness::getRevenue)
    ).apply(instance, LoggingBusiness::new));

    private static final SavedDataType<LoggingBusiness> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(NMEconomy.MOD_ID, "logging_business"),
            LoggingBusiness::new,
            CODEC,
            null
    );

    public static LoggingBusiness getBusiness(MinecraftServer server) {
        return server.getDataStorage().computeIfAbsent(TYPE);
    }

    public static void registerBusiness() {
        NMEconomy.LOGGER.info("Registering logging businesses for " + NMEconomy.MOD_ID);
    }
}
