package com.nick.nmeconomy.data.business;

import java.util.Map;

public interface Business {
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
    void setEmployees(Map<String, String> employees);
    void setExpenses(Map<String, Integer> expenses);
    void setRevenue(Map<String, Integer> revenue);

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
