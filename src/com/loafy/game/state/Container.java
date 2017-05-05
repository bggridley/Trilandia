package com.loafy.game.state;

import com.loafy.game.state.gui.Gui;

public class Container {

    private Gui currentGui;

    public void setCurrentGuiFromContainer(Gui gui) {
        this.currentGui = gui;

        if (gui != null)
            gui.init();
    }

    public Gui getCurrentGuiFromContainer() {
        return currentGui;
    }

    public void render() {
        if (currentGui != null)
            currentGui.render();
    }

    public void update(float delta) {
        if (currentGui != null)
            currentGui.update(delta);
    }
}
