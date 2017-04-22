package com.loafy.game.resources;

import com.loafy.game.gfx.*;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Sound;
import org.newdawn.slick.opengl.CursorLoader;
import org.newdawn.slick.opengl.ImageDataFactory;
import org.newdawn.slick.opengl.LoadableImageData;

import java.io.File;
import java.io.FileInputStream;

public class Resources {

    public static Texture backgroundTexture;
    public static Texture mainLogo;

    public static Texture buttonTexture;
    public static Texture wbuttonTexture;
    public static Texture inventoryTexture;
    public static Texture play;
    public static Texture pencil;
    public static Texture trashcan;

    public static Animation playerAnimation;
    public static Animation goatAnimation;
    public static SpriteSheet itemsSprite;
    public static SpriteSheet blocksSprite;

    public static String gameLocation;

    public static void initGameLocation() {
        String os = System.getProperty("os.name").toUpperCase();

        if (os.contains("WIN"))
            gameLocation = System.getenv("APPDATA");
        else if (os.contains("MAC"))
            gameLocation = System.getProperty("user.home") + "/Library/Application " + "Support";
        else if (os.contains("NUX"))
            gameLocation = System.getProperty("user.home");

        gameLocation += "/loafy";

        System.out.println("Found home location at " + gameLocation);

    }

    public static void init() {
        loadAnimations();
        loadImages();
        loadSounds();
        loadSprites();
        initCursor();
        loadFont();
    }

    public static void loadImages() {
        TextureAtlas gui = new TextureAtlas(Texture.loadBi("gui/gui.png", 1));
        backgroundTexture = Texture.loadTexture(Texture.loadBi("gui/background3.png", 1));
        mainLogo = Texture.loadTexture(Texture.loadBi("gui/logo.png", 2));

        wbuttonTexture = gui.getTexture(0, 0, 240, 32, 2);
        buttonTexture = gui.getTexture(0, 32, 128, 24, 2);

        inventoryTexture = gui.getTexture(0, 80, 22, 22, 2);
        play = gui.getTexture(0, 56, 24, 24, 2);
        pencil = gui.getTexture(24, 56, 24, 24, 2);
        trashcan = gui.getTexture(48, 56, 24, 24, 2);
    }

    public static void loadAnimations() {
        // fix these animations because if more than one is created then they're all the same lmao
        playerAnimation = new Animation("entity/player.png", 2, 8, 8);
        goatAnimation = new Animation("entity/goat.png", 2, 1, 1);
    }

    public static void loadSounds() {
        AudioPlayer.addSound("openInventory", "openInventory.ogg");
    }

    public static void loadSprites() {
        itemsSprite = new SpriteSheet(Texture.loadBi("items.png", 2), 16, 16);
        blocksSprite = new SpriteSheet(Texture.loadBi("blocks.png", 2), 16, 16);
    }

    public static void initCursor() {
        try {
            final LoadableImageData data = ImageDataFactory.getImageDataFor(Resources.gameLocation + "/res/gui/mouse.png");
            data.loadImage(new FileInputStream(Resources.gameLocation + "/res/gui/mouse.png"), true, true, null);
            CursorLoader loader = CursorLoader.get();
            Cursor cursor = loader.getCursor(data, 0, 0);

            Mouse.setNativeCursor(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadFont() {
        Font.initFont();
    }

    /**
     * Creates directories.
     */

    public static File makeFile(String path) {
        File file = new File(path);
        file.mkdirs();
        return file;
    }

    public static File makeFile(String filePath, String fileName) {
        try {
            makeFile(filePath);

            File file = new File(filePath, fileName);
            if (!file.exists()) file.createNewFile();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
