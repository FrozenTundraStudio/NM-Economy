package com.nick.nmeconomy;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.nick.nmeconomy.block.ModBlocks;
import com.nick.nmeconomy.block.entity.ModBlockEntities;
import com.nick.nmeconomy.command.ModCommands;
import com.nick.nmeconomy.creativemodetab.ModCreativeModeTabs;
import com.nick.nmeconomy.data.business.types.*;
import com.nick.nmeconomy.data.government.police.PoliceDepartment;
import com.nick.nmeconomy.data.government.prison.Prison;
import com.nick.nmeconomy.data.player.EconomyPlayer;
import com.nick.nmeconomy.item.ModItems;
import com.nick.nmeconomy.item.components.CashComponent;
import com.nick.nmeconomy.item.components.ModComponents;
import com.nick.nmeconomy.network.atm.ServerboundDepositPayload;
import com.nick.nmeconomy.network.atm.ServerboundWithdrawPayload;
import com.nick.nmeconomy.network.tablet.ServerboundEmployeePayload;
import com.nick.nmeconomy.render.menu.ModMenuTypes;
import com.nick.nmeconomy.render.screen.wallet.WalletScreen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NMEconomy implements ModInitializer {
	public static final String MOD_ID = "nm-economy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		EconomyPlayer.registerEconomyPlayer();
		NotSetBusiness.registerBusiness();
		DeliveryBusiness.registerBusiness();
		GarbageBusiness.registerBusiness();
		RestaurantBusiness.registerBusiness();
		FishingBusiness.registerBusiness();
		MiningBusiness.registerBusiness();
		LoggingBusiness.registerBusiness();
		PoliceDepartment.registerBusiness();
		Prison.registerPrison();
		PayloadTypeRegistry.serverboundPlay().register(ServerboundDepositPayload.TYPE, ServerboundDepositPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(ServerboundWithdrawPayload.TYPE, ServerboundWithdrawPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(ServerboundEmployeePayload.TYPE, ServerboundEmployeePayload.CODEC);
		ModCreativeModeTabs.registerModCreativeModeTabs();
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModBlockEntities.initialize();
		ModComponents.registerModComponents();
		ItemComponentTooltipProviderRegistry.addAfter(DataComponents.DAMAGE, ModComponents.CASH_COMPONENT);
		ModMenuTypes.initialize();
		MenuScreens.register(ModMenuTypes.WALLET_BLOCK, WalletScreen::new);

		/* Deposit Receiver */
		ServerPlayNetworking.registerGlobalReceiver(ServerboundDepositPayload.TYPE, (payload, context) -> {
			ServerPlayer player = context.player();
			ItemStack heldItem = player.getMainHandItem();
			Item cashItem = heldItem.getItem();
			if (cashItem == ModItems.CASH) {
				CashComponent heldCash = heldItem.get(ModComponents.CASH_COMPONENT);
				int cashAmount = heldCash.amount();
				int depositAmount = payload.amount();
				int newCashAmount = cashAmount - depositAmount;
				int balance = EconomyPlayer.get(player).getBalance();
				int newBalance = balance + depositAmount;
				EconomyPlayer.get(player).setBalance(newBalance);
				if (newCashAmount <= 0) {
					player.getInventory().removeItem(heldItem);
				}
				CashComponent cashComponent = new CashComponent(newCashAmount);
				player.getMainHandItem().set(ModComponents.CASH_COMPONENT, cashComponent);
            }
		});

		/* Withdraw Receiver */
		ServerPlayNetworking.registerGlobalReceiver(ServerboundWithdrawPayload.TYPE, (payload, context) -> {
			ServerPlayer player = context.player();
			ItemStack heldItem = player.getMainHandItem();
			Item cashItem = heldItem.getItem();
			int withdrawnAmount = payload.amount();
			int balance = EconomyPlayer.get(player).getBalance();
            if (withdrawnAmount <= balance) {
                int newBalance = balance - withdrawnAmount;
                EconomyPlayer.get(player).setBalance(newBalance);
                if (cashItem == ModItems.CASH) {
                    CashComponent heldCash = heldItem.get(ModComponents.CASH_COMPONENT);
                    int cashAmount = heldCash.amount();
                    int newCashAmount = cashAmount + withdrawnAmount;
                    CashComponent cashComponent = new CashComponent(newCashAmount);
                    player.getMainHandItem().set(ModComponents.CASH_COMPONENT, cashComponent);
                } else {
                    ItemStack newCashItem = new ItemStack(ModItems.CASH);
                    CashComponent cashComponent = new CashComponent(withdrawnAmount);
                    newCashItem.set(ModComponents.CASH_COMPONENT, cashComponent);
                    player.addItem(newCashItem);
                }
            }
        });

		/* New employee receiver */
		ServerPlayNetworking.registerGlobalReceiver(ServerboundEmployeePayload.TYPE, (payload, context) -> {
			MinecraftServer server = context.server();
			String playerName = payload.employeeName();
			String position = payload.position();
			String jobType = payload.jobType();
			switch (jobType) {
				case "delivery":
					DeliveryBusiness deliveryBusiness = DeliveryBusiness.getBusiness(server);
					deliveryBusiness.addEmployee(playerName, position);
					break;
				case "fishing":
					FishingBusiness fishingBusiness = FishingBusiness.getBusiness(server);
					fishingBusiness.addEmployee(playerName, position);
					break;
				case "garbage":
					GarbageBusiness garbageBusiness = GarbageBusiness.getBusiness(server);
					garbageBusiness.addEmployee(playerName, position);
					break;
				case "logging":
					LoggingBusiness loggingBusiness = LoggingBusiness.getBusiness(server);
					loggingBusiness.addEmployee(playerName, position);
					break;
				case "mining":
					MiningBusiness miningBusiness = MiningBusiness.getBusiness(server);
					miningBusiness.addEmployee(playerName, position);
					break;
				case "restaurant":
					RestaurantBusiness restaurantBusiness = RestaurantBusiness.getBusiness(server);
					restaurantBusiness.addEmployee(playerName, position);
					break;
                default:
					break;
			}
		});

		/* Command Registration */
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("addtobank")
					.then(Commands.argument("amount", IntegerArgumentType.integer())
							.executes(ModCommands::addToBankCommand)));
		});
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("balance")
					.executes(ModCommands::balanceCommand));
		});
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("split")
					.then(Commands.argument("amount", IntegerArgumentType.integer())
							.executes(ModCommands::splitCommand)));
		});
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("combine")
					.executes(ModCommands::combineCommand));
		});
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("addcell")
					.executes(ModCommands::addCellCommand));
		});
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("arrest")
					.then(Commands.argument("player", StringArgumentType.string())
							.executes(ModCommands::arrestCommand)));
		});
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(Commands.literal("createprison")
					.executes(ModCommands::createPrisonCommand));
		});
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
