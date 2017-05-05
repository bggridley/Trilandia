package com.loafy.game.state.gui;

import com.loafy.game.resources.Resources;
import com.loafy.game.state.MenuState;
import com.loafy.game.state.gui.objects.GuiWorldButton;
import com.loafy.game.world.WorldLoader;
import com.loafy.game.world.data.WorldData;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;

public class GuiPlay extends Gui {

    private HashMap<String, WorldData> worlds = new HashMap<>(); //TODO init contructor

    public GuiPlay(final MenuState state, Gui parent) {
        super(state, parent, "Play");

        updateWorlds();
    }

    public void updateWorlds() {
        getButtons().clear();

        String path = Resources.gameLocation + "/saves";
        Resources.makeFile(path);

        File[] directories = new File(path).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() && file.getName().startsWith("world") && !file.getPath().isEmpty();
            }

        });

        if (directories == null)
            return;

        for (File file : directories) {
            worlds.put(file.getName(), (WorldData) WorldLoader.load(WorldData.class, file.getName(), "world.dat"));
        }

        for (int i = 0; i < 5; i++) {
            String directoryName = "world" + (i + 1);
            WorldData data = null;
            if (worlds.containsKey(directoryName)) {
                data = worlds.get(directoryName);
            }

            GuiWorldButton worldButton = new GuiWorldButton((MenuState) state, data, i, 200 + (i * 75)) {

                public void action() {

                }

            };

            addButton(worldButton);
        }
    }
}
