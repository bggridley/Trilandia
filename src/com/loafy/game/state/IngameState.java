package com.loafy.game.state;

import com.loafy.game.Main;
import com.loafy.game.entity.player.EntityPlayer;
import com.loafy.game.gfx.Graphics;
import com.loafy.game.gfx.SpriteSheet;
import com.loafy.game.gfx.Texture;
import com.loafy.game.input.Controls;
import com.loafy.game.input.InputManager;
import com.loafy.game.state.gui.*;
import com.loafy.game.world.World;
import com.loafy.game.world.WorldGenerator;
import com.loafy.game.world.WorldLoader;
import com.loafy.game.world.block.Material;
import com.loafy.game.world.data.LevelData;
import com.loafy.game.world.data.WorldData;
import org.lwjgl.opengl.Display;

import java.awt.*;

public class IngameState extends Container implements GameState {

    private static SpriteSheet sprites = new SpriteSheet(Texture.loadBi("gui/ingame.png", 2), 16, 16);
    private Texture heart = sprites.getTexture(0);
    private Texture halfheart = sprites.getTexture(1);
    private Texture fheart = sprites.getTexture(2);
    private Texture darkheart = sprites.getTexture(4);
    private Texture stamina = sprites.getTexture(16);

    public GuiPaused guiPaused;
    public GuiControls guiControls;
    public GuiVideoSettings guiVideoSettings;
    public GuiAudioSettings guiAudioSettings;
    public GuiConsole guiConsole;

    private static float startTime = 0;
    private final int flashAmount = 8;//* (Main.FPS / Main.UPS);
    private final float flashInterval = 4.5f; //* (Main.FPS / Main.UPS);
    private int flashTimes;
    private int flash = 0;

    private World world;
    public boolean generated = false, loaded = false;

    public IngameState() {
        this.guiPaused = new GuiPaused(this);
        this.guiControls = new GuiControls(this, guiPaused);
        this.guiVideoSettings = new GuiVideoSettings(this, guiPaused);
        this.guiAudioSettings = new GuiAudioSettings(this, guiPaused);
        this.guiConsole = new GuiConsole(this);
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
                generator.setName(worldName);
                generator.generate();

                WorldLoader.save(new WorldData(generator), fileName, "world.dat");
                WorldLoader.save(new LevelData(generator), fileName, "level.dat");
                generator = null;


                world = new World(fileName, (WorldData) WorldLoader.load(WorldData.class, fileName, "world.dat"), (LevelData) WorldLoader.load(LevelData.class, fileName, "level.dat")); //todo
                //WorldLoader.save(world.getData(), fileName, "world.dat");
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
                    if (!Main.getDrawable().isCurrent())
                        Main.getDrawable().makeCurrent();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                world = new World(fileName, (WorldData) WorldLoader.load(WorldData.class, fileName, "world.dat"), (LevelData) WorldLoader.load(LevelData.class, fileName, "level.dat"));
                loaded = true;

                Main.setState(GameState.INGAME);
                setCurrentGui(null);
                //Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void update(float delta) {
        super.update(delta);

        if (world == null)
            return;

        if (getCurrentGui() == null || getCurrentGui() == guiConsole)
            world.update(delta);


        if (generated || loaded) {
            if (InputManager.keyPressed(Controls.getControls().get("pause"))) {
                //todo this is weird
                if (this.getCurrentGui() == null) {
                    this.setCurrentGui(guiPaused);
                } else
                    this.setCurrentGui(null);
            } else if (InputManager.keyPressed(41)) {
                //todo this is weird
                if (this.getCurrentGui() == null) {
                    this.setCurrentGui(guiConsole);
                } else
                    this.setCurrentGui(null);
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


        int size = 202;
        int gap = 20;

        int mapX = Display.getWidth() - size - gap;
        int mapY = gap;


        int x = (int) player.getX() / Material.SIZE;
        int y = (int) player.getY() / Material.SIZE;

        int startX = x - 18;
        int startY = y - 20;

        for (int i = 0; i < 39; i++) {
            for (int j = 0; j < 39; j++) {
                int blockX = startX + i;
                int blockY = startY + j;

                float lightLevel = 1f - world.getLightMap().getLevel(blockX, blockY);

                int block = world.getBlock(blockX, blockY);
                int wall = world.getWall(blockX, blockY);

                if (block == Material.AIR.getID()) {
                    Graphics.setColor(Material.fromID(wall).getColor());
                    if(wall == Material.AIR.getID()) {
                        lightLevel = 1f - world.getTimeLight();
                    }
                } else {
                    Graphics.setColor(Material.fromID(block).getColor());
                }


                Graphics.fillRect(mapX + (blockX * Material.SIZE - world.xOffset) / (24f / 5f) - 32f, mapY + (blockY * Material.SIZE - world.yOffset) / (24f / 5f) + 38f, 5, 5);
                Graphics.setColor(new Color(0f, 0f, 0f, lightLevel));
                Graphics.fillRect(mapX + (blockX * Material.SIZE - world.xOffset) / (24f / 5f) - 32f, mapY + (blockY * Material.SIZE - world.yOffset) / (24f / 5f) + 38f, 5, 5);
            }
        }

        Graphics.setColor(Color.BLACK);
        Graphics.drawRect(mapX, mapY, size, size, 6);

        super.render();
    }

    public static void flashHearts() {
        startTime = Main.time;
    }

    public void flashHearts(float delta) {
        EntityPlayer player = world.getPlayer();
        if (player.damaged) {

            float curTime = Main.time - startTime;

            if (curTime % flashInterval < delta) {
                if (flash == 0) flash = 1;
                else flash = 0;

                flashTimes++;
            }

            if (flashTimes >= flashAmount) {
                flash = 0;
                flashTimes = 0;
                startTime = 0;
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
