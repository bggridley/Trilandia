package com.loafy.game.item;

import org.newdawn.slick.Color;

public enum ItemTier {

    DEFAULT(new Color(235, 235, 235)), NORMAL(new Color(255, 255, 255)), COMMON1(new Color(255, 209, 104)), COMMON2(new Color(152, 255, 104)), COMMON3(new Color(112, 250, 240)),
    RARE1(new Color(94, 110, 255)), RARE2(new Color(159, 50, 255)), RARE3(new Color(237, 75, 255)), LEGENDARY(new Color(255, 73, 73));

    private Color color;

    ItemTier(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

}
