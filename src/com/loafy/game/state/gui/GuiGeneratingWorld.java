package com.loafy.game.state.gui;

import com.loafy.game.Main;
import com.loafy.game.state.GameState;
import com.loafy.game.state.gui.objects.GuiLoadingBar;

public class GuiGeneratingWorld extends Gui {

    private GuiLoadingBar loadingBar;

    public GuiGeneratingWorld(GameState state) {
        super(state, "Generating world");
        this.loadingBar = new GuiLoadingBar(364, "Generating World.", 20);
    }

    public void generate(String fileName, String worldName) {
        Main.ingameState.generateWorld(fileName, worldName, 2000, 992);
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
