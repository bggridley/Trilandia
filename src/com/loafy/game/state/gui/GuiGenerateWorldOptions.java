package com.loafy.game.state.gui;

import com.loafy.game.gfx.Font;
import com.loafy.game.state.GameState;

import com.loafy.game.state.MenuState;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

public class GuiGenerateWorldOptions extends Gui {

    private GuiTextbox textBox;

    public GuiGenerateWorldOptions(final MenuState state, Gui parent) {
        super(state, parent, "World options");


        GuiButton generateWorld = new GuiButton("Generate world", 350 + (56 * 3)) {

            public void action() {
                state.setCurrentGui(state.guiGeneratingWorld);
                state.guiGeneratingWorld.generate(textBox.text);
            }

        };

        addButton(generateWorld);

        int maxWidth = 20;
        textBox = new GuiTextbox(0, 200, 4, maxWidth);
        textBox.setX((Display.getWidth() - Font.getWidth(maxWidth, 4)) / 2);
    }

    public void update() {
        super.update();
        textBox.update();
    }

    public void render() {
        super.render();
        textBox.render();

        Font.renderCenteredString("World name:", 200 - 32, 4, Color.black);
    }

}
