package com.loafy.game.gfx;

import java.awt.image.BufferedImage;

public class TextureAtlas {

    private BufferedImage image;

    public TextureAtlas(BufferedImage image) {
        this.image = image;
    }

    public Texture getTexture(int x, int y, int width, int height, int scale) {
        BufferedImage img = image.getSubimage(x, y , width, height);
        img = Texture.toBufferedImage(img.getScaledInstance(img.getWidth() * scale, img.getHeight() * scale, BufferedImage.SCALE_SMOOTH));

        return Texture.loadTexture(img);
    }

}
