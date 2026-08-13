package com.nick.nmeconomy.render.widgets.atm;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class DepositTextFieldWidget extends EditBox {

    public DepositTextFieldWidget(Font font, int x, int y, int width, int height,  Component narration) {
        super(font, x, y, width, height, narration);
    }
}
