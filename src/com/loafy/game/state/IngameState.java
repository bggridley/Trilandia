package com.loafy.game.state;

import com.google.gson.Gson;
import com.loafy.game.Main;
import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.gfx.SpriteSheet;
import com.loafy.game.gfx.Texture;
import com.loafy.game.input.Controls;
import com.loafy.game.input.InputManager;
import com.loafy.game.state.gui.*;
import com.loafy.game.world.Chunk;
import com.loafy.game.world.World;
import com.loafy.game.world.WorldGenerator;
import com.loafy.game.world.WorldLoader;
import com.loafy.game.world.block.Block;
import com.loafy.game.world.data.LightMapData;
import com.loafy.game.world.data.PlayerData;
import com.loafy.game.world.data.WorldData;

public class IngameState extends Container implements GameState {

    private static SpriteSheet sprites = new SpriteSheet(Texture.loadBi("gui/ingame.png", 2), 16, 16);
    private Texture heart = sprites.getTexture(0);
    private Texture halfheart = sprites.getTexture(1);
    private Texture fheart = sprites.getTexture(2);
    private Texture darkheart = sprites.getTexture(4);
    private Texture stamina = sprites.getTexture(16);
    private World world;
    private final int flashTime = 30;//* (Main.FPS / Main.UPS);
    private final int flashInterval = 6; //* (Main.FPS / Main.UPS);
    private int flash = 0;
    private float curTime;
    public boolean generated = false, loaded = false;
    public GuiPaused guiPaused;
    public GuiControls guiControls;
    public GuiVideoSettings guiVideoSettings;
    public GuiAudioSettings guiAudioSettings;

    public IngameState() {
        this.guiPaused = new GuiPaused(this);
        this.guiControls = new GuiControls(this, guiPaused);
        this.guiVideoSettings = new GuiVideoSettings(this, guiPaused);
        this.guiAudioSettings = new GuiAudioSettings(this, guiPaused);
    }

    // this is here because all world generation details must be passed to the world object
    public void generateWorld(String fileName, final String worldName, final int width, final int height) {
        new Thread(new Runnable() {

            public void run() {
                try {
                    Main.getDrawable().makeCurrent();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                WorldGenerator generator = new WorldGenerator(width, height);
                Block[][] blocks = generator.getBlocks();
                Block[][] walls = generator.getWalls();
                Chunk[][] chunks = new Chunk[width / Chunk.SIZE][height / Chunk.SIZE];

                /**
                 * Creating chunks out of generated blocks
                 */

                for (int x = 0; x < chunks.length; x++) {
                    for (int y = 0; y < chunks[0].length; y++) {
                        int xx = x * Chunk.SIZE;
                        int yy = y * Chunk.SIZE;
                        Block[][] chunkBlocks = new Block[Chunk.SIZE][Chunk.SIZE];
                        Block[][] chunkWalls = new Block[Chunk.SIZE][Chunk.SIZE];

                        for (int i = 0; i < Chunk.SIZE; i++) {
                            for (int j = 0; j < Chunk.SIZE; j++) {
                                chunkBlocks[i][j] = blocks[xx + i][yy + j];
                                chunkWalls[i][j] = walls[xx + i][yy + j];
                            }
                        }

                        chunks[x][y] = new Chunk(chunkBlocks, chunkWalls, x, y);
                    }
                }

                /**
                 * Save chunks to file as first time
                 */


                for (int x = 0; x < chunks.length; x++) {
                    for (int y = 0; y < chunks[0].length; y++) {
                        Gson gson = new Gson();
                        WorldLoader.saveChunk(fileName, chunks[x][y], gson); //todo make sure
                    }
                }


                world = new World(fileName, worldName, generator);
                WorldLoader.save(world.getData(), fileName, "world.dat");
                WorldLoader.save(world.getPlayer().getData(), fileName, "player.dat");
                WorldLoader.save(world.getLightMap().getData(), fileName, "light.dat");
                generated = true;

                Main.setState(GameState.INGAME);
                Main.menuState.setCurrentGui(null);
            }
        }).start();

    }

    public void loadWorld(final String fileName) {
        new Thread(new Runnable() {

            public void run() {
                try {
                    if(!Main.getDrawable().isCurrent())
                    Main.getDrawable().makeCurrent();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                world = new World(fileName, (WorldData) WorldLoader.load(WorldData.class, fileName, "world.dat"), (PlayerData) WorldLoader.load(PlayerData.class, fileName, "player.dat"), (LightMapData) WorldLoader.load(LightMapData.class, fileName, "light.dat"));
                loaded = true;

                Main.setState(GameState.INGAME);
                setCurrentGui(null);
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void update(float delta) {
        super.update(delta);

        if (world == null)
            return;

        if (getCurrentGui() == null)
            world.update(delta);


        if (generated || loaded) {
            if (InputManager.keyPressed(Controls.getControls().get("pause"))) {
                //todo this is weird
                if (this.getCurrentGui() == null) {
                    this.setCurrentGui(guiPaused);
                } else
                    this.setCurrentGui(null);
            } else {
                // if (this.getCurrentGui() == null)
                //world.getPlayer().getInventory().handleKeyboard();
            }
        }

        flashHearts(delta);
    }

    public void render() {
        if (world == null)
            return;

        EntityPlayer player = world.getPlayer();
        world.render();

        float health = player.getHealth();
        float hunger = player.getStamina();

        for (int i = 0; i < player.getMaxHealth() / 20; i++) {
            float x = 8 + i * heart.getWidth();
            float rX = x - (i * 2);


            darkheart.render(rX, 8, 1f, false);
            if (health >= i * 20) {
                if (health >= i * 20 + 10) {
                    heart.render(rX, 8, 1f, false);
                } else {
                    halfheart.render(rX, 8, 1f, false);
                }
            }

            if (flash == 1)
                fheart.render(rX, 8, 1f, false);

        }

        for (int i = 0; i < player.getMaxStamina() / 20; i++) {
            float x = 8 + i * stamina.getWidth();
            stamina.render(x - (i * 2), 8 + stamina.getHeight());
        }

        super.render();
    }

    public void flashHearts(float delta) {
        EntityPlayer player = world.getPlayer();
        if (player.damaged) {

            if ((int) curTime % flashInterval == 0) {
                if (flash == 0) flash = 1;
                else flash = 0;
            }

            curTime += delta;

            if (curTime > flashTime) {
                curTime = 0;
                flash = 0;
                player.damaged = false;
            }
        }
    }

    public Gui getCurrentGui() {
        return getCurrentGuiFromContainer();
    }

    public void setCurrentGui(Gui gui) {
        setCurrentGuiFromContainer(gui);
    }

    public World getWorld() {
        return world;
    }

}
