package com.loafy.game.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    public transient Texture[] textures;
    public transient BufferedImage bi;

    public int rows, cols;

    public SpriteSheet(BufferedImage bi, int rows, int cols) {
        this.bi = bi;
        this.rows = rows;
        this.cols = cols;
        textures = new Texture[rows * cols];
        int width = bi.getWidth() / rows;
        int height = bi.getHeight() / cols;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                textures[(i * cols) + j] = Texture.loadTexture(bi.getSubimage(j * width, i * height, width, height));
            }
        }
    }

    public int getWidth() {
        return bi.getWidth();
    }

    public int getHeight() {
        return bi.getHeight();
    }

    public Texture getTexture(int i) {

        return textures[i];
    }
}
