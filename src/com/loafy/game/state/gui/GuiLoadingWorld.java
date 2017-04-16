package com.loafy.game.state.gui;

import com.loafy.game.Main;
import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Texture;
import com.loafy.game.resources.Resources;
import com.loafy.game.state.GameState;
import com.loafy.game.world.WorldLoader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class GuiLoadingWorld extends Gui {

    private GuiLoadingBar loadingBar;

    public GuiLoadingWorld(GameState state) {
        super(state, "Loading world");

        loadingBar = new GuiLoadingBar(400, "Loading World", 20); //TODO IDK HOW MANY STEPS BALLS
    }

    public void load(String name) {
        Main.ingameState.loadWorld(name);
    }

    public void update() {
        super.update();
    }

    public void render() {
        super.render();

        loadingBar.render();
    }
}
