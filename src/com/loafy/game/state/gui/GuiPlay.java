package com.loafy.game.state.gui;

import com.loafy.game.state.GameState;
import com.loafy.game.state.MenuState;
import org.lwjgl.opengl.Display;

public class GuiPlay extends Gui {

    public GuiPlay(final MenuState state, Gui parent) {
        super(state, parent, "Play");

        float space = 56;
        float yOffset = Display.getHeight() / 2 - (24) * 2;

        GuiButton loadWorld = new GuiButton("Load world", yOffset + 0 * space) {

            public void action() {
                state.setCurrentGui(state.guiSelectWorld);
            }

        };

        final GuiButton generateWorld = new GuiButton("Generate World", yOffset + 1 * space) {

          public void action() {
              state.setCurrentGui(state.guiGenerateWorldOptions);
              //state.setCurrentGui(state.guiGeneratingWorld);
              //state.guiGeneratingWorld.generate();
              //state.setCurrentGui(state.guiGenerateWorldOptions);
          }
        };


        addButton(generateWorld);
        addButton(loadWorld);
    }
}
