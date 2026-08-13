package com.nick.nmeconomy.item;

import com.nick.nmeconomy.NMEconomy;
import com.nick.nmeconomy.item.components.CashComponent;
import com.nick.nmeconomy.item.components.ModComponents;
import com.nick.nmeconomy.item.custom.Cash;
import com.nick.nmeconomy.item.custom.Tablet;
import com.nick.nmeconomy.item.custom.Wallet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {
    public static final Item CASH = registerItem("cash",
            properties -> new Cash(properties.stacksTo(1).component(ModComponents.CASH_COMPONENT, new CashComponent(1))));

    public static final Item WALLET = registerItem("wallet",
            properties -> new Wallet(properties.stacksTo(1)));

    public static final Item TABLET = registerItem("tablet",
            properties -> new Tablet(properties.stacksTo(1)));

    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(NMEconomy.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(
                        Registries.ITEM, Identifier.fromNamespaceAndPath(NMEconomy.MOD_ID, name)))));
    }

    public static void registerModItems() {
        NMEconomy.LOGGER.info("Registering Mod Items for " + NMEconomy.MOD_ID);
    }

}
