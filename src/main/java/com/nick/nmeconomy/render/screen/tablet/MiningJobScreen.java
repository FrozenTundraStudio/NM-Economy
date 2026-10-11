package com.nick.nmeconomy.render.screen.tablet;

import com.nick.nmeconomy.network.tablet.ServerboundEmployeePayload;
import com.nick.nmeconomy.render.widgets.tablet.TabletBackgroundWidget;
import com.nick.nmeconomy.render.widgets.tablet.TabletTextFieldWidget;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MiningJobScreen extends Screen {
    public Screen parent;
    public MiningJobScreen(Component title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.parent);
    }

    @Override
    protected void init() {
        TabletBackgroundWidget tabletBackgroundWidget = new TabletBackgroundWidget(this.width/2 - 200, this.height/2 - 128, 400, 256);

        TabletTextFieldWidget employeeNameWidget = new TabletTextFieldWidget(font, this.width/2 - 75, this.height/2 - 40, 150, 25, Component.empty());

        TabletTextFieldWidget jobPositionWidget = new TabletTextFieldWidget(font, this.width/2 - 75, this.height/2 - 10, 150, 25, Component.empty());

        Button addEmployee = Button.builder(Component.literal("Add Employee"), (btn) -> {
            String playerName = employeeNameWidget.getValue().toString();
            String position = jobPositionWidget.getValue().toString();
            String jobType = "mining";
            ServerboundEmployeePayload payload = new ServerboundEmployeePayload(playerName, position, jobType);
            ClientPlayNetworking.send(payload);
        }).bounds(this.width/2 - 40, this.height/2 + 15, 80, 20).build();

        Button mainMenuWidget = Button.builder(Component.literal("Main Menu"), (btn) -> {
            this.onClose();
        }).bounds(this.width/2 - 40, this.height/2 + 70, 80, 20).build();

        this.addRenderableWidget(tabletBackgroundWidget);
        this.addRenderableWidget(employeeNameWidget);
        this.addRenderableWidget(jobPositionWidget);
        this.addRenderableWidget(addEmployee);
        this.addRenderableWidget(mainMenuWidget);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        graphics.text(this.font, "Add a new employee.", this.width/2 - 30, this.height/2 - this.font.lineHeight - 60, 0xFFFFFFFF, true);
    }
}
