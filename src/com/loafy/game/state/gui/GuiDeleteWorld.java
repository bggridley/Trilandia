package com.loafy.game.state.gui;

import com.loafy.game.state.GameState;
import com.loafy.game.world.WorldLoader;

public class GuiDeleteWorld extends GuiPrompt {

    private int worldSlot;

    public GuiDeleteWorld(GameState state, Gui parent, String worldName, int worldSlot) {
        super(state, parent, "Are you sure you want to delete the world '" + worldName + "'?");
        this.worldSlot = worldSlot;
    }

    public void accept() {
        WorldLoader.delete(worldSlot);
    }
}
