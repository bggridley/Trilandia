package com.loafy.game.state.gui;

import com.loafy.game.gfx.Font;

import com.loafy.game.state.MenuState;
import com.loafy.game.state.gui.objects.GuiButton;
import com.loafy.game.state.gui.objects.GuiTextbox;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

public class GuiGenerateWorldOptions extends Gui {

    private GuiTextbox textBox;
    private int worldSlot = -1;

    public GuiGenerateWorldOptions(final MenuState state, Gui parent) {
        super(state, parent, "World options");


        GuiButton generateWorld = new GuiButton("Generate world", 350 + (56 * 3)) {

            public void action() {
                state.setCurrentGui(state.guiGeneratingWorld);
                state.guiGeneratingWorld.generate("world" + (getWorldSlot() + 1), textBox.text);
            }

        };

        addButton(generateWorld);

        textBox = new GuiTextbox(0, 200, 2, 23);
        textBox.setX((Display.getWidth() - 200f) / 2f);
    }

    public void update(float delta) {
        super.update(delta);
        textBox.update(delta);
    }

    public void render() {
        super.render();
        textBox.render();

        Font.renderCenteredString("World name:", 200 - 32, 2, Color.black);
    }

    public int getWorldSlot() {
        return worldSlot;
    }

    public void setWorldSlot(int worldSlot) {
        this.worldSlot = worldSlot;
    }
}
