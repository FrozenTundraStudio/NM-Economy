package com.nick.nmeconomy.data.business;

import java.util.HashMap;
import java.util.Map;

public interface Business {
    HashMap<String, String> employees = new HashMap<>();
    BusinessType getBusinessType();
    String getOwner();
    String getBusinessName();
    int getBalance();
    Map<String, String> getEmployees();
    Map<String, Integer> getExpenses();
    Map<String, Integer> getRevenue();

    void setBusinessType(BusinessType type);
    void setOwner(String owner);
    void setBusinessName(String businessName);
    void setBalance(int balance);
    void addEmployee(String employee, String position);
    void setExpenses(HashMap<String, Integer> expenses);
    void setRevenue(HashMap<String, Integer> revenue);

    enum BusinessType {
        NOT_SET,
        DELIVERY,
        GARBAGE,
        RESTAURANT,
        FISHING,
        MINING,
        LOGGING
    }
}
