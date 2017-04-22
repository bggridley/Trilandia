package com.loafy.game.state.gui;

import com.loafy.game.gfx.Font;
import com.loafy.game.state.GameState;
import com.loafy.game.state.gui.objects.GuiButton;
import org.newdawn.slick.Color;

public abstract class GuiPrompt extends Gui {

    private String promptText;

    public GuiPrompt(GameState state, Gui parent, String promptText) {
        super(state, parent, "Are you sure?");
        this.promptText = promptText;

        GuiButton yes = new GuiButton("Yes", 520) {
            public void action() {
                accept();
                state.setCurrentGui(parent);
            }
        };

        addButton(yes);
    }

    public void render() {
        super.render();

        Font.renderCenteredString(promptText, 300, 3f, Color.white);
    }

    public abstract void accept();
}
