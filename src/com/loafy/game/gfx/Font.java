package com.loafy.game.gfx;

import com.loafy.game.resources.Resources;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Font {

    private static int tex;
    private static HashMap<Integer, FontGlyph> glyphs;

    public static void initFont() {
        glyphs = new HashMap<>();
        try {
            tex = Texture.loadTex(Texture.loadBi("font/bitmap.png", 1));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(Resources.gameLocation + "/res/font/bitmap.fnt")));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isEmpty()) continue;

                FontGlyph glyph = new FontGlyph();
                String[] parts = line.split(" +");

                for (int i = 0; i < parts.length; i++) {
                    String[] pairs = parts[i].split("=");

                    for (int j = 0; j < pairs.length; j += 2) {
                        String key = pairs[j];
                        int value = Integer.valueOf(pairs[j + 1]);

                        switch (key) {
                            case "id":
                                glyph.id = value;
                                break;
                            case "x":
                                glyph.x = value;
                                break;
                            case "y":
                                glyph.y = value;
                            case "width":
                                glyph.width = value;
                                break;
                            case "height":
                                glyph.height = value;
                                break;
                            case "xoffset":
                                glyph.xOffset = value;
                                break;
                            case "yoffset":
                                glyph.yOffset = value;
                                break;
                            case "xadvance":
                                glyph.xAdvance = value;
                                break;
                        }
                    }
                }

                glyphs.put(glyph.id, glyph);
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        float width = 512f; // The width should of course be the actual pixel width of the image.
        float height = 512f; // The height should of course be the actual pixel height of the image.

        for (FontGlyph glyph : glyphs.values()) {

            glyph.u = glyph.x / width;
            glyph.v = glyph.y / height;
            glyph.u2 = (glyph.x / width) + (glyph.width / width);
            glyph.v2 = (glyph.y / height) + (glyph.height / height);


        }
    }

    public static void renderCenteredString(String text, float y, float size, Color color) {
        renderString(text, (Display.getWidth() - getWidth(text, size)) / 2, y, size, color);
    }

    public static float renderString(String text, float x, float y, float size, Color color) {
        return renderString(text, x, y, size, color, true);
    }

    public static float renderString(String text, float x, float y, float size, Color color, boolean render) {
        text = text.toUpperCase();
        size /= 12f;
        float startX = x;

        float r = color.getRed() / 255f;
        float gl = color.getGreen() / 255f;
        float bl = color.getBlue() / 255f;

        if (render) {
            glPushAttrib(GL_TEXTURE_BIT | GL_ENABLE_BIT | GL_COLOR_BUFFER_BIT);
            glEnable(GL_TEXTURE_2D);
            glBindTexture(GL_TEXTURE_2D, tex);
            glEnable(GL_BLEND);
            glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            glPushMatrix();
            glBegin(GL_QUADS);
            glEnable(GL_COLOR);
            glColor3f(r, gl, bl);
        }


        for (int i = 0, istop = text.length(); i < istop; i++) {
            int ascii = (int) text.charAt(i);

            FontGlyph g = glyphs.get(ascii);

            float ww = g.width * size;
            float hh = g.height * size;

            float xx = x + g.xOffset * size;
            float yy = y + g.yOffset * size;


            if (render) {
                glTexCoord2f(g.u, g.v);
                glVertex2f(xx, yy);

                glTexCoord2f(g.u2, g.v);
                glVertex2f(xx + ww, yy);

                glTexCoord2f(g.u2, g.v2);
                glVertex2f(xx + ww, yy + hh);

                glTexCoord2f(g.u, g.v2);
                glVertex2f(xx, yy + hh);
            }

            x += (g.xAdvance) * size;
        }

        if (render) {

            GL11.glDisable(GL11.GL_COLOR);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnd();
            GL11.glPopMatrix();

            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }


        // todo maybe just make seperate method for finding the size instaed of doing if (render) xd
        return x - startX;
    }

    public static float getWidth(String string, float size) {
        return renderString(string, 0, 0, size, Color.black, false);
    }
}
