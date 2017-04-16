package com.loafy.game.state.gui;

import com.loafy.game.Main;
import com.loafy.game.resources.Resources;
import com.loafy.game.state.MenuState;
import org.lwjgl.opengl.Display;

public class GuiMainMenu extends Gui {

    public GuiMainMenu(final MenuState state) {
        super(state, "");

        float xOffset = 350;
        float space = 56;

        GuiButton play = new GuiButton("Play", xOffset + 0 * space) {

            public void action() {
               state.setCurrentGui(state.guiPlay);
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

        GuiButton exit = new GuiButton("Exit", xOffset + 4 * space) {

            public void action() {
                Main.cleanUp();
            }
        };

        addButton(play);
        addButton(audioSettings);
        addButton(videoSettings);
        addButton(controls);
        addButton(exit);
    }

    public void init() {
        ((MenuState) state).setScrollingBackground(true);
    }

    public void update() {
        super.update();
    }

    public void render() {
        super.render();
        Resources.mainLogo.render((Display.getWidth() - Resources.mainLogo.getWidth()) / 2, 50);
    }
}
