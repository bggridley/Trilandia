package com.loafy.game.gfx;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Graphics {

    private static float r;
    private static float g;
    private static float b;
    private static float a;

    public static void setColor(Color color) {
        r = color.getRed() / 255.0F;
        g = color.getGreen() / 255.0F;
        b = color.getBlue() / 255.0F;
        a = color.getAlpha() / 255.0F;
    }

    public static void drawLine(float x1, float y1, float x2, float y2, int width) {
        //GL11.glColor4f(r, g, b, a);
        //GL11.glRectd(x1, y1, x2, y2);


        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_COLOR);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glLineWidth(width);

        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glColor4f(r, g, b, a);

        GL11.glVertex2f(x1, y1);
        GL11.glVertex2f(x2, y2);

        GL11.glDisable(GL11.GL_COLOR);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnd();
        GL11.glPopMatrix();


    }

    public static void drawBorderedRect(float x, float y, float width, float height, int lwidth, Color outer, Color inner) {
        setColor(inner);
        fillRect(x, y, width, height);

        setColor(outer);
        drawRect(x, y, width, height, lwidth);
    }

    public static void drawRect(float x, float y, float width, float height, int lwidth) {

        int rr = (lwidth / 2); //half width

        // top
        drawLine(x, y + rr, x + width, y + rr, lwidth);
        // right
        drawLine(x + width - rr, y, x + width - rr, y + height, lwidth);
        //bottom
        drawLine(x, y + height - rr, x + width, y + height - rr, lwidth);
        //left
        drawLine(x + rr, y, x + rr, y + height, lwidth);

    }

    public static void fillRect(float x, float y, float width, float height) {
        GL11.glColor4f(r, g, b, a);
        GL11.glRectd(x, y, x + width, y + height);
        /*
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_COLOR);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glBegin(GL11.GL_QUADS);

        GL11.glColor4f(r, g, b, a);

        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x, y + height);

        GL11.glDisable(GL11.GL_COLOR);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnd();
        GL11.glPopMatrix();
        */
    }
}
