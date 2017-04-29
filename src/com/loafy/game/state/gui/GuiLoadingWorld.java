package com.loafy.game.state.gui;

import com.loafy.game.Main;
import com.loafy.game.state.GameState;
import com.loafy.game.state.gui.objects.GuiLoadingBar;

public class GuiLoadingWorld extends Gui {

    private GuiLoadingBar loadingBar;

    public GuiLoadingWorld(GameState state) {
        super(state, "Loading world");

        loadingBar = new GuiLoadingBar(400, "Loading World", 20); //TODO IDK HOW MANY STEPS BALLS
    }

    public void load(String name) {
        Main.ingameState.loadWorld(name);
    }

    public void update(float delta) {
        super.update(delta);
    }

    public void render() {
        super.render();

        loadingBar.render();
    }
}
