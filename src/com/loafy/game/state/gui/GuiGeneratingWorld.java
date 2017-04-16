package com.loafy.game.state.gui;

import com.loafy.game.Main;
import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Texture;
import com.loafy.game.resources.Resources;
import com.loafy.game.state.GameState;
import com.loafy.game.state.MenuState;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GuiGeneratingWorld extends Gui {

    private GuiLoadingBar loadingBar;

    public GuiGeneratingWorld(GameState state) {
        super(state, "Generating world");
        this.loadingBar = new GuiLoadingBar(364, "Generating World.", 20);
    }

    public void generate(String worldName) {
        Main.ingameState.generateWorld(worldName, 2000, 992);
    }

    public void update() {
        super.update();
    }

    public void render() {
        super.render();

        loadingBar.render();
    }

    public GuiLoadingBar getLoadingBar() {
        return loadingBar;
    }
}
