package com.nick.nmeconomy.render.screen.tablet;

import com.nick.nmeconomy.render.widgets.tablet.TabletBackgroundWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TabletScreen extends Screen {
    public TabletScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        TabletBackgroundWidget tabletBackgroundWidget = new TabletBackgroundWidget(this.width/2 - 200, this.height/2 - 128, 400, 256);

        Button miningJobAppButton = Button.builder(Component.literal("Mining"), (btn) -> {
            Screen currentScreen = Minecraft.getInstance().gui.screen();
            Minecraft.getInstance().setScreenAndShow(
                    new MiningJobScreen(Component.empty(), currentScreen)
            );
        }).bounds(this.width/2 - 40, this.height/2 + 15, 80, 20).build();

        this.addRenderableWidget(tabletBackgroundWidget);
        this.addRenderableWidget(miningJobAppButton);
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
    }
}
