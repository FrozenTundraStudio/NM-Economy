package com.nick.nmeconomy.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.nick.nmeconomy.NMEconomy;
import com.nick.nmeconomy.data.player.EconomyPlayer;
import com.nick.nmeconomy.item.ModItems;
import com.nick.nmeconomy.item.components.CashComponent;
import com.nick.nmeconomy.item.components.ModComponents;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModCommands {

    public static int splitCommand(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        ItemStack heldItem = player.getMainHandItem();
        Item cashItem = heldItem.getItem();
        CashComponent heldCash = heldItem.get(ModComponents.CASH_COMPONENT);
        int cashAmount = heldCash.amount();
        int splitAmount = IntegerArgumentType.getInteger(context, "amount");
        int newCashAmount = cashAmount - splitAmount;
        if (cashAmount > splitAmount) {
            if (cashItem == ModItems.CASH) {
                CashComponent cashComponent = new CashComponent(newCashAmount);
                player.getMainHandItem().set(ModComponents.CASH_COMPONENT, cashComponent);
                ItemStack newCashItem = new ItemStack(ModItems.CASH);
                CashComponent newCashComponent = new CashComponent(splitAmount);
                newCashItem.set(ModComponents.CASH_COMPONENT, newCashComponent);
                player.addItem(newCashItem);
                context.getSource().sendSuccess(() -> Component.literal("Split: $" + splitAmount), false);
            }
        } else {
            context.getSource().sendFailure(Component.literal("You dont have enough cash."));
        }
        NMEconomy.LOGGER.info(player.getPlainTextName() + " Used /split");
        return 1;
    }

    public static int balanceCommand(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        int balance = EconomyPlayer.get(player).getBalance();
        context.getSource().sendSuccess(() -> Component.literal("Balance: " + balance), false);
        NMEconomy.LOGGER.info(player.getPlainTextName() + " Used /balance");
        return 1;
    }

    public static int addToBankCommand(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        int balance = EconomyPlayer.get(player).getBalance();
        int amount = IntegerArgumentType.getInteger(context, "amount");
        int newBalance = balance + amount;
        EconomyPlayer.get(player).setBalance(newBalance);
        context.getSource().sendSuccess(() -> Component.literal("Added. New balance is now: " + newBalance), false);
        NMEconomy.LOGGER.info(player.getPlainTextName() + " Used /addToBank");
        return 1;
    }

    public static int combineCommand(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        int cashTotal = 0;
        for(ItemStack itemStack : player.getInventory()) {
            if(itemStack.is(ModItems.CASH)) {
                CashComponent cashComponent = itemStack.get(ModComponents.CASH_COMPONENT);
                int cashAmount = cashComponent.amount();
                cashTotal += cashAmount;
                player.getInventory().removeItem(itemStack);
            }
        }
        ItemStack newCashItem = new ItemStack(ModItems.CASH);
        CashComponent newCashComponent = new CashComponent(cashTotal);
        newCashItem.set(ModComponents.CASH_COMPONENT, newCashComponent);
        player.addItem(newCashItem);
        context.getSource().sendSuccess(() -> Component.literal("Combined."), false);
        NMEconomy.LOGGER.info(player.getPlainTextName() + " Used /combine");
        return 1;
    }
}
