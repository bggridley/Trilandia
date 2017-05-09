package com.loafy.game.state.gui;

import com.loafy.game.Main;
import com.loafy.game.state.GameState;
import com.loafy.game.state.IngameState;
import com.loafy.game.state.MenuState;
import com.loafy.game.state.gui.objects.GuiButton;
import com.loafy.game.world.World;
import com.loafy.game.world.WorldLoader;

public class GuiPaused extends Gui {

    public GuiPaused(final IngameState state) {
        super(state, "Paused");

        float xOffset = 350;
        float space = 56;

        GuiButton resume = new GuiButton("Resume", xOffset + 0 * space) {

            public void action() {
                state.setCurrentGui(null);
            }
        };

        GuiButton audioSettings = new GuiButton("Audio Settings", xOffset + 1 * space) {

            public void action() {
                state.setCurrentGui(state.guiAudioSettings);
            }
        };

        GuiButton videoSettings = new GuiButton("Video Settings", xOffset + 2 * space) {

            public void action() {
                state.setCurrentGui(state.guiVideoSettings);
            }
        };

        GuiButton controls = new GuiButton("Controls", xOffset + 3 * space) {

            public void action() {
                state.setCurrentGui(state.guiControls);
            }
        };

        GuiButton exit = new GuiButton("Save and exit", xOffset + 4 * space) {

            public void action() {
                Main.menuState = new MenuState();
                Main.setState(GameState.MENU);
                Main.menuState.setCurrentGui(Main.menuState.guiMainMenu);

                state.getWorld().unload();

                Main.ingameState = new IngameState();
            }
        };

        addButton(resume);
        addButton(audioSettings);
        addButton(videoSettings);
        addButton(controls);
        addButton(exit);
    }
}
