package com.loafy.game.state;


import com.loafy.game.state.gui.Gui;

public interface GameState {

    int MENU = 0;
    int INGAME = 1;

    void update(float delta);

    void render();

    void setCurrentGui(Gui gui);

    Gui getCurrentGui();
}
