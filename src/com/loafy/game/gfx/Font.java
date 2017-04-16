package com.loafy.game.gfx;

import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Font {

    private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,:;'\"!?$%()-=+/ ";
    private static SpriteSheet sprites = new SpriteSheet(Texture.loadBi("font/font.png", 1), 16, 16);
    private static int[] spaces;

    private static int tex;

    public static void initFont() {
        try {
            tex = Texture.loadTex(Texture.loadBi("font/bitmap2.png", 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void renderCenteredString(String text, float y, float size, Color color) {
        renderString(text, (Display.getWidth() - getWidth(text, size)) / 2, y, size, color);
    }

    public static void renderString(String text, float x, float y, float size, Color color) {
        renderString(text, tex, 16, x, y, 16 * size, 9 * size, color);
    }

    public static float getWidth(String string, float size) {
        return string.length() * (16 * size) / 5;
    }

    public static float getWidth(int i, float size) {
        return i * (16 * size) / 5;
    }

    private static void renderString(String string, int textureObject, int gridSize, float x, float y,
                                     float characterWidth, float characterHeight, Color color) {

        string = string.toUpperCase();

        glPushAttrib(GL_TEXTURE_BIT | GL_ENABLE_BIT | GL_COLOR_BUFFER_BIT);
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, textureObject);
        glEnable(GL_BLEND);
        glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        glPushMatrix();
        glBegin(GL_QUADS);
        glEnable(GL_COLOR);


        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;


        glColor3f(r, g, b);

        for (int i = 0; i < string.length(); i++) {
            int asciiCode = (int) string.charAt(i);
            final float cellSize = 1.0f / gridSize;
            float cellX = (asciiCode % gridSize) * cellSize;
            float cellY = (asciiCode / gridSize) * cellSize;

            // bottom left
            glTexCoord2f(cellX, cellY);
            glVertex2f(x + i * characterWidth / 5, y);
            // bottom right
            glTexCoord2f(cellX + cellSize, cellY);
            glVertex2f(x + i * characterWidth / 5 + characterWidth / 2, y);
            // top right
            glTexCoord2f(cellX + cellSize, cellY + cellSize);
            glVertex2f(x + i * characterWidth / 5 + characterWidth / 2, y + characterHeight);
            // top left
            glTexCoord2f(cellX, cellY + cellSize);
            glVertex2f(x + i * characterWidth / 5, y + characterHeight);

        }

        glEnd();
        glPopMatrix();
        glPopAttrib();
    }
}
