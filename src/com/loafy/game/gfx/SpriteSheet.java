package com.loafy.game.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    public Texture[] textures;
    public BufferedImage[] bufferedImages;
    public BufferedImage bi;

    public int rows, cols;

    public SpriteSheet(BufferedImage bi, int rows, int cols) {
        this.bi = bi;
        this.rows = rows;
        this.cols = cols;
        textures = new Texture[rows * cols];
        bufferedImages = new BufferedImage[rows * cols];
        int width = bi.getWidth() / rows;
        int height = bi.getHeight() / cols;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                textures[(j * cols) + i] = Texture.loadTexture(bi.getSubimage(i * width, j * height, width, height));
                bufferedImages[(j * cols) + i] = bi.getSubimage(i * width, j * height, width, height);
            }
        }
    }

    public int getWidth() {
        return bi.getWidth();
    }

    public int getHeight() {
        return bi.getHeight();
    }

    public BufferedImage getImage(int i) {
        return bufferedImages[i];
    }

    public Texture getTexture(int i) {

        return textures[i];
    }
}
