package com.loafy.game.gfx;

import com.loafy.game.resources.Resources;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

public class Texture {

    private int tex;
    private static BufferedImage image;
    private float width, height;

    public Texture(int tex, float width, float height) {
        this.tex = tex;
        this.width = width;
        this.height = height;
    }

    public static Texture getTexture(String name, int scale) {
        return loadTexture(loadBi(name, scale));
    }

    public static BufferedImage loadBi(String name, int scale) {
        BufferedImage img;
        try {
            System.out.println(Resources.gameLocation + "/res/" + name);
            img = ImageIO.read(new File(Resources.gameLocation + "/res/" + name));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        image = toBufferedImage(img.getScaledInstance(img.getWidth() * scale, img.getHeight() * scale, BufferedImage.SCALE_SMOOTH));
        return image;
    }

    public static Texture loadTexture(BufferedImage img) {
        int tex = loadTex(img);

        int width = img.getWidth();
        int height = img.getHeight();

        return new Texture(tex, width, height);
    }

    public static int loadTex(BufferedImage img) {
        int tex;

        int width = img.getWidth();
        int height = img.getHeight();

        int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);

        ByteBuffer b = BufferUtils.createByteBuffer(width * height * 4);
        tex = GL11.glGenTextures();

        for (int i : pixels) {
            byte rr = (byte) ((i >> 16) & 0xFF);
            byte gg = (byte) ((i >> 8) & 0xFF);
            byte bb = (byte) (i & 0xFF);
            byte aa = (byte) ((i >> 24) & 0xFF);

            b.put(rr);
            b.put(gg);
            b.put(bb);
            b.put(aa);
        }

        b.flip();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, b);

        return tex;
    }


    public void render(float x, float y) {
        render(x, y, 1f, false);
    }

    public void render(float x, float y, float scale, boolean flip) {
        render(x, y, scale, flip, 1, 1, 1, 1);
    }

    public void render(float x, float y, float scale, boolean flip, Color color) {
        render(x, y, scale, flip, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
    }

    public void render(float x, float y, float scale, boolean flip, float r, float g, float b, float a) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_COLOR);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor4f(r, g, b, a);

        if (!flip)
            GL11.glTexCoord2f(0, 0);
        else
            GL11.glTexCoord2f(1, 0);

        GL11.glVertex2f(x, y);

        if (!flip)
            GL11.glTexCoord2f(1, 0);
        else
            GL11.glTexCoord2f(0, 0);

        GL11.glVertex2f(x + width * scale, y);

        if (!flip)
            GL11.glTexCoord2f(1, 1);
        else
            GL11.glTexCoord2f(0, 1);

        GL11.glVertex2f(x + width * scale, y + height * scale);

        if (!flip)
            GL11.glTexCoord2f(0, 1);
        else
            GL11.glTexCoord2f(1, 1);

        GL11.glVertex2f(x, y + height * scale);

        GL11.glDisable(GL11.GL_COLOR);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }


    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

    public BufferedImage getImage() {
        return image;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}