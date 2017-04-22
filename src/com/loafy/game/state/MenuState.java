package com.loafy.game.state;

import com.loafy.game.Main;
import com.loafy.game.gfx.Texture;
import com.loafy.game.state.gui.*;
import com.loafy.game.resources.Resources;

public class MenuState extends Container implements GameState {

    private Texture background;
    public GuiMainMenu guiMainMenu;
    public GuiGeneratingWorld guiGeneratingWorld;
    public GuiChooseWorld guiSelectWorld;
    public GuiLoadingWorld guiLoadingWorld;
    public GuiControls guiControls;
    public GuiVideoSettings guiVideoSettings;
    public GuiAudioSettings guiAudioSettings;

    public GuiGenerateWorldOptions guiGenerateWorldOptions;

    public float xOffset = 0f, yOffset = 0f;

    public GuiPlay guiPlay;

    private boolean backgroundScrolling = true;

    public MenuState() {
        this.background = Resources.backgroundTexture;
        this.guiMainMenu = new GuiMainMenu(this);
        this.guiGeneratingWorld = new GuiGeneratingWorld(this);
        this.guiLoadingWorld = new GuiLoadingWorld(this);
        this.guiControls = new GuiControls(this, guiMainMenu);
        this.guiVideoSettings = new GuiVideoSettings(this, guiMainMenu);
        this.guiAudioSettings = new GuiAudioSettings(this, guiMainMenu);
        this.guiPlay = new GuiPlay(this, guiMainMenu);
        this.guiGenerateWorldOptions = new GuiGenerateWorldOptions(this, guiPlay);
        this.guiSelectWorld = new GuiChooseWorld(this, guiPlay);

        setCurrentGui(guiMainMenu);
    }

    public Gui getCurrentGui() {
        return getCurrentGuiFromContainer();
    }

    public void setCurrentGui(Gui gui) {
        setCurrentGuiFromContainer(gui);
    }

    float m = 1;

    public void update(float delta) {
        super.update();

        if(backgroundScrolling) {
            if (xOffset > 1920 - 1280) m = -1;
            else if (xOffset < 0) m = 1;
            if (delta < 10)
                xOffset += m * delta; //(1f * delta) / 1000f * Main.UPS * m;
        }
    }

    public void render() {
            background.render(0, 0);

        super.render();
    }

    public void setScrollingBackground(boolean b) {
        this.backgroundScrolling = b;
    }
}