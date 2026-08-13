package com.nick.nmeconomy.render.widgets.atm;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class WithdrawTextFieldWidget extends EditBox {

    public WithdrawTextFieldWidget(Font font, int x, int y, int width, int height, Component narration) {
        super(font, x, y, width, height, narration);
    }
}
