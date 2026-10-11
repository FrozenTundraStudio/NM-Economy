package com.nick.nmeconomy.render.screen.atm;

import com.nick.nmeconomy.data.player.EconomyPlayer;
import com.nick.nmeconomy.item.ModItems;
import com.nick.nmeconomy.item.components.CashComponent;
import com.nick.nmeconomy.item.components.ModComponents;
import com.nick.nmeconomy.network.atm.ServerboundDepositPayload;
import com.nick.nmeconomy.render.widgets.atm.ATMBackgroundWidget;
import com.nick.nmeconomy.render.widgets.atm.DepositTextFieldWidget;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DepositScreen extends Screen {
    public Screen parent;
    public DepositScreen(Component title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.parent);
    }

    @Override
    protected void init() {
        ATMBackgroundWidget atmBackgroundWidget = new ATMBackgroundWidget(this.width/2 - 128, this.height/2 - 128, 256, 256);

        DepositTextFieldWidget depositTextFieldWidget = new DepositTextFieldWidget(font, this.width/2 - 75, this.height/2 - 40, 150, 25, Component.empty());

        Button depositWidget = Button.builder(Component.literal("Deposit"), (btn) -> {
            int amount = Integer.parseInt(depositTextFieldWidget.getValue());
            ServerboundDepositPayload payload = new ServerboundDepositPayload(amount);
            ClientPlayNetworking.send(payload);
        }).bounds(this.width/2 - 40, this.height/2 + 15, 80, 20).build();

        Button mainMenuWidget = Button.builder(Component.literal("Main Menu"), (btn) -> {
            this.onClose();
        }).bounds(this.width/2 - 40, this.height/2 + 70, 80, 20).build();

        this.addRenderableWidget(atmBackgroundWidget);
        this.addRenderableWidget(depositTextFieldWidget);
        this.addRenderableWidget(depositWidget);
        this.addRenderableWidget(mainMenuWidget);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);

        int rectangleX = this.width/2 - 60;
        int rectangleY = this.height/2 - 73;
        int rectangleWidth = 120;
        int rectangleHeight = 15;
        graphics.fill(rectangleX, rectangleY, rectangleX + rectangleWidth, rectangleY + rectangleHeight, 0xff000000);

        Player player = this.minecraft.player;
        int balance = EconomyPlayer.get(player).getBalance();
        graphics.text(this.font, "Balance: $" + balance, this.width/2 - 35, this.height/2 - this.font.lineHeight - 60, 0xFFFFFFFF, true);

        ItemStack heldItem = player.getMainHandItem();
        Item cashItem = heldItem.getItem();
        if (cashItem == ModItems.CASH) {
            CashComponent heldCash = heldItem.get(ModComponents.CASH_COMPONENT);
            int cashAmount = heldCash.amount();
            graphics.text(this.font, "Cash in hand detected: $" + cashAmount, this.width/2 - 105, this.height/2 - this.font.lineHeight - 95, 0xFFFFFFFF, true);
        } else {
            graphics.text(this.font, "No cash detected in hand", this.width/2 - 105, this.height/2 - this.font.lineHeight - 95, 0xFFFFFFFF, true);
        }
    }
}
