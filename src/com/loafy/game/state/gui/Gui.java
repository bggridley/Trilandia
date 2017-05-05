package com.loafy.game.state.gui;

import com.loafy.game.Main;
import com.loafy.game.gfx.Font;
import com.loafy.game.state.GameState;
import com.loafy.game.state.IngameState;
import com.loafy.game.state.gui.objects.GuiButton;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import java.util.LinkedList;

public class Gui {

    protected LinkedList<GuiButton> buttons;
    protected String title;
    public GameState state;

    public Gui(final GameState state, String title) {
        this.buttons = new LinkedList<>();
        this.title = title;
        this.state = state;
    }

    public Gui(final GameState state, final Gui parent, String title) {
        this(state, title);

        GuiButton back = new GuiButton("Back", 350 + (56 * 4)) {

            public void action() {
                super.action();
                state.setCurrentGui(parent);
                exit();
            }
        };

        addButton(back);
    }

    public void init() {

    }

    public void exit() {

    }

    public void render() {
        if (state instanceof IngameState) {
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.15f);
            GL11.glRectf(0, 0, Display.getWidth(), Display.getHeight());
        }

        for (GuiButton button : buttons) {
            button.render();
        }

        if (title != null)
            Font.renderCenteredString(title, 42, 6f, Color.white);
    }

    public void update(float delta) {
        for (GuiButton button : buttons) {
            button.update();
        }
    }

    public static float getCenteredX(float width) {
        return ((Main.width * Main.scale) - width) / 2;
    }

    public static float getCenteredY(float height) {
        return ((Main.height * Main.scale) - height) / 2;
    }

    public static float getTop(float height) {
        return ((Main.height * Main.scale) - height);
    }

    public LinkedList<GuiButton> getButtons() {
        return buttons;
    }

    public void addButton(GuiButton button) {
        buttons.add(button);
    }
}
