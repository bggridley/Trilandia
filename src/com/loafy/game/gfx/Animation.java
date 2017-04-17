package com.loafy.game.gfx;

import com.loafy.game.Main;

import java.io.Serializable;

public class Animation {

    // SHEET

    private SpriteSheet sheet;

    // TYPES

    private int type;
    public static final int STILL = 0;
    public static final int LOOP = 1;
    public static final int CUSTOM = 2;

    // FRAMES

    private int start;
    private int end;
    private int frame;
    private float interval;
    private int time;

    public Animation(String path, int scale, int rows, int cols) {
        this.sheet = new SpriteSheet(Texture.loadBi(path, scale), rows, cols);
    }

    public void render(float x, float y) {
        sheet.getTexture(frame).render(x, y);
    }

    public void update() {
        time++;

        if (type != STILL) {
            if (time % (int)interval == 0) {
                if (frame >= end)
                    frame = start;
                else
                    frame++;
            }
        }
    }

    public Texture getFrame() {
        return sheet.getTexture(frame);
    }

    public void setInterval(int interval) {
        this.interval = interval; //* ((float)Main.FPS / (float)Main.UPS);
    }

    public float getInterval() {
        return interval;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}
