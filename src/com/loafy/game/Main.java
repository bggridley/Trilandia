package com.loafy.game;

import com.loafy.game.input.Controls;
import com.loafy.game.input.InputManager;
import com.loafy.game.resources.Resources;
import com.loafy.game.state.GameState;
import com.loafy.game.state.IngameState;
import com.loafy.game.state.MenuState;
import com.loafy.game.world.block.Materials;
import org.apache.commons.io.FileUtils;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.*;
import util.KeyConversions;

import java.io.File;

public class Main {

    private static int state;
    public static MenuState menuState;
    public static IngameState ingameState;

    // DISPLAY

    public static int width = 640;
    public static int height = 360;
    public static int scale = 2;

    //this means fps can change but will still update the same
    public static int FPS = 144; // PREFERRED FPS
    public static int rFPS = 30;
    public static float constantInterval = 1000 / rFPS; // CONSTANT TICK XD

    public static int CURRENT_FPS = 0;

    public static int delta;

    private long lastFrame;

    private static Drawable drawable;

    //private int[][] tia;

    public static void main(String args[]) {
        initResources();
        new Main();
    }

    public Main() {
        try {
            Display.setDisplayMode(new DisplayMode(width * scale, height * scale));
            Display.setTitle("Trilandia");
            Display.create();

        } catch (Exception e) {
            e.printStackTrace();
        }

        init();


        int updates = 0;
        int frames = 0;

        long lastTimer = getTime();

        try {
            drawable = new SharedDrawable(Display.getDrawable());
            while (!Display.isCloseRequested()) {
                frames++;
                updates++;
                GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

                delta = getDelta();

                if (getState() != null)
                    getState().update(delta / constantInterval);

                if (!Display.isCurrent())
                    Display.makeCurrent();

                getState().render();
                InputManager.update();

                if (getTime() - lastTimer >= 1000) {
                    lastTimer += 1000;
                    System.out.println("Frames: " + frames + " | Ticks: " + updates);
                    CURRENT_FPS = frames;
                    frames = 0;
                    updates = 0;
                }

                Display.update();
                Display.sync(FPS);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (state == GameState.INGAME) {
            ingameState.getWorld().unload();
        }
        cleanUp();
    }

    private int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public void init() {
      /*  tia = new int[2000][1000];

        for(int x = 0; x < tia.length; x++) {
            for (int y = 0; y < tia[0].length; y++) {
                tia[x][y] = 3;
            }
        }*/

        initOpenGl();
        Resources.init();
        KeyConversions.init();
        Controls.init();
        Materials.init();
        InputManager.init();

        menuState = new MenuState();
        ingameState = new IngameState();
        state = GameState.MENU;
    }

    private static void initResources() {
        Resources.initGameLocation();
        System.setProperty("org.lwjgl.librarypath", (new File(Resources.gameLocation + "/lib/natives/" + LWJGLUtil.getPlatformName()).getAbsolutePath()));
    }

    private void initOpenGl() {
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1.0, 1.0);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void cleanUp() {
        AL.destroy();
        Display.destroy();
        System.exit(0);
    }

    public static void setState(int statee) {
        state = statee;
    }

    public static GameState getState() {
        switch (state) {
            case GameState.INGAME:
                return ingameState;
            case GameState.MENU:
                return menuState;
        }

        return null;
    }

    public static Drawable getDrawable() {
        return drawable;
    }
}
