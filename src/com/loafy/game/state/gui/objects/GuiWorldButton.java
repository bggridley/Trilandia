package com.loafy.game.state.gui.objects;

import com.loafy.game.gfx.Font;
import com.loafy.game.resources.Resources;
import com.loafy.game.state.MenuState;
import com.loafy.game.state.gui.GuiDeleteWorld;
import com.loafy.game.world.data.WorldData;
import org.newdawn.slick.Color;

import java.util.ArrayList;

public class GuiWorldButton extends GuiButton {

    private ArrayList<GuiButton> widgets;
    private WorldData data;
    private int worldSlot;
    private String name;
    private String sub;

    public GuiWorldButton(MenuState state, WorldData data, int worldSlot, float y) {
        super(Resources.wbuttonTexture, y);
        this.data = data;
        this.worldSlot = worldSlot;
        this.setColor(new Color(255, 255, 255)); // j make color protected xd
        widgets = new ArrayList<>();

        float widgetWidth = Resources.play.getWidth();
        float widgetHeight = Resources.play.getHeight();

        this.sub = "World " + (worldSlot + 1);

        if (data != null) {
            this.name = data.name;
            // add date to sub
        } else {
            this.name = "empty";
            sub += ": empty";
        }

        int playOrder = data == null ? 1 : 3;

        GuiButton play = new GuiButton(Resources.play, x + width - ((widgetWidth + 8) * playOrder), y + (height - widgetHeight) / 2) {
            // make this pass information to GuiGenerateWorldOptions
            public void action() {
                if (playOrder == 1) {
                    // generate world options xd
                    state.setCurrentGui(state.guiGenerateWorldOptions);
                    state.guiGenerateWorldOptions.setWorldSlot(worldSlot);
                } else {
                    // load
                    state.setCurrentGui(state.guiLoadingWorld);
                    state.guiLoadingWorld.load("world" + (worldSlot + 1));

                }
            }

        };

        play.setColor(new Color(180, 180, 200));


        GuiButton edit = new GuiButton(Resources.pencil, x + width - ((widgetWidth + 8) * 2), y + (height - widgetHeight) / 2) {

        };

        edit.setColor(new Color(180, 180, 200));

        GuiButton delete = new GuiButton(Resources.trashcan, x + width - ((widgetWidth + 8) * 1), y + (height - widgetHeight) / 2) {

            public void action() {
                state.setCurrentGui(new GuiDeleteWorld(state, state.getCurrentGui(), name, worldSlot));
            }

        };

        delete.setColor(new Color(180, 180, 200));

        widgets.add(play);

        if (data != null) {
            widgets.add(edit);
            widgets.add(delete);
        }
    }

    public void init() {
        this.width = buttonTexture.getWidth();
        this.height = buttonTexture.getHeight();
    }

    public void update() {
        super.update();

        for (GuiButton button : widgets) {
            button.update();
        }
    }


    public void render() {
        super.render();


        Font.renderString(name, x + 12, y + 12, 2, Color.black);
        Font.renderString(sub, x + 12, y + 28, 2, Color.darkGray);

        for (GuiButton button : widgets) {
            button.render();
        }
    }
}
