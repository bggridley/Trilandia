package com.loafy.game.state.gui;

import com.loafy.game.Main;
import com.loafy.game.gfx.Font;
import com.loafy.game.gfx.Texture;
import com.loafy.game.resources.Resources;
import com.loafy.game.state.MenuState;
import com.loafy.game.state.gui.objects.GuiButton;
import com.loafy.game.state.gui.objects.GuiWorldButton;
import com.loafy.game.world.WorldLoader;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.data.WorldData;

import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiChooseWorld extends Gui {

    private HashMap<String, WorldData> worlds = new HashMap<>();

    private float startX;
    private float startY;

    private float width;
    private float height;

    private Texture dirt = Material.DIRT.getTexture();
    private Texture dirtWall = Material.DIRT_WALL.getTexture();

    public GuiChooseWorld(final MenuState state, Gui parent) {
        super(state, parent, "Select World");

        this.width = 720;
        this.height = 380;

        this.startX = Gui.getCenteredX(width);
        this.startY = Gui.getCenteredY(height);


        float border = 20f;
        String path = Resources.gameLocation + "/saves";
        Resources.makeFile(path);

        File[] directories = new File(path).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() && file.getName().startsWith("world");
            }

        });

        if (directories == null)
            return;

        for (File file : directories) {
            worlds.put(file.getName(), (WorldData) WorldLoader.load(WorldData.class, file.getName(), "world.dat"));
        }


        List<String> keyList = new ArrayList<>(worlds.keySet());

        for (int i = 0; i < worlds.size(); i++) {
            final String worldName = keyList.get(i);
            WorldData data = worlds.get(worldName);

            GuiWorldButton button = new GuiWorldButton(state, data, 0, startX + i * (Resources.wbuttonTexture.getHeight() + 8)) {

                public void action() {
                    state.setCurrentGui(state.guiLoadingWorld);
                    state.guiLoadingWorld.load(worldName);
                }
            };

            addButton(button);

        }
    }

    int start = 4;
    int end = 22;

    public void render() {

        renderWalls(true, 0, 50);


        for (GuiButton button : buttons) {
            if (button instanceof GuiWorldButton)
                button.render();
        }

        renderWalls(false, 0, start);
        renderWalls(false, end - 5, end);

        if (title != null)
            Font.renderCenteredString(title, 42, 8f, Color.WHITE);

        for (GuiButton button : buttons) {
            if (!(button instanceof GuiWorldButton))
                button.render();
        }
        //
    }


    public void renderWalls(boolean background, int start, int end) {
        for (int x = 0; x < (Main.width * Main.scale) / dirt.getWidth(); x++) {
            for (int y = 0; y < (Main.height * Main.scale) / dirt.getHeight(); y++) {
                if ((y >= start && y <= end)) {
                    if (background)
                        dirtWall.render(x * dirt.getWidth(), y * dirt.getHeight());
                    else
                        dirt.render(x * dirt.getWidth(), y * dirt.getHeight());
                }
            }
        }
    }

    public void update() {
        for (GuiButton button : buttons) {
            button.update();
        }

        //scrollBar.update();
        for (int i = 0; i < getButtons().size(); i++) {
            GuiButton button = getButtons().get(i);

            if (button instanceof GuiWorldButton) {
                GuiWorldButton worldButton = (GuiWorldButton) button;

                //worldButton.setY(startY + i * (Resources.wbuttonTexture.getHeight() + 8) + scrollBar.getOffset());
            }
        }
    }

    public void init() {
        ((MenuState) state).setScrollingBackground(false);
    }

    public void exit() {
        ((MenuState) state).setScrollingBackground(true);
    }
}
