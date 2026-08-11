package com.nick.nmeconomy.item.custom;

import com.nick.nmeconomy.item.ModItems;
import com.nick.nmeconomy.item.components.CashComponent;
import com.nick.nmeconomy.item.components.ModComponents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Cash extends Item {
    public Cash(Properties properties) {
        super(properties);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack self, ItemStack other, Slot slot, ClickAction clickAction, Player player, SlotAccess carriedItem) {
        if(other.is(ModItems.CASH)) {
            int cashTotal = 0;
            CashComponent selfComponent = self.get(ModComponents.CASH_COMPONENT);
            CashComponent otherComponent = other.get(ModComponents.CASH_COMPONENT);
            int selfCashAmount = selfComponent.amount();
            int otherCashAmount = otherComponent.amount();
            cashTotal += selfCashAmount;
            cashTotal += otherCashAmount;
            player.getInventory().removeItem(self);
            CashComponent newCashComponent = new CashComponent(cashTotal);
            other.set(ModComponents.CASH_COMPONENT, newCashComponent);
            return true;
        }
        return false;
    }
}
