package com.nick.nmeconomy.render.screen.atm;

import com.nick.nmeconomy.data.player.EconomyPlayer;
import com.nick.nmeconomy.render.widgets.ATMBackgroundWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ATMScreen extends Screen {
    public ATMScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        ATMBackgroundWidget atmBackgroundWidget = new ATMBackgroundWidget(this.width/2 - 128, this.height/2 - 128, 256, 256);

        Button depositWidget = Button.builder(Component.literal("Deposit"), (btn) -> {
            Screen currentScreen = Minecraft.getInstance().gui.screen();
            Minecraft.getInstance().setScreenAndShow(
                    new DepositScreen(Component.empty(), currentScreen)
            );
        }).bounds(this.width/2 - 40, this.height/2 - 10, 80, 20).build();

        Button withdrawWidget = Button.builder(Component.literal("Withdraw"), (btn) -> {
            Screen currentScreen = Minecraft.getInstance().gui.screen();
            Minecraft.getInstance().setScreenAndShow(
                    new WithdrawScreen(Component.empty(), currentScreen)
            );
        }).bounds(this.width/2 - 40, this.height/2 + 30, 80, 20).build();

        Button closeWidget = Button.builder(Component.literal("Close"), (btn) -> {
            Minecraft.getInstance().setScreenAndShow(null);
        }).bounds(this.width/2 - 40, this.height/2 + 70, 80, 20).build();

        this.addRenderableWidget(atmBackgroundWidget);
        this.addRenderableWidget(depositWidget);
        this.addRenderableWidget(withdrawWidget);
        this.addRenderableWidget(closeWidget);
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
    }
}
