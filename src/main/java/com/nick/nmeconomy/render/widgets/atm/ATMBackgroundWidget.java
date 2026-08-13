package com.nick.nmeconomy.render.widgets.atm;

import com.nick.nmeconomy.NMEconomy;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class ATMBackgroundWidget extends AbstractWidget {
    public ATMBackgroundWidget(int x, int y, int width, int height) {
        super(x, y, width, height, Component.empty());
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        Identifier atmScreenBackground = Identifier.fromNamespaceAndPath(NMEconomy.MOD_ID, "textures/gui/atmscreen.png");
        graphics.blit(RenderPipelines.GUI_TEXTURED, atmScreenBackground, getX(), getY(), 0, 0, this.width, this.height, this.width, this.height);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        return;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return false;
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        return false;
    }
}
