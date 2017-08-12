package com.loafy.game.gfx;

import com.loafy.game.Main;

public class Animation {

    // SHEET

    private SpriteSheet sheet;

    // TYPES

    private int type;
    public static final int STILL = 0;
    public static final int LOOP = 1;
    public static final int CUSTOM = 2;

    private boolean flipped = false;

    // FRAMES

    private int start;
    private int end;
    private int frame;
    private float interval;
    private float time;


    public Animation(Texture texture, int rows, int cols) {
        this.sheet = new SpriteSheet(texture.getImage(), rows, cols);
    }

    public Animation(String path, int scale, int rows, int cols) {
        this.sheet = new SpriteSheet(Texture.loadBi(path, scale), rows, cols);
    }

    public void render(float x, float y, float light) {
        sheet.getTexture(frame).render(x, y, 1f, flipped, light, light, light, 255f);
    }

    public void update(float delta) {
        time+= delta;
        if (type != STILL) {
            if (time >= interval) {
                time = 0;
                if (frame >= end)
                    frame = start;
                else
                    frame++;
            }
        }
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public Texture getFrame() {
        return sheet.getTexture(frame);
    }

    public void setInterval(float interval) {
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
